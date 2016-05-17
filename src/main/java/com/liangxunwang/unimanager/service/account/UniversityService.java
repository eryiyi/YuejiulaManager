package com.liangxunwang.unimanager.service.account;

import com.liangxunwang.unimanager.dao.UniversityDao;
import com.liangxunwang.unimanager.model.University;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.Constants;
import com.liangxunwang.unimanager.util.UUIDFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liuzwei on 2015/1/30.
 */
@Service("universityService")
public class UniversityService implements SaveService, ListService, FindService, UpdateService, DeleteService{
    @Autowired
    @Qualifier("universityDao")
    private UniversityDao dao;

    @Override
    public Object save(Object object) {
        University university = (University) object;

        University universityBack = dao.findByName(university.getSchoolName());

        if (universityBack != null){
            throw new ServiceException(Constants.HAS_EXISTS);
        }

        university.setSchoolId(UUIDFactory.random());
        university.setDateline(System.currentTimeMillis()+"");
        try {
            dao.save(university);
        }catch (Exception e){
            throw new ServiceException(Constants.SAVE_ERROR);
        }
        return university;
    }

    /**
     * 查找所有的学校
     * @param object
     * @return
     */
    @Override
    public List list(Object object) {

        if (object instanceof String) {
            if (object.equals("all")){
                return dao.listAll();
            }
            return dao.listNearby((String)object);
        } else{
            List<University> list = dao.list();
            return list;
        }
    }

    @Override
    public Object findById(Object object) throws ServiceException {
        String schoolId = (String) object;

        return dao.findById(schoolId);
    }

    @Override
    public Object update(Object object) {
        University university = (University) object;
        university.setDateline(System.currentTimeMillis()+"");
        dao.update(university);
        return null;
    }

    @Override
    public Object delete(Object object) throws ServiceException {
        dao.deleteById((String) object);
        return null;
    }
}
