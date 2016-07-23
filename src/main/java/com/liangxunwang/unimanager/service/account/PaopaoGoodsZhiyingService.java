package com.liangxunwang.unimanager.service.account;

import com.liangxunwang.unimanager.dao.CollegeDao;
import com.liangxunwang.unimanager.dao.PaopaoGoodsDao;
import com.liangxunwang.unimanager.model.PaopaoGoods;
import com.liangxunwang.unimanager.mvc.vo.PaopaoGoodsVO;
import com.liangxunwang.unimanager.query.PaopaoGoodsQuery;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.SaveService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.service.UpdateService;
import com.liangxunwang.unimanager.util.StringUtil;
import com.liangxunwang.unimanager.util.UUIDFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuzh on 2015/8/16.
 */
@Service("paopaoGoodsZhiyingService")
public class PaopaoGoodsZhiyingService implements SaveService,ListService,UpdateService{

    @Autowired
    @Qualifier("paopaoGoodsDao")
    private PaopaoGoodsDao paopaoGoodsDao;

    @Autowired
    @Qualifier("collegeDao")
    private CollegeDao collegeDao;

    // 41425f5e51bc4bd98715c3efe888da11|1089|41425f5e51bc4bd98715c3efe888da11|1095|41425f5e51bc4bd98715c3efe888da11|1102
    @Override
    public Object save(Object object) throws ServiceException {
        if (object instanceof Object[]){
            String str = "";
            Object[] params = (Object[]) object;
            PaopaoGoods goods = (PaopaoGoods) params[0];
            String emp_ids_schools = goods.getEmpId();//商家id和学校id

            String[] emp_ids_schoolsAry = emp_ids_schools.split("\\|");

//            String[] schoolAry = new String[emp_ids_schoolsAry.length/2];
//            String[] empIdAry = new String[emp_ids_schoolsAry.length/2];
            List<String> schoolAry = new ArrayList<String>();
            List<String> empIdAry = new ArrayList<String>();
            for(int i=0;i<emp_ids_schoolsAry.length;i++){
                if(i%2 == 0){
                    //偶数
                    empIdAry.add(emp_ids_schoolsAry[i]);
                }else {
                    schoolAry.add(emp_ids_schoolsAry[i]);
                }
            }
            for (int i=0; i<schoolAry.size(); i++){
//                List<PaopaoGoods> list = paopaoGoodsDao.listByEmpSchool(goods.getEmpId(), schoolAry[i]);
//                if (list.size()< Integer.parseInt(count)) {//如果小于限制数量让发布
                    goods.setId(UUIDFactory.random());
                    goods.setIsUse("0");
                    goods.setIsDel("0");
                    goods.setUpTime(System.currentTimeMillis() + "");
                    goods.setSchoolId(schoolAry.get(i));
                    goods.setEmpId(empIdAry.get(i));
                    paopaoGoodsDao.save(goods);
//                }else {
//                    College college = collegeDao.getGroupId(schoolAry[i]);
//                    str+= college.getName()+"  ";
//                }
            }
            return str;
        }
        return null;
    }


    @Override
    public Object list(Object object) throws ServiceException {
            Object[] params = (Object[]) object;
            PaopaoGoodsQuery query = (PaopaoGoodsQuery) params[0];
            String empId = (String) params[1];

            int index = (query.getIndex() - 1) * query.getSize();
            int size = query.getIndex() * query.getSize();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("index", index);
            map.put("size", size);
            if(!StringUtil.isNullOrEmpty(query.getManager_id())){
                map.put("manager_id", query.getManager_id());
            }
            List<PaopaoGoodsVO> list = paopaoGoodsDao.listGoods(map);
            long count = paopaoGoodsDao.count(map);
            return new Object[]{list, count};
    }

    @Override
    public Object update(Object object) {
        Object[] params  = (Object[]) object;
        String goods_id = (String) params[0];
        String is_youhuo = (String) params[1];
        paopaoGoodsDao.updateZhiying(goods_id, is_youhuo);
        return null;
    }

}
