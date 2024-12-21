package chat.server.session;

import chat.exception.NameDuplicateException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

import static chat.config.ChatConst.*;
import static chat.util.SocketUtil.closeAll;

/**
 * 서버-클라이언트 간 통신을 담당하는 세션 클래스
 * - 각 클라이언트와 독립적으로 메시지를 송수신
 * - 생성 시 `SessionManager`에 자신을 등록
 * - 클라이언트로부터 받은 명령어를 처리하고, 적절한 응답을 전송
 */
@Slf4j
@Getter
public class Session implements Runnable {

    private final Socket socket;
    private final DataInputStream input;
    private final DataOutputStream output;
    private final SessionManager sessionManager = SessionManager.getInstance();
    private boolean closed = false;

    private boolean isFirst = true;
    private boolean joined = false;

    public Session(Socket socket) throws IOException {
        this.socket = socket;
        sessionManager.add(this);
        this.input = new DataInputStream(socket.getInputStream());
        this.output = new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        try {
            while (true) {
                String msg = input.readUTF();

                // 첫 번째 메시지는 이름으로 간주
                if (isFirst) {
                    nameProcess(msg);
                    isFirst = false;
                }

                if (msg.equals(EXIT)) {
                    return;
                }

                if (msg.equals(USERS)) {
                    List<String> names = sessionManager.getAllNames();
                    output.writeUTF(String.valueOf(names));
                }

                if (msg.equals(CHANGE)) {
                    String newName = input.readUTF();
                    nameProcess(newName);
                }

                if (msg.equals(JOIN)) {
                    joined = true;
                    sessionManager.sendToAllUsersJoinMsg(this);

                    chattingMod();
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            sessionManager.clear(this);
            close();
        }
    }

    private void nameProcess(String msg) throws IOException {
        try {
            sessionManager.add(this, msg);
            output.writeInt(OK);
        } catch (NameDuplicateException e) {
            output.writeInt(DUPLICATE_NAME);
        }
    }

    private void chattingMod() {
        while (true) {

        }
    }

    public synchronized void close() {
        if (closed) {
            return;
        }
        closeAll(socket, input, output);
        closed = true;
    }
}