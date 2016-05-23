package com.liangxunwang.unimanager.service.member;

import com.liangxunwang.unimanager.dao.MemberDao;
import com.liangxunwang.unimanager.dao.SmsDao;
import com.liangxunwang.unimanager.model.Member;
import com.liangxunwang.unimanager.model.Sms;
import com.liangxunwang.unimanager.service.ExecuteService;
import com.liangxunwang.unimanager.service.SaveService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.Constants;
import com.liangxunwang.unimanager.util.SMSMessage;
import com.liangxunwang.unimanager.util.UUIDFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by zhl on 2015/2/2.
 */
@Service("smsMessageService")
public class SMSMessageService implements SaveService , ExecuteService{

    @Autowired
    @Qualifier("smsDao")
    private SmsDao messageDao;

    @Autowired
    @Qualifier("memberDao")
    private MemberDao memberDao;

    @Override
    public Object save(Object object) throws ServiceException {
        String phoneNumber = (String) object;

        Member member = memberDao.findByMobile(phoneNumber);

        if (member != null){
            throw new ServiceException(Constants.HAS_EXISTS);//已经注册
        }

        Sms sms = messageDao.findByMobile(phoneNumber);
        if (sms != null){
            throw new ServiceException(Constants.HAS_CODE);//已经发过验证码
        }

        SMSMessage smsMessage = new SMSMessage();
        //生成随机六位验证码
        String code = String.valueOf((int)((Math.random()*9+1)*100000));
        //拼接发送信息
        String content = "【良讯网】约酒啦注册验证码："+code+",如非本人操作，请忽略该短信。";
        //发送后的返回信息
        String state = smsMessage.sendMobileMessageByURL(phoneNumber, content);

        if (!state.equals("SUCCESS")){
            throw new ServiceException(Constants.SEND_SMS_ERROR);
        }
        Sms message = new Sms();
        message.setSmsId(UUIDFactory.random());
        message.setSmsMobile(phoneNumber);
        message.setCode(code);
        message.setState(state);
        message.setIsSuccess("1");
        message.setDateline(System.currentTimeMillis()+"");

        messageDao.save(message);

        return null;
    }

    /**
     * 检查验证码是否有效
     * @param object
     * @return
     * @throws ServiceException
     */
    @Override
    public Object execute(Object object) throws ServiceException {
        Object[] params = (Object[]) object;
        String phoneNumber = (String) params[0];
        String code = (String) params[1];

        Sms message = messageDao.findByMobile(phoneNumber);

        if (message == null){
            throw new ServiceException(Constants.NO_SEND_CODE);
        }
        if (!message.getCode().equals(code)){
            throw new ServiceException(Constants.CODE_NOT_EQUAL);//验证码不匹配
        }
        return true;
    }
}
