package config;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class WebSocketConfig {
    private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<>());

    public static void addSession(Session session) {
        sessions.add(session);
    }

    public static void removeSession(Session session) {
        sessions.remove(session);
    }

    public static void broadcast(String message) {
        synchronized (sessions) {
            for (Session session : sessions) {
                try {
                    if (session.isOpen()) {
                        session.getBasicRemote().sendText(message);
                    }
                } catch (IOException e) {
                    System.err.println("Error broadcasting message: " + e.getMessage());
                }
            }
        }
    }

    public static int getConnectedClients() {
        return sessions.size();
    }
}