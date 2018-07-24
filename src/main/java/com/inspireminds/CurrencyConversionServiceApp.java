package com.inspireminds;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@EnableAutoConfiguration
//@ComponentScan("com.inspireminds.controller")
public class CurrencyConversionServiceApp {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyConversionServiceApp.class, args);
	}
	

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext context) {

		return args -> {

			String[] beans = context.getBeanDefinitionNames();
			Arrays.sort(beans);
			for (String bean : beans) {
				System.out.println(bean);
			}

		};

	}
	
}
