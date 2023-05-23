package http.request;

import java.io.*;
import java.net.Socket;


import controller.UserController;
import handler.StaticResourceHandler;
import http.request.factory.HttpRequestFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import http.utils.request.RequestHandlerUtils;

import static java.nio.charset.StandardCharsets.*;

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
                StaticResourceHandler.handleStaticResourceRequest(dos, httpRequest, path);
            } else if (path.equals("/user/create")) {
                UserController.handleSinupRequest(dos, br, httpRequest);
            } else if (path.equals("/user/login")) {
                UserController.handleLoginRequest(dos, br, httpRequest);
            }


        } catch (IOException e) {
            log.error(e.getMessage());
        } catch (RuntimeException e) {
            log.error(e.getMessage());
        }
    }


}
