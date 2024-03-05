package com.pankova.portsimulator.Service1;

import java.util.Calendar;


public class ShipTimeTable {

    private Calendar arrivalDate;
    private Calendar arrivalDateDelay;
    private int unloadingTime;
    private int unloadingTimeDelay;

    public ShipTimeTable(Calendar arrivalDate, int unloadingTime) {
        this.arrivalDate = arrivalDate;
        this.unloadingTime = unloadingTime;

        this.arrivalDateDelay = null;
        this.unloadingTimeDelay = 0;
    }

    public Calendar getArrivalDate() {
        return arrivalDate;
    }

    public Calendar getArrivalDateDelay() {
        return arrivalDateDelay;
    }

    public int getUnloadingTime() {
        return unloadingTime;
    }

    public int getUnloadingTimeDelay() {
        return unloadingTimeDelay;
    }

    public void setArrivalDate(Calendar arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public void setArrivalDateDelay(Calendar arrivalDateDelay) {
        this.arrivalDateDelay = arrivalDateDelay;
    }

    public void setUnloadingTime(int unloadingTime) {
        this.unloadingTime = unloadingTime;
    }

    public void setUnloadingTimeDelay(int unloadingTimeDelay) {
        this.unloadingTimeDelay = unloadingTimeDelay;
    }

    
}
