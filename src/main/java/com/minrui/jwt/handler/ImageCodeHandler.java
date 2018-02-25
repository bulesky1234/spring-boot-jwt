package com.minrui.jwt.handler;

import com.minrui.jwt.utils.Checker;
import com.minrui.jwt.utils.CodeImage;
import com.minrui.jwt.utils.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Gale on 1/5/18.
 */

@Component
public class ImageCodeHandler implements JwtPreHandler {

    @Autowired
    @Qualifier("imageCodeChecker")
    Checker checker;

    @Value("${spring.profiles.active}")
    String profileActive;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String numbers = profileActive.equals("dev")?"1234":RandomUtils.getNumbs4();
        CodeImage codeImage = new CodeImage(numbers);
        long currentMillis = System.currentTimeMillis();
        String sign = checker.encrypt(currentMillis, numbers);
        response.setHeader(Checker.SIGN_HEADER, sign);
        response.setHeader(Checker.TIME_HEADER, currentMillis + "");
        response.setContentType("image/jpeg");
        response.setHeader("Cache-Control", "no-cache");
        codeImage.write(response.getOutputStream());
        return false;
    }

    @Override
    public PathMethod path() {
        return new PathMethod("/auth/imageCode", "GET");
    }
}
