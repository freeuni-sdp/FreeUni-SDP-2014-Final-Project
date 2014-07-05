package ge.edu.freeuni.taxi.rest;

import ge.edu.freeuni.taxi.Driver;
import ge.edu.freeuni.taxi.manager.DriversManager;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Date;
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
		list.addAll(DriversManager.getInstance().getAllDrivers());
	}

    @Path("/")
    @GET
    public List<Driver> getDrivers() {
        return list;
    }

    @Path("/{name}")
    @PUT
    public List<Driver> updateDriver(@PathParam("name") String name, Driver driver) {
        driver.setLocationLastUpdateTime(new Date());
        list.set(list.indexOf(driver), driver);
        return list;
    }

    @Path("/available")
    @GET
    public List<Driver> getAvailableDrivers() {
		return DriversManager.getInstance().getAvailableDrivers();
	}
}