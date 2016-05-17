package com.liangxunwang.unimanager.service.app;

import com.liangxunwang.unimanager.dao.AppCityDao;
import com.liangxunwang.unimanager.dao.ProvinceDao;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2015/8/17.
 */
@Service("appCityService")
public class AppCityService implements ListService {
    @Autowired
    @Qualifier("appCityDao")
    private AppCityDao appCityDao;

    @Override
    public Object list(Object object) throws ServiceException {
        String provinceid = (String) object;
        return appCityDao.list(provinceid);
    }
}
