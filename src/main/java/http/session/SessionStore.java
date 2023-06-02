package http.session;


import model.User;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class SessionStore {

    /**
     * SessionID(UUID로 생성한 String 값)을 KEY, UserID를 Value값으로 하는 MAP
      * @param 생성한
     */

    private static Map<String, String> sessionStore = new ConcurrentHashMap<>();


    public static Optional<String> findSession(String sid) {
        return Optional.ofNullable(sessionStore.get(sid));
    }

    /*
    정적 팩토리 메서드
    -> class 내부에서 static으로 선언해서 생성자 대신 메서드로 인스턴스 생성하는 것?
    생성자가 많을 때 헷갈리 수 있으니? 용도에 따라 의미전달을 해줄 수 있다.

    -> @Builder 와의 차이는 ? //
    -> 그떄 그때 사용하는 사람이 만들 수 있다. 어떤 걸 추가할 지 결정한다. 사용자가.
    하나의 메서드에
     */

    public static Session storeSession(String sid, String userId) {
        sessionStore.put(sid, userId);
        return Session.createDefaultSession(sid);
    }



}
