package ge.edu.freeuni.taxi.dispatcher;

import java.util.Date;

import ge.edu.freeuni.taxi.Passenger;
import ge.edu.freeuni.taxi.PassengerOrder;
import ge.edu.freeuni.taxi.core.Message;
import ge.edu.freeuni.taxi.core.MessageType;
import ge.edu.freeuni.taxi.manager.OrderManager;

public class OrderMessageListener implements IncomingMessageListener{

	@Override
	public void onIncomingMessage(Message message) {
			OrderManager manager = OrderManager.getInstance();
			PassengerOrder order = new PassengerOrder();
			order.setCreateTime(new Date());
			Passenger p = new Passenger();
			p.setName(message.getSender());
			order.setPassenger(p);
			manager.updateOrder(order);
			// TODO
	}

}
