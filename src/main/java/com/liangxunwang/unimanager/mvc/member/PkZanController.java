package com.liangxunwang.unimanager.mvc.member;

import com.liangxunwang.unimanager.model.PKZan;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.mvc.vo.PkZanVO;
import com.liangxunwang.unimanager.query.PkZanQuery;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.SaveService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.ControllerConstants;
import com.liangxunwang.unimanager.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuzwei on 15-4-5.
 */
@Controller
public class PkZanController extends ControllerConstants{
    @Autowired
    @Qualifier("pkZanService")
    private SaveService pkZanSaveService;

    @Autowired
    @Qualifier("pkZanService")
    private ListService pkZanListService;
    /**
     * 投票赞
     * @return
     * @param zan
     * http://localhost:8080/addPkZanApp.do?zpId=7391bcddfaca4617b1efe2e837f79732&empId=6e52aaf7bcfe4334ab0e85a3b23476b6
     */
    @RequestMapping("/addPkZanApp")
    @ResponseBody
    public String addPkZan(PKZan zan){
        try {
            pkZanSaveService.save(zan);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            if (e.getMessage().equals("HAS_END")){//已经结束
                return toJSONString(ERROR_3);
            }
            if (e.getMessage().equals("HAS_PK_ZAN")){//已经投票
                return toJSONString(ERROR_2);
            }
            return toJSONString(ERROR_1);
        }
    }

    /**
     * 根据作品ID查询赞 即投票
     * @param query
     * @param page
     * @return
     * http://localhost:8080/listPkZanApp.do?zpId=7391bcddfaca4617b1efe2e837f79732
     */
    @RequestMapping(value = "/listPkZanApp", produces = "text/plain;charset=UTF-8;")
    @ResponseBody
    public String listPkZan(PkZanQuery query, Page page){
        query.setIndex(page.getPage() == 0 ? 1 : page.getPage());
        query.setSize(query.getSize()==0?page.getDefaultSize():query.getSize());
        try {
            Object[] result = (Object[]) pkZanListService.list(query);
            List<PkZanVO> voList = (List<PkZanVO>) result[1];
            Long count = (Long) result[0];
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("list", voList);
            map.put("count", count);
            DataTip tip = new DataTip();
            tip.setData(map);
            return toJSONString(tip);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }
}
