package ge.edu.freeuni.taxi.twitter;

import ge.edu.freeuni.taxi.twitter.TwitterClient.TwitterMessageListener;

/**
 * @author Sandro Dolidze
 *
 * small demo
 *    writes a small tweet
 *    listens to all tweets for a given hashtag
 */
public class TwitterDemo {
    public static void main(String[] args) {
        TwitterClient twitterClient = TwitterClient.getInstance(new TwitterMessageListener() {
            public void onTwitterMessage(TwitterMessage twitterMessage) {
                System.out.println(twitterMessage.getMessage());
            }
        });

        // every tweet must be unique, or system will block it
        twitterClient.sendMessage("someone is testing twitter client at " + System.currentTimeMillis());
    }
}