package com.liangxunwang.unimanager.service.app;

import com.liangxunwang.unimanager.dao.MemberDao;
import com.liangxunwang.unimanager.query.MsgAdQuery;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2015/8/17.
 */
@Service("appMsgAdService")
public class AppMsgAdService implements ListService {
    @Autowired
    @Qualifier("memberDao")
    private MemberDao memberDao;

    @Override
    public Object list(Object object) throws ServiceException {
        MsgAdQuery query = (MsgAdQuery) object;
        return memberDao.memberCountById(query.getSchool_id());
    }
}
