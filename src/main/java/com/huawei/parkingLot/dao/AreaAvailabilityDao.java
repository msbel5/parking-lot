package com.huawei.parkingLot.dao;

public interface AreaAvailabilityDao {

    void initAreaAvailability(int parkingCapacity);

    boolean isAreaAvailable();

    boolean isParkingEmpty();

    int getNearestAvailableArea();

    void occupyArea(int areaNumber);

    void freeArea(int areaNumber);

}
