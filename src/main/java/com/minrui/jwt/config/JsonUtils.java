package com.minrui.jwt.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * Created by Gale on 1/5/18.
 */

public class JsonUtils {



     static ObjectMapper objectMapper;

    public static ObjectMapper getMapper(){
        return objectMapper;
    }
}
