package com.minrui.jwt.utils;

import javax.servlet.http.HttpServletResponse;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by Gale on 12/27/17.
 */

public class StatusCode<T> {
    private boolean error;
    private String message;
    private T data;


    private StatusCode(boolean error, String message,T data) {
        this.error = error;
        this.message = message;
        this.data=data;
    }

    public static <T>StatusCode error(String message,T data) {
        return new StatusCode(true, message,data);
    }
    public static StatusCode error(String message) {
        return new StatusCode(true, message,null);
    }


    public static StatusCode error() {
        return new StatusCode(true, "error",null);
    }


    public static  <T>StatusCode ok(String message,T data) {
        return new StatusCode(false, message,data);
    }

    public static <T>StatusCode ok(T data) {
        return new StatusCode(false, "ok",data);
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String httpBadReq(HttpServletResponse response) {
        if(this.error){
            response.setStatus(415);
        }

        return message;
    }
    public String httpBadReq(HttpServletResponse response,int state) {
        if(this.error){
            response.setStatus(state);
        }

        return message;
    }

    public <A> A res(Supplier<A> ok, Supplier<A> error) {
        if (this.error) {
            return error.get();
        } else {
            return ok.get();
        }
    }

    public <R> R res(Function<T, R> ok, Function<String, R> error) {
        if (this.error) {
            return error.apply(message);
        } else {
            return ok.apply(data);
        }
    }

    public void res(Consumer<T> ok, Consumer<String> error) {
        if (this.error) {
            error.accept(message);
        } else {
            ok.accept(data);
        }
    }

    public T getData() {
        return data;
    }
}
