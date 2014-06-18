package ge.edu.freeuni.taxi.twitter;

import java.util.Date;

/**
 * @author Sandro Dolidze
 *
 * Simple entity class for saving Twitter Message.
 * I could have used entity classes provided by Twitter4J,
 * but that would mean we couldn't use other library if needed.
 * Plus, right now twitter is the only message transport, but
 * if we needed to add another, using this class, we could
 * do it without much trouble.
 */
public class TwitterMessage {
    private final String message;
    private final String sender;
    private final Location location;
    private final Date date;

    public TwitterMessage(String message, String sender, Location location, Date date) {
        this.message = message;
        this.sender = sender;
        this.location = location;
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public String getSender() {
        return sender;
    }

    public Location getLocation() {
        return location;
    }

    public Date getDate() {
        return date;
    }
}