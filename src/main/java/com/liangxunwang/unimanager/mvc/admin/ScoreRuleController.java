package com.liangxunwang.unimanager.mvc.admin;

import com.liangxunwang.unimanager.model.ScoreRule;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.ControllerConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by liuzwei on 2015/2/4.
 */
@Controller
public class ScoreRuleController extends ControllerConstants {

    @Autowired
    @Qualifier("scoreRuleService")
    private SaveService ruleSaveService;

    @Autowired
    @Qualifier("scoreRuleService")
    private UpdateService updateRuleService;

    @Autowired
    @Qualifier("scoreRuleService")
    private ListService listRuleService;

    @Autowired
    @Qualifier("scoreRuleService")
    private DeleteService deleteRuleService;

    @RequestMapping("/saveRule")
    @ResponseBody
    public String saveRule(ScoreRule scoreRule){
        try {
            ruleSaveService.save(scoreRule);
        }catch (ServiceException e){
            toJSONString(ERROR_1);
        }
        return toJSONString(SUCCESS);
    }

    @RequestMapping("/toAddRule")
    public String toAddRule(){

        return "/rule/addRule";
    }
    @RequestMapping("/listRule")
    public String listRule(ModelMap map){
        List<ScoreRule> list = (List<ScoreRule>) listRuleService.list(null);
        map.put("list", list);
        return "/rule/listRule";
    }

    @RequestMapping("/deleteRule")
    @ResponseBody
    public String deleteRule(String ruleId){
        try {
            deleteRuleService.delete(ruleId);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }
}
