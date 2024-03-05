package com.pankova.portsimulator.Service3;

import java.io.File;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Service3Application {

	public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();

        String scheduleJsonFilePath = restTemplate.getForObject("http://localhost:8081/service2/generate-schedule", String.class);
		File scheduleJsonFile = new File(scheduleJsonFilePath);
		
		PortSimulation portSimulation = new PortSimulation();
        SimulationReport simulationReport = portSimulation.simulate(scheduleJsonFile);

		ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8081/service2", simulationReport, String.class);

		System.out.println("Response status: " + response.getStatusCode() +
							"\nSimulation report located at: " + response.getBody());
	}

}
