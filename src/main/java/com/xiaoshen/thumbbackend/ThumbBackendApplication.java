package com.xiaoshen.thumbbackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan("com.xiaoshen.thumbbackend.mapper")
@SpringBootApplication
public class ThumbBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThumbBackendApplication.class, args);
	}

}
