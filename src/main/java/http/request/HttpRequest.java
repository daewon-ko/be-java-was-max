package http.request;

import http.request.component.HttpRequestHeader;
import http.request.component.HttpRequestQueryString;
import http.request.component.HttpRequestStartLine;

public class HttpRequest {
    /*
    starLine
    header
    eof
    body
     */

    private final HttpRequestStartLine requestStartLine;
    private final HttpRequestHeader requestHeader;

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

    @Override
    public String toString() {
        return String.format("%s\n%s", requestStartLine, requestHeader);
    }
}
