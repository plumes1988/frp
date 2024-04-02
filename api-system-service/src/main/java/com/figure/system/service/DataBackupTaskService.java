package com.figure.system.service;


import com.figure.core.helper.QuartzscheduleruleMgr;
import com.figure.core.model.base.BaseDataBackupTask;
import com.figure.core.service.base.IBaseDataBackupTaskService;
import com.figure.system.job.DataBackupTaskJob;
import lombok.Data;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

@Service
@Data
public class DataBackupTaskService {

    @Autowired
    IBaseDataBackupTaskService logBakTaskService;

    @Autowired
    BackupService backupService;

    private String groupName = "DataBackupTask";


    public void addTaskJob(Integer taskId){
        BaseDataBackupTask logBakTask = logBakTaskService.getById(taskId);
        Scheduler sched = QuartzscheduleruleMgr.getInstanceScheduler();
        JobDetail job =newJob(DataBackupTaskJob.class)
                .withIdentity("job_"+taskId, groupName+"1")
                .build();

        Integer mode = logBakTask.getTaskMode();

        String expression = "";
        CronTrigger trigger = null;

        if(0==mode){
            //单次
            Date ExcuteTime = logBakTask.getExcuteTime();
            Calendar cal = Calendar.getInstance();
            cal.setTime(ExcuteTime);
            int year = cal.get(Calendar.YEAR);//获取年份
            int month=cal.get(Calendar.MONTH)+1;//获取月份
            int day=cal.get(Calendar.DATE);//获取日
            int hour=cal.get(Calendar.HOUR_OF_DAY);//小时
            int minute=cal.get(Calendar.MINUTE);//分
            int second=cal.get(Calendar.SECOND);//秒
            expression = second+" "+minute+" "+hour+" "+day+" "+month+" ? "+year;
            trigger = newTrigger().withIdentity("job_"+taskId, groupName+"3").withSchedule(cronSchedule(expression).withMisfireHandlingInstructionDoNothing())
                    .build();
        }else if(3==mode) {
            //月模式
            expression = "0 0 0 "+logBakTask.getDayOfMonth()+" * ? *";
            trigger = newTrigger().withIdentity("job_"+taskId, groupName+"3").withSchedule(cronSchedule(expression).withMisfireHandlingInstructionDoNothing())
                    .build();
        }else if(4==mode) {
            //年模式
            String[] arr = logBakTask.getDateOfYear().split(" ");
            expression = "0 0 0 "+arr[1]+" "+arr[0]+" ? *";
            trigger = newTrigger().withIdentity("job_"+taskId, groupName+"3").withSchedule(cronSchedule(expression).withMisfireHandlingInstructionDoNothing())
                    .build();
        }

        job.getJobDataMap().put("backupService",backupService);
        job.getJobDataMap().put("taskId",taskId);

        try {
            sched.scheduleJob(job, trigger);
            sched.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }


    public void deleteTaskJob(Integer taksId){
        Scheduler sched = QuartzscheduleruleMgr.getInstanceScheduler();
        try{
            TriggerKey triggerKey3 = TriggerKey.triggerKey("job_"+taksId, groupName+"3");
            sched.pauseTrigger(triggerKey3);// 停止触发器
            sched.unscheduleJob(triggerKey3);// 移除触发器
            sched.deleteJob(JobKey.jobKey("job_"+taksId, groupName+"1"));// 删除任务
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
