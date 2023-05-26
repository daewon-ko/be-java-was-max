package http.response.component;

import common.HttpVersion;

public class HttpStatusLine {
    /**
     * 여기서 멤버변수에 final을 붙이고 안 붙이고의 차이는 무엇이 있을까?
     * 불변성을 보장해주는 차원에서 붙인다고 하면 만약 아래 변수들이 변화될 가능성은 없을까?
     *
     */
    private final HttpVersion httpVersion;
    private final HttpStatusCode httpStatusCode;

    public HttpStatusLine(final HttpVersion httpVersion, final HttpStatusCode httpStatusCode) {
        this.httpVersion = httpVersion;
        this.httpStatusCode = httpStatusCode;
    }



    public HttpVersion getHttpVersion() {
        return httpVersion;
    }

    public HttpStatusCode getHttpStatusCode() {
        return httpStatusCode;
    }

//    @Override
//    public String toString() {
//        return String.format("%s %s, httpVersion, httpStatusCode");
//    }

    @Override
    public String toString() {
        return String.format("%s %s", httpVersion, httpStatusCode);
    }
}
