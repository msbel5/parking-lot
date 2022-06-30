package com.huawei.parkingLot.dao;

import com.huawei.parkingLot.models.Vehicle;

public interface AreaLicenseInfoDao {

    void initAreaLicenseInfo();

    void parkVehicle(int areaNumber, Vehicle vehicle);

    void unParkVehicle(Vehicle vehicle);

    int getAreaNumberForLicense(String License);

}
