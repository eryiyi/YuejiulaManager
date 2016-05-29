package com.liangxunwang.unimanager.mvc.admin;

import com.liangxunwang.unimanager.model.Admin;
import com.liangxunwang.unimanager.model.SchoolThreeTingtai;
import com.liangxunwang.unimanager.model.SchoolThreeTingtaiBd;
import com.liangxunwang.unimanager.mvc.vo.SchoolThreePtBdVO;
import com.liangxunwang.unimanager.query.SchoolThreePtBdQuery;
import com.liangxunwang.unimanager.service.*;
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
 * Created by zhl on 2015/2/5.
 */
@Controller
public class SchoolThreePingtaiBdController extends ControllerConstants {
    @Autowired
    @Qualifier("schoolThreePingtaiBdService")
    private SaveService schoolThreePingtaiBdServiceSave;

    @Autowired
    @Qualifier("schoolThreePingtaiBdService")
    private ListService listNoticeService;

    @Autowired
    @Qualifier("schoolThreePingtaiBdService")
    private FindService findNoticeService;

    @Autowired
    @Qualifier("schoolThreePingtaiBdService")
    private UpdateService updateNoticeService;

    @Autowired
    @Qualifier("schoolThreePingtaiBdService")
    private DeleteService deleteNoticeService;



    @Autowired
    @Qualifier("schoolThreePingtaiService")
    private ListService schoolThreePingtaiServiceList;

    //绑定用户第三方平台

    @RequestMapping("/toBangdingThreePT")
    public String toBangdingThreePT(ModelMap map){
        List<SchoolThreeTingtai> listpt = (List<SchoolThreeTingtai>)schoolThreePingtaiServiceList.list("");
        map.put("listpt", listpt);
        map.put("listCitysAll", toJSONString(listpt));
        return "/threePingtai/addBangding";
    }


    @RequestMapping("/saveBangdingThreePtBd")
    @ResponseBody
    public String saveBangdingThreePt(HttpSession session, SchoolThreeTingtaiBd schoolThreeTingtaiBd){
        Admin admin = (Admin) session.getAttribute(ACCOUNT_KEY);
        if(admin != null){
            schoolThreeTingtaiBd.setEmp_id(admin.getEmpId());
        }
        try {
            schoolThreePingtaiBdServiceSave.save(schoolThreeTingtaiBd);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            String msg = e.getMessage();
            if (msg.equals("hasExist")){
                return toJSONString(ERROR_2);
            }else{
                return toJSONString(ERROR_1);
            }
        }
    }

    @RequestMapping("/listThreePingtaiBd")
    public String listThreePingtai(HttpSession session, ModelMap map,SchoolThreePtBdQuery query){
        Admin admin = (Admin) session.getAttribute(ACCOUNT_KEY);
        if(admin != null){
            query.setEmp_id(admin.getEmpId());
        }
        List<SchoolThreePtBdVO> list = (List<SchoolThreePtBdVO>) listNoticeService.list(query);
        map.put("list", list);
        return "/threePingtai/listBangDing";
    }


    @RequestMapping("/toUpdateThreePingtaiBd")
    public String toUpdateThreePingtai(String emp_pingtai_mng_id, ModelMap map){
        SchoolThreeTingtaiBd schoolThreeTingtai = (SchoolThreeTingtaiBd) findNoticeService.findById(emp_pingtai_mng_id);
        map.put("schoolThreeTingtai", schoolThreeTingtai);

        List<SchoolThreeTingtai> listpt = (List<SchoolThreeTingtai>)schoolThreePingtaiServiceList.list("");
        map.put("listpt", listpt);

        return "/threePingtai/updateBangding";
    }

    @RequestMapping("/updateThreePingtaiBd")
    @ResponseBody
    public String updateThreePingtai(SchoolThreeTingtaiBd schoolThreeTingtai){

        if (StringUtil.isNullOrEmpty(schoolThreeTingtai.getSchool_three_pingtai_id())){
            return toJSONString(ERROR_1);
        }

        updateNoticeService.update(schoolThreeTingtai);
        return toJSONString(SUCCESS);
    }

    @RequestMapping("/deleteThreePingtaiBd")
    @ResponseBody
    public String deleteThreePingtai(String emp_pingtai_mng_id){
        try {
            deleteNoticeService.delete(emp_pingtai_mng_id);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }


}
