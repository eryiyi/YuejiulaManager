package com.liangxunwang.unimanager.service.account;

import com.liangxunwang.unimanager.dao.LevelDao;
import com.liangxunwang.unimanager.model.Level;
import com.liangxunwang.unimanager.service.DeleteService;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.SaveService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.Constants;
import com.liangxunwang.unimanager.util.UUIDFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by liuzwei on 2015/2/1.
 */
@Service("levelService")
public class LevelService implements SaveService, ListService , DeleteService{

    @Autowired
    @Qualifier("levelDao")
    private LevelDao levelDao;
    @Override
    public Object save(Object object) throws ServiceException {
        Level level = (Level) object;
        level.setLevelId(UUIDFactory.random());
        levelDao.save(level);
        return null;
    }

    @Override
    public Object list(Object object) throws ServiceException {

        return levelDao.list();
    }

    @Override
    public Object delete(Object object) throws ServiceException {
        String levelId = (String) object;
        try {
            levelDao.delete(levelId);

        }catch (ServiceException e){
            throw new ServiceException(Constants.SAVE_ERROR);
        }
        return null;
    }
}
