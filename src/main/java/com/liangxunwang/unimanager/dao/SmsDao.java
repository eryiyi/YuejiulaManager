package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.Sms;
import org.springframework.stereotype.Repository;

/**
 * Created by zhl on 2015/1/29.
 */
@Repository("smsDao")
public interface SmsDao {
    public Sms findByMobile(String phoneNumber);

    public void save(Sms message);

    /**
     * 根据手机号修改数据库中验证码状态
     * @param phoneNumber
     */
    public void update(String phoneNumber);
}
