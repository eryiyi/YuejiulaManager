package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.Notice;
import com.liangxunwang.unimanager.mvc.vo.NoticeVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by liuzwei on 2015/2/5.
 */
@Repository("noticeDao")
public interface NoticeDao {

    public void save(Notice notice);

    public List<Notice> list(Map<String,Object> map);

    public Notice findById(String noticeId);

    public void update(Notice notice);

    public void delete(String noticeId);
}
