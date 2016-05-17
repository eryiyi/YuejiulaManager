package com.liangxunwang.unimanager.mvc.admin;

import com.liangxunwang.unimanager.model.Admin;
import com.liangxunwang.unimanager.service.ExecuteService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.ControllerConstants;
import com.liangxunwang.unimanager.util.StringUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by liuzwei on 2015/1/29.
 */
@Controller
public class AdminLoginController extends ControllerConstants {

    @Autowired
    @Qualifier("adminLoginService")
    private ExecuteService adminLoginService;

    @RequestMapping("/adminLogin")
    @ResponseBody
    public String adminLogin(HttpSession session,HttpServletRequest request, @Param(value = "username")String username,@Param(value = "password") String password){
        if (StringUtil.isNullOrEmpty(username)){
            return toJSONString(ERROR_1);
        }
        if (StringUtil.isNullOrEmpty(password)){
            return toJSONString(ERROR_2);
        }
        Object[] params = new Object[]{username, password};

        try {
            Object[] results = (Object[]) adminLoginService.execute(params);
            Admin admin = (Admin) results[0];
            session.setAttribute(ControllerConstants.ACCOUNT_KEY, admin);
            String permissions = (String) results[1];
            if ("3".equals(admin.getType())){//说明是商家登录
                permissions += "\\|SELLER";
            }
            if(permissions == null || permissions.isEmpty()) {
                return toJSONString(ERROR_6);//权限为空，请为此账号设置权限
            }
            session.setAttribute(ControllerConstants.PERMISSIONS, permissions);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            String msg = e.getMessage();
            if (msg.equals("USERNAME_ERROR")){
                return toJSONString(ERROR_3);
            }
            if (msg.equals("PASSWORD_ERROR")){
                return toJSONString(ERROR_4);
            }else {
                return toJSONString(ERROR_5);
            }
        }
    }
}
