package com.minrui.jwt.utils;

import com.minrui.jwt.interceptor.JwtData;
import com.minrui.jwt.interceptor.JwtInterceptor;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Gale on 1/10/18.
 */

public interface JwtDataUtils {
    static JwtData jwtData(HttpServletRequest request){

        return (JwtData)request.getAttribute(JwtInterceptor.JWT_DATE_NAME);
    }

}
