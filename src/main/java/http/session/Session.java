package http.session;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Session {
    private Map<String, String> session = new HashMap<>();


    private final UUID sessionId;
    private final Long maxAge;
    private final String path;


    public Session() {

        this.sessionId = UUID.randomUUID();
        this.maxAge = (long) (Math.random() * 50) + 1;
        this.path = "/";

    }

    public Map<String, String> getSession() {
        return session;
    }

    public void addSession(String name, String value) {
        session.put(name, value);
    }

    public Long getMaxAge() {
        return maxAge;
    }

    public String getPath() {
        return path;
    }


    public UUID getSessionId() {
        return sessionId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String cookie : session.keySet()) {
            String value = session.get(cookie);
            sb.append(cookie).append("=").append(value).append(";");
        }
        return String.join("; ", sb.toString().split(";"));
    }
}
