package com.liangxunwang.unimanager.service.account;

import com.liangxunwang.unimanager.dao.SchoolThreeTingtaiBdDao;
import com.liangxunwang.unimanager.dao.SchoolThreeTingtaiDao;
import com.liangxunwang.unimanager.model.SchoolThreeTingtai;
import com.liangxunwang.unimanager.model.SchoolThreeTingtaiBd;
import com.liangxunwang.unimanager.mvc.vo.SchoolThreePtBdVO;
import com.liangxunwang.unimanager.query.SchoolThreePtBdQuery;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.StringUtil;
import com.liangxunwang.unimanager.util.UUIDFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/2/5.
 */
@Service("schoolThreePingtaiBdService")
public class SchoolThreePingtaiBdService implements SaveService,ListService, FindService, UpdateService, DeleteService{
    @Autowired
    @Qualifier("schoolThreeTingtaiBdDao")
    private SchoolThreeTingtaiBdDao schoolThreeTingtaiBdDao;

    @Override
    public Object list(Object object) throws ServiceException {
        SchoolThreePtBdQuery query = (SchoolThreePtBdQuery) object;
        Map<String,Object> map = new HashMap<String, Object>();
        if(!StringUtil.isNullOrEmpty(query.getEmp_id())){
            map.put( "emp_id" , query.getEmp_id());
        }
        if(!StringUtil.isNullOrEmpty(query.getSchool_three_pingtai_id())){
            map.put( "school_three_pingtai_id" , query.getSchool_three_pingtai_id());
        }
        return schoolThreeTingtaiBdDao.list(map);
    }

    @Override
    public Object save(Object object) throws ServiceException {
        SchoolThreeTingtaiBd notice = (SchoolThreeTingtaiBd) object;
        //先判断是否已经绑定了   根据用户id和平台id查询
        Map<String,Object> map = new HashMap<String, Object>();
        if(!StringUtil.isNullOrEmpty(notice.getEmp_id())){
            map.put( "emp_id" , notice.getEmp_id());
        }
        if(!StringUtil.isNullOrEmpty(notice.getSchool_three_pingtai_id())){
            map.put( "school_three_pingtai_id" , notice.getSchool_three_pingtai_id());
        }
        List<SchoolThreePtBdVO> list = schoolThreeTingtaiBdDao.list(map);
        if(list != null && list.size() > 0){
            throw new ServiceException("hasExist");
        }
        notice.setEmp_pingtai_mng_id(UUIDFactory.random());
        schoolThreeTingtaiBdDao.save(notice);
        return null;
    }

    @Override
    public Object findById(Object object) throws ServiceException {
        String emp_pingtai_mng_id = (String) object;
        return schoolThreeTingtaiBdDao.findById(emp_pingtai_mng_id);
    }

    @Override
    public Object update(Object object) {
        schoolThreeTingtaiBdDao.update((SchoolThreeTingtaiBd) object);
        return null;
    }

    @Override
    public Object delete(Object object) throws ServiceException {
        String emp_pingtai_mng_id = (String) object;
        schoolThreeTingtaiBdDao.delete(emp_pingtai_mng_id);
        return null;
    }

}
