package com.liangxunwang.unimanager.service.member;

import com.liangxunwang.unimanager.dao.VisitorDao;
import com.liangxunwang.unimanager.model.Visitor;
import com.liangxunwang.unimanager.mvc.vo.VisitorVO;
import com.liangxunwang.unimanager.query.VisitorQuery;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.SaveService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.UUIDFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuzwei on 2015/2/3.
 */
@Service("visitorService")
public class VisitorService implements SaveService, ListService {
    @Autowired
    @Qualifier("visitorDao")
    private VisitorDao visitorDao;
    @Override
    public Object list(Object object) throws ServiceException {
        if (object instanceof VisitorQuery) {
            VisitorQuery query = (VisitorQuery) object;
            int index = ((query.getIndex()-1)*query.getSize())+1;
            int size = query.getIndex()*query.getSize();

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("index", index);
            map.put("size", size);
            map.put("empId", query.getEmpId());
            List<VisitorVO> list = visitorDao.listVisitor(map);
            return list;
        }
        return null;
    }

    @Override
    public Object save(Object object) throws ServiceException {
        Visitor visitor = (Visitor) object;

        Visitor check = visitorDao.findByParam(visitor.getEmpOne(), visitor.getEmpTwo());
        if (check != null){
            //访问过的话去更新访问时间
            visitorDao.updateTime(check.getId(), System.currentTimeMillis()+"");
        }else {
            //没有访问过的话去保存访问记录
            visitor.setId(UUIDFactory.random());
            visitor.setDateline(System.currentTimeMillis()+"");
            visitorDao.save(visitor);
        }
        return null;
    }
}
