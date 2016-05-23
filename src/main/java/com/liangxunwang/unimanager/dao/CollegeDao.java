package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.College;
import com.liangxunwang.unimanager.mvc.vo.SchoolVO;
import com.liangxunwang.unimanager.mvc.vo.VideosVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/2/25.
 */
@Repository("collegeDao")
public interface CollegeDao {
    /**
     * 根据省份查找该省的所有学校
     * @param map
     * @return
     */
    List<College> list(Map<String, Object> map);


    void updateGroupId(@Param(value = "coid") String coid, @Param(value = "groupId") String groupId);

    College getGroupId(@Param(value = "coid") String coid);

    //后台分页
    public List<SchoolVO> lists(Map<String, Object> map);
    public long count(Map<String, Object> map);

    //保存学校
    void save(College college);

    void deleteById(String coid);

}
