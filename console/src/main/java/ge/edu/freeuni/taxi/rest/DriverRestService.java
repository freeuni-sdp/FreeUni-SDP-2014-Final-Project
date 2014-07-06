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

        @Path("/available")
        @GET
        public List<Driver> getAvailableDrivers() {
                return DriversManager.getInstance().getAvailableDrivers();
        }

        @Path("/working")
        @GET
        public List<Driver> getWoringDrivers() {
                return DriversManager.getInstance().getWorkingDrivers();
        }

        @POST
        public Driver addDriver(Driver driver) {
                return DriversManager.getInstance().updateDriver(driver);
        }

        @PUT
        public Driver updateDriver(Driver driver) {
                return DriversManager.getInstance().updateDriver(driver);
        }

        @DELETE
        public void deleteDriver(Driver driver) {
                DriversManager.getInstance().deleteDriver(driver);
        }
}