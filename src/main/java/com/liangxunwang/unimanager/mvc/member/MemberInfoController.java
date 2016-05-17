package com.liangxunwang.unimanager.mvc.member;

import com.liangxunwang.unimanager.model.Member;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.mvc.vo.MemberVO;
import com.liangxunwang.unimanager.query.MemberQuery;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.ControllerConstants;
import com.liangxunwang.unimanager.util.Page;
import com.liangxunwang.unimanager.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by liuzwei on 2015/2/8.
 */
@Controller
public class MemberInfoController extends ControllerConstants {
    @Autowired
    @Qualifier("memberInfoService")
    private ExecuteService listMemberInfoService;

    @Autowired
    @Qualifier("memberInfoService")
    private FindService findMemberService;

    @Autowired
    @Qualifier("memberInfoService")
    private UpdateService updateMemberService;

    @Autowired
    @Qualifier("memberInfoService")
    private ListService memberInfoListService;

    @RequestMapping(value = "/listMemberInfo", produces = "text/plain;charset=UTF-8;")
    @ResponseBody
    public String getMemberInfo(String phoneStr){
        try {
            List<Member> list = (List<Member>) listMemberInfoService.execute(phoneStr);
            DataTip tip = new DataTip();
            tip.setData(list);
            return toJSONString(tip);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }

    @RequestMapping(value = "/getMemberInfoById", produces = "text/plain;charset=UTF-8;")
    @ResponseBody
    public String getMemberInfoById(String empId){
        try {
            MemberVO memberVO = (MemberVO) findMemberService.findById(empId);
            DataTip tip = new DataTip();
            tip.setData(memberVO);
            return toJSONString(tip);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }

    @RequestMapping("/updatePushId")
    @ResponseBody
    public String updatePushId(@RequestParam String id, @RequestParam String pushId, @RequestParam String type){
        if (StringUtil.isNullOrEmpty(id) || StringUtil.isNullOrEmpty(pushId) || StringUtil.isNullOrEmpty(type)){
            return toJSONString(ERROR_1);
        }
        Object[] params = new Object[]{id, pushId, type};
        try {
            updateMemberService.update(params);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);

        }
    }

    @RequestMapping(value = "/searchMember", produces = "text/plain;charset=UTF-8;")
    @ResponseBody
    public String searchMember(MemberQuery query, Page page){
        try {
            query.setIndex(page.getPage() == 0 ? 1 : page.getPage());
            query.setSize(query.getSize() == 0 ? page.getDefaultSize() : query.getSize());
            Object[] params = new Object[]{query, page.getPage()};
            List<Member> list = (List<Member>) memberInfoListService.list(params);
            DataTip tip = new DataTip();
            tip.setData(list);
            return toJSONString(tip);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }

    @RequestMapping(value = "/listInviteMemberInfo", produces = "text/plain;charset=UTF-8;")
    @ResponseBody
    public String getInviteMemberInfo(String hxUserNames){
        try {
            Object[] params = new Object[]{hxUserNames};
            List<Member> list = (List<Member>) listMemberInfoService.execute(params);
            DataTip tip = new DataTip();
            tip.setData(list);
            return toJSONString(tip);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }
}
