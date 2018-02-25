package com.minrui.jwt.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Gale on 1/9/18.
 */

public class Checker {
    public static String SIGN_HEADER = "code-sign";
    public static String TIME_HEADER = "code-time";
    public static String VALUE_HEADER = "code-value";


    public Map<String, String> cache;

    public Checker(int initalizingCount) {
        this.cache = new LinkedHashMap() {
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return this.size() > initalizingCount;
            }
        };
    }

    public StatusCode<String> checkHeader(HttpServletRequest request, long interval) {
        return checkHeader(request.getHeader(SIGN_HEADER), request.getHeader(TIME_HEADER), request.getHeader(VALUE_HEADER), interval);
    }

    public StatusCode<String> checkHeader(String sign, String timeMillis, String value, long interval) {
        String salt = this.cache.getOrDefault(sign, "");
        try {
            if ((Long.parseLong(timeMillis) + interval) < System.currentTimeMillis()) {
                return StatusCode.error("验证码过期");
            }

            if (!EncryptionUtls.SHA1(timeMillis + value + salt).equals(sign)) {
                if (salt.equals("")) {
                    return StatusCode.error("验证码已使用过!");
                }
                return StatusCode.error("验证码不匹配！");
            }
        } catch (NumberFormatException e) {
            return StatusCode.error("时间头错误!");
        }
        this.cache.remove(sign);
        return StatusCode.ok("ok");
    }

    public String encrypt(long currentMillis, String numbers) {
        String salt = RandomUtils.getStr6();

        String sign = EncryptionUtls.SHA1(currentMillis + numbers + salt);
        //确保sign 不会重复
        if (this.cache.containsKey(sign)) {
            salt += "1";
            sign = EncryptionUtls.SHA1(currentMillis + numbers + salt);
        }
        this.cache.put(sign, salt);
        return sign;
    }
}
