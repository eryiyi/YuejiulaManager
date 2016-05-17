package com.liangxunwang.unimanager.mvc.member;

import com.liangxunwang.unimanager.model.Goods;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.mvc.vo.GoodsVO;
import com.liangxunwang.unimanager.mvc.vo.PaopaoGoodsVO;
import com.liangxunwang.unimanager.mvc.vo.RecordVO;
import com.liangxunwang.unimanager.query.GoodsQuery;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.Constants;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuzwei on 2015/2/2.
 */
@Controller
public class GoodsController extends ControllerConstants {

    @Autowired
    @Qualifier("goodsService")
    private SaveService saveGoodsService;

    @Autowired
    @Qualifier("goodsService")
    private ListService listGoodsService;

    @Autowired
    @Qualifier("goodsService")
    private FindService findGoodsService;

    @Autowired
    @Qualifier("goodsService")
    private DeleteService deleteGoodsService;

    @Autowired
    @Qualifier("goodsService")
    private UpdateService updateGoodsService;

    @Autowired
    @Qualifier("paopaoGoodsService")
    private FindService paopaoGoodsFindService;

    @RequestMapping("/addGoods")
    @ResponseBody
    public String addGoods(Goods goods){
        if (StringUtil.isNullOrEmpty(goods.getName())){
            return toJSONString(ERROR_1);//商品名称不能为空
        }
        if (StringUtil.isNullOrEmpty(goods.getContent())){
            return toJSONString(ERROR_2);//商品介绍不能为空
        }
        if (StringUtil.isNullOrEmpty(goods.getCover())){
            return toJSONString(ERROR_3);//商品图片不能为空
        }
        if (StringUtil.isNullOrEmpty(goods.getMoney())){
            return toJSONString(ERROR_4);//商品价格不能为空
        }
        try {
            saveGoodsService.save(goods);
            return  toJSONString(SUCCESS);//保存成功
        }catch (ServiceException e){
            return toJSONString(ERROR_5);//保存失败
        }
    }
    @RequestMapping(value = "/listGoodsByType", produces = "text/plain; charset=UTF-8")
    @ResponseBody
    public String listGoodsByType(GoodsQuery query, Page page){
        try {
            query.setIndex(page.getPage()==0?1:page.getPage());
            query.setSize(query.getSize()==0?page.getDefaultSize():query.getSize());
            List<GoodsVO> list = (List<GoodsVO>) listGoodsService.list(query);
            DataTip tip = new DataTip();
            tip.setData(list);
            return toJSONString(tip);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }

    @RequestMapping(value = "/findGoodsById", produces = "text/plain;charset=UTF-8;")
    @ResponseBody
    public String findGoodsById(String goodsId){
        try {
            GoodsVO goodsVO = (GoodsVO) findGoodsService.findById(goodsId);
            DataTip tip = new DataTip();
            tip.setData(goodsVO);
            return toJSONString(tip);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }

    /**
     * 下架和删除商品
     * @param goodsId
     * @param type  1 下架  2 删除
     * @return
     */
    @RequestMapping("/deleteGoodsById")
    @ResponseBody
    public String deleteGoodsById(@RequestParam String goodsId, @RequestParam String type){
        try {
            Object[] params = new Object[]{goodsId, type};
            deleteGoodsService.delete(params);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }

    @RequestMapping(value = "/viewGoods",  produces = "text/plain;charset=UTF-8;")
    public String viewGoods(String goodsId, ModelMap map){
        try {
            Object[] params = new Object[]{goodsId};
            PaopaoGoodsVO goods = (PaopaoGoodsVO) paopaoGoodsFindService.findById(params);
            map.put("vo", goods);
            return "/paopaogoods/viewGoods";
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }

    /**
     * 根据商品ID进行置顶
     * @param goodsId
     * @param position
     * @return
     */
    @RequestMapping("/updateGoodsPosition")
    @ResponseBody
    public String updatePosition(@RequestParam String goodsId, @RequestParam String position){
        try {
            Object[] params = new Object[]{goodsId, position};
            updateGoodsService.update(params);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }
}
