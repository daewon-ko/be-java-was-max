package common;

public enum HttpVersion {

    HTTP(1.1);
    private double version;

    HttpVersion(final double version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return String.format("%s/%1.1f", name(), version);
    }
}

