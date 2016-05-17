package com.liangxunwang.unimanager.mvc.member;

import com.liangxunwang.unimanager.model.Relate;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.mvc.vo.RelateVO;
import com.liangxunwang.unimanager.query.RelateQuery;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.ControllerConstants;
import com.liangxunwang.unimanager.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by liuzwei on 2015/2/8.
 */
@Controller
public class RelateController extends ControllerConstants {

    @Autowired
    @Qualifier("relateService")
    private ListService relateListService;

    @RequestMapping(value = "/listRelate", produces = "text/plain;charset=UTF-8;")
    @ResponseBody
    public String listAndme(RelateQuery query, Page page){
        query.setIndex(page.getPage() == 0 ? 1 : page.getPage());
        query.setSize(query.getSize() == 0 ? page.getDefaultSize() : query.getSize());
        try {
            List<RelateVO> relates = (List<RelateVO>) relateListService.list(query);
            DataTip dataTip = new DataTip();
            dataTip.setData(relates);
            return toJSONString(dataTip);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }

    }
}
