package com.liangxunwang.unimanager.service.member;

import com.liangxunwang.unimanager.dao.RecordDao;
import com.liangxunwang.unimanager.model.Record;
import com.liangxunwang.unimanager.service.DeleteService;
import com.liangxunwang.unimanager.service.FindService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.Constants;
import com.liangxunwang.unimanager.util.RelativeDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liuzwei on 2015/4/2.
 */
@Service("promotionService")
public class PromotionService implements FindService, DeleteService {
    @Autowired
    @Qualifier("recordDao")
    private RecordDao recordDao;

    @Override
    public Object findById(Object object) throws ServiceException {
        List<Record> records = recordDao.findByEmpIdAndType((String) object, "1");
        for (Record record : records){
            record.setDateLine(RelativeDateFormat.format(Long.parseLong(record.getDateLine())));

            if (record.getRecordPicUrl().startsWith("upload")) {
                record.setRecordPicUrl(Constants.URL + record.getRecordPicUrl());
            }else {
                record.setRecordPicUrl(Constants.QINIU_URL + record.getRecordPicUrl());
            }

        }
        return records;
    }

    @Override
    public Object delete(Object object) throws ServiceException {
        String empId = (String) object;
        recordDao.deletePromotion(empId);
        return null;
    }
}
