package com.pankova.portsimulator.Service1;

import com.pankova.portsimulator.Service3.Crane;

import java.util.Calendar;
import java.util.Comparator;


public class Ship {
    public static final int MAX_DRY_CARGO_WEIGHT = 300000;
    public static final int MAX_LIQUID_CARGO_WEIGHT = 500000;
    public static final int MAX_CONTAINER_CARGO_WEIGHT = 25000;

    public static Comparator<Ship> shipDateComparator = Comparator.comparing(Ship::getArrivalDate);

    private Calendar arrivalDate;
    private String name = "Ship ";
    private CargoType cargoType;
    private int cargoWeight;
    private int unloadingTimeInHours;

     Ship() {}

    public Ship(Calendar arrivalDate, String name, CargoType cargoType, int cargoWeight) {
        this.arrivalDate = arrivalDate;
        this.name += name;
        this.cargoType = cargoType;
        this.cargoWeight = cargoWeight;

        this.unloadingTimeInHours = switch(cargoType) {
            case DRY -> (int) Math.ceil(cargoWeight / Crane.DRY_PER_HOUR);
            case LIQUID -> (int) Math.ceil(cargoWeight / Crane.LIQUID_PER_HOUR);
            case CONTAINER -> (int) Math.ceil(cargoWeight/ Crane.CONTAINER_PER_HOUR);
            default -> 0;
        };
    }

    @Override
    public String toString() {
        return "Ship{" +
                "date=" + arrivalDate.getTime() +
                ", Name='" + name + '\'' +
                ", Cargo Type=" + cargoType +
                ", Cargo Weight=" + cargoWeight +
                ", Unloading Time=" + unloadingTimeInHours +
                '}';
    }

    public Calendar getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Calendar arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CargoType getCargoType() {
        return cargoType;
    }

    public void setCargoType(CargoType cargoType) {
        this.cargoType = cargoType;
    }

    public int getCargoWeight() {
        return cargoWeight;
    }

    public void setCargoWeight(int cargoWeight) {
        this.cargoWeight = cargoWeight;
    }

    public int getUnloadingTimeInHours() {
        return unloadingTimeInHours;
    }

    public void setUnloadingTimeInHours(int unloadingTime) {
        this.unloadingTimeInHours = unloadingTime;
    }
}
