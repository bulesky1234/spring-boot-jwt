package com.minrui.jwt.config;

import com.minrui.jwt.handler.JwtPostHandler;
import com.minrui.jwt.handler.JwtPreHandler;
import com.minrui.jwt.inter.JwtConfig;
import com.minrui.jwt.interceptor.AccessControlInterceptor;
import com.minrui.jwt.interceptor.AccessControlProperties;
import com.minrui.jwt.interceptor.JwtInterceptor;
import com.minrui.jwt.utils.Checker;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolverComposite;
import org.springframework.web.servlet.handler.MappedInterceptor;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.List;

/**
 * Created by Gale on 1/3/18.
 */

@Configuration
@EnableConfigurationProperties(AccessControlProperties.class)
@ConditionalOnBean(JwtConfig.class)
public class AutoConfiguration implements InitializingBean {

    @Autowired
    AccessControlProperties accessControlProperties;

    @Autowired
    JwtConfig jwtConfig;

    @Autowired
    List<JwtPreHandler> jwtPreHandlerList;

    @Autowired
    List<JwtPostHandler> jwtPostHandleList;

    @Autowired
    MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    @Bean
    public MappedInterceptor accessControl() {

        MappedInterceptor mappedInterceptor = new MappedInterceptor(new String[]{"/**"}, new AccessControlInterceptor(accessControlProperties));
        return mappedInterceptor;
    }

    @Bean
    public MappedInterceptor jwtControl() {
        MappedInterceptor mappedInterceptor = new MappedInterceptor(new String[]{"/**"}, new JwtInterceptor(jwtConfig, jwtPreHandlerList, jwtPostHandleList));
        return mappedInterceptor;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        JsonUtils.objectMapper = mappingJackson2HttpMessageConverter.getObjectMapper();
    }


    @Bean("imageCodeChecker")
    public Checker imageCodeChecker() {
        return new Checker(100);
    }
}
