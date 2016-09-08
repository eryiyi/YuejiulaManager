package com.liangxunwang.unimanager.mvc.admin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.liangxunwang.unimanager.huanxin.comm.httpclient.apidemo.EasemobChatGroups;
import com.liangxunwang.unimanager.model.College;
import com.liangxunwang.unimanager.model.Province;
import com.liangxunwang.unimanager.query.CollegeQuery;
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
public class SchoolsController extends ControllerConstants {
    @Autowired
    @Qualifier("schoolsService")
    private SaveService schoolsServiceSave;

    @Autowired
    @Qualifier("schoolsService")
    private ListService schoolsServicelist;

    @Autowired
    @Qualifier("schoolsService")
    private UpdateService schoolsServiceUpdate;

    @Autowired
    @Qualifier("provinceService")
    private ListService provinceListService;

    @RequestMapping("/listSchools")
    public String newsList(CollegeQuery query,Page page, ModelMap map){

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

        List<Province> list = (List<Province>) provinceListService.list(null);
        map.put("provinces", list);

        return "/schools/listSchools";
    }


    /**
     * 保存圈子
     * @return
     */
    @RequestMapping("/saveSchools")
    @ResponseBody
    public String saveNews(College college){
        schoolsServiceSave.save(college);
        return toJSONString(SUCCESS);
    }

    @RequestMapping("/toAddSchools")
    public String toAddNews(ModelMap map){
        List<Province> list = (List<Province>) provinceListService.list(null);
        map.put("provinces", list);
        return "/schools/addSchools";
    }


    @Autowired
    @Qualifier("schoolsAllService")
    private ListService schoolsAllService;

    @RequestMapping("/regHxCollege")
    public String regHxCollege(){
        //查询所有大学
        List<College> list = (List<College>) schoolsAllService.list("");
        if(list != null){
            for(College college : list){
                ObjectNode dataObjectNode = JsonNodeFactory.instance.objectNode();
                dataObjectNode.put("groupname", college.getName());
                dataObjectNode.put("desc", college.getName());
                dataObjectNode.put("approval", true);
                dataObjectNode.put("public", true);
                dataObjectNode.put("maxusers", 2000);
                dataObjectNode.put("owner", "12345678910");//12345678910 超级管理员 密码 0123456
                ArrayNode arrayNode = JsonNodeFactory.instance.arrayNode();
                dataObjectNode.put("members", arrayNode);
                ObjectNode creatChatGroupNode = EasemobChatGroups.creatChatGroups(dataObjectNode);
                //处理数据
                    ObjectNode objectNode = (ObjectNode) creatChatGroupNode.get("data");//解析数据
                    JsonNode groupid = objectNode.get("groupid");
                    //获得环信的groupid，更新数据库
                    Object[] params = new Object[2];
                    params[0] = college.getCoid();
                    params[1] = groupid.textValue();
                    schoolsServiceUpdate.update(params);
            }
        }

        return "/schools/regHxCollege";
    }



    @Autowired
    @Qualifier("schoolsService")
    private DeleteService schoolsServiceDelete;

    @RequestMapping("deleteCollege")
    @ResponseBody
    public String delete(String id){
        schoolsServiceDelete.delete(id);
        return toJSONString(SUCCESS);
    }


}
