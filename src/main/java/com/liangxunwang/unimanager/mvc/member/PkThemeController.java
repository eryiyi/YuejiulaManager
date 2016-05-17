package com.liangxunwang.unimanager.mvc.member;

import com.liangxunwang.unimanager.model.PKTheme;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.mvc.vo.CommentVO;
import com.liangxunwang.unimanager.mvc.vo.PkPrizeVO;
import com.liangxunwang.unimanager.mvc.vo.ZanVO;
import com.liangxunwang.unimanager.query.PkPrizeQuery;
import com.liangxunwang.unimanager.query.PkThemeQuery;
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
 * Created by liuzwei on 15-4-3.
 */
@Controller("pkThemeController")
public class PkThemeController extends ControllerConstants{
    @Autowired
    @Qualifier("pkThemService")
    private ListService themeListService;

    @Autowired
    @Qualifier("pkThemService")
    private SaveService themeSaveService;

    @Autowired
    @Qualifier("pkThemService")
    private FindService themeFindService;

    @Autowired
    @Qualifier("pkThemService")
    private UpdateService themeUpdateService;

    @Autowired
    @Qualifier("pkThemService")
    private DeleteService themeDeleteService;

    /**
     * 添加PK主题
     * @param theme
     * @return
     */
    @RequestMapping("/addPkTheme")
    @ResponseBody
    public String addPkTheme(PKTheme theme){
        if (StringUtil.isNullOrEmpty(theme.getTitle())){
            return toJSONString(ERROR_1);//主题不能为空
        }
        if (StringUtil.isNullOrEmpty(theme.getNumber()+"")){
            return toJSONString(ERROR_2);//期次不能为空
        }
        if (StringUtil.isNullOrEmpty(theme.getContent())){
            return toJSONString(ERROR_3);//内容不能为空
        }
        if (StringUtil.isNullOrEmpty(theme.getMudi())){
            return toJSONString(ERROR_4);//目的不能为空
        }
        try {
            themeSaveService.save(theme);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            String msg = e.getMessage();
            if (msg.equals("HAS_EXISTS")){
                return toJSONString(ERROR_6);//该期次活动已存在
            }
            if (msg.equals("TIME_ERROR")){
                return toJSONString(ERROR_7);//本期活动开始时间不能早于上期结束时间
            }
            return toJSONString(ERROR_5);
        }
    }

    @RequestMapping(value = "/listTheme", produces = "text/plain;charset=UTF-8;")
    public String listAllTheme(PkThemeQuery query, Page page,ModelMap map){
        query.setIndex(page.getPage()==0?1:page.getPage());
        query.setSize(query.getSize()==0?page.getDefaultSize():query.getSize());
        Object[] result = (Object[]) themeListService.list(query);
        map.put("list", result[0]);
        long count = (Long) result[1];
        page.setCount(count);
        page.setPageCount(calculatePageCount(query.getSize(), count));
        map.addAttribute("page", page);
        map.addAttribute("query", query);
        return "/theme/listTheme";
    }

    /**
     * 查看往期的所有主题
     * @return
     */
    @RequestMapping(value = "/listThemeApp", produces = "text/plain;charset=UTF-8;")
    @ResponseBody
    public String listAllThemeApp(PkThemeQuery query, Page page){
        query.setIndex(page.getPage() == 0 ? 1 : page.getPage());
        query.setSize(query.getSize() == 0 ? page.getDefaultSize() : query.getSize());
        query.setIsApp(true);
        try {
            Object[] result = (Object[]) themeListService.list(query);
            DataTip tip = new DataTip();
            tip.setData(result[0]);
            return toJSONString(tip);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }

    }


    /**
     * 调转到添加PK主题添加页面
     * @return
     */
    @RequestMapping("/toAddTheme")
    public String toAddTheme(){

        return "/theme/addTheme";
    }

    /**
     * 获得PK主题
     * @return
     */
    @RequestMapping(value = "/getTheme", produces = "text/plain;charset=UTF-8;")
    @ResponseBody
    public String getTheme(){
        try {
            PKTheme theme = (PKTheme) themeFindService.findById(null);
            DataTip tip = new DataTip();
            tip.setData(theme);
            return toJSONString(tip);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }

    /**
     *
     * @param themeId
     * @return
     */
    @RequestMapping("/addPrice")
    public String addTheme(String themeId, ModelMap map){
        PKTheme theme = (PKTheme) themeFindService.findById(themeId);
        map.put("theme", theme);
        return "/theme/addPrice";
    }

    /**
     * 每天凌晨一点查看过期活动, 并统计冠军
     * @return
     */
    public String updateTheme(){
        themeUpdateService.update(null);
        return null;
    }

    /**
     * 定时开启PK活动
     * @return
     */
    public String startTheme(){
        try {
            themeUpdateService.update("start");
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }

    /**
     * 根据主题ID删除主题
     * @param id
     * @return
     */
    @RequestMapping("/deleteTheme")
    @ResponseBody
    public String deleteTheme(String id){
        try {
            themeDeleteService.delete(id);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }


    @Autowired
    @Qualifier("pkPrizeService")
    private ListService pkPrizeListService;


    /**
     * 获得PK主题,新样式
     * @return
     */
    @RequestMapping(value = "/getThemeApp", produces = "text/plain;charset=UTF-8;")
    public String getTheme(PkPrizeQuery query, Page page, ModelMap map){
        query.setIndex(page.getPage() == 0 ? 1 : page.getPage());
        query.setSize(query.getSize() == 0 ? page.getDefaultSize() : query.getSize());
        try {
            PKTheme theme = (PKTheme) themeFindService.findById(null);
            query.setThemeId(theme.getId());
            List<PkPrizeVO> list = (List<PkPrizeVO>) pkPrizeListService.list(query);
            map.put("theme", theme);
            map.put("listPrize", list);
            return "/theme/viewTheme";
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }

}
