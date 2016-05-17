package com.liangxunwang.unimanager.mvc.member;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.liangxunwang.unimanager.model.Member;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.mvc.vo.MemberVO;
import com.liangxunwang.unimanager.service.ExecuteService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.ControllerConstants;
import com.liangxunwang.unimanager.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by liuzwei on 2015/1/29.
 */
@Controller
public class MemberLoginController extends ControllerConstants{
    /**
     * @see com.liangxunwang.unimanager.service.member.MemberLoginService
     */
    @Autowired
    @Qualifier("memberLoginService")
    private ExecuteService memberLoginService;
    /**
     * 会员登录
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @RequestMapping(value = "/memberLogin" ,produces="text/plain;charset=UTF-8")
    @ResponseBody
    public String memberLogin(@RequestParam String username, @RequestParam String password){
        if (StringUtil.isNullOrEmpty(username)){
            return toJSONString(ERROR_4);
        }
        if (StringUtil.isNullOrEmpty(password)){
            return toJSONString(ERROR_5);
        }
        Object[] params = new Object[]{username, password};
        MemberVO member = null;
        try {
            member = (MemberVO) memberLoginService.execute(params);

        }catch (ServiceException e){
            String emsg = e.getMessage();
            if (emsg.equals("NotFound")){
                return toJSONString(ERROR_1);
            }
            if (emsg.equals("PassError")){
                return toJSONString(ERROR_2);
            }
            if (emsg.equals("NotUse")){
                return toJSONString(ERROR_3);
            }
        }
        DataTip tip = new DataTip();
        tip.setData(member);
        //环信登陆

        return toJSONString(tip);
    }
}
