package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.NewsType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liuzwei on 2015/3/1.
 */
@Repository("newsTypeDao")
public interface NewsTypeDao {

    void save(NewsType type);

    List<NewsType> list();

    void update(NewsType type);

    void delete(String id);

}
