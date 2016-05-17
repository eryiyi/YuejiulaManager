package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.PKWork;
import com.liangxunwang.unimanager.mvc.vo.PkWorkVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by liuzwei on 15-4-4.
 */
@Repository("workDao")
public interface WorkDao {
    /**
     * 添加作品
     * @param work
     */
    void saveZp(PKWork work);

    PKWork findByZtIdAndEmpId(@Param(value = "empId")String empId, @Param(value = "ztId")String ztId);

    /**
     * 条件查询所有的参赛作品
     * @param map
     * @return
     */
    List<PkWorkVO> listWork(Map<String,Object> map);

    /**
     * 查找总冠军
     * @param themeId
     * @return
     */
    PkWorkVO findQueue(String themeId);

    /**
     * 根据作品ID查找作品的详细信息
     * @param id
     * @return
     */
    PkWorkVO findWorkVo(String id);

    /**
     * 查找各个学校的冠军
     * @param themeId
     * @return
     */
    List<PkWorkVO> findAllSchoolQueue(String themeId);

    void deleteByZpId(String zpId);

    /**
     * 根据workId查找作品
     * @param workId
     * @return
     */
    PKWork findWorkById(String workId);

    /**
     * 条件查询所有的参赛作品
     * @param map
     * @return
     */
    List<PkWorkVO> listWork2(Map<String,Object> map);

}
