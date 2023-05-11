package webserver;

public enum ContentType {
    TEXT_HTML("text/html"),
    TEXT_CSS("text/css"),
    APPLICATION_JAVASCRIPT("application/javascript");

    private String value;

    ContentType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
