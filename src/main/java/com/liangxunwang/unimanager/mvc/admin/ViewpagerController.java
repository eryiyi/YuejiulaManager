package com.liangxunwang.unimanager.mvc.admin;

import com.liangxunwang.unimanager.model.Admin;
import com.liangxunwang.unimanager.model.Viewpager;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.mvc.vo.ContractSchoolVO;
import com.liangxunwang.unimanager.mvc.vo.PaopaoGoodsVO;
import com.liangxunwang.unimanager.mvc.vo.ViewpagerVO;
import com.liangxunwang.unimanager.query.PaopaoGoodsQuery;
import com.liangxunwang.unimanager.query.ViewpagerQuery;
import com.liangxunwang.unimanager.service.DeleteService;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.SaveService;
import com.liangxunwang.unimanager.util.ControllerConstants;
import com.liangxunwang.unimanager.util.Page;
import com.liangxunwang.unimanager.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by liuzh on 2015/8/26.
 * 商城轮播相关接口
 */
@Controller
@RequestMapping("/viewpager")
public class ViewpagerController extends ControllerConstants {

    @Autowired
    @Qualifier("contractSchoolService")
    private ListService contractSchoolListService;

    @Autowired
    @Qualifier("viewpagerService")
    private ListService viewpagerListService;

    @Autowired
    @Qualifier("viewpagerService")
    private DeleteService viewpagerDeleteService;

    @Autowired
    @Qualifier("viewpagerService")
    private SaveService viewpagerSaveService;

    @Autowired
    @Qualifier("paopaoGoodsService")
    private ListService paopaoGoodsListService;

    /**
     * 手机端查询商城轮播
     * @return
     */
    @RequestMapping(value = "appList", produces = "text/plain;charset=UTF-8;")
    @ResponseBody
    public String appList(ViewpagerQuery query){
        if (StringUtil.isNullOrEmpty(query.getSchoolId())){
            return toJSONString(SUCCESS);
        }
        List<Viewpager> list = (List<Viewpager>) viewpagerListService.list(query);
        DataTip tip = new DataTip();
        tip.setData(list);
        return toJSONString(tip);
    }

    /**
     * 后台查看商城轮播列表
     * @param map
     * @return
     */
    @RequestMapping("list")
    public String list(ViewpagerQuery query, HttpSession session, ModelMap map){
        Admin admin = (Admin) session.getAttribute(ACCOUNT_KEY);
        if (admin != null){
            query.setType(admin.getType());
            if (!"1".equals(query.getEmpId())){
                query.setEmpId(admin.getEmpId());
            }
        }
        List<ViewpagerVO> list = (List<ViewpagerVO>) viewpagerListService.list(query);
        map.put("list", list);
        return "/viewpager/list";
    }

    /**
     * 后台删除轮播
     * @param id
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public String delete(String id){
        viewpagerDeleteService.delete(id);
        return toJSONString(SUCCESS);
    }

    /**
     * 后台添加轮播
     * @param viewpager
     * @return
     */
    @RequestMapping("save")
    @ResponseBody
    public String save(Viewpager viewpager){
        if (StringUtil.isNullOrEmpty(viewpager.getPicAddress())){
            return toJSONString(ERROR_1);
        }
        viewpagerSaveService.save(viewpager);
        return toJSONString(SUCCESS);
    }

    @RequestMapping("toSave")
    public String toSave(HttpSession session, ModelMap map){
        Admin admin = (Admin) session.getAttribute(ACCOUNT_KEY);
        List<ContractSchoolVO> contractSchoolVOs = (List<ContractSchoolVO>) contractSchoolListService.list(admin.getEmpId());
        map.put("vo", contractSchoolVOs);
        return "/viewpager/add";
    }

    @RequestMapping(value = "searchGoods", produces = "text/plain;charset=UTF-8;")
    @ResponseBody
    public String searchGoods(PaopaoGoodsQuery query, Page page){
        query.setIndex(page.getPage()==0?1:page.getPage());
        query.setSize(query.getSize() == 0 ? page.getDefaultSize() : query.getSize());
        List<PaopaoGoodsVO> list = (List<PaopaoGoodsVO>) paopaoGoodsListService.list(query);
        return toJSONString(list);
    }
}
