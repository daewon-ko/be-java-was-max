package http.utils.request;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class HttpRequestUtilsTest {

    @DisplayName("QueryString을 Parsing하는 테스트코드")
    @Test
    void parse() {
        //given : 어떤 데이터가 준비되었을 때
        String queryString = "userid=eodnjs568&password=1234";

        //when : 어떤 함수를 실행하면
        Map<String, String> parseQueryString = HttpRequestUtils.parseQueryString(queryString);

        //then : 어떤 결과값이 나온다.
        Assertions.assertThat(parseQueryString.get("userid")).isEqualTo("eodnjs568");
        Assertions.assertThat(parseQueryString.get("password")).isEqualTo("1234");

    }

}
