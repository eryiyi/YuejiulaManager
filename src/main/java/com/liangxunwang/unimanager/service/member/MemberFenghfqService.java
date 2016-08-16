package com.liangxunwang.unimanager.service.member;

import com.liangxunwang.unimanager.dao.FqfhObjDao;
import com.liangxunwang.unimanager.dao.MemberDao;
import com.liangxunwang.unimanager.mvc.vo.FhFqObjVO;
import com.liangxunwang.unimanager.query.FenghaofengqunQuery;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/2/4.
 */
@Service("memberFenghfqService")
public class MemberFenghfqService implements  ListService{
    @Autowired
    @Qualifier("memberDao")
    private MemberDao memberDao;

    @Autowired
    @Qualifier("fqfhObjDao")
    private FqfhObjDao fqfhObjDao ;

    @Override
    public Object list(Object object) throws ServiceException {
        FenghaofengqunQuery query = (FenghaofengqunQuery) object;
        Map<String,Object> map = new HashMap<String, Object>();
        if(StringUtil.isNullOrEmpty( query.getEmp_id())){
            map.put("emp_id", query.getEmp_id());
        }
        if(StringUtil.isNullOrEmpty( query.getEmp_id_m())){
            map.put("emp_id_m", query.getEmp_id_m());
        }
        if(StringUtil.isNullOrEmpty( query.getIstype())){
            map.put("istype", query.getIstype());
        }
        List<FhFqObjVO> list = fqfhObjDao.lists(map);
        return list ;
    }

}
