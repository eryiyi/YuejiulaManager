package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.DailiObj;
import com.liangxunwang.unimanager.mvc.vo.DailiObjVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/1/31.
 */
@Repository("dailiObjDao")
public interface DailiObjDao {

    /**
     * 保存
     * @param dailiObj
     */
    public void save(DailiObj dailiObj);

    /**
     * 查找集合
     * @param map
     * @return
     */
    public List<DailiObjVO> list(Map<String, Object> map);

    /**
     * 根据ID删除
     */
    public void deleteById(DailiObj dailiObj);
}
