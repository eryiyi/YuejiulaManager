package com.liangxunwang.unimanager.service.member;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.liangxunwang.unimanager.dao.CollegeDao;
import com.liangxunwang.unimanager.dao.CountDao;
import com.liangxunwang.unimanager.dao.MemberDao;
import com.liangxunwang.unimanager.dao.SmsDao;
import com.liangxunwang.unimanager.huanxin.comm.HxConstants;
import com.liangxunwang.unimanager.huanxin.comm.Roles;
import com.liangxunwang.unimanager.huanxin.comm.httpclient.utils.ChatUtils;
import com.liangxunwang.unimanager.huanxin.comm.httpclient.utils.GroupUtils;
import com.liangxunwang.unimanager.huanxin.comm.httpclient.vo.ClientSecretCredential;
import com.liangxunwang.unimanager.huanxin.comm.httpclient.vo.Credential;
import com.liangxunwang.unimanager.model.College;
import com.liangxunwang.unimanager.model.Count;
import com.liangxunwang.unimanager.model.Member;
import com.liangxunwang.unimanager.service.ExecuteService;
import com.liangxunwang.unimanager.service.SaveService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.Constants;
import com.liangxunwang.unimanager.util.MD5Util;
import com.liangxunwang.unimanager.util.StringUtil;
import com.liangxunwang.unimanager.util.UUIDFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by liuzwei on 2015/1/29.
 */
@Service("memberRegisterService")
public class MemberRegisterService implements SaveService, ExecuteService {
    private static Logger LOGGER = LoggerFactory.getLogger(MemberRegisterService.class);
    private static JsonNodeFactory factory = new JsonNodeFactory(false);
    private static Credential credential = new ClientSecretCredential(HxConstants.APP_CLIENT_ID,
            HxConstants.APP_CLIENT_SECRET, Roles.USER_ROLE_APPADMIN);

    @Autowired
    @Qualifier("smsDao")
    private SmsDao smsDao;

    @Autowired
    @Qualifier("memberDao")
    private MemberDao memberDao;

    @Autowired
    @Qualifier("countDao")
    private CountDao countDao;

    @Autowired
    @Qualifier("collegeDao")
    private CollegeDao collegeDao;

    @Override
    public Object save(Object object) {
        Member member = (Member) object;
        Member checkMember = memberDao.findByMobile(member.getEmpMobile());
        if (checkMember != null){
            throw new ServiceException("MobileIsUse");
        }

        member.setEmpId(UUIDFactory.random());//设置ID
        member.setDateline(System.currentTimeMillis()+"");//时间戳
        member.setEmpCover(Constants.PHOTOURLS[new Random().nextInt(61)]);//头像
        member.setEmpPass(new MD5Util().getMD5ofStr(member.getEmpPass()));//密码加密
        member.setEmpTypeId("0");//默认不是管理员
        member.setIsUse("0");//默认没有禁用
        member.setEmpSex("1");//默认女
        member.setMobileStatus("0");//默认手机号不公开
        member.setHxUsername(member.getDateline()+member.getEmpMobile());
        try {
            memberDao.save(member);
            Count count = new Count();
            count.setId(UUIDFactory.random());
            count.setEmpId(member.getEmpId());
            count.setCount(1);
            countDao.save(count);
        }catch (Exception e){
            throw new ServiceException(Constants.SAVE_ERROR);
        }
        Boolean suc=ChatUtils.register(member.getHxUsername(),member.getEmpPass());
        if(suc) {
            College college=collegeDao.getGroupId(member.getSchoolId());
            GroupUtils.addGroup(college.getGroupId(), member.getDateline() + member.getEmpMobile());
        }else {
            throw new ServiceException(Constants.HX_ERROR);
        }
        return member;
    }

    /**
     * 校验昵称的唯一性
     * @param object
     * @return
     * @throws ServiceException
     */
    @Override
    public Object execute(Object object) throws ServiceException {
        Object[] params = (Object[]) object;
        String nickName = (String) params[0];
        String empId = (String) params[1];
        if (!StringUtil.isNullOrEmpty(nickName)) {
            Map<String,Object> map = new HashMap<String, Object>();
            if (!StringUtil.isNullOrEmpty(empId)) {
                map.put("empId", empId);
            }
            map.put("nickName", nickName);
            Member member = memberDao.findMemberByNickName(map);
            if (!StringUtil.isNullOrEmpty(empId)){
                if (member != null){
                    throw new ServiceException(Constants.HAS_EXISTS);
                }
            }
            if (member != null) {
                throw new ServiceException(Constants.HAS_EXISTS);
            }
        }
        return null;
    }



}
