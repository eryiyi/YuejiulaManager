package com.liangxunwang.unimanager.mvc.admin;

import com.liangxunwang.unimanager.model.GoodsType;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.Constants;
import com.liangxunwang.unimanager.util.ControllerConstants;
import com.liangxunwang.unimanager.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by liuzwei on 2015/2/2.
 */
@Controller
public class GoodsTypeController extends ControllerConstants{

    @Autowired
    @Qualifier("goodsTypeService")
    private SaveService saveGoodsTypeService;

    @Autowired
    @Qualifier("goodsTypeService")
    private ListService listGoodsTypeService;

    @Autowired
    @Qualifier("goodsTypeService")
    private FindService findGoodsTypeService;

    @Autowired
    @Qualifier("goodsTypeService")
    private UpdateService updateGoodsTypeService;

    @Autowired
    @Qualifier("goodsTypeService")
    private DeleteService deleteGoodsTypeService;

    @RequestMapping("/toAddGoodsType")
    public String toAddType(){

        return "/goodsType/addType";
    }

    @RequestMapping("/addGoodsType")
    @ResponseBody
    public String addType(GoodsType type){
        if (StringUtil.isNullOrEmpty(type.getTypeName())){
            return toJSONString(ERROR_1);//名称不能为空
        }
        if (StringUtil.isNullOrEmpty(type.getTypeContent())){
            return toJSONString(ERROR_2);//介绍不能为空
        }
        if (StringUtil.isNullOrEmpty(type.getTypeCover())){
            return toJSONString(ERROR_3);//图片不能为空
        }

        try {
            saveGoodsTypeService.save(type);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            return toJSONString(ERROR_4);
        }
    }

    @RequestMapping("/listType")
    public String listType(ModelMap map){
        List<GoodsType> list = (List<GoodsType>) listGoodsTypeService.list(null);
        map.put("list", list);
        return "/goodsType/listType";
    }

    @RequestMapping("/toUpdateType")
    public String toUpdateType(ModelMap map, String typeId){
        GoodsType type = (GoodsType) findGoodsTypeService.findById(typeId);
        map.put("type", type);
        return "/goodsType/updateType";
    }

    /**
     * 更新分类
     * @param type
     * @return
     */
    @RequestMapping("/updateGoodsType")
    @ResponseBody
    public String updateGoodsType(GoodsType type){
        try {
            updateGoodsTypeService.update(type);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }

    @RequestMapping("/deleteType")
    @ResponseBody
    public String deleteType(String typeId){
        try {
            deleteGoodsTypeService.delete(typeId);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }

    @RequestMapping(value = "/goodsTypeList", produces = "text/plain; charset=utf-8")
    @ResponseBody
    public String getTypeList(){
        List<GoodsType> list = (List<GoodsType>) listGoodsTypeService.list("0");
        for (GoodsType type : list){
            type.setTypeCover(Constants.URL+type.getTypeCover());
        }
        DataTip tip  = new DataTip();
        tip.setData(list);
        return toJSONString(tip);
    }

}
