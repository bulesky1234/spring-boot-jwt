package com.minrui.jwt.fade;

/**
 * Created by Gale on 1/10/18.
 */

public class Ok implements Status{
    private Object obj;

    public Ok(Object obj) {
        this.obj = obj;
    }

    public Object getObj() {
        return obj;
    }
}
