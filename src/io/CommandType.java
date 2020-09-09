package io;

public enum CommandType {
    CREATE("create"),
    REMOVE("remove"),
    READ("read"),
    WRITE("write");

    private final String type;

    CommandType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
