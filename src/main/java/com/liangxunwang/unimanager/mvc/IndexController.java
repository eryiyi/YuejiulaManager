package com.liangxunwang.unimanager.mvc;

import com.liangxunwang.unimanager.model.Admin;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.util.ControllerConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

/**
 * Created by liuzwei on 2015/1/29.
 */
@Controller
public class IndexController extends ControllerConstants {
    @Autowired
    @Qualifier("indexService")
    private ListService indexListService;

    @RequestMapping("/index")
    public String index(HttpSession session){
        Admin admin = (Admin) session.getAttribute(ControllerConstants.ACCOUNT_KEY);
        if (admin != null){
            return "/index";
        }

        return "/adminLogin";
    }

    @RequestMapping("/main")
    public String left(){

        return "/index";
    }

    @RequestMapping("/mainPage")
    public String mainPage(ModelMap map){
        List<Object> list = (List<Object>) indexListService.list(null);
        //总共会员数量
        Long memberCount = (Long) list.get(0);
        //被关禁闭会员数量
        Long closeMemberCount = (Long) list.get(1);
        //广告数量
        Long advertCont = (Long) list.get(2);

        map.put("memberCount", memberCount);
        map.put("closeMemberCount", closeMemberCount);
        map.put("advertCont", advertCont);

        return "/main";
    }

    /**
     *
     * @return
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session){
        Enumeration en = session.getAttributeNames();
        while (en.hasMoreElements()) {
            session.removeAttribute((String)en.nextElement());
        }
        return "redirect:/";
    }

}
