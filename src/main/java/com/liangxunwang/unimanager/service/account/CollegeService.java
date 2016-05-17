package com.liangxunwang.unimanager.service.account;

import com.liangxunwang.unimanager.dao.CollegeDao;
import com.liangxunwang.unimanager.model.College;
import com.liangxunwang.unimanager.query.CollegeQuery;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuzwei on 2015/2/25.
 */
@Service("collegeService")
public class CollegeService implements ListService ,UpdateService,FindService,SaveService{
    @Autowired
    @Qualifier("collegeDao")
    private CollegeDao collegeDao;
    @Override
    public Object list(Object object) throws ServiceException {
        CollegeQuery query = (CollegeQuery) object;
        String provinceID = query.getProvinceId();
        String keyWords = query.getKeyWords();
        Map<String, Object> map = new HashMap<String, Object>();
        if (!StringUtil.isNullOrEmpty(provinceID)) {
            map.put("provinceId", provinceID);
        }
        if (!StringUtil.isNullOrEmpty(keyWords)) {
            map.put("keyWords", keyWords);
        }
        return collegeDao.list(map);
    }

    @Override
    public Object update(Object object) {
        Object[] params = (Object[]) object;
        String coid = (String) params[0];
        String groupId = (String) params[1];
        collegeDao.updateGroupId(coid, groupId);
        return null;
    }

    @Override
    public Object findById(Object object) throws ServiceException {
        Object[] params = (Object[]) object;
        String coid = (String) params[0];
        College college=collegeDao.getGroupId(coid);
        return college.getGroupId();
    }

    @Override
    public Object save(Object object) throws ServiceException {
        return null;
    }
}
