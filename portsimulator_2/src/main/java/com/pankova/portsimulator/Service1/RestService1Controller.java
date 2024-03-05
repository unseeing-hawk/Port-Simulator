package com.pankova.portsimulator.Service1;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;


@Validated
@RestController
@RequestMapping("/service1")
public class RestService1Controller {

    @GetMapping
    public ShipSchedule getShipSchedule(@RequestParam(name="shipsCont", defaultValue="1000")
                                        @Valid
                                        @Min(10)
                                        int shipsCount) {
        ShipSchedule schedule = new ShipSchedule();
        schedule.generateSchedule(shipsCount);
        
        return schedule;
    }

}
