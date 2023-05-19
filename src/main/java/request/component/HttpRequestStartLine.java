package request.component;

import common.HttpMethod;
import common.HttpVersion;

public class HttpRequestStartLine {
    private final HttpMethod httpMethod;
    private final HttpRequestTarget httpRequestTarget;
    private final HttpVersion httpVersion;

    public HttpRequestStartLine(final HttpMethod httpMethod, final HttpRequestTarget httpRequestTarget, final HttpVersion httpVersion) {
        this.httpMethod = httpMethod;
        this.httpRequestTarget = httpRequestTarget;
        this.httpVersion = httpVersion;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public HttpRequestTarget getHttpRequestURI() {
        return httpRequestTarget;
    }

    public HttpVersion getHttpVersion() {
        return httpVersion;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", httpMethod, httpRequestTarget, httpVersion);
    }
}
