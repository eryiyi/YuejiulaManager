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
import com.liangxunwang.unimanager.dao.PkCommentDao;
import com.liangxunwang.unimanager.dao.RelateDao;
import com.liangxunwang.unimanager.model.Member;
import com.liangxunwang.unimanager.model.PKComment;
import com.liangxunwang.unimanager.model.Relate;
import com.liangxunwang.unimanager.mvc.vo.MemberVO;
import com.liangxunwang.unimanager.mvc.vo.PkCommentVO;
import com.liangxunwang.unimanager.query.PkCommentQuery;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.SaveService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuzwei on 15-4-4.
 */
@Service("pkCommentService")
public class PkCommentService implements SaveService, ListService {

    @Autowired
    @Qualifier("pkCommentDao")
    private PkCommentDao pkCommentDao;

     @Autowired
    @Qualifier("memberDao")
    private MemberDao memberDao;

    @Autowired
    @Qualifier("relateDao")
    private RelateDao relateDao;

    @Override
    public Object list(Object object) throws ServiceException {
        PkCommentQuery query = (PkCommentQuery) object;
        int index = ((query.getIndex()-1)*query.getSize())+1;
        int size = query.getIndex()*query.getSize();
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("index", index);
        map.put("size", size);
        map.put("zpId", query.getZpId());
        List<PkCommentVO> list = pkCommentDao.list(map);
        for (PkCommentVO comment:list){
            comment.setDateline(RelativeDateFormat.format(Long.parseLong(comment.getDateline())));
            if (comment.getCover().startsWith("upload")) {
                comment.setCover(Constants.URL + comment.getCover());
            }else {
                comment.setCover(Constants.QINIU_URL + comment.getCover());
            }
        }
        return list;
    }

    @Override
    public Object save(Object object) throws ServiceException {
        PKComment comment = (PKComment) object;
        comment.setId(UUIDFactory.random());
        comment.setDateline(System.currentTimeMillis() + "");

        pkCommentDao.saveComment(comment);

        MemberVO member = memberDao.findInfoById(comment.getEmpId());
        if (!StringUtil.isNullOrEmpty(comment.getfPlid()) && !"".equals(comment.getfPlid()) && !"0".equals(comment.getfPlid())){
            if (!comment.getEmpId().equals(comment.getSendEmpId())){
                //有服评论
                Relate relate = new Relate();
                relate.setId(UUIDFactory.random());
                relate.setEmpId(comment.getEmpId());
                relate.setEmpTwoId(comment.getSendEmpId());
                relate.setRecordId(comment.getZpId());
                relate.setTypeId("2");
                relate.setDateline(System.currentTimeMillis()+"");
                relate.setCont(member.getEmpName()+" 评论了你的PK作品");
                relateDao.save(relate);
            }


            if (!comment.getFplempid().equals(comment.getSendEmpId()) && !comment.getFplempid().equals(comment.getEmpId())){
                Relate relate1 = new Relate();
                relate1.setId(UUIDFactory.random());
                relate1.setEmpId(comment.getEmpId());
                relate1.setEmpTwoId(comment.getFplempid());
                relate1.setRecordId(comment.getZpId());
                relate1.setTypeId("2");
                relate1.setDateline(System.currentTimeMillis()+"");
                relate1.setCont(member.getEmpName()+" 回复了你的PK作品评论");
                relateDao.save(relate1);
            }

        }else {
            //没有父评论
            if (!comment.getEmpId().equals(comment.getSendEmpId())){
                Relate relate = new Relate();
                relate.setId(UUIDFactory.random());
                relate.setEmpId(comment.getEmpId());
                relate.setEmpTwoId(comment.getSendEmpId());
                relate.setRecordId(comment.getZpId());
                relate.setTypeId("2");
                relate.setDateline(System.currentTimeMillis()+"");
                relate.setCont(member.getEmpName()+" 评论了你的PK作品");
                relateDao.save(relate);
            }

        }

        Member pushMember =  memberDao.findById(comment.getSendEmpId());
        String pushId =pushMember.getPushId();
        String type = pushMember.getDeviceType();



        if (!StringUtil.isNullOrEmpty(comment.getfPlid()) && !"".equals(comment.getfPlid()) && !"0".equals(comment.getfPlid())){
            //有父评论
            if (!comment.getSendEmpId().equals(comment.getEmpId())) {
                //动态所有者发通知
                pushZan(pushId,type, member.getEmpName() + " 评论了你的PK作品");
            }

            if (!comment.getFplempid().equals(member.getEmpId()) && !comment.getFplempid().equals(comment.getSendEmpId())) {
                //向父评论者发通知
                Member pushMember1 =  memberDao.findById(comment.getFplempid());
                String pushId1 =pushMember1.getPushId();
                String type1 = pushMember1.getDeviceType();

                pushZan(pushId1, type1, member.getEmpName() + " 回复了你的PK作品评论");
            }
        }else {
            //没有父评论
            if (!comment.getSendEmpId().equals(comment.getEmpId())) {
                pushZan(pushId,type, member.getEmpName() + " 评论了你的PK作品");
            }
        }
        return null;
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
//            request.setChannelId(Long.parseLong(pushId));
//            request.setChannelId(5100663888284228047L);
                    request.setUserId(pushId);

                    request.setMessageType(1);
                    request.setMessage("{\"title\":\"提醒\",\"description\":\"" + content + "\",\"custom_content\": {\"type\":\"2\"}}");


                    // 5. 调用pushMessage接口
                    PushUnicastMessageResponse response = channelClient.pushUnicastMessage(request);

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
