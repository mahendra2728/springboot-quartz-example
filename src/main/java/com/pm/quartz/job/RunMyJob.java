package com.pm.quartz.job;


import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
public class RunMyJob extends QuartzJobBean {

    private static final Logger logger = LoggerFactory.getLogger(RunMyJob.class);

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        logger.info("Executing Job with key {}", context.getJobDetail().getKey());
        JobDataMap jobDataMap = context.getMergedJobDataMap();
        String jobId = jobDataMap.getString("jobId");
        String jobName = jobDataMap.getString("jobName");
        logger.info(" JobId is {} and JobName is {}",jobId,jobName);
    }
}
