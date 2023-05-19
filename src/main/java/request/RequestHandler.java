package request;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import common.ContentType;
import common.HttpVersion;
import request.component.HttpRequestHeader;
import request.component.HttpRequestQueryString;
import request.component.HttpRequestStartLine;
import request.component.HttpRequestTarget;
import request.factory.HttpRequestFactory;
import utils.request.HttpRequestUtils;
import utils.request.RequestHandlerUtils;
import response.HttpResponse;
import response.HttpResponseFactory;
import utils.response.HttpResponseUtils;

import static java.nio.charset.StandardCharsets.*;
import static common.HttpMethod.*;

public class RequestHandler implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            DataOutputStream dos = new DataOutputStream(out);
            BufferedReader br = new BufferedReader(new InputStreamReader(in, UTF_8));
            HttpRequest httpRequest = HttpRequestFactory.createHttpRequest(br);
            log.debug("httpRequest: {}", httpRequest);
            String path = httpRequest.getPath();

            if (RequestHandlerUtils.isStaticResource(path)) {
                byte[] messageBody = RequestHandlerUtils.readFile(httpRequest.getRequestStartLine());
                ContentType contentType = ContentType.of(path);
                sendHttp200Response(dos, messageBody, contentType);
            } else if (path.equals("/user/create")) {

                HttpRequestQueryString queryString = httpRequest.getQueryString();
                HttpRequestTarget httpRequestTarget = new HttpRequestTarget("/index.html", queryString);
                HttpRequestStartLine httpRequestStartLine = new HttpRequestStartLine(POST, httpRequestTarget, HttpVersion.HTTP);
                log.debug("ContentLength: {}", httpRequest.getRequestHeader().getHeader("Content-Length"));
                String requestBodyLength = httpRequest.getRequestHeader().getHeader("Content-Length");

                String requestBody = RequestHandlerUtils.getRequestBody(br, requestBodyLength);

                Map<String, String> param = HttpRequestUtils.parseQueryString(requestBody);
                queryString.add(param);
                RequestHandlerUtils.requestSingUp(queryString);

                httpRequest = new HttpRequest(httpRequestStartLine, new HttpRequestHeader(new HashMap<>()));
                HttpRequestStartLine requestStartLine = httpRequest.getRequestStartLine();
                byte[] messageBody = RequestHandlerUtils.readFile(requestStartLine);
                sendHttp302Response(dos, messageBody);
            }


        } catch (IOException e) {
            log.error(e.getMessage());
        }
//        catch (URISyntaxException e) {
//            throw new RuntimeException(e);
//        }
    }

    private static void sendHttp200Response(final DataOutputStream dos, final byte[] messageBody, final ContentType contentType) {
        HttpResponse httpResponse = HttpResponseFactory.create200OkResponse(messageBody);
        httpResponse.addHeader("Content-Type", contentType);
        httpResponse.addHeader("Content-Length", String.valueOf(httpResponse.getHttpMessageBody().length));
        HttpResponseUtils.responseHeader(dos, httpResponse);
        HttpResponseUtils.responseBody(dos, httpResponse);
        log.debug("httpResponse: {}", httpResponse);
    }

    // TODO : Location의 value값을 /index.html로 설정하는게 맞을까? 아니면 localhost:8080/index.html로 설정하는게 올바를까?
    private static void sendHttp302Response(final DataOutputStream dos, final byte[] messageBody) {
        HttpResponse httpResponse = HttpResponseFactory.create302FoundResponse(messageBody);
        httpResponse.addHeader("Location", "/index.html");
        HttpResponseUtils.responseHeader(dos, httpResponse);
        HttpResponseUtils.responseBody(dos, httpResponse);
        log.debug("httpResponse: {}", httpResponse);
    }


    //todo: URISyntaxException에 대해 학습
    //TODO : /user/create로 들어간 path를 /user/login.html로 바꾸는 방법은 없을까?
    private byte[] handleHttpRequest(HttpRequest httpRequest) throws URISyntaxException {
        String path = httpRequest.getPath();
        HttpRequestQueryString queryString = httpRequest.getQueryString();
        if (RequestHandlerUtils.isStaticResource(path)) {
            return RequestHandlerUtils.readFile(httpRequest.getRequestStartLine());
        } else if (path.equals("/user/create")) {
            RequestHandlerUtils.requestSingUp(queryString);
            HttpRequestQueryString httpRequestQueryString = new HttpRequestQueryString(new HashMap<>());
            HttpRequestTarget httpRequestTarget = new HttpRequestTarget("/user/login.html", httpRequestQueryString);
            HttpRequestStartLine httpRequestStartLine = new HttpRequestStartLine(POST, httpRequestTarget, HttpVersion.HTTP);
            httpRequest = new HttpRequest(httpRequestStartLine, new HttpRequestHeader(new HashMap<>()));
            HttpRequestStartLine requestStartLine = httpRequest.getRequestStartLine();
            return RequestHandlerUtils.readFile(requestStartLine);
        }
        return new byte[0];
    }


}
