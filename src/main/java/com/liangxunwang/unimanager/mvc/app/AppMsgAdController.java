package com.liangxunwang.unimanager.mvc.app;

import com.liangxunwang.unimanager.model.Admin;
import com.liangxunwang.unimanager.model.MsgAd;
import com.liangxunwang.unimanager.model.RecordJp;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.mvc.vo.RecordJpVO;
import com.liangxunwang.unimanager.query.MsgAdQuery;
import com.liangxunwang.unimanager.query.RecordJpQuery;
import com.liangxunwang.unimanager.service.ExecuteService;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.SaveService;
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
public class AppMsgAdController extends ControllerConstants{
    @Autowired
    @Qualifier("msgAdObjService")
    private ListService levelService;

    @Autowired
    @Qualifier("indexService")
    private ListService indexListService;

    //查询列表
    @RequestMapping(value = "/listsMsgAds", produces = "text/plain; charset=UTF-8")
    @ResponseBody
    //school_id
    public String listsMsgAds(MsgAdQuery query){
        List<MsgAd> list = (List<MsgAd>) levelService.list(query);
        MsgAd msgAd = null;
        if(list != null && list.size()>0){
            msgAd  = list.get(0);
            //查询用户数量
            List<Object> listAll = (List<Object>) indexListService.list(null);
            //总共会员数量
            Long memberCount = (Long) listAll.get(0);
            msgAd.setNumberEmp(String.valueOf(memberCount));
        }
        DataTip tip = new DataTip();
        tip.setData(msgAd);
        return toJSONString(tip);
    }


}
