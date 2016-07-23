package com.liangxunwang.unimanager.service.app;

import com.liangxunwang.unimanager.dao.ManagerInfoDao;
import com.liangxunwang.unimanager.mvc.vo.ManagerInfoVo;
import com.liangxunwang.unimanager.service.FindService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by liuzh on 2015/8/30.
 */
@Service("appManagerInfoService")
public class AppManagerInfoService implements  FindService {

    @Autowired
    @Qualifier("managerInfoDao")
    private ManagerInfoDao managerInfoDao;
    @Override
    public Object findById(Object object) throws ServiceException {
        if (object instanceof String){
            String emp_id = (String) object;
            ManagerInfoVo managerInfoVo =  managerInfoDao.getEmpMsg(emp_id);
            if(managerInfoVo!= null){
                if (managerInfoVo.getEmp_cover().startsWith("upload")) {
                    managerInfoVo.setEmp_cover(Constants.URL + managerInfoVo.getEmp_cover());
                }else {
                    managerInfoVo.setEmp_cover(Constants.QINIU_URL + managerInfoVo.getEmp_cover());
                }
                if (managerInfoVo.getCompany_pic().startsWith("upload")) {
                    managerInfoVo.setCompany_pic(Constants.URL + managerInfoVo.getCompany_pic());
                }else {
                    managerInfoVo.setCompany_pic(Constants.QINIU_URL + managerInfoVo.getCompany_pic());
                }
            }
            return managerInfoVo;
        }
        return null;
    }


}
