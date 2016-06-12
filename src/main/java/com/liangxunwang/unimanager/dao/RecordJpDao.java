package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.AdObj;
import com.liangxunwang.unimanager.model.RecordJp;
import com.liangxunwang.unimanager.mvc.vo.RecordJpVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/1/29.
 */
@Repository("recordJpDao")
public interface RecordJpDao {
    /**
     * 查询
     */
    List<RecordJpVO> lists(Map<String, Object> map);

    //保存
    void save(RecordJp recordJp);

    /**
     * 根据ID查找
     * @param school_record_jp_id
     * @return
     */
    public RecordJp findById(String school_record_jp_id);

}
