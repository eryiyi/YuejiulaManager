package com.liangxunwang.unimanager.service.app;

import com.liangxunwang.unimanager.dao.AppVideosCommentDao;
import com.liangxunwang.unimanager.model.Comment;
import com.liangxunwang.unimanager.mvc.vo.CommentVO;
import com.liangxunwang.unimanager.query.CommentQuery;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.SaveService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.Constants;
import com.liangxunwang.unimanager.util.RelativeDateFormat;
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
@Service("appCommentService")
public class AppCommentService implements SaveService, ListService {
    @Autowired
    @Qualifier("appVideosCommentDao")
    private AppVideosCommentDao appCommentDao;


    @Override
    public Object save(Object object) throws ServiceException {
        Comment comment = (Comment) object;
        comment.setId(UUIDFactory.random());
        comment.setDateline(System.currentTimeMillis()+"");
        appCommentDao.save(comment);
        return null;
    }

    @Override
    public Object list(Object object) throws ServiceException {
        CommentQuery query = (CommentQuery) object;
        int index = (query.getIndex() - 1) * query.getSize();
        int size = query.getIndex()*query.getSize();
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("index", index);
        map.put("size", size);
        map.put("recordId", query.getRecordId());
        List<CommentVO> list = appCommentDao.list(map);
        for (CommentVO comment:list){
            comment.setDateline(RelativeDateFormat.format(Long.parseLong(comment.getDateline())));
            if (comment.getCover().startsWith("upload")) {
                comment.setCover(Constants.URL + comment.getCover());
            }else {
                comment.setCover(Constants.QINIU_URL + comment.getCover());
            }
        }
        return list;
    }

}
