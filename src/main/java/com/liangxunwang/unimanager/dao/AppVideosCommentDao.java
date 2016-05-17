package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.Comment;
import com.liangxunwang.unimanager.mvc.vo.CommentVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 */
@Repository("appVideosCommentDao")
public interface AppVideosCommentDao {
    /**
     * 保存一条评论
     * @param comment
     */
    public void save(Comment comment);

    /**
     * 根据动态ID查找评论
     * @param map
     * @return
     */
    public List<CommentVO> list(Map<String, Object> map);
}
