package com.figure.core.helper;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;


public class QuartzscheduleruleMgr {
    private static final Scheduler scheduler=getScheduler();
    /**
     * 创建一个调度对象
     * @return
     * @throws SchedulerException
     */
    private static Scheduler getScheduler() {
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler scheduler=null;
        try {
            scheduler = sf.getScheduler();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return scheduler;
    }
    public static Scheduler getInstanceScheduler(){
        return scheduler;
    }
}

