package com.pankova.portsimulator.Service3;

import java.util.Calendar;
import java.util.Comparator;

import com.pankova.portsimulator.Service1.CargoType;
import com.pankova.portsimulator.Utils.TimeUtils;


public class UnloadShipReport {
    private String shipName;
    private CargoType cargoType;
    private Calendar arrivalDate;
    private Calendar unloadingStart;
    private long waitingTimeInQueue;
    private long unloadingTime;
    private long unloadDelay;

    public static Comparator<UnloadShipReport> unloadReportComparator = Comparator.comparing(UnloadShipReport::getArrivalDate);

    
    public UnloadShipReport(String shipName, CargoType cargoType, Calendar arrivalDate, Calendar unloadingStart,
                            long waitingTimeInQueue, long unloadingTime, long unloadDelay) {
        this.shipName = shipName;
        this.cargoType = cargoType;
        this.arrivalDate = arrivalDate;
        this.unloadingStart = unloadingStart;
        this.waitingTimeInQueue = waitingTimeInQueue;
        this.unloadingTime = unloadingTime;
        this.unloadDelay = unloadDelay;
    }

    @Override
    public String toString() {
        String waiting = TimeUtils.formatTime(TimeUtils.convertMillisToDaysHoursMinutes(waitingTimeInQueue));
        String unloading = TimeUtils.formatTime(TimeUtils.convertMillisToDaysHoursMinutes(unloadingTime));
        String delay = TimeUtils.formatTime(TimeUtils.convertMillisToDaysHoursMinutes(unloadDelay));

        return "UnloadReport{\n" +
                "shipName = '" + shipName +
                "',\narrivalDate = " + arrivalDate.getTime() +
                ",\nwaitingTimeInQueue = " + waiting +
                ",\nunloadingStart = " + unloadingStart.getTime() +
                ",\nunloadingTime = " + unloading +
                ",\nunloadDelay = " + delay +
                "}\n";
    }

    public String getShipName() {
        return shipName;
    }

    public CargoType getCargoType() {
        return cargoType;
    }

    public Calendar getArrivalDate() {
        return arrivalDate;
    }

    public Calendar getUnloadingStart() {
        return unloadingStart;
    }

    public long getWaitingTimeInQueue() {
        return waitingTimeInQueue;
    }

    public long getUnloadingTime() {
        return unloadingTime;
    }

    public long getUnloadDelay() {
        return unloadDelay;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public void setCargoType(CargoType cargoType) {
        this.cargoType = cargoType;
    }

    public void setArrivalDate(Calendar arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public void setUnloadingStart(Calendar unloadingStart) {
        this.unloadingStart = unloadingStart;
    }

    public void setWaitingTimeInQueue(long waitingTimeInQueue) {
        this.waitingTimeInQueue = waitingTimeInQueue;
    }

    public void setUnloadingTime(long unloadingTime) {
        this.unloadingTime = unloadingTime;
    }

    public void setUnloadDelay(long unloadDelay) {
        this.unloadDelay = unloadDelay;
    }

}
