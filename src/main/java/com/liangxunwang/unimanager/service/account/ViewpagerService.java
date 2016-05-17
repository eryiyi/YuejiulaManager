package com.liangxunwang.unimanager.service.account;

import com.liangxunwang.unimanager.dao.ContractSchoolDao;
import com.liangxunwang.unimanager.dao.SellerGoodsDao;
import com.liangxunwang.unimanager.dao.ViewpagerDao;
import com.liangxunwang.unimanager.model.SellerGoods;
import com.liangxunwang.unimanager.model.Viewpager;
import com.liangxunwang.unimanager.mvc.vo.ContractSchoolVO;
import com.liangxunwang.unimanager.mvc.vo.ViewpagerVO;
import com.liangxunwang.unimanager.query.ViewpagerQuery;
import com.liangxunwang.unimanager.service.DeleteService;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.SaveService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.Constants;
import com.liangxunwang.unimanager.util.StringUtil;
import com.liangxunwang.unimanager.util.UUIDFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.swing.text.View;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuzh on 2015/8/26.
 */
@Service("viewpagerService")
public class ViewpagerService implements ListService, SaveService, DeleteService {

    @Autowired
    @Qualifier("contractSchoolDao")
    private ContractSchoolDao schoolDao;

    @Autowired
    @Qualifier("viewpagerDao")
    private ViewpagerDao viewpagerDao;
    @Override
    public Object delete(Object object) throws ServiceException {
        if (object instanceof String){
            String id = (String) object;
            viewpagerDao.delete(id);
        }
        return null;
    }

    @Override
    public Object list(Object object) throws ServiceException {
        if (object instanceof ViewpagerQuery){
            ViewpagerQuery query = (ViewpagerQuery) object;
            Map<String,Object> map = new HashMap<String, Object>();
            int[] schoolIds = null;
            if (!StringUtil.isNullOrEmpty(query.getType())) {//网页端请求
                if (!"1".equals(query.getType())) {//不是管理员查询所有轮播
                    if (!StringUtil.isNullOrEmpty(query.getEmpId())) {
                        List<ContractSchoolVO> list = schoolDao.listByEmpId(query.getEmpId());
                        if (list.size()>0) {
                            schoolIds = new int[list.size()];
                            for (int i = 0; i < list.size(); i++) {
                                schoolIds[i] = Integer.parseInt(list.get(i).getSchoolId().trim());
                            }
                        }
                    }
                } else {//是管理员查询轮播
                    //无需添加查询条件
                }
            }else {//手机端请求
                schoolIds = new int[]{Integer.parseInt(query.getSchoolId()==null?"0":query.getSchoolId())};
            }
            if (schoolIds != null) {
                map.put("schoolIds", schoolIds);
            }
            List<ViewpagerVO> list =viewpagerDao.list(map);
            for (ViewpagerVO viewpager : list){
                if (!StringUtil.isNullOrEmpty(viewpager.getPicAddress())) {
                    viewpager.setPicAddress(Constants.URL + viewpager.getPicAddress());
                }
            }
            return list;
        }else {
            return viewpagerDao.list(null);
        }
    }

    @Override
    public Object save(Object object) throws ServiceException {
        if (object instanceof Viewpager){
            Viewpager viewpager = (Viewpager) object;
            viewpager.setId(UUIDFactory.random());
            viewpagerDao.save(viewpager);
        }
        return null;
    }
}
