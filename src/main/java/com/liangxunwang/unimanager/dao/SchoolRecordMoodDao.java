package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.College;
import com.liangxunwang.unimanager.model.SchoolRecordMood;
import com.liangxunwang.unimanager.mvc.vo.SchoolVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/2/25.
 */
@Repository("schoolRecordMoodDao")
public interface SchoolRecordMoodDao {
    /**
     */
    List<SchoolRecordMood> list(Map<String, Object> map);

    //后台分页
    public List<SchoolRecordMood> lists(Map<String, Object> map);
    public long count(Map<String, Object> map);

    //保存
    void save(SchoolRecordMood schoolRecordMood);

    void deleteById(String school_record_mood_id);

}
