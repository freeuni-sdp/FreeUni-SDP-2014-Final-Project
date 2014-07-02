package ge.edu.freeuni.taxi.rest;

import ge.edu.freeuni.taxi.Driver;
import ge.edu.freeuni.taxi.Location;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gio on 7/2/2014.
 */

@Path("/drivers")
public class DriverRestService {

    private static List<Driver> list = new ArrayList<>();

    static {
        list.add(new Driver("driverone", new Location("Kandelaki Street", System.currentTimeMillis())));
        list.add(new Driver("drivertwo", new Location("Irakli Abashidze Street", System.currentTimeMillis())));
        list.add(new Driver("driverthree", new Location("Budapest Street #13a", System.currentTimeMillis())));
        list.add(new Driver("driverfour", new Location("Leselidze St", System.currentTimeMillis())));
        list.add(new Driver("driverfive", new Location("Rustaveli", System.currentTimeMillis())));
    }

    @Path("/")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Driver> getDrivers() {
        System.out.println("all drivers");

        return list;
    }

    @Path("/{name}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean updateDriver(@PathParam("name") String name) {
        System.out.println("add drivereeee with name " + name);
        return false;
    }
}
