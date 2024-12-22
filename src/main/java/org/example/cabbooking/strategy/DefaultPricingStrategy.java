package org.example.cabbooking.strategy;

import org.example.cabbooking.model.Cab;
import org.example.cabbooking.model.Location;
import org.example.cabbooking.model.Rider;

import java.util.List;

public class DefaultPricingStrategy implements PricingStrategy{

    public static  final double PRICE_PER_KM = 10.0;

    @Override
    public double calculatePrice(Location from, Location to) {
        double distance = from.getDistance(to);
        return distance*PRICE_PER_KM;
    }
}
