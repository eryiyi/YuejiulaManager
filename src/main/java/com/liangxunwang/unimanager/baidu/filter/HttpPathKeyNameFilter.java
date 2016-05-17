package com.liangxunwang.unimanager.baidu.filter;


import com.liangxunwang.unimanager.baidu.annotation.HttpPathKeyName;
import com.liangxunwang.unimanager.baidu.annotation.R;

import java.lang.reflect.Field;
import java.util.Map;

public class HttpPathKeyNameFilter implements IFieldFilter {

    @Override
    public void validate(Field field, Object req) throws Exception {

        if (field.isAnnotationPresent(HttpPathKeyName.class)) {
            Object obj = field.get(req);
            if (obj == null) {
                HttpPathKeyName annotation = field
                        .getAnnotation(HttpPathKeyName.class);
                if (annotation.param() == R.REQUIRE) {
                    throw new Exception(field.getName()
                            + " is null, default require");
                }
            }
        }

    }

    @Override
    public void mapping(Field field, Object obj, Map<String, String> map) {

    }

}
