package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.ContractSchool;
import com.liangxunwang.unimanager.mvc.vo.ContractSchoolVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/3/24.
 */
@Repository("contractSchoolDao")
public interface ContractSchoolDao {

    List<ContractSchoolVO> listByEmpId(String empId);

    void save(ContractSchool contractSchool);

    void delete(String id);

    ContractSchool findBySchoolId(String schoolId);

    /**
     * 根据ID查找
     * @param id
     * @return
     */
    ContractSchool findById(String id);

    List<ContractSchool> findContractSchoolByEmpId(String empId);

    /**
     * 查找所有的圈主
     * @param map
     * @return
     */
    List<ContractSchoolVO> listAllContract(Map<String,Object> map);

    /**
     * 根据当前时间查找有没有过期的圈主
     * @param nowTime
     * @return
     */
    List<ContractSchool> findEndTime(String nowTime);

    /**
     * 根据会员ID和时间查找该圈主还有没有没有到期的承包圈子
     * @param empId
     * @param nowTime
     * @return
     */
    List<ContractSchool> findByEmpAndEndTime(@Param(value = "empId")String empId,@Param(value = "nowTime") String nowTime);

    long count(Map<String, Object> map);

    //根据圈子id查询圈主
    List<ContractSchoolVO> getManagerById(String school_id);
}
