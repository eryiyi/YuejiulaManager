package com.liangxunwang.unimanager.service.member;

import com.liangxunwang.unimanager.baidu.channel.auth.ChannelKeyPair;
import com.liangxunwang.unimanager.baidu.channel.client.BaiduChannelClient;
import com.liangxunwang.unimanager.baidu.channel.exception.ChannelClientException;
import com.liangxunwang.unimanager.baidu.channel.exception.ChannelServerException;
import com.liangxunwang.unimanager.baidu.channel.model.PushUnicastMessageRequest;
import com.liangxunwang.unimanager.baidu.channel.model.PushUnicastMessageResponse;
import com.liangxunwang.unimanager.baidu.log.YunLogEvent;
import com.liangxunwang.unimanager.baidu.log.YunLogHandler;
import com.liangxunwang.unimanager.dao.MemberDao;
import com.liangxunwang.unimanager.dao.RelateDao;
import com.liangxunwang.unimanager.dao.ZanDao;
import com.liangxunwang.unimanager.model.Member;
import com.liangxunwang.unimanager.model.Relate;
import com.liangxunwang.unimanager.model.Zan;
import com.liangxunwang.unimanager.mvc.vo.MemberVO;
import com.liangxunwang.unimanager.mvc.vo.ZanVO;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.SaveService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liuzwei on 2015/2/3.
 */
@Service("zanService")
public class ZanService implements SaveService , ListService{
    private static Logger logger = LogManager.getLogger(ZanService.class);
    @Autowired
    @Qualifier("zanDao")
    private ZanDao zanDao;

    @Autowired
    @Qualifier("relateDao")
    private RelateDao relateDao;

    @Autowired
    @Qualifier("memberDao")
    private MemberDao memberDao;


    @Override
    public Object save(Object object) throws ServiceException {
        Zan zan = (Zan) object;
        List<Zan> check = zanDao.findByParams(zan.getRecordId(), zan.getEmpId());
        if (check.size() > 0) {
            throw new ServiceException(Constants.HAS_ZAN);
        }
        zan.setId(UUIDFactory.random());
        zan.setDateline(System.currentTimeMillis() + "");

        zanDao.save(zan);

        if (zan.getEmpId().equals(zan.getSendEmpId())) {
            return null;
        }

        MemberVO member = memberDao.findInfoById(zan.getEmpId());

        Relate relate = new Relate();
        relate.setId(UUIDFactory.random());
        relate.setEmpId(zan.getEmpId());
        relate.setEmpTwoId(zan.getSendEmpId());
        relate.setRecordId(zan.getRecordId());
        relate.setTypeId("0");
        relate.setDateline(System.currentTimeMillis() + "");
        relate.setCont(member.getEmpName() + " 赞了你的动态");
        relateDao.save(relate);

        Member pushMember = memberDao.findById(zan.getSendEmpId());
        String pushId = pushMember.getPushId();
        if(StringUtil.isNullOrEmpty(pushId)){
            return zan;
        }
        String type = pushMember.getDeviceType();

        pushZan(pushId, type, member.getEmpName() + " 赞了你的动态");
        return zan;
    }

    @Override
    public Object list(Object object) throws ServiceException {
        List<ZanVO> list =zanDao.listZanByRecord((String) object);
        for (ZanVO zanVO : list){
            if (zanVO.getCover().startsWith("upload")) {
                zanVO.setCover(Constants.URL + zanVO.getCover());
            }else {
                zanVO.setCover(Constants.QINIU_URL + zanVO.getCover());
            }
            zanVO.setDateline(RelativeDateFormat.format(Long.parseLong(zanVO.getDateline())));
        }
        return list;
    }

    private void pushZan(final String pushId, final String type, final String content){
        CommonUtil.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                ChannelKeyPair pair = null;
                if (type.equals("3")) {
                    pair = new ChannelKeyPair(Constants.API_KEY, Constants.SECRET_KEY);
                } else {
                    pair = new ChannelKeyPair(Constants.IOS_API_KEY, Constants.IOS_SECRET_KEY);
                }
                // 2. 创建BaiduChannelClient对象实例
                BaiduChannelClient channelClient = new BaiduChannelClient(pair);
                // 3. 若要了解交互细节，请注册YunLogHandler类
                channelClient.setChannelLogHandler(new YunLogHandler() {
                    @Override
                    public void onHandle(YunLogEvent event) {
                        System.out.println(event.getMessage());
                    }
                });
                try {
                    // 4. 创建请求类对象
                    // 手机端的ChannelId， 手机端的UserId， 先用1111111111111代替，用户需替换为自己的
                    PushUnicastMessageRequest request = new PushUnicastMessageRequest();
                    request.setDeviceType(Integer.parseInt(type));
                    if (type.equals("4")) {//如果是苹果手机端要设置这个
                        request.setDeployStatus(2);
                    }
//            request.setDeviceType(3); // device_type => 1: web 2: pc 3:android  4:ios 5:wp
//            request.setChannelId(3671408368535076013L);
//            request.setUserId("792078116439786170");
                    request.setUserId(pushId);

                    request.setMessageType(1);
                    request.setMessage("{\"title\":\"提醒\",\"description\":\"" + content + "\",\"custom_content\": {\"type\":\"2\"}}");

                    logger.info("开始调用赞的推送");
                    // 5. 调用pushMessage接口
                    PushUnicastMessageResponse response = channelClient.pushUnicastMessage(request);

                    logger.info("赞推送成功+++++"+response.getSuccessAmount());
                    // 6. 认证推送成功
                    System.out.println("push amount : " + response.getSuccessAmount());

                } catch (ChannelClientException e) {
                    // 处理客户端错误异常
                    e.printStackTrace();
                } catch (ChannelServerException e) {
                    // 处理服务端错误异常
                    System.out.println(String.format(
                            "request_id: %d, error_code: %d, error_message: %s",
                            e.getRequestId(), e.getErrorCode(), e.getErrorMsg()));
                }
            }
        });
    }
}
