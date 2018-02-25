package com.minrui.jwt.handler;

import com.minrui.jwt.config.JsonUtils;
import com.minrui.jwt.inter.JwtConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Gale on 12/27/17.
 */

@Component
public class UserJwtPostHandler implements JwtPostHandler {
    @Autowired
    JwtConfig jwtConfig;

    @Override
    public boolean postHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        jwtConfig.user(request).res(ok -> {

            PrintUtils.printJsonMessage(response, ok);

        }, error -> {
            try {
                response.setStatus(415);
                PrintUtils.printMessage(response.getOutputStream(),error.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        return false;
    }

    @Override
    public PathMethod path() {
        return new PathMethod("/auth/user", "GET");
    }
}
