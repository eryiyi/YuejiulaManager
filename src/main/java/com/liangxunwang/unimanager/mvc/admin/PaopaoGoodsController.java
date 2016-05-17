package com.liangxunwang.unimanager.mvc.admin;

import com.liangxunwang.unimanager.model.Admin;
import com.liangxunwang.unimanager.model.GoodsType;
import com.liangxunwang.unimanager.model.PaopaoGoods;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.mvc.vo.GoodsVO;
import com.liangxunwang.unimanager.mvc.vo.PaopaoGoodsVO;
import com.liangxunwang.unimanager.mvc.vo.SellerSchoolList;
import com.liangxunwang.unimanager.query.PaopaoGoodsQuery;
import com.liangxunwang.unimanager.service.*;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuzh on 2015/8/16.
 */
@Controller
@RequestMapping("/paopaogoods")
public class PaopaoGoodsController extends ControllerConstants {

    @Autowired
    @Qualifier("sellerGoodsService")
    private ListService sellerGoodsListService;

    @Autowired
    @Qualifier("goodsTypeService")
    private ListService goodsTypeListService;

    @Autowired
    @Qualifier("paopaoGoodsService")
    private SaveService paopaoGoodsSaveService;

    @Autowired
    @Qualifier("paopaoGoodsService")
    private ListService paopaoGoodsListService;

    @Autowired
    @Qualifier("paopaoGoodsService")
    private DeleteService paopaoGoodsDeleteService;

    @Autowired
    @Qualifier("paopaoGoodsService")
    private FindService paopaoGoodsFindService;

    @Autowired
    @Qualifier("paopaoGoodsService")
    private UpdateService paopaoGoodsUpdateService;
    @Autowired
    @Qualifier("appPaopaoGoodsService")
    private UpdateService appPaopaoGoodsUpdateService;
    
    @RequestMapping("toAdd")
    public String toAdd(ModelMap map, HttpSession session){
        Admin admin = (Admin) session.getAttribute(ACCOUNT_KEY);
        List<GoodsType> list = (List<GoodsType>) goodsTypeListService.list("0");
        List<SellerSchoolList> schools = (List<SellerSchoolList>) sellerGoodsListService.list(admin.getEmpId());
        map.put("list", list);
        map.put("schools", schools);
        return "/paopaogoods/add";
    }

    @RequestMapping(value = "save", produces = "text/plain;charset=UTF-8;")
    @ResponseBody
    public String save(PaopaoGoods goods, String schools, HttpSession session){
        Admin admin = (Admin) session.getAttribute(ACCOUNT_KEY);
        goods.setEmpId(admin.getEmpId());
        Object[] params = new Object[]{goods, schools, admin.getGoodsCount()};
        String str = (String) paopaoGoodsSaveService.save(params);
        if (!StringUtil.isNullOrEmpty(str)){
            DataTip tip = new DataTip();
            tip.setData(str);
            return toJSONString(tip);
        }
        return toJSONString(SUCCESS);
    }

    /**
     * App端取宝贝列表
     * @param query
     * @return
     */
    @RequestMapping(value = "listGoods", produces = "text/plain;charset=UTF-8;")
    @ResponseBody
    public String listGoods(PaopaoGoodsQuery query, Page page){
        try {
            query.setIndex(page.getPage()==0?1:page.getPage());
            query.setSize(query.getSize()==0?page.getDefaultSize():query.getSize());
            List<PaopaoGoodsVO> list = (List<PaopaoGoodsVO>) paopaoGoodsListService.list(query);
            DataTip tip = new DataTip();
            tip.setData(list);
            return toJSONString(tip);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }


    /**
     * 后台查询我的商品
     * @param query
     * @param page
     * @param session
     * @param map
     * @return
     */
    @RequestMapping("list")
    public String list(PaopaoGoodsQuery query, Page page, HttpSession session, ModelMap map){
        Admin admin = (Admin) session.getAttribute(ACCOUNT_KEY);
        query.setIndex(page.getPage()==0?1:page.getPage());
        query.setSize(query.getSize()==0?page.getDefaultSize():query.getSize());
        Object[] params = new Object[]{query,admin.getEmpId()};
        Object[] results = (Object[]) paopaoGoodsListService.list(params);
        map.put("list", results[0]);
        long count = (Long) results[1];
        page.setCount(count);
        page.setPageCount(calculatePageCount(query.getSize(), count));
        map.addAttribute("page", page);
        map.addAttribute("query", query);
        return "/paopaogoods/list";
    }

    /**
     * 根据ID删除我的宝贝
     * @param id
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public String delete(String id){
        paopaoGoodsDeleteService.delete(id);
        return toJSONString(SUCCESS);
    }

    @RequestMapping("edit")
    public String edit(String id, ModelMap map){
        List<GoodsType> list = (List<GoodsType>) goodsTypeListService.list("0");
        PaopaoGoods goods = (PaopaoGoods) paopaoGoodsFindService.findById(id);
        map.put("list", list);
        map.put("goods", goods);
        return "/paopaogoods/edit";
    }

    @RequestMapping("update")
    @ResponseBody
    public String update(PaopaoGoods goods){
        paopaoGoodsUpdateService.update(goods);
        return toJSONString(SUCCESS);
    }

    /**
     * App根据ID查询宝贝详情
     * @param id
     * @return
     */
    @RequestMapping(value = "findById", produces = "text/plain;charset=UTF-8;")
    @ResponseBody
    public String findByGoodsId(String id){
        if (StringUtil.isNullOrEmpty(id)){
            return toJSONString(ERROR_1);
        }
        Object[] params = new Object[]{id};
        PaopaoGoodsVO vo = (PaopaoGoodsVO) paopaoGoodsFindService.findById(params);
        DataTip tip = new DataTip();
        tip.setData(vo);
        return toJSONString(tip);
    }

    /**
     * 根据商品ID查找商品详情
     * @param id
     * @param map
     * @return
     */
    @RequestMapping("detail")
    public String detail(String id, ModelMap map){
        if (StringUtil.isNullOrEmpty(id)){
            return toJSONString(ERROR_1);
        }
        Object[] params = new Object[]{id};
        PaopaoGoodsVO vo = (PaopaoGoodsVO) paopaoGoodsFindService.findById(params);
        map.put("vo", vo);
        return "/paopaogoods/detail";
    }

    @RequestMapping("updatePosition")
    @ResponseBody
    public String updatePosition(String id, String position){
        Map<String, String> map = new HashMap<String, String>();
        map.put("id" , id);
        map.put("goodsPosition" , position);
        appPaopaoGoodsUpdateService.update(map);
        return toJSONString(SUCCESS);
    }

}
