package com.liangxunwang.unimanager.mvc.member;

import com.liangxunwang.unimanager.model.DailiObj;
import com.liangxunwang.unimanager.model.FhFqObj;
import com.liangxunwang.unimanager.model.Member;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.mvc.vo.ContractSchoolVO;
import com.liangxunwang.unimanager.mvc.vo.DailiObjVO;
import com.liangxunwang.unimanager.mvc.vo.FhFqObjVO;
import com.liangxunwang.unimanager.mvc.vo.MemberVO;
import com.liangxunwang.unimanager.query.DailiObjQuery;
import com.liangxunwang.unimanager.query.FenghaofengqunQuery;
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
 * Created by zhl on 2015/2/8.
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
    public String updatePushId(@RequestParam String id, @RequestParam String pushId, @RequestParam String type, @RequestParam String channelId){
        if (StringUtil.isNullOrEmpty(id) || StringUtil.isNullOrEmpty(pushId) || StringUtil.isNullOrEmpty(type)|| StringUtil.isNullOrEmpty(channelId)){
            return toJSONString(ERROR_1);
        }
        Object[] params = new Object[]{id, pushId, type, channelId};
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


    @Autowired
    @Qualifier("contractSchoolMemberService")
    private ListService contractSchoolMemberService;
    @RequestMapping(value = "/getManagerById", produces = "text/plain;charset=UTF-8;")
    @ResponseBody
    public String getManagerById(String school_id){
        try {
            List<ContractSchoolVO> list= (List<ContractSchoolVO>) contractSchoolMemberService.list(school_id);
            DataTip tip = new DataTip();
            tip.setData(list);
            return toJSONString(tip);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }


    @Autowired
    @Qualifier("memberUpdateFenghaoService")
    private UpdateService memberUpdateFenghaoService;

    @RequestMapping("/updateEmpIsFenghao")
    @ResponseBody
    public String updateEmpIsFenghao(FhFqObj fhFqObj){
        try {
            memberUpdateFenghaoService.update(fhFqObj);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }
    @RequestMapping("/updateEmpIsFengQun")
    @ResponseBody
    public String updateEmpIsFengQun(FhFqObj fhFqObj){
        try {
            memberUpdateFenghaoService.update(fhFqObj);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }



    @Autowired
    @Qualifier("memberFenghfqService")
    private ListService memberFenghfqService;

    //获得封群封号
    @RequestMapping(value = "/getFenghaofengquns", produces = "text/plain;charset=UTF-8;")
    @ResponseBody
    public String getFenghaofengquns(FenghaofengqunQuery query){
        try {
            List<FhFqObjVO> list = (List<FhFqObjVO>) memberFenghfqService.list(query);
            DataTip tip = new DataTip();
            tip.setData(list);
            return toJSONString(tip);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }


    @RequestMapping(value = "/listsMineFhFq", produces = "text/plain;charset=UTF-8;")
    @ResponseBody
    public String listsMineFhFq(FenghaofengqunQuery query){
        try {
            List<FhFqObjVO> list = (List<FhFqObjVO>) memberFenghfqService.list(query);
            DataTip tip = new DataTip();
            tip.setData(list);
            return toJSONString(tip);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }



    @Autowired
    @Qualifier("dailiObjObjService")
    private SaveService dailiObjObjService;

    @RequestMapping("/saveDaili")
    @ResponseBody
    public String saveDaili(DailiObj dailiObj){
        try {
            dailiObjObjService.save(dailiObj);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            String emsg = e.getMessage();
            if (emsg.equals("adIsTooMuch")){
                return toJSONString(ERROR_2);
            }else{
                return toJSONString(ERROR_1);
            }
        }
    }


    @Autowired
    @Qualifier("dailiObjObjService")
    private ListService dailiObjObjServiceLst;

    @RequestMapping(value = "/listDaili", produces = "text/plain;charset=UTF-8;")
    @ResponseBody
    public String listDaili(DailiObjQuery query){
        try {
            List<DailiObjVO> list = (List<DailiObjVO>) dailiObjObjServiceLst.list(query);
            DataTip tip = new DataTip();
            tip.setData(list);
            return toJSONString(tip);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }

    @Autowired
    @Qualifier("dailiObjObjService")
    private DeleteService dailiObjObjServiceDel;

    @RequestMapping("/deleDaili")
    @ResponseBody
    public String deleDaili(DailiObj dailiObj){
        try {
            dailiObjObjServiceDel.delete(dailiObj);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            String emsg = e.getMessage();
            if (emsg.equals("adIsTooMuch")){
                return toJSONString(ERROR_2);
            }else{
                return toJSONString(ERROR_1);
            }
        }
    }

}
