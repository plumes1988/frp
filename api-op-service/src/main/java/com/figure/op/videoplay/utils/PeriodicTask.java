package com.figure.op.videoplay.utils;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.PeriodicTrigger;

import java.util.concurrent.TimeUnit;

/**
 * @Author liqiang
 * @Date 2023/10/11 15:05
 * @Version 1.5
 */
@Setter
@Getter
public class PeriodicTask extends TriggerTask {
    private final long period;
    private final TimeUnit timeUnit;

    public PeriodicTask(Runnable runnable, long period,TimeUnit timeUnit) {
        this(runnable, new PeriodicTrigger(period,timeUnit));
    }

    public PeriodicTask(Runnable runnable, PeriodicTrigger periodicTrigger) {
        super(runnable, periodicTrigger);
        this.period = periodicTrigger.getPeriod();
        this.timeUnit = periodicTrigger.getTimeUnit();
    }
}
