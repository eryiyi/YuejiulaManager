package com.liangxunwang.unimanager.mvc.member;

import com.liangxunwang.unimanager.model.Visitor;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.mvc.vo.VisitorVO;
import com.liangxunwang.unimanager.query.VisitorQuery;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.SaveService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.ControllerConstants;
import com.liangxunwang.unimanager.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by liuzwei on 2015/2/3.
 */
@Controller
public class VisitorController extends ControllerConstants {

    @Autowired
    @Qualifier("visitorService")
    private SaveService saveVisitorService;

    @Autowired
    @Qualifier("visitorService")
    private ListService listVisitorService;

    /**
     * 保存访客数据
     * @param visitor
     * @return
     */
    @RequestMapping("/saveVisitor")
    @ResponseBody
    public String saveVisitor(Visitor visitor){
        try {
            saveVisitorService.save(visitor);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }

    /**
     * 根据ID查找访客
     * @param query
     *
     * @return
     */
    @RequestMapping(value = "/listVisitor", produces="text/plain;charset=UTF-8")
    @ResponseBody
    public String listVisitor(VisitorQuery query, Page page){
        query.setIndex(page.getPage()==0?1:page.getPage());
        query.setSize(query.getSize()==0?page.getDefaultSize():query.getSize());
        try {
            List<VisitorVO> list = (List<VisitorVO>) listVisitorService.list(query);
            DataTip tip = new DataTip();
            tip.setData(list);
            return toJSONString(tip);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }
}
