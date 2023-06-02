package http.request;

import http.request.component.HttpRequestHeader;
import http.request.component.HttpRequestQueryString;
import http.request.component.HttpRequestStartLine;
import http.session.Cookie;

import java.util.Optional;

public class HttpRequest {
    /*
    starLine
    header
    eof
    body
     */

    private final HttpRequestStartLine requestStartLine;
    private final HttpRequestHeader requestHeader;

    private Cookie cookie;


//    public HttpRequest(final HttpRequestStartLine requestStartLine, final HttpRequestHeader requestHeader, final Cookie cookie) {
//        this.requestStartLine = requestStartLine;
//        this.requestHeader = requestHeader;
//        this.cookie = cookie;
//    }

    public HttpRequest(final HttpRequestStartLine requestStartLine, final HttpRequestHeader requestHeader) {
        this.requestStartLine = requestStartLine;
        this.requestHeader = requestHeader;
    }

    public HttpRequestStartLine getRequestStartLine() {
        return requestStartLine;
    }

    public HttpRequestHeader getRequestHeader() {
        return requestHeader;
    }

    public String getPath() {
        return requestStartLine.getHttpRequestURI().getPath();
    }

    public HttpRequestQueryString getQueryString() {
        return requestStartLine.getHttpRequestURI().getQueryString();
    }


    /**
     * sid 숫자를 입력하면 Map의 형태로 requestHeader에 저장해두었다가 꺼내주는 메서드
     * null값이 나올 수 있기에 Optional로 null 방지.
     */

    public Optional<Cookie> getCookie(String sid) {
        Object cookie = requestHeader.getHeader(sid);
        return Optional.ofNullable((Cookie)cookie);
    }


    public void setCookie(final Cookie cookie) {
        this.cookie = cookie;
    }

    @Override
    public String toString() {
        return String.format("%s\n%s", requestStartLine, requestHeader);
    }
}
