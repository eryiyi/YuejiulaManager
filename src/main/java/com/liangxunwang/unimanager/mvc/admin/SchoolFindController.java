package com.liangxunwang.unimanager.mvc.admin;

import com.liangxunwang.unimanager.model.SchoolFind;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.query.VideosQuery;
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

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by zhl on 2015/2/3.
 */
@Controller
public class SchoolFindController extends ControllerConstants {
    @Autowired
    @Qualifier("schoolFindService")
    private SaveService videosService;

    @Autowired
    @Qualifier("schoolFindService")
    private ListService videosServiceList;

    @Autowired
    @Qualifier("schoolFindService")
    private DeleteService videosServiceDelete;

    @Autowired
    @Qualifier("schoolFindService")
    private FindService videosServiceFind;

    @Autowired
    @Qualifier("appSchoolFindService")
    private ListService appSchoolFindService;

    @RequestMapping("/listSchoolFind")
    public String listSchoolFind(VideosQuery query,Page page, ModelMap map){
        query.setIndex(page.getPage()==0?1:page.getPage());
        query.setSize(query.getSize()==0?page.getDefaultSize():query.getSize());
        try {
            Object[] result = (Object[]) videosServiceList.list(query);
            map.put("list", result[0]);
            long count = (Long) result[1];
            page.setCount(count);
            page.setPageCount(calculatePageCount(query.getSize(), count));
            map.addAttribute("page", page);
            map.addAttribute("query", query);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
        return "/schoolFind/listFinds";
    }


    @RequestMapping(value = "/listFindsApp", produces = "text/plain;charset=UTF-8;")
    @ResponseBody
        public String listFindsApp(){
        try {
            List<SchoolFind> list = (List<SchoolFind>) appSchoolFindService.list("");
            DataTip tip = new DataTip();
            tip.setData(list);
            return toJSONString(tip);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }

    /**
     * 发布
     * @return
     */
    @RequestMapping("/saveFinds")
    @ResponseBody
    public String saveFinds(SchoolFind videos, HttpSession session){
        if (StringUtil.isNullOrEmpty(videos.getTitle())){
            return toJSONString(ERROR_1);
        }
        if (StringUtil.isNullOrEmpty(videos.getWww_url())){
            return toJSONString(ERROR_2);
        }
        if (StringUtil.isNullOrEmpty(videos.getTop_num())){
            return toJSONString(ERROR_3);
        }
        if (StringUtil.isNullOrEmpty(videos.getPic_url())){
            return toJSONString(ERROR_4);
        }
        videosService.save(videos);
        return toJSONString(SUCCESS);
    }


    @RequestMapping("/toAddFinds")
    public String toAddNews(ModelMap map){
        return "/schoolFind/addFinds";
    }



    @RequestMapping("/deleteFinds")
    @ResponseBody
    public String deleteNews(String school_find_id){
        try {
            videosServiceDelete.delete(school_find_id);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }
}
