package org.example.cabbooking.strategy;

import org.example.cabbooking.model.Location;

public interface PricingStrategy {
    public double calculatePrice(Location from , Location to);
}
