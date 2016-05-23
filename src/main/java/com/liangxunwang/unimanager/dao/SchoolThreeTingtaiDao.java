package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.Notice;
import com.liangxunwang.unimanager.model.SchoolThreeTingtai;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/2/5.
 */
@Repository("schoolThreeTingtaiDao")
public interface SchoolThreeTingtaiDao {

    public void save(SchoolThreeTingtai schoolThreeTingtai);

    public List<SchoolThreeTingtai> list(Map<String, Object> map);

    public SchoolThreeTingtai findById(String school_three_pingtai_id);

    public void update(SchoolThreeTingtai schoolThreeTingtai);

    public void delete(String school_three_pingtai_id);
}
