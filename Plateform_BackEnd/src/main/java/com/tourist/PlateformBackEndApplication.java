package com.tourist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
//@EntityScan(basePackages = {"com.tourist.model", "com.tourist.cloudinary.model"})
public class PlateformBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlateformBackEndApplication.class, args);
	}

}
