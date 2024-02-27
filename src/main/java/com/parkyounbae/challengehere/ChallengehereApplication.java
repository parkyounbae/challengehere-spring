package com.parkyounbae.challengehere;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ChallengehereApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChallengehereApplication.class, args);
	}

}
