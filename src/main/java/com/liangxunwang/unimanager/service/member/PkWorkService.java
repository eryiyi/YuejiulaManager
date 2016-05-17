package com.liangxunwang.unimanager.service.member;

import com.liangxunwang.unimanager.dao.PkCommentDao;
import com.liangxunwang.unimanager.dao.RelateDao;
import com.liangxunwang.unimanager.dao.WorkDao;
import com.liangxunwang.unimanager.model.PKWork;
import com.liangxunwang.unimanager.mvc.vo.PkCommentVO;
import com.liangxunwang.unimanager.mvc.vo.PkWorkVO;
import com.liangxunwang.unimanager.query.PkWorkQuery;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.*;
import com.qiniu.common.QiniuException;
import com.qiniu.storage.BucketManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuzwei on 15-4-4.
 */
@Service("pkWorkService")
public class PkWorkService implements SaveService , ListService, DeleteService, FindService, ExecuteService{
    @Autowired
    @Qualifier("workDao")
    private WorkDao workDao;

    @Autowired
    @Qualifier("pkCommentDao")
    private PkCommentDao pkCommentDao;

    @Autowired
    @Qualifier("relateDao")
    private RelateDao relateDao;

    @Override
    public Object delete(Object object) throws ServiceException {
        String workId = (String) object;
        Auth auth = Auth.create(ControllerConstants.QINIU_AK, ControllerConstants.QINIU_SK);
        BucketManager bucketManager = new BucketManager(auth);
        PKWork work = workDao.findWorkById(workId);

        //视频不为空  删除七牛空间的视频
        if (!StringUtil.isNullOrEmpty(work.getVideoUrl())){
            if (!work.getVideoUrl().startsWith("upload")){
                try {
                    bucketManager.delete(Constants.QINIU_SPACE, work.getVideoUrl());
                } catch (QiniuException e) {
                    throw new ServiceException("ERROR");
                }
            }
        }
        //图片不为空  删除七牛空间的图片
        if (!StringUtil.isNullOrEmpty(work.getPicUrl())){
            if (!work.getPicUrl().startsWith("upload")){
                String[] picKeys = work.getPicUrl().split(",");
                for (int i=0; i<picKeys.length; i++){
                    try {
                        bucketManager.delete(Constants.QINIU_SPACE, picKeys[i]);
                    } catch (QiniuException e) {
                        throw new ServiceException("ERROR");
                    }
                }
            }
        }

        workDao.deleteByZpId(workId);//删除作品
        relateDao.deleteByRecordId(workId);
        return null;
    }

    @Override
    public Object list(Object object) throws ServiceException {
        PkWorkQuery query = (PkWorkQuery) object;
        String schoolId = query.getSchoolId();
        String empId = query.getEmpId();
        String zan = query.getZan();
        String keyWords = query.getKeyWords();
        String time = query.getTime();

        int index = (query.getIndex() - 1) * query.getSize();
        int size = query.getIndex() * query.getSize();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("index", index);
        map.put("size", size);
        if (!StringUtil.isNullOrEmpty(query.getThemeId())){
            map.put("themeId", query.getThemeId());
        }
        if (!StringUtil.isNullOrEmpty(empId)){
            map.put("empId", empId);
        }
        if (!StringUtil.isNullOrEmpty(schoolId)){
            map.put("schoolId", schoolId);
        }
        if (!StringUtil.isNullOrEmpty(keyWords)){
            map.put("keyWords", keyWords);
        }
        if ("1".equals(zan)){
            map.put("zan", zan);
        }
        if ("1".equals(time)){
            map.put("time", time);
        }

        List<PkWorkVO> workVOList = workDao.listWork(map);
        for (PkWorkVO vo : workVOList){
            if (vo.getEmpCover().startsWith("upload")) {
                vo.setEmpCover(Constants.URL + vo.getEmpCover());
            }else {
                vo.setEmpCover(Constants.QINIU_URL + vo.getEmpCover());
            }
            vo.setDateline(RelativeDateFormat.format(Long.parseLong(vo.getDateline())));
            if (!StringUtil.isNullOrEmpty(vo.getVideoUrl()) && vo.getVideoUrl().startsWith("upload")) {
                vo.setVideoUrl(Constants.URL + vo.getVideoUrl());
            }else {
                vo.setVideoUrl(Constants.QINIU_URL + vo.getVideoUrl());
            }
            //处理图片URL链接
            StringBuffer buffer = new StringBuffer();
            String[] pics = new String[]{};
            if(vo!=null && vo.getPicUrl()!=null){
                pics = vo.getPicUrl().split(",");
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
            vo.setPicUrl(buffer.toString());
        }
        return workVOList;
    }

    @Override
    public Object save(Object object) throws ServiceException {
        PKWork work = (PKWork) object;
        work.setId(UUIDFactory.random());
        work.setIsUse("0");
        work.setDateline(System.currentTimeMillis() + "");

        if ("2".equals(work.getType())){//说明发表的是视频
            if (!work.getVideoUrl().startsWith("upload")) {
                String picName = getVideoPic(work.getVideoUrl());
                work.setPicUrl(picName);
            }
        }
        workDao.saveZp(work);
        return null;
    }

    @Override
    public Object findById(Object object) throws ServiceException {
        String zpId = (String) object;
        PkWorkVO vo = workDao.findWorkVo(zpId);
        if (vo.getEmpCover().startsWith("upload")) {
            vo.setEmpCover(Constants.URL + vo.getEmpCover());
        }else {
            vo.setEmpCover(Constants.QINIU_URL + vo.getEmpCover());
        }
        if (!StringUtil.isNullOrEmpty(vo.getPicUrl())){
            //处理图片URL链接
            StringBuffer buffer = new StringBuffer();
            String[] pics = vo.getPicUrl().split(",");
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
            vo.setPicUrl(buffer.toString());
        }
//        if (StringUtil.isNullOrEmpty(vo.getVideoUrl())){
//            vo.setVideoUrl(Constants.URL+vo.getVideoUrl());
//        }
        vo.setDateline(RelativeDateFormat.format(Long.parseLong(vo.getDateline())));
        return vo;
    }

    @Override
    public Object execute(Object object) throws ServiceException {
        String zpId = (String) object;
        PkWorkVO vo = workDao.findWorkVo(zpId);

        if(vo != null){
            if (vo.getEmpCover().startsWith("upload")) {
                vo.setEmpCover(Constants.URL + vo.getEmpCover());
            }else {
                vo.setEmpCover(Constants.QINIU_URL + vo.getEmpCover());
            }
        }
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("index", 1);
        map.put("size", 20);
        map.put("zpId", zpId);

        List<PkCommentVO> list = pkCommentDao.list(map);

        if(list != null){
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getCover().startsWith("upload")) {
                        list.get(i).setCover(Constants.URL + list.get(i).getCover());
                    }else {
                        list.get(i).setCover(Constants.QINIU_URL + list.get(i).getCover());
                    }
                }
        }

        return new Object[]{vo, list};
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
}
