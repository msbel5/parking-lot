package com.huawei.parkingLot.dao;

import com.huawei.parkingLot.models.Area;
import com.huawei.parkingLot.models.Vehicle;

import java.util.List;

public interface AreaPositionDao {

    void initAreaPositions(int parkingCapacity);

    void parkVehicle(int areaNumber, Vehicle vehicle);

    Vehicle unParkVehicle(int areaNumber);

    List<Area> getParkingStatus();

}
