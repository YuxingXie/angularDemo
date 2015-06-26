package com.dabast.common.base;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

/**
 * Created by Administrator on 2015/3/11.
 */
@Target({ FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LikeSearch {
}
