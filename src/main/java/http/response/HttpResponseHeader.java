package http.response;

import java.util.Map;
import java.util.stream.Collectors;

public class HttpResponseHeader {
    private final Map<String, Object> header;

    public HttpResponseHeader(final Map<String, Object> header) {
        this.header = header;
    }


    public Object getHeaderValue(String key) {
        return header.get(key);
    }

    public void addHeader(String key, Object value) {
        header.put(key, value);
    }


    //todo: 아래의 Stream을 이용한 코드와 Set을 이용한 코드를 성능 상 비교하고 Stream 코드를 뜯어서 분석해보기
    @Override
    public String toString() {
        return header.keySet().stream()
                .map(key -> String.format("%s: %s", key, header.get(key)))
                .collect(Collectors.joining("\n")).trim();
    }

//    @Override
//    public String toString() {
//        Set<String> keySet = header.keySet();
//        String result = "";
//        for (String key : keySet) {
//            String value = header.get(key);
//            result = String.format("%s: %s\n", key, value).trim();
//        }
//        return result;
//    }
}
