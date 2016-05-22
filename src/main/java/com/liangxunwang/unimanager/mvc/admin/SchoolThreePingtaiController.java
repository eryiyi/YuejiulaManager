package com.liangxunwang.unimanager.mvc.admin;

import com.liangxunwang.unimanager.model.Notice;
import com.liangxunwang.unimanager.model.SchoolThreeTingtai;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.query.NoticeQuery;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.ControllerConstants;
import com.liangxunwang.unimanager.util.Page;
import com.liangxunwang.unimanager.util.RelativeDateFormat;
import com.liangxunwang.unimanager.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by liuzwei on 2015/2/5.
 */
@Controller
public class SchoolThreePingtaiController extends ControllerConstants {
    @Autowired
    @Qualifier("schoolThreePingtaiService")
    private SaveService saveNoticeService;

    @Autowired
    @Qualifier("schoolThreePingtaiService")
    private ListService listNoticeService;

    @Autowired
    @Qualifier("schoolThreePingtaiService")
    private FindService findNoticeService;

    @Autowired
    @Qualifier("schoolThreePingtaiService")
    private UpdateService updateNoticeService;

    @Autowired
    @Qualifier("schoolThreePingtaiService")
    private DeleteService deleteNoticeService;

    @RequestMapping("/toAddThreePingtai")
    public String toAddThreePingtai(){
        return "/threePingtai/add";
    }

    @RequestMapping("/saveThreePingtai")
    @ResponseBody
    public String saveThreePingtai(SchoolThreeTingtai schoolThreeTingtai){
        saveNoticeService.save(schoolThreeTingtai);
        return toJSONString(SUCCESS);
    }

    @RequestMapping("/listThreePingtai")
    public String listThreePingtai(ModelMap map){
        List<SchoolThreeTingtai> list = (List<SchoolThreeTingtai>) listNoticeService.list("");
        map.put("list", list);
        return "/threePingtai/list";
    }


    @RequestMapping("/toUpdateThreePingtai")
    public String toUpdateThreePingtai(String school_three_pingtai_id, ModelMap map){
        SchoolThreeTingtai schoolThreeTingtai = (SchoolThreeTingtai) findNoticeService.findById(school_three_pingtai_id);
        map.put("schoolThreeTingtai", schoolThreeTingtai);
        return "/threePingtai/update";
    }

    @RequestMapping("/updateThreePingtai")
    @ResponseBody
    public String updateThreePingtai(SchoolThreeTingtai schoolThreeTingtai){

        if (StringUtil.isNullOrEmpty(schoolThreeTingtai.getSchool_three_pingtai_name())){
            return toJSONString(ERROR_1);
        }
        if (StringUtil.isNullOrEmpty(schoolThreeTingtai.getSchool_three_pingtai_pic())){
            return toJSONString(ERROR_2);
        }
        updateNoticeService.update(schoolThreeTingtai);
        return toJSONString(SUCCESS);
    }

    @RequestMapping("/deleteThreePingtai")
    @ResponseBody
    public String deleteThreePingtai(String school_three_pingtai_id){
        try {
            deleteNoticeService.delete(school_three_pingtai_id);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }

}
