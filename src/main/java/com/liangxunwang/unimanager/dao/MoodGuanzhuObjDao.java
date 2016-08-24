package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.AdObj;
import com.liangxunwang.unimanager.model.MoodGuanzhuObj;
import com.liangxunwang.unimanager.mvc.vo.MoodsGuanzhuVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/1/29.
 */
@Repository("moodGuanzhuObjDao")
public interface MoodGuanzhuObjDao {
    /**
     * 查询ad
     */
    List<MoodsGuanzhuVO> lists(Map<String, Object> map);

    //保存
    void save(MoodGuanzhuObj moodGuanzhuObj);

    //删除
    void delete(String id);

    /**
     * 根据ID查找
     * @param id
     * @return
     */
    public MoodsGuanzhuVO findById(String id);

}
