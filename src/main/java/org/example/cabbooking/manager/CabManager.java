package org.example.cabbooking.manager;

import lombok.SneakyThrows;
import org.example.cabbooking.exception.CabAlreadyPresentException;
import org.example.cabbooking.exception.CabNotAvailableException;
import org.example.cabbooking.exception.CabNotPresentException;
import org.example.cabbooking.model.Cab;
import org.example.cabbooking.model.Location;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CabManager {
    Map<String, Cab> cabMap;

    public CabManager() {
        cabMap = new HashMap<>();
    }

    @SneakyThrows
    public void addCab(Cab cab) {
        if(cabMap.containsKey(cab.getId()))
            throw new CabAlreadyPresentException("Cab already present");
        cabMap.put(cab.getId(), cab);
    }

    @SneakyThrows
    public Cab getCab(String id) {
        if(!cabMap.containsKey(id)) {
            throw new CabNotPresentException("Rider is already present");
        }
        return cabMap.get(id);
    }

    public List<Cab> getCabs(Location location , int distance) {
        List<Cab> cabs = cabMap.values().stream()
                .filter(cab -> cab.getCurrentLocation().getDistance(location) < distance)
                .collect(Collectors.toUnmodifiableList());
        return cabs;
    }

    @SneakyThrows
    public void updateAvailability(String cabId, boolean availability) {
        if(!cabMap.containsKey(cabId))
            throw new CabNotAvailableException("Cab not Available!!");

        printWithNewLine("Setting cab availability");
        cabMap.get(cabId).setAvailable(availability);
        printWithNewLine("Cab Availability Status " + availability);
    }

    @SneakyThrows
    public void updateLocation(String cabId, Location location) {
        if(!cabMap.containsKey(cabId))
            throw new CabNotAvailableException("Cab not Available!!");
        printWithNewLine("Setting New Location");
        cabMap.get(cabId).setCurrentLocation(location);
        printWithNewLine("New Location set for " + cabId + " " + location.getX_coordinate() + " " + location.getY_coordinate());
    }

    public void printWithNewLine(String msg) {
        System.out.println(msg);
    }
}
