package com.liangxunwang.unimanager.service.member;

import com.liangxunwang.unimanager.baidu.channel.auth.ChannelKeyPair;
import com.liangxunwang.unimanager.baidu.channel.client.BaiduChannelClient;
import com.liangxunwang.unimanager.baidu.channel.exception.ChannelClientException;
import com.liangxunwang.unimanager.baidu.channel.exception.ChannelServerException;
import com.liangxunwang.unimanager.baidu.channel.model.PushUnicastMessageRequest;
import com.liangxunwang.unimanager.baidu.channel.model.PushUnicastMessageResponse;
import com.liangxunwang.unimanager.baidu.log.YunLogEvent;
import com.liangxunwang.unimanager.baidu.log.YunLogHandler;
import com.liangxunwang.unimanager.dao.*;
import com.liangxunwang.unimanager.model.*;
import com.liangxunwang.unimanager.mvc.vo.FhFqObjVO;
import com.liangxunwang.unimanager.mvc.vo.MemberVO;
import com.liangxunwang.unimanager.mvc.vo.RecordVO;
import com.liangxunwang.unimanager.query.RecordQuery;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.*;
import com.qiniu.common.QiniuException;
import com.qiniu.storage.BucketManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by zhl on 2015/2/2.
 */
@Service("recordService")
public class RecordService implements ListService, SaveService,DeleteService, FindService, Runnable{

    @Autowired
    @Qualifier("contractSchoolDao")
    private ContractSchoolDao contractSchoolDao;

    @Autowired
    @Qualifier("recordDao")
    private RecordDao recordDao;

    @Autowired
    @Qualifier("countDao")
    private CountDao countDao;

    @Autowired
    @Qualifier("advertDao")
    private AdvertDao advertDao;

    @Autowired
    @Qualifier("relateDao")
    private RelateDao relateDao;
    @Autowired
    @Qualifier("memberDao")
    private MemberDao memberDao;

    @Autowired
    @Qualifier("fqfhObjDao")
    private FqfhObjDao fqfhObjDao;

    @Override
    public Object list(Object object) throws ServiceException {
        RecordQuery query = (RecordQuery) object;
        String schoolId = query.getSchoolId();
        String empId = query.getEmpId();
        int index = (query.getIndex() - 1) * query.getSize();
        int size = query.getIndex() * query.getSize();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("index", index);
        map.put("size", size);
        if (!StringUtil.isNullOrEmpty(empId)){
            map.put("empId", empId);
        }
        if (!StringUtil.isNullOrEmpty(schoolId)){
            map.put("schoolId", schoolId);
        }
        if (!StringUtil.isNullOrEmpty(query.getSchool_record_mood_id())){
            map.put("school_record_mood_id", query.getSchool_record_mood_id());
        }
        List<RecordVO> list = recordDao.list(map);
//        RecordVO recordVO = null;
        //查询首页的推广
//        if (!StringUtil.isNullOrEmpty(query.getSchoolIdEmp())){
//            recordVO = recordDao.findBySchoolId(query.getSchoolIdEmp());
//        }
//        //查询我的学校和其他学校的推广
//        if (!StringUtil.isNullOrEmpty(schoolId)){
//            recordVO = recordDao.findBySchoolId(schoolId);
//        }
//        if (recordVO != null){
//            list.add(recordVO);
//        }

        for (RecordVO record : list){
            if (!StringUtil.isNullOrEmpty(record.getEmpCover())){
                if (record.getEmpCover().startsWith("upload")){
                    record.setEmpCover(Constants.URL+record.getEmpCover());
                }else {
                    record.setEmpCover(Constants.QINIU_URL+record.getEmpCover());
                }
            }
            //处理图片URL链接
            StringBuffer buffer = new StringBuffer();
            String[] pics = new String[]{};
            if(record!=null && record.getRecordPicUrl()!=null){
                pics = record.getRecordPicUrl().split(",");
            }
            for (int i=0; i<pics.length; i++){
                if (pics[i].startsWith("upload")) {
                    buffer.append(Constants.URL + pics[i]);
                    if (i < pics.length - 1) {
                        buffer.append(",");
                    }
                }else {
                    buffer.append(Constants.QINIU_URL + pics[i]);
                    if (i < pics.length - 1) {
                        buffer.append(",");
                    }
                }
            }
            record.setRecordPicUrl(buffer.toString());
            if (!StringUtil.isNullOrEmpty(record.getRecordVideo())){
                if (record.getRecordVideo().startsWith("upload")) {
                    record.setRecordVideo(Constants.URL + record.getRecordVideo());
                }else {
                    record.setRecordVideo(Constants.QINIU_URL + record.getRecordVideo());
                }
            }
            if(StringUtil.isNullOrEmpty(record.getSchool_record_mood_name())){
                //如果是空的话
                record.setSchool_record_mood_name("无");
            }
            record.setDateLine(RelativeDateFormat.format(Long.parseLong(record.getDateLine())));
        }
        return list;
    }
    Record recordTmp = null;
    @Override
    public Object save(Object object) throws ServiceException {
        Record record = (Record) object;
        record.setRecordCont(CommonUtil.replaceBlank(record.getRecordCont()));
        if(StringUtil.isNullOrEmpty(record.getIs_paimai())){
            //如果是空或者null 说明不是拍卖
            record.setIs_paimai("0");
        }
        //如果是推广
        if ("1".equals(record.getRecordType())){
            List<Record> recordList = recordDao.findByEmpIdAndType(record.getRecordEmpId(), "1");
            if (recordList.size() > 0){
                //说明已经发布了推广
                throw new ServiceException("HAS_PUBLISH");
            }
            List<ContractSchool> list = contractSchoolDao.findContractSchoolByEmpId(record.getRecordEmpId());
            for (ContractSchool school : list){
                String recordId = UUIDFactory.random();
                record.setRecordId(recordId);
                record.setDateLine(System.currentTimeMillis() + "");
                record.setRecordIsDel("0");
                record.setRecordIsUse("0");
                record.setRecordSchoolId(school.getSchoolId());
                recordDao.save(record);
            }
        }else {
            //判断是否被封号
            Member member = memberDao.findById(record.getRecordEmpId());
            if(member != null){
                if("1".equals(member.getIs_fenghao())){
                    //说明被封号了
                    //查询是否封的这个学校
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("emp_id", member.getEmpId());
                    map.put("istype", "0");
                    map.put("school_id", member.getSchoolId());
                    List<FhFqObjVO>  lists = fqfhObjDao.lists(map);
                    if(lists != null && lists.size()>0){
                        throw new ServiceException("HAS_FENGHAO");
                    }

                }
                record.setDateLine(System.currentTimeMillis() + "");
                record.setRecordIsDel("0");
                record.setRecordIsUse("0");
                if ("2".equals(record.getRecordType())){//说明发表的是视频
                    if (!record.getRecordVideo().startsWith("upload")) {
                        String picName = getVideoPic(record.getRecordVideo());
                        record.setRecordPicUrl(picName);
//                    record.setRecordPicUrl("upload/video_fail.jpg");
                    }
                }
                recordTmp = record;
                recordDao.save(record);
                //更新积分分数
                countDao.update(record.getRecordEmpId(), record.getRecordType());
                //开启线程，处理关注标签通知
                //todo
                new Thread(RecordService.this).start();
            }else {
                throw new ServiceException("NO_EMP");
            }

        }
        return null;
    }

    @Override
    public Object findById(Object object) throws ServiceException {
        if (object instanceof String) {
            RecordVO vo = recordDao.findById((String) object);
            if (vo != null) {
                if (vo.getEmpCover().startsWith("upload")) {
                    vo.setEmpCover(Constants.URL + vo.getEmpCover());
                }else {
                    vo.setEmpCover(Constants.QINIU_URL + vo.getEmpCover());
                }
                vo.setDateLine(RelativeDateFormat.format(Long.parseLong(vo.getDateLine())));
                if (!StringUtil.isNullOrEmpty(vo.getRecordPicUrl())) {
                    String[] pic = vo.getRecordPicUrl().split(",");
                    StringBuffer buffer = new StringBuffer();
                    for (int i = 0; i < pic.length; i++) {
                        if (pic[i].startsWith("upload")) {
                            buffer.append(Constants.URL + pic[i]);
                            if (i < pic.length - 1) {
                                buffer.append(",");
                            }
                        }else {
                            buffer.append(Constants.QINIU_URL + pic[i]);
                            if (i < pic.length - 1) {
                                buffer.append(",");
                            }
                        }
                    }
                    vo.setRecordPicUrl(buffer.toString());
                }
                if (!StringUtil.isNullOrEmpty(vo.getRecordVideo())){
                    if (vo.getRecordVideo().startsWith("upload")) {
                        vo.setRecordVideo(Constants.URL + vo.getRecordVideo());
                    }else {
                        vo.setRecordVideo(Constants.QINIU_URL + vo.getRecordVideo());
                    }
                }
            }
            return vo;
        }else {
            Object[] params = (Object[]) object;
            String recordId = (String) params[0];
            RecordVO vo = recordDao.findById(recordId);
//            if(vo != null){
                Advert backAdvert = null;
                Random random = new Random();
                List<Advert> list = advertDao.getSmall(vo.getRecordSchoolId());
                if (list.size()>0){
                    backAdvert =  list.get(random.nextInt(list.size()));
                }else {
                    List<Advert> adverts = advertDao.getBig("4");
                    if (adverts.size()>0) {
                        backAdvert =  adverts.get(0);
                    }
                }

                if (vo != null) {
                    if (vo.getEmpCover().startsWith("upload")) {
                        vo.setEmpCover(Constants.URL + vo.getEmpCover());
                    }else {
                        vo.setEmpCover(Constants.QINIU_URL + vo.getEmpCover());
                    }
//                vo.setDateLine(RelativeDateFormat.format(Long.parseLong(vo.getDateLine())));
                    if (!StringUtil.isNullOrEmpty(vo.getRecordPicUrl())) {
                        String[] pic = vo.getRecordPicUrl().split(",");
                        StringBuffer buffer = new StringBuffer();
                        for (int i = 0; i < pic.length; i++) {
                            if (pic[i].startsWith("upload")) {
                                buffer.append(Constants.URL + pic[i]);
                                if (i < pic.length - 1) {
                                    buffer.append(",");
                                }
                            }else {
                                buffer.append(Constants.QINIU_URL + pic[i]);
                                if (i < pic.length - 1) {
                                    buffer.append(",");
                                }
                            }
                        }
                        vo.setRecordPicUrl(buffer.toString());
                    }
                }

                //分享出去的视频添加七牛链接
                if (!StringUtil.isNullOrEmpty(vo.getRecordVideo()) && !vo.getRecordVideo().startsWith("upload")) {
                    vo.setRecordVideo(Constants.QINIU_URL+vo.getRecordVideo());
                }
                return new Object[]{vo, backAdvert};
//            }else {
//                return null;
//            }
        }
    }

    @Override
    public Object delete(Object object) throws ServiceException {
        Auth auth = Auth.create(ControllerConstants.QINIU_AK, ControllerConstants.QINIU_SK);
        BucketManager bucketManager = new BucketManager(auth);
        String recordId = (String) object;
        if (!StringUtil.isNullOrEmpty(recordId)) {
            RecordVO record  = recordDao.findById(recordId);
            //视频不为空  删除七牛空间的视频
            if (!StringUtil.isNullOrEmpty(record.getRecordVideo())){
                if (!record.getRecordVideo().startsWith("upload")){
                    try {
                        bucketManager.delete(Constants.QINIU_SPACE, record.getRecordVideo());
                    } catch (QiniuException e) {
                        throw new ServiceException("ERROR");
                    }
                }
            }
            //图片不为空  删除七牛空间的图片
            if (!StringUtil.isNullOrEmpty(record.getRecordPicUrl())){
                if (!record.getRecordPicUrl().startsWith("upload")){
                    String[] picKeys = record.getRecordPicUrl().split(",");
                    for (int i=0; i<picKeys.length; i++){
                        try {
                            bucketManager.delete(Constants.QINIU_SPACE, picKeys[i]);
                        } catch (QiniuException e) {
                            throw new ServiceException("ERROR");
                        }
                    }
                }
            }
            //删除动态
            recordDao.deleteById(recordId);
            //删除与动态关联的与我相关数据
            relateDao.deleteByRecordId(recordId);
        }else {
            throw new ServiceException("ERROR");
        }
        return null;
    }

    private String getVideoPic(String videoName){
        String picName = UUIDFactory.random();
        StringMap params = new StringMap()
                .putWhen("force", 1, true)
                .putNotEmpty("pipeline", "");

        String saveas = Constants.QINIU_SPACE+":"+picName;
        String safeSaveas = UrlSafeBase64.encodeToString(saveas);

        String fops = "vframe/jpg/offset/1/rotate/auto|saveas/";

        String fop = fops + safeSaveas;

        String persistentId;
        try {
            persistentId = new Ops().oper(Constants.QINIU_SPACE, videoName,fop,params);
            System.out.println(persistentId);
        } catch (QiniuException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            try {
                System.out.println(e.response.bodyString());
            } catch (QiniuException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }

        return picName;
    }

    @Override
    public void run() {
        //recordTmp
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("school_record_mood_id", recordTmp.getSchool_record_mood_id());
        map.put("school_id", recordTmp.getRecordSchoolId());
        List<MemberVO> list = memberDao.listByMoods(map);//关注了这个标签的会员
        if(list != null){
            for(MemberVO memberVO:list){
                String pushId =memberVO.getPushId();
                String type = memberVO.getDeviceType();
                if(!StringUtil.isNullOrEmpty(pushId) && !StringUtil.isNullOrEmpty(type) ){
                    Relate relate1 = new Relate();
                    relate1.setId(UUIDFactory.random());
                    relate1.setEmpId(recordTmp.getRecordEmpId());
                    relate1.setEmpTwoId(memberVO.getEmpId());//推送给谁
                    relate1.setRecordId(recordTmp.getRecordId());
                    relate1.setTypeId("0");
                    relate1.setDateline(System.currentTimeMillis()+"");
                    relate1.setCont("您关注的朋友圈有新动态");
                    relateDao.save(relate1);
                    pushZan(pushId, type, "您关注的朋友圈有新动态");
                }
            }
        }
    }

    private static Logger logger = LogManager.getLogger(RecordService.class);

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

                    logger.info("开始调用百度推送接口");
                    // 5. 调用pushMessage接口
                    PushUnicastMessageResponse response = channelClient.pushUnicastMessage(request);

                    logger.info("推送成功----"+response.getSuccessAmount());
                    // 6. 认证推送成功
                    System.out.println("push amount : " + response.getSuccessAmount());

                } catch (ChannelClientException e) {
                    // 处理客户端错误异常
                    e.printStackTrace();
                    logger.info("处理客户端异常"+e.getMessage());
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
