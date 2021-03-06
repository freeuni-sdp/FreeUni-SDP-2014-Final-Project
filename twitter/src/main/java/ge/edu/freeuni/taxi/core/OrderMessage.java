package ge.edu.freeuni.taxi.core;

/**
 * @author Sandro Dolidze
 */
public class OrderMessage extends Message {
    private final String origin;
    private final String destination;
    private final double fare;

    public OrderMessage(long id, String sender, Location location, String origin, String destination, double fare) {
        super(id, sender, MessageType.CLIENT_ORDERED, location, Message.NOT_REPLY);

        this.origin = origin;
        this.destination = destination;
        this.fare = fare;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public double getFare() {
        return fare;
    }
}
