package com.liangxunwang.unimanager.mvc.admin;

import com.liangxunwang.unimanager.model.NewsType;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.service.DeleteService;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.SaveService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.ControllerConstants;
import com.liangxunwang.unimanager.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by liuzwei on 2015/3/1.
 */
@Controller
public class NewsTypeController extends ControllerConstants {

    @Autowired
    @Qualifier("newsTypeService")
    private ListService newsTypeListService;
    @Autowired
    @Qualifier("newsTypeService")
    private SaveService newsTypeSaveService;

    @Autowired
    @Qualifier("newsTypeService")
    private DeleteService newsTypeDeleteService;

    /**
     * 获得所有的新闻类别 app用
     * @return
     */
    @RequestMapping(value = "/listNewsTypeApp", produces = "text/plain;charset=UTF-8;")
    @ResponseBody
    public String listType(){
        try {
            List<NewsType> list = (List<NewsType>) newsTypeListService.list(null);
            DataTip tip = new DataTip();
            tip.setData(list);
            return toJSONString(tip);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }

    @RequestMapping("/listNewsType")
    public String listNewsType(ModelMap map){
        List<NewsType> list = (List<NewsType>) newsTypeListService.list(null);
        map.put("list", list);
        return "/newsType/listNewsType";
    }

    @RequestMapping("/toAddNewsType")
    public String toAddNewsType(){
        return "/newsType/addNewsType";
    }

    /**
     * 添加新闻类别
     * @param type 类别名称
     * @return
     */
    @RequestMapping("/addNewsType")
    @ResponseBody
    public String addNewsType(NewsType type){
        if (StringUtil.isNullOrEmpty(type.getName())){
            return toJSONString(ERROR_1);
        }
        try {
            newsTypeSaveService.save(type);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            return toJSONString(ERROR_2);
        }
    }

    /**
     * 删除新闻类别
     * @param id  类别ID
     * @return
     */
    @RequestMapping("/deleteNewsType")
    @ResponseBody
    public String deleteNewsType(String id){
        try {
            newsTypeDeleteService.delete(id);
            return toJSONString(SUCCESS);
        }catch (ServiceException r){
            return toJSONString(ERROR_1);
        }
    }
}
