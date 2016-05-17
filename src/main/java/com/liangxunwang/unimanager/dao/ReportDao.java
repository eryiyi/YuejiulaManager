package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.Report;
import com.liangxunwang.unimanager.mvc.vo.ReportVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liuzwei on 2015/2/3.
 */
@Repository("reportDao")
public interface ReportDao {
    /**
     * 插入一条举报信息
     * @param report
     */
    void save(Report report);

    /**
     * 查找没有处理的举报
     * @return
     */
    List<ReportVO> list();

    void update(String reportId);
}
