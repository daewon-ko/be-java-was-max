package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequestUtils {
    private static final Logger log = LoggerFactory.getLogger(HttpRequestUtils.class);

    public static String getUrl(String line) {
        String[] splitedLine = line.split(" ");
        String path = splitedLine[1];
        log.debug("request URL:"+path);
        return path;
    }
}
