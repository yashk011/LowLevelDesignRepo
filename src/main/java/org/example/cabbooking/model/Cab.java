package org.example.cabbooking.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Cab {
    String id;
    String driver;
    Trip currentTrip;
    Location currentLocation;
    boolean isAvailable;
}
