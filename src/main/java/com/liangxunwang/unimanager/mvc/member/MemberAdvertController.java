package com.liangxunwang.unimanager.mvc.member;

import com.liangxunwang.unimanager.model.Advert;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.service.FindService;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.Constants;
import com.liangxunwang.unimanager.util.ControllerConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by liuzwei on 2015/2/3.
 */
@Controller
public class MemberAdvertController extends ControllerConstants{
    @Autowired
    @Qualifier("memberAdvertService")
    private FindService advertFindService;
    @Autowired
    @Qualifier("memberAdvertService")
    private ListService listAdvertService;

    /**
     * 获得大广告
     * @return
     */
    @RequestMapping("/getBigAdvert")
    @ResponseBody
    public String bigAdvert(){
        try {
            Advert advert = (Advert) advertFindService.findById(null);
            advert.setAdPic(Constants.URL+advert.getAdPic());
            DataTip tip = new DataTip();
            tip.setData(advert);
            return toJSONString(tip);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }

    /**
     * 获得小广告
     * @param schoolId
     * @return
     */
    @RequestMapping("/getSmallAdvert")
    @ResponseBody
    public String getSmallAdvert(String schoolId){
        try {
            Advert advert = (Advert) listAdvertService.list(schoolId);
            DataTip tip = new DataTip();
            tip.setData(advert);
            return toJSONString(tip);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }

}
