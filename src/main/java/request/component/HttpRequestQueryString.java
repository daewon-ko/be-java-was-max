package request.component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * userId=1234&password=1234
 * 위의 예시와 같이 uri 상에서 ? 뒤에 위치하는 HTTP Request의 startLine 중
 * queryString 클래스
 */

public class HttpRequestQueryString {


    /**
     * final로 선언하거나 안 하거나 이 필드변수에서의 유의미한 차이는?
     */
    private final Map<String, String> parameter;

    /**
     * 생성자를 통해 주입을 하고있는데 이 장점은?
     *
     * @param parameter
     */
    public HttpRequestQueryString(final Map<String, String> parameter) {
        this.parameter = parameter;
    }

    public String getValue(String key) {
        return parameter.get(key);
    }

    public void add(String key, String value) {
        parameter.put(key, value);
    }

    public void add(Map<String, String> param) {
        Set<String> keySet = param.keySet();
        for (String key : keySet) {
            parameter.put(key, param.get(key));
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (parameter.size() > 0) {
            sb.append("?");
        }
        for (String key : parameter.keySet()) {
            sb.append(key).append("=").append(parameter.get(key))
                    .append(" ");
        }

        return String.join("&", sb.toString().trim().split(" "));
    }

}
