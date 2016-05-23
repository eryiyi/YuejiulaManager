package com.liangxunwang.unimanager.mvc.member;

import com.liangxunwang.unimanager.model.Goods;
import com.liangxunwang.unimanager.model.SchoolRecordMood;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.mvc.vo.GoodsVO;
import com.liangxunwang.unimanager.mvc.vo.PaopaoGoodsVO;
import com.liangxunwang.unimanager.query.GoodsQuery;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.ControllerConstants;
import com.liangxunwang.unimanager.util.Page;
import com.liangxunwang.unimanager.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by zhl on 2015/2/2.
 */
@Controller
public class AppSchoolRecordMoodController extends ControllerConstants {

    @Autowired
    @Qualifier("appSchoolRecordMoodService")
    private ListService appSchoolRecordMoodService;

    @RequestMapping(value = "/listsRecodMoods", produces = "text/plain; charset=UTF-8")
    @ResponseBody
    public String listsRecodMoods(){
        try {
            List<SchoolRecordMood> list = (List<SchoolRecordMood>) appSchoolRecordMoodService.list("");
            DataTip tip = new DataTip();
            tip.setData(list);
            return toJSONString(tip);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }

}
