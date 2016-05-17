package com.liangxunwang.unimanager.mvc.admin;

import com.liangxunwang.unimanager.model.Advert;
import com.liangxunwang.unimanager.model.College;
import com.liangxunwang.unimanager.model.Province;
import com.liangxunwang.unimanager.query.AdvertQuery;
import com.liangxunwang.unimanager.query.CollegeQuery;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.ControllerConstants;
import com.liangxunwang.unimanager.util.Page;
import com.liangxunwang.unimanager.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by liuzwei on 2015/1/31.
 */
@Controller("advertController")
public class AdvertController extends ControllerConstants {
    @Autowired
    @Qualifier("universityService")
    private ListService universityListService;

    @Autowired
    @Qualifier("advertService")
    private ListService advertListService;

    @Autowired
    @Qualifier("advertService")
    private FindService findAdvertService;

    @Autowired
    @Qualifier("advertService")
    private UpdateService updateAdvertService;

    @Autowired
    @Qualifier("advertService")
    private DeleteService deleteAdvertService;

    @Autowired
    @Qualifier("provinceService")
    private ListService provinceListService;

    @Autowired
    @Qualifier("collegeService")
    private ListService collegeListService;

    /**
     * @see com.liangxunwang.unimanager.service.account.AdvertService
     */
    @Autowired
    @Qualifier("advertService")
    private SaveService advertSaveService;

    @RequestMapping("/ajax/listAdvert")
    public String listAdvert(ModelMap map, AdvertQuery query, Page page){
        query.setIndex(page.getPage()==0?1:page.getPage());
        query.setSize(query.getSize()==0?page.getDefaultSize():query.getSize());

        Object[] result = (Object[]) advertListService.list(query);
        map.put("list", result[0]);
        long count = (Long) result[1];
        page.setCount(count);
        page.setPageCount(calculatePageCount(query.getSize(), count));
        map.addAttribute("page", page);
        map.addAttribute("query", query);

        return "/advert/listAdvert";
    }

    @RequestMapping("/ajax/toAddAdvert")
    public String toAddAdvert(ModelMap map){
        List<Province> list = (List<Province>) provinceListService.list(null);
        map.put("provinces", list);

        List<College> colleges = (List<College>) collegeListService.list(new CollegeQuery());
        map.put("colleges", toJSONString(colleges));

//        List<University> universityList = (List<University>) universityListService.list(null);
//        map.put("schools", universityList);

        return "/advert/addAdvert";
    }

    @RequestMapping("/ajax/toAddDefaultAdvert")
    public String toAddDefaultAdvert(){
        return "/advert/defaultAdvert";
    }

    @RequestMapping("/addAdvert")
    @ResponseBody
    public String addAdvert(Advert advert){
        if (StringUtil.isNullOrEmpty(advert.getAdPic())){
            return toJSONString(ERROR_2);
        }
        if (StringUtil.isNullOrEmpty(advert.getAdUrl())){
            return toJSONString(ERROR_3);
        }
        try {
            Advert result = (Advert) advertSaveService.save(advert);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
        return toJSONString(SUCCESS);
    }

    @RequestMapping("/updateAdvert")
    @ResponseBody
    public String updateAdvert(Advert advert){
        try {
            updateAdvertService.update(advert);

        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
        return toJSONString(SUCCESS);
    }

    @RequestMapping("/deleteAdvert")
    @ResponseBody
    public String deleteAdvert(String adId){
        try {
            deleteAdvertService.delete(adId);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }

    public String update(){
        deleteAdvert("update");
        return null;
    }
}
