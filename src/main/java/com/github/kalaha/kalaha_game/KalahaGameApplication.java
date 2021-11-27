package com.github.kalaha.kalaha_game;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class KalahaGameApplication {

	@Autowired Environment env;
	public static void main(String[] args) {
		SpringApplication.run(KalahaGameApplication.class, args);
	}
}
