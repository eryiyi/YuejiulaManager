package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.SchoolThreeTingtaiBd;
import com.liangxunwang.unimanager.mvc.vo.SchoolThreePtBdVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/2/5.
 */
@Repository("schoolThreeTingtaiBdDao")
public interface SchoolThreeTingtaiBdDao {

    public void save(SchoolThreeTingtaiBd schoolThreeTingtai);

    public List<SchoolThreePtBdVO> list(Map<String, Object> map);

    public SchoolThreeTingtaiBd findById(String emp_pingtai_mng_id);

    public void update(SchoolThreeTingtaiBd schoolThreeTingtai);

    public void delete(String emp_pingtai_mng_id);
}
