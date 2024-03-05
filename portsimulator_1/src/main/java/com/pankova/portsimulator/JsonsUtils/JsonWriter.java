package com.pankova.portsimulator.JsonsUtils;

import com.pankova.portsimulator.Service1.ShipSchedule;
import com.pankova.portsimulator.Service3.SimulationReport;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;


public class JsonWriter {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void writeToJson(ShipSchedule schedule, File file) {
        try {
            objectMapper.writeValue(file, schedule);
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeToJson(SimulationReport simulationReport, File file) {
        try {
            objectMapper.writeValue(file, simulationReport);
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }
}
