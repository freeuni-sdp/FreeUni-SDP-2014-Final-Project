package ge.edu.freeuni.taxi.dispatcher;


import ge.edu.freeuni.taxi.PassengerOrder;
import ge.edu.freeuni.taxi.core.Message;
import ge.edu.freeuni.taxi.manager.OrderManager;

public class CancelMessageListener implements IncomingMessageListener{

	@Override
	public void onIncomingMessage(Message message) {

		OrderManager orderManager = OrderManager.getInstance();
		String passengerName = message.getSender();
		PassengerOrder order = orderManager.getOrderByPassengerName(passengerName);
		if(order!=null) orderManager.deleteOrder(order);
		
		
		
	}

}
