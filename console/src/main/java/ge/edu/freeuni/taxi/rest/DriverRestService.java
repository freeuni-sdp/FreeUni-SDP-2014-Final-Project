package ge.edu.freeuni.taxi.rest;

import ge.edu.freeuni.taxi.Driver;
import ge.edu.freeuni.taxi.Location;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by giorgi kochakidze on 7/2/2014.
 * send drivers to operator
 * update driver location
 * send worker drivers
 */

@Path("/drivers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
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
    public List<Driver> getDrivers() {
        return list;
    }

    @Path("/{name}")
    @PUT
    public List<Driver> updateDriver(@PathParam("name") String name, Driver driver) {
        driver.getLocation().setLast_update(System.currentTimeMillis());
        list.set(list.indexOf(driver), driver);
        return list;
    }

    @Path("/available")
    @GET
    public List<String> getWorkerDrivers() {
        List<String> freeDrivers = new ArrayList<>();
        freeDrivers.add("driver1");
        freeDrivers.add("driver2");
        return freeDrivers;
    }
}
