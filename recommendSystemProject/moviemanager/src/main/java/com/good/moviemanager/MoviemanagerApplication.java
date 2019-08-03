package com.good.moviemanager;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.good.moviemanager.mapper")
@SpringBootApplication
public class MoviemanagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoviemanagerApplication.class, args);
	}

}
