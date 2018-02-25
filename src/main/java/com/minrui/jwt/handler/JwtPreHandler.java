package com.minrui.jwt.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Gale on 12/27/17.
 */

public interface JwtPreHandler extends JwtPath{

    boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception;


}
