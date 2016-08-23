package com.liangxunwang.unimanager.mvc.admin;

import com.liangxunwang.unimanager.model.Admin;
import com.liangxunwang.unimanager.model.GoodsType;
import com.liangxunwang.unimanager.model.PaopaoGoods;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.mvc.vo.PaopaoGoodsVO;
import com.liangxunwang.unimanager.mvc.vo.SellerGoodsVO;
import com.liangxunwang.unimanager.mvc.vo.SellerSchoolList;
import com.liangxunwang.unimanager.query.GoodsTypeThreeQuery;
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
    @Qualifier("paopaoGoodsZhiyingService")
    private SaveService paopaoGoodsZhiyingService;

    @Autowired
    @Qualifier("paopaoGoodsService")
    private ListService paopaoGoodsListService;

    @Autowired
    @Qualifier("paopaoGoodsZhiyingService")
    private ListService paopaoGoodsZhiyingServiceList;


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
    public String toAdd(GoodsTypeThreeQuery query,ModelMap map, HttpSession session){
        Admin admin = (Admin) session.getAttribute(ACCOUNT_KEY);
        List<GoodsType> list = (List<GoodsType>) goodsTypeListService.list(query);
        query.setLx_goods_type_type("0");
        query.setType_isuse("0");
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

    @RequestMapping(value = "saveAppGoods", produces = "text/plain;charset=UTF-8;")
    @ResponseBody
    public String saveAppGoods(PaopaoGoods goods, String schools){
        Object[] params = new Object[]{goods, schools, "0"};
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
            List<PaopaoGoodsVO> list1 = new ArrayList<PaopaoGoodsVO>();
            if("1".equals(query.getIsMine())){
                //说明查询我的
                list1.addAll(list);
            }else{
                //说明不是查询我的
                for(PaopaoGoodsVO paopaoGoodsVO:list){
                    if("0".equals(paopaoGoodsVO.getIsUse())){
                        list1.add(paopaoGoodsVO);//只查询有用的
                    }
                }
            }
            DataTip tip = new DataTip();
            tip.setData(list1);
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
        GoodsTypeThreeQuery query = new GoodsTypeThreeQuery();
        List<GoodsType> list = (List<GoodsType>) goodsTypeListService.list(query);
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
        Object[] paramsa = (Object[]) paopaoGoodsFindService.findById(params);
//        PaopaoGoodsVO vo = (PaopaoGoodsVO) paopaoGoodsFindService.findById(params);
        map.put("vo", paramsa[0]);
        map.put("listPics", paramsa[1]);
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



    @Autowired
    @Qualifier("appPaopaoGoodsJiaService")
    private UpdateService appPaopaoGoodsJiaService;
    /**
     * App上架 下架产品
     * @param id
     * @return
     */
    @RequestMapping(value = "updatePaopaoGoodsJia", produces = "text/plain;charset=UTF-8;")
    @ResponseBody
    public String updatePaopaoGoodsJia(String id,String status){
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", id);
        map.put("status", status);
        appPaopaoGoodsJiaService.update(map);
        return toJSONString(SUCCESS);
    }



    /**
     * 后台查询我的商品--承包商
     * @param query
     * @param page
     * @param session
     * @param map
     * @return
     */
    @RequestMapping("listZhiying")
    public String listZhiying(PaopaoGoodsQuery query, Page page, HttpSession session, ModelMap map){
        Admin admin = (Admin) session.getAttribute(ACCOUNT_KEY);
        if(admin != null){
            query.setManager_id(admin.getEmpId()==null?"":admin.getEmpId());//承包商id
        }
        query.setIndex(page.getPage()==0?1:page.getPage());
        query.setSize(query.getSize()==0?page.getDefaultSize():query.getSize());
        Object[] params = new Object[]{query,admin.getEmpId()};
        Object[] results = (Object[]) paopaoGoodsZhiyingServiceList.list(params);
        map.put("list", results[0]);
        long count = (Long) results[1];
        page.setCount(count);
        page.setPageCount(calculatePageCount(query.getSize(), count));
        map.addAttribute("page", page);
        map.addAttribute("query", query);
        return "/paopaogoods/listZhiying";
    }


    @Autowired
    @Qualifier("mineSellerService")
    private ExecuteService mineSellerService;


    @RequestMapping("toAddZhiying")
    public String toAddZhiying( GoodsTypeThreeQuery query, ModelMap map, HttpSession session){
        Admin admin = (Admin) session.getAttribute(ACCOUNT_KEY);
        query.setLx_goods_type_type("0");//管理员用户只能查看商城分类
        List<GoodsType> list = (List<GoodsType>) goodsTypeListService.list(query);//商品类别
        map.put("list", list);
        //查询我的商家
        List<SellerGoodsVO> listSh = (List<SellerGoodsVO>) mineSellerService.execute(admin.getEmpId());
        map.put("listSh", listSh);
        return "/paopaogoods/addZhiying";
    }


//    41425f5e51bc4bd98715c3efe888da11|1089|41425f5e51bc4bd98715c3efe888da11|1095|41425f5e51bc4bd98715c3efe888da11|1102
    @RequestMapping(value = "saveZhiying", produces = "text/plain;charset=UTF-8;")
    @ResponseBody
    public String saveZhiying(PaopaoGoods goods, HttpSession session){
        Admin admin = (Admin) session.getAttribute(ACCOUNT_KEY);
        if(admin != null){
            goods.setManager_id(admin.getEmpId());
        }
        Object[] params = new Object[]{goods};
        String str = (String) paopaoGoodsZhiyingService.save(params);
        if (!StringUtil.isNullOrEmpty(str)){
            DataTip tip = new DataTip();
            tip.setData(str);
            return toJSONString(tip);
        }
        return toJSONString(SUCCESS);
    }

    @Autowired
    @Qualifier("paopaoGoodsZhiyingService")
    private UpdateService paopaoGoodsZhiyingServiceUpdate;

    @RequestMapping("updateZhiyingYouhuo")
    @ResponseBody
    public String updateZhiyingYouhuo(String  id,String is_youhuo){
        Object[] params = new Object[]{id, is_youhuo};
        paopaoGoodsZhiyingServiceUpdate.update(params);
        return toJSONString(SUCCESS);
    }



    /**
     * 后台查询商品列表
     * @param query
     * @param page
     * @param session
     * @param map
     * @return
     */
    @RequestMapping("listsgoods")
    public String listsgoods(PaopaoGoodsQuery query, Page page, HttpSession session, ModelMap map){
        Admin admin = (Admin) session.getAttribute(ACCOUNT_KEY);
        query.setIndex(page.getPage()==0?1:page.getPage());
        query.setSize(query.getSize()==0?page.getDefaultSize():query.getSize());
        Object[] params = new Object[]{query,""};
        Object[] results = (Object[]) paopaoGoodsListService.list(params);
        map.put("list", results[0]);
        long count = (Long) results[1];
        page.setCount(count);
        page.setPageCount(calculatePageCount(query.getSize(), count));
        map.addAttribute("page", page);
        map.addAttribute("query", query);
        return "/paopaogoods/listAll";
    }


}
