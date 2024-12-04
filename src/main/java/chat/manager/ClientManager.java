package chat.manager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClientManager {

    private static ClientManager clientManager;
    private final Set<String> nameSet = new HashSet<>();

    private ClientManager() {
    }

    public static ClientManager getInstance() {
        if (clientManager == null) {
            clientManager = new ClientManager();
        }
        return clientManager;
    }

    public void add(String name) {
        nameSet.add(name);
    }

    public void delete(String name) {
        nameSet.remove(name);
    }

    public boolean isUsingName(String name) {
        return nameSet.contains(name);
    }

    public List<String> allUsers() {
        return new ArrayList<>(nameSet);
    }
}