package request.utils;

import db.Database;
import model.User;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import request.component.HttpRequestQueryString;
import request.component.HttpRequestStartLine;
import request.component.HttpRequestURI;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RequestHandlerUtils {
    // TODO: 아래 STATIC_PATH, TEMPLATES_PATH에서 ./src/~..가 아니라 /src의 형태에서는 왜 불가능한가?
    private static final Logger logger = LoggerFactory.getLogger(RequestHandlerUtils.class);
    private static final String STATIC_PATH = "./src/main/resources/static".trim();
    private static final String TEMPLATES_PATH = "./src/main/resources/templates".trim();

    public static byte[] readFile(HttpRequestStartLine httpRequestStartLine) {
        HttpRequestURI httpRequestURI = httpRequestStartLine.getHttpRequestURI();
        String requestUriPath = httpRequestURI.getPath();
        logger.debug("requestURI: {}", httpRequestURI);
        logger.debug("requestURIPath : {}", requestUriPath);
        try {
            return findFile(requestUriPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 아래 return이 null처리 될 경우 예외처리는 어떻게?
     * 해줘야 하지 않을까?
     * Http Exception 등으로 처리?
     *
     * @param requestUriPath
     * @return
     * @throws IOException
     */
    private static byte[] findFile(final String requestUriPath) throws IOException {
        // static 내 존재 시
        File file = new File(STATIC_PATH + requestUriPath);
        if (file.exists()) {
            return Files.readAllBytes(file.toPath());
        }
        // templates 내 존재시
        file = new File(TEMPLATES_PATH + requestUriPath);
        if (file.exists()) {
            return Files.readAllBytes(file.toPath());
        }
        return null;
    }

    public static boolean isStaticResource(final String requestUriPath) {
        // static 폴더 내 존재시
        File file = new File(STATIC_PATH + requestUriPath);
        if (file.exists()) {
            return true;
        }
        // templates 폴더 내 존재 시
        file = new File(TEMPLATES_PATH + requestUriPath);
        if (file.exists()) {
            return true;
        }
        return false;
    }

    public static void requestSingUp(HttpRequestQueryString queryString) {
        String userId = queryString.getValue("userId");
        String password = queryString.getValue("password");
        String name = queryString.getValue("name");
        String email = queryString.getValue("email");
        User user = new User(userId, password, name, email);
        logger.debug("user: {}", user);
        Database.addUser(user);
    }


}
