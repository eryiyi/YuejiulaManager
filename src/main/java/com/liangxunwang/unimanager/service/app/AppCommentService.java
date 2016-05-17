package com.liangxunwang.unimanager.service.app;

import com.liangxunwang.unimanager.baidu.channel.auth.ChannelKeyPair;
import com.liangxunwang.unimanager.baidu.channel.client.BaiduChannelClient;
import com.liangxunwang.unimanager.baidu.channel.exception.ChannelClientException;
import com.liangxunwang.unimanager.baidu.channel.exception.ChannelServerException;
import com.liangxunwang.unimanager.baidu.channel.model.PushUnicastMessageRequest;
import com.liangxunwang.unimanager.baidu.channel.model.PushUnicastMessageResponse;
import com.liangxunwang.unimanager.baidu.log.YunLogEvent;
import com.liangxunwang.unimanager.baidu.log.YunLogHandler;
import com.liangxunwang.unimanager.dao.AppVideosCommentDao;
import com.liangxunwang.unimanager.dao.CommentDao;
import com.liangxunwang.unimanager.dao.MemberDao;
import com.liangxunwang.unimanager.dao.RelateDao;
import com.liangxunwang.unimanager.model.Comment;
import com.liangxunwang.unimanager.model.Member;
import com.liangxunwang.unimanager.model.Relate;
import com.liangxunwang.unimanager.mvc.vo.CommentVO;
import com.liangxunwang.unimanager.mvc.vo.MemberVO;
import com.liangxunwang.unimanager.query.CommentQuery;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.SaveService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuzwei on 2015/2/3.
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
        int index = ((query.getIndex()-1)*query.getSize())+1;
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
