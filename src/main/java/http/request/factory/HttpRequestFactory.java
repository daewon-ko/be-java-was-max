package http.request.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import http.request.HttpRequest;
import common.HttpMethod;
import common.HttpVersion;
import http.request.component.HttpRequestHeader;
import http.request.component.HttpRequestQueryString;
import http.request.component.HttpRequestStartLine;
import http.request.component.HttpRequestTarget;
import http.utils.request.HttpRequestUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestFactory {
    private static final Logger log = LoggerFactory.getLogger(HttpRequestFactory.class);
    private static final String CRLF = "";
    private static final String REQUEST_STARTLINE_SEPARATOR = " ";
    private static final String REQUEST_HEADER_SEPARATOR = ": \\s*";

    public HttpRequestFactory() {
    }

    public static HttpRequest createHttpRequest(final BufferedReader br) throws IOException {
        HttpRequestStartLine startLine = parsingStartLine(br);
        HttpRequestHeader header = parsingHeader(br);
        return new HttpRequest(startLine, header);

    }

    private static HttpRequestStartLine parsingStartLine(final BufferedReader br) throws IOException {
        String startLine = br.readLine();
        String[] splitStartLine = startLine.split(REQUEST_STARTLINE_SEPARATOR);
        HttpMethod httpMethod = HttpMethod.valueOf(splitStartLine[0]);
        String uri = splitStartLine[1];
//        Double httpVersionNumber = Double.valueOf(splitStartLine[2].split("/")[1]);
//        HttpVersion httpVersion = new HttpVersion(httpVersionNumber);

        HttpVersion httpVersion = HttpVersion.HTTP;

        String[] splitURI = uri.split("\\?");
        String path = splitURI[0];
        HttpRequestQueryString httpRequestQueryString = new HttpRequestQueryString(new HashMap<>());
        if (splitURI.length >= 2) {
            String queryString = splitURI[1];
            Map<String, String> parameter = HttpRequestUtils.parseQueryString(queryString);
            httpRequestQueryString = new HttpRequestQueryString(parameter);
        }
        HttpRequestTarget httpRequestTarget = new HttpRequestTarget(path, httpRequestQueryString);

        return new HttpRequestStartLine(httpMethod, httpRequestTarget, httpVersion);
    }

    private static HttpRequestHeader parsingHeader(final BufferedReader br) throws IOException {
        Map<String, Object> header = new HashMap<>();
        String line;
        while (!(line = br.readLine()).equals(CRLF)) {
            String[] split = line.split(REQUEST_HEADER_SEPARATOR);
            String key = split[0];
            String value = split[1];
            header.put(key, value);
        }
        HttpRequestHeader httpRequestHeader = new HttpRequestHeader(header);
        return httpRequestHeader;

    }


}
