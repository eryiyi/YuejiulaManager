package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.News;
import com.liangxunwang.unimanager.mvc.vo.NewsVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by liuzwei on 2015/2/3.
 */
@Repository("newsDao")
public interface NewsDao {

    /**
     * 查询新闻
     * @param map
     * @return
     */
    public List<NewsVO> list(Map<String, Object> map);

    public long count(Map<String, Object> map);

    public void save(News news);

    /**
     * 根据ID获得新闻的详细信息
     * @param map
     * @return
     */
    public NewsVO findById(Map<String, Object> map);
    public NewsVO findByIdType0(Map<String, Object> map);
    public NewsVO findByIdType1(Map<String, Object> map);

    /**
     * 根据新闻ID删除新闻
     * @param newsId
     */
    public void delete(String newsId);
}
