package com.liangxunwang.unimanager.service.account;

import com.liangxunwang.unimanager.dao.CollegeDao;
import com.liangxunwang.unimanager.model.College;
import com.liangxunwang.unimanager.mvc.vo.SchoolVO;
import com.liangxunwang.unimanager.query.CollegeQuery;
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
@Service("schoolsAllService")
public class SchoolsAllService implements ListService{
    @Autowired
    @Qualifier("collegeDao")
    private CollegeDao collegeDao;
    @Override
    public Object list(Object object) throws ServiceException {
        Map<String,Object> map = new HashMap<String, Object>();
        List<College> list = collegeDao.list(map);
        return list;
    }

}
