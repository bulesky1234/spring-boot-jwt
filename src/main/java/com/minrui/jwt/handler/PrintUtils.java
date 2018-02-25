package com.minrui.jwt.handler;

import com.minrui.jwt.config.JsonUtils;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Gale on 12/27/17.
 */

public class PrintUtils {
    public static void printMessage(OutputStream stream, String message) {
        try {
            stream.write(message.getBytes("utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printErrorMessage(HttpServletResponse response, String message) {
        response.setHeader(HttpHeaders.CONTENT_TYPE, "text/html;charset=UTF-8");
        response.setStatus(415);
        try {
            response.getWriter().print(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void printMessage(HttpServletResponse response, String message) {
        response.setHeader(HttpHeaders.CONTENT_TYPE, "text/html;charset=UTF-8");
        try {
            response.getWriter().print(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printJsonMessage(HttpServletResponse response, Object message) {
        response.setHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
        try {
            response.getOutputStream().write(JsonUtils.getMapper().writeValueAsBytes(message));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printJsonErrorMessage(HttpServletResponse response, Object message) {
        response.setHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
        response.setStatus(415);
        try {
            response.getOutputStream().write(JsonUtils.getMapper().writeValueAsBytes(message));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setResponse(int status, HttpServletResponse response, String message) {
        response.setHeader(HttpHeaders.CONTENT_TYPE, "text/html;charset=UTF-8");
        response.setStatus(status);
        try {
            response.getWriter().println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
