package org.http;

public enum HttpMethod {
    GET, HEAD;
    public static final int MAX_LENGTH;
    static {
        int tempMaxLength = -1;
        for (HttpMethod method : values()) {
         tempMaxLength = method.name().length();
        }
        MAX_LENGTH = tempMaxLength;
    }

}
