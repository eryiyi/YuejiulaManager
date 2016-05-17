package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.Champion;
import com.liangxunwang.unimanager.mvc.vo.ChampionVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by liuzwei on 2015/4/9.
 */
@Repository("championDao")
public interface ChampionDao {
    /**
     * 保存一条冠军记录
     * @param champion
     */
    void save(Champion champion);

    List<ChampionVO> listChampion(Map<String, Object> map);

    long count(Map<String, Object> map);

    /**
     * 根据ID更新获奖图片
     * @param id
     * @param pic
     */
    void updatePic(@Param(value = "id")String id, @Param(value = "pic")String pic);

    /**
     * 确认领奖
     * @param id
     */
    void championSure(String id);

    Champion getChampionById(String id);

}
