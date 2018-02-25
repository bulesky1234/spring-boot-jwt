package com.minrui.jwt.interceptor;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Gale on 12/26/17.
 */

@ConfigurationProperties(prefix = "access.control")
public class AccessControlProperties {

/*     response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, OPTIONS, PATCH");
        response.setHeader("Access-Control-Allow-Headers", "Origin, Content-Type, Accept, access-token");
        response.setHeader("Access-Control-Expose-Headers", "access-token");
        response.setHeader("Access-Control-Allow-Credentials", "true");*/

    private String allowOrigin ;
    private String allowMethods ;
    private String allowHeaders ;
    private String exposeHeaders;
    private String allowCredentials;
    private String maxAge;

    public String getAllowOrigin() {
        return allowOrigin;
    }

    public void setAllowOrigin(String allowOrigin) {
        this.allowOrigin = allowOrigin;
    }

    public String getAllowMethods() {
        return allowMethods;
    }

    public void setAllowMethods(String allowMethods) {
        this.allowMethods = allowMethods;
    }

    public String getAllowHeaders() {
        return allowHeaders;
    }

    public void setAllowHeaders(String allowHeaders) {
        this.allowHeaders = allowHeaders;
    }

    public String getExposeHeaders() {
        return exposeHeaders;
    }

    public void setExposeHeaders(String exposeHeaders) {
        this.exposeHeaders = exposeHeaders;
    }

    public String getAllowCredentials() {
        return allowCredentials;
    }

    public void setAllowCredentials(String allowCredentials) {
        this.allowCredentials = allowCredentials;
    }

    public String getMaxAge() {
        return maxAge;
    }
    public void setMaxAge(String maxAge) {
        this.maxAge = maxAge;
    }
}
