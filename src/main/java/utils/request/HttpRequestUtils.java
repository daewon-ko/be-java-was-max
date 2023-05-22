package utils.request;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.component.HttpRequestQueryString;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestUtils {
    private static final Logger log = LoggerFactory.getLogger(HttpRequestUtils.class);


    // To DO : Stream 등을 통해 개선할 수는 없을까? 또 만약 그렇다면 그게 더 좋은 코드일까?
    public static Map<String, String> parseQueryString(String queryString) {
        Map<String, String> parseQueryString = new HashMap<>();
        String[] keyValue = queryString.split("&");
        for (String s : keyValue) {
            String[] splitKeyValue = s.split("=");
            String key = splitKeyValue[0];
            String value = splitKeyValue[1];
            parseQueryString.put(key, value);
        }
        return parseQueryString;
    }


    public static void  requestSingUp(HttpRequestQueryString queryString) {
        String userId = queryString.getValue("userId");
        String password = queryString.getValue("password");
        String name = queryString.getValue("name");
        String email = queryString.getValue("email");
        User user = new User(userId, password, name, email);
        log.debug("user: {}", user);
        Database.addUser(user);
    }



    public static String getRequestBody(final BufferedReader br, final String requestBodyLength) throws IOException {
        char[] buffer = new char[Integer.valueOf(requestBodyLength)];
        br.read(buffer, 0, Integer.valueOf(requestBodyLength));
        String s = String.valueOf(buffer);
        return s;
    }



    //ToDO : 아래와 같이 ContentType을 Request에서 추출하는 것은 방법이 없을까?
//    public static String extractContentType(final BufferedReader br) throws IOException {
//        String contentType = "";
//        String line = br.readLine();
//        while (line != null && !line.isEmpty()) {
//            if (line.startsWith("Accept: ")) {
//                String[] parts = line.split("[:,;]");
//                String wanted = parts[1].trim();
//                contentType = wanted;
//            }
//            line = br.readLine();
//            log.debug("headerLine: {} ", line);
//        }
//        log.debug("Content-Type: {}", contentType);
//        return contentType;
//    }
}
