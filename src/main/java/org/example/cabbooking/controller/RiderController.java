package org.example.cabbooking.controller;

import org.example.cabbooking.manager.CabManager;
import org.example.cabbooking.manager.RiderManager;
import org.example.cabbooking.manager.TripManager;
import org.example.cabbooking.model.Location;
import org.example.cabbooking.model.Rider;
import org.example.cabbooking.model.Trip;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.UUID;

public class RiderController {

    RiderManager riderManager;
    TripManager tripManager;

    public RiderController(RiderManager riderManager , TripManager tripManager) {
        this.tripManager = tripManager;
        this.riderManager = riderManager;
    }

    @RequestMapping(value = "/v1/register/rider", method = RequestMethod.POST)
    public ResponseEntity registerRider(String riderId, String riderName) {
        riderManager.addRider(new Rider(riderId, riderName));
        return new ResponseEntity("", HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/book/ride", method = RequestMethod.POST)
    public ResponseEntity bookRide(double fromX, double fromY ,
                                   double toX ,double toY, String riderId) {

        Trip trip = tripManager.createTrip(new Location(fromX,fromY) , new Location(toX, toY), riderManager.getRider(riderId));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/getHistory/rider", method = RequestMethod.POST)
    public ResponseEntity getHistory(String riderId) {
        List<Trip> trips = tripManager.getTripHistory(riderId);
        return new ResponseEntity<>(trips, HttpStatus.OK );
    }

}
