package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.PKZan;
import com.liangxunwang.unimanager.mvc.vo.PkZanVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by liuzwei on 15-4-5.
 */
@Repository("pkZanDao")
public interface PkZanDao {
    /**
     * 保存投票
     * @param zan
     */
    void save(PKZan zan);

    /**
     * 检查某个会员有没有给这个作品投过票
     * @param empId
     * @param zpId
     * @return
     */
    PKZan checkIsZan(@Param(value = "empId")String empId, @Param(value = "zpId")String zpId);

    List<PkZanVO> listPkZan(Map<String, Object> map);

    long count(Map<String,Object> map);
}
