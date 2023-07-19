package com.pbma.ngo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.pbma.ngo.entity")
//@EnableJpaRepositories
public class EntitlementApplication {

	public static void main(String[] args) {
		SpringApplication.run(EntitlementApplication.class, args);
	}

}
