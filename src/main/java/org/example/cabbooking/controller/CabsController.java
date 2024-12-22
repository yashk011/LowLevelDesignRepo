package org.example.cabbooking.controller;

import org.example.cabbooking.manager.CabManager;
import org.example.cabbooking.manager.TripManager;
import org.example.cabbooking.model.Cab;
import org.example.cabbooking.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CabsController {

    CabManager cabManager;
    TripManager tripManager;

    public CabsController(CabManager cabManager , TripManager tripManager) {
        this.tripManager = tripManager;
        this.cabManager = cabManager;
    }

    @RequestMapping(value = "/healthCheck" , method = RequestMethod.GET)
    public ResponseEntity healthCheck() {
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/register/cab" , method = RequestMethod.POST)
    public ResponseEntity registerCab(String cabId, String driver) {
        cabManager.addCab(new Cab(cabId, driver, null, new Location(1,1), true));
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/update/cab/availability",  method = RequestMethod.POST )
    public ResponseEntity updateCabAvailability(String cabId, boolean availability) {
        cabManager.updateAvailability(cabId, availability);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value =  "/update/cab/location", method = RequestMethod.POST)
    public ResponseEntity updateCabLocation(String cabId, Location location) {
        cabManager.updateLocation(cabId, location);
        return  null;
    }

    @RequestMapping(value =  "/update/cab/end/trip", method = RequestMethod.POST)
    public ResponseEntity endTrip(String cabId) {
        tripManager.endTrip(cabManager.getCab(cabId));
        return null;
    }
}
