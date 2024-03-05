package com.pankova.portsimulator.Service2;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.pankova.portsimulator.JsonsUtils.JsonReader;
import com.pankova.portsimulator.JsonsUtils.JsonWriter;
import com.pankova.portsimulator.Service1.ShipSchedule;
import com.pankova.portsimulator.Service3.SimulationReport;

import java.io.File;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


@Validated
@RestController
@RequestMapping("/service2")
public class RestService2Controller {

    @GetMapping("/generate-schedule")
    public String getSchedule(@RequestParam(name="shipsCount", defaultValue="1000")
                                @Valid
                                @Min(10)
                                int shipsCount) {

        RestTemplate restTemplate = new RestTemplate();
        String service1Url = "http://localhost:8080/service1?shipsCount=" + String.valueOf(shipsCount);

        ShipSchedule shipSchedule = restTemplate.getForObject(service1Url, ShipSchedule.class);
        
        final File SCHEDULE_JSON_FILE = new File(System.getProperty("user.dir") + File.separator + "/schedule.json");
        JsonWriter.writeToJson(shipSchedule, SCHEDULE_JSON_FILE); 

        return SCHEDULE_JSON_FILE.toPath().toString(); //return json name or json full?
    }

    @GetMapping("/read-schedule-json")
    public ShipSchedule getScheduleFromJson(@RequestParam(name="filePath")
                                      @NotBlank
                                      @NotNull
                                      @Valid
                                      String filePath) {
        File file = new File(filePath);

        if (!file.exists()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Json ship-schedule file with this name is absent.");
        } 

        ShipSchedule shipSchedule = JsonReader.readFromJson(file);

        return shipSchedule;
    }

    @PostMapping
    public String createSimulationReport(@RequestBody
                                            @NotNull
                                            @Valid
                                            SimulationReport simulationReport) {
        final File REPORT_JSON_FILE = new File(System.getProperty("user.dir") + File.separator + "/simulationReport.json");
        JsonWriter.writeToJson(simulationReport, REPORT_JSON_FILE);

        return REPORT_JSON_FILE.getAbsolutePath();
    }
}
