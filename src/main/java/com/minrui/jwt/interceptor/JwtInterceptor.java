package com.minrui.jwt.interceptor;

import com.minrui.jwt.annotation.Authorized;
import com.minrui.jwt.annotation.Role;
import com.minrui.jwt.handler.JwtPostHandler;
import com.minrui.jwt.handler.JwtPreHandler;
import com.minrui.jwt.handler.PrintUtils;
import com.minrui.jwt.inter.JwtConfig;
import com.minrui.jwt.utils.JwtUtils;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Gale on 12/27/17.
 */

public class JwtInterceptor implements HandlerInterceptor {
    Logger log = LoggerFactory.getLogger(this.getClass());

    public static String JWT_DATE_NAME = JwtInterceptor.class.getName() + "-data";

    public static int defaultErrorCode = 415;

    private JwtConfig jwtConfig;

    List<JwtPreHandler> jwtPreHandlerList;
    List<JwtPostHandler> jwtPostHandleList;
    private Map<String, JwtPreHandler> jwtPreHandlerMap = new HashMap<>();
    private Map<String, JwtPostHandler> jwtPostHandlerMap = new HashMap<>();

    public JwtInterceptor(JwtConfig jwtConfig, List<JwtPreHandler> jwtPreHandlerList, List<JwtPostHandler> jwtPostHandleList) {
        this.jwtConfig = jwtConfig;
        this.jwtPreHandlerList = jwtPreHandlerList;
        this.jwtPostHandleList = jwtPostHandleList;
        jwtPreHandlerList.forEach(o -> jwtPreHandlerMap.put(o.path().value(), o));
        jwtPostHandleList.forEach(o -> jwtPostHandlerMap.put(o.path().value(), o));
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI() + "," + request.getMethod().toUpperCase();


        JwtPreHandler jwtPreHandler = jwtPreHandlerMap.get(uri);
        if (jwtPreHandler != null) {
            return jwtPreHandler.preHandle(request, response, handler);
        }

        if (jwtHandle(request, response, handler,jwtPostHandlerMap.containsKey(uri))) {
            JwtPostHandler jwtPostHandler = jwtPostHandlerMap.get(uri);
            if (jwtPostHandler != null) {
                return jwtPostHandler.postHandle(request, response, handler);
            }
            return true;
        }
        return false;
    }

    private boolean jwtHandle(HttpServletRequest request, HttpServletResponse response, Object handler,boolean isMustHandle) throws Exception {



        if (handler instanceof HandlerMethod || isMustHandle) {
            Role[] innerRoles = {};
            if(handler instanceof HandlerMethod){
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                Authorized authorized = handlerMethod.getMethodAnnotation(Authorized.class);

                if (authorized == null) {
                    authorized = handlerMethod.getBeanType().getDeclaredAnnotation(Authorized.class);
                }
                if (authorized == null || !authorized.value()) {
                    return true;
                }
                innerRoles = authorized.roles();
            }
            String jwtValue = request.getHeader(JwtUtils.JWT_HEADER);
            if (jwtValue == null || jwtValue.equals("")) {
                PrintUtils.setResponse(401, response, "token为空!");
                return false;
            }



            Role[] roles = innerRoles;
            Jwt jwt;
            try {
                jwt = Jwts.parser().setSigningKeyResolver(new SigningKeyResolverAdapter() {
                    @Override
                    public byte[] resolveSigningKeyBytes(JwsHeader header, Claims claims) {
                        log.debug("header:" + header.toString() + "\n claims:" + claims);
                        if (roles.length == 0 || (roles.length == 1 && roles[0].value().equals(""))) {

                        } else {
                            if (!Arrays.stream(roles).map(Role::value).anyMatch(role -> role.equals(claims.getSubject()))) {
                                throw new SignatureException("用户身份校验错误！");
                            }
                        }
                        String userKey = jwtConfig.getUserKey(claims.getId());
                        if (userKey == null) {
                            throw new SignatureException("找不到该用户的key！");
                        }
                        return userKey.getBytes();
                    }
                }).parse(jwtValue.substring(JwtUtils.JWT_HEADER_LENGTH));
            } catch (ExpiredJwtException e) {
                PrintUtils.setResponse(401, response, "登录过期!");
                return false;
            } catch (SignatureException e) {
                PrintUtils.setResponse(401, response, "验证错误!");
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                PrintUtils.setResponse(401, response, e.getMessage());
                return false;
            }
            JwtData jwtData = jwtConfig.resolveJwt(jwt.getBody());
            request.setAttribute(JWT_DATE_NAME, jwtData);
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
