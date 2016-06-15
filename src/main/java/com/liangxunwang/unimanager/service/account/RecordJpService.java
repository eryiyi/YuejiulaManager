package com.liangxunwang.unimanager.service.account;

import com.liangxunwang.unimanager.dao.AdDao;
import com.liangxunwang.unimanager.dao.RecordDao;
import com.liangxunwang.unimanager.dao.RecordJpDao;
import com.liangxunwang.unimanager.model.AdObj;
import com.liangxunwang.unimanager.model.RecordJp;
import com.liangxunwang.unimanager.mvc.vo.RecordJpVO;
import com.liangxunwang.unimanager.query.AdQuery;
import com.liangxunwang.unimanager.query.RecordJpQuery;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.Constants;
import com.liangxunwang.unimanager.util.RelativeDateFormat;
import com.liangxunwang.unimanager.util.StringUtil;
import com.liangxunwang.unimanager.util.UUIDFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/3/3.
 */
@Service("recordJpService")
public class RecordJpService implements ListService,SaveService,ExecuteService {
    @Autowired
    @Qualifier("recordJpDao")
    private RecordJpDao recordJpDao;

    @Override
    public Object list(Object object) throws ServiceException {
        RecordJpQuery query = (RecordJpQuery) object;
        Map<String, Object> map = new HashMap<String, Object>();
        if(!StringUtil.isNullOrEmpty(query.getEmp_id())){
            map.put("emp_id", query.getEmp_id());
        }
        if(!StringUtil.isNullOrEmpty(query.getRecord_emp_id())){
            map.put("record_emp_id", query.getRecord_emp_id());
        }
        if(!StringUtil.isNullOrEmpty(query.getRecord_id())){
            map.put("record_id", query.getRecord_id());
        }
        List<RecordJpVO> lists = recordJpDao.lists(map);
        if(lists != null){
            for(RecordJpVO recordJpVO:lists){
                if (!StringUtil.isNullOrEmpty(recordJpVO.getEmpCover())){
                    if (recordJpVO.getEmpCover().startsWith("upload")){
                        recordJpVO.setEmpCover(Constants.URL+recordJpVO.getEmpCover());
                    }else {
                        recordJpVO.setEmpCover(Constants.QINIU_URL+recordJpVO.getEmpCover());
                    }
                }
                if (!StringUtil.isNullOrEmpty(recordJpVO.getEmpCoverJp())){
                    if (recordJpVO.getEmpCoverJp().startsWith("upload")){
                        recordJpVO.setEmpCoverJp(Constants.URL + recordJpVO.getEmpCoverJp());
                    }else {
                        recordJpVO.setEmpCoverJp(Constants.QINIU_URL + recordJpVO.getEmpCoverJp());
                    }
                }
                recordJpVO.setDateline(RelativeDateFormat.format(Long.parseLong(recordJpVO.getDateline())));
            }
        }
        return lists;
    }


    @Autowired
    @Qualifier("recordDao")
    private RecordDao recordDao;
    @Override
    public Object save(Object object) throws ServiceException {
        RecordJp recordJp = (RecordJp) object;
        recordJp.setSchool_record_jp_id(UUIDFactory.random());
        recordJp.setDateline(System.currentTimeMillis() + "");
        recordJpDao.save(recordJp);
        int moneyY = Integer.parseInt(recordJp.getMoneyY());
        int money = Integer.parseInt(recordJp.getMoney());
        //更改价格  原始的
        recordDao.update(recordJp.getRecord_id(), String.valueOf(moneyY+money));
        return null;
    }


    @Override
    public Object execute(Object object) throws ServiceException {
        return recordJpDao.findById((String) object);
    }

}
