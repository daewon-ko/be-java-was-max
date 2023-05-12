package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestUtils {
    private static final Logger log = LoggerFactory.getLogger(HttpRequestUtils.class);

    public static String getUrl(String line) {
        String[] splitedLine = line.split(" ");
        String path = splitedLine[1];
        log.debug("request URL:" + path);
        return path;
    }

    // To DO : Stream 등을 통해 개선할 수는 없을까? 또 만약 그렇다면 그게 더 좋은 코드일까?
    public static Map<String, String> parseQueryString(String queryString) {
        HashMap<String, String> params = new HashMap<>();
        String[] tokens = queryString.split("&");
        for (String param : tokens) {
            int index = param.indexOf("=");
            if (index > 1) {
                String key = param.substring(0, index);
                String value = param.substring(index + 1);
                params.put(key, value);
            }
        }
        return params;

    }

    public static String extractContentType(final BufferedReader br) throws IOException {
        String contentType = "";
        String line = br.readLine();
        while (line != null && !line.isEmpty()) {
            if (line.startsWith("Accept: ")) {
                String[] parts = line.split("[:,;]");
                String wanted = parts[1].trim();
                contentType = wanted;
            }
            line = br.readLine();
            log.debug("headerLine: {} ", line);
        }
        log.debug("Content-Type: {}", contentType);
        return contentType;
    }
}
