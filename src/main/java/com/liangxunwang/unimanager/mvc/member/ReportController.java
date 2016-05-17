package com.liangxunwang.unimanager.mvc.member;

import com.liangxunwang.unimanager.model.Report;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.mvc.vo.ReportVO;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.SaveService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.service.UpdateService;
import com.liangxunwang.unimanager.util.ControllerConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by liuzwei on 2015/2/3.
 */
@Controller
public class ReportController extends ControllerConstants{

    @Autowired
    @Qualifier("reportService")
    private SaveService saveReportService;

    @Autowired
    @Qualifier("reportService")
    private ListService listReportService;

    @Autowired
    @Qualifier("reportService")
    private UpdateService updateReportService;
    /**
     * 举报
     * @param report
     * @return
     */
    @RequestMapping("/report")
    @ResponseBody
    public String report(Report report){
        try {
            saveReportService.save(report);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }

    /**
     * 获取所有未处理的举报
     * @return
     */
    @RequestMapping(value = "/listReport", produces = "text/plain;charset=UTF-8;")
    @ResponseBody
    public String listReport(){
        try {
            List<ReportVO> list = (List<ReportVO>) listReportService.list(null);
            DataTip tip = new DataTip();
            tip.setData(list);
            return toJSONString(tip);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }

    @RequestMapping("/manageReport")
    @ResponseBody
    public String manageReport(String reportId){
        try {
            updateReportService.update(reportId);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }
}
