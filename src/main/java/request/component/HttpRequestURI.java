package request.component;

public class HttpRequestURI {
    private final String path;
    private final HttpRequestQueryString queryString;


    public HttpRequestURI(final String path, final HttpRequestQueryString queryString) {
        this.path = path;
        this.queryString = queryString;
    }

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
