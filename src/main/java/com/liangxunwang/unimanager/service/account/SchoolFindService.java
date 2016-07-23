package com.liangxunwang.unimanager.service.account;

import com.liangxunwang.unimanager.dao.SchoolFindDao;
import com.liangxunwang.unimanager.model.SchoolFind;
import com.liangxunwang.unimanager.query.VideosQuery;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.Constants;
import com.liangxunwang.unimanager.util.UUIDFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/2/3.
 */
@Service("schoolFindService")
public class SchoolFindService implements SaveService ,ListService, DeleteService,FindService{
    @Autowired
    @Qualifier("schoolFindDao")
    private SchoolFindDao schoolFindDao;

    @Override
    public Object save(Object object) throws ServiceException {
        SchoolFind videos = (SchoolFind) object;
        videos.setSchool_find_id(UUIDFactory.random());
        schoolFindDao.save(videos);
        return null;
    }

    @Override
    public Object list(Object object) throws ServiceException {

        VideosQuery query = (VideosQuery) object;
        int index = (query.getIndex() - 1) * query.getSize();
        int size = query.getIndex()*query.getSize();

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("index", index);
        map.put("size", size);

        List<SchoolFind> list = schoolFindDao.lists(map);
        for (SchoolFind vo : list){
            if (vo.getPic_url().startsWith("upload")) {
                vo.setPic_url(Constants.URL + vo.getPic_url());
            }else {
                vo.setPic_url(Constants.QINIU_URL + vo.getPic_url());
            }
        }
        long count = schoolFindDao.count(map);
        return new Object[]{list, count};
    }

    @Override
    public Object delete(Object object) throws ServiceException {
        String school_find_id = (String) object;
        schoolFindDao.delete(school_find_id);
        return null;
    }

    @Override
    public Object findById(Object object) throws ServiceException {
        String school_find_id = (String) object;
        SchoolFind vo = schoolFindDao.findById(school_find_id);
        if(vo != null){
            if (vo.getPic_url().startsWith("upload")) {
                vo.setPic_url(Constants.URL + vo.getPic_url());
            }else {
                vo.setPic_url(Constants.QINIU_URL + vo.getPic_url());
            }
        }
        return vo;
    }
}
