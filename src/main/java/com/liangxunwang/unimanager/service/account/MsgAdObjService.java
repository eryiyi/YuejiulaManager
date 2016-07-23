package com.liangxunwang.unimanager.service.account;

import com.liangxunwang.unimanager.dao.MsgAdDao;
import com.liangxunwang.unimanager.model.MsgAd;
import com.liangxunwang.unimanager.query.MsgAdQuery;
import com.liangxunwang.unimanager.service.*;
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
@Service("msgAdObjService")
public class MsgAdObjService implements ListService,SaveService,DeleteService,ExecuteService, UpdateService {
    @Autowired
    @Qualifier("msgAdDao")
    private MsgAdDao msgAdDao;

    @Override
    public Object list(Object object) throws ServiceException {
        MsgAdQuery query = (MsgAdQuery) object;
        Map<String, Object> map = new HashMap<String, Object>();
        if(!StringUtil.isNullOrEmpty(query.getEmp_id())){
            map.put("emp_id", query.getEmp_id());
        }
        if(!StringUtil.isNullOrEmpty(query.getSchool_id())){
            map.put("school_id", query.getSchool_id());
        }
        List<MsgAd> lists = msgAdDao.lists(map);
        return lists;
    }

    @Override
    public Object save(Object object) throws ServiceException {
        if (object instanceof Object[]){
            Object[] params = (Object[]) object;
            MsgAd adObj = (MsgAd) params[0];
            String schools = (String) params[1];

            String[] schoolAry = schools.split("\\|");
            for (int i=0; i<schoolAry.length; i++){
                Map<String, Object> map = new HashMap<String, Object>();
                if(!StringUtil.isNullOrEmpty(adObj.getEmp_id())){
                    map.put("emp_id", adObj.getEmp_id());
                } if(!StringUtil.isNullOrEmpty(schoolAry[i])){
                    map.put("school_id", schoolAry[i]);
                }

                List<MsgAd> lists = msgAdDao.lists(map);
                if(lists != null && lists.size() > 0){
                    //说明该学校有广告语 不能重复添加
                    throw new ServiceException("adIsTooMuch");
                }else {
                    adObj.setMsg_ad_no(UUIDFactory.random());
                    adObj.setDateline(System.currentTimeMillis() + "");
                    adObj.setSchool_id(schoolAry[i]);
                    msgAdDao.save(adObj);
                }

            }
        }

        return null;
    }

    @Override
    public Object delete(Object object) throws ServiceException {
        String msg_ad_no = (String) object;
        msgAdDao.delete(msg_ad_no);
        return null;
    }

    @Override
    public Object execute(Object object) throws ServiceException {
        return msgAdDao.findById((String) object);
    }

    @Override
    public Object update(Object object) {
        MsgAd msgAd = (MsgAd) object;
        msgAdDao.update(msgAd);
        return null;
    }
}
