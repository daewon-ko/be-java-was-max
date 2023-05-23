package http.request.component;

public class HttpRequestTarget {
    private final String path;
    private final HttpRequestQueryString queryString;


    public HttpRequestTarget(final String path, final HttpRequestQueryString queryString) {
        this.path = path;
        this.queryString = queryString;
    }

    //TODO : 아래와 같은 별도의 생성자가 또 필요할까? /index.html/null과 같이 처리하면 Parsing하기 더 까다로워지지 않읗까?



    public String getPath() {
        return path;
    }

    public HttpRequestQueryString getQueryString() {
        return queryString;
    }

    @Override
    public String toString() {
        return String.format("%s/%s", path, queryString);
    }
}
