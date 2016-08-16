package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.BigAreaObj;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/1/29.
 */
@Repository("bigAreaDao")
public interface BigAreaDao {

    /**
     * 查询
     */
    List<BigAreaObj> lists(Map<String, Object> map);

    //保存
    void save(BigAreaObj bigAreaObj);

    /**
     * 根据ID查找
     * @param area_id
     * @return
     */
    public BigAreaObj findById(String area_id);

    /**
     * 更新
     * @param bigAreaObj
     */
    public void update(BigAreaObj bigAreaObj);
}
