package com.liangxunwang.unimanager.service.account;

import com.liangxunwang.unimanager.dao.ContractSchoolDao;
import com.liangxunwang.unimanager.dao.OrderDao;
import com.liangxunwang.unimanager.mvc.vo.ContractSchoolVO;
import com.liangxunwang.unimanager.mvc.vo.OrdersVO;
import com.liangxunwang.unimanager.query.IncomeQuery;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.Constants;
import com.liangxunwang.unimanager.util.DateUtil;
import com.liangxunwang.unimanager.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuzh on 2015/8/22.
 */
@Service("incomeService")
public class IncomeService implements ListService {

    @Autowired
    @Qualifier("orderDao")
    private OrderDao orderDao;

    @Autowired
    @Qualifier("contractSchoolDao")
    private ContractSchoolDao schoolDao;
    @Override
    public Object list(Object object) throws ServiceException {
        if (object instanceof IncomeQuery){
            IncomeQuery query = (IncomeQuery) object;
            int index = (query.getIndex() - 1) * query.getSize();
            int size = query.getIndex() * query.getSize();

            Map<String,Object> map = new HashMap<String, Object>();

            map.put("index", index);
            map.put("size", size);
            map.put("orderStatus", "5");
            if (!StringUtil.isNullOrEmpty(query.getStartTime())){
                map.put("startTime", DateUtil.getMs(query.getStartTime(), "MM/dd/yyyy"));
            }else {
                map.put("startTime", DateUtil.getStartDay());
            }
            if (!StringUtil.isNullOrEmpty(query.getEndTime())){
                map.put("endTime",DateUtil.getMs(query.getEndTime(), "MM/dd/yyyy")+ Constants.DAY_MILLISECOND);
            }else {
                map.put("endTime", DateUtil.getEndDay());
            }
            if (!StringUtil.isNullOrEmpty(query.getEmpType())) {
                if ("3".equals(query.getEmpType())) {//商家查询
                    if (!StringUtil.isNullOrEmpty(query.getEmpId())) {
                        map.put("empId", query.getEmpId());
                    }
                }else if ("2".equals(query.getEmpType())){//圈主查询
                    if (!StringUtil.isNullOrEmpty(query.getSchoolId())) {
                        map.put("schoolId", query.getSchoolId());//schoolId不为空，查询该学校的收入
                    }else {//shcoolId为空，查询该圈主下所有学校的收入
                        List<ContractSchoolVO> list = schoolDao.listByEmpId(query.getEmpId());//
                        String[] schools = new String[list.size()];
                        for (int i = 0; i < list.size(); i++) {
                            schools[i] = list.get(i).getSchoolId();
                        }
                        map.put("schools", schools);
                    }
                }
            }

            List<OrdersVO> list = orderDao.listOrders(map);
            Long count = orderDao.count(map);
            Float income = orderDao.income(map);

            return new Object[]{list, count, income};
        }
        return null;
    }


}
