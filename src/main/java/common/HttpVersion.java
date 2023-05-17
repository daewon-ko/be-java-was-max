package common;

public class HttpVersion {
    private static final String name = "HTTP";
    private double version;

    public HttpVersion(final double version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return String.format("%s/%.1f", name, version);
    }

}
