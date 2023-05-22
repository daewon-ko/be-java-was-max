package controller;

import common.HttpVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import request.component.HttpRequestHeader;
import request.component.HttpRequestQueryString;
import request.component.HttpRequestStartLine;
import request.component.HttpRequestTarget;
import utils.request.HttpRequestUtils;
import utils.request.RequestHandlerUtils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static common.HttpMethod.POST;
import static utils.response.HttpResponseUtils.sendHttp302Response;

public class UserController {
    private static final Logger log =  LoggerFactory.getLogger(UserController.class);
    public static void handleSinupRequest(final DataOutputStream dos, final BufferedReader br, HttpRequest httpRequest) throws IOException {
        HttpRequestQueryString queryString = httpRequest.getQueryString();
        HttpRequestTarget httpRequestTarget = new HttpRequestTarget("/index.html", queryString);
        HttpRequestStartLine httpRequestStartLine = new HttpRequestStartLine(POST, httpRequestTarget, HttpVersion.HTTP);
        log.debug("ContentLength: {}", httpRequest.getRequestHeader().getHeader("Content-Length"));
        String requestBodyLength = httpRequest.getRequestHeader().getHeader("Content-Length");

        String requestBody = RequestHandlerUtils.getRequestBody(br, requestBodyLength);

        Map<String, String> param = HttpRequestUtils.parseQueryString(requestBody);
        queryString.add(param);
        RequestHandlerUtils.requestSingUp(queryString);

        httpRequest = new HttpRequest(httpRequestStartLine, new HttpRequestHeader(new HashMap<>()));
        HttpRequestStartLine requestStartLine = httpRequest.getRequestStartLine();
        byte[] messageBody = RequestHandlerUtils.readFile(requestStartLine);
        sendHttp302Response(dos, messageBody);
    }
}
