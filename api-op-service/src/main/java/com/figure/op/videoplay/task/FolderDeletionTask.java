package com.figure.op.videoplay.task;

import com.figure.op.cameramanager.mapper.CameraInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author liqiang
 * @Date 2023/9/20 15:46
 * @Version 1.5
 */
@Component
@Slf4j
public class FolderDeletionTask {
    @Value("${playback.save_path}")
    String playBackSavePath;
    @Value("${playback.save_days}")
    Integer playBackSaveDays;

    @Resource
    CameraInfoMapper cameraInfoMapper;

    /**
     * 每天凌晨执行
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteFolders() {
        // 获取当前日期
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        // 获取设定的保存录像日期
        calendar.add(Calendar.DAY_OF_YEAR, -playBackSaveDays);
        Date deleteDate = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //三天前日期
        String deleteFolderName = sdf.format(deleteDate);
        File folder = new File(playBackSavePath);
        if (folder.exists() && folder.isDirectory()) { //第一层
            File[] files = folder.listFiles();
            for (File file : files) {                //id层
                File[] subPath = file.listFiles();
                for (File subFile : subPath) {       //日期层
                    //文件夹时间与三天前时间进行比较
                    if (subFile.isDirectory() && subFile.getName().compareTo(deleteFolderName)<0){
                        String cmd = "rm -rf " + subFile.getAbsolutePath();
                        try{
                            Process exec = Runtime.getRuntime().exec(cmd);
                            exec.waitFor();
                            log.info("删除文件夹成功");
                        }catch (Exception e){
                            log.info("删除文件夹失败");
                        }
                    }
                }
            }
        } else {
            System.out.println("文件夹不存在或者不是一个有效的文件夹路径：" + playBackSavePath);
        }
    }
}
