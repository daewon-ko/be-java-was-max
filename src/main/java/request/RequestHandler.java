package request;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import common.ContentType;
import common.HttpVersion;
import request.component.HttpRequestHeader;
import request.component.HttpRequestQueryString;
import request.component.HttpRequestStartLine;
import request.component.HttpRequestURI;
import request.factory.HttpRequestFactory;
import request.utils.RequestHandlerUtils;
import response.HttpResponse;
import response.HttpResponseFactory;

import static java.nio.charset.StandardCharsets.*;
import static common.HttpMethod.*;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            DataOutputStream dos = new DataOutputStream(out);
            BufferedReader br = new BufferedReader(new InputStreamReader(in, UTF_8));
            HttpRequest httpRequest = HttpRequestFactory.createHttpRequest(br);


            byte[] messageBody = handleHttpRequest(httpRequest);
            HttpResponse httpResponse = HttpResponseFactory.createOkResponse(messageBody);
            ContentType contentType =ContentType.of(httpRequest.getRequestStartLine().getHttpRequestURI().getPath()) ;
            httpResponse.addHeader("Content-Type", contentType);
            httpResponse.addHeader("Content-Length", String.valueOf(httpResponse.getHttpMessageBody().length));


            response200Header(dos, httpResponse);
            responseBody(dos, httpResponse);

            logger.debug("httpRequest: {}", httpRequest);
            logger.debug("httpResponse : {}", httpResponse);

        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    //todo: URISyntaxException에 대해 학습
    //TODO : 왜 QueryString은 ?password=1234&name=1234&userId=1234&email=1234%401234와 같은 형식으로 들어갈까? 또 그렇게 들억가도 문제는 없을까?
    //TODO : /user/create로 들어간 path를 /user/login.html로 바꾸는 방법은 없을까?
    private byte[] handleHttpRequest(HttpRequest httpRequest) throws URISyntaxException {
        String path = httpRequest.getRequestStartLine().getHttpRequestURI().getPath();
        HttpRequestQueryString queryString = httpRequest.getRequestStartLine().getHttpRequestURI().getQueryString();
        if (RequestHandlerUtils.isStaticResource(path)) {
            return RequestHandlerUtils.readFile(httpRequest.getRequestStartLine());
        } else if (path.equals("/user/create")) {
            RequestHandlerUtils.requestSingUp(queryString);
            HttpRequestQueryString httpRequestQueryString = new HttpRequestQueryString(new HashMap<>());
            HttpRequestURI httpRequestURI = new HttpRequestURI("/user/login.html", httpRequestQueryString);
            HttpRequestStartLine httpRequestStartLine = new HttpRequestStartLine(GET, httpRequestURI, new HttpVersion(1.1));
            httpRequest = new HttpRequest(httpRequestStartLine, new HttpRequestHeader(new HashMap<>()));
            HttpRequestStartLine requestStartLine = httpRequest.getRequestStartLine();
            path = requestStartLine.getHttpRequestURI().getPath();
            return RequestHandlerUtils.readFile(requestStartLine);
        }
        return new byte[0];
    }

    private void response200Header(DataOutputStream dos, HttpResponse httpResponse) {
        try {
            dos.writeBytes(httpResponse.toString());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, HttpResponse httpResponse) {
        try {
            byte[] messageBody = httpResponse.getHttpMessageBody();
            dos.write(messageBody, 0, messageBody.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
