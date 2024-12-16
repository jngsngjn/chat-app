package chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static chat.config.ChatConst.SERVER_PORT;

/**
 * 클라이언트와 연결 수립 역할만 하는 서버 스레드
 * 연결 수립 시 새로운 스레드를 시작
 */
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