package com.batch.service;

import lombok.SneakyThrows;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class CronJobService {


    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;


    @SneakyThrows
    @Scheduled(fixedRate = 15000)
    @Async
    public void startBatch() {

        JobParameters jobParameter = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis()).toJobParameters();

        try {
			jobLauncher.run(job, jobParameter);
		} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
				| JobParametersInvalidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        System.out.println("batch started...");
    }
}


