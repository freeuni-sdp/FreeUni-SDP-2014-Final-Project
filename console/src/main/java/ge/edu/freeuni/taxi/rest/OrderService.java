package ge.edu.freeuni.taxi.rest;

import ge.edu.freeuni.taxi.Driver;
import ge.edu.freeuni.taxi.Location;
import ge.edu.freeuni.taxi.Passenger;
import ge.edu.freeuni.taxi.PassengerOrder;
import ge.edu.freeuni.taxi.manager.OrderManager;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;

/**
 * Created by gio kochakidze on 7/3/2014.
 * Operator notifies OrderProcessor about new client
 * and sends appropriate driver or drivers to be assigned
 * on order
 */
@Path("/orders")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OrderService {

    @GET
    @Path("/active")
    public List<PassengerOrder> getPassangerOrders() {
        return OrderManager.getInstance().getActiveOrders();
    }

    @GET
    public PassengerOrder getPassangerOrder() {
        Passenger passenger = new Passenger();
        passenger.setId(1000l);
        passenger.setInfo("Passenger Location");
        Location location = new Location();
        location.setLatitude(1.1);
        location.setLongitude(1.2);
        location.setName("passenger location");
        passenger.setLocation(location);
        Driver driver = new Driver();
        driver.setLocation(location);
        driver.setName("driver");
        driver.setId(2222l);
        driver.setAvailable(true);
        driver.setLastWorkingDate(new Date());
        driver.setLocationLastUpdateTime(new Date());
        driver.setWorking(true);
        PassengerOrder order = new PassengerOrder();
        order.setId(1l);
        order.setAmount(100);
        order.setCreateTime(new Date());
        order.setDestination(location);
        order.setDuration(100l);
        order.setDriver(driver);
        order.setPassenger(passenger);
        return order;
    }
}
