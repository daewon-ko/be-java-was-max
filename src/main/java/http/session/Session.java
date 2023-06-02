package http.session;


public class Session {

    /**
     * 미션 요구사항에 있는 예시만큼 세션값들 생성.
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

}
