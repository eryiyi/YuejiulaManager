package com.liangxunwang.unimanager.mvc.app;

import com.liangxunwang.unimanager.model.RecordJp;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.mvc.vo.RecordJpVO;
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
public class AppRecordJpController extends ControllerConstants{

    @Autowired
    @Qualifier("recordJpService")
    private ListService recordJpServiceList;


    @Autowired
    @Qualifier("recordJpService")
    private ExecuteService recordJpServiceExe;

    @Autowired
    @Qualifier("recordJpService")
    private SaveService recordJpServiceSave;

    //保存
    @RequestMapping("/saveRecordJp")
    @ResponseBody
    public String saveRecordJp(RecordJp recordJp){
        recordJpServiceSave.save(recordJp);
        return toJSONString(SUCCESS);//保存成功
    }

    //查询列表
    @RequestMapping(value = "/listRecordJps", produces = "text/plain; charset=UTF-8")
    @ResponseBody
    public String listRecordJps(RecordJpQuery query){
        List<RecordJpVO> list = (List<RecordJpVO>) recordJpServiceList.list(query);
        DataTip tip = new DataTip();
        tip.setData(list);
        return toJSONString(tip);
    }

    //查询
    @RequestMapping(value = "/getSingleRecordJpById", produces = "text/plain; charset=UTF-8")
    @ResponseBody
    public String getSingleRecordJpById(String school_record_jp_id){
        RecordJp recordJp = (RecordJp) recordJpServiceExe.execute(school_record_jp_id);
        DataTip tip = new DataTip();
        tip.setData(recordJp);
        return toJSONString(tip);
    }

}
