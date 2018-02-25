package com.minrui.jwt.utils;

import com.minrui.jwt.annotation.Role;
import com.minrui.jwt.inter.JwtConfig;
import io.jsonwebtoken.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Arrays;

/**
 * Created by Gale on 12/27/17.
 */

public class JwtUtils {
    public final static String JWT_HEADER = "access-token";
    public final static int JWT_HEADER_LENGTH= "Bearer ".length();


}
