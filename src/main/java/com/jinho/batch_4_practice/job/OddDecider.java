package com.jinho.batch_4_practice.job;

import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class OddDecider implements JobExecutionDecider {

    public static final String EVEN = "EVEN";
    public static final String ODD = "ODD";

    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        Random random = new Random();
        int randomNumber = random.nextInt(50) + 1 % 2;
        if (randomNumber % 2 == 0) {
            return new FlowExecutionStatus(EVEN);
        }
        return new FlowExecutionStatus(ODD);
    }
}
