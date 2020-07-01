package com.photoapp.apiaccountmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ApiAccountManagementService {

	public static void main(String[] args) {
		SpringApplication.run(ApiAccountManagementService.class, args);
	}

}
