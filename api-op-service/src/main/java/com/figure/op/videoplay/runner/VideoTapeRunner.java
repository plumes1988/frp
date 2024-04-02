package com.figure.op.videoplay.runner;

import com.figure.op.cameramanager.domain.CameraInfo;
import com.figure.op.videoplay.other.CommandManager;
import com.figure.op.videoplay.other.CommandManagerImpl;
import com.figure.op.videoplay.service.RealViewService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author sdt
 * @version 1.0
 * @description:回放任务
 * @date 2023/9/20 13:44
 */
@Component
public class VideoTapeRunner implements CommandLineRunner {

    public static CommandManager manager = new CommandManagerImpl(20);
    @Value("${playback.save_path}")
    private String savepath;




    /**
     * 获取摄像头列表
     */
    @Resource
    private RealViewService realViewService;

    @Override
    public void run(String... args) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //获取摄像头列表
        List<CameraInfo> cameraList = realViewService.getCameraList();
        for (CameraInfo cameraInfo : cameraList) {
            String rtspUrl = "rtsp://" + cameraInfo.getUsername() + ":" + cameraInfo.getPassword() + "@" + cameraInfo.getIp() + ":554" + "/" + "/Streaming/Channels/"+ cameraInfo.getChannelNo();

            String outPutPath = savepath+cameraInfo.getCameraCode();

            Date date = new Date();

            String day = sdf.format(date);

            File file = new File(outPutPath+File.separator+day+File.separator);
            if (!file.exists()){
                file.mkdirs();
            }
            String command = "ffmpeg  -loglevel quiet  -i "+rtspUrl+" -c:v copy -an -f hls -hls_time 5 -hls_list_size 100 -strftime 1  -hls_segment_filename "+
                    outPutPath+File.separator+"%Y-%m-%d"+File.separator+"%Y-%m-%d_%H-%M-%S.ts " +  outPutPath+File.separator+"output.m3u8 ";
            manager.start("play_back_"+cameraInfo.getCameraId(),command);
        }
    }

    @PreDestroy
    public  void end(){
        manager.stopAll();
    }
}