package com.wxh.swing;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.wxh.swing.dao")
public class SwingWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SwingWebApplication.class, args);
	}

}
