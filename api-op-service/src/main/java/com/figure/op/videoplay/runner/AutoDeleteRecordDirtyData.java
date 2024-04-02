package com.figure.op.videoplay.runner;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.figure.op.videoplay.domain.WarningRecord;
import com.figure.op.videoplay.mapper.WarningRecordMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;

/**
 * @author sdt
 * @version 1.0
 * @description:TODO
 * @date 2023/11/7 23:37
 */
@Component
public class AutoDeleteRecordDirtyData implements CommandLineRunner {

    @Resource
    private WarningRecordMapper warningRecordMapper;

    @Override
    public void run(String... args) throws Exception {
        Calendar calendar=Calendar.getInstance();
        calendar.set(1972,00,01,00,00,00);
        Date calendarTime1 = calendar.getTime();

        LambdaUpdateWrapper<WarningRecord> updateWrapper = Wrappers.<WarningRecord>lambdaUpdate()
                .lt(WarningRecord::getEndTime, calendarTime1);
        warningRecordMapper.delete(updateWrapper);
    }
}
