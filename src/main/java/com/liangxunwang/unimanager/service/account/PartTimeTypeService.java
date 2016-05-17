package com.liangxunwang.unimanager.service.account;

import com.liangxunwang.unimanager.dao.PartTimeTypeDao;
import com.liangxunwang.unimanager.model.PartTimeType;
import com.liangxunwang.unimanager.service.DeleteService;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.SaveService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.Constants;
import com.liangxunwang.unimanager.util.UUIDFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liuzwei on 2015/2/7.
 */
@Service("partTimeTypeService")
public class PartTimeTypeService implements SaveService, ListService, DeleteService {
    @Autowired
    @Qualifier("partTimeTypeDao")
    private PartTimeTypeDao partTimeTypeDao;
    @Override
    public Object list(Object object) throws ServiceException {
        List<PartTimeType> list = partTimeTypeDao.list();
        for (PartTimeType type : list){
            type.setCover(Constants.URL+type.getCover());
        }
        return list;
    }

    @Override
    public Object save(Object object) throws ServiceException {
        PartTimeType type = (PartTimeType) object;
        type.setId(UUIDFactory.random());
        partTimeTypeDao.save(type);
        return null;
    }

    @Override
    public Object delete(Object object) throws ServiceException {
        String typeId = (String) object;

        partTimeTypeDao.delete(typeId);
        return null;
    }
}
