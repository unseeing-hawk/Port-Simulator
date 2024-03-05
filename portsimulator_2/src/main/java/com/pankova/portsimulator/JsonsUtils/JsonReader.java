package com.pankova.portsimulator.JsonsUtils;

import com.pankova.portsimulator.Service1.ShipSchedule;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;


public class JsonReader {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static ShipSchedule readFromJson(File file) {
        ShipSchedule schedule = new ShipSchedule();
        try {
            schedule = objectMapper.readValue(file, ShipSchedule.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return schedule;
    }
}
