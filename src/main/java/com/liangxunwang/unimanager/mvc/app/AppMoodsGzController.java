package com.liangxunwang.unimanager.mvc.app;

import com.liangxunwang.unimanager.model.Area;
import com.liangxunwang.unimanager.model.MoodGuanzhuObj;
import com.liangxunwang.unimanager.model.Record;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.mvc.vo.MoodsGuanzhuVO;
import com.liangxunwang.unimanager.mvc.vo.RecordVO;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.ControllerConstants;
import com.liangxunwang.unimanager.util.UUIDFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Administrator on 2015/8/17.
 */
@Controller
public class AppMoodsGzController extends ControllerConstants {
    @Autowired
    @Qualifier("moodsGuanzhuObjService")
    private ListService moodsGuanzhuObjService;

    @Autowired
    @Qualifier("moodsGuanzhuObjService")
    private SaveService moodsGuanzhuObjServiceSave;


    @Autowired
    @Qualifier("moodsGuanzhuObjService")
    private DeleteService moodsGuanzhuObjServiceDel;

    @Autowired
    @Qualifier("moodsGuanzhuObjService")
    private ExecuteService moodsGuanzhuObjServiceExe;


    /**
     * 查询会员关注列表
     * @return
     */
    @RequestMapping(value = "/appGetMoodsGuanzhu", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appGetMoodsGuanzhu(String emp_id){
        try {
            List<MoodsGuanzhuVO> list = (List<MoodsGuanzhuVO>) moodsGuanzhuObjService.list(emp_id);
            DataTip tip = new DataTip();
            tip.setData(list);
            return toJSONString(tip);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }

    @RequestMapping(value = "/saveMoodsGuanzhu", produces = "text/plain;charset=UTF-8;")
    @ResponseBody
    public String saveMoodsGuanzhu(MoodGuanzhuObj moodGuanzhuObj){
        try {
            moodsGuanzhuObjServiceSave.save(moodGuanzhuObj);
            DataTip tip = new DataTip();
            tip.setData(SUCCESS);
            return toJSONString(tip);
        }catch (ServiceException e){
            if (e.getMessage().equals("adIsTooMuch")){
                return toJSONString(ERROR_2);//超了
            }else {
                return toJSONString(ERROR_1);
            }
        }
    }

    @RequestMapping(value = "/deleteMoodsGuanzhuById", produces = "text/plain;charset=UTF-8;")
    @ResponseBody
    public String deleteMoodsGuanzhuById(String id){
        try {
            moodsGuanzhuObjServiceDel.delete(id);
            DataTip tip = new DataTip();
            tip.setData(SUCCESS);
            return toJSONString(tip);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }
}
