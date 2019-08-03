package com.movie.moviesite;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.movie.moviesite.mapper")
@SpringBootApplication
public class MoviesiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoviesiteApplication.class, args);
	}

}