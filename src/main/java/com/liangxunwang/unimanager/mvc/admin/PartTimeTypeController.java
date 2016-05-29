package com.liangxunwang.unimanager.mvc.admin;

import com.liangxunwang.unimanager.model.PartTimeType;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.mvc.vo.PartTimeVO;
import com.liangxunwang.unimanager.query.PartTimeQuery;
import com.liangxunwang.unimanager.service.DeleteService;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.SaveService;
import com.liangxunwang.unimanager.service.ServiceException;
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
 * Created by zhl on 2015/2/7.
 */
@Controller
public class PartTimeTypeController extends ControllerConstants{

    @Autowired
    @Qualifier("partTimeTypeService")
    private SaveService saveTypeService;

    @Autowired
    @Qualifier("partTimeTypeService")
    private ListService listTypeService;

    @Autowired
    @Qualifier("partTimeTypeService")
    private DeleteService deleteTypeService;

    @RequestMapping("/listPartTimeType")
    public String listPartTimeType(ModelMap map){
        List<PartTimeType> list = (List<PartTimeType>) listTypeService.list(null);
        map.put("list", list);
        return "/partTimeType/listType";
    }

    @RequestMapping(value = "/appListPartTimeType", produces = "text/plain;charset=UTF-8;")
    @ResponseBody
    public String listPartTimeType(){
        List<PartTimeType> list = (List<PartTimeType>) listTypeService.list(null);
        DataTip tip = new DataTip();
        tip.setData(list);
        return toJSONString(tip);
    }


    @RequestMapping("/savePartTimeType")
    @ResponseBody
    public String saveType(PartTimeType type){
        if (StringUtil.isNullOrEmpty(type.getName())){
            return toJSONString(ERROR_1);
        }
        if (StringUtil.isNullOrEmpty(type.getCover())){
            return toJSONString(ERROR_2);
        }
        try {
            saveTypeService.save(type);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            return toJSONString(ERROR_3);
        }
    }

    @RequestMapping("/deletePartTimeType")
    @ResponseBody
    public String deletePartTimeType(String typeId){
        try {
            deleteTypeService.delete(typeId);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }


    @RequestMapping("/toAddType")
    public String toAddType(){

        return "/partTimeType/addType";
    }


    @Autowired
    @Qualifier("partTimeHtService")
    private ListService partTimeHtService;

    //招聘列表
    @RequestMapping("/listPartTimeList")
    public String listPartTimeList(PartTimeQuery query, Page page, ModelMap map){
        query.setIndex(page.getPage()==0?1:page.getPage());
        query.setSize(query.getSize()==0?page.getDefaultSize():query.getSize());
//        List<PartTimeVO> list = (List<PartTimeVO>) partTimeHtService.list(query);
//        map.put("list", list);

        Object[] result = (Object[]) partTimeHtService.list(query);
        map.put("list", result[0]);
        long count = (Long) result[1];
        page.setCount(count);
        page.setPageCount(calculatePageCount(query.getSize(), count));
        map.addAttribute("page", page);
        map.addAttribute("query", query);

        return "/partTimeType/list";
    }
}
