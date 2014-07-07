package ge.edu.freeuni.taxi.rest;

import ge.edu.freeuni.taxi.Driver;
import ge.edu.freeuni.taxi.Location;
import ge.edu.freeuni.taxi.Passenger;
import ge.edu.freeuni.taxi.PassengerOrder;

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

    @POST
    public void assigneeDriverOnOrder(Client client, String driver) {
        System.out.println(client);
        System.out.println(driver);
    }

    @POST
    @Path("/tweet")
    public void assigneeAvailableDriversOnOrder(Client client, List<String> drivers) {
        System.out.println(client);
        for (String s : drivers)
            System.out.println(s);
    }


    private class Client {
        private String name;
        private String location;

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Client{" +
                    "name='" + name + '\'' +
                    ", location='" + location + '\'' +
                    '}';
        }
    }
}
