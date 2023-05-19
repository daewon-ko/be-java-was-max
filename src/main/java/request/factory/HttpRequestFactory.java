package request.factory;

import request.HttpRequest;
import common.HttpMethod;
import common.HttpVersion;
import request.component.HttpRequestHeader;
import request.component.HttpRequestQueryString;
import request.component.HttpRequestStartLine;
import request.component.HttpRequestTarget;
import utils.request.HttpRequestUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestFactory {
    private static final String CRLF = "";
    private static final String REQUEST_STARTLINE_SEPARATOR = " ";
    private static final String REQUEST_HEADER_SEPARATOR = ": \\s*";

    public HttpRequestFactory() {
    }

    public static HttpRequest createHttpRequest(final BufferedReader br) throws IOException {
        HttpRequestStartLine startLine = parsingStartLine(br);
        HttpRequestHeader header = parsingHeader(br);


//        System.out.println(br.readLine());
//        System.out.println(br.readLine());
//        System.out.println(br.readLine());
//        System.out.println(br.readLine());
//        System.out.println(br.readLine());
//        System.out.println(br.readLine());
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
        Map<String, String> header = new HashMap<>();
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
