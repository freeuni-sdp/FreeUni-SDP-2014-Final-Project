package ge.edu.freeuni.taxi.dispatcher;

import java.util.HashMap;
import java.util.Map;

import ge.edu.freeuni.taxi.core.Message;
import ge.edu.freeuni.taxi.core.MessageListener;
import ge.edu.freeuni.taxi.core.MessageProcessor;
import ge.edu.freeuni.taxi.core.MessageType;
import ge.edu.freeuni.taxi.twitter.TwitterMessageProcessor;

/**
 * @author - Giorgi Kochakidze, Sandro Dolidze
 */
public class MessageDispatcher {
    // careful singleton :) bad practice, but unavoidable in this case
    private static final MessageDispatcher instance = new MessageDispatcher(TwitterMessageProcessor.getInstance());

    private final MessageProcessor messageProcessor;
	private final Map<MessageType, IncomingMessageListener> listeners;

    private final MessageListener messageListener = new MessageListener() {
        public void onMessage(Message message) {
        if (listeners.containsKey(message.getMessageType())) {
            listeners.get(message.getMessageType()).onIncomingMessage(message);
        }
        }
    };
	
	public MessageDispatcher(MessageProcessor messageProcessor) {
		this.messageProcessor = messageProcessor;
        this.listeners = new HashMap<MessageType, IncomingMessageListener>();

        System.out.println("safe and sound");

        addListeners();
        messageProcessor.addIncomingMessageListener(messageListener);
        messageProcessor.listenToStream();
    }

    private void addListeners() {
        listeners.put(MessageType.CLIENT_ORDERED, new OrderMessageListener());
    }

    public static void main(String[] args) {
        System.out.println("Are you alive?");
    }
}