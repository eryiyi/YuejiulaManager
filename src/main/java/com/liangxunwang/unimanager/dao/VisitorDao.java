package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.Visitor;
import com.liangxunwang.unimanager.mvc.vo.VisitorVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by liuzwei on 2015/2/3.
 */
@Repository("visitorDao")
public interface VisitorDao {

    /**
     * 插入访客记录
     * @param visitor
     */
    public void save(Visitor visitor);

    public Visitor findByParam(@Param(value = "empOne")String empOne, @Param(value = "empTwo")String empTwo);

    /**
     * 更新访问时间
     * @param id
     * @param time
     */
    public void updateTime(@Param(value = "vid") String vid, @Param(value = "time")String time);

    /**
     * 根据ID查找访客
     * @param map
     * @return
     */
    public List<VisitorVO> listVisitor(Map<String,Object> map);
}
