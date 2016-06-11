package com.liangxunwang.unimanager.service.account;

import com.liangxunwang.unimanager.dao.*;
import com.liangxunwang.unimanager.model.Admin;
import com.liangxunwang.unimanager.model.ContractSchool;
import com.liangxunwang.unimanager.model.Member;
import com.liangxunwang.unimanager.model.SellerGoods;
import com.liangxunwang.unimanager.mvc.vo.ContractSchoolVO;
import com.liangxunwang.unimanager.query.ContractQuery;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.DateUtil;
import com.liangxunwang.unimanager.util.StringUtil;
import com.liangxunwang.unimanager.util.UUIDFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
