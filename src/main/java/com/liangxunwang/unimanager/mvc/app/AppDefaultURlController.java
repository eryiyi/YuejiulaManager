package com.liangxunwang.unimanager.mvc.app;

import com.liangxunwang.unimanager.model.BigAreaObj;
import com.liangxunwang.unimanager.model.MsgAd;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.query.MsgAdQuery;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.util.ControllerConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Administrator on 2015/8/10.
 */
@Controller
public class AppDefaultURlController extends ControllerConstants{
    @Autowired
    @Qualifier("bigAreaObjService")
    private ListService levelService;

    //查询
    @RequestMapping(value = "/getDefaultBigAreas", produces = "text/plain; charset=UTF-8")
    @ResponseBody
    //school_id
    public String getDefaultBigAreas(){
        List<BigAreaObj> list = (List<BigAreaObj>) levelService.list("");
        DataTip tip = new DataTip();
        tip.setData(list);
        return toJSONString(tip);
    }


}
