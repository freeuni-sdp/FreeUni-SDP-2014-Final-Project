package ge.edu.freeuni.taxi.demo;

import ge.edu.freeuni.taxi.core.Message;
import ge.edu.freeuni.taxi.core.MessageListener;
import ge.edu.freeuni.taxi.core.MessageType;
import ge.edu.freeuni.taxi.twitter.TwitterMessageProcessor;
import ge.edu.freeuni.taxi.util.MockMessageProcessor;

/**
 * @author Sandro Dolidze
 */
public class TwitterMessageProcessorDemo {
    public static void main(String[] args) {
        //testTwitter();
        testMock();
    }

    public static void testTwitter() {
        final TwitterMessageProcessor twitterMessageProcessor = TwitterMessageProcessor.getInstance();
        twitterMessageProcessor.listenToStream();
        twitterMessageProcessor.addIncomingMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                System.out.println("incoming: " + message);
            }
        });

        twitterMessageProcessor.sendOutgoingMessage(new Message(MessageType.CLIENT_CANCELED));
    }

    public static void testMock() {
        MockMessageProcessor mockMessageProcessor = new MockMessageProcessor();

        mockMessageProcessor.addIncomingMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                System.out.println("mock incoming: " + message);
            }
        });
        mockMessageProcessor.sendIncomingMessage(new Message(MessageType.CLIENT_CANCELED));


        mockMessageProcessor.addOutgoingMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                System.out.println("mock outgoing: " + message);
            }
        });
        mockMessageProcessor.sendOutgoingMessage(new Message(MessageType.DRIVER_DELIVERED_CLIENT));
    }
}