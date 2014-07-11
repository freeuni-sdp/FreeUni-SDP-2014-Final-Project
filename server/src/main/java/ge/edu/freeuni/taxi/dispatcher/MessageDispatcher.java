package ge.edu.freeuni.taxi.dispatcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ge.edu.freeuni.taxi.core.Message;
import ge.edu.freeuni.taxi.core.MessageListener;
import ge.edu.freeuni.taxi.core.MessageProcessor;
import ge.edu.freeuni.taxi.core.MessageType;

public class MessageDispatcher implements MessageListener {
	private List<MessageProcessor> messageProcessors;
	private Map<MessageType, List<IncomingMessageListener>> incomingMessageListeners;
	
	public MessageDispatcher() {
		messageProcessors = new ArrayList<>();
		incomingMessageListeners = new HashMap<>();
	}
	
	public void registerManagerOnMessageType(MessageType messageType, IncomingMessageListener incomingMessageListener) {
		if (incomingMessageListeners.containsKey(messageType)) {
			incomingMessageListeners.get(messageType).add(incomingMessageListener);
		} else {
			List<IncomingMessageListener> list = new ArrayList<>();
			list.add(incomingMessageListener);
			incomingMessageListeners.put(messageType, list);
		}
	}

	public void addMessageProcessor(MessageProcessor messageProcessor) {
		messageProcessors.add(messageProcessor);
	}
	
	public void removeMessageProcessor(MessageProcessor messageProcessor) {
		messageProcessors.remove(messageProcessor);
	}
	
	public void startListeningMessages() {
		for (MessageProcessor messageProcessor : messageProcessors) {
			messageProcessor.listenToStream();
		}
	}

	@Override
	public void onMessage(Message message) {
		for (IncomingMessageListener incomingMessageListener : incomingMessageListeners.get(message.getMessageType())) {
			incomingMessageListener.onIncomingMessage(message);
		}
	}
}
