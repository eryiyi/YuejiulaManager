package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.PartTimeType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liuzwei on 2015/2/7.
 */
@Repository("partTimeTypeDao")
public interface PartTimeTypeDao {
    public void save(PartTimeType type);
    public List<PartTimeType> list();
    public void delete(String id);
}
