package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.PKComment;
import com.liangxunwang.unimanager.mvc.vo.PkCommentVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by liuzwei on 15-4-4.
 */
@Repository("pkCommentDao")
public interface PkCommentDao {
    /**
     * 保存作品评论
     * @param comment
     */
    void saveComment(PKComment comment);


    /**
     * 根据动态ID查找作品评论
     * @param map
     * @return
     */
    List<PkCommentVO> list(Map<String,Object> map);
}
