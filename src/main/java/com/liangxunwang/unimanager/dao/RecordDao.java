package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.Record;
import com.liangxunwang.unimanager.mvc.vo.RecordVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by liuzwei on 2015/2/2.
 */
@Repository("recordDao")
public interface RecordDao {
    /**
     * 分页查询动态记录
     * @param map
     * @return
     */
    List<RecordVO> list(Map<String, Object> map);
    //推荐精彩
    List<RecordVO> listTwo(Map<String, Object> map);

    /**
     * 保存一条动态记录
     * @param record
     */
    void save(Record record);

    /**
     * 根据ID查找动态
     * @param id
     * @return
     */
    RecordVO findById(String id);

    /**
     * 根据学校ID查找推广
     * @param schoolId
     * @return
     */
    RecordVO findBySchoolId(String schoolId);

    /**
     * 根据ID删除动态
     * @param id
     */
    void deleteById(String id);


    /**
     * 根据会员ID和动态类别查询推广
     * @param empId
     * @param type
     * @return
     */
    List<Record> findByEmpIdAndType(@Param(value = "empId")String empId, @Param(value = "type")String type);

    void deletePromotion(String rmpId);

    /**
     * 根据学校ID删除该学校下的推广
     * @param schoolId
     */
    void deleteBySchoolId(String schoolId);

}
