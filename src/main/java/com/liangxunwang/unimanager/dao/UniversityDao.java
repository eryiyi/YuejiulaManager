package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.University;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhl on 2015/1/30.
 */
@Repository("universityDao")
public interface UniversityDao {

    public void save(University university);

    /**
     * 根据圈子名称查找数据
     * @param name
     * @return
     */
    public University findByName(String name);

    /**
     * 查询所有的圈子
     * @return
     */
    public List<University> list();

    /**
     * 查找所有可用圈子
     * @return
     */
    public List<University> listAll();

    /**
     * 查询附近的圈子
     * @param schoolId
     * @return
     */
    public List<University> listNearby(String schoolId);

    /**
     * 根据ID查找圈子
     * @param schoolId
     * @return
     */
    public University findById(String schoolId);

    /**
     * 更新圈子
     * @param university
     */
    public void update(University university);

    /**
     * 根据ID删除圈子
     * @param schoolId
     */
    public void deleteById(String schoolId);

    /**
     * 查找当前可用的大学
     * @return
     */
    public List<University> listIsUse();
}
