package http.request.component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpRequestHeader {
    /**
     * RequestHeader 역시 key-valuue 형식을 지원.
     */
    private  Map<String, Object> header = new HashMap<>();

    public HttpRequestHeader(final Map<String, Object> parameter) {
        this.header = parameter;
    }

    public Object getHeader(String key) {
        return header.get(key);
    }

    public void add(String key, String value) {
        header.put(key, value);
    }


    /**
     * 아래 stream 코드 원리에 대해서 학습하기
     */
    @Override
    public String toString() {
        return header.keySet().stream()
                .map(key -> String.format("%s: %s", key, header.get(key)))
                .collect(Collectors.joining("\n")).trim();
    }
}

