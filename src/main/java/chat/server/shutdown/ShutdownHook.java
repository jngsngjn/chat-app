package chat.server.shutdown;

import chat.server.session.SessionManager;
import lombok.extern.slf4j.Slf4j;

import java.net.ServerSocket;

@Slf4j
public class ShutdownHook implements Runnable {

    private final ServerSocket serverSocket;
    private final SessionManager sessionManager;

    public ShutdownHook(ServerSocket serverSocket, SessionManager sessionManager) {
        this.serverSocket = serverSocket;
        this.sessionManager = sessionManager;
    }

    @Override
    public void run() {
        log.debug("shutdownHook START");
        try {
            sessionManager.closeAll();
            serverSocket.close();
            Thread.sleep(1000); // 자원 정리 대기
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        log.debug("shutdownHook END");
    }
}