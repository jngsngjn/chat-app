package chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static chat.config.ChatConst.SERVER_PORT;

public class ServerMain {

    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new Session(socket)).start();
            }
        }
    }
}