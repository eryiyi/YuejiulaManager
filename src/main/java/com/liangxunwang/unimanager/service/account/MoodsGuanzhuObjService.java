package com.liangxunwang.unimanager.service.account;

import com.liangxunwang.unimanager.dao.AdDao;
import com.liangxunwang.unimanager.dao.MoodGuanzhuObjDao;
import com.liangxunwang.unimanager.model.AdObj;
import com.liangxunwang.unimanager.model.MoodGuanzhuObj;
import com.liangxunwang.unimanager.mvc.vo.MoodsGuanzhuVO;
import com.liangxunwang.unimanager.query.AdQuery;
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
@Service("moodsGuanzhuObjService")
public class MoodsGuanzhuObjService implements ListService,SaveService,DeleteService,ExecuteService {
    @Autowired
    @Qualifier("moodGuanzhuObjDao")
    private MoodGuanzhuObjDao moodGuanzhuObjDao;

    @Override
    public Object list(Object object) throws ServiceException {
     String emp_id = (String) object;
        Map<String, Object> map = new HashMap<String, Object>();
        if(!StringUtil.isNullOrEmpty(emp_id)){
            map.put("emp_id", emp_id);
        }
        List<MoodsGuanzhuVO> lists = moodGuanzhuObjDao.lists(map);

        return lists;
    }

    @Override
    public Object save(Object object) throws ServiceException {
        MoodGuanzhuObj moodGuanzhuObj = (MoodGuanzhuObj) object;
        //先查询有多少个
        Map<String, Object> map = new HashMap<String, Object>();
        if(!StringUtil.isNullOrEmpty(moodGuanzhuObj.getEmp_id())){
            map.put("emp_id", moodGuanzhuObj.getEmp_id());
        }
        List<MoodsGuanzhuVO> lists = moodGuanzhuObjDao.lists(map);
        if(lists != null && lists.size() > 4){
            throw new ServiceException("adIsTooMuch");
        }
        boolean flag = true;
       for(MoodGuanzhuObj moodGuanzhuObj1:lists){
           if(moodGuanzhuObj1.getSchool_record_mood_id().equals(moodGuanzhuObj.getSchool_record_mood_id()) && moodGuanzhuObj1.getEmp_id().equals(moodGuanzhuObj.getEmp_id())){
               //说明添加了
               flag = false;
               break;
           }
       }
        if(!flag){
            //
            throw new ServiceException("has_add");
        }
        moodGuanzhuObj.setId(UUIDFactory.random());
        moodGuanzhuObjDao.save(moodGuanzhuObj);
        return null;
    }

    @Override
    public Object delete(Object object) throws ServiceException {
        String id = (String) object;
        moodGuanzhuObjDao.delete(id);
        return null;
    }

    @Override
    public Object execute(Object object) throws ServiceException {
        return moodGuanzhuObjDao.findById((String) object);
    }

}
