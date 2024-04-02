package com.figure.system.job;

import com.figure.system.service.DeleteBackupedDataService;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class DeleteBackupedDataJob implements Job {

    @Override
    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        JobDataMap data = context.getJobDetail().getJobDataMap();
        DeleteBackupedDataService deleteBackupedDataService = (DeleteBackupedDataService) data.get("deleteBackupedDataService");
        Integer backupId = (Integer) data.get("backupId");
        String cmd = (String) data.get("cmd");
        if("backup".equals(cmd)){
            deleteBackupedDataService.deleteBackup(backupId);
        }else{
            deleteBackupedDataService.deleteHistoryData(backupId);
        }
    }
}
