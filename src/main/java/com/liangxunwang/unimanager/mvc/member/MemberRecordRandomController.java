package com.liangxunwang.unimanager.mvc.member;

import com.liangxunwang.unimanager.service.SaveService;
import com.liangxunwang.unimanager.service.UpdateService;
import com.liangxunwang.unimanager.util.ControllerConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Server on 2015/7/24.
 */
@Controller
public class MemberRecordRandomController extends ControllerConstants {

    @Autowired
    @Qualifier("memberRecordRandomService")
    private UpdateService memberRecordRandomService;

    @RequestMapping("/toRecord")
    public String toRecord(){
        memberRecordRandomService.update(null);
        return "/record/toRecord";
    }

}
