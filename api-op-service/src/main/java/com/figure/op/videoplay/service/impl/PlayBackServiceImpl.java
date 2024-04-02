package com.figure.op.videoplay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.figure.op.cameramanager.domain.CameraInfo;
import com.figure.op.cameramanager.domain.Pair;
import com.figure.op.cameramanager.mapper.CameraInfoMapper;
import com.figure.op.common.domain.R;
import com.figure.op.common.exception.GlobalException;
import com.figure.op.videoplay.domain.RealMonitor;
import com.figure.op.videoplay.domain.vo.HistoryThermometry;
import com.figure.op.videoplay.filter.FileFilter;
import com.figure.op.videoplay.mapper.RealMonitorMapper;
import com.figure.op.videoplay.runner.VideoTapeRunner;
import com.figure.op.videoplay.service.PlayBackService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author lizhijie
 * @version 1.0
 * @description:TODO
 * @date 2023/9/19 14:32
 */

@Service
public class PlayBackServiceImpl implements PlayBackService {
    @Value("${playback.save_path}")
    private String savepath;

    @Value("${playback.m3Url}")
    private String m3url;
    @Resource
    private RealMonitorMapper realMonitorMapper;

    @Resource
    private CameraInfoMapper cameraInfoMapper;

    @Override
    public List<HistoryThermometry> historyThermometry(Integer cameramaId, String startTime, String endTime) {

        List<HistoryThermometry> resultData = new ArrayList<>();


        QueryWrapper<RealMonitor> realMonitorQueryWrapper = new QueryWrapper<>();
        realMonitorQueryWrapper.eq("cameraId", cameramaId).between("insertTime", startTime, endTime).orderByAsc("insertTime");
        List<RealMonitor> list = realMonitorMapper.selectList(realMonitorQueryWrapper);
        //按照部件id进行分组
        Map<String, List<RealMonitor>> collect = list.stream().collect(Collectors.groupingBy(RealMonitor::getPartCode));
        Set<String> keySet = collect.keySet();


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (String key : keySet) {
            HistoryThermometry historyThermometry = new HistoryThermometry();
            List<Pair<String, Float>> pairs = new ArrayList<>();
            List<RealMonitor> realMonitorList = collect.get(key);
            for (int i = 0; i < realMonitorList.size(); i++) {
                RealMonitor monitor = realMonitorList.get(i);
                if (i == 0) {
                    historyThermometry.setPartCode(monitor.getPartCode());
                    historyThermometry.setPartName(monitor.getPartName());
                }
                pairs.add(new Pair<String, Float>(sdf.format(monitor.getInsertTime()), monitor.getTemperature()));
            }
            historyThermometry.setList(pairs);
            resultData.add(historyThermometry);
        }
        return resultData;
    }

    @Override
    public R getM3u8FIle(Integer cameramaId, String startTime, String endTime, HttpServletResponse response) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = dateFormat.parse(startTime);
            endDate = dateFormat.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(calcSubTime(startDate,endDate)<-60*60*3-10){
            throw new RuntimeException("回放时间不能大于3小时！");
        }


        //计算时间差 选择文件
        String startDay = startTime.substring(0, 10);
        String endDay = endTime.substring(0, 10);

        ArrayList<String> fileList = new ArrayList<>();


        //根据id查摄像头信息
        CameraInfo cameraInfo = cameraInfoMapper.selectById(cameramaId);
        String cameraCode = cameraInfo.getCameraCode();
        //获取所有的文件名
        FileFilter filter = new FileFilter(dateCalcMin(startTime, 0, 0), dateCalcMin(endTime, 0, 0));
        File file1 = new File(savepath + cameraCode + File.separator + startDay);
        File file2 = new File(savepath + cameraCode + File.separator + endDay);
        //文件夹为空判断

        File[] files1;
        if (file1.exists()){
            files1 = file1.listFiles(filter);

            for (int i = 0; i < files1.length; i++) {
                fileList.add(files1[i].getAbsolutePath());
            }
        }

        //隔天处理
        if (file2.exists()){
            if (!startDay.equals(endDay)) {
                file1 = new File(savepath + cameraCode + File.separator + endDay);
                files1 = file1.listFiles(filter);
                for (int i = 0; i < files1.length; i++) {
                    fileList.add(files1[i].getAbsolutePath());
                }
            }
        }
        if (fileList.size()<1){
            return R.fail("录像文件不存在！");
        }
        //文件名排序
        List<String> list = fileList.stream().sorted(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        }).collect(Collectors.toList());

        String uuid = UUID.randomUUID().toString().substring(0,5);
        //组装命令
        String command = "cd "+savepath + cameraCode + File.separator + startDay + "&& ffmpeg -i \"concat:";
        for (int i = 0; i < list.size(); i++) {
            command +=list.get(i);
            if (i!=list.size()-1){
                command+="|";
            }
        }

        command += "\" -c copy -f hls -hls_time 5 -hls_segment_filename "+uuid+"_%3d  -hls_playlist_type vod " +savepath + cameraCode + File.separator + startDay +File.separator + uuid+".m3u8";
        Runtime rt = Runtime.getRuntime();
        Process p = null;
        try {
            if (System.getProperty("os.name").toLowerCase().contains("windows")){
                p = rt.exec(command);
            }
            if (System.getProperty("os.name").toLowerCase().contains("linux"))
            {
                String [] cmd={"/bin/sh","-c",command};
                p = rt.exec(cmd);
            }


            //获取进程的标准输入流
            final InputStream is1 = p.getInputStream();
            //获取进城的错误流
            final InputStream is2 = p.getErrorStream();
            //启动两个线程，一个线程负责读标准输出流，另一个负责读标准错误流
            new Thread() {
                @Override
                public void run() {
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(is1));
                    try {
                        String line1 = null;
                        while ((line1 = br1.readLine()) != null) {
                            if (line1 != null) {
                                System.out.println(line1);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            is1.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();

            new Thread() {
                @Override
                public void run() {
                    BufferedReader br2 = new BufferedReader(new InputStreamReader(is2));
                    try {
                        String line2 = null;
                        while ((line2 = br2.readLine()) != null) {
                            if (line2 != null) {
                                System.out.println(line2);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            is2.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();

            p.waitFor(600, TimeUnit.SECONDS);
            p.destroy();
            System.out.println("我想被打印...");
        } catch (Exception e) {
            try {
                p.getErrorStream().close();
                p.getInputStream().close();
                p.getOutputStream().close();
            } catch (Exception ee) {
            }
        }


        File file = new File(savepath + cameraCode + File.separator + startDay+ File.separator+ uuid+".m3u8");
        if (!file.exists()) {
            return R.fail("回放文件生成失败！");
        }

        return R.ok(m3url+cameraCode + File.separator + startDay +File.separator+ uuid+".m3u8");
    }

    @Override
    public void getVidedo(Integer cameramaId, String startTime, String endTime, HttpServletResponse response) {

        String startDay = startTime.substring(0, 10);
        String endDay = endTime.substring(0, 10);




        ArrayList<String> fileList = new ArrayList<>();
        //获取所有的文件名
        FileFilter filter = new FileFilter(dateCalcMin(startTime, 0, 0), dateCalcMin(endTime, 0, 0));
        File file1 = new File(savepath + cameramaId + File.separator + startDay);

        File[] files1 = file1.listFiles(filter);

        for (int i = 0; i < files1.length; i++) {
            fileList.add(files1[i].getAbsolutePath());
        }
        //隔天处理
        if (!startDay.equals(endDay)) {
            file1 = new File(savepath + cameramaId + File.separator + endDay);
            files1 = file1.listFiles(filter);
            for (int i = 0; i < files1.length; i++) {
                fileList.add(files1[i].getAbsolutePath());
            }
        }
        //文件名排序
        List<String> list = fileList.stream().sorted(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        }).collect(Collectors.toList());



        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        //计算时间差 选择文件
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = dateFormat.parse(startTime);
            endDate = dateFormat.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(calcSubTime(startDate,endDate)<-60*30){
            throw new RuntimeException("回放时间不能大于30分钟");
        }
        if(calcSubTime(endDate,new Date())>-60*5){
            throw new RuntimeException("视频暂未生成，请五分钟后查看");
        }

        Integer ss = 0;
        Integer to = 0;
        Boolean beginFlag = true;
        while (list.size() > 0 && beginFlag) {
            Integer sub = calcSubTime(startDate, pathToTime(list.get(0)));
            //如果65,就表明与视频无缘，小于0说明第一个视频已经超前了
            if (sub > 305 ) {
                list.remove(0);
                continue;
            }
            //<0说明 第一个视频已在开始时间和结束时间的区间之内
            if (sub<0){
                beginFlag = false;
                continue;
            }
            //如果在60-65之间，就说明需要取前一段视频的后五秒
            if (sub > 300&&sub < 305) {
                beginFlag = false;
                ss = 300 - (5 - (sub - 300));
                break;
            }

            beginFlag = false;
            //到这一步说明只与一个视频关联：小于5表示前面也没视频了，ss=0,只能这么着了
            if (sub - 5 > 0) {
                ss = sub - 5;
            }
        }
        Boolean endFlag = true;
        while (list.size() > 0 && endFlag) {
            Date subEndDate = pathToTime(list.get(list.size() - 1));
            Calendar instance = Calendar.getInstance();
            instance.setTime(subEndDate);
            instance.add(Calendar.MINUTE, 5);
            Integer sub = calcSubTime(endDate, instance.getTime());
            //小于-65大于0无缘
            if (sub < -305) {
                list.remove(list.size() - 1);
                continue;
            }
            //两个视频：60-65之间说明能取到前面几秒
            if (sub < -300 && sub > -305) {
                endFlag = false;
                to = sub+305;
                break;
            }

            //一个视频，如果不满足，只能这么着了
            endFlag = false;
            if ((300 + sub + 5) < 300) {
                to = 300 + sub + 5;
            }
        }

        //组装命令
        if (list.size() < 1) {
            throw new GlobalException("回放文件生成失败:录像文件不存在");
        }
        String command = packVideoConcatCommand(list, ss, to);

        //得到输出文件名
        String uid = UUID.randomUUID().toString().substring(0, 8);
        String outPutPath = savepath + cameramaId + File.separator + startDay + File.separator + uid + ".mp4";
        command += outPutPath;
//        try {
//            //执行命令
//            Process p = Runtime.getRuntime().exec(command);
//
//            p.waitFor();
//            p.destroy();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        Runtime rt = Runtime.getRuntime();
        Process p = null;
        try {
            if (System.getProperty("os.name").toLowerCase().contains("windows")){
                p = rt.exec(command);
            }
            if (System.getProperty("os.name").toLowerCase().contains("linux"))
            {
                String [] cmd={"/bin/sh","-c",command};
                p = rt.exec(cmd);
            }


            //获取进程的标准输入流
            final InputStream is1 = p.getInputStream();
            //获取进城的错误流
            final InputStream is2 = p.getErrorStream();
            //启动两个线程，一个线程负责读标准输出流，另一个负责读标准错误流
            new Thread() {
                @Override
                public void run() {
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(is1));
                    try {
                        String line1 = null;
                        while ((line1 = br1.readLine()) != null) {
                            if (line1 != null) {
                                System.out.println(line1);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            is1.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();

            new Thread() {
                @Override
                public void run() {
                    BufferedReader br2 = new BufferedReader(new InputStreamReader(is2));
                    try {
                        String line2 = null;
                        while ((line2 = br2.readLine()) != null) {
                            if (line2 != null) {
                                System.out.println(line2);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            is2.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();

            p.waitFor(600, TimeUnit.SECONDS);
            p.destroy();
            System.out.println("我想被打印...");
        } catch (Exception e) {
            try {
                p.getErrorStream().close();
                p.getInputStream().close();
                p.getOutputStream().close();
            } catch (Exception ee) {
            }
        }


        File file = new File(outPutPath);
        if (!file.exists()) {
            throw new GlobalException("回放文件生成失败");
        }


        try {
            FileInputStream fileInput = new FileInputStream(outPutPath);
            int i = fileInput.available();
            byte[] content = new byte[i];
            fileInput.read(content);
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename="
                    .concat(uid + ".mp4"));
            OutputStream output = response.getOutputStream();
            output.write(content);
            output.flush();
            fileInput.close();
            output.close();

        } catch (
                Exception e) {
            throw new GlobalException(e.getMessage());
        }

    }

    /**
     * 停止并且删除文件，用于删除摄像头时进行调用
     */
    @Override
    public boolean stopAndDeleteDir(Integer cameramaId) {
        //根据id查摄像头信息
        CameraInfo cameraInfo = cameraInfoMapper.selectById(cameramaId);
        String cameraCode = cameraInfo.getCameraCode();
        VideoTapeRunner.manager.stop("play_back_" + cameramaId);
        String dirPath = savepath + cameraCode;
        try {
            FileUtils.forceDelete(new File(dirPath));
        } catch (IOException e) {
            throw new GlobalException("摄像头相关视频文件删除失败");
        }
        return true;
    }

    @Override
    public boolean updateVideoTape(CameraInfo cameraInfo) {
        VideoTapeRunner.manager.stop("play_back_" + cameraInfo.getCameraCode());
        addVideoTape(cameraInfo);
        return true;
    }

    @Override
    public boolean addVideoTape(CameraInfo cameraInfo) {
        String rtspUrl = "rtsp://" + cameraInfo.getUsername() + ":" + cameraInfo.getPassword() + "@" + cameraInfo.getIp() + ":554" + "/" + "/Streaming/Channels/" + cameraInfo.getChannelNo();

        String outPutPath = savepath + cameraInfo.getCameraCode();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String day = sdf.format(date);

        File file = new File(outPutPath + File.separator + day + File.separator);
        if (!file.exists()) {
            file.mkdirs();
        }
        String command = "ffmpeg -use_wallclock_as_timestamps 1 -loglevel quiet  -i " + rtspUrl + " -c:v copy -an -f segment -segment_time 60 -reset_timestamps 1 -strftime 1 " + outPutPath + File.separator + "%Y-%m-%d" + File.separator + "%Y-%m-%d_%H-%M-%S.mp4";
        VideoTapeRunner.manager.start("play_back_" + cameraInfo.getCameraId(), command);
        return true;
    }


    //时间以分钟做加减
    private String dateCalcMin(String time, Integer minNum, Integer secNum) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = dateFormat.parse(time);

            Calendar instance = Calendar.getInstance();

            instance.setTime(date);

            instance.add(Calendar.MINUTE, minNum);

            instance.add(Calendar.SECOND, secNum);
            DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
            return dateFormat1.format(instance.getTime());
        } catch (ParseException e) {
            throw new GlobalException("回放：时间戳计算异常");
        }
    }


    //计算时间差:单位秒
    private Integer calcSubTime(Date time1, Date time2) {
        System.out.println(time1);
        System.out.println(time2);

        // 将日期对象转换为Calendar对象
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(time1);
        cal2.setTime(time2);

        // 计算两个Calendar对象之间的差距
        long diffInMillis = cal1.getTimeInMillis() - cal2.getTimeInMillis();

        // 将毫秒转换为秒
        long diffInSeconds = diffInMillis / 1000;
        return (int) diffInSeconds;
    }


    //路径转时间
    public Date pathToTime(String path) {

        File file = new File(path);
        String fileName = file.getName().split(".mp4")[0];
        String[] split = fileName.split("_");
        Date parse = null;
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            parse = dateFormat.parse(split[0] + " " + split[1].replace("-", ":"));
        } catch (ParseException e) {
            throw new GlobalException("回放：文件名转时间错误");
        }
        return parse;
    }

    //组装拼接视频命令
    public String packVideoConcatCommand(List<String> list, Integer ss, Integer to) {
        StringBuilder builder = new StringBuilder();
        builder.append("ffmpeg  ");

        for (int i = 0; i < list.size(); i++) {
            if (i == 0 && ss != 0) {
                builder.append("-ss " + ss + " ");
            }
            if ((i == list.size() - 1) && to != 0) {
                builder.append("-to " + to + " ");
            }
            builder.append("-i " + list.get(i) + " ");
        }
        if (list.size() > 1) {
            builder.append("-filter_complex \"concat=n=" + list.size() + ":v=1\" -c:v libx264  ");
        }
        return builder.toString();
    }


}
