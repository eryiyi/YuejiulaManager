package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.Count;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by liuzwei on 2015/2/4.
 */
@Repository("countDao")
public interface CountDao {

    public void save(Count count);

    public void update(@Param(value = "empId")String empId,@Param(value = "type") String type);

    void updateScore(@Param(value = "empId")String empId, @Param(value = "count")String count);
}
