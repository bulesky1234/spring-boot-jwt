package com.minrui.jwt.inter;

import com.minrui.jwt.interceptor.JwtData;
import com.minrui.jwt.utils.StatusCode;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by Gale on 12/27/17.
 */

public interface JwtConfig {
    String getUserKey(String userId);
    JwtData resolveJwt(Object obj);
    StatusCode<JwtData> login(HttpServletRequest request);
    StatusCode logout(HttpServletRequest request);
    StatusCode register(HttpServletRequest request);
    StatusCode refresh(HttpServletRequest request);

    StatusCode user(HttpServletRequest request);
}
