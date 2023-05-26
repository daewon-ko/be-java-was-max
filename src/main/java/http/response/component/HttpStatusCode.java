package http.response.component;

public enum HttpStatusCode {
    OK("OK",200),
    FOUND("Found", 302);

    private int statusCodeNumber;
    private String reasonPhrase;


    HttpStatusCode(final String reasonPhrase, final int statusCodeNumber) {
        this.reasonPhrase = reasonPhrase;
        this.statusCodeNumber = statusCodeNumber;
    }

    @Override
    public String toString() {
        return String.format("%d %s", statusCodeNumber, reasonPhrase);
    }
}
