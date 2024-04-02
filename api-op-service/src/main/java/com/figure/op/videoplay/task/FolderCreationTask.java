package com.figure.op.videoplay.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.figure.op.cameramanager.domain.CameraInfo;
import com.figure.op.cameramanager.mapper.CameraInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author liqiang
 * @Date 2023/9/20 14:55
 * @Version 1.5
 */
@Component
@Slf4j
public class FolderCreationTask {

    @Value("${playback.save_path}")
    String playBackSavePath;

    @Resource
    CameraInfoMapper cameraInfoMapper;

    /**
     * 每天凌晨执行
     */
     @Scheduled(cron = "0 0 0 * * ?")
    public void createFolder() {
        // 获取今天的日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String todayFolderName = sdf.format( new Date(System.currentTimeMillis()));
        // 明天
        Date yesterday = new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000);
        String tomorrowFolderName = sdf.format(yesterday);

        //查询所有摄像头以摄像头id建立路径，路径下建立文件夹
        List<CameraInfo> cameraInfos = cameraInfoMapper.selectList(new QueryWrapper<CameraInfo>());
        String folderPath ;
        for (CameraInfo cameraInfo : cameraInfos) {
            folderPath =  playBackSavePath + File.separator + cameraInfo.getCameraId().toString()+ File.separator + todayFolderName;
            createFolderByPath(folderPath);
            folderPath =  playBackSavePath + File.separator + cameraInfo.getCameraCode() + File.separator + tomorrowFolderName;
            createFolderByPath(folderPath);
        }
    }

    private void createFolderByPath(String folderPath) {
        File folder = new File(folderPath);
        if (!folder.exists()) {
            boolean success = folder.mkdir();
            if (success) {
                log.info("文件夹创建成功：" + folderPath);
            } else {
                log.info("文件夹创建失败：" + folderPath);
            }
        } else {
            log.info("文件夹已存在：" + folderPath);
        }
    }
}
