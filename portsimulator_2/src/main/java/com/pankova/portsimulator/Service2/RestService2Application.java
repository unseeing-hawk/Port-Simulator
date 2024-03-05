package com.pankova.portsimulator.Service2;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestService2Application {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(RestService2Application.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8081")); 
        app.run(args);
	}

}
