package com.liangxunwang.unimanager.mvc.admin;

import com.liangxunwang.unimanager.model.*;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.mvc.vo.ContractSchoolVO;
import com.liangxunwang.unimanager.mvc.vo.MemberVO;
import com.liangxunwang.unimanager.query.CollegeQuery;
import com.liangxunwang.unimanager.query.ContractQuery;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.Constants;
import com.liangxunwang.unimanager.util.ControllerConstants;
import com.liangxunwang.unimanager.util.DateUtil;
import com.liangxunwang.unimanager.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by liuzwei on 2015/3/24.
 * 承包商主要接口
 */
@Controller("contractSchoolController")
public class ContractSchoolController extends ControllerConstants{

    @Autowired
    @Qualifier("contractSchoolService")
    private ListService contractSchoolListService;

    @Autowired
    @Qualifier("contractSchoolService")
    private UpdateService contractSchoolUpdateService;

    @Autowired
    @Qualifier("contractSchoolService")
    private DeleteService contractSchoolDeleteService;

    @Autowired
    @Qualifier("contractSchoolService")
    private SaveService contractSchoolSaveService;

    @Autowired
    @Qualifier("provinceService")
    private ListService provinceListService;

    @Autowired
    @Qualifier("collegeService")
    private ListService collegeListService;

    @Autowired
    @Qualifier("memberInfoService")
    private FindService findMemberService;

    @Autowired
    @Qualifier("contractSchoolService")
    private ExecuteService executeContractSchoolService;

    @Autowired
    @Qualifier("roleService")
    private ListService roleListService;

    /**
     * 跳转到承包商添加页面
     * @param empId
     * @param map
     * @return
     */
    @RequestMapping("/toAddContractSchool")
    public String toAddContractSchool(String empId, ModelMap map){
        List<Province> list = (List<Province>) provinceListService.list(null);
        map.put("provinces", list);

        List<College> colleges = (List<College>) collegeListService.list(new CollegeQuery());
        map.put("colleges", toJSONString(colleges));

        MemberVO memberVO = (MemberVO) findMemberService.findById(empId);
        map.put("memberVo", memberVO);

        List<ContractSchoolVO> contractSchoolVOs = (List<ContractSchoolVO>) contractSchoolListService.list(empId);
        map.put("list", contractSchoolVOs);

        return "/member/addContractSchool";
    }

    @RequestMapping("/toSelectPermission")
    public String toSelectPermission(String empId, ModelMap map){
        List<Role> roles = (List<Role>) roleListService.list(null);
        map.put("roles", roles);
        map.put("empId", empId);
        return "/contract/permission";
    }

    /**
     * 查找会员下面开通了几个学校
     * @param empId
     * @return
     */
    @RequestMapping(value = "/findContractSchoolById", produces = "text/plain;charset=UTF-8;")
    @ResponseBody
    public String findContractSchool(String empId){
        try {
            List<ContractSchoolVO> contractSchoolVOs = (List<ContractSchoolVO>) contractSchoolListService.list(empId);
            for (ContractSchoolVO vo : contractSchoolVOs){
                vo.setEndTime(DateUtil.getDate(vo.getEndTime(), "yyyy-MM-dd"));
            }
            DataTip tip = new DataTip();
            tip.setData(contractSchoolVOs);
            return toJSONString(tip);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }

    /**
     * 根据当前登录承包商查询开通的学校
     * @param session
     * @param map
     * @return
     */
    @RequestMapping("/listSchool")
    public String listSchool(HttpSession session, ModelMap map){
        Admin admin = (Admin) session.getAttribute(ACCOUNT_KEY);
        List<ContractSchoolVO> contractSchoolVOs = (List<ContractSchoolVO>) contractSchoolListService.list(admin.getEmpId());
        map.put("schools", contractSchoolVOs);
        return "/contractor/schools";
    }

    /**
     * 给承包商添加学校
     * @param contractSchool
     * @return
     */
    @RequestMapping("/addContractSchool")
    @ResponseBody
    public String addContractSchool(ContractSchool contractSchool){
        try {
            contractSchoolSaveService.save(contractSchool);
        }catch (ServiceException e){
            String msg = e.getMessage();
            if (msg.equals("HAS_CONTRACT")){
                return toJSONString(ERROR_2);
            }else {
                return toJSONString(ERROR_1);
            }
        }
        return toJSONString(SUCCESS);
    }

    /**
     * 根据ID删除承包商下的学校
     * 要删除该承包商下学校的所有商家
     *      判断该商家是否是别的学校的商家，如果不是设置为普通会员
     * 删除该商家下的所有商品
     * 删除该商家下的所有兼职
     * 删除该承包商下的所有商品
     * 删除该承包商下的所有兼职
     * @param id  承包学校ID
     * @return
     */
    @RequestMapping("/deleteContractSchool")
    @ResponseBody
    public String deleteContractSchool(String id){
        try {
            contractSchoolDeleteService.delete(id);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }

    /**
     * 将用户设置为承包商
     * @param empId  用户的会员ID
     * @return
     */
    @RequestMapping("/setContractUser")
    @ResponseBody
    public String setContractUser(String empId, String empTypeId){
        Object[] params = new Object[]{empId, empTypeId};
        try {
            contractSchoolUpdateService.update(params);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }

    /**
     * 分页查询所有的承包商
     * @param query
     * @param page
     * @return
     */
    @RequestMapping("/listContracts")
    public String getAllContract(ContractQuery query, Page page, ModelMap map){
        query.setIndex(page.getPage()==0?1:page.getPage());
        query.setSize(query.getSize() == 0 ? page.getDefaultSize() : query.getSize());
        Object[] results = (Object[]) contractSchoolListService.list(query);
        List<ContractSchoolVO> list = (List<ContractSchoolVO>) results[0];
        long count = (Long) results[1];
        page.setCount(count);
        page.setPageCount(calculatePageCount(query.getSize(), count));
        map.addAttribute("page", page);
        map.addAttribute("query", query);
        map.put("list", list);
        return "/contract/list";
    }

    /**
     * 定时查找所有承包商过期的学校
     * @return
     */
    public String update(){
        executeContractSchoolService.execute(null);
        return null;
    }

    /**
     *去开通商家
     * @param map
     * @param session
     * @return
     */
    @RequestMapping("/toSetSeller")
    public String toSetSeller(ModelMap map, HttpSession session){
        Admin admin = (Admin) session.getAttribute(ACCOUNT_KEY);
        List<ContractSchoolVO> contractSchoolVOs = (List<ContractSchoolVO>) contractSchoolListService.list(admin.getEmpId());
        map.put("schools", contractSchoolVOs);
        return "/contractor/setseller";
    }


}
