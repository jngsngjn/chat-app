package chat.config;

public abstract class ChatConst {
    public static final String HOST = "localhost";
    public static final int SERVER_PORT = 45678;

    public static final String JOIN = "/join";
    public static final String MSG = "/msg";
    public static final String CHANGE = "/change";
    public static final String USERS = "/users";
    public static final String EXIT = "/exit";

    public static final String WELCOME_MSG = ("입장: " + JOIN + " {name}\n"
            + "메시지: " + MSG + " {content}\n"
            + "이름 변경: " + CHANGE + " {name}\n"
            + "전체 사용자: " + USERS + "\n"
            + "종료: " + EXIT).trim();
}