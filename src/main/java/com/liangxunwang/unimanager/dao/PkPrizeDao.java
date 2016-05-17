package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.PkPrize;
import com.liangxunwang.unimanager.mvc.vo.PkPrizeVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by liuzwei on 15-4-6.
 */
@Repository("pkPrizeDao")
public interface PkPrizeDao {

    /**
     * 添加奖品
     * @param pkPrize
     */
    void save(PkPrize pkPrize);

    /**
     * 检查某个活动有没有设置奖品
     * @param themeId
     * @return
     */
    PkPrize findByThemeId(@Param(value = "themeId")String themeId, @Param(value = "type")String type);

    /**
     * 商家添加奖品时校验有没有添加过
     * @param themeId
     * @param schoolId
     * @param type
     * @return
     */
    PkPrize findBySchoolIdAndThemeId(@Param(value = "themeId")String themeId, @Param(value = "schoolId")String schoolId, @Param(value = "type")String type);

    /**
     * 根据ID删除奖品
     * @param id
     */
    void deleteById(String id);

    /**
     * 根据代理商的ID查找所有的奖品设置
     * @param map
     * @return
     */
    List<PkPrizeVO> findByEmpId (Map<String, Object> map);

}
