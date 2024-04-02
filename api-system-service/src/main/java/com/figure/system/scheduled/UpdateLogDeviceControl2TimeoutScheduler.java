package com.figure.system.scheduled;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.figure.core.model.log.LogDeviceControl;
import com.figure.core.service.log.ILogDeviceControlService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static com.figure.core.constant.Constants.SET_INDICATOR_TIMEOUT;
import static com.figure.core.model.log.LogDeviceControl.SETTING;
import static com.figure.core.model.log.LogDeviceControl.TIMEOUT;

@Component
@EnableScheduling
public class UpdateLogDeviceControl2TimeoutScheduler {

    @Resource
    ILogDeviceControlService logDeviceControlService;

    @Scheduled(fixedRate = 5 * 1000, initialDelay = 20 * 1000)//间隔5秒
    private void alarmCheckTask() {

        // 创建 QueryWrapper
        UpdateWrapper<LogDeviceControl> updateWrapper = new UpdateWrapper();

        // 获取当前时间并减去5秒
        LocalDateTime fiveSecondBefore = LocalDateTime.now().minus(5, ChronoUnit.SECONDS);

        // 设置时间条件
        updateWrapper.le("requestTime", fiveSecondBefore);

        updateWrapper.eq("requestStatus",SETTING);

        updateWrapper.set("requestStatus", TIMEOUT)
                .set("responseContent", SET_INDICATOR_TIMEOUT);

        logDeviceControlService.update(null, updateWrapper);
    }
}
