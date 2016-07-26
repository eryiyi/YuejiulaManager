package com.liangxunwang.unimanager.service.member;

import com.liangxunwang.unimanager.dao.FqfhObjDao;
import com.liangxunwang.unimanager.dao.MemberDao;
import com.liangxunwang.unimanager.model.FhFqObj;
import com.liangxunwang.unimanager.mvc.vo.FhFqObjVO;
import com.liangxunwang.unimanager.service.UpdateService;
import com.liangxunwang.unimanager.util.UUIDFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/2/8.
 */
@Service("memberUpdateFenghaoService")
public class MemberUpdateFenghaoService implements   UpdateService {
    @Autowired
    @Qualifier("memberDao")
    private MemberDao memberDao ;
    @Autowired
    @Qualifier("fqfhObjDao")
    private FqfhObjDao fqfhObjDao ;

    @Override
    public Object update(Object object) {
        FhFqObj fhFqObj = (FhFqObj) object;
        if("0".equals(fhFqObj.getIstype())){
            //封号操作
            if("0".equals(fhFqObj.getIs_fenghao())){
                //解封号
                fqfhObjDao.deleteByEmpid(fhFqObj);
            }else if("1".equals(fhFqObj.getIs_fenghao())){
                //封号
                //先判断 是否已经封号了
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("emp_id", fhFqObj.getEmp_id());
                map.put("emp_id_m", fhFqObj.getEmp_id_m());
                map.put("istype", fhFqObj.getIstype());
                List<FhFqObjVO>  list1 = fqfhObjDao.lists(map);
                if(list1 != null && list1.size()>0){
                    //说明已经封号了
                    return null;
                }else {
                    //进行封号操作
                    memberDao.updateFenghao("1", fhFqObj.getEmp_id());//先操作会员表
                    fhFqObj.setFhfq_id(UUIDFactory.random());
                    fqfhObjDao.save(fhFqObj);//封号
                }
            }
        }

        if("1".equals(fhFqObj.getIstype())){
            //封群操作
            if("0".equals(fhFqObj.getIs_fengqun())){
                //解封群
                fqfhObjDao.deleteByEmpid(fhFqObj);
            }else if("1".equals(fhFqObj.getIs_fengqun())){
                //封号
                //先判断 是否已经封群了
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("emp_id", fhFqObj.getEmp_id());
                map.put("emp_id_m", fhFqObj.getEmp_id_m());
                map.put("istype", fhFqObj.getIstype());
                List<FhFqObjVO>  list1 = fqfhObjDao.lists(map);
                if(list1 != null && list1.size()>0){
                    //说明已经封群了
                    return null;
                }else {
                    //进行封群操作
                    memberDao.updateFengQun("1", fhFqObj.getEmp_id());//先操作会员表
                    fhFqObj.setFhfq_id(UUIDFactory.random());
                    fqfhObjDao.save(fhFqObj);//封群
                }
            }
        }

        return null;
    }


}
