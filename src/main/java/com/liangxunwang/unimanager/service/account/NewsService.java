package com.liangxunwang.unimanager.service.account;

import com.liangxunwang.unimanager.dao.NewsDao;
import com.liangxunwang.unimanager.model.News;
import com.liangxunwang.unimanager.mvc.vo.NewsVO;
import com.liangxunwang.unimanager.query.NewsQuery;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.Constants;
import com.liangxunwang.unimanager.util.RelativeDateFormat;
import com.liangxunwang.unimanager.util.StringUtil;
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
@Service("newsService")
public class NewsService implements ListService, SaveService, FindService , DeleteService{
    @Autowired
    @Qualifier("newsDao")
    private NewsDao newsDao;
    @Override
    public Object list(Object object) throws ServiceException {
        NewsQuery query = (NewsQuery) object;
        int index = (query.getIndex() - 1) * query.getSize();
        int size = query.getIndex()*query.getSize();

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("index", index);
        map.put("size", size);
        if (!StringUtil.isNullOrEmpty(query.getIsUse())) {
            map.put("isUse", query.getIsUse());
        }
        if (!StringUtil.isNullOrEmpty(query.getTypeId())){
            map.put("typeId", query.getTypeId());
        }
        List<NewsVO> list = newsDao.list(map);
        for (NewsVO vo : list){
            vo.setDateLine(RelativeDateFormat.format(Long.parseLong(vo.getDateLine())));
        }
        long count = newsDao.count(map);
        return new Object[]{list, count};
    }
    @Override
    public Object save(Object object) throws ServiceException {
        News news = (News) object;
        if (news.getPublishType().equals("1")){
            String[] pics = news.getDateLine().split(",");
            StringBuffer buffer = new StringBuffer();
            for (int i=0; i<pics.length; i++){
                buffer.append("<img src='").append(Constants.URL+pics[i]).append("'/>");
            }
            news.setContent("<p>"+buffer.toString()+news.getContent()+"</p>");
        }
        news.setDateLine(System.currentTimeMillis()+"");
        news.setIsUse("0");
        news.setId(UUIDFactory.random());
        newsDao.save(news);
        return null;
    }

    @Override
    public Object findById(Object object) throws ServiceException {
        Object[] params = (Object[]) object;
        String newsId = (String) params[0];
        String publishType = (String) params[1];
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("newsId", newsId);
        map.put("publishType", publishType);
        if (publishType !=null && "0".equals(publishType)) {
           return newsDao.findByIdType0(map);
        }
        return newsDao.findByIdType1(map);
    }

    @Override
    public Object delete(Object object) throws ServiceException {
        String newsId = (String) object;
        newsDao.delete(newsId);
        return null;
    }
}
