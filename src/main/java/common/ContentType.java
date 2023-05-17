package common;

public enum ContentType {
    //Todo : 아래 주석과 위의 value값의 차이에 대해서 고민해보기.
    HTML("text/html;charset=utf-8"),
    CSS("text/css;charset=utf-8"),
    JS("application/javascript;charset=utf-8"),
    ICO("image/vnd.microsoft.icon"),
    PNG("image/png"),
    JPG("image/jpeg"),
    TTF("font/ttf"),
    WOFF("font/woff");

//    HTML("text/html"),
//    CSS("text/css"),
//    JS("applicaton/javascript"),
//    ICO("image/vnd.microsoft/icon"),
//    PNG("image/png"),
//    JPG("image/jpeg"),
//    TTF("font/ttf"),
//    WOFF("font/woff");


    private String value;

    ContentType(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


    /**
     * 메서드명 of : 팩터리 메서드
     * 팩터리 메서드는 보통 생성자 대신 사용되며, 호출코드가 간단하고 가독성이 좋아지는 차이점이 있음.
     * 자바 8 이후 자주 사용
     *
     * @param uri
     * @return
     */
    public static ContentType of(String uri) {
        for (ContentType c : values()) {
            if (uri.toUpperCase().endsWith(c.name())) {
                c.name();
                return c;
            }
        }
        throw new RuntimeException("일치하는 확장자가 없습니다. ");
    }

    @Override
    public String toString() {
        return value;
    }
}

