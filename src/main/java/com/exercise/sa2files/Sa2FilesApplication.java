package com.exercise.sa2files;

import com.exercise.sa2files.application.IFileStorage;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Sa2FilesApplication {

	public static void main(String[] args) {
		SpringApplication.run(Sa2FilesApplication.class, args);
	}

	@Bean
	CommandLineRunner init(IFileStorage storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}

}
