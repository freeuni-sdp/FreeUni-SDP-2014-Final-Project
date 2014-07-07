package ge.edu.freeuni.taxi.rest;

import ge.edu.freeuni.taxi.Driver;
import ge.edu.freeuni.taxi.Location;
import ge.edu.freeuni.taxi.manager.DriversManager;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
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

    @GET
    public List<Driver> getDrivers() {
        return DriversManager.getInstance().getAllDrivers();
    }

    @GET
    @Path("/working")
    public List<Driver> getWorkingDrivers() {
        return DriversManager.getInstance().getAvailableDrivers();
    }

    @GET
    @Path("/available")
    public List<Driver> getAvailableDrivers() {
        return DriversManager.getInstance().getWorkingDrivers();
    }

    @POST
    public Driver addDriver(Driver driver) {
        return DriversManager.getInstance().addDriver(driver);
    }

    @DELETE
    public void removeDriver(long driverId) {
        DriversManager.getInstance().deleteDriver(driverId);
    }

    @PUT
    @Path("{driverId}")
    public Driver updateDriverName(@PathParam("{driverId}")long driverId, String name) {
        return DriversManager.getInstance().updateDriverName(driverId, name);
    }

    @PUT
    @Path("{driverId}/location")
    public Driver updateDriverLocation(@PathParam("{driverId}")long driverId, Location location) {
        return DriversManager.getInstance().updateDriverLocation(driverId, location);
    }
}