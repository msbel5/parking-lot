package com.huawei.parkingLot.dao.impl;

import com.huawei.parkingLot.dao.AreaLicenseInfoDao;
import com.huawei.parkingLot.models.Vehicle;
import com.huawei.parkingLot.utils.VehicleValidationUtil;
import com.huawei.parkingLot.utils.StringUtil;

import java.util.HashMap;
import java.util.Map;

public class AreaLicenseInfoDaoImpl implements AreaLicenseInfoDao {

    private static final AreaLicenseInfoDao instance = new AreaLicenseInfoDaoImpl();

    private Map<String, Integer> licenseToAreaMap;

    // package default constructor so that it can be instantiated in unit tests, for actual use we'll be using singleton instance only
    AreaLicenseInfoDaoImpl() {
    }

    public static AreaLicenseInfoDao getInstance() {
        return instance;
    }

    @Override
    public void initAreaLicenseInfo() {
        this.licenseToAreaMap = new HashMap<>();
    }

    @Override
    public void parkVehicle(final int areaNumber, final Vehicle vehicle) {
        verifyInitialization();
        validateAreaNumber(areaNumber);
        VehicleValidationUtil.validateVehicle(vehicle);
        validateVehicleIsNotPresent(vehicle);

        this.licenseToAreaMap.put(vehicle.getLicense(), areaNumber);
    }

    @Override
    public void unParkVehicle(final Vehicle vehicle) {
        verifyInitialization();
        VehicleValidationUtil.validateVehicle(vehicle);
        validateVehiclePresent(vehicle);

        this.licenseToAreaMap.remove(vehicle.getLicense());
    }

    @Override
    public int getAreaNumberForLicense(final String license) {
        verifyInitialization();
        StringUtil.validateNotBlank(license);
        validateLicensePresent(license);

        return this.licenseToAreaMap.get(license);
    }

    private void validateAreaNumber(final int areaNumber) {
        if (areaNumber < 1) {
            throw new IllegalArgumentException("Yanlis Alan Numarasi");
        }
    }

    private void validateVehicleIsNotPresent(final Vehicle vehicle) {
        if (licenseToAreaMap.containsKey(vehicle.getLicense())) {
            throw new IllegalArgumentException("Alan Dolu");
        }
    }

    private void validateVehiclePresent(final Vehicle vehicle) {
        if (!licenseToAreaMap.containsKey(vehicle.getLicense())) {
            throw new IllegalArgumentException("Arac alanda degil");
        }
    }

    private void validateLicensePresent(final String license) {
        if (!licenseToAreaMap.containsKey(license)) {
            throw new IllegalArgumentException("Arac bulunamadi");
        }
    }

    private void verifyInitialization() {
        if (this.licenseToAreaMap == null) {
            throw new IllegalStateException("Alan ayarlanamadi");
        }
    }
    
}
