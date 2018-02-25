package com.minrui.jwt.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Created by Gale on 12/27/17.
 */
@Target({ElementType.ANNOTATION_TYPE})
public @interface Role {
    String value();
}
