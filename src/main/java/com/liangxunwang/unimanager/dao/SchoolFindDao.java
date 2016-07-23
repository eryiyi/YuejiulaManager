package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.SchoolFind;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/2/3.
 *
 */
@Repository("schoolFindDao")
public interface SchoolFindDao {

    //后台保存
    public void save(SchoolFind videos);
    //后台分页
    public List<SchoolFind> lists(Map<String, Object> map);//时间排序

    public List<SchoolFind> listsApp(Map<String, Object> map);

    public void delete(String id);

    public long count(Map<String, Object> map);

    public SchoolFind findById(String id);
}
