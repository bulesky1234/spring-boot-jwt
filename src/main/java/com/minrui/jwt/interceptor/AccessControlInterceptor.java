package com.minrui.jwt.interceptor;

import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gale on 12/26/17.
 */
public class AccessControlInterceptor implements HandlerInterceptor {

    AccessControlProperties accessControlProperties;
    private Map<String, String> putHeaders = new HashMap<>();

    public AccessControlInterceptor(AccessControlProperties accessControlProperties) {
        this.accessControlProperties = accessControlProperties;
        putHeaders.put("Access-Control-Allow-Origin", accessControlProperties.getAllowOrigin());
        putHeaders.put("Access-Control-Allow-Methods", accessControlProperties.getAllowMethods());
        putHeaders.put("Access-Control-Allow-Headers", accessControlProperties.getAllowHeaders());
        putHeaders.put("Access-Control-Expose-Headers", accessControlProperties.getExposeHeaders());
        putHeaders.put("Access-Control-Allow-Credentials", accessControlProperties.getAllowCredentials());
        putHeaders.put("Access-Control-Allow-Credentials", accessControlProperties.getAllowCredentials());
        putHeaders.put("Access-Control-Max-Age", accessControlProperties.getMaxAge());
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        putHeaders.forEach((k, v) -> {
            if (v != null) {
                response.setHeader(k, v);
            }
        });

        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}

