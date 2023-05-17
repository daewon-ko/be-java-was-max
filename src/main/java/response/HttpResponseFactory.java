package response;

import common.HttpVersion;

import java.util.HashMap;

public class HttpResponseFactory {
    private static final double DEFAULT_HTTP_VERSION = 1.1;

    public HttpResponseFactory() {
    }

    public static HttpResponse createOkResponse(byte[] messageBody) {
        return new HttpResponse(new HttpStatusLine(new HttpVersion(DEFAULT_HTTP_VERSION), HttpStatusCode.OK),
                new HttpResponseHeader(new HashMap<>()), messageBody);

    }
}
