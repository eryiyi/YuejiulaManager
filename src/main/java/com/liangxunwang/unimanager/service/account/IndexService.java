package com.liangxunwang.unimanager.service.account;

import com.liangxunwang.unimanager.dao.AdvertDao;
import com.liangxunwang.unimanager.dao.MemberDao;
import com.liangxunwang.unimanager.model.Advert;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by liuzwei on 2015/3/3.
 */
@Service("indexService")
public class IndexService implements ListService {
    @Autowired
    @Qualifier("memberDao")
    private MemberDao memberDao;

    @Autowired
    @Qualifier("advertDao")
    private AdvertDao advertDao;
    @Override
    public Object list(Object object) throws ServiceException {
        //总共会员数量
        long memberCount = memberDao.memberCount();
        //被关禁闭会员数量
        long closeMemberCount = memberDao.closeMemberCount();

        //广告数量
        long advertCont = advertDao.count(new HashMap<String, Object>());
        List<Long> list = new ArrayList<Long>();
        list.add(memberCount);
        list.add(closeMemberCount);
        list.add(advertCont);
        return list;
    }
}
