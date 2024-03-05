package com.pankova.portsimulator.Service3;

import com.pankova.portsimulator.JsonsUtils.JsonReader;
import com.pankova.portsimulator.Service1.CargoType;
import com.pankova.portsimulator.Service1.Ship;
import com.pankova.portsimulator.Service1.ShipSchedule;
import com.pankova.portsimulator.Utils.TimeUtils;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;


public class PortSimulation {
    private final ConcurrentLinkedQueue<Ship> dryShipsQueue = new ConcurrentLinkedQueue<>();
    private final ConcurrentLinkedQueue<Ship> liquidShipsQueue = new ConcurrentLinkedQueue<>();
    private final ConcurrentLinkedQueue<Ship> containerShipsQueue = new ConcurrentLinkedQueue<>();
    
    private final int FINE_PER_HOUR = 100;

    private ConcurrentLinkedQueue<UnloadShipReport> unloadShipReports = new ConcurrentLinkedQueue<>();
    private Random random = new Random();

    private CyclicBarrier barrier;
    private volatile boolean isDone = false;

    public SimulationReport simulate(final File SCHEDULE_JSON_FILE) {
        Map<CargoType, Integer> cranesCount = new HashMap<>();
        cranesCount.put(CargoType.DRY, 1);
        cranesCount.put(CargoType.LIQUID, 1);
        cranesCount.put(CargoType.CONTAINER, 1);

        boolean isMinimumFines = true;
        Map<CargoType, Long> fines = new HashMap<>();

        do {
            isMinimumFines = true;
            fines = tryWithSettedCranes(JsonReader.readFromJson(SCHEDULE_JSON_FILE), 
                                        cranesCount.get(CargoType.DRY),
                                        cranesCount.get(CargoType.LIQUID),
                                        cranesCount.get(CargoType.CONTAINER));
            for (CargoType ct : fines.keySet()) {
                if(fines.get(ct) >= Crane.CRANE_PRICE) {
                    // System.out.println("type = " + ct + "; fine = " + fines.get(ct));
                    cranesCount.merge(ct, 1, Integer::sum);
                    isMinimumFines = false;
                }
            }
            //if ("e".equals(new Scanner(System.in).nextLine())) System.exit(0);

        } while (!isMinimumFines);

        long finesSum = fines.values().stream()
                         .mapToLong(Long::longValue)
                         .sum();

        return new SimulationReport(new LinkedList<>(unloadShipReports), finesSum, cranesCount);
    }

    public Map<CargoType, Long> tryWithSettedCranes(ShipSchedule schedule, int dryCraneCount, int liquidCraneCount, int containerCraneCount) {
        createDelaysInSchedule(schedule);
        unloadShipReports = new ConcurrentLinkedQueue<>();

        for (Ship s : schedule.getSchedule()) {
            switch (s.getCargoType()) {
                case DRY -> dryShipsQueue.add(s);
                case LIQUID -> liquidShipsQueue.add(s);
                case CONTAINER -> containerShipsQueue.add(s);
            }
        }

        isDone = false;
        int sumCrane = dryCraneCount + liquidCraneCount + containerCraneCount;
        barrier = new CyclicBarrier(sumCrane,
                                    () -> {
                                        System.out.println("Finished unloading");
                                        isDone = true;
                                    });

        ExecutorService craneService = Executors.newFixedThreadPool(sumCrane);
        execute(craneService, dryShipsQueue, dryCraneCount);
        execute(craneService, liquidShipsQueue, liquidCraneCount);
        execute(craneService, containerShipsQueue, containerCraneCount);

        while (!isDone) {}
        
        craneService.shutdown();

        Map<CargoType, Long> fines = unloadShipReports.stream()
                                    .collect(Collectors.groupingBy(UnloadShipReport::getCargoType,
                                             Collectors.summingLong(u -> {
                                                long waitTime = u.getWaitingTimeInQueue();
                                                long unloadDelay = u.getUnloadDelay();
                                                return (waitTime + unloadDelay) / TimeUtils.MS_PER_HOUR * FINE_PER_HOUR;
                                            })));

        return fines;
    }

    private void createDelaysInSchedule(ShipSchedule schedule) {
        List<Ship> scheduleList = new LinkedList<>(schedule.getSchedule());

        for (Ship s : scheduleList) {
            // рандомно выбираем корабли, которые прибудут с задержкой
            if (random.nextBoolean()) continue;

            int oneWeekInMinuts = 10080;
            int delay = random.nextInt(oneWeekInMinuts * 2);
            s.getArrivalDate().add(Calendar.MINUTE, delay - oneWeekInMinuts);

            // если вышли за границы одного месяца, то убираем
            if (s.getArrivalDate().get(Calendar.MONTH) != 6) {
                schedule.getSchedule().remove(s);
                continue;
            }
        }

        schedule.getSchedule().sort(Ship.shipDateComparator);
    }

    private void execute(ExecutorService service, ConcurrentLinkedQueue<Ship> queue, int craneCount) {
        for (int i = 0; i < craneCount; i++) {
            service.execute(new Crane(queue, unloadShipReports, craneCount, barrier));
        }
    }
}
