package ge.edu.freeuni.taxi.dispatcher;


import java.sql.DriverManager;

import ge.edu.freeuni.taxi.Driver;
import ge.edu.freeuni.taxi.PassengerOrder;
import ge.edu.freeuni.taxi.core.Message;
import ge.edu.freeuni.taxi.manager.DriversManager;
import ge.edu.freeuni.taxi.manager.OrderManager;

public class CancelMessageListener implements IncomingMessageListener{

	@Override
	public void onIncomingMessage(Message message) {

		OrderManager orderManager = OrderManager.getInstance();
		String passengerName = message.getSender();
		PassengerOrder order = orderManager.getOrderByPassengerName(passengerName);
		if(order != null) orderManager.deleteOrder(order);

        Driver driver  = order.getDriver();
        /**
         * maybe driver is not assigned
         * on PassengerOrder then no one
         * should be updated
         */
        if (driver != null) {
            DriversManager driverManager = DriversManager.getInstance();
            driverManager.updateDriverAvailability(driver.getId(), true);
        }
	}

}
