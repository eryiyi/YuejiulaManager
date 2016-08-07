package com.liangxunwang.unimanager.mvc.admin;

import com.liangxunwang.unimanager.model.AdObj;
import com.liangxunwang.unimanager.model.Admin;
import com.liangxunwang.unimanager.model.BigAreaObj;
import com.liangxunwang.unimanager.query.AdQuery;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.ControllerConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 */
@Controller
@RequestMapping("/bigAreaObjController")
public class BigAreaObjController extends ControllerConstants {

    @Autowired
    @Qualifier("bigAreaObjService")
    private ListService levelService;

    @Autowired
    @Qualifier("bigAreaObjService")
    private SaveService levelServiceSave;

    @Autowired
    @Qualifier("bigAreaObjService")
    private ExecuteService levelServiceSaveExe;

    @Autowired
    @Qualifier("bigAreaObjService")
    private UpdateService levelServiceSaveUpdate;


    @RequestMapping("list")
    public String list(ModelMap map){
        List<BigAreaObj> list = (List<BigAreaObj>) levelService.list("");
        map.put("list", list);
        return "/bigArea/list";
    }

    @RequestMapping("toAdd")
    public String add(){
        return "/bigArea/add";
    }

    @RequestMapping("add")
    @ResponseBody
    public String addPiao(BigAreaObj bigAreaObj){
        try {
            levelServiceSave.save(bigAreaObj);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            String msg = e.getMessage();
            if (msg.equals("adIsTooMuch")){
                return toJSONString(ERROR_2);
            }else{
                return toJSONString(ERROR_1);
            }
        }
    }

    @RequestMapping("/toEdit")
    public String toUpdateType(ModelMap map, String area_id) throws Exception {
        BigAreaObj bigAreaObj = (BigAreaObj) levelServiceSaveExe.execute(area_id);
        map.put("bigAreaObj", bigAreaObj);
        return "/bigArea/edit";
    }

    /**
     * 更新
     */
    @RequestMapping("/edit")
    @ResponseBody
    public String updateGoodsType(BigAreaObj bigAreaObj){
        try {
            levelServiceSaveUpdate.update(bigAreaObj);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }



}
