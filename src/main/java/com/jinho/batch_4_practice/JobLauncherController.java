package com.jinho.batch_4_practice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class JobLauncherController {

    private static final String FILE_NAME = "fileName";
    private static final String JOB_NAME_PATH = "input.file.name";
    private static final String TIME = "time";
    private static final String DONE = "DONE";

    private final JobLauncher jobLauncher;
    private final Job job;

    @GetMapping("/launchjob")
    public String handle(@RequestParam(FILE_NAME) String fileName) {
        JobParameters jobParameters = getJobParameters(fileName);
        try {
            jobLauncher.run(job, jobParameters);
        } catch (Exception e) {
            log.error("[ERROR] : {}", e.getMessage());
        }
        return DONE;
    }

    private JobParameters getJobParameters(String fileName) {
        return new JobParametersBuilder()
            .addString(JOB_NAME_PATH, fileName)
            .addLong(TIME, System.currentTimeMillis())
            .toJobParameters();
    }
}
