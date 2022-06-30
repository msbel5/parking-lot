package com.huawei.parkingLot.models;

public class Area {

    private int areaNumber;
    private Vehicle parkedVehicle;

    public Area(){}

    public Area(final int areaNumber, final Vehicle parkedVehicle){
        this.areaNumber=areaNumber;
        this.parkedVehicle= parkedVehicle;
    }

    public int getAreaNumber() {
        return areaNumber;
    }

    public void setAreaNumber(int areaNumber) {
        this.areaNumber = areaNumber;
    }

    public Vehicle getParkedVehicle() {
        return parkedVehicle;
    }

    public void setParkedVehicle(Vehicle parkedVehicle) {
        this.parkedVehicle = parkedVehicle;
    }


}
