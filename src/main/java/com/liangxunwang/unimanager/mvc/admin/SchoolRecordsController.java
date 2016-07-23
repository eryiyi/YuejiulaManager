package com.liangxunwang.unimanager.mvc.admin;

import com.liangxunwang.unimanager.mvc.vo.RecordVO;
import com.liangxunwang.unimanager.query.RecordQuery;
import com.liangxunwang.unimanager.service.DeleteService;
import com.liangxunwang.unimanager.service.FindService;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.util.ControllerConstants;
import com.liangxunwang.unimanager.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/schoolRecordsController")
public class SchoolRecordsController extends ControllerConstants {
    @Autowired
    @Qualifier("schoolRecordService")
    private ListService schoolRecordService;

    @Autowired
    @Qualifier("recordService")
    private FindService findRecordService;

    @Autowired
    @Qualifier("recordService")
    private DeleteService deleteRecordService;
    

    @RequestMapping("list")
    public String list(RecordQuery query, Page page, HttpSession session, ModelMap map){
        query.setIndex(page.getPage()==0?1:page.getPage());
        query.setSize(query.getSize()==0?page.getDefaultSize():query.getSize());
        Object[] results = (Object[]) schoolRecordService.list(query);
        map.put("list", results[0]);
        long count = (Long) results[1];
        page.setCount(count);
        page.setPageCount(calculatePageCount(query.getSize(), count));
        map.addAttribute("page", page);
        map.addAttribute("query", query);
        return "/record/list";
    }

    @RequestMapping("delete")
    @ResponseBody
    public String delete(String id){
        deleteRecordService.delete(id);
        return toJSONString(SUCCESS);
    }

    @RequestMapping("detail")
    public String detail(String id, ModelMap map){
        RecordVO record = (RecordVO) findRecordService.findById(id);
        map.put("record", record);
        return "/record/detail";
    }


}
