package com.liangxunwang.unimanager.mvc.app;

import com.liangxunwang.unimanager.model.Area;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.query.MemberQuery;
import com.liangxunwang.unimanager.query.UpdateCollegeQuery;
import com.liangxunwang.unimanager.service.ListService;
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
 * Created by Administrator on 2015/8/17.
 */
@Controller
public class AppUpdateCollegeController extends ControllerConstants {
    @Autowired
    @Qualifier("appMemberService")
    private UpdateService appMemberService;

    @RequestMapping(value = "/updateCollegeById", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String updateCollegeById(UpdateCollegeQuery query){
        try {
            appMemberService.update(query);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }
}
