package com.huawei.parkingLot.main;

import com.huawei.parkingLot.models.Area;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Commands {

    public Map<String, Method> commandsMap;

    public Commands() {
        commandsMap = new HashMap<String, Method>();
        try {
            populateCommandsHashMap();
        } catch (final NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
    private void populateCommandsHashMap() throws NoSuchMethodException {
        commandsMap.put("createArea", ParkingArea.class.getMethod("createArea", String.class));
        commandsMap.put("park", ParkingArea.class.getMethod("doPark", String.class, String.class));
        commandsMap.put("leave", ParkingArea.class.getMethod("doLeave", String.class));
        commandsMap.put("status", ParkingArea.class.getMethod("getAreaStatus"));
        commandsMap.put("licensesWithSize", ParkingArea.class.getMethod("getLicensesFromSize", String.class));
        commandsMap.put("areasWithSize", ParkingArea.class.getMethod("getAreaNosFromSize", String.class));
        commandsMap.put("vehiclePosition", ParkingArea.class.getMethod("getAreaNoFromLicense", String.class));
    }

}
