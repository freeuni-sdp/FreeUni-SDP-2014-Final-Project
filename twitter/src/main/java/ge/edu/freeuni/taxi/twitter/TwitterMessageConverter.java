package ge.edu.freeuni.taxi.twitter;

import ge.edu.freeuni.taxi.core.Location;
import ge.edu.freeuni.taxi.core.MessageType;
import ge.edu.freeuni.taxi.core.Message;
import ge.edu.freeuni.taxi.core.OrderMessage;

import twitter4j.GeoLocation;
import twitter4j.Status;

/**
 * @author Sandro Dolidze
 *
 * Small utility class used for converting twitter messages for message types and vice-versa
 *
 * format used for ordering taxi is - "#freeunitaxi order taxi, ${address}, ${fare}"
 */
public class TwitterMessageConverter {

    private static class Pair {
        public final String pattern;
        public final MessageType messageType;

        public Pair(String pattern, MessageType messageType) {
            this.pattern = pattern;
            this.messageType = messageType;
        }
    }

    private final Pair[] pairs = new Pair[] {
        new Pair("order taxi"      , MessageType.CLIENT_ORDERED),
        new Pair("cancel taxi"     , MessageType.CLIENT_CANCELED),
        new Pair("arrived"         , MessageType.DRIVER_ARRIVED),
        new Pair("location update" , MessageType.DRIVER_LOCATION_UPDATED),
        new Pair("assign me"       , MessageType.DRIVER_ASSIGNED_TO_ORDER),
        new Pair("client delivered", MessageType.DRIVER_DELIVERED_CLIENT)
    };

    public MessageType parseMessageType(String message) {
        for (Pair pair: pairs) {
            if (message.contains(pair.pattern)) {
                return pair.messageType;
            }
        }

        throw new RuntimeException("unable to parse twitter message: " + message);
    }

    public String formatMessageType(MessageType messageType) {
        for (Pair pair: pairs) {
            if (messageType.equals(pair.messageType)) {
                return pair.pattern + " (" + System.currentTimeMillis() + ")";
            }
        }

        throw new RuntimeException("unable to format message type: " + messageType);
    }

    public Message parseMessage(Status status) {
        try {
            MessageType messageType = parseMessageType(status.getText());

            if (messageType == MessageType.CLIENT_ORDERED) {
                return getOrderMessage(status);
            } else {
                return getNormalMessage(status);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("unable to parse tweet", e);
        }
    }

    private Message getNormalMessage(Status status) {
        return new Message(
                status.getId(),
                status.getUser().getScreenName(),
                parseMessageType(status.getText()),
                getLocation(status.getGeoLocation()),
                status.getInReplyToStatusId() // if status is not a reply -1 (Message.NOT_REPLY) is returned
        );
    }

    private Message getOrderMessage(Status status) {
        return new OrderMessage(
            status.getId(),
            status.getUser().getScreenName(),
            getLocation(status.getGeoLocation()),
            getDestination(status.getText()),
            getFare(status.getText())
        );
    }

    private Location getLocation(GeoLocation geoLocation) {
        if (geoLocation == null) {
            return null;
        } else {
            return new Location(geoLocation.getLatitude(), geoLocation.getLongitude());
        }
    }

    private String getDestination(String tweet) {
        return tweet.split(", ")[1];
    }

    private double getFare(String tweet) {
        return Double.parseDouble(tweet.split(", ")[2]);
    }
}