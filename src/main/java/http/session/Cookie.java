package http.session;

public class Cookie {

//    private Map<>
    private String name;
    private String value;

//    public Cookie(final String name, final String value) {
//        this.name = name;
//        this.value = value;
//    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("%s=%s; ", name, value);
    }
}
