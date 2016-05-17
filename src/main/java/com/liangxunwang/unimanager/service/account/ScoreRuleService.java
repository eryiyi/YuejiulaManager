package com.liangxunwang.unimanager.service.account;

import com.liangxunwang.unimanager.dao.ScoreRuleDao;
import com.liangxunwang.unimanager.model.ScoreRule;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.UUIDFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liuzwei on 2015/2/4.
 */
@Service("scoreRuleService")
public class ScoreRuleService implements SaveService, ListService,UpdateService, DeleteService {

    @Autowired
    @Qualifier("scoreRuleDao")
    private ScoreRuleDao scoreRuleDao;

    @Override
    public Object list(Object object) throws ServiceException {
        List<ScoreRule> list = scoreRuleDao.list();
        return list;
    }

    @Override
    public Object save(Object object) throws ServiceException {
        ScoreRule scoreRule = (ScoreRule) object;
        scoreRule.setId(UUIDFactory.random());
        scoreRuleDao.save(scoreRule);
        return null;
    }

    @Override
    public Object update(Object object) {
        ScoreRule scoreRule = (ScoreRule) object;
        scoreRuleDao.update(scoreRule);
        return null;
    }

    @Override
    public Object delete(Object object) throws ServiceException {
        String ruleId = (String) object;
        scoreRuleDao.delete(ruleId);
        return null;
    }
}
