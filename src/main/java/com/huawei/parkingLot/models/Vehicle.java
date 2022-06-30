package com.huawei.parkingLot.models;

import java.util.Objects;

public class Vehicle {
    private String license;
    private String size;

    public Vehicle(){}

    public  Vehicle(final String license, final String size) {
        this.license = license;
        this.size = size;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vehicle)) return false;
        Vehicle vehicle = (Vehicle) o;
        return getSize() == vehicle.getSize() &&
                Objects.equals(getLicense(), vehicle.getLicense());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLicense(), getSize());
    }
}
