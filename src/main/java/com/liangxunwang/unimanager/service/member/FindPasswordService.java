package com.liangxunwang.unimanager.service.member;

import com.liangxunwang.unimanager.dao.MemberDao;
import com.liangxunwang.unimanager.dao.SmsDao;
import com.liangxunwang.unimanager.huanxin.comm.httpclient.utils.ChatUtils;
import com.liangxunwang.unimanager.model.Member;
import com.liangxunwang.unimanager.model.Sms;
import com.liangxunwang.unimanager.service.ExecuteService;
import com.liangxunwang.unimanager.service.FindService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.Constants;
import com.liangxunwang.unimanager.util.MD5Util;
import com.liangxunwang.unimanager.util.SMSMessage;
import com.liangxunwang.unimanager.util.UUIDFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by liuzwei on 2015/2/8.
 */
@Service("findPasswordService")
public class FindPasswordService implements FindService, ExecuteService {

    @Autowired
    @Qualifier("memberDao")
    private MemberDao memberDao;

    @Autowired
    @Qualifier("smsDao")
    private SmsDao messageDao;

    @Override
    public Object findById(Object object) throws ServiceException {
        String phoneNumber = (String) object;

        Member member = memberDao.findByPhone(phoneNumber);
        if (member == null){
            throw new ServiceException("NOT_EXISTS");
        }

        Sms sms = messageDao.findByMobile(phoneNumber);
        if (sms != null){
            throw new ServiceException(Constants.HAS_CODE);//已经发送验证码了
        }
        SMSMessage smsMessage = new SMSMessage();
        //生成随机六位验证码
        String code = String.valueOf((int)((Math.random()*9+1)*100000));
        //拼接发送信息
        String content = "约酒啦找回密码验证码："+code+",如非本人操作，请忽略该短信。";
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

    @Override
    public Object execute(Object object) throws ServiceException {
        Object[] params = (Object[]) object;
        String phoneNumber  = (String) params[0];
//        String code = (String) params[1];
        String password = (String) params[1];

        Member member = memberDao.findByPhone(phoneNumber);
        if (member == null){
            throw new ServiceException("NOT_EXISTS");
        }
//        Sms sms = messageDao.findByMobile(phoneNumber);
//        if (sms == null){
//            throw new ServiceException(Constants.NO_SEND_CODE);
//        }
//        if (!sms.getCode().equals(code)){
//            throw new ServiceException(Constants.CODE_NOT_EQUAL);
//        }
        memberDao.updatePassword(phoneNumber, new MD5Util().getMD5ofStr(password));

        messageDao.update(phoneNumber);

        String hxUsername = member.getHxUsername();
        //修改环信用户密码
        Boolean succ= ChatUtils.resetPW(hxUsername, new MD5Util().getMD5ofStr(password));
        if(!succ){
            throw new ServiceException(Constants.HX_ERROR);
        }
        return null;
    }
}
