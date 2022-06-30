package com.huawei.parkingLot.dao;

import com.huawei.parkingLot.models.Vehicle;

import java.util.List;

public interface AreaSizeInfoDao {

    void initAreaSizeInfo();

    boolean parkVehicle(int areaNumber, Vehicle vehicle);

    boolean unParkVehicle(int areaNumber, Vehicle vehicle);

    List<String> getAreaPositionsForVehicleSize(String vehicleSize);

    List<String> getLicensesForVehicleSize(final String vehicleSize);

}
