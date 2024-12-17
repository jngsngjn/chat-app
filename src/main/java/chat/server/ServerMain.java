package chat.server;

import chat.server.session.Session;
import chat.server.session.SessionManager;
import chat.server.shutdown.ShutdownHook;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static chat.config.ChatConst.SERVER_PORT;

/**
 * 클라이언트와 연결 수립 역할만 하는 서버 스레드
 * 연결 수립 시 새로운 스레드를 시작
 */
@Slf4j
public class ServerMain {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(SERVER_PORT);

        ShutdownHook shutdownHook = new ShutdownHook(serverSocket, SessionManager.getInstance());
        Runtime.getRuntime().addShutdownHook(new Thread(shutdownHook, "shutdown"));

        try {
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new Session(socket)).start();
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}