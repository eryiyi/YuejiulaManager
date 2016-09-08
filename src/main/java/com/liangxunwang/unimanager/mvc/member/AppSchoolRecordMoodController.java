package com.liangxunwang.unimanager.mvc.member;

import com.liangxunwang.unimanager.model.Member;
import com.liangxunwang.unimanager.model.SchoolRecordMood;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.service.ExecuteService;
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
 * Created by zhl on 2015/2/2.
 */
@Controller
public class AppSchoolRecordMoodController extends ControllerConstants {

    @Autowired
    @Qualifier("appSchoolRecordMoodService")
    private ListService appSchoolRecordMoodService;

    @RequestMapping(value = "/listsRecodMoods", produces = "text/plain; charset=UTF-8")
    @ResponseBody
    public String listsRecodMoods(){
        try {
            List<SchoolRecordMood> list = (List<SchoolRecordMood>) appSchoolRecordMoodService.list("");
            DataTip tip = new DataTip();
            tip.setData(list);
            return toJSONString(tip);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }



    @Autowired
    @Qualifier("appEmpExeByIdService")
    private ExecuteService appEmpExeByIdService;
    //查询会员圈子的圈主的信息
    @RequestMapping(value = "/getManagerCollegeByEmpId", produces = "text/plain; charset=UTF-8")
    @ResponseBody
    public String getManagerCollegeByEmpId(String school_id){
        try {
            Member member = (Member) appEmpExeByIdService.execute(school_id);
            DataTip tip = new DataTip();
            tip.setData(member);
            return toJSONString(tip);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }
}
