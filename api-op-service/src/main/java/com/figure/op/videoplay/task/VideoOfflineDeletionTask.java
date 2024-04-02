package com.figure.op.videoplay.task;

import com.figure.op.cameramanager.domain.CameraInfo;
import com.figure.op.cameramanager.domain.Pair;
import com.figure.op.cameramanager.netsdk.HCNetBase;
import com.figure.op.common.exception.GlobalException;
import com.figure.op.videoplay.other.data.CommandTasker;
import com.figure.op.videoplay.runner.VideoTapeRunner;
import com.figure.op.videoplay.service.RealViewService;
import com.figure.op.videoplay.service.impl.RealViewServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author sdt
 * @version 1.0
 * @description:TODO
 * @date 2023/12/19 10:52
 */
@Component
@Slf4j
public class VideoOfflineDeletionTask {
    @Resource
    private RealViewService realViewService;

    @Value("${playback.save_path}")
    private String savepath;


    @Value("${nginx.rtmp.url}")
    String rtmpTempUrl;

    @Value("${nginx.http_flv.url}")
    String httpFlv;


    @Scheduled(cron = "* 0/1 * * * ? ")
    public void deletionPlayBack() {

        //查询摄像头
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //获取摄像头列表
        List<CameraInfo> cameraList = realViewService.getCameraList();
        for (CameraInfo bo : cameraList) {

            boolean status = false;
            try {
                status = InetAddress.getByName(bo.getIp()).isReachable(3);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //如果在线     manager中没有则进行添加
            if (status && Objects.isNull(VideoTapeRunner.manager.query("play_back_" + bo.getCameraId()))) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //录像
                String rtspUrl = "rtsp://" + bo.getUsername() + ":" + bo.getPassword() + "@" + bo.getIp() + ":554" + "/" + "/Streaming/Channels/" + bo.getChannelNo();

                String outPutPath = savepath + bo.getCameraCode();

                Date date = new Date();

                String day = sdf.format(date);

                File file = new File(outPutPath + File.separator + day + File.separator);
                if (!file.exists()) {
                    file.mkdirs();
                }
                String command = "ffmpeg  -loglevel quiet  -i " + rtspUrl + " -c:v copy -an -f hls -hls_time 5 -hls_list_size 100 -strftime 1  -hls_segment_filename " +
                        outPutPath + File.separator + "%Y-%m-%d" + File.separator + "%Y-%m-%d_%H-%M-%S.ts " + outPutPath + File.separator + "output.m3u8 ";
                VideoTapeRunner.manager.start("play_back_" + bo.getCameraId(), command);
            }

        }


    }

    @Scheduled(cron = "0/10 * * * * ?")
    public void deletionTaskAndStop() {
        //获取摄像头列表
        List<CameraInfo> cameraList = realViewService.getCameraList();
        for (CameraInfo bo : cameraList) {
            boolean status = false;
            try {
                status = InetAddress.getByName(bo.getIp()).isReachable(3);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //不在线，停掉相关任务
            if (!status) {
                RealViewServiceImpl.manager.stop(bo.getCameraId().toString());
                VideoTapeRunner.manager.stop("play_back_" + bo.getCameraId());
            }
        }
    }

    public static void main(String[] args) {
        boolean status = false;
        try {
            status = InetAddress.getByName("36.152.44.95").isReachable(3);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!status){
            System.out.println("11111");
        }else {
            System.out.println("reached");
        }
    }
}
