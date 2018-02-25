package com.minrui.jwt.utils;

import java.time.format.DateTimeFormatter;

/**
 * Created by Gale on 12/11/17.
 */

public class DateUtils {
    public static DateTimeFormatter normal(){
       return  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    public static DateTimeFormatter acrossyyyyMMhh(){
        return  DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    public static DateTimeFormatter yyyyMMdd(){
        return  DateTimeFormatter.ofPattern("yyyyMMdd");
    }


}
