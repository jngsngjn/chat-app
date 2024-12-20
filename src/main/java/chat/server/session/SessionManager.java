package chat.server.session;

import chat.exception.NameDuplicateException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 세션 관리 클래스 (서버 측)
 * - 다중 클라이언트를 지원하기 위해 세션(Session) 객체와 클라이언트 이름을 관리
 * - 싱글톤 패턴으로 구현하여 서버에서 단 하나의 인스턴스만 생성
 */
public class SessionManager {

    private static SessionManager sessionManager;
    private final Map<Session, String> sessionMap = new ConcurrentHashMap<>();

    private SessionManager() {
    }

    public static SessionManager getInstance() {
        if (sessionManager == null) {
            sessionManager = new SessionManager();
        }
        return sessionManager;
    }

    public void add(Session session) {
        sessionMap.put(session, "");
    }

    public void add(Session session, String name) {
        nameDuplicateCheck(name);
        sessionMap.put(session, name);
    }

    public void nameDuplicateCheck(String name) {
        List<String> names = getAllNames();
        for (String s : names) {
            if (s.equals(name)) {
                throw new NameDuplicateException();
            }
        }
    }

    public void clear(Session session) {
        sessionMap.remove(session);
    }

    public void closeAll() {
        for (Session session : sessionMap.keySet()) {
            session.close();
        }
        sessionMap.clear();
    }

    public List<String> getAllNames() {
        return new ArrayList<>(sessionMap.values());
    }
}