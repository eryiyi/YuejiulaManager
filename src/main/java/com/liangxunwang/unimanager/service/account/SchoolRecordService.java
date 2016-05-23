package com.liangxunwang.unimanager.service.account;

import com.liangxunwang.unimanager.dao.*;
import com.liangxunwang.unimanager.model.Advert;
import com.liangxunwang.unimanager.model.ContractSchool;
import com.liangxunwang.unimanager.model.Record;
import com.liangxunwang.unimanager.mvc.vo.RecordVO;
import com.liangxunwang.unimanager.query.RecordQuery;
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
import java.util.Random;

/**
 * Created by zhl on 2015/2/2.
 */
@Service("schoolRecordService")
public class SchoolRecordService implements ListService{

    @Autowired
    @Qualifier("recordDao")
    private RecordDao recordDao;

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
            record.setDateLine(RelativeDateFormat.format(Long.parseLong(record.getDateLine())));
        }
        long count = recordDao.count(map);
        return new Object[]{list, count};
    }

}
