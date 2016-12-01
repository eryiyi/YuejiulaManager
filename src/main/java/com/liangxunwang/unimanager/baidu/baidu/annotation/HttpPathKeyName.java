package com.liangxunwang.unimanager.baidu.baidu.annotation;

import com.liangxunwang.unimanager.baidu.annotation.R;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface HttpPathKeyName {

    public R param();

}
