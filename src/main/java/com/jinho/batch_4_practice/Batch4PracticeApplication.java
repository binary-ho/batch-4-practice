package com.jinho.batch_4_practice;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/* MEMO : 배치 활성화 어노테이션 */
@EnableBatchProcessing
@SpringBootApplication
public class Batch4PracticeApplication {

	public static void main(String[] args) {
		SpringApplication.run(Batch4PracticeApplication.class, args);
	}
}
