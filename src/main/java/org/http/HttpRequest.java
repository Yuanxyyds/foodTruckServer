package org.http;

public class HttpRequest extends HttpMessage{
    private String method;
    private String requestTarget;
    private String httpVersion;

    public String getMethod() {
        return method;
    }

    public String getRequestTarget() {
        return requestTarget;
    }

    void setMethod(String methodName) throws HttpParsingException {
       this.method = methodName;
    }


    HttpRequest(){

    }

    public void setRequestTarget(String requestTarget) {
        this.requestTarget = requestTarget;
    }
}
