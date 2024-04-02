package com.figure.system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.figure.core.helper.DateHelper;
import com.figure.core.helper.QuartzscheduleruleMgr;
import com.figure.core.model.base.BaseDataBackup;
import com.figure.core.model.base.BaseTableBackup;
import com.figure.core.model.sys.SysPara;
import com.figure.core.repository.others.CommonMapper;
import com.figure.core.service.base.IBaseDataBackupService;
import com.figure.core.service.base.IBaseTableBackupService;
import com.figure.core.service.sys.ISysParaService;
import com.figure.system.job.DeleteBackupedDataJob;
import lombok.Data;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

@Service
@Data
public class DeleteBackupedDataService {

    private String groupName = "DeleteBackupedData";

    @Autowired
    IBaseDataBackupService baseDataBackupService;

    @Autowired
    IBaseTableBackupService baseTableBackupService;

    @Autowired
    ISysParaService sysParaService;

    @Autowired
    CommonMapper commonMapper;

    public void addTaskJob(Integer backupId) {
        Date now = new Date();
        BaseDataBackup baseDataBackup =  baseDataBackupService.getById(backupId);

        if(baseDataBackup.getExcuteStatus()==null||baseDataBackup.getExcuteStatus()==2||baseDataBackup.getExcuteStatus()==0){
             return;
        }
        Date finishTime = baseDataBackup.getFinishTime();
        Date deleteBackup_time = DateHelper.addDay(finishTime,baseDataBackup.getBackRetentionTime());
        Date deleteHistoryData_time = DateHelper.addDay(finishTime,baseDataBackup.getDataRetentionTime());
        if(deleteBackup_time.before(now)){
              deleteBackup(backupId);
        }else{
             initDeleteJobs(deleteBackup_time,"backup",backupId);
        }
        if(deleteHistoryData_time.before(now)){
              deleteHistoryData(backupId);
        }else{
            initDeleteJobs(deleteBackup_time,"hisitoryData",backupId);
        }
    }

    private void initDeleteJobs(Date excuteTime,String cmd,Integer backupId){
        Scheduler sched = QuartzscheduleruleMgr.getInstanceScheduler();
        JobDetail job =newJob(DeleteBackupedDataJob.class)
                .withIdentity("job_"+backupId, groupName+"1")
                .build();
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
        trigger = newTrigger().withIdentity("job_"+backupId, groupName+"3").withSchedule(cronSchedule(expression).withMisfireHandlingInstructionDoNothing())
                .build();
        job.getJobDataMap().put("deleteBackupedDataService",this);
        job.getJobDataMap().put("backupId",backupId);
        job.getJobDataMap().put("cmd",cmd);
        try {
            sched.scheduleJob(job, trigger);
            sched.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public void deleteHistoryData(Integer backupId) {

        BaseDataBackup baseDataBackup =  baseDataBackupService.getById(backupId);

        QueryWrapper<SysPara> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("paraName","bakFilePath");

        List<SysPara> list =  sysParaService.list(queryWrapper);

        String filePath = list.get(0).getParaValue()+baseDataBackup.getBackupFileName();

        File file = new File(filePath);

        if(file.exists()) {
            file.delete();
        }

    }

    public void deleteBackup(Integer backupId) {

        BaseDataBackup baseDataBackup =  baseDataBackupService.getById(backupId);

        Map<String,String> params = new HashMap<String, String>();

        QueryWrapper<BaseTableBackup> baseTableBackupQueryWrapper = new QueryWrapper<>();
        baseTableBackupQueryWrapper.eq("tablename",baseDataBackup.getBackupObject());

        List<BaseTableBackup> baseTableBackups =  baseTableBackupService.list(baseTableBackupQueryWrapper);
        if(baseTableBackups==null||baseTableBackups.size()==0){
            return;
        }
        BaseTableBackup baseTableBackup = baseTableBackups.get(0);
        String condition = baseTableBackup.getConditions();
        String dataName = baseTableBackup.getDataName();
        String tableName = baseTableBackup.getTablename();

        String conditions = "'"+baseDataBackup.getStartTime()+"'<="+dataName+" AND '"+baseDataBackup.getEndTime()+"'>="+dataName+" AND "+condition;
        params.clear();
        params.put("condition", conditions);
        params.put("tableName", tableName);
        commonMapper.DeleteTableData(params);

    }

    public void initJobs() {
        QueryWrapper<BaseDataBackup> queryWrapper = new QueryWrapper();
        List<BaseDataBackup> logBakTasks = baseDataBackupService.list(queryWrapper);
        for(BaseDataBackup baseDataBackup : logBakTasks){
            addTaskJob(baseDataBackup.getId());
        }
    }

}
