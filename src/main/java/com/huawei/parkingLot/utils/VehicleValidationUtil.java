package com.huawei.parkingLot.utils;

import com.huawei.parkingLot.models.Vehicle;

public class VehicleValidationUtil {

    private VehicleValidationUtil() {
    }

    public static void validateVehicle(final Vehicle vehicle) {
        if (vehicle == null || StringUtil.isBlank(vehicle.getLicense()) || StringUtil.isBlank(String.valueOf(vehicle.getSize()))) {
            throw new IllegalArgumentException("Hatali Arac");
        }
    }

}
