package com.minrui.jwt.config;

import com.minrui.jwt.interceptor.JwtData;
import com.minrui.jwt.utils.JwtDataUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Gale on 1/10/18.
 */
public class JwtDataMethodProcessor implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(JwtData.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        JwtData jwtData = JwtDataUtils.jwtData((HttpServletRequest) webRequest.getNativeRequest());
        if(jwtData==null){
            throw new IllegalStateException("未认证接口不能注入jwtData");
        }
        return jwtData;
    }
}
