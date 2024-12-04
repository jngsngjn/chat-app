package chat.manager;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {

    private static SessionManager sessionManager;
    private final Map<Socket, String> socketMap = new ConcurrentHashMap<>();

    private SessionManager() {
    }

    public static SessionManager getInstance() {
        if (sessionManager == null) {
            sessionManager = new SessionManager();
        }
        return sessionManager;
    }

    public void add(Socket socket, String name) {
        socketMap.put(socket, name);
    }

    public void delete(Socket socket) {
        socketMap.remove(socket);
    }

    public void deleteAll() {
        socketMap.clear();
    }

    public List<String> findAllUsername() {
        return new ArrayList<>(socketMap.values());
    }
}