package ge.edu.freeuni.taxi.util;

import ge.edu.freeuni.taxi.core.Message;
import ge.edu.freeuni.taxi.core.MessageListener;
import ge.edu.freeuni.taxi.core.MessageProcessor;

/**
 * @author Sandro Dolidze
 *
 * Because whole application is based on receiving/sending messages,
 * there is no good way to write seperate pieces of application
 * without proper testing environment.
 *
 * Instead of using EasyMock, Mockito or any other mocking tool,
 * I encourage you to use this class instead. Most things are already
 * implemented for you.
 *
 * If you have use-cases where your life would be easier if I added
 * some convenience features, feel free to ask me ;)
 */
public class MockMessageProcessor extends MessageProcessor {
    private MessageListener outgoingMessageListener;

    /**
     * When this method is called, every incomingMessageListeners
     * (registered using addIncomingMessageListener) is triggered.
     */
    public void sendIncomingMessage(Message message) {
        notifyListeners(message);
    }

    /**
     * You can only have one listener at a time.
     * If you call this method twice, second listener will
     * be over-written over the first one.
     */
    public void addOutgoingMessageListener(MessageListener outgoingMessageListener) {
        this.outgoingMessageListener = outgoingMessageListener;
    }

    /**
     * Removes messageListener, so next time sendOutgoingMessage method
     * is called, nothing will happen.
     */
    public void removeOutgoingMessageListener() {
        outgoingMessageListener = null;
    }

    /**
     * As this is simply a testing class, message is not actually sent to the network.
     * Instead, outgoingMessageListener is called if such exists.
     */
    @Override
    public void sendOutgoingMessage(Message message) {
        if (outgoingMessageListener != null) {
            outgoingMessageListener.onMessage(message);
        }
    }

	@Override
	public void listenToStream() {}
}