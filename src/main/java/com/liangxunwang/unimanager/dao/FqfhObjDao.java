package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.FhFqObj;
import com.liangxunwang.unimanager.mvc.vo.FhFqObjVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/1/29.
 */
@Repository("fqfhObjDao")
public interface FqfhObjDao {

    /**
     * 查询
     */
    List<FhFqObjVO> lists(Map<String, Object> map);

    //保存
    void save(FhFqObj adObj);

    //删除
    void delete(String fhfq_id);

    //删除
    void deleteByEmpid(FhFqObj adObj);

    /**
     * 根据ID查找
     * @param fhfq_id
     * @return
     */
    public FhFqObj findById(String fhfq_id);

}
