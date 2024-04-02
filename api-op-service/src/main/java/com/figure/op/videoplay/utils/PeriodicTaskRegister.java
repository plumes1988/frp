package com.figure.op.videoplay.utils;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @Author liqiang
 * @Date 2023/10/11 15:09
 * @Version 1.5
 */
@Component
public class PeriodicTaskRegister implements DisposableBean {
    private final Map<Runnable, ScheduledTask> scheduledTasks = new ConcurrentHashMap<>(16);

    public Map<Runnable, ScheduledTask> getScheduledTasks() {
        return scheduledTasks;
    }

    @Autowired
    private TaskScheduler taskScheduler;

    public TaskScheduler getScheduler() {
        return this.taskScheduler;
    }

    public void addCronTask(Runnable task, long period, TimeUnit timeUnit) {
        addCronTask(new PeriodicTask(task, period,timeUnit));
    }

    public void addCronTask(PeriodicTask periodicTask) {
        if (periodicTask != null) {
            Runnable task = periodicTask.getRunnable();
            if (this.scheduledTasks.containsKey(task)) {
                removeCronTask(task);
            }

            this.scheduledTasks.put(task, scheduleCronTask(periodicTask));
        }
    }

    public void removeCronTask(Runnable task) {
        ScheduledTask scheduledTask = this.scheduledTasks.remove(task);
        if (scheduledTask != null)
        {
            scheduledTask.cancel();
        }
    }

    public ScheduledTask scheduleCronTask(PeriodicTask periodicTask) {
        ScheduledTask scheduledTask = new ScheduledTask();
        scheduledTask.future = this.taskScheduler.schedule(periodicTask.getRunnable(), periodicTask.getTrigger());

        return scheduledTask;
    }


    @Override
    public void destroy() {
        for (ScheduledTask task : this.scheduledTasks.values()) {
            task.cancel();
        }
        this.scheduledTasks.clear();
    }
}
