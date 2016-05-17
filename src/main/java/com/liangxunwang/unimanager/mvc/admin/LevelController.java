package com.liangxunwang.unimanager.mvc.admin;

import com.liangxunwang.unimanager.model.Level;
import com.liangxunwang.unimanager.service.DeleteService;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.SaveService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.Constants;
import com.liangxunwang.unimanager.util.ControllerConstants;
import com.liangxunwang.unimanager.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuzwei on 2015/2/1.
 */
@Controller
public class LevelController extends ControllerConstants {
    @Autowired
    @Qualifier("levelService")
    private SaveService levelSaveService;

    @Autowired
    @Qualifier("levelService")
    private ListService levelListService;

    @Autowired
    @Qualifier("levelService")
    private DeleteService deleteLevelService;

    @RequestMapping("/listLevel")
    public String listLevel(ModelMap map){
        try {
            List<Level> list = (List<Level>) levelListService.list(null);
            map.put("list",list);
        }catch (ServiceException e){
            map.put("list", new ArrayList<Level>());
        }
        return "/level/listLevel";
    }

    @RequestMapping("/toAddLevel")
    public String toAddLevel(){

        return "/level/addLevel";
    }

    @RequestMapping("/addLevel")
    @ResponseBody
    public String addLevel(Level level){
        if (StringUtil.isNullOrEmpty(level.getLevelName())){
            return  toJSONString(ERROR_1);
        }
        if (StringUtil.isNullOrEmpty(level.getLevelStart())){
            return toJSONString(ERROR_2);
        }
        if (StringUtil.isNullOrEmpty(level.getLevelEnd())){
            return toJSONString(ERROR_3);
        }
        if (Integer.parseInt(level.getLevelStart()) > Integer.parseInt(level.getLevelEnd())){
            return toJSONString(ERROR_4);
        }
        try {
            levelSaveService.save(level);
        }catch (ServiceException e){
            return toJSONString(ERROR_5);
        }
        return toJSONString(SUCCESS);
    }

    @RequestMapping("/deleteLevel")
    @ResponseBody
    public String deleteLevel(String levelId){
        try {
            deleteLevelService.delete(levelId);
        } catch (ServiceException e){
            String msg = e.getMessage();
            if (msg.equals(Constants.SAVE_ERROR)) {
                return toJSONString(ERROR_1);
            }else {

            }
        }
        return toJSONString(SUCCESS);
    }

}
