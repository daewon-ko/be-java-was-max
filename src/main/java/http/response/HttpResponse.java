package http.response;

import http.response.component.HttpResponseHeader;
import http.response.component.HttpStatusLine;
import http.session.Session;
import http.session.SessionStore;

import java.util.ArrayList;
import java.util.List;

public class HttpResponse {
    private final HttpStatusLine httpStatusLine;
    private final HttpResponseHeader httpResponseHeader;
    private final byte[] httpMessageBody;

    private SessionStore sessionStore;



    public HttpResponse(final HttpStatusLine httpStatusLine, final HttpResponseHeader httpResponseHeader, final byte[] httpMessageBody) {
        this.httpStatusLine = httpStatusLine;
        this.httpResponseHeader = httpResponseHeader;
        this.httpMessageBody = httpMessageBody;
    }

    public HttpStatusLine getHttpStatusLine() {
        return httpStatusLine;
    }

    public HttpResponseHeader getHttpResponseHeader() {
        return httpResponseHeader;
    }


    public byte[] getHttpMessageBody() {
        return httpMessageBody;
    }


//    public String getSessionCookie() {
//        List<String> sessions = new ArrayList<>();
//        Session session = Session.createDefaultSession();
//        String path = session.getPath();
//        String sessionId = session.getSessionId();
//        sessions.add(sessionId);
//        sessions.add(path);
//
//        StringBuilder sb = new StringBuilder();
//        for (String s : sessions) {
//
//        }
//        StringBuilder sb = new StringBuilder();
//        sb.append("sid=").append(sessionId).append(";")
//
//
//        return sessionId;
//    }



    /**
     * HttpMessagebody는 왜 빠졌을까?
     * 또, httpResponseHeader 이후에 왜 두칸이나 뛸까?
     * @return
     */
    @Override
    public String toString() {

        return String.format("%s\n%s\n\n", httpStatusLine, httpResponseHeader);
    }

    public void addHeader(final String key, final Object value) {
        httpResponseHeader.addHeader(key, value);

    }

}
