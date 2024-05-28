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
public class DeciderJobConfiguration {

    private static final String DECIDER_JOB = "deciderJob";
    private static final String FIRST_STEP = "firstStep";
    private static final String ODD_STEP = "OddStep";
    private static final String EVEN_STEP = "EvenStep";
    private static final String OTHER_CONDITIONS = "*";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final OddDecider oddDecider;

    @Bean
    public Job deciderJob() {
        log.error(">>>>>>>>> decider job={} init", DECIDER_JOB);

        return jobBuilderFactory.get(DECIDER_JOB)
            // step 1의 실패
            .start(firstStep())
            .next(oddDecider)

            .from(oddDecider)
                .on(OddDecider.ODD)
                .to(oddStep())
            .from(oddDecider)
                .on(OddDecider.EVEN)
                .to(evenStep())
            .end()
            .build();
    }

    @Bean
    public Step firstStep() {
        return stepBuilderFactory.get(FIRST_STEP)
            .tasklet((contribution, chunkContext) -> {

//                contribution.setExitStatus(ExitStatus.FAILED);
                System.out.println(contribution.getExitStatus());
                log.error(">>>>>>>>>>> 스텝 ={} setExitStatus={}", FIRST_STEP, ExitStatus.FAILED);
                log.error(">>>>>>>>>>> 스텝 ={}", FIRST_STEP);
                return RepeatStatus.FINISHED;
            })
            .build();
    }

    @Bean
    public Step oddStep() {
        return stepBuilderFactory.get(ODD_STEP)
            .tasklet((contribution, chunkContext) -> {
                log.error(">>>>>>>>>>> 스텝 ={}", ODD_STEP);
                ;
                return RepeatStatus.FINISHED;
            })
            .build();
    }

    @Bean
    public Step evenStep() {
        return stepBuilderFactory.get(EVEN_STEP)
            .tasklet((contribution, chunkContext) -> {
                log.error(">>>>>>>>>>> 스텝 ={}", EVEN_STEP);
                return RepeatStatus.FINISHED;
            })
            .build();
    }
}
