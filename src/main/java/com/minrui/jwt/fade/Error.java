package com.minrui.jwt.fade;

/**
 * Created by Gale on 1/10/18.
 */

public class Error implements Status{
    private String message;

    public Error(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
