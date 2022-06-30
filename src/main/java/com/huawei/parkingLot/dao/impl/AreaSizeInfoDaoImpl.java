package com.huawei.parkingLot.dao.impl;

import com.huawei.parkingLot.dao.AreaSizeInfoDao;
import com.huawei.parkingLot.models.Vehicle;
import com.huawei.parkingLot.utils.StringUtil;
import com.huawei.parkingLot.utils.VehicleValidationUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AreaSizeInfoDaoImpl implements AreaSizeInfoDao {

    private Map<String, Set<String>> sizeToAreaNumbersMap;
    private Map<String, Set<String>> sizeToLicenseMap;

    private static final AreaSizeInfoDao instance = new AreaSizeInfoDaoImpl();

    // package default constructor so that it can be instantiated in unit tests, for actual use we'll be using singleton instance only
    AreaSizeInfoDaoImpl() {
    }

    public static AreaSizeInfoDao getInstance() {
        return instance;
    }

    @Override
    public void initAreaSizeInfo() {
        this.sizeToAreaNumbersMap = new HashMap<>();
        this.sizeToLicenseMap = new HashMap<>();
    }

    @Override
    public boolean parkVehicle(final int areaNumber, final Vehicle vehicle) {
        validateSlotNumber(areaNumber);
        VehicleValidationUtil.validateVehicle(vehicle);

        if (!this.sizeToAreaNumbersMap.containsKey(vehicle.getSize())) {
            this.sizeToAreaNumbersMap.put(String.valueOf(vehicle.getSize()), new HashSet<>());
        }

        if (!this.sizeToLicenseMap.containsKey(vehicle.getSize())) {
            this.sizeToLicenseMap.put(String.valueOf(vehicle.getSize()), new HashSet<>());
        }

        return this.sizeToAreaNumbersMap.get(vehicle.getSize()).add(String.valueOf(areaNumber))
                && this.sizeToLicenseMap.get(vehicle.getSize()).add(vehicle.getLicense());
    }

    @Override
    public boolean unParkVehicle(final int areaNumber, final Vehicle vehicle) {
        validateSlotNumber(areaNumber);
        VehicleValidationUtil.validateVehicle(vehicle);
        validateVehicleSizePresent(String.valueOf(vehicle.getSize()));

        return this.sizeToAreaNumbersMap.get(vehicle.getSize()).remove(areaNumber)
                && this.sizeToLicenseMap.get(vehicle.getSize()).remove(vehicle.getLicense());
    }

    @Override
    public List<String> getAreaPositionsForVehicleSize(final String vehicleSize) {
        StringUtil.validateNotBlank(String.valueOf(vehicleSize));
        validateVehicleSizePresent(String.valueOf(vehicleSize));

        final List<String> vehicleSizeSlots = new ArrayList<>(this.sizeToAreaNumbersMap.get(vehicleSize));
        Collections.sort(vehicleSizeSlots);

        return vehicleSizeSlots;
    }

    @Override
    public List<String> getLicensesForVehicleSize(final String vehicleSize) {
        StringUtil.validateNotBlank((vehicleSize));
        validateVehicleSizePresent((vehicleSize));

        return new ArrayList<>(this.sizeToLicenseMap.get(vehicleSize));
    }

    private void validateSlotNumber(final int areaNumber) {
        if (areaNumber < 1) {
            throw new IllegalArgumentException("areaNumber is invalid");
        }
    }

    private void validateVehicleSizePresent(final String vehicleSize) {

        if (isVehicleSizeNotInAreaMap(vehicleSize) || isVehicleSizeNotInLicenseMap(vehicleSize)) {
            throw new IllegalArgumentException("No vehicle with given color present in parking area");
        }
    }

    private boolean isVehicleSizeNotInAreaMap(final String vehicleSize) {
        return !this.sizeToAreaNumbersMap.containsKey(vehicleSize)
                || this.sizeToAreaNumbersMap.get(vehicleSize) == null
                || this.sizeToAreaNumbersMap.get(vehicleSize).isEmpty();
    }

    private boolean isVehicleSizeNotInLicenseMap(final String vehicleSize) {
        return !this.sizeToLicenseMap.containsKey(vehicleSize)
                || this.sizeToLicenseMap.get(vehicleSize) == null
                || this.sizeToLicenseMap.get(vehicleSize).isEmpty();
    }


}
