package chat.config;

/**
 * 서버와 클라이언트에서 사용되는 상수 관리 클래스
 * - 명령어, 포트 번호, 메시지 형식 등 공통적으로 사용되는 값을 정의
 * - 변경이 용이하도록 중앙 관리 역할 수행
 */
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

    public static final String ENTER_NAME = "이름을 입력해 주세요: ";

    public static final int OK = 200;
    public static final int DUPLICATE_NAME = 1000;
}