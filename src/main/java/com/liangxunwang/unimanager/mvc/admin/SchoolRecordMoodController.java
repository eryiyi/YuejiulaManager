package com.liangxunwang.unimanager.mvc.admin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.liangxunwang.unimanager.huanxin.comm.httpclient.apidemo.EasemobChatGroups;
import com.liangxunwang.unimanager.model.College;
import com.liangxunwang.unimanager.model.Province;
import com.liangxunwang.unimanager.model.SchoolRecordMood;
import com.liangxunwang.unimanager.query.CollegeQuery;
import com.liangxunwang.unimanager.query.SchoolRecordMoodQuery;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.ControllerConstants;
import com.liangxunwang.unimanager.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class SchoolRecordMoodController extends ControllerConstants {
    @Autowired
    @Qualifier("schoolRecordMoodService")
    private SaveService schoolsServiceSave;

    @Autowired
    @Qualifier("schoolRecordMoodService")
    private ListService schoolsServicelist;


    @RequestMapping("/listSchoolRecordMood")
    public String listSchoolRecordMood(SchoolRecordMoodQuery query,Page page, ModelMap map){
        query.setIndex(page.getPage() == 0 ? 1 : page.getPage());
        query.setSize(query.getSize() == 0 ? page.getDefaultSize() : query.getSize());
        try {
            Object[] result = (Object[]) schoolsServicelist.list(query);
            map.put("list", result[0]);
            long count = (Long) result[1];
            page.setCount(count);
            page.setPageCount(calculatePageCount(query.getSize(), count));
            map.addAttribute("page", page);
            map.addAttribute("query", query);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
        return "/schoolRecordMood/list";
    }


    /**
     * 保存
     */
    @RequestMapping("/saveSchoolRecordMood")
    @ResponseBody
    public String saveSchoolRecordMood(SchoolRecordMood schoolRecordMood){
        schoolsServiceSave.save(schoolRecordMood);
        return toJSONString(SUCCESS);
    }

    @RequestMapping("/toAddSchoolRecordMood")
    public String toAddSchoolRecordMood(ModelMap map){

        return "/schoolRecordMood/add";
    }

    @Autowired
    @Qualifier("schoolRecordMoodService")
    private DeleteService schoolRecordMoodServiceDelete;

    @RequestMapping("deleteSchoolRecordMood")
    @ResponseBody
    public String delete(String school_record_mood_id){
        schoolRecordMoodServiceDelete.delete(school_record_mood_id);
        return toJSONString(SUCCESS);
    }


}
