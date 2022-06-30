package com.huawei.parkingLot.services.impl;

import com.huawei.parkingLot.dao.AreaAvailabilityDao;
import com.huawei.parkingLot.dao.AreaLicenseInfoDao;
import com.huawei.parkingLot.dao.AreaPositionDao;
import com.huawei.parkingLot.dao.AreaSizeInfoDao;
import com.huawei.parkingLot.dao.impl.AreaAvailabilityDaoImpl;
import com.huawei.parkingLot.dao.impl.AreaLicenseInfoDaoImpl;
import com.huawei.parkingLot.dao.impl.AreaPositionDaoImpl;
import com.huawei.parkingLot.dao.impl.AreaSizeInfoDaoImpl;
import com.huawei.parkingLot.models.Area;
import com.huawei.parkingLot.models.Vehicle;
import com.huawei.parkingLot.services.AreaService;
import com.huawei.parkingLot.utils.StringUtil;
import com.huawei.parkingLot.utils.VehicleValidationUtil;

import java.util.List;


public class AreaServiceImpl implements AreaService {

    private final AreaPositionDao areaPositionDao;
    private final AreaAvailabilityDao areaAvailabilityDao;
    private final AreaLicenseInfoDao areaLicenseInfoDao;
    private final AreaSizeInfoDao areaSizeInfoDao;

    private static final AreaService instance = new AreaServiceImpl();

    // package default constructor so that it can be instantiated in unit tests, for actual use we'll be using singleton instance only
    AreaServiceImpl() {
        this.areaPositionDao = AreaPositionDaoImpl.getInstance();
        this.areaAvailabilityDao = AreaAvailabilityDaoImpl.getInstance();
        this.areaLicenseInfoDao = AreaLicenseInfoDaoImpl.getInstance();
        this.areaSizeInfoDao = AreaSizeInfoDaoImpl.getInstance();
    }

    public static AreaService getInstance() {
        return instance;
    }

    public static AreaService getNewInstance(){
        return new AreaServiceImpl();
    }

    @Override
    public void initArea(final int parkingCapacity) {
        areaPositionDao.initAreaPositions(parkingCapacity);
        areaAvailabilityDao.initAreaAvailability(parkingCapacity);
        areaLicenseInfoDao.initAreaLicenseInfo();
        areaSizeInfoDao.initAreaSizeInfo();
    }

    @Override
    public int parkVehicle(final Vehicle vehicle) {
        VehicleValidationUtil.validateVehicle(vehicle);

        // if there's no parking slot available
        if (!areaAvailabilityDao.isAreaAvailable()) {
            return -1;
        }

        final int availableAreaNumber = areaAvailabilityDao.getNearestAvailableArea();
        areaPositionDao.parkVehicle(availableAreaNumber, vehicle);
        areaAvailabilityDao.occupyArea(availableAreaNumber);
        areaLicenseInfoDao.parkVehicle(availableAreaNumber, vehicle);
        areaSizeInfoDao.parkVehicle(availableAreaNumber, vehicle);

        return availableAreaNumber;
    }

    @Override
    public boolean unParkVehicle(final int areaNumber) {
        validateAreaNumber(areaNumber);

        // if parking is empty, vehicle can't be un-parked
        if (areaAvailabilityDao.isParkingEmpty()) {
            return false;
        }

        areaAvailabilityDao.freeArea(areaNumber);
        final Vehicle unParkedVehicle = areaPositionDao.unParkVehicle(areaNumber);
        areaLicenseInfoDao.unParkVehicle(unParkedVehicle);
        return areaSizeInfoDao.unParkVehicle(areaNumber, unParkedVehicle);
    }

    @Override
    public List<String> getAreaPositionsForVehicleSize(final String vehicleSize) {
        StringUtil.validateNotBlank((vehicleSize));

        return areaSizeInfoDao.getAreaPositionsForVehicleSize(vehicleSize);
    }

    @Override
    public List<String> getLicenseForVehicleSize(final String vehicleSize) {
        StringUtil.validateNotBlank(String.valueOf(vehicleSize));

        return areaSizeInfoDao.getLicensesForVehicleSize(vehicleSize);
    }

    @Override
    public int getAreaNumberForLicense(final String license) {
        StringUtil.validateNotBlank(license);

        return areaLicenseInfoDao.getAreaNumberForLicense(license);
    }

    @Override
    public List<Area> getParkingStatus() {
        return areaPositionDao.getParkingStatus();
    }

    private void validateAreaNumber(final int areaNumber) {
        if (areaNumber < 1) {
            throw new IllegalArgumentException("Invalid slot number");
        }
    }

}
