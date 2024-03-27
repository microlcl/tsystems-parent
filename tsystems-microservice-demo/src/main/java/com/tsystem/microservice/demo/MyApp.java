package com.tsystem.microservice.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import com.tsystems.core.autoconfigure.EnableTsMicroService;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableTsMicroService
public class MyApp {
	public static void main(String[] args) {
		SpringApplication.run(MyApp.class, args);
	}
}

