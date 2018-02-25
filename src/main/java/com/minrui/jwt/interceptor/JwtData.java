package com.minrui.jwt.interceptor;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gale on 12/27/17.
 */

public class JwtData {
    private Object userId;
    private String role;
    private Integer type;
    private String key;
    private Map<String, Object> data;

    public JwtData(Object userId, String role, Integer type, String key) {
        this.userId = userId;
        this.role = role;
        this.type = type;
        this.key = key;
        this.data = new HashMap<>();
    }

    public JwtData(Object userId, String role, Integer type, String key, Map<String, Object> data) {
        this.userId = userId;
        this.role = role;
        this.type = type;
        this.key = key;
        this.data = data;
        if(this.data==null){
            this.data=new HashMap<>();
        }
    }

    public String getKey() {
        return key;
    }

    public Integer getType() {
        return type;
    }

    public Object getUserId() {
        return userId;
    }

    public String getRole() {
        return role;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public Object get(String key) {
        return data.get(key);
    }

    public void put(String key,Object obj){
        data.put(key,obj);
    }
}
