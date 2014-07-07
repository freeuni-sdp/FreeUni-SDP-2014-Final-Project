package ge.edu.freeuni.taxi.rest;

import ge.edu.freeuni.taxi.PassengerOrder;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
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
        return new PassengerOrder();
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
