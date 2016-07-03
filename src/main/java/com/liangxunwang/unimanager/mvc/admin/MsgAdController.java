package com.liangxunwang.unimanager.mvc.admin;

import com.liangxunwang.unimanager.model.AdObj;
import com.liangxunwang.unimanager.model.Admin;
import com.liangxunwang.unimanager.model.MsgAd;
import com.liangxunwang.unimanager.mvc.vo.ContractSchoolVO;
import com.liangxunwang.unimanager.mvc.vo.SellerSchoolList;
import com.liangxunwang.unimanager.query.AdQuery;
import com.liangxunwang.unimanager.query.MsgAdQuery;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.ControllerConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/msgAdController")
public class MsgAdController extends ControllerConstants {

    @Autowired
    @Qualifier("msgAdObjService")
    private ListService levelService;

    @Autowired
    @Qualifier("msgAdObjService")
    private SaveService levelServiceSave;

    @Autowired
    @Qualifier("msgAdObjService")
    private ExecuteService levelServiceSaveExe;

    @Autowired
    @Qualifier("msgAdObjService")
    private UpdateService levelServiceSaveUpdate;

    @Autowired
    @Qualifier("msgAdObjService")
    private DeleteService levelServiceSaveDel;


    @RequestMapping("list")
    public String list(HttpSession session,ModelMap map, MsgAdQuery query){
        Admin manager = (Admin) session.getAttribute(ACCOUNT_KEY);
        if(manager != null){
            query.setEmp_id(manager.getEmpId());
        }
        List<MsgAd> list = (List<MsgAd>) levelService.list(query);
        map.put("list", list);
        return "/msgad/list";
    }

    @Autowired
    @Qualifier("contractSchoolService")
    private ListService contractSchoolListService;

    @RequestMapping("add")
    public String add(ModelMap map, HttpSession session ){
        Admin admin = (Admin) session.getAttribute(ACCOUNT_KEY);
//        List<SellerSchoolList> schools = (List<SellerSchoolList>) sellerGoodsListService.list(admin.getEmpId());
        List<ContractSchoolVO> contractSchoolVOs = (List<ContractSchoolVO>) contractSchoolListService.list(admin.getEmpId());
        map.put("schools", contractSchoolVOs);
        return "/msgad/addAd";
    }

    @RequestMapping("addAd")
    @ResponseBody
    public String addAd(String schools,HttpSession session,MsgAd adObj){
        Admin manager = (Admin) session.getAttribute(ACCOUNT_KEY);
        if(manager != null){
            adObj.setEmp_id(manager.getEmpId());
        }
        try {
            Object[] params = new Object[]{adObj, schools};
            levelServiceSave.save(params);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            String msg = e.getMessage();
            if (msg.equals("adIsTooMuch")){
                return toJSONString(ERROR_2);
            }else{
                return toJSONString(ERROR_1);
            }
        }
    }

    @RequestMapping("delete")
    @ResponseBody
    public String delete(HttpSession session,String msg_ad_no){
        Admin manager = (Admin) session.getAttribute(ACCOUNT_KEY);
        levelServiceSaveDel.delete(msg_ad_no);
        return toJSONString(SUCCESS);
    }

    @RequestMapping("/edit")
    public String toUpdateType(HttpSession session,ModelMap map, String msg_ad_no) throws Exception {
        Admin manager = (Admin) session.getAttribute(ACCOUNT_KEY);
        MsgAd adObj = (MsgAd) levelServiceSaveExe.execute(msg_ad_no);
        map.put("adObj", adObj);
        return "/msgad/editAd";
    }

    /**
     * 更新
     */
    @RequestMapping("/editAd")
    @ResponseBody
    public String updateGoodsType(HttpSession session,MsgAd adObj){
        Admin manager = (Admin) session.getAttribute(ACCOUNT_KEY);
        try {
            levelServiceSaveUpdate.update(adObj);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }



}
