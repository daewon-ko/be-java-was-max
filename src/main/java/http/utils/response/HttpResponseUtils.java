package http.utils.response;

import common.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import http.response.HttpResponse;
import http.response.HttpResponseFactory;
import http.session.Session;

import java.io.DataOutputStream;
import java.io.IOException;

public class HttpResponseUtils {
    private static Logger log = LoggerFactory.getLogger(HttpResponseUtils.class);

    public static void responseHeader(DataOutputStream dos, HttpResponse httpResponse) {
        try {
            dos.writeBytes(httpResponse.toString());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public static void responseBody(DataOutputStream dos, HttpResponse httpResponse) {
        try {
            byte[] messageBody = httpResponse.getHttpMessageBody();
            dos.write(messageBody, 0, messageBody.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public static void sendHttp200Response(final DataOutputStream dos, final byte[] messageBody, final ContentType contentType) {
        HttpResponse httpResponse = HttpResponseFactory.create200OkResponse(messageBody);
        httpResponse.addHeader("Content-Type", contentType);
        httpResponse.addHeader("Content-Length", String.valueOf(httpResponse.getHttpMessageBody().length));
        HttpResponseUtils.responseHeader(dos, httpResponse);
        HttpResponseUtils.responseBody(dos, httpResponse);
        log.debug("httpResponse: {}", httpResponse);
    }


    // TODO : Location의 value값을 /index.html로 설정하는게 맞을까? 아니면 localhost:8080/index.html로 설정하는게 올바를까?
    // 일단 작동은 잘 한다. 그러나 원리는 아직은 잘 모르겠음.
    public static void sendHttp302Response(final DataOutputStream dos, final byte[] messageBody, String redirect) {
        HttpResponse httpResponse = HttpResponseFactory.create302FoundResponse(messageBody);
        httpResponse.addHeader("Location", redirect);
        HttpResponseUtils.responseHeader(dos, httpResponse);
        HttpResponseUtils.responseBody(dos, httpResponse);
        log.debug("httpResponse: {}", httpResponse);
    }

    public static void sendHttp302ResponseBasicHomeUsingSession(final DataOutputStream dos, final byte[] messageBody, Session session) {
        HttpResponse httpResponse = HttpResponseFactory.create302FoundResponse(messageBody);
        httpResponse.addHeader("Location", "/index.html");
        httpResponse.addHeader("Set-cookie", session);
        HttpResponseUtils.responseHeader(dos, httpResponse);
        HttpResponseUtils.responseBody(dos, httpResponse);
        log.debug("httpResponse: {}", httpResponse);
    }



}
