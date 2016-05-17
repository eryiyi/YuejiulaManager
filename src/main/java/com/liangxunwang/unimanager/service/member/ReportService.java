package com.liangxunwang.unimanager.service.member;

import com.liangxunwang.unimanager.dao.ReportDao;
import com.liangxunwang.unimanager.model.Report;
import com.liangxunwang.unimanager.mvc.vo.ReportVO;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.SaveService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.service.UpdateService;
import com.liangxunwang.unimanager.util.RelativeDateFormat;
import com.liangxunwang.unimanager.util.UUIDFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liuzwei on 2015/2/3.
 * 举报service
 */
@Service("reportService")
public class ReportService implements SaveService, ListService, UpdateService{
    @Autowired
    @Qualifier("reportDao")
    private ReportDao reportDao;


    @Override
    public Object save(Object object) throws ServiceException {
        Report report = (Report) object;
        report.setId(UUIDFactory.random());
        report.setDateline(System.currentTimeMillis()+"");
        report.setIsDel("0");// 是否处理  0否  1是
        reportDao.save(report);
        return report;
    }

    @Override
    public Object list(Object object) throws ServiceException {
        List<ReportVO> list = reportDao.list();
        for (ReportVO vo : list){
            vo.setDateline(RelativeDateFormat.format(Long.parseLong(vo.getDateline())));
        }
        return list;
    }

    @Override
    public Object update(Object object) {
        String reportId = (String) object;
        reportDao.update(reportId);
        return null;
    }
}
