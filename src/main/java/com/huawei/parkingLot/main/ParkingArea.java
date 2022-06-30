package com.huawei.parkingLot.main;

import java.util.List;

import com.huawei.parkingLot.models.Vehicle;
import com.huawei.parkingLot.models.Area;
import com.huawei.parkingLot.services.AreaService;
import com.huawei.parkingLot.services.impl.AreaServiceImpl;

public class ParkingArea {

    private AreaService areaService;

    public void createArea(final String areaNumber){
        areaService = AreaServiceImpl.getInstance();
        areaService.initArea(Integer.parseInt(areaNumber));

        System.out.println(String.format("%s park alani olusturuldu.", areaNumber));
    }

    public void createNewArea(final int areaNumber){
        areaService = AreaServiceImpl.getNewInstance();
        areaService.initArea((areaNumber));

    }

    public void doPark(final String license, final String size){

        final int areaNumber = areaService.parkVehicle(new Vehicle(license, (size)));
        if(areaNumber == -1){
            System.out.println("Alan Dolu");
        }else{
            System.out.println("Park edilen alan: " + areaNumber);
        }

    }


    public void doLeave(final String areaNumber){
        try{
            if(!areaService.unParkVehicle(Integer.parseInt(areaNumber)))
                System.out.println(areaNumber+"numarali alan bosaldi");
            else
                System.out.println("Bulunamadi");

        } catch (final IllegalArgumentException e) {
            System.out.println("Hata");
        }

    }

    public void getAreaStatus(){
        final List<Area> areas = areaService.getParkingStatus();
        System.out.println("Alan No.\tLicense\tSize");
        for (final Area parkingArea: areas) {
            System.out.println(parkingArea.getAreaNumber() + "\t\t" + parkingArea.getParkedVehicle().getLicense()
                    + "\t" + parkingArea.getParkedVehicle().getSize());
        }

    }

    public void getLicensesFromSize(final String size){
        final List<String> licenses = areaService.getLicenseForVehicleSize((size));

        for(int i=0;i<licenses.size()-1;i++){
            System.out.print(licenses.get(i) + ", ");
        }
        System.out.println(licenses.get(licenses.size()-1));

    }

    public void getAreaNosFromSize(final String size){
        final List<String> areas = areaService.getAreaPositionsForVehicleSize((size));

        for(int i=0;i<areas.size()-1;i++){
            System.out.print(areas.get(i) + ", ");
        }
        System.out.println(areas.get(areas.size()-1));
    }

    public void getAreaNoFromLicense(final String license){
        try{
            final int areaNumber = areaService.getAreaNumberForLicense(license);
            System.out.println(areaNumber);
        }catch (final IllegalArgumentException e){
            System.out.println("Not found");
        }
    }


}
