package com.huawei.parkingLot.dao.impl;

import com.huawei.parkingLot.models.Vehicle;
import com.huawei.parkingLot.models.Area;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import com.huawei.parkingLot.dao.AreaPositionDao;
import com.huawei.parkingLot.utils.VehicleValidationUtil;

public class AreaPositionDaoImpl implements AreaPositionDao {

    private static final AreaPositionDao instance = new AreaPositionDaoImpl();

    private List<Vehicle> areas;

    // package default constructor so that it can be instantiated in unit tests, for actual use we'll be using singleton instance only
    AreaPositionDaoImpl() {
    }

    public static AreaPositionDao getInstance() {
        return instance;
    }

    @Override
    public void initAreaPositions(final int parkingCapacity) {
        validateParkingCapacity(parkingCapacity);

        this.areas = new ArrayList<>(parkingCapacity);
        IntStream.range(0, parkingCapacity).forEach(i -> this.areas.add(null));
    }

    @Override
    public void parkVehicle(final int areaNumber, final Vehicle vehicle) {
        verifyInitialization();
        validateAreaNumber(areaNumber);
        VehicleValidationUtil.validateVehicle(vehicle);

        // area is already occupied
        if (this.areas.get(areaNumber - 1) != null) {
            throw new IllegalArgumentException("Given area is already occupied");
        }

        this.areas.set(areaNumber - 1, vehicle);
    }

    @Override
    public Vehicle unParkVehicle(final int areaNumber) {
        verifyInitialization();
        validateAreaNumber(areaNumber);

        // area is already empty
        if (this.areas.get(areaNumber - 1) == null) {
            throw new IllegalArgumentException("Given area is already empty");
        }

        return this.areas.set(areaNumber - 1, null);
    }

    @Override
    public List<Area> getParkingStatus() {
        verifyInitialization();

        return IntStream.range(0, this.areas.size())
                .filter(i -> this.areas.get(i) != null)
                .mapToObj(i -> new Area(i + 1, this.areas.get(i)))
                .collect(Collectors.toList());
    }

    private void verifyInitialization() {
        if (this.areas == null) {
            throw new IllegalStateException("Parking lot has not been initialized properly");
        }
    }

    private void validateAreaNumber(final int areaNumber) {
        if (areaNumber < 1 || areaNumber > this.areas.size()) {
            throw new IllegalArgumentException("Invalid areaNumber");
        }
    }

    private void validateParkingCapacity(final int parkingCapacity) {
        if (parkingCapacity < 0) {
            throw new IllegalArgumentException("Invalid parking capacity");
        }
    }
    
}
