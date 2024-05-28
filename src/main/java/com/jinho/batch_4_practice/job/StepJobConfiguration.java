package com.jinho.batch_4_practice.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class StepJobConfiguration {

    private static final String JOB = "stepJob";
    private static final String STEP1 = "step1";
    private static final String STEP2 = "step2";
    private static final String STEP3 = "step3";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job stepjob() {
        log.error(">>>>>>>>> job={} init", JOB);
        return jobBuilderFactory.get(JOB)
            .start(step1())
            .next(step2())
            .next(step3())
            .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get(STEP1)
            .tasklet((contribution, chunkContext) -> {
                log.error(">>>>>>>>>>> 스텝 ={}", STEP1);
                return RepeatStatus.FINISHED;
            })
            .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get(STEP2)
            .tasklet((contribution, chunkContext) -> {
                log.error(">>>>>>>>>>> 스텝 ={}", STEP2);;
                return RepeatStatus.FINISHED;
            })
            .build();
    }

    @Bean
    public Step step3() {
        return stepBuilderFactory.get(STEP3)
            .tasklet((contribution, chunkContext) -> {
                log.error(">>>>>>>>>>> 스텝 ={}", STEP2);
                return RepeatStatus.FINISHED;
            })
            .build();
    }
}
