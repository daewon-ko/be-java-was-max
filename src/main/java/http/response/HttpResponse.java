package http.response;

public class HttpResponse {
    private final HttpStatusLine httpStatusLine;
    private final HttpResponseHeader httpResponseHeader;
    private final byte[] httpMessageBody;

    public HttpResponse(final HttpStatusLine httpStatusLine, final HttpResponseHeader httpResponseHeader, final byte[] httpMessageBody) {
        this.httpStatusLine = httpStatusLine;
        this.httpResponseHeader = httpResponseHeader;
        this.httpMessageBody = httpMessageBody;
    }

    public HttpStatusLine getHttpStatusLine() {
        return httpStatusLine;
    }

    public HttpResponseHeader getHttpResponseHeader() {
        return httpResponseHeader;
    }

    public byte[] getHttpMessageBody() {
        return httpMessageBody;
    }


    /**
     * HttpMessagebody는 왜 빠졌을까?
     * 또, httpResponseHeader 이후에 왜 두칸이나 뛸까?
     * @return
     */
    @Override
    public String toString() {

        return String.format("%s\n%s\n\n", httpStatusLine, httpResponseHeader);
    }

    public void addHeader(final String key, final Object value) {
        httpResponseHeader.addHeader(key, value);

    }
}
