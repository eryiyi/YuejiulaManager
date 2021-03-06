package com.liangxunwang.unimanager.service.member;

import com.liangxunwang.unimanager.dao.RecordDao;
import com.liangxunwang.unimanager.mvc.vo.RecordVO;
import com.liangxunwang.unimanager.query.RecordQuery;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.Constants;
import com.liangxunwang.unimanager.util.RelativeDateFormat;
import com.liangxunwang.unimanager.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/2/2.
 */
@Service("recordOneService")
public class RecordOneService implements ListService{

    @Autowired
    @Qualifier("recordDao")
    private RecordDao recordDao;

    @Override
    public Object list(Object object) throws ServiceException {
        RecordQuery query = (RecordQuery) object;
        String schoolId = query.getSchoolId();
        int index = (query.getIndex() - 1) * query.getSize();
        int size = query.getIndex() * query.getSize();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("index", index);
        map.put("size", size);
        if (!StringUtil.isNullOrEmpty(schoolId)){
            map.put("schoolId", schoolId);//这个动态的ID  推荐的时候  去除当前这个动态
        }
        List<RecordVO> list = recordDao.listTwo(map);

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
        return list;
    }

}
