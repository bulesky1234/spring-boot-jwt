package com.minrui.jwt.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Gale on 12/27/17.
 */

public interface JwtPostHandler extends JwtPath {

    boolean postHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception;


}
