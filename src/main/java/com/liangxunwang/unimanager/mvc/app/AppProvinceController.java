package com.liangxunwang.unimanager.mvc.app;

import com.liangxunwang.unimanager.model.Province;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.mvc.vo.OrderVo;
import com.liangxunwang.unimanager.query.OrdersQuery;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.ControllerConstants;
import com.liangxunwang.unimanager.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Administrator on 2015/8/17.
 */
@Controller
public class AppProvinceController extends ControllerConstants {


    @Autowired
    @Qualifier("appProvinceService")
    private ListService appProvinceListService;

    /**
     * 获得所有的省份
     * @return
     */
    @RequestMapping(value = "/appGetProvince", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String getProvince(){
        try {
            List<Province> list = (List<Province>) appProvinceListService.list(null);
            DataTip tip = new DataTip();
            tip.setData(list);
            return toJSONString(tip);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }
}
