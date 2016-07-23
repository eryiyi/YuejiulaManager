package com.liangxunwang.unimanager.service.member;

import com.liangxunwang.unimanager.dao.SchoolFindDao;
import com.liangxunwang.unimanager.model.SchoolFind;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/2/3.
 */
@Service("appSchoolFindService")
public class AppSchoolFindService implements ListService{
    @Autowired
    @Qualifier("schoolFindDao")
    private SchoolFindDao schoolFindDao;

    @Override
    public Object list(Object object) throws ServiceException {

        Map<String,Object> map = new HashMap<String, Object>();

        List<SchoolFind> list = schoolFindDao.listsApp(map);
        for (SchoolFind vo : list){
            if (vo.getPic_url().startsWith("upload")) {
                vo.setPic_url(Constants.URL + vo.getPic_url());
            }else {
                vo.setPic_url(Constants.QINIU_URL + vo.getPic_url());
            }
        }
        return list;
    }

}
