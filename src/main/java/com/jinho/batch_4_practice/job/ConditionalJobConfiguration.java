package com.jinho.batch_4_practice.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class ConditionalJobConfiguration {

    private static final String CONDITIONAL_JOB = "conditionalJob";
    private static final String STEP1 = "conditionalJobStep1";
    private static final String STEP2 = "conditionalJobStep2";
    private static final String STEP3 = "conditionalJobStep3";
    private static final String OTHER_CONDITIONS = "*";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job conditionalJob() {
        log.error(">>>>>>>>> job={} init", CONDITIONAL_JOB);

        return jobBuilderFactory.get(CONDITIONAL_JOB)
            // step 1의 실패
            .start(conditionalJobStep1())
                .on(ExitStatus.FAILED.getExitCode())
                .to(conditionalJobStep3())

                .on(OTHER_CONDITIONS)   // step3의 결과와 관계없이 발동
                .end()
            
            // step 1의 성공
            .from(conditionalJobStep1())
                .on(OTHER_CONDITIONS)   // step1이 FAILED가 아닌 경우
                .to(conditionalJobStep2())
                .next(conditionalJobStep3())

                .on(OTHER_CONDITIONS)
                .end()
            .end()
            .build();
    }

    @Bean
    public Step conditionalJobStep1() {
        return stepBuilderFactory.get(STEP1)
            .tasklet((contribution, chunkContext) -> {

//                contribution.setExitStatus(ExitStatus.FAILED);
                System.out.println(contribution.getExitStatus());
                log.error(">>>>>>>>>>> 스텝 ={} setExitStatus={}", STEP1, ExitStatus.FAILED);
                log.error(">>>>>>>>>>> 스텝 ={}", STEP1);
                return RepeatStatus.FINISHED;
            })
            .build();
    }

    @Bean
    public Step conditionalJobStep2() {
        return stepBuilderFactory.get(STEP2)
            .tasklet((contribution, chunkContext) -> {
                log.error(">>>>>>>>>>> 스텝 ={}", STEP2);;
                return RepeatStatus.FINISHED;
            })
            .build();
    }

    @Bean
    public Step conditionalJobStep3() {
        return stepBuilderFactory.get(STEP3)
            .tasklet((contribution, chunkContext) -> {
                log.error(">>>>>>>>>>> 스텝 ={}", STEP3);
                return RepeatStatus.FINISHED;
            })
            .build();
    }
}
