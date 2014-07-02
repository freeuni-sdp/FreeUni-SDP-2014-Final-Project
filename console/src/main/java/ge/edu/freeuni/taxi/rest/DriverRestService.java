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

    @Path("/")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Driver> getDrivers() {
        System.out.println("all drivers");
        List<Driver> list = new ArrayList<>();
        return list;
    }

    @Path("/")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean updateDriver(Driver driver) {
        System.out.println(driver);
        return false;
    }
}
