package utils.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import response.HttpResponse;

import java.io.DataOutputStream;
import java.io.IOException;

public class HttpResponseUtils {
    private static Logger log = LoggerFactory.getLogger(HttpResponseUtils.class);

    public static void responseHeader(DataOutputStream dos, HttpResponse httpResponse) {
        try {
            dos.writeBytes(httpResponse.toString());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public static void responseBody(DataOutputStream dos, HttpResponse httpResponse) {
        try {
            byte[] messageBody = httpResponse.getHttpMessageBody();
            dos.write(messageBody, 0, messageBody.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
