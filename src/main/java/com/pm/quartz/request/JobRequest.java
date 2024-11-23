package com.pm.quartz.request;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobRequest {

    private String jobId;
    private String jobName;
    private String jobDescription;
    private String cronExpression;
    private String dataOfBirth;
    private LocalDateTime dateTime;
    private ZoneId timeZone;
}
