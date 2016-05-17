package com.liangxunwang.unimanager.mvc.admin;

import com.liangxunwang.unimanager.model.University;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.ChinaUtil;
import com.liangxunwang.unimanager.util.Constants;
import com.liangxunwang.unimanager.util.ControllerConstants;
import com.liangxunwang.unimanager.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuzwei on 2015/1/30.
 */
@Controller
public class UniversityController extends ControllerConstants{
    @Autowired
    @Qualifier("universityService")
    private SaveService universitySaveService;

    @Autowired
    @Qualifier("universityService")
    private ListService universityListService;

    @Autowired
    @Qualifier("universityService")
    private FindService universityFindService;

    @Autowired
    @Qualifier("universityService")
    private UpdateService universityUpdateService;

    @Autowired
    @Qualifier("universityService")
    private DeleteService deleteUniversityService;


    @RequestMapping("/ajax/addUniversity")
    public String addPage(){

        return "/university/addUniversity";
    }
    @RequestMapping("/ajax/listUniversity")
    public String listUniversity(ModelMap map){
        List<University> list = (List<University>) universityListService.list(null);
        List<University> universityList = new ArrayList<University>();
        for (int i=0; i<list.size() ; i++) {
            University university = list.get(i);
            String areaNo = university.getRegionCode();
            String provinceNo = areaNo.substring(0, 2) + "0000";
            String cityNo = areaNo.substring(0, 4) + "00";
            String countyNo = areaNo;
            ChinaUtil util = ChinaUtil.getDistrict();
            university.setRegionCode(util.getDistrictNameAll(provinceNo, 1) + " " + util.getDistrictNameAll(cityNo, 2)+" "+util.getDistrictNameAll(countyNo, 3));
            universityList.add(university);
        }
        map.put("list", universityList);
        return "/university/listUniversity";
    }

    @RequestMapping(value = "/saveUniversity", method = RequestMethod.POST)
    @ResponseBody
    public String saveUniversity(University university){
        if (StringUtil.isNullOrEmpty(university.getSchoolName())){
            return toJSONString(ERROR_1);
        }
        if (StringUtil.isNullOrEmpty(university.getRegionCode())){
            return toJSONString(ERROR_2);
        }
        try {
            universitySaveService.save(university);

        }catch (ServiceException e){
            if (e.getMessage().equals(Constants.SAVE_ERROR)){
                return toJSONString(ERROR_3);
            }
            if (e.getMessage().equals(Constants.HAS_EXISTS)){
                return toJSONString(ERROR_4);
            }
        }

        return toJSONString(SUCCESS);
    }

    @RequestMapping("/toUpdateUniversity")
    public String toUpdate(String schoolId, ModelMap map){
        University university = (University) universityFindService.findById(schoolId);
        map.put("school", university);
        return  "/university/updateUniversity";
    }

    @RequestMapping("/updateUniversity")
    @ResponseBody
    public String updateUniversity(University university){
        try {
            universityUpdateService.update(university);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }

    @RequestMapping("/deleteUniversity")
    @ResponseBody
    public String deleteUniversity(String schoolId){
        try {
            deleteUniversityService.delete(schoolId);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }
}
