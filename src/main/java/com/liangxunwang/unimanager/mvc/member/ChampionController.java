package com.liangxunwang.unimanager.mvc.member;

import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.mvc.vo.ChampionVO;
import com.liangxunwang.unimanager.query.ChampionQuery;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.service.member.ChampionService;
import com.liangxunwang.unimanager.util.ControllerConstants;
import com.liangxunwang.unimanager.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Observable;

/**
 * Created by liuzwei on 2015/4/9.
 */
@Controller
public class ChampionController extends ControllerConstants{

    @Autowired
    @Qualifier("championService")
    private ListService championListService;

    @Autowired
    @Qualifier("championService")
    private ExecuteService championExecuteService;

    @Autowired
    @Qualifier("championService")
    private UpdateService championUpdateService;

    /**
     * ��ùھ���
     * @param query
     * @param page
     * @return
     */
    @RequestMapping(value = "/getChampion", produces = "text/plain;charset=UTF-8;")
    @ResponseBody
    private String getChampion(ChampionQuery query, Page page){
        query.setIndex(page.getPage() == 0 ? 1 : page.getPage());
        query.setSize(query.getSize() == 0 ? page.getDefaultSize() : query.getSize());
        try {
            List<ChampionVO> list = (List<ChampionVO>) championListService.list(query);
            DataTip tip = new DataTip();
            tip.setData(list);
            return toJSONString(tip);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }

    /**
     * 分页查询所有的冠军
     * @param query
     * @param page
     * @return
     */
    @RequestMapping("/championList")
    public String championList(ChampionQuery query, Page page, ModelMap map){
        query.setIndex(page.getPage() == 0 ? 1 : page.getPage());
        query.setSize(query.getSize() == 0 ? page.getDefaultSize() : query.getSize());
        query.setType("0");
        Object[] result = (Object[]) championExecuteService.execute(query);
        map.put("list", result[0]);
        long count = (Long) result[1];
        page.setCount(count);
        page.setPageCount(calculatePageCount(query.getSize(), count));
        map.addAttribute("page", page);
        map.addAttribute("query", query);
        return "/theme/championList";
    }

    /**
     * 根据冠军ID添加照片
     * @param id
     * @param pic
     * @return
     */
    @RequestMapping("/uploadChampionPic")
    @ResponseBody
    public String uploadChampionPic(String id, String pic){
        try {
            Object[] params = new Object[]{id, pic};
            championUpdateService.update(params);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }

    /**
     * 确认领奖
     * @param championId
     * @return
     */
    @RequestMapping("/championSure")
    @ResponseBody
    public String championSure(String championId){
        try {
            championUpdateService.update(championId);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            if (e.getMessage().equals("HAS_GET")) {
                return toJSONString(ERROR_1);
            }else {
                return toJSONString(ERROR_2);
            }
        }
    }

    /**
     * 跳转到上传照片页面
     * @param championId
     * @param map
     * @return
     */
    @RequestMapping("/toUploadPic")
    public String toUploadPic (String championId, ModelMap map){
        map.put("championId", championId);
        return "/theme/addChampionPic";
    }


}
