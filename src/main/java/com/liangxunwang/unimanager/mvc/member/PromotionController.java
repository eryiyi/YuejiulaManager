package com.liangxunwang.unimanager.mvc.member;

import com.liangxunwang.unimanager.model.Record;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.service.DeleteService;
import com.liangxunwang.unimanager.service.FindService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.ControllerConstants;
import com.liangxunwang.unimanager.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by liuzwei on 2015/4/2.
 */
@Controller
public class PromotionController extends ControllerConstants{

    @Autowired
    @Qualifier("promotionService")
    private FindService promotionFindService;

    @Autowired
    @Qualifier("promotionService")
    private DeleteService promotionDeleteService;

    /**
     * 根据承包商的会员ID查找推广
     * @param empId
     * @return
     */
    @RequestMapping(value = "/getPromotion", produces = "text/plain;charset=UTF-8;")
    @ResponseBody
    public String getPromotion(String empId){
        try {
            List<Record> list = (List<Record>) promotionFindService.findById(empId);
            DataTip tip = new DataTip();
            tip.setData(list);
            return toJSONString(tip);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }

    /**
     * 根据承包商的会员ID删除推广
     * @param empId
     * @return
     */
    @RequestMapping("/deletePromotion")
    @ResponseBody
    public String deletePromotion(String empId){
        try {
            promotionDeleteService.delete(empId);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }
}
