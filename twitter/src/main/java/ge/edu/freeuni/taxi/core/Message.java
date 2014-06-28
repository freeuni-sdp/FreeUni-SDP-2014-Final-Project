package ge.edu.freeuni.taxi.core;

/**
 * @author Sandro Dolidze
 */
public class Message {
    private static final long NOT_REPLY = -1;

    private final long id;
    private final String sender;
    private final MessageType messageType;
    private final Location location;
    private final long inReplyToMessageId;

    public Message(long id, String sender, MessageType messageType, Location location, long inReplyToMessageId) {
        this.id = id;
        this.sender = sender;
        this.messageType = messageType;
        this.location = location;
        this.inReplyToMessageId = inReplyToMessageId;
    }

    public Message(MessageType messageType) {
        this(-1, null, messageType, null, -1);
    }

    public long getId() {
        return id;
    }

    public String getSender() {
        return sender;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public Location getLocation() {
        return location;
    }

    public boolean isReply() {
        return inReplyToMessageId != NOT_REPLY;
    }

    public long getInReplyToMessageId() {
        return inReplyToMessageId;
    }

    @Override
    public String toString() {
        return  "{id=" + id + "," +
                "sender=" + sender + "," +
                "messageType=" + messageType + "," +
                "location=" + location + "," +
                "inReplyToMessageId="+ inReplyToMessageId + "}";
    }
}