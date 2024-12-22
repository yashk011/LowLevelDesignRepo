package org.example.cabbooking.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@AllArgsConstructor
@Getter
public class Location {
    double x_coordinate;
    double y_coordinate;

    public double getDistance(Location location) {
         double a = Math.pow( (location.x_coordinate - this.x_coordinate),2);
         double b = Math.pow( (location.y_coordinate - this.y_coordinate),2);
         return Math.sqrt(a+b);
    }
}
