package http.session;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Session {
    /*
    미션 자체만 구현(o) -> 스프링처럼 구현
     */


    private final String sessionId;
    private final String path;
    private final int maxAge;

    private Session(final String sessionId, final String path) {
        this.sessionId = sessionId;
        this.path = path;
        this.maxAge = 1000;
    }


    public String getPath() {
        return path;
    }


    public String getSessionId() {
        return sessionId;
    }


    /*
    생성자만 쓰면 가독성이 떨어지나,static으로 밖에 생성자를 사용하지 못한다.


     */
    public static Session createDefaultSession(String sessionId) {
        String path = "/";
        return new Session(sessionId, path);
    }




    @Override
    public String toString() {
        return String.format("sid=%s; path=%s; max-age=%s", sessionId, path, maxAge);
    }

    //    @Override
//    public String toString() {
//            StringBuilder sb = new StringBuilder();
//            for (String cookie : session.keySet()) {
//                String value = session.get(cookie);
//                sb.append(cookie).append("=").append(value).append(";");
//            }
//            return String.join("; ", sb.toString().split(";"));
//
//    }
}
