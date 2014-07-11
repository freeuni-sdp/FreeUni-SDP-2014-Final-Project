package ge.edu.freeuni.taxi.rest;

import ge.edu.freeuni.taxi.Driver;
import ge.edu.freeuni.taxi.Location;
import ge.edu.freeuni.taxi.Passenger;
import ge.edu.freeuni.taxi.PassengerOrder;
import ge.edu.freeuni.taxi.manager.DriversManager;
import ge.edu.freeuni.taxi.manager.OrderManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class OrderRestService {

    @GET
    @Path("/active")
    public List<PassengerOrder> getPassengerOrders() {
        return OrderManager.getInstance().getActiveOrders();
    }

    @PUT
    public void createPassengerOrderAndAssignDriver(PassengerOrder passengerOrder) {
        OrderManager.getInstance().createPassengerOrder(passengerOrder);
    }

    @PUT
    @Path("/multiple_drivers")
    public void createPassengerOrderAndNotifyDrivers(PassengerOrderWrapper wrapper) {
        if (wrapper.getOrder().getId() == null)
            OrderManager.getInstance().createOrderWithoutDriver(wrapper.getOrder());
        //TODO someone have to notify twitter drivers via twitter api, but that someone doesn't exist :D
    }

    @GET
    @Path("/incoming")
    public List<PassengerOrder> getIncomingOrders() {
        return OrderManager.getInstance().getIncomingOrders();
    }
}
