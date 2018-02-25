package com.minrui.jwt.interceptor;

import com.minrui.jwt.fade.Error;
import com.minrui.jwt.inter.JwtConfig;
import com.minrui.jwt.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * Created by Gale on 1/3/18.
 */
@ControllerAdvice
@ConditionalOnBean(JwtConfig.class)
public class JwtRefreshAdvice implements ResponseBodyAdvice {
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (request instanceof ServletServerHttpRequest && response instanceof ServletServerHttpResponse) {
            JwtData jwtData = (JwtData) ((ServletServerHttpRequest) request).getServletRequest().getAttribute(JwtInterceptor.JWT_DATE_NAME);
            if (jwtData != null) {
                try {
                    String token = "Bearer " + Jwts.builder().setId(jwtData.getUserId().toString())
                            .compressWith(CompressionCodecs.DEFLATE).setExpiration(Date.from(Instant.now().plus(120, ChronoUnit.MINUTES)))
                            .claim(Claims.ISSUER, jwtData.getType()).claim(Claims.SUBJECT, jwtData.getRole()).claim("data", jwtData.getData())
                            .signWith(SignatureAlgorithm.HS512, jwtData.getKey().getBytes("utf-8")).compact();
                    ((ServletServerHttpResponse) response).getServletResponse().setHeader(JwtUtils.JWT_HEADER, token);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

        }
        if (body.getClass().equals(Error.class)) {
            response.setStatusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
            return ((Error) body).getMessage();
        }


        return body;
    }
}
