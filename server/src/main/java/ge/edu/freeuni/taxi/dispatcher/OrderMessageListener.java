package ge.edu.freeuni.taxi.dispatcher;

import java.util.Date;

import ge.edu.freeuni.taxi.Location;
import ge.edu.freeuni.taxi.Passenger;
import ge.edu.freeuni.taxi.PassengerOrder;
import ge.edu.freeuni.taxi.core.Message;
import ge.edu.freeuni.taxi.manager.OrderManager;

public class OrderMessageListener implements IncomingMessageListener{


    /**
     * ტვიტერიდან შემოსული მესიჯი დამუშავდება და გაეგზავნება
     * ოპერატორს რომელიც ამის შემდეგ გადაწყვეტს თვითონ
     * მიამაგროს შეკვეთაზე მძღოლი, ტვიტერის საშუალებით მოხდეს
     * არჩევა თუ რაციის მეშვეობით.
     * @param message
     */
	@Override
	public void onIncomingMessage(Message message) {
        OrderManager manager = OrderManager.getInstance();
        PassengerOrder order = new PassengerOrder();
        order.setAmount(10);
        order.setCreateTime(new Date());
        Location location = new Location();
        location.setName("");
        location.setLongitude(message.getLocation().getLongitude());
        location.setLatitude(message.getLocation().getLatitude());
        Passenger passenger = new Passenger();
        passenger.setInfo(message.getSender());
        passenger.setLocation(location);
        order.setPassenger(passenger);
        order.setPassenger(passenger);
        order.setIncoming(true);
        manager.updateOrder(order);
        /**
         *  TODO in Message should be amount of order and Destination Location
         *  TODO this features should be done by sandro dolidze
         *  TODO Message should be parsed if message is not parsed correctly the message
         *  TODO should be sent to operator in order not to loose order of client
         */
	}

}
