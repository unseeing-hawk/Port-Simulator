package com.pankova.portsimulator.Service3;

import com.pankova.portsimulator.Service1.CargoType;
import com.pankova.portsimulator.Service1.Ship;
import com.pankova.portsimulator.Utils.TimeUtils;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.concurrent.*;


public class Crane implements Runnable {
    public static final int DRY_PER_HOUR = 30000;
    public static final int LIQUID_PER_HOUR = 50000;
    public static final int CONTAINER_PER_HOUR = 2500;
    public static final int CRANE_PRICE = 30000;

    private ConcurrentLinkedQueue<Ship> ships;
    private ConcurrentLinkedQueue<UnloadShipReport> unloadShipReports;

    private Calendar actualTime;
    private int cransCount;

    private final CyclicBarrier barrier;

    public Crane(
                 ConcurrentLinkedQueue<Ship> ships,
                 ConcurrentLinkedQueue<UnloadShipReport> unloadShipReports,
                 int cransCount,
                 CyclicBarrier barrier) {
        this.ships = ships;
        this.unloadShipReports = unloadShipReports;
        this.actualTime = new GregorianCalendar();
        this.actualTime.setTimeInMillis(0);
        this.cransCount = cransCount;
        this.barrier = barrier;
    }

    @Override
    public void run() {

        while (!ships.isEmpty()) {
            Ship ship = ships.poll();
            if (ship != null) {

                Calendar unloadingStart = actualTime.after(ship.getArrivalDate()) ? actualTime : ship.getArrivalDate();
                long waitingTime = unloadingStart.getTimeInMillis() - ship.getArrivalDate().getTimeInMillis();
            
                long unloadDelay = 0;
                if (new Random().nextBoolean()) {
                    double lambda = 1.0 / 1440.0; 
                    double uniformRandom = new Random().nextDouble();
                    unloadDelay = (long)(-Math.log(1.0 - uniformRandom) / lambda * 50);
                }
                long unloadingTime = ship.getUnloadingTimeInHours() * TimeUtils.MS_PER_HOUR + unloadDelay;

                if (tryAddCraneToUnload(unloadingTime, unloadingStart.getTimeInMillis(), ship.getCargoType())) {
                    unloadDelay /= 2;
                    unloadingTime /= 2;
                }

                unloadShipReports.add(new UnloadShipReport(
                        ship.getName(),
                        ship.getCargoType(),
                        ship.getArrivalDate(),
                        unloadingStart,
                        waitingTime,
                        unloadingTime,
                        unloadDelay
                ));

                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                actualTime.setTimeInMillis(unloadingStart.getTimeInMillis() + unloadingTime);
            }
        }

        try {
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    private boolean tryAddCraneToUnload(long unloadingTime, long unloadingStart, CargoType cargoType) {
        if (cransCount <= 1) return false;
        
        boolean isOneFreeCran = false;
        UnloadShipReport working = null;
        Ship nextShip = ships.peek();
        long nextShipArrivalDate = nextShip == null ? 0 : nextShip.getArrivalDate().getTimeInMillis();

        for (UnloadShipReport unload : unloadShipReports) {
            if (unload.getCargoType() != cargoType) continue;
            working = unload;
            if (unload.getUnloadingStart().getTimeInMillis() + unload.getUnloadingTime() < unloadingStart && 
                (nextShipArrivalDate == 0 || (unloadingStart + unloadingTime / 2) < nextShipArrivalDate)) {
                    isOneFreeCran = true;
                    break;
                }
        }

        if (!isOneFreeCran && working != null) return false; // cannot add more cranes
        
        if (working == null) {
            if (nextShipArrivalDate == 0 || (unloadingStart + unloadingTime / 2) < nextShipArrivalDate) {
                return true;
            }
        }
        
        if (isOneFreeCran) return true;

        return false;
    }
}
