package ge.edu.freeuni.taxi.twitter;

import ge.edu.freeuni.taxi.core.MessageType;

/**
 * @author Sandro Dolidze
 *
 * Small utility class used for converting twitter messages for message types and vice-versa
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
}