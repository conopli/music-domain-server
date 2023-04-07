package com.sideproject.conopli;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@PropertySource("classpath:/env.yml")
public class ConopliApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConopliApplication.class, args);
	}

}
