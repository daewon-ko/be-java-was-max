package request;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Map;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.utils.HttpRequestUtils;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String HTML_CONTENT_TYPE = "text/html";
    private static final String CSS_CONTENT_TYPE = "text/css";
    private static final String JS_CONTENT_TYPE = "*/*";

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));

            String startLine = br.readLine();
            if (startLine == null) {
                return;
            }

            String url = HttpRequestUtils.getUrl(startLine);
            logger.debug("URL: {} ", url);

            logger.debug(startLine);
            String contentType = HttpRequestUtils.extractContentType(br);


            if (url.startsWith("/user/create")) {
                int index = url.indexOf("?");
                String queryString = url.substring(index + 1);
                Map<String, String> params = HttpRequestUtils.parseQueryString(queryString);
                User user = new User(params.get("userId"), params.get("password"), params.get("name"), params.get("email"));
                Database.addUser(user);
                logger.debug("User : {}", user);
                url = "/index.html";
            }
            final DataOutputStream dos = new DataOutputStream(out);
            String resourcePath = "./src/main/resources";

            switch (contentType) {
                case HTML_CONTENT_TYPE:
                    resourcePath = resourcePath + "/templates";
                    byte[] body = Files.readAllBytes(new File(resourcePath + url).toPath());
                    response200Header(dos, body.length);
                    responseBody(dos, body);
                    break;
                case CSS_CONTENT_TYPE:
                    body = Files.readAllBytes(new File(resourcePath + url).toPath());
                    responseHeaderCss(dos, body.length);
                    responseBody(dos, body);
                    break;
                case JS_CONTENT_TYPE:
                    body = Files.readAllBytes(new File(resourcePath + url).toPath());
                    responseHeaderJs(dos, body.length);
                    responseBody(dos, body);
                    break;
            }

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {

            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseIco(DataOutputStream dos, int lengthOfBodyContent) {
        try {

            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: image/x-icon;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseHeaderCss(DataOutputStream dos, int lengthOfBodyContent) {
        try {

            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/css;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
    private void responseHeaderJs(DataOutputStream dos, int lengthOfBodyContent) {
        try {

            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/javascript;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
