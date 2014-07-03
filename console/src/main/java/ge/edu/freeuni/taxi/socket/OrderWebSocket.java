package ge.edu.freeuni.taxi.socket;

import ge.edu.freeuni.taxi.PassengerOrder;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by giorgi kochakidze on 7/3/2014.
 * when active orders will be changed
 * Operator will know about it right away via socket
 */
@ServerEndpoint(value = "/socket")
public class OrderWebSocket {
    private static Map<String, Session> map = new HashMap<>();

    @OnOpen
    public synchronized void onOpen(Session session) {
        if (!map.containsKey(session.getId())) {
            map.put(session.getId(), session);
        }
    }

    @OnMessage
    public synchronized String onMessage(String message, Session session) {
        System.out.println(message);
        return message;
    }

    @OnClose
    public synchronized void onClose(Session session, CloseReason closeReason) {
        if (map.containsKey(session.getId())) {
            map.remove(session.getId());
        }
    }

    public void sendOrders(List<PassengerOrder> passengerOrders){
        for (String sessionId: map.keySet()) {
            try {
                map.get(sessionId).getBasicRemote().sendText(passengerOrders.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
