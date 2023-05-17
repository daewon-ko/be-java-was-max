package response;

import common.HttpVersion;

import java.util.HashMap;

public class HttpResponseFactory {
    public HttpResponseFactory() {
    }

    public static HttpResponse createOkResponse(byte[] messageBody) {
        return new HttpResponse(new HttpStatusLine(HttpVersion.HTTP, HttpStatusCode.OK),
                new HttpResponseHeader(new HashMap<>()), messageBody);

    }
}
