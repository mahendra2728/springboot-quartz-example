package com.pm.quartz.rest;


import com.pm.quartz.job.RunMyJob;
import com.pm.quartz.request.JobRequest;
import com.pm.quartz.response.JobResponse;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class JobSchedulerRestController {

    private static final Logger logger = LoggerFactory.getLogger(JobSchedulerRestController.class);

    @Autowired
    private Scheduler scheduler;

    @PostMapping("/schedule")
    public ResponseEntity<JobResponse> createJobScheduler(@RequestBody JobRequest jobRequest)
        throws SchedulerException {
        ZonedDateTime dateTime = ZonedDateTime.of(jobRequest.getDateTime(),jobRequest.getTimeZone());
//        if(dateTime.isBefore(ZonedDateTime.now())) {
//            JobResponse response = new JobResponse("dateTime must be after current time","FAILED");
//            return ResponseEntity.badRequest().body(response);
//        }
        jobRequest.setJobId(UUID.randomUUID().toString());
        JobDetail jobDetail =  buildJobDetail(jobRequest);
        Trigger jobTrigger = buildJobTrigger(jobDetail,jobRequest.getCronExpression());
        Date date = scheduler.scheduleJob(jobDetail,jobTrigger);
        if(Objects.nonNull(date)){
            JobResponse response = new JobResponse("Job Scheduled Successfully...","SUCCESS");
            return ResponseEntity.badRequest().body(response);
        }
        JobResponse response = new JobResponse("something wrong happened","FAILED");
        return ResponseEntity.badRequest().body(response);
    }

    private JobDetail buildJobDetail(JobRequest jobRequest) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("jobId",jobRequest.getJobId());
        jobDataMap.put("jobName",jobRequest.getJobName());

       return JobBuilder.newJob(RunMyJob.class)
            .withIdentity(jobRequest.getJobId(),"testGroup")
            .withDescription(jobRequest.getJobDescription())
            .storeDurably()
            .usingJobData(jobDataMap).build();
    }

    private Trigger buildJobTrigger(JobDetail jobDetail,String cronExpression) {
        return TriggerBuilder.newTrigger()
            .forJob(jobDetail)
            .withIdentity(jobDetail.getKey().getName(), "event-trigger")
            .withDescription(jobDetail.getDescription())
            .startAt(Date.from(Instant.now()))
           // .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
            .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
            .build();
    }

}
