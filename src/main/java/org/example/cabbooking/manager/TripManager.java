package org.example.cabbooking.manager;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.example.cabbooking.exception.CabNotAvailableException;
import org.example.cabbooking.exception.TripNotAvailableException;
import org.example.cabbooking.model.*;
import org.example.cabbooking.strategy.DriverMatchingStrategy;
import org.example.cabbooking.strategy.PricingStrategy;
import org.springframework.stereotype.Component;

import java.sql.Driver;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
public class TripManager {
    PricingStrategy pricingStrategy;
    DriverMatchingStrategy driverMatchingStrategy;
    CabManager cabManager;
    RiderManager riderManager;
    Map<String, List<Trip>> tripMap;
    public static final int MAX_DISTANCE = 20;

    public TripManager(
            CabManager cabsManager,
            RiderManager ridersManager,
            DriverMatchingStrategy driverMatchingStrategy,
            PricingStrategy pricingStrategy) {
        this.cabManager = cabsManager;
        this.riderManager = ridersManager;
        this.driverMatchingStrategy = driverMatchingStrategy;
        this.pricingStrategy = pricingStrategy;
        this.tripMap = new HashMap<>();
    }

    @SneakyThrows
    public Trip createTrip(Location fromPoint, Location toPoint, Rider rider) {

        List<Cab> cabList = cabManager.getCabs(fromPoint, MAX_DISTANCE);
        List<Cab> availableCabList = cabList.stream()
                .filter(cab -> cab.getCurrentTrip() == null)
                .collect(Collectors.toUnmodifiableList());

        Cab selectedCab = driverMatchingStrategy.matchDriver(rider, availableCabList, fromPoint, toPoint);

        if (selectedCab == null)
            throw new CabNotAvailableException("cab not available");

        double price = pricingStrategy.calculatePrice(fromPoint, toPoint);

        Trip trip = new Trip(UUID.randomUUID().toString(), rider,
                selectedCab, price, TripStatus.IN_PROGRESS,
                fromPoint,
                toPoint);
        if (!tripMap.containsKey(rider.getId())) {
            tripMap.put(rider.getId(), new ArrayList<>());
        }
        System.out.println("Trip Selected - "+ trip.getId() + " cab " +  trip.getCab().getId() + " status " + trip.getTripStatus() + " amount " + trip.getAmount());
        tripMap.get(rider.getId()).add(trip);
        selectedCab.setCurrentTrip(trip);
        return trip;
    }

    @SneakyThrows
    public void endTrip(Cab cab) {
        cab.getCurrentTrip().endTrip();
    }


    public List<Trip>  getTripHistory(String riderId) {
        return tripMap.get(riderId);
    }
}
