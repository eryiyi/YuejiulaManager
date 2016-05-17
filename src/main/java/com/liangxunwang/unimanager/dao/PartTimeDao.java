package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.PartTime;
import com.liangxunwang.unimanager.mvc.vo.PartTimeVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by liuzwei on 2015/2/7.
 */
@Repository("partTimeDao")
public interface PartTimeDao {
    void save(PartTime partTime);

    List<PartTimeVO> list(Map<String,Object> map);

    PartTimeVO findById(String id);

    void delete(String id);

    void notUse(String id);

    void deletePartTimeByEmp(@Param(value = "empId")String empId, @Param(value = "schoolId")String schoolId);
}
