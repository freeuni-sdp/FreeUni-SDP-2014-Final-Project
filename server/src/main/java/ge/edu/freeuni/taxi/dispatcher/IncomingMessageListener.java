package ge.edu.freeuni.taxi.dispatcher;

import ge.edu.freeuni.taxi.core.Message;

/**
 * 
 * @author giorgi kochakidze
 *
 */
public interface IncomingMessageListener {
	
	public void onIncomingMessage(Message message);
}
