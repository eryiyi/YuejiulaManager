package com.liangxunwang.unimanager.mvc.member;

import com.liangxunwang.unimanager.service.ExecuteService;
import com.liangxunwang.unimanager.service.FindService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.Constants;
import com.liangxunwang.unimanager.util.ControllerConstants;
import com.liangxunwang.unimanager.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by liuzwei on 2015/2/8.
 */
@Controller
public class FindPasswordController extends ControllerConstants {

    @Autowired
    @Qualifier("findPasswordService")
    private FindService findPasswordService;

    @Autowired
    @Qualifier("findPasswordService")
    private ExecuteService executePasswordService;

    @RequestMapping("/getCheckCode")
    @ResponseBody
    public String getCheckCode(String phoneNumber){
        if (StringUtil.isNullOrEmpty(phoneNumber)){
            return toJSONString(ERROR_1);
        }
        try {
            findPasswordService.findById(phoneNumber);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            String msg = e.getMessage();
            if (msg.equals("NOT_EXISTS")){
                return toJSONString(ERROR_2);//该手机号没有注册
            }else if (msg.equals(Constants.HAS_CODE)){
                return toJSONString(ERROR_3);//验证码已发送到手机
            }else {
                return toJSONString(ERROR_4);//验证码发送失败
            }
        }
    }

    @RequestMapping("/resetPassword")
    @ResponseBody
    public String resetPassword(String phoneNumber,String password){
        if (StringUtil.isNullOrEmpty(phoneNumber)){
            return toJSONString(ERROR_1);
        }
        if (StringUtil.isNullOrEmpty(password)){
            return toJSONString(ERROR_3);
        }
        Object[] params = new Object[]{phoneNumber, password};
        try {
            executePasswordService.execute(params);
        }catch (ServiceException e){
            String msg = e.getMessage();
            if (msg.equals(Constants.NO_SEND_CODE)){
                return toJSONString(ERROR_4);
            }
            if (msg.equals(Constants.CODE_NOT_EQUAL)){
                return toJSONString(ERROR_5);
            }
            if (msg.equals("NOT_EXISTS")){
                return toJSONString(ERROR_6);
            }else {
                return toJSONString(ERROR_7);
            }
        }
        return toJSONString(SUCCESS);
    }
}
