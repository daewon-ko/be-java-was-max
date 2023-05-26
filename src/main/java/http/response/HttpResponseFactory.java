package http.response;

import common.HttpVersion;
import http.response.component.HttpResponseHeader;
import http.response.component.HttpStatusCode;
import http.response.component.HttpStatusLine;

import java.util.HashMap;

public class HttpResponseFactory {
    public HttpResponseFactory() {
    }

    public static HttpResponse create200OkResponse(byte[] messageBody) {
        return new HttpResponse(new HttpStatusLine(HttpVersion.HTTP, HttpStatusCode.OK),
                new HttpResponseHeader(new HashMap<>()), messageBody);

    }

    public static HttpResponse create302FoundResponse(byte[] messageBody) {
        return new HttpResponse(new HttpStatusLine(HttpVersion.HTTP, HttpStatusCode.FOUND),
                new HttpResponseHeader(new HashMap<>()), messageBody);

    }

}
