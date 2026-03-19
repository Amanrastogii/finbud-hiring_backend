package com.financebuddha.finbud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.financebuddha.finbud")
public class FinbudHiringApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinbudHiringApplication.class, args);
	}
}