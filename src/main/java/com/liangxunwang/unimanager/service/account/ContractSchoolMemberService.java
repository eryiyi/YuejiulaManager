package com.liangxunwang.unimanager.service.account;

import com.liangxunwang.unimanager.dao.ContractSchoolDao;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by zhl on 2015/3/24.
 */
@Service("contractSchoolMemberService")
public class ContractSchoolMemberService implements  ListService{

    @Autowired
    @Qualifier("contractSchoolDao")
    private ContractSchoolDao schoolDao;

    @Override
    public Object list(Object object) throws ServiceException {
            String school_id = (String) object;
            if (!StringUtil.isNullOrEmpty(school_id)) {
                return schoolDao.getManagerById(school_id);
            } else {
                return null;
            }
    }


}
