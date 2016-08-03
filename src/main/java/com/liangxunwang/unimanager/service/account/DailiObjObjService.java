package com.liangxunwang.unimanager.service.account;

import com.liangxunwang.unimanager.dao.DailiObjDao;
import com.liangxunwang.unimanager.model.DailiObj;
import com.liangxunwang.unimanager.mvc.vo.DailiObjVO;
import com.liangxunwang.unimanager.query.DailiObjQuery;
import com.liangxunwang.unimanager.service.DeleteService;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.SaveService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.Constants;
import com.liangxunwang.unimanager.util.StringUtil;
import com.liangxunwang.unimanager.util.UUIDFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("dailiObjObjService")
public class DailiObjObjService implements ListService,SaveService,DeleteService {
    @Autowired
    @Qualifier("dailiObjDao")
    private DailiObjDao dailiObjDao;

    @Override
    public Object list(Object object) throws ServiceException {
        DailiObjQuery query = (DailiObjQuery) object;
        Map<String, Object> map = new HashMap<String, Object>();
        if(!StringUtil.isNullOrEmpty(query.getEmp_id())){
            map.put("emp_id", query.getEmp_id());
        }
        if(!StringUtil.isNullOrEmpty(query.getEmp_id_d())){
            map.put("emp_id_d", query.getEmp_id_d());
        }
        List<DailiObjVO> lists = dailiObjDao.list(map);

        if(lists != null){
            for(DailiObjVO adObj:lists){
                if(!StringUtil.isNullOrEmpty(adObj.getEmp_cover())){
                    if(adObj.getEmp_cover().startsWith("upload")){
                        adObj.setEmp_cover(Constants.URL + adObj.getEmp_cover());
                    }else {
                        adObj.setEmp_cover(Constants.QINIU_URL + adObj.getEmp_cover());
                    }
                }
            }
        }
        return lists;
    }

    @Override
    public Object save(Object object) throws ServiceException {
        DailiObj adObj = (DailiObj) object;
        Map<String, Object> map = new HashMap<String, Object>();
        if(!StringUtil.isNullOrEmpty(adObj.getEmp_id())){
            map.put("emp_id", adObj.getEmp_id());
        }
        if(!StringUtil.isNullOrEmpty(adObj.getEmp_id_d())){
            map.put("emp_id_d", adObj.getEmp_id_d());
        }
        List<DailiObjVO> lists = dailiObjDao.list(map);
        if(lists != null && lists.size() > 5){
            throw new ServiceException("adIsTooMuch");
        }
        adObj.setDaili_id(UUIDFactory.random());
        dailiObjDao.save(adObj);
        return null;
    }


    @Override
    public Object delete(Object object) throws ServiceException {
        DailiObj adObj = (DailiObj) object;
        dailiObjDao.deleteById(adObj);
        return null;
    }

}
