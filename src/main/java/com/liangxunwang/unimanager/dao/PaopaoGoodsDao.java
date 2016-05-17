package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.PaopaoGoods;
import com.liangxunwang.unimanager.mvc.vo.PaopaoGoodsVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by liuzh on 2015/8/16.
 */
@Repository("paopaoGoodsDao")
public interface PaopaoGoodsDao {
    /**
     * 根据商家会员ID和学校ID查询该学校已经添加了几个商品
     * @param empId
     * @param schoolId
     * @return
     */
    List<PaopaoGoods> listByEmpSchool(@Param(value = "empId")String empId, @Param(value = "schoolId") String schoolId);

    void save(PaopaoGoods goods);

    List<PaopaoGoodsVO> listGoods(Map<String,Object> map);

    Long count(Map<String, Object> map);

    /**
     * 根据ID删除我的宝贝
     * @param id
     */
    void deleteById(String id);

    /**
     * 根据ID查找宝贝
     * @param id
     * @return
     */
    PaopaoGoods findById(String id);

    void update(PaopaoGoods goods);

    PaopaoGoodsVO findGoodsVO(String id);
    /**
     * 根据商品id，减去商品数量
     * */
    void updateCountById(@Param(value = "id")String id, @Param(value = "goodsCount") String goodsCount);

    /**
     * 删除商家下的商品，至为删除状态
     * @param map
     */
    void deleteGoodsById(Map<String,Object> map);

    void deleteGoodsByEmp(@Param(value = "empId")String empId, @Param(value = "schoolId")String schoolId);

    void updatePostionById(@Param(value = "id")String id, @Param(value = "goodsPosition")String goodsPosition);

}
