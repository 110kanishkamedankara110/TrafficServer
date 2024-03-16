package web.util;

import jakarta.websocket.Session;

import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class ServerClients {
    private static final List<Session> clients=new LinkedList<>();

    public static List<Session> getClients() {
        return clients;
    }

    public static void setClient(Session client) {
        ServerClients.clients.add(client);
    }
}
