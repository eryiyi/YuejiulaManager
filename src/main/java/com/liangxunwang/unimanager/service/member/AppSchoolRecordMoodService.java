package com.liangxunwang.unimanager.service.member;

import com.liangxunwang.unimanager.dao.SchoolRecordMoodDao;
import com.liangxunwang.unimanager.model.SchoolRecordMood;
import com.liangxunwang.unimanager.query.SchoolRecordMoodQuery;
import com.liangxunwang.unimanager.service.DeleteService;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.SaveService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.UUIDFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/2/25.
 */
@Service("appSchoolRecordMoodService")
public class AppSchoolRecordMoodService implements ListService{
    @Autowired
    @Qualifier("schoolRecordMoodDao")
    private SchoolRecordMoodDao schoolRecordMoodDao;
    @Override
    public Object list(Object object) throws ServiceException {
        Map<String,Object> map = new HashMap<String, Object>();
        List<SchoolRecordMood> list = schoolRecordMoodDao.list(map);
        return list;
    }
}
