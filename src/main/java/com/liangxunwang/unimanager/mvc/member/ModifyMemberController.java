package com.liangxunwang.unimanager.mvc.member;

import com.liangxunwang.unimanager.model.Member;
import com.liangxunwang.unimanager.service.ExecuteService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.service.UpdateService;
import com.liangxunwang.unimanager.util.Constants;
import com.liangxunwang.unimanager.util.ControllerConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by liuzwei on 2015/2/5.
 */
@Controller
public class ModifyMemberController extends ControllerConstants{

    @Autowired
    @Qualifier("memberService")
    private UpdateService updateMemberService;

    @Autowired
    @Qualifier("modifyMemberService")
    private ExecuteService modifyMemberExecuteService;

    @Autowired
    @Qualifier("modifyMemberService")
    private UpdateService modifyMemberUpdateService;

    @RequestMapping("/modifyMember")
    @ResponseBody
    public String modifyMember(Member member){
        try {
            updateMemberService.update(member);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }

    /**
     * 重设密码
     * @param empId  用户ID
     * @param pass  原密码
     * @param rePass  新密码
     * @return
     */
    @RequestMapping("/resetPass")
    @ResponseBody
    public String resetPassword(String empId, String pass, String rePass){
        Object[] params = new Object[]{empId, pass, rePass};
        try {
            modifyMemberExecuteService.execute(params);

        }catch (ServiceException e){
            String msg = e.getMessage();
            if (msg.equals("PassError")){
                return toJSONString(ERROR_1);
            }
            if (msg.equals("NoPeople")){
                return toJSONString(ERROR_2);
            }
            if(msg.equals(Constants.HX_ERROR)){
                return toJSONString(ERROR_4);
            }
            return toJSONString(ERROR_3);
        }
        return toJSONString(SUCCESS);
    }

    /**
     * 修改手机号
     * @return
     */
    @RequestMapping("/resetMobile")
    @ResponseBody
    public String resetMobile(String mobile, String reMobile,String empId){
        Object[] params = new Object[]{mobile, reMobile,empId};
        try {
            modifyMemberUpdateService.update(params);

        }catch (ServiceException e){
            String msg=  e.getMessage();
            if (msg.equals("NoThisMobile")){
                return toJSONString(ERROR_1);//原手机号没有注册
            }
            if (msg.equals("NoOwnAccount")){
                return toJSONString(ERROR_2);//手机号和当前登陆账号不匹配
            }
            if (msg.equals(Constants.NO_SEND_CODE)){
                return toJSONString(ERROR_3);//没有发送验证码
            }
            if (msg.equals(Constants.HAS_EXISTS)){
                return toJSONString(ERROR_6);//重设手机号已经是注册用户
            }
            if (msg.equals(Constants.CODE_NOT_EQUAL)){
                return toJSONString(ERROR_4);// 验证码不匹配
            }else {
                return toJSONString(ERROR_5);//修改失败
            }

        }
        return toJSONString(SUCCESS);
    }


}
