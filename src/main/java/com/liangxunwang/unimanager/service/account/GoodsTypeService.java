package com.liangxunwang.unimanager.service.account;

import com.liangxunwang.unimanager.dao.GoodsTypeDao;
import com.liangxunwang.unimanager.model.GoodsType;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.UUIDFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuzwei on 2015/2/2.
 */
@Service("goodsTypeService")
public class GoodsTypeService implements SaveService, ListService, FindService , UpdateService, DeleteService{

    @Autowired
    @Qualifier("goodsTypeDao")
    private GoodsTypeDao goodsTypeDao;

    @Override
    public Object save(Object object) throws ServiceException {
        GoodsType type = (GoodsType) object;
        type.setTypeId(UUIDFactory.random());
        goodsTypeDao.save(type);
        return null;
    }

    @Override
    public Object list(Object object) throws ServiceException {
        String isUse = (String) object;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("isUse", isUse);
        return goodsTypeDao.list(map);
    }

    @Override
    public Object findById(Object object) throws ServiceException {
        return goodsTypeDao.findById((String) object);
    }

    @Override
    public Object update(Object object) {
        GoodsType type = (GoodsType) object;
        goodsTypeDao.update(type);
        return null;
    }

    @Override
    public Object delete(Object object) throws ServiceException {
        goodsTypeDao.deleteById((String) object);
        return null;
    }
}
