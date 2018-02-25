package com.minrui.jwt.handler;

/**
 * Created by Gale on 1/5/18.
 */

public class PathMethod {
    private String path;
    private String method;

    public PathMethod(String path, String method) {
        this.path = path;
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public String getMethod() {
        return method;
    }

    public String value(){

        return path+","+method;
    }
}
