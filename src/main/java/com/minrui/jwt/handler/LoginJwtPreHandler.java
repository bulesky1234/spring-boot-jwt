package com.minrui.jwt.handler;

import com.minrui.jwt.inter.JwtConfig;
import com.minrui.jwt.interceptor.JwtInterceptor;
import com.minrui.jwt.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * Created by Gale on 12/27/17.
 */

@Component
@ConditionalOnBean(JwtConfig.class)
public class LoginJwtPreHandler implements JwtPreHandler {

    @Autowired
    private JwtConfig jwtConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        OutputStream outputStream = response.getOutputStream();
        jwtConfig.login(request).res(
                jwtData -> {
                    try {
                        String token = "Bearer " + Jwts.builder().setId(jwtData.getUserId().toString())
                                .compressWith(CompressionCodecs.DEFLATE).setExpiration(Date.from(Instant.now().plus(120, ChronoUnit.MINUTES)))
                                .claim(Claims.ISSUER, jwtData.getType()).claim(Claims.SUBJECT,jwtData.getRole()).claim("data",jwtData.getData())
                                .signWith(SignatureAlgorithm.HS512, jwtData.getKey().getBytes("utf-8")).compact();
                        response.setHeader(JwtUtils.JWT_HEADER, token);

                    } catch (UnsupportedEncodingException e) {
                        response.setStatus(JwtInterceptor.defaultErrorCode);
                        PrintUtils.printMessage(outputStream, "系统异常！");
                    }
                }
                , error -> {
                    response.setHeader(HttpHeaders.CONTENT_TYPE, "text/html;charset=UTF-8");
                    response.setStatus(JwtInterceptor.defaultErrorCode);
                    PrintUtils.printMessage(outputStream, error);
                }
        );

        return false;
    }

    @Override
    public PathMethod path() {
        return new PathMethod("/auth/login","POST");
    }
}
