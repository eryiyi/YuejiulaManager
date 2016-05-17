package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.Viewpager;
import com.liangxunwang.unimanager.mvc.vo.ViewpagerVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by liuzh on 2015/8/26.
 */
@Repository("viewpagerDao")
public interface ViewpagerDao {

    /**
     * 保存一个轮播项
     * @param viewpager
     */
    void save(Viewpager viewpager);

    /**
     * 根据ID删除一个轮播项
     * @param id
     */
    void delete(String id);

    /**
     * 查找所有的轮播项
     * @return
     */
    List<ViewpagerVO> list(Map<String,Object> map);

}
