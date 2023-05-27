package org.http;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest extends HttpMessage {
    private String method;
    private String requestTarget;
    private String httpVersion;

    private String path;

    private Map<String, String> parameter;

    HttpRequest() {
        this.parameter = new HashMap<>();
    }



    public void setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
    }

    private void setPath(String path) {
        this.path = path;
    }

    void setMethod(String methodName) throws HttpParsingException {
        this.method = methodName;
    }

    public void setRequestTarget(String requestTarget) {
        this.requestTarget = requestTarget;
        extractPath(requestTarget);
    }


    public String getHttpVersion() {
        return httpVersion;
    }

    public Map<String, String> getParameter() {
        return parameter;
    }

    public String getPath() {
        return path;
    }


    public String getMethod() {
        return method;
    }

    public String getRequestTarget() {
        return requestTarget;
    }

    private void extractPath(String url) {
        int questionMarkIndex = url.indexOf('?');

        // Extract the path
        String path = (questionMarkIndex != -1) ? url.substring(0, questionMarkIndex) : url;
        setPath(path);

        // Extract the parameters
        if (questionMarkIndex != -1 && questionMarkIndex < url.length() - 1) {
            String paramString = url.substring(questionMarkIndex + 1);
            String[] paramPairs = paramString.split("&");

            for (String paramPair : paramPairs) {
                String[] keyValue = paramPair.split("=");

                if (keyValue.length == 2) {
                    String key = keyValue[0];
                    String value = keyValue[1];
                    parameter.put(key, value);
                }else {
                    // TODO: handle Bad Request
                }
            }
        }
    }
}


