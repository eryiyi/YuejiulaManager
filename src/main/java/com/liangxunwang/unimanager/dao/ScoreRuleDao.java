package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.ScoreRule;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liuzwei on 2015/2/4.
 */
@Repository("scoreRuleDao")
public interface ScoreRuleDao {
    public void save(ScoreRule scoreRule);

    public List<ScoreRule> list();

    public void update(ScoreRule scoreRule);

    public void delete(String ruleId);
}
