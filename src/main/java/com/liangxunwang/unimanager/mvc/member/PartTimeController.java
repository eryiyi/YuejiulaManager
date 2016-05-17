package com.liangxunwang.unimanager.mvc.member;

import com.liangxunwang.unimanager.model.PartTime;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.mvc.vo.GoodsVO;
import com.liangxunwang.unimanager.mvc.vo.PartTimeVO;
import com.liangxunwang.unimanager.query.PartTimeQuery;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.ControllerConstants;
import com.liangxunwang.unimanager.util.Page;
import com.liangxunwang.unimanager.util.RelativeDateFormat;
import com.liangxunwang.unimanager.util.StringUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuzwei on 2015/2/7.
 */
@Controller
public class PartTimeController extends ControllerConstants {
    private Logger logger = LogManager.getLogger(PartTimeController.class);

    @Autowired
    @Qualifier("partTimeService")
    private ListService partTimeListService;

    @Autowired
    @Qualifier("partTimeService")
    private FindService findPartTimeService;

    @Autowired
    @Qualifier("partTimeService")
    private DeleteService deletePartTimeService;

    @Autowired
    @Qualifier("partTimeService")
    private SaveService savePartTimeService;

    @RequestMapping("/savePartTime")
    @ResponseBody
    public String savePartTime(PartTime partTime){
        try {
            savePartTimeService.save(partTime);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            if (e.getMessage().equals("NO_VALID_NUMBER")){
                logger.error("发布兼职的人数不是数字");
            }
            return toJSONString(ERROR_1);
        }
    }


    @RequestMapping(value = "/listPartTime", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String listPartTime(PartTimeQuery query, Page page){
        query.setIndex(page.getPage() == 0 ? 1 : page.getPage());
        query.setSize(query.getSize() == 0 ? page.getDefaultSize() : query.getSize());
        try {
            List<PartTimeVO> list = (List<PartTimeVO>) partTimeListService.list(query);
            DataTip tip = new DataTip();
            tip.setData(list);
            return toJSONString(tip);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }

    @RequestMapping("/viewPartTime")
    public String viewPartTime(ModelMap map, @RequestParam String id){
        PartTimeVO vo = (PartTimeVO) findPartTimeService.findById(id);
        if(vo != null){
            vo.setDateline(RelativeDateFormat.format(Long.parseLong(vo.getDateline())));
            map.put("partTime", vo);
            return "/partTime/viewPartTime";
        }else {
            return null;
        }
    }

    /**
     * 删除和禁用微兼职
     * @param id
     * @param type  1 禁用  2删除
     * @return
     */
    @RequestMapping("/deletePartTime")
    @ResponseBody
    public String deletePartTime(@RequestParam String id, @RequestParam String type){
        try {
            Object[] params = new Object[]{id, type};
            deletePartTimeService.delete(params);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }
    @RequestMapping(value = "/findPartTimeById", produces = "text/plain;charset=UTF-8;")
    @ResponseBody
    public String findPartTimeById(String partTimeId){
        try {
            PartTimeVO partTimeVO = (PartTimeVO) findPartTimeService.findById(partTimeId);
            DataTip tip = new DataTip();
            tip.setData(partTimeVO);
            return toJSONString(tip);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }
}
