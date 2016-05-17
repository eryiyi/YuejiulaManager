package com.liangxunwang.unimanager.mvc.member;

import com.liangxunwang.unimanager.model.University;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.ServiceException;
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
public class MemberUniversityController extends ControllerConstants {

    @Autowired
    @Qualifier("universityService")
    private ListService listUniversityService;

    @RequestMapping(value = "/nearbyUniversity", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String listUni(String schoolId){
        try {
            List<University> list = (List<University>) listUniversityService.list(schoolId);
            DataTip tip = new DataTip();
            tip.setData(list);
            return toJSONString(tip);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }
}
