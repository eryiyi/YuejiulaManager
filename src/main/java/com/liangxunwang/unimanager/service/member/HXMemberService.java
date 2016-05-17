package com.liangxunwang.unimanager.service.member;

import com.liangxunwang.unimanager.dao.CollegeDao;
import com.liangxunwang.unimanager.dao.MemberDao;
import com.liangxunwang.unimanager.huanxin.comm.httpclient.utils.GroupUtils;
import com.liangxunwang.unimanager.model.College;
import com.liangxunwang.unimanager.service.UpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by liuzwei on 2015/3/9.
 */
@Service("hXMemberService")
public class HXMemberService implements UpdateService {
    @Autowired
    @Qualifier("collegeDao")
    private CollegeDao collegeDao;

    @Autowired
    @Qualifier("memberDao")
    private MemberDao memberDao;
    @Override
    public Object update(Object object) {
        Object[] params = (Object[]) object;
        String hxUsername = (String) params[0];
        String coid = (String) params[1];
        String empId = (String) params[2];

        College college = collegeDao.getGroupId(coid);
        String groupId = college.getGroupId();
        GroupUtils.addGroup(groupId, hxUsername);
        boolean flag =  true;
        if (flag){
            memberDao.updateHx(empId);
        }
        return null;
    }
}
