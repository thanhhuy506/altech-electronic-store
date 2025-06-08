package com.altech.electronic.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class AltechElectronicStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(AltechElectronicStoreApplication.class, args);
	}

}
