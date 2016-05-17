package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liuzh on 2015/8/8.
 */
@Repository("roleDao")
public interface RoleDao {
    /**
     * 查询所有的角色
     * @return
     */
    List<Role> list();

    /**
     * 保存角色
     * @param role
     */
    void save(Role role);

    /**
     * 删除角色
     * @param id
     */
    void delete(String id);

    Role find(String id);

    void update(Role role);
}
