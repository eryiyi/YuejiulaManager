package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.City;
import com.liangxunwang.unimanager.model.Province;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liuzwei on 2015/2/25.
 */
@Repository("appCityDao")
public interface AppCityDao {
    public List<City> list(String provinceid);
}
