package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.GoodsComment;
import com.liangxunwang.unimanager.mvc.vo.GoodsCommentVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by liuzwei on 2015/2/5.
 */
@Repository("goodsCommentDao")
public interface GoodsCommentDao {
    public void save(GoodsComment comment);

    public List<GoodsCommentVO> list(Map<String,Object> map);
}
