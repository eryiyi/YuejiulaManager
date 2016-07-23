package com.liangxunwang.unimanager.service.member;

import com.liangxunwang.unimanager.dao.MemberDao;
import com.liangxunwang.unimanager.model.Member;
import com.liangxunwang.unimanager.service.ExecuteService;
import com.liangxunwang.unimanager.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by zhl on 15-4-4.
 */
@Service("appEmpExeByIdService")
public class AppEmpExeByIdService implements ExecuteService{
    @Autowired
    @Qualifier("memberDao")
    private MemberDao memberDao;

    @Override
    public Object execute(Object object) throws ServiceException {
        Member member = memberDao.findEmpByManagerEmpId((String) object);
        return member;
    }
}
