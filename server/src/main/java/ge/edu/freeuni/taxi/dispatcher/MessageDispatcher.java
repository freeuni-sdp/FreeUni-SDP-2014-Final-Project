package ge.edu.freeuni.taxi.dispatcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ge.edu.freeuni.taxi.core.Message;
import ge.edu.freeuni.taxi.core.MessageListener;
import ge.edu.freeuni.taxi.core.MessageProcessor;
import ge.edu.freeuni.taxi.core.MessageType;
import ge.edu.freeuni.taxi.managers.MessageManager;

public class MessageDispatcher implements MessageListener{
	private List<MessageProcessor> messageProcessors;
	private Map<MessageType, List<MessageManager>> messageManagers;
	
	public MessageDispatcher() {
		messageProcessors = new ArrayList<>();
		messageManagers = new HashMap<>();
	}
	
	public void registerManagerOnMessageType(MessageType messageType, MessageManager messageManager) {
		if (messageManagers.containsKey(messageType)) {
			messageManagers.get(messageType).add(messageManager);
		} else {
			List<MessageManager> list = new ArrayList<>();
			list.add(messageManager);
			messageManagers.put(messageType, list);
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
		for (MessageManager manager : messageManagers.get(message.getMessageType())) {
			System.out.println(manager.toString() + " will serve on message type " + message.getMessageType());
		}
	}
}
