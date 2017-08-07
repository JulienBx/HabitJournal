package com.julienbx.habitjournal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.julienbx.habitjournal.configuration.JpaConfiguration;


@Import(JpaConfiguration.class)
@SpringBootApplication(scanBasePackages={"com.julienbx.habitjournal"})// same as @Configuration @EnableAutoConfiguration @ComponentScan
public class SpringBootHabitJournalApp {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootHabitJournalApp.class, args);
	}
}
