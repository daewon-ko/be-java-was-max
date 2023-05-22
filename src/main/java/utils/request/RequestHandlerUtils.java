package utils.request;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.component.HttpRequestQueryString;
import request.component.HttpRequestStartLine;
import request.component.HttpRequestTarget;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class RequestHandlerUtils {
    // TODO: 아래 STATIC_PATH, TEMPLATES_PATH에서 ./src/~..가 아니라 /src의 형태에서는 왜 불가능한가?
    // -> FIlE 클래스가 가지고 있는 메서드의 어떤 특성?
    private static final Logger logger = LoggerFactory.getLogger(RequestHandlerUtils.class);
    private static final String STATIC_PATH = "./src/main/resources/static".trim();
    private static final String TEMPLATES_PATH = "./src/main/resources/templates".trim();

    public static byte[] readFile(HttpRequestStartLine httpRequestStartLine) {
        HttpRequestTarget httpRequestTarget = httpRequestStartLine.getHttpRequestURI();
        String requestUriPath = httpRequestTarget.getPath();
        logger.debug("requestURI: {}", httpRequestTarget);
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

    // ../

    private static byte[] findFile(final String requestUriPath) throws IOException {
        // static 내 존재 시
        // ./ -> 최상위 디렉토리
        // 현재 디렉토리부터 찾는 경로는 무엇이지?..
        File file = new File(STATIC_PATH + requestUriPath);
        if (file.exists()) {
            return Files.readAllBytes(file.toPath());
        }
        // templates 내 존재시
        file = new File(TEMPLATES_PATH + requestUriPath);
        if (file.exists()) {
            return Files.readAllBytes(file.toPath());
        }
         throw new RuntimeException();
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


    //TODO : 메서드 이름 고민하기! 부적절해보임.


//    public static String readRequestBody(BufferedReader br, int bodyLength) throws IOException {
//        StringBuilder requestBody = new StringBuilder();
//        String line = br.readLine();
//        while (!(line = br.readLine()).equals("")) {
//        }
////        while (line != null && !line.isEmpty()) {
////            line = br.readLine();
////        }
//        if (line != null && bodyLength > 0) {
//            requestBody.append(line.substring(0, Math.min(bodyLength, line.length())));
//        }
//        return requestBody.toString();
//    }

    /*
    쓰레드 디버깅..
    소켓연결해서 Input, Output Test 까다롭다...
    시부레...

     */
    public static String readRequestBody(BufferedReader br, Integer bodyLength) throws IOException {
        String requestBody = "";
        logger.debug("br.readLine: {}", br.readLine());
        while (!br.readLine().equals("")) {
            br.readLine();
        }
        requestBody = br.readLine().substring(0, bodyLength);
        return requestBody;

    }


}
