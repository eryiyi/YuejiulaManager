package com.liangxunwang.unimanager.mvc.admin;

import com.liangxunwang.unimanager.model.Admin;
import com.liangxunwang.unimanager.model.ManagerInfo;
import com.liangxunwang.unimanager.service.FindService;
import com.liangxunwang.unimanager.service.SaveService;
import com.liangxunwang.unimanager.service.UpdateService;
import com.liangxunwang.unimanager.util.ControllerConstants;
import com.liangxunwang.unimanager.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by liuzh on 2015/8/30.
 */
@Controller
@RequestMapping("/managerinfo")
public class ManagerInfoController extends ControllerConstants {

    @Autowired
    @Qualifier("managerInfoService")
    private SaveService managerInfoSaveService;

    @Autowired
    @Qualifier("managerInfoService")
    private FindService managerInfoFindService;

    @Autowired
    @Qualifier("managerInfoService")
    private UpdateService managerInfoUpdateService;

    @RequestMapping("save")
    @ResponseBody
    private String save(ManagerInfo info, HttpSession session){

        Admin admin = (Admin) session.getAttribute(ACCOUNT_KEY);
        info.setManagerId(admin.getId());
        if (StringUtil.isNullOrEmpty(info.getId())){
            managerInfoSaveService.save(info);
        }else {
            managerInfoUpdateService.update(info);
        }
        return toJSONString(SUCCESS);
    }

    @RequestMapping("add")
    public String edit(HttpSession session, ModelMap map){
        Admin admin = (Admin) session.getAttribute(ACCOUNT_KEY);
        ManagerInfo info = (ManagerInfo) managerInfoFindService.findById(admin.getId());
        map.put("info", info);
        return "/managerinfo/add";
    }
}
