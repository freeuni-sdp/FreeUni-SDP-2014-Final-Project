package ge.edu.freeuni.taxi.core;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Sandro Dolidze
 *
 * Beware this is just an abstract class, not an actual implementation.
 * Reason why this is abstract class, not interface, is becuase it contains
 * some convenience methods which are used by implementing classes.
 */
public abstract class MessageProcessor {
    private List<MessageListener> messageListeners;

    public MessageProcessor() {
        messageListeners = new ArrayList<MessageListener>();
    }

    public void addIncomingMessageListener(MessageListener messageListener) {
        messageListeners.add(messageListener);
    }

    public void removeIncomingMessageListener(MessageListener messageListener) {
        messageListeners.remove(messageListener);
    }

    /**
     * This is a small utility method for only sub-classes to use.
     * If you are not sub-classing MessageProcessor don't use this method.
     * If you need to emit incoming message for testing purposes,
     * use MockMessageProcessor class instead.
     */
    protected void notifyListeners(Message message) {
        for (MessageListener messageListener: messageListeners) {
            messageListener.onMessage(message);
        }
    }

    /**
     * send message via core and return sent message id
     */
    public abstract long sendOutgoingMessage(Message message);

	public abstract void listenToStream();

}
