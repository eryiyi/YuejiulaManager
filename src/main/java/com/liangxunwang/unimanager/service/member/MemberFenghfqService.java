package com.liangxunwang.unimanager.service.member;

import com.liangxunwang.unimanager.baidu.channel.auth.ChannelKeyPair;
import com.liangxunwang.unimanager.baidu.channel.client.BaiduChannelClient;
import com.liangxunwang.unimanager.baidu.channel.exception.ChannelClientException;
import com.liangxunwang.unimanager.baidu.channel.exception.ChannelServerException;
import com.liangxunwang.unimanager.baidu.channel.model.PushUnicastMessageRequest;
import com.liangxunwang.unimanager.baidu.channel.model.PushUnicastMessageResponse;
import com.liangxunwang.unimanager.baidu.log.YunLogEvent;
import com.liangxunwang.unimanager.baidu.log.YunLogHandler;
import com.liangxunwang.unimanager.dao.*;
import com.liangxunwang.unimanager.model.ManagerEmp;
import com.liangxunwang.unimanager.model.ManagerLog;
import com.liangxunwang.unimanager.model.Member;
import com.liangxunwang.unimanager.mvc.vo.ManagerEmpVO;
import com.liangxunwang.unimanager.mvc.vo.MemberVO;
import com.liangxunwang.unimanager.query.FenghaofengqunQuery;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.SaveService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.service.UpdateService;
import com.liangxunwang.unimanager.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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




    @Override
    public Object list(Object object) throws ServiceException {
        FenghaofengqunQuery query = (FenghaofengqunQuery) object;
        Map<String,Object> map = new HashMap<String, Object>();
        List<MemberVO> list = new ArrayList<MemberVO>();
        String schools = query.getSchoolds();
       if(query.getType().equals("0")){
           //封号
           list = memberDao.getFenghaos();
       }
        if(query.getType().equals("1")){
            //封群
            list = memberDao.getFengquns();
        }
        String[] arrSchools = schools.split(",");
        List<MemberVO> lastLists = new ArrayList<MemberVO>();
        if(list != null && list.size()>0){
            //筛选符合该承包商的会员
            for (MemberVO member : list){

                //这个会员是否存在承包商的学校之内
                for (int i=0;i<arrSchools.length;i++) {
                    if (arrSchools[i].equals(member.getSchoolId())) {
                        //说明这个会员是符合要求的
                        if (member.getEmpCover().startsWith("upload")) {
                            member.setEmpCover(Constants.URL + member.getEmpCover());
                        }else {
                            member.setEmpCover(Constants.QINIU_URL + member.getEmpCover());
                        }
                        lastLists.add(member);
                        break;
                    }
                }

            }
        }

        return lastLists ;
    }

}