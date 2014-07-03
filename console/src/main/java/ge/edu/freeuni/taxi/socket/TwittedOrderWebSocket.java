package ge.edu.freeuni.taxi.socket;

import ge.edu.freeuni.taxi.PassengerOrder;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
/**
 * Created by giorgi kochakidze on 7/3/2014.
 * if driver have been assigned to order
 * via twitter Operator will be notified right away
 */
@ServerEndpoint(value = "/api/orders/new/twitter")
public class TwittedOrderWebSocket {
    private static Map<String, Session> map = new HashMap<>();

    @OnOpen
    public synchronized void onOpen(Session session) {
        if (!map.containsKey(session.getId())) {
            map.put(session.getId(), session);
        }
    }

    @OnMessage
    public synchronized String onMessage(String message) {
        return message;
    }

    @OnClose
    public synchronized void onClose(Session session) {
        if (map.containsKey(session.getId())) {
            map.remove(session.getId());
        }
    }

    public void sendOrder(PassengerOrder passengerOrder){
        for (String sessionId: map.keySet())
            try {
                map.get(sessionId).getBasicRemote().sendText(passengerOrder.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
