package com.liangxunwang.unimanager.service.account;

import com.liangxunwang.unimanager.dao.NewsTypeDao;
import com.liangxunwang.unimanager.model.NewsType;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.UUIDFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by liuzwei on 2015/3/1.
 */
@Service("newsTypeService")
public class NewsTypeService implements SaveService, ListService, UpdateService , DeleteService{

    @Autowired
    @Qualifier("newsTypeDao")
    private NewsTypeDao newsTypeDao;

    @Override
    public Object list(Object object) throws ServiceException {
        return newsTypeDao.list();
    }

    @Override
    public Object save(Object object) throws ServiceException {
        NewsType type = (NewsType) object;
        type.setId(UUIDFactory.random());
        type.setDateline(System.currentTimeMillis()+"");
        newsTypeDao.save(type);
        return type;
    }

    @Override
    public Object update(Object object) {
        newsTypeDao.update((NewsType) object);
        return null;
    }

    @Override
    public Object delete(Object object) throws ServiceException {
        String id = (String) object;
        newsTypeDao.delete(id);
        return null;
    }
}
