package com.vates.provincia.seguros.techchallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.vates.provincia.seguros.techchallenge")
@EnableFeignClients
public class TechchallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechchallengeApplication.class, args);
	}

}
