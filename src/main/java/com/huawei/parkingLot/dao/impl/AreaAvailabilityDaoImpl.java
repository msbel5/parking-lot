package com.huawei.parkingLot.dao.impl;

import com.huawei.parkingLot.dao.AreaAvailabilityDao;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.IntStream;

public class AreaAvailabilityDaoImpl implements AreaAvailabilityDao {

    private int parkingCapacity;
    private Queue<Integer> emptyParkingAreasOrder;

    private static final AreaAvailabilityDao instance = new AreaAvailabilityDaoImpl();

    public static AreaAvailabilityDao getInstance() {
        return instance;
    }

    // package default constructor so that it can be instantiated in unit tests, for actual use we'll be using singleton instance only
    AreaAvailabilityDaoImpl() {
    }

    @Override
    public void initAreaAvailability(final int parkingCapacity) {
        this.emptyParkingAreasOrder = new PriorityQueue<>();
        this.parkingCapacity = parkingCapacity;
        IntStream.range(1, parkingCapacity + 1).forEach(i -> this.emptyParkingAreasOrder.offer(i));
    }

    @Override
    public boolean isAreaAvailable() {
        verifyInitialization();
        return !this.emptyParkingAreasOrder.isEmpty();
    }

    @Override
    public boolean isParkingEmpty() {
        verifyInitialization();
        return this.emptyParkingAreasOrder.size() == parkingCapacity;
    }

    @Override
    public int getNearestAvailableArea() {
        verifyInitialization();
        return this.emptyParkingAreasOrder.peek();
    }

    @Override
    public void occupyArea(final int areaNumber) {
        verifyInitialization();
        validateAreaNumber(areaNumber);

        if (areaNumber != this.emptyParkingAreasOrder.peek()) {
            throw new IllegalArgumentException("given slot can't be occupied");

        }
        this.emptyParkingAreasOrder.remove(areaNumber);
    }

    @Override
    public void freeArea(final int AreaNumber) {
        verifyInitialization();
        validateAreaNumber(AreaNumber);

        this.emptyParkingAreasOrder.add(AreaNumber);
    }

    private void validateAreaNumber(final int areaNumber) {
        if (areaNumber < 1) {
            throw new IllegalArgumentException("areaNumber is invalid");
        }
    }

    private void verifyInitialization() {
        if (this.emptyParkingAreasOrder == null) {
            throw new IllegalStateException("Parking lot has not been initialized properly");
        }
    }

}
