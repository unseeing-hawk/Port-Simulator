package com.pankova.portsimulator.Service1;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class RestService1Application {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(RestService1Application.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8080")); 
        app.run(args);
	}

}
