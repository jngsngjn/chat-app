package chat;

import chat.manager.SessionManager;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

import static chat.config.ChatConst.USERS;

/**
 * 서버-클라이언트 간 통신을 담당하는 세션 클래스
 * - 각 클라이언트와 독립적으로 메시지를 송수신
 * - 생성 시 `SessionManager`에 자신을 등록
 * - 클라이언트로부터 받은 명령어를 처리하고, 적절한 응답을 전송
 */
public class Session implements Runnable {

    private final Socket socket;
    private final DataInputStream input;
    private final DataOutputStream output;
    private final SessionManager sessionManager = SessionManager.getInstance();

    private boolean isFirst = true;

    public Session(Socket socket) throws IOException {
        this.socket = socket;
        sessionManager.add(this);
        this.input = new DataInputStream(socket.getInputStream());
        this.output = new DataOutputStream(socket.getOutputStream());
    }

    @SneakyThrows
    @Override
    public void run() {
        while (true) {
            String msg = input.readUTF();

            // 첫 번째 메시지는 이름으로 간주
            // TODO 이름 중복 검사해야 함
            if (isFirst) {
                sessionManager.add(this, msg);
                isFirst = false;
            }

            if (msg.equals(USERS)) {
                List<String> names = sessionManager.getAllNames();

                // 요청한 클라이언트에게만 사용자 목록 전송
                output.writeUTF(String.valueOf(names));
            }
        }
    }
}