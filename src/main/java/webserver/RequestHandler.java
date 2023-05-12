package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Map;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HttpRequestUtils;

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
            String contentType = extractContentType(br);

            if (url.startsWith("/user/create")) {
                int index = url.indexOf("?");
                String queryString = url.substring(index + 1);
                Map<String, String> params = HttpRequestUtils.parseQueryString(queryString);
                User user = new User(params.get("userId"), params.get("password"), params.get("name"), params.get("email"));
                Database.addUser(user);
                logger.debug("User : {}", user);
                url = "/index.html";

            }
            if (contentType.equals(HTML_CONTENT_TYPE)) {
                DataOutputStream dos = new DataOutputStream(out);
                byte[] body = Files.readAllBytes(new File("./src/main/resources/templates" + url).toPath());
                response200Header(dos, body.length);
                responseBody(dos, body);

            } else if (contentType.equals(CSS_CONTENT_TYPE)) {
                DataOutputStream dos = new DataOutputStream(out);
                byte[] body = Files.readAllBytes(new File("./src/main/resources" + url).toPath());
                responseHeaderCss(dos, body.length);
                responseBody(dos, body);
            } else {
                DataOutputStream dos = new DataOutputStream(out);
                byte[] body = Files.readAllBytes(new File("./src/main/resources/" + url).toPath());
                responseHeaderJs(dos, body.length);
                responseBody(dos, body);
            }


        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private static String extractContentType(final BufferedReader br) throws IOException {
        String contentType = "";
        String line = br.readLine();
        while (line != null && !line.isEmpty()) {
            if (line.startsWith("Accept: ")) {
                String[] parts = line.split("[:,;]");
                String wanted = parts[1].trim();
                contentType = wanted;
            }
            line = br.readLine();
            logger.debug("headerLine: {} ", line);
        }
        logger.debug("Content-Type: {}", contentType);
        return contentType;
    }

    private void sendHttpResponse(final OutputStream out, final String url) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        byte[] body = Files.readAllBytes(new File("./src/main/resources/" + url).toPath());
        if (url.endsWith("html")) {
            response200Header(dos, body.length);
        } else if (url.startsWith("css")) {
            responseHeaderCss(dos, body.length);
        } else if (url.startsWith("js")) {
            responseHeaderJs(dos, body.length);
        }
        responseBody(dos, body);
    }

//    private static void searchContentType(final Map<String, String> headers, final String line) {
//        // Content-Type을 기준으로 Split
//        if (line.startsWith("Accept: ")) {
//            String[] splited = line.split("Accept: ");
//        }
//        String[] parts = line.split(": ");
//        String key = parts[0];
//        String value = parts[1];
//        headers.put(key, value);
//    }


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
