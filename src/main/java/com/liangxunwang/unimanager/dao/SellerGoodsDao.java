package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.SellerGoods;
import com.liangxunwang.unimanager.model.SettlementSellers;
import com.liangxunwang.unimanager.mvc.vo.SellerGoodsVO;
import com.liangxunwang.unimanager.mvc.vo.SellerSchoolList;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by liuzwei on 2015/3/24.
 */
@Repository("sellerGoodsDao")
public interface SellerGoodsDao {
    /**
     * 批量插入商家
     * @param list
     */
    void saveList(List<SellerGoods> list);

    List<SellerGoods> getSellerGoodsById(@Param(value = "empId") String empId, @Param(value = "contractId") String contractId);
    List<SellerGoods> getSellerGoodsByEmpId(@Param(value = "empId") String empId);

    void delete(@Param(value = "empId")String empId, @Param(value = "contractId")String contractId);

    List<SellerGoods> findByEmpId(String empId);

    /**
     * 根据承包商的会员ID查找该承包商有多少商家
     * @param map
     * @return
     */
    List<SellerGoodsVO> findSellerById(Map<String,Object> map);


    long count(Map<String, Object> map);

    /**
     * 根据商家ID和承包商ID查找商家下面有几个学校
     * @param empId
     * @param contractId
     * @return
     */
    List<SellerSchoolList> findSellerSchoolList(@Param(value = "empId")String empId, @Param(value = "contractId")String contractId);

    /**
     * 查找该商家下的所有学校
     * @param empId
     * @return
     */
    List<SellerSchoolList> findSellerSchoolListById(@Param(value = "empId")String empId);

    /**
     * 根据ID删除商家学校
     * @param id
     */
    void deleteById(String id);

    /**
     * 根据ID更新到期时间
     * @param id
     * @param endTime
     */
    void updateEndTime(@Param(value = "id")String id, @Param(value = "endTime")String endTime);

    /**
     * 查找过期的商家
     * @param nowTime
     * @return
     */
    List<SellerGoods> getEndSeller(String nowTime);

    /**
     * 根据会员ID，查找有没有没过期的学校
     * @param empId
     * @param time
     * @return
     */
    List<SellerGoods> getCheckSeller(@Param(value = "empId")String empId, @Param(value = "time")String time);

    /**
     * 根据学校ID查找该学校下的所有商家
     * @param schoolId
     * @return
     */
    List<SellerGoods> findBySchoolId(String schoolId);

    /**
     * 查找该商家是不是其他学校的商家
     * @param empId
     * @param schoolId
     * @return
     */
    List<SellerGoods> getSellerByEmpAndSchool(@Param(value = "empId")String empId,@Param(value = "schoolId") String schoolId);

    /**
     * 根据商家会员ID和学校ID查询该商家的详细信息
     * @param empId
     * @param schoolId
     * @return
     */
    SellerGoodsVO findByEmpContract(@Param(value = "empId")String empId, @Param(value = "schoolId") String schoolId);

    List<SettlementSellers> settlementSellers(Map<String,Object> map);

    long settlementSellersCount(Map<String,Object> map);
}
