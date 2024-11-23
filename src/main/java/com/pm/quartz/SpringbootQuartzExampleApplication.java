package com.pm.quartz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SpringbootQuartzExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootQuartzExampleApplication.class, args);
	}

}
