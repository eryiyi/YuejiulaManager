package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.MsgAd;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/1/29.
 */
@Repository("msgAdDao")
public interface MsgAdDao {

    /**
     * 查询
     */
    List<MsgAd> lists(Map<String, Object> map);

    //保存
    void save(MsgAd adObj);

    //删除
    void delete(String msg_ad_no);

    /**
     * 根据ID查找
     * @param msg_ad_no
     * @return
     */
    public MsgAd findById(String msg_ad_no);

    /**
     * 更新
     * @param msgAd
     */
    public void update(MsgAd msgAd);
}
