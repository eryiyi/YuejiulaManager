package com.liangxunwang.unimanager.service.account;

import com.liangxunwang.unimanager.dao.MemberDao;
import com.liangxunwang.unimanager.model.Member;
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

@Service("memberUpdateByIdService")
public class MemberUpdateByIdService implements UpdateService{
    @Autowired
    @Qualifier("memberDao")
    private MemberDao memberDao;


    @Override
    public Object update(Object object) {
        Member member = (Member) object;
        if(StringUtil.isNullOrEmpty(member.getEmpSign()) || "null".equals(member.getEmpSign())){
            member.setEmpSign("老年大学我的家");
        }
        memberDao.updateMemberById(member);
        return null;
    }

}
