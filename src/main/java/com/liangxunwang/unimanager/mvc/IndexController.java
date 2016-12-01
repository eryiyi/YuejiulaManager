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
import java.util.List;

/**
 * Created by zhl on 2015/1/29.
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
        Long memberCountNoDay = (Long) list.get(2);
        Long countGoodsAll = (Long) list.get(3);
        Long countOrderAll = (Long) list.get(4);
        Long countOrderDone = (Long) list.get(5);
        Long countOrderDay = (Long) list.get(6);

        map.put("memberCount", memberCount);
        map.put("closeMemberCount", closeMemberCount);
        map.put("memberCountNoDay", memberCountNoDay);
        map.put("countOrderAll", countOrderAll);
        map.put("countOrderDone", countOrderDone);
        map.put("countOrderDay", countOrderDay);


        list.add(memberCount);
        list.add(closeMemberCount);
        list.add(memberCountNoDay);

        list.add(countGoodsAll);
        list.add(countOrderAll);
        list.add(countOrderDone);
        list.add(countOrderDay);

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
