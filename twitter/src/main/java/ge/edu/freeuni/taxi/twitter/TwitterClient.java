package ge.edu.freeuni.taxi.twitter;

import twitter4j.*;

/**
 * @author Sandro Dolidze
 *
 * this class is responsible for all twitter communication.
 *
 * basically you can two things:
 *    listen to incoming tweet for a given hashtag
 *    send tweet
 *
 * since all tweets are public, if user of this class wants to tweet
 * to a particular person, they must mention their name in the tweet.
 *
 * user must be careful, listener is called from another thread.
 */
public class TwitterClient {
    private static final String HASHTAG_TO_LISTEN_TO = "#freeunitaxi";

    private final Twitter twitter;

    public static TwitterClient getInstance(TwitterMessageListener twitterMessageListener) {
        return new TwitterClient(
            new TwitterFactory().getInstance(),
            new TwitterStreamFactory().getInstance(),
            twitterMessageListener
        );
    }

    private TwitterClient(Twitter twitter, TwitterStream twitterStream, TwitterMessageListener messageListener) {
        this.twitter = twitter;

        twitterStream.addListener(new TwitterStatusListener(messageListener));

        FilterQuery query = new FilterQuery();
        query.track(new String[]{HASHTAG_TO_LISTEN_TO});

        twitterStream.filter(query);
    }

    public void sendMessage(String message) {
        if (message == null || message.length() == 0 || message.length() > 140) {
            throw new RuntimeException("message must be non-empty string less than 140 characters");
        }

        try {
            twitter.updateStatus(message);
        } catch(TwitterException e) {
            throw new RuntimeException("unable to tweet", e);
        }
    }

    interface TwitterMessageListener {
        public void onTwitterMessage(TwitterMessage twitterMessage);
    }
}