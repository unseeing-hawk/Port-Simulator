package com.pankova.portsimulator.Service3;

import java.util.List;
import java.util.Map;

import com.pankova.portsimulator.Service1.CargoType;
import com.pankova.portsimulator.Utils.TimeUtils;

public class SimulationReport {
    List<UnloadShipReport> unloadShips;
    int shipsCount;
    long waitingInQueueAverage;
    long maxUnloadingDelay;
    long unloadingDelayAverage;
    long totalFine;
    Map<CargoType, Integer> cranesCount;

    public SimulationReport() {}
   
    public SimulationReport(List<UnloadShipReport> unloadShips, long totalFine, Map<CargoType, Integer> cranesCount) {
        this.unloadShips = unloadShips;

        this.shipsCount = this.unloadShips.size();
        this.waitingInQueueAverage = (long) unloadShips.stream()
                                        .mapToLong(UnloadShipReport::getWaitingTimeInQueue)
                                        .average()
                                        .orElse(0);
        this.maxUnloadingDelay = unloadShips.stream()
                                .mapToLong(UnloadShipReport::getUnloadDelay)
                                .max()
                                .orElse(0);
        this.unloadingDelayAverage = (long) unloadShips.stream()
                                        .mapToLong(UnloadShipReport::getUnloadDelay)
                                        .average()
                                        .orElse(0);

        this.totalFine = totalFine;
        this.cranesCount = cranesCount;
    }

    @Override
    public String toString() {
        String averageWait = TimeUtils.formatTime(TimeUtils.convertMillisToDaysHoursMinutes(waitingInQueueAverage));
        String maxDelay = TimeUtils.formatTime(TimeUtils.convertMillisToDaysHoursMinutes(maxUnloadingDelay));
        String averageDelay = TimeUtils.formatTime(TimeUtils.convertMillisToDaysHoursMinutes(unloadingDelayAverage));

        return "SimulationReport{\n" + 
                "unloadShips='" + unloadShips + 
                "'\nshipsCount=" + shipsCount + 
                "'\nwaitingInQueueAverage=" + averageWait + 
                "'\nmaxUnloadingDelay=" + maxDelay + 
                "'\nunloadingDelayAverage=" + averageDelay + 
                "'\ntotalFine=" + totalFine + 
                "'\ncranesCount=" + cranesCount + 
                "\n}";
    }

    public List<UnloadShipReport> getUnloadShips() {
        return unloadShips;
    }

    public int getShipsCount() {
        return shipsCount;
    }

    public long getWaitingInQueueAverage() {
        return waitingInQueueAverage;
    }

    public long getMaxUnloadingDelay() {
        return maxUnloadingDelay;
    }

    public long getUnloadingDelayAverage() {
        return unloadingDelayAverage;
    }

    public long getTotalFine() {
        return totalFine;
    }

    public Map<CargoType, Integer> getCranesCount() {
        return cranesCount;
    }

    public void setUnloadShips(List<UnloadShipReport> unloadShips) {
        this.unloadShips = unloadShips;
    }

    public void setShipsCount(int shipsCount) {
        this.shipsCount = shipsCount;
    }

    public void setWaitingInQueueAverage(long waitingInQueueAverage) {
        this.waitingInQueueAverage = waitingInQueueAverage;
    }

    public void setMaxUnloadingDelay(long maxUnloadingDelay) {
        this.maxUnloadingDelay = maxUnloadingDelay;
    }

    public void setUnloadingDelayAverage(long unloadingDelayAverage) {
        this.unloadingDelayAverage = unloadingDelayAverage;
    }

    public void setTotalFine(long totalFine) {
        this.totalFine = totalFine;
    }

    public void setCranesCount(Map<CargoType, Integer> cranesCount) {
        this.cranesCount = cranesCount;
    }
    
}
