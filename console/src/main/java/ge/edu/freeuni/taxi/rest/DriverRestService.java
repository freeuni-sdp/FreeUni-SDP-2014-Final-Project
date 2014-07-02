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
        list.add(new Driver("qochi", new Location("prochi", System.currentTimeMillis())));
        list.add(new Driver("vato", new Location("mawkulane", System.currentTimeMillis())));
        return list;
    }

    @Path("/{name}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean updateDriver( @PathParam("name") String name) {
        System.out.println("add drivereeee with name " + name);
        return false;
    }
}
