package chat.util;

import chat.server.session.Session;
import chat.server.session.SessionManager;

import java.io.IOException;
import java.time.LocalDateTime;

import static chat.config.ChatConst.TIME_FORMAT;

public class MsgUtil {

    private static final SessionManager sessionManager;

    static {
        sessionManager = SessionManager.getInstance();
    }

    public static void sendJoinMsgToAllUsers(Session session) throws IOException {
        String name = sessionManager.getNameBySession(session);
        String msg = createJoinMsg(name);
        sendToAllUsers(msg);
    }

    public static void sendToAllUsers(String msg) throws IOException {
        for (Session s : sessionManager.getSessionMap().keySet()) {
            if (s.isJoined()) {
                s.getOutput().writeUTF(msg);
            }
        }
    }

    public static void sendMsgToAllUsers(Session session, String msg) throws IOException {
        String name = sessionManager.getNameBySession(session);
        String result = createChatMsg(name, msg);
        sendToAllUsers(result);
    }

    private static String createJoinMsg(String name) {
        return "[" + name + "] 님이 입장했습니다.";
    }

    private static String createChatMsg(String name, String msg) {
        return "[" + TIME_FORMAT.format(LocalDateTime.now()) + "][" + name + "]: " + msg;
    }
}