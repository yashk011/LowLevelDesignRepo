package org.example.cabbooking.strategy;

import org.example.cabbooking.model.Cab;
import org.example.cabbooking.model.Location;
import org.example.cabbooking.model.Rider;

import java.util.List;

public class DefaultDriverMatchingStrategy implements DriverMatchingStrategy{
    @Override
    public Cab matchDriver(Rider rider, List<Cab> cabs, Location from, Location to) {
        return cabs.get(0);
    }
}
