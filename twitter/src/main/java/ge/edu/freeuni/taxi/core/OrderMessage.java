package ge.edu.freeuni.taxi.core;

/**
 * @author Sandro Dolidze
 */
public class OrderMessage extends Message {
    private final String address;
    private final double fare;

    public OrderMessage(long id, String sender, Location location, String address, double fare) {
        super(id, sender, MessageType.CLIENT_ORDERED, location, Message.NOT_REPLY);

        this.address = address;
        this.fare = fare;
    }

    public String getAddress() {
        return address;
    }

    public double getFare() {
        return fare;
    }
}
