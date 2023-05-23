package handler;

import common.ContentType;
import http.request.HttpRequest;
import http.utils.request.RequestHandlerUtils;

import java.io.DataOutputStream;

import static http.utils.response.HttpResponseUtils.sendHttp200Response;

public class StaticResourceHandler {

    public static void handleStaticResourceRequest(final DataOutputStream dos, final HttpRequest httpRequest, final String path) {
        byte[] messageBody = RequestHandlerUtils.readFile(httpRequest.getRequestStartLine());
        ContentType contentType = ContentType.of(path);
        sendHttp200Response(dos, messageBody, contentType);
    }
}
