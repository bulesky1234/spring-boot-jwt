package com.minrui.jwt.config;

import com.minrui.jwt.fade.Ok;
import com.minrui.jwt.fade.Status;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Created by Gale on 1/10/18.
 */

public class StatusReturnValueHandler implements HandlerMethodReturnValueHandler {
    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return returnType.getParameterType().isAssignableFrom(Status.class);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        if(returnValue instanceof  Ok){
            Ok ok = (Ok)returnValue;
        }
    }
}
