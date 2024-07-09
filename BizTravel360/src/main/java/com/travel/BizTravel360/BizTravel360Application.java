package com.travel.BizTravel360;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class BizTravel360Application {

	public static void main(String[] args) {
		SpringApplication.run(BizTravel360Application.class, args);
	}

}
