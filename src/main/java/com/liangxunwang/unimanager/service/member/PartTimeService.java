package com.liangxunwang.unimanager.service.member;

import com.liangxunwang.unimanager.dao.PartTimeDao;
import com.liangxunwang.unimanager.model.PartTime;
import com.liangxunwang.unimanager.mvc.vo.PartTimeVO;
import com.liangxunwang.unimanager.query.PartTimeQuery;
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
 * Created by liuzwei on 2015/2/7.
 */
@Service("partTimeService")
public class PartTimeService implements SaveService, ListService, DeleteService , FindService{
    @Autowired
    @Qualifier("partTimeDao")
    private PartTimeDao partTimeDao;

    @Override
    public Object list(Object object) throws ServiceException {
        PartTimeQuery query = (PartTimeQuery) object;
        int index = ((query.getIndex()-1)*query.getSize())+1;
        int size = query.getIndex()*query.getSize();
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("index", index);
        map.put("size", size);
        if (!StringUtil.isNullOrEmpty(query.getKeyWords())){
            map.put("keyWords", query.getKeyWords());
        }
        if (!StringUtil.isNullOrEmpty(query.getTypeId())){
            map.put("typeId", query.getTypeId());
        }
        if (!StringUtil.isNullOrEmpty(query.getSchoolId())){
            map.put("schoolId", query.getSchoolId());
        }
        if (!StringUtil.isNullOrEmpty(query.getEmpId())){
            map.put("empId", query.getEmpId());
        }
        List<PartTimeVO> list = partTimeDao.list(map);
        for (PartTimeVO vo : list){
            vo.setDateline(RelativeDateFormat.format(Long.parseLong(vo.getDateline())));
            vo.setTypeCover(Constants.URL+vo.getTypeCover());
        }
        return list;
    }

    @Override
    public Object save(Object object) throws ServiceException {
        PartTime partTime = (PartTime) object;
        if (!StringUtil.isValidInteger(partTime.getPeopleNumber())){
            throw new ServiceException("NO_VALID_NUMBER");
        }
        partTime.setId(UUIDFactory.random());
        partTime.setDateline(System.currentTimeMillis() + "");
        partTime.setIsDel("0");
        partTime.setIsUse("0");
        partTimeDao.save(partTime);
        return partTime;
    }

    @Override
    public Object delete(Object object) throws ServiceException {
        Object[] params = (Object[]) object;
        String id = (String) params[0];
        String type= (String) params[1];
        if (type.equals("1")){
            partTimeDao.notUse(id);
        }else {
            partTimeDao.delete(id);
        }
        return null;
    }

    @Override
    public Object findById(Object object) throws ServiceException {
        PartTimeVO vo = partTimeDao.findById((String) object);
        if(vo != null){
            if (vo.getEmpCover().startsWith("upload")) {
                vo.setEmpCover(Constants.URL + vo.getEmpCover());
            }else {
                vo.setEmpCover(Constants.QINIU_URL + vo.getEmpCover());
            }
        }
        return vo;
    }
}
