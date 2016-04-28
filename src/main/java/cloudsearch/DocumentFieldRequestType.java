package cloudsearch;

public enum DocumentFieldRequestType {
    ADD("add"),
    DELETE("delete");

    String value;

    DocumentFieldRequestType(String value) {
        this.value = value;
    }

    public String asText() {
        return value;
    }
}
