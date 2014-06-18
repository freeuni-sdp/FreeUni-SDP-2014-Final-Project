package ge.edu.freeuni.taxi.twitter;

import twitter4j.*;

/**
 * @author Sandro Dolidze
 *
 * small internal wrapper class, don't use this class externally.
 */
public class TwitterStatusListener implements StatusListener {
    private final TwitterClient.TwitterMessageListener messageListener;

    public TwitterStatusListener(TwitterClient.TwitterMessageListener messageListener) {
        this.messageListener = messageListener;
    }

    public void onStatus(Status status) {
        TwitterMessage twitterMessage = new TwitterMessage(
            status.getText(),
            status.getUser().getScreenName(),
            new Location(status.getGeoLocation().getLatitude(), status.getGeoLocation().getLongitude()),
            status.getCreatedAt()
        );

        messageListener.onTwitterMessage(twitterMessage);
    }

    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}
    public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}
    public void onScrubGeo(long userId, long upToStatusId) {}
    public void onStallWarning(StallWarning warning) {}
    public void onException(Exception ex) {}
}