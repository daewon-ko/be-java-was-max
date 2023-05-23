package http.session;


public class SessionFactory {

    public static Session createSession() {
        Session session = new Session();
        session.addSession("sid", String.valueOf(session.getSessionId()));
        session.addSession("max-age", String.valueOf(session.getMaxAge()));
        session.addSession("path", session.getPath());
        return session;
    }

}
