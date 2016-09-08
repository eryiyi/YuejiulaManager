package com.liangxunwang.unimanager.mvc.admin;

import com.liangxunwang.unimanager.model.Admin;
import com.liangxunwang.unimanager.model.GoodsType;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.mvc.vo.ContractSchoolVO;
import com.liangxunwang.unimanager.query.GoodsTypeThreeQuery;
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

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by zhl on 2015/2/2.
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


    @Autowired
    @Qualifier("contractSchoolService")
    private ListService contractSchoolListService;

    @RequestMapping("/toAddGoodsTypeThree")
    public String toAddGoodsTypeThree(HttpSession session, ModelMap map){
        //查询当前圈主的学校
        Admin admin = (Admin) session.getAttribute(ACCOUNT_KEY);
        List<ContractSchoolVO> contractSchoolVOs = (List<ContractSchoolVO>) contractSchoolListService.list(admin.getEmpId());
        map.put("schools", contractSchoolVOs);

        return "/goodsType/addTypeThree";
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

    @Autowired
    @Qualifier("goodsTypeThreeService")
    private SaveService goodsTypeThreeServiceSave;


    @RequestMapping("/addGoodsTypeThree")
    @ResponseBody
    public String addGoodsTypeThree(GoodsType type, String schools, HttpSession session){
        Admin admin = (Admin) session.getAttribute(ACCOUNT_KEY);
        if (StringUtil.isNullOrEmpty(type.getTypeName())){
            return toJSONString(ERROR_1);//名称不能为空
        }
        if (StringUtil.isNullOrEmpty(type.getTypeContent())){
            return toJSONString(ERROR_2);//介绍不能为空
        }
        if (StringUtil.isNullOrEmpty(type.getTypeCover())){
            return toJSONString(ERROR_3);//图片不能为空
        }
        if(!"2".equals(admin.getType())){
            return toJSONString(ERROR_4);//不是圈主不能设置
        }else {
            type.setEmp_id(admin.getEmpId());
        }
        Object[] params = new Object[]{type, schools};

        try {
            goodsTypeThreeServiceSave.save(params);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            return toJSONString(ERROR_5);
        }
    }


    @RequestMapping("/listType")
    public String listType(ModelMap map, GoodsTypeThreeQuery query, HttpSession session){
        Admin admin = (Admin) session.getAttribute(ACCOUNT_KEY);
        if("2".equals(admin.getType())){
           //说明是圈主
            query.setLx_goods_type_type("1");//圈主可以查看第三方  自己的第三方
            query.setEmp_id(admin.getEmpId());//圈主的id
        }else {
            query.setLx_goods_type_type("0");//管理员用户只能查看商城分类
        }
        List<GoodsType> list = (List<GoodsType>) listGoodsTypeService.list(query);
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



    @Autowired
    @Qualifier("goodsTypeThreeService")
    private ListService goodsTypeThreeServiceList;

    @RequestMapping(value = "/goodsTypeList", produces = "text/plain; charset=utf-8")
    @ResponseBody
    public String getTypeList(GoodsTypeThreeQuery query){
        List<GoodsType> list = (List<GoodsType>) goodsTypeThreeServiceList.list(query);
        for (GoodsType type : list){
            type.setTypeCover(Constants.URL+type.getTypeCover());
        }
        DataTip tip  = new DataTip();
        tip.setData(list);
        return toJSONString(tip);
    }

    @Autowired
    @Qualifier("goodsTypeThreeService2")
    private ListService goodsTypeThreeService2;

    @RequestMapping(value = "/goodsTypeList2", produces = "text/plain; charset=utf-8")
    @ResponseBody
    public String goodsTypeList2(GoodsTypeThreeQuery query){
        List<GoodsType> list = (List<GoodsType>) goodsTypeThreeService2.list(query);
        for (GoodsType type : list){
            type.setTypeCover(Constants.URL+type.getTypeCover());
        }
        DataTip tip  = new DataTip();
        tip.setData(list);
        return toJSONString(tip);
    }

}
