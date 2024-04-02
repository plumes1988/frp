package com.figure.system.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.figure.core.helper.QuartzscheduleruleMgr;
import com.figure.core.model.base.BaseDataBackupTask;
import com.figure.core.service.base.IBaseDataBackupTaskService;
import com.figure.system.job.BackupTaskJob;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
@Service
@Data
@Slf4j
public class BackupTaskService {

    @Autowired
    IBaseDataBackupTaskService logBakTaskService;

    @Autowired
    DataBackupTaskService dataBackupTaskService;

    private String groupName = "BackupTask";


    public void addTaskJob(Integer taskId){
        BaseDataBackupTask logBakTask = logBakTaskService.getById(taskId);
        Scheduler sched = QuartzscheduleruleMgr.getInstanceScheduler();
        JobDetail job =newJob(BackupTaskJob.class)
                .withIdentity("job_"+taskId, groupName+"1")
                .build();
        Date excuteTime = logBakTask.getExcuteTime();
        Calendar cal = Calendar.getInstance();
        cal.setTime(excuteTime);
        int year = cal.get(Calendar.YEAR);//获取年份
        int month=cal.get(Calendar.MONTH)+1;//获取月份
        int day=cal.get(Calendar.DATE);//获取日
        int hour=cal.get(Calendar.HOUR_OF_DAY);//小时
        int minute=cal.get(Calendar.MINUTE);//分
        int second=cal.get(Calendar.SECOND);//秒
        String expression = second+" "+minute+" "+hour+" "+day+" "+month+" ? "+year+"-"+year;
        CronTrigger trigger = null;
        trigger = newTrigger().withIdentity("job_"+taskId, groupName+"3").withSchedule(cronSchedule(expression).withMisfireHandlingInstructionDoNothing())
                .build();
        job.getJobDataMap().put("dataBackupTaskService",dataBackupTaskService);
        job.getJobDataMap().put("taskId",taskId);
        try {
            sched.scheduleJob(job, trigger);
            sched.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
            log.error("An error occurred", e);
        }
    }

    public void modifyTaskJob(Integer taksId){
        deleteTaskJob(taksId);
        dataBackupTaskService.deleteTaskJob(taksId);
        addTaskJob(taksId);
    }

    public void deleteTaskJob(Integer taksId){
        Scheduler sched = QuartzscheduleruleMgr.getInstanceScheduler();
        try{
            TriggerKey triggerKey3 = TriggerKey.triggerKey("job_"+taksId, groupName+"3");
            sched.pauseTrigger(triggerKey3);// 停止触发器
            sched.unscheduleJob(triggerKey3);// 移除触发器
            sched.deleteJob(JobKey.jobKey("job_"+taksId, groupName+"1"));// 删除任务
            dataBackupTaskService.deleteTaskJob(taksId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    public void initJobs() {
        QueryWrapper<BaseDataBackupTask> queryWrapper = new QueryWrapper();
        List<BaseDataBackupTask> logBakTasks = logBakTaskService.list(queryWrapper);
        for(BaseDataBackupTask logBakTask : logBakTasks){
            addTaskJob(logBakTask.getId());
        }
    }
}
