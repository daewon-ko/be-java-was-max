package request.component;

import common.HttpMethod;
import common.HttpVersion;

public class HttpRequestStartLine {
    private final HttpMethod httpMethod;
    private final HttpRequestURI httpRequestURI;
    private final HttpVersion httpVersion;

    public HttpRequestStartLine(final HttpMethod httpMethod, final HttpRequestURI httpRequestURI, final HttpVersion httpVersion) {
        this.httpMethod = httpMethod;
        this.httpRequestURI = httpRequestURI;
        this.httpVersion = httpVersion;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public HttpRequestURI getHttpRequestURI() {
        return httpRequestURI;
    }

    public HttpVersion getHttpVersion() {
        return httpVersion;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", httpMethod, httpRequestURI, httpVersion);
    }
}
