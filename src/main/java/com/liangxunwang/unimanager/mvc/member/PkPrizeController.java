package com.liangxunwang.unimanager.mvc.member;

import com.liangxunwang.unimanager.model.PkPrize;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.mvc.vo.PkPrizeVO;
import com.liangxunwang.unimanager.query.PkPrizeQuery;
import com.liangxunwang.unimanager.service.DeleteService;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.SaveService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.ControllerConstants;
import com.liangxunwang.unimanager.util.Page;
import com.liangxunwang.unimanager.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by liuzwei on 15-4-6.
 */
@Controller
public class PkPrizeController extends ControllerConstants {

    @Autowired
    @Qualifier("pkPrizeService")
    private SaveService pkPrizeSaveService;

    @Autowired
    @Qualifier("pkPrizeService")
    private DeleteService pkPrizeDeleteService;

    @Autowired
    @Qualifier("pkPrizeService")
    private ListService pkPrizeListService;
    /**
     * 添加奖品
     * @param pkPrize
     * @return
     */
    @RequestMapping("/savePrize")
    @ResponseBody
    public String addPrize(PkPrize pkPrize){
        if (StringUtil.isNullOrEmpty(pkPrize.getContent())){
            return toJSONString(ERROR_1);
        }
        if (StringUtil.isNullOrEmpty(pkPrize.getPic())){
            return toJSONString(ERROR_2);
        }
        try {
            pkPrizeSaveService.save(pkPrize);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            String msg = e.getMessage();
            if (msg.equals("HAS_PRIZE")){
                return toJSONString(ERROR_3);
            }else {
                return toJSONString(ERROR_4);
            }
        }
    }

    /**
     * 根据ID删除奖品
     * @param id
     * @return
     */
    @RequestMapping("/deletePrize")
    @ResponseBody
    public String deletePrize(String id){
        try {
            pkPrizeDeleteService.delete(id);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }

    /**
     * 根据主题ID查看奖品
     * @param query
     * @return
     */
    @RequestMapping(value = "/viewPriceApp", produces = "text/plain;charset=UTF-8;")
    @ResponseBody
    public String viewPriceApp(PkPrizeQuery query, Page page){
        query.setIndex(page.getPage() == 0 ? 1 : page.getPage());
        query.setSize(query.getSize() == 0 ? page.getDefaultSize() : query.getSize());
        try {
            List<PkPrizeVO> list = (List<PkPrizeVO>) pkPrizeListService.list(query);
            DataTip tip = new DataTip();
            tip.setData(list);
            return toJSONString(tip);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }

    /**
     *
     * @param query
     * @return
     */
    @RequestMapping("/viewPrice")
    public String viewPrize(PkPrizeQuery query, ModelMap map, Page page){
        query.setIndex(1);
        query.setSize(Integer.MAX_VALUE);
        List<PkPrizeVO> list = (List<PkPrizeVO>) pkPrizeListService.list(query);
        map.put("list", list);
        return "/theme/viewPrice";
    }
}
