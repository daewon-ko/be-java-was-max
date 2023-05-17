package request.component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpRequestHeader {
    /**
     * RequestHeader 역시 key-valuue 형식을 지원.
     */
    Map<String, String> header = new HashMap<>();

    public HttpRequestHeader(final Map<String, String> parameter) {
        this.header = parameter;
    }

    public String getHeader(String key) {
        return header.get(key);
    }

    public void add(String key, String value) {
        header.put(key, value);
    }


    /**
     * 아래 stream 코드 원리에 대해서 학습하기
     * \n은 단순개행이지만, \r\n과의 차이는?
     * -> 리눅스 , 윈도우 운영체제의 차이
     * @return
     */
    @Override
    public String toString() {
        return header.keySet().stream()
                .map(key -> String.format("%s: %s", key, header.get(key)))
                .collect(Collectors.joining("\n")).trim();
    }
}

