package controller;

import common.ContentType;
import common.HttpVersion;
import db.Database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import http.request.HttpRequest;
import http.request.component.HttpRequestHeader;
import http.request.component.HttpRequestQueryString;
import http.request.component.HttpRequestStartLine;
import http.request.component.HttpRequestTarget;
import http.utils.request.HttpRequestUtils;
import http.utils.request.RequestHandlerUtils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static common.HttpMethod.POST;
import static http.utils.response.HttpResponseUtils.*;

public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

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

        // 회원가입 시 Basic homepage인 index.html로 redirect
        sendHttp302ResponseBasicHome(dos, messageBody);
    }


    // TODO : 존재하는 회원이 없을경우 Exception을 어느 곳에서 던져줘야할까? 또 한 번만 던져주면 충분한 것일까?
    // 기능 요구사항에 따라

    public static void handleLoginRequest(final DataOutputStream dos, final BufferedReader br, HttpRequest httpRequest) throws IOException {


        String requestBodyLength = httpRequest.getRequestHeader().getHeader("Content-Length");
        String requestBody = HttpRequestUtils.getRequestBody(br, requestBodyLength);
        Map<String, String> parseQueryString = HttpRequestUtils.parseQueryString(requestBody);


        HttpRequestQueryString queryString = httpRequest.getQueryString();
        HttpRequestHeader requestHeader = httpRequest.getRequestHeader();


        String userId = parseQueryString.get("userId");
        String password = parseQueryString.get("password");

        if (Database.isExistUser(userId, password)) {
            // 로그인 성공 시
            HttpRequestTarget httpRequestTarget = new HttpRequestTarget("/index.html", queryString);
            HttpRequestStartLine httpRequestStartLine = new HttpRequestStartLine(POST, httpRequestTarget, HttpVersion.HTTP);
            httpRequest = new HttpRequest(httpRequestStartLine, requestHeader);

            String path = httpRequest.getPath();
            ContentType contentType = ContentType.of(path);
            HttpRequestStartLine requestStartLine = httpRequest.getRequestStartLine();
            byte[] messageBody = RequestHandlerUtils.readFile(requestStartLine);
            sendHttp302ResponseBasicHomeUsingSession(dos, messageBody);

        }
        // DB에 존재하지 않을경우(즉 회원가입이 되어있지 않을 경우)
        HttpRequestTarget httpRequestTarget = new HttpRequestTarget("/user/login.html", queryString);
        HttpRequestStartLine httpRequestStartLine = new HttpRequestStartLine(POST, httpRequestTarget, HttpVersion.HTTP);
        httpRequest = new HttpRequest(httpRequestStartLine, requestHeader);

        String path = httpRequest.getPath();
        ContentType contentType = ContentType.of(path);
        HttpRequestStartLine requestStartLine = httpRequest.getRequestStartLine();
        byte[] messageBody = RequestHandlerUtils.readFile(requestStartLine);
        // 로그인 실패 시 /user/login_failed 페이지로 redirect
        sendHttp302ResponseLoginFailed(dos, messageBody);


    }
}
