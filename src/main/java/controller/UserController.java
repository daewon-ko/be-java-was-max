package controller;

import common.ContentType;
import common.HttpVersion;
import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import request.component.HttpRequestHeader;
import request.component.HttpRequestQueryString;
import request.component.HttpRequestStartLine;
import request.component.HttpRequestTarget;
import utils.request.HttpRequestUtils;
import utils.request.RequestHandlerUtils;

import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static common.HttpMethod.POST;
import static utils.response.HttpResponseUtils.sendHttp200Response;
import static utils.response.HttpResponseUtils.sendHttp302Response;

public class UserController {
    private static final Logger log =  LoggerFactory.getLogger(UserController.class);
    public static void handleSinupRequest(final DataOutputStream dos, final BufferedReader br, HttpRequest httpRequest) throws IOException {
        HttpRequestQueryString queryString = httpRequest.getQueryString();
        HttpRequestTarget httpRequestTarget = new HttpRequestTarget("/index.html", queryString);
        HttpRequestStartLine httpRequestStartLine = new HttpRequestStartLine(POST, httpRequestTarget, HttpVersion.HTTP);
        log.debug("ContentLength: {}", httpRequest.getRequestHeader().getHeader("Content-Length"));
        String requestBodyLength = httpRequest.getRequestHeader().getHeader("Content-Length");

        String requestBody = HttpRequestUtils.getRequestBody(br, requestBodyLength);

        Map<String, String> param = HttpRequestUtils.parseQueryString(requestBody);
        queryString.add(param);
        HttpRequestUtils.requestSingUp(queryString);

        httpRequest = new HttpRequest(httpRequestStartLine, new HttpRequestHeader(new HashMap<>()));
        HttpRequestStartLine requestStartLine = httpRequest.getRequestStartLine();
        byte[] messageBody = RequestHandlerUtils.readFile(requestStartLine);
        sendHttp302Response(dos, messageBody);
    }


    // TODO : 존재하는 회원이 없을경우 Exception을 어느 곳에서 던져줘야할까? 또 한 번만 던져주면 충분한 것일까?

    public static void handleLoginRequest(final DataOutputStream dos, final BufferedReader br, final HttpRequest httpRequest) throws IOException {
        String requestBodyLength = httpRequest.getRequestHeader().getHeader("Content-Length");
        String requestBody = HttpRequestUtils.getRequestBody(br, requestBodyLength);
        Map<String, String> parseQueryString = HttpRequestUtils.parseQueryString(requestBody);
        for (String id : parseQueryString.keySet()) {
            String password = parseQueryString.get(id);
            if (Database.isExistUser(id, password)) {

                HttpRequestStartLine requestStartLine = httpRequest.getRequestStartLine();
                byte[] messageBody = RequestHandlerUtils.readFile(requestStartLine);
                String path = httpRequest.getPath();
                ContentType contentType = ContentType.of(path);
                sendHttp200Response(dos, messageBody, contentType);

            }

        }
        throw new RuntimeException("회원이 존재하지 않습니다. ");


    }
}
