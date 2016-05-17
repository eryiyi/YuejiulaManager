package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.University;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liuzwei on 2015/1/30.
 */
@Repository("universityDao")
public interface UniversityDao {

    public void save(University university);

    /**
     * 根据学校名称查找数据
     * @param name
     * @return
     */
    public University findByName(String name);

    /**
     * 查询所有的学校
     * @return
     */
    public List<University> list();

    /**
     * 查找所有可用学校
     * @return
     */
    public List<University> listAll();

    /**
     * 查询附近的学校
     * @param schoolId
     * @return
     */
    public List<University> listNearby(String schoolId);

    /**
     * 根据ID查找学校
     * @param schoolId
     * @return
     */
    public University findById(String schoolId);

    /**
     * 更新学校
     * @param university
     */
    public void update(University university);

    /**
     * 根据ID删除学校
     * @param schoolId
     */
    public void deleteById(String schoolId);

    /**
     * 查找当前可用的大学
     * @return
     */
    public List<University> listIsUse();
}
