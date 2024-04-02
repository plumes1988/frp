package com.figure.system.job;

import com.figure.system.service.DataBackupTaskService;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


public class BackupTaskJob implements Job {

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {

		JobDataMap data = context.getJobDetail().getJobDataMap();

		DataBackupTaskService dataBackupTaskService = (DataBackupTaskService) data.get("dataBackupTaskService");
		Integer taskId = (Integer) data.get("taskId");
		dataBackupTaskService.addTaskJob(taskId);

	}
}
