package com.liangxunwang.unimanager.service.member;

import com.liangxunwang.unimanager.dao.FqfhObjDao;
import com.liangxunwang.unimanager.dao.MemberDao;
import com.liangxunwang.unimanager.model.FhFqObj;
import com.liangxunwang.unimanager.mvc.vo.FhFqObjVO;
import com.liangxunwang.unimanager.mvc.vo.MemberVO;
import com.liangxunwang.unimanager.query.FenghaofengqunQuery;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.Constants;
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

    @Autowired
    @Qualifier("fqfhObjDao")
    private FqfhObjDao fqfhObjDao ;

    @Override
    public Object list(Object object) throws ServiceException {
        FenghaofengqunQuery query = (FenghaofengqunQuery) object;
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("emp_id_m", query.getEmp_id_m());
        map.put("istype", query.getIstype());
        List<FhFqObjVO> list = fqfhObjDao.lists(map);

//        List<MemberVO> list = new ArrayList<MemberVO>();
//        String schools = query.getSchoolds();
//       if(query.getType().equals("0")){
//           //封号
//           list = memberDao.getFenghaos();
//       }
//        if(query.getType().equals("1")){
//            //封群
//            list = memberDao.getFengquns();
//        }
//        String[] arrSchools = schools.split(",");
//        List<MemberVO> lastLists = new ArrayList<MemberVO>();
//        if(list != null && list.size()>0){
//            //筛选符合该承包商的会员
//            for (MemberVO member : list){
//
//                //这个会员是否存在承包商的学校之内
//                for (int i=0;i<arrSchools.length;i++) {
//                    if (arrSchools[i].equals(member.getSchoolId())) {
//                        //说明这个会员是符合要求的
//                        if (member.getEmpCover().startsWith("upload")) {
//                            member.setEmpCover(Constants.URL + member.getEmpCover());
//                        }else {
//                            member.setEmpCover(Constants.QINIU_URL + member.getEmpCover());
//                        }
//                        lastLists.add(member);
//                        break;
//                    }
//                }
//
//            }
//        }
        return list ;
    }

}
