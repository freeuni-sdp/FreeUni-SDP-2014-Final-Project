package ge.edu.freeuni.taxi.dispatcher;

import java.util.Date;

import ge.edu.freeuni.taxi.core.Message;
import ge.edu.freeuni.taxi.core.OrderMessage;

import ge.edu.freeuni.taxi.Location;
import ge.edu.freeuni.taxi.Passenger;
import ge.edu.freeuni.taxi.PassengerOrder;

import ge.edu.freeuni.taxi.manager.OrderManager;

/**
 * @author - Giorgi Kochakidze, Sandro Dolidze
 *
 * this class is called  when user orders taxi from twitter
 */
public class OrderMessageListener implements IncomingMessageListener {
    private final OrderManager orderManager = OrderManager.getInstance();

	@Override
	public void onIncomingMessage(Message message) {
        OrderMessage orderMessage = (OrderMessage) message;
        PassengerOrder order = getPassengerOrder(orderMessage);
        orderManager.createOrderWithoutDriver(order);
	}

    private PassengerOrder getPassengerOrder(OrderMessage orderMessage) {
        PassengerOrder order = new PassengerOrder();

        order.setPassenger(getPassenger(orderMessage));
        order.setAmount(orderMessage.getFare());
        order.setCreateTime(new Date());
        order.setDestination(getDestination(orderMessage));
        order.setIncoming(true);

        return order;
    }

    private Passenger getPassenger(OrderMessage orderMessage) {
        Passenger passenger = new Passenger();

        passenger.setInfo(orderMessage.getSender());
        passenger.setLocation(getOrigin(orderMessage));

        return passenger;
    }

    private Location getOrigin(OrderMessage orderMessage) {
        Location location = new Location();

        location.setName(orderMessage.getOrigin());

        if (orderMessage.getLocation() != null) {
            location.setLongitude(orderMessage.getLocation().getLongitude());
            location.setLatitude(orderMessage.getLocation().getLatitude());
        }

        return location;
    }

    private Location getDestination(OrderMessage orderMessage) {
        Location location = new Location();

        location.setName(orderMessage.getDestination());

        return location;
    }
}
