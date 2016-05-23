package com.liangxunwang.unimanager.service.account;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.liangxunwang.unimanager.dao.CollegeDao;
import com.liangxunwang.unimanager.dao.SchoolRecordMoodDao;
import com.liangxunwang.unimanager.huanxin.comm.httpclient.apidemo.EasemobChatGroups;
import com.liangxunwang.unimanager.model.College;
import com.liangxunwang.unimanager.model.SchoolRecordMood;
import com.liangxunwang.unimanager.mvc.vo.SchoolVO;
import com.liangxunwang.unimanager.query.CollegeQuery;
import com.liangxunwang.unimanager.query.SchoolRecordMoodQuery;
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
 * Created by zhl on 2015/2/25.
 */
@Service("schoolRecordMoodService")
public class SchoolRecordMoodService implements ListService ,SaveService,DeleteService{
    @Autowired
    @Qualifier("schoolRecordMoodDao")
    private SchoolRecordMoodDao schoolRecordMoodDao;
    @Override
    public Object list(Object object) throws ServiceException {
        SchoolRecordMoodQuery query = (SchoolRecordMoodQuery) object;
        int index = (query.getIndex() - 1) * query.getSize();
        int size =  query.getSize();
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("index", index);
        map.put("size", size);

        List<SchoolRecordMood> list = schoolRecordMoodDao.lists(map);
        long count = schoolRecordMoodDao.count(map);
        return new Object[]{list, count};
    }


    @Override
    public Object save(Object object) throws ServiceException {
        SchoolRecordMood schoolRecordMood = (SchoolRecordMood) object;
        schoolRecordMood.setSchool_record_mood_id(UUIDFactory.random());
        schoolRecordMoodDao.save(schoolRecordMood);
        return null;
    }

    @Override
    public Object delete(Object object) throws ServiceException {
        String school_record_mood_id = (String) object;
        schoolRecordMoodDao.deleteById(school_record_mood_id);
        return null;
    }
}
