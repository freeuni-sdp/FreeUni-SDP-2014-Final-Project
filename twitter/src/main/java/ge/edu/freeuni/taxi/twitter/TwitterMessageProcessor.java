package ge.edu.freeuni.taxi.twitter;


import ge.edu.freeuni.taxi.core.Message;
import ge.edu.freeuni.taxi.core.MessageProcessor;
import ge.edu.freeuni.taxi.core.MessageListener;
import ge.edu.freeuni.taxi.core.Location;

import twitter4j.TwitterFactory;
import twitter4j.Twitter;
import twitter4j.TwitterStreamFactory;
import twitter4j.TwitterStream;
import twitter4j.StatusUpdate;
import twitter4j.TwitterException;
import twitter4j.FilterQuery;
import twitter4j.Status;
import twitter4j.GeoLocation;
import twitter4j.StatusListener;
import twitter4j.StatusDeletionNotice;
import twitter4j.StallWarning;

/**
 * @author Sandro Dolidze
 *
 * Twitter implementation of MessageProcessor
 */
public class TwitterMessageProcessor extends MessageProcessor {
    private static final String TAG_TO_LISTEN_TO = "#freeunitaxi";

    private final Twitter twitter;
    private final TwitterMessageConverter twitterMessageConverter;

    public static TwitterMessageProcessor getInstance() {
        return new TwitterMessageProcessor(
            new TwitterFactory().getInstance(),
            new TwitterStreamFactory().getInstance(),
            new TwitterMessageConverter()
        );
    }

    private TwitterMessageProcessor(Twitter twitter,
                                    TwitterStream twitterStream,
                                    TwitterMessageConverter twitterMessageConverter) {
        listenToTwitterStream(twitterStream);
        this.twitter = twitter;
        this.twitterMessageConverter = twitterMessageConverter;
    }

    /**
     * BEWARE: message listener is called from another thread
     */
    @Override
    public void addIncomingMessageListener(MessageListener messageListener) {
        super.addIncomingMessageListener(messageListener);
    }

    @Override
    public void sendOutgoingMessage(Message message) {
        try {
            StatusUpdate statusUpdate = getStatusUpdate(message);
            twitter.updateStatus(statusUpdate);
        } catch (RuntimeException | TwitterException e) {
            throw new RuntimeException("couldn't send message: " + message.getMessageType(), e);
        }
    }

    private StatusUpdate getStatusUpdate(Message message) {
        String status = twitterMessageConverter.formatMessageType(message.getMessageType());
        StatusUpdate statusUpdate = new StatusUpdate(status);
        if (message.isReply()) {
            statusUpdate.setInReplyToStatusId(message.getInReplyToMessageId());
        }
        return statusUpdate;
    }

    private void listenToTwitterStream(TwitterStream twitterStream) {
        twitterStream.addListener(statusListener);

        FilterQuery query = new FilterQuery();
        query.track(new String[]{TAG_TO_LISTEN_TO});

        twitterStream.filter(query);
    }

    private Location getLocation(GeoLocation geoLocation) {
        if (geoLocation == null) {
            return null;
        } else {
            return new Location(geoLocation.getLatitude(), geoLocation.getLongitude());
        }
    }

    private Message getMessage(Status status) {
        return new Message(
            status.getId(),
            status.getUser().getScreenName(),
            twitterMessageConverter.parseMessageType(status.getText()),
            getLocation(status.getGeoLocation()),
            status.getInReplyToStatusId() // if status is not a reply -1 is returned
        );
    }

    private void notifyListeners(Status status) {
        try {
            Message message = getMessage(status);
            notifyListeners(message);
        } catch (RuntimeException e) {
            System.out.println("unable to parse tweet: " + status.getText());
        }
    }

    private StatusListener statusListener = new StatusListener() {
        public void onStatus(Status status) { notifyListeners(status); }
        public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}
        public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}
        public void onScrubGeo(long userId, long upToStatusId) {}
        public void onStallWarning(StallWarning warning) {}
        public void onException(Exception ex) {}
    };
}