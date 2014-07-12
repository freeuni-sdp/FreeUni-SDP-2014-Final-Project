package ge.edu.freeuni.taxi.twitter;

import ge.edu.freeuni.taxi.core.Message;
import ge.edu.freeuni.taxi.core.MessageProcessor;
import ge.edu.freeuni.taxi.core.MessageListener;

import twitter4j.TwitterFactory;
import twitter4j.Twitter;
import twitter4j.TwitterStreamFactory;
import twitter4j.TwitterStream;
import twitter4j.StatusUpdate;
import twitter4j.TwitterException;
import twitter4j.FilterQuery;
import twitter4j.Status;
import twitter4j.StatusListener;
import twitter4j.StatusDeletionNotice;
import twitter4j.StallWarning;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
* @author Sandro Dolidze
*
* Twitter implementation of MessageProcessor
*/
public class TwitterMessageProcessor extends MessageProcessor {
    private static final String TAG_TO_LISTEN_TO = "#freeunitaxi";

	private Logger logger = LoggerFactory.getLogger(TwitterMessageProcessor.class);

    private final Twitter twitter;
    private final TwitterMessageConverter twitterMessageConverter;
    private final TwitterStream twitterStream;

    public static TwitterMessageProcessor getInstance() {
        ConfigurationBuilder cb = new ConfigurationBuilder()
            .setDebugEnabled(true)
            .setOAuthConsumerKey("uDoPhuBiirlVF2iv4jQQsRdGf")
            .setOAuthConsumerSecret("h91vj6DSNHxDVIlWgawtsOJiWavKClMtGgklDI6HIXxGRg3d7l")
            .setOAuthAccessToken("2567184752-TrDVTX1jGnlZAdPKErYfWSYZxjEIvTdJvnv3J3r")
            .setOAuthAccessTokenSecret("ZfTTPzKBqKz4EPxlF47PS7OBzqBMu5gCwGT2UxzFt6ykt");

        return new TwitterMessageProcessor(
            new TwitterFactory().getInstance(),
            new TwitterStreamFactory(cb.build()).getInstance(),
            new TwitterMessageConverter()
        );
    }

    private TwitterMessageProcessor(Twitter twitter,
                                    TwitterStream twitterStream,
                                    TwitterMessageConverter twitterMessageConverter) {
        this.twitterStream = twitterStream;
        this.twitter = twitter;
        this.twitterMessageConverter = twitterMessageConverter;
    }

    /**
     * BEWARE: message listener is called from another thread.
     * same thread is used for every status update, so don't block it for too long.
     */
    @Override
    public void addIncomingMessageListener(MessageListener messageListener) {
        super.addIncomingMessageListener(messageListener);
    }

    /**
     * sends given message with tweeter and returns tweet id
     */
    @Override
    public long sendOutgoingMessage(Message message) {
        try {
            StatusUpdate statusUpdate = getStatusUpdate(message);
            Status status = twitter.updateStatus(statusUpdate);
            return status.getId();
        } catch (RuntimeException | TwitterException e) {
			logger.error("couldn't send message: " + message.getMessageType(), e);
            throw new RuntimeException("couldn't send message: " + message.getMessageType(), e);
        }
    }

    @Override
    public void listenToStream() {
        twitterStream.addListener(statusListener);

        FilterQuery query = new FilterQuery();
        query.track(new String[]{TAG_TO_LISTEN_TO});

        twitterStream.filter(query);
    }

    private StatusUpdate getStatusUpdate(Message message) {
        String status = twitterMessageConverter.formatMessageType(message.getMessageType());
        StatusUpdate statusUpdate = new StatusUpdate(status);
        if (message.isReply()) {
            statusUpdate.setInReplyToStatusId(message.getInReplyToMessageId());
        }
        return statusUpdate;
    }

    private void notifyListeners(Status status) {
        try {
            Message message = twitterMessageConverter.parseMessage(status);
            notifyListeners(message);
        } catch (RuntimeException e) {
            logger.warn("unable to parse tweet: " + status.getText(), e);
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