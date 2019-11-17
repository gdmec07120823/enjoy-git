package com.lgd.home;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
@MapperScans({@MapperScan("com.lgd.home.dao")})
public class HomewebApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomewebApplication.class, args);
	}

}
