package org.example.cabbooking.manager;

import lombok.SneakyThrows;
import org.example.cabbooking.exception.RiderAlreadyPresentException;
import org.example.cabbooking.exception.RiderNotPresentException;
import org.example.cabbooking.model.Rider;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class RiderManager {
    Map<String, Rider> riderMap;

    public RiderManager() {
        riderMap = new HashMap<>();
    }

    @SneakyThrows
    public void addRider(Rider rider) {
        if(riderMap.containsKey(rider.getId()))
            throw new RiderAlreadyPresentException("Rider already present");
        riderMap.put(rider.getId(), rider);
    }

    @SneakyThrows
    public Rider getRider(String id) {
        if(!riderMap.containsKey(id)) {
            throw new RiderNotPresentException("Rider is already present");
        }
        return riderMap.get(id);
    }
}

