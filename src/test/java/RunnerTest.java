
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.example.cabbooking.controller.CabsController;
import org.example.cabbooking.controller.RiderController;
import org.example.cabbooking.manager.CabManager;
import org.example.cabbooking.manager.RiderManager;
import org.example.cabbooking.manager.TripManager;
import org.example.cabbooking.model.Location;
import org.example.cabbooking.model.Trip;
import org.example.cabbooking.strategy.DefaultDriverMatchingStrategy;
import org.example.cabbooking.strategy.DefaultPricingStrategy;
import org.example.cabbooking.strategy.DriverMatchingStrategy;
import org.example.cabbooking.strategy.PricingStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class RunnerTest {
    CabsController cabsController;
    RiderController ridersController;

    @BeforeEach
    void setUp() {
        CabManager cabsManager = new CabManager();
        RiderManager ridersManager = new RiderManager();

        DriverMatchingStrategy driverMatchingStrategy = new DefaultDriverMatchingStrategy();
        PricingStrategy pricingStrategy = new DefaultPricingStrategy();

        TripManager tripsManager = new TripManager(cabsManager, ridersManager, driverMatchingStrategy, pricingStrategy);

        cabsController = new CabsController(cabsManager, tripsManager);
        ridersController = new RiderController(ridersManager, tripsManager);
    }

    @Test
    void testCabBookingFlow() {

        String r1 = "r1";
        ridersController.registerRider(r1, "ud");
        String r2 = "r2";
        ridersController.registerRider(r2, "du");
        String r3 = "r3";
        ridersController.registerRider(r3, "rider3");
        String r4 = "r4";
        ridersController.registerRider(r4, "rider4");

        String c1 = "c1";
        cabsController.registerCab(c1, "driver1");
        String c2 = "c2";
        cabsController.registerCab(c2, "driver2");
        String c3 = "c3";
        cabsController.registerCab(c3, "driver3");
        String c4 = "c4";
        cabsController.registerCab(c4, "driver4");
        String c5 = "c5";
        cabsController.registerCab(c5, "driver5");

        cabsController.updateCabLocation(c1, new Location(20.0 , 30.0));
        cabsController.updateCabLocation(c2, new Location(2.0, 2.0)); //na
        cabsController.updateCabLocation(c3, new Location(100.0, 100.0));
        cabsController.updateCabLocation(c4, new Location(110.0, 110.0)); //na
        cabsController.updateCabLocation(c5, new Location(4.0, 4.0));

        cabsController.updateCabAvailability(c2, false);
        cabsController.updateCabAvailability(c4, false);


        ridersController.bookRide(0.0, 0.0, 500.0, 500.0, r1);
        ridersController.bookRide(0.0, 0.0, 500.0, 500.0, r2);

        System.out.println("###################");

        List<Trip> trips = (List<Trip>) ridersController.getHistory(r1).getBody();
        for(Trip trip : trips) {
            System.out.println(trip.getCab().getId());
            System.out.println(trip.getTripStatus());
            System.out.println(trip.getAmount());
            System.out.println(trip.getSource().getX_coordinate() + " " + trip.getDestination().getY_coordinate());
            System.out.println(trip.getDestination().getX_coordinate() + " " + trip.getDestination().getY_coordinate());
        }
        System.out.println("###################");

        cabsController.endTrip(c5);

        List<Trip> trips2 = (List<Trip>) ridersController.getHistory(r1).getBody();
        for(Trip trip : trips2) {
            System.out.println(trip.getCab().getId());
            System.out.println(trip.getTripStatus());
            System.out.println(trip.getAmount());
            System.out.println(trip.getSource().getX_coordinate() + " " + trip.getDestination().getY_coordinate());
            System.out.println(trip.getDestination().getX_coordinate() + " " + trip.getDestination().getY_coordinate());
        }
        System.out.println("###################");

    }

}