package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.ManagerInfo;
import org.springframework.stereotype.Repository;

/**
 * Created by liuzh on 2015/8/30.
 */
@Repository("managerInfoDao")
public interface ManagerInfoDao {
    /**
     * 保存后台登陆者的信息
     * @param info
     */
    void save(ManagerInfo info);

    /**
     * 更新信息
     * @param info
     */
    void update(ManagerInfo info);

    /**
     * 根据登陆者的manageId查找信息
     * @param id
     * @return
     */
    ManagerInfo findById(String id);
}
