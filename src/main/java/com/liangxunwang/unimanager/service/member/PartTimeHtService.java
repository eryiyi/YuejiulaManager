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
 * Created by zhl on 2015/2/7.
 */
@Service("partTimeHtService")
public class PartTimeHtService implements  ListService {
    @Autowired
    @Qualifier("partTimeDao")
    private PartTimeDao partTimeDao;

    @Override
    public Object list(Object object) throws ServiceException {
        PartTimeQuery query = (PartTimeQuery) object;

        int index = (query.getIndex() - 1) * query.getSize();
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

        long count = partTimeDao.count(map);
        return new Object[]{list, count};
    }

}
