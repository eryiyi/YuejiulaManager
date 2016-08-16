package com.liangxunwang.unimanager.service.account;

import com.liangxunwang.unimanager.dao.BigAreaDao;
import com.liangxunwang.unimanager.model.BigAreaObj;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.Constants;
import com.liangxunwang.unimanager.util.StringUtil;
import com.liangxunwang.unimanager.util.UUIDFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/3/3.
 */
@Service("bigAreaObjService")
public class BigAreaObjService implements ListService,SaveService,ExecuteService, UpdateService {
    @Autowired
    @Qualifier("bigAreaDao")
    private BigAreaDao bigAreaDao;

    @Override
    public Object list(Object object) throws ServiceException {
        Map<String, Object> map = new HashMap<String, Object>();
        List<BigAreaObj> lists = bigAreaDao.lists(map);
        if(lists != null){
            for(BigAreaObj adObj:lists){
                if(!StringUtil.isNullOrEmpty(adObj.getArea_pic())){
                    if(adObj.getArea_pic().startsWith("upload")){
                        adObj.setArea_pic(Constants.URL + adObj.getArea_pic());
                    }else {
                        adObj.setArea_pic(Constants.QINIU_URL + adObj.getArea_pic());
                    }
                }
            }
        }
        return lists;
    }

    @Override
    public Object save(Object object) throws ServiceException {
        BigAreaObj bigAreaObj = (BigAreaObj) object;
        bigAreaObj.setArea_id(UUIDFactory.random());
        bigAreaDao.save(bigAreaObj);
        return null;
    }

    @Override
    public Object execute(Object object) throws ServiceException {
        return bigAreaDao.findById((String) object);
    }

    @Override
    public Object update(Object object) {
        BigAreaObj bigAreaObj = (BigAreaObj) object;
        bigAreaDao.update(bigAreaObj);
        return null;
    }


}
