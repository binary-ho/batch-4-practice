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
public class SimpleJobConfiguration {

    private static final String JOB = "job";
    private static final String STEP1 = "step";
    private static final String DATE_STEP = "dateStep";
    private static final String FAIL_STEP = "failStep";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    /* MEMO : Job은 하나의 배치 작업 단위이고, 여러 Step을 가지고 있다. */
    @Bean
    public Job job() {
        log.error(">>>>>>>>> job={} init", JOB);
        return jobBuilderFactory.get(JOB)
            .start(step())
            .build();
    }

    /* MEMO : Step 안에는 여러 Tasklet, Reader & Processor & Writer 묶음이 존재한다. */
    @Bean
    public Step step() {
        return stepBuilderFactory.get(STEP1)
            .tasklet((contribution, chunkContext) -> {
                log.error(">>>>>>>>>>> 스텝 1");
                return RepeatStatus.FINISHED;
            })
            .build();
    }


    /* MEMO : 동일한 job이더라도,
        파라미터 값이 달라질 떄마다 BATCH_JOB_INSTANCE에 새로 레코드가 저장되고,
        동일한 파라미터로는 또 실행할 수 없다. */
    /* MEMO : JOB_INSTANCE 테이블을 바탕으로 자식 테이블인 JOB_EXECUTION 테이블이 만들어지고, 성공/실패 결과 등이 저장된다.
    *   이 EXECUTION은 시행에 관한 테이블이므로, 여기엔 한 인스턴스 별로 여러 데이터가 쌓일 수 있다. */
    @Bean
    @JobScope
    public Step dateStep(@Value("#{jobParameters[requestDate]}") String requestDate) {
        return stepBuilderFactory.get(STEP1)
            .tasklet((contribution, chunkContext) -> {
                log.error(">>>>>>>>>>> requestDate = {}", requestDate);
                return RepeatStatus.FINISHED;
            })
            .build();
    }

    /* MEMO : STATUS가 FAILED로 나타난다. */
    @Bean
    public Step failStep() {
        return stepBuilderFactory.get(STEP1)
            .tasklet((contribution, chunkContext) -> {
                throw new IllegalArgumentException("실패하는 스텝");
            })
            .build();
    }
}
