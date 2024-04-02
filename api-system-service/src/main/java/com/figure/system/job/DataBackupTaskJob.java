package com.figure.system.job;

import com.figure.system.service.BackupService;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


public class DataBackupTaskJob implements Job {

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		JobDataMap data = context.getJobDetail().getJobDataMap();
		BackupService backupService = (BackupService) data.get("backupService");
		Integer taskId = (Integer) data.get("taskId");
		backupService.backup(taskId);
	}
}
