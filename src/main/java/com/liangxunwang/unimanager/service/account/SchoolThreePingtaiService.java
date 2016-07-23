package com.liangxunwang.unimanager.service.account;

import com.liangxunwang.unimanager.dao.SchoolThreeTingtaiDao;
import com.liangxunwang.unimanager.model.SchoolThreeTingtai;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.UUIDFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhl on 2015/2/5.
 */
@Service("schoolThreePingtaiService")
public class SchoolThreePingtaiService implements SaveService,ListService, FindService, UpdateService, DeleteService{
    @Autowired
    @Qualifier("schoolThreeTingtaiDao")
    private SchoolThreeTingtaiDao schoolThreeTingtaiDao;

    @Override
    public Object list(Object object) throws ServiceException {
        Map<String,Object> map = new HashMap<String, Object>();
        return schoolThreeTingtaiDao.list(map);
    }

    @Override
    public Object save(Object object) throws ServiceException {
        SchoolThreeTingtai notice = (SchoolThreeTingtai) object;
        notice.setSchool_three_pingtai_id(UUIDFactory.random());
        schoolThreeTingtaiDao.save(notice);
        return null;
    }

    @Override
    public Object findById(Object object) throws ServiceException {
        String school_three_pingtai_id = (String) object;
        return schoolThreeTingtaiDao.findById(school_three_pingtai_id);
    }

    @Override
    public Object update(Object object) {
        schoolThreeTingtaiDao.update((SchoolThreeTingtai) object);
        return null;
    }

    @Override
    public Object delete(Object object) throws ServiceException {
        String school_three_pingtai_id = (String) object;
        schoolThreeTingtaiDao.delete(school_three_pingtai_id);
        return null;
    }

}
