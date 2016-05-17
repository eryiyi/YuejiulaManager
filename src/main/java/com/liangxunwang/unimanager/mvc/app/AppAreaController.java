package com.liangxunwang.unimanager.mvc.app;

import com.liangxunwang.unimanager.model.Area;
import com.liangxunwang.unimanager.model.City;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.ControllerConstants;
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
public class AppAreaController extends ControllerConstants {
    @Autowired
    @Qualifier("appAreaService")
    private ListService appAreaListService;

    /**
     * 获得所有的地区，根据城市id
     * @return
     */
    @RequestMapping(value = "/appGetArea", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appGetArea(String cityid){
        try {
            List<Area> list = (List<Area>) appAreaListService.list(cityid);
            DataTip tip = new DataTip();
            tip.setData(list);
            return toJSONString(tip);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }
}
