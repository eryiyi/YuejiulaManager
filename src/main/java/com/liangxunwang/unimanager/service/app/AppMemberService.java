package com.liangxunwang.unimanager.service.app;

import com.liangxunwang.unimanager.dao.MemberDao;
import com.liangxunwang.unimanager.model.Member;
import com.liangxunwang.unimanager.mvc.vo.EmpDianpu;
import com.liangxunwang.unimanager.mvc.vo.MemberVO;
import com.liangxunwang.unimanager.query.MemberQuery;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.Constants;
import com.liangxunwang.unimanager.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/1/31.
 */
@Service("appMemberService")
public class AppMemberService implements ListService{
    @Autowired
    @Qualifier("memberDao")
    private MemberDao memberDao;

    @Override
    public Object list(Object object) throws ServiceException {
        MemberQuery query = (MemberQuery) object;
        int index = (query.getIndex() - 1) * query.getSize();
        int size = query.getIndex() * query.getSize();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("index", index);
        map.put("size", size);

        if (!StringUtil.isNullOrEmpty(query.getEmp_typeid())) {
            map.put("emp_typeid", query.getEmp_typeid());
        }
        if (!StringUtil.isNullOrEmpty(query.getSchool_id())) {
            map.put("school_id", query.getSchool_id());
        }
        if (!StringUtil.isNullOrEmpty(query.getKeyWords())) {
            map.put("keyWords", query.getKeyWords());
        }
        List<EmpDianpu> list = memberDao.listDianPu(map);
        for(EmpDianpu empDianpu : list){
            if (empDianpu.getEmpCover().startsWith("upload")) {
                empDianpu.setEmpCover(Constants.URL + empDianpu.getEmpCover());
            }else {
                empDianpu.setEmpCover(Constants.QINIU_URL + empDianpu.getEmpCover());
            }
            if (empDianpu.getCompany_pic().startsWith("upload")) {
                empDianpu.setCompany_pic(Constants.URL + empDianpu.getCompany_pic());
            }else {
                empDianpu.setCompany_pic(Constants.QINIU_URL + empDianpu.getCompany_pic());
            }
        }
        return list;
    }


}
