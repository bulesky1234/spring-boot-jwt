package com.minrui.jwt.handler;

import com.minrui.jwt.inter.JwtConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Gale on 12/27/17.
 */

@Component
@ConditionalOnBean(JwtConfig.class)
public class LogoutJwtPreHandler implements JwtPostHandler {

    @Autowired
    private JwtConfig jwtConfig;

    @Override
    public boolean postHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        jwtConfig.logout(request);
        return false;
    }

    @Override
    public PathMethod path() {
        return new PathMethod("/auth/logout","POST");
    }
}
