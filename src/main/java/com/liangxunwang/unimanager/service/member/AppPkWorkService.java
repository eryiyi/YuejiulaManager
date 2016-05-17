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
@Service("appPkWorkService")
public class AppPkWorkService implements  ListService{
    @Autowired
    @Qualifier("workDao")
    private WorkDao workDao;

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

        List<PkWorkVO> workVOList = workDao.listWork2(map);
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


}
