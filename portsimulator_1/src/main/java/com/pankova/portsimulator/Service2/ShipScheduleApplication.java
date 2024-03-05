package com.pankova.portsimulator.Service2;

import com.pankova.portsimulator.JsonsUtils.JsonWriter;
import com.pankova.portsimulator.Service1.ShipSchedule;
import com.pankova.portsimulator.Service3.PortSimulation;
import com.pankova.portsimulator.Service3.SimulationReport;

import java.io.File;
import java.text.ParseException;
import java.util.Scanner;


public class ShipScheduleApplication {
    static final File SCHEDULE_JSON_FILE = new File("src/main/resources/Jsons/schedule.json");
    static final File REPORT_JSON_FILE = new File("src/main/resources/Jsons/simulationReport.json");

    public static void main(String[] args) {
        ShipSchedule schedule = new ShipSchedule();

        schedule.generateSchedule(1000);

        System.out.println("Do you want to add ship manually?\nEnter \"y\" if yes.");
        Scanner in = new Scanner(System.in);
        if ("y".equals(in.nextLine())) {
            try {
                schedule.inputShipSchedule();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        in.close();

        JsonWriter.writeToJson(schedule, SCHEDULE_JSON_FILE);
        
        PortSimulation portSimulation = new PortSimulation();
        SimulationReport simulationReport = portSimulation.simulate(SCHEDULE_JSON_FILE);

        System.out.println(simulationReport);
        JsonWriter.writeToJson(simulationReport, REPORT_JSON_FILE);

    }
}
