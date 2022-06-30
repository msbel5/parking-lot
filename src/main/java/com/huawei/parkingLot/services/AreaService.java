package com.huawei.parkingLot.services;

import com.huawei.parkingLot.models.Area;
import com.huawei.parkingLot.models.Vehicle;

import java.util.List;

public interface AreaService {

    void initArea(int parkingCapacity);

    int parkVehicle(Vehicle vehicle);

    boolean unParkVehicle(int areaNumber);

    List<String> getAreaPositionsForVehicleSize(String vehicleSize);

    List<String> getLicenseForVehicleSize(final String vehicleSize);

    int getAreaNumberForLicense(final String License);

    List<Area> getParkingStatus();

}
