package com.liangxunwang.unimanager.service.member;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/2/8.
 */
@Service("memberUpdateFenghaoService")
public class MemberUpdateFenghaoService implements   UpdateService {
    @Autowired
    @Qualifier("memberDao")
    private MemberDao memberDao ;

    @Override
    public Object update(Object object) {
        Object[] params = (Object[]) object;

        String emp_id = (String) params[1];
        String type = (String) params[2];
        if("1".equals(type)){
            String is_fenghao = (String) params[0];
            memberDao.updateFenghao(is_fenghao, emp_id);
        }
        if("2".equals(type)){
            String is_fengqun = (String) params[0];
            memberDao.updateFengQun(is_fengqun, emp_id);
        }
        return null;
    }


}
