package response;

public enum HttpStatusCode {
    OK(200);

    private int value;


    HttpStatusCode(final int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("%d %s", value, name());
    }
}
