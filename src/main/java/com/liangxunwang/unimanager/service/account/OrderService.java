package com.liangxunwang.unimanager.service.account;

import com.liangxunwang.unimanager.dao.*;
import com.liangxunwang.unimanager.model.Admin;
import com.liangxunwang.unimanager.model.ManagerInfo;
import com.liangxunwang.unimanager.model.Rate;
import com.liangxunwang.unimanager.model.Settlement;
import com.liangxunwang.unimanager.mvc.vo.OrdersVO;
import com.liangxunwang.unimanager.query.OrdersQuery;
import com.liangxunwang.unimanager.query.SettlementQuery;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.Constants;
import com.liangxunwang.unimanager.util.DateUtil;
import com.liangxunwang.unimanager.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuzh on 2015/8/19.
 */
public class OrderService implements ListService, DeleteService, FindService, UpdateService, ExecuteService {

    @Autowired
    @Qualifier("orderDao")
    private OrderDao orderDao;

    @Autowired
    @Qualifier("rateDao")
    private RateDao rateDao;
    @Autowired
    @Qualifier("adminDao")
    private AdminDao adminDao;

    @Autowired
    @Qualifier("managerInfoDao")
    private ManagerInfoDao infoDao;
    @Override
    public Object delete(Object object) throws ServiceException {

        return null;
    }

    @Override
    public Object list(Object object) throws ServiceException {
        if (object instanceof OrdersQuery){
            OrdersQuery query = (OrdersQuery) object;
            String empId = query.getEmpId();
            int index = (query.getIndex() - 1) * query.getSize();
            int size = query.getIndex() * query.getSize();

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("index", index);
            map.put("size", size);
            if (!StringUtil.isNullOrEmpty(empId)) {
                map.put("empId", empId);
            }
            if (!StringUtil.isNullOrEmpty(query.getEmpName())){
                map.put("empName", query.getEmpName());
            }
            if (!StringUtil.isNullOrEmpty(query.getEmpPhone())){
                map.put("empPhone", query.getEmpPhone());
            }
            if (!StringUtil.isNullOrEmpty(query.getOrderStatus())){
                map.put("orderStatus", query.getOrderStatus());
            }
            if (!StringUtil.isNullOrEmpty(query.getPayStatus())){
                map.put("payStatus", query.getPayStatus());
            }
            if (!StringUtil.isNullOrEmpty(query.getDistribStatus())){
                map.put("distribStatus", query.getDistribStatus());
            }


            List<OrdersVO> list = orderDao.listOrders(map);
            long count = orderDao.count(map);

            return new Object[]{list, count};
        }else if (object instanceof SettlementQuery ){
            SettlementQuery query = (SettlementQuery) object;
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("index", 1);
            map.put("size", Integer.MAX_VALUE);
            map.put("orderStatus", "5");//订单完成状态
            if (!StringUtil.isNullOrEmpty(query.getEmpId())) {
                map.put("empId", query.getEmpId());
            }
            if (!StringUtil.isNullOrEmpty(query.getDate())) {
                Object[] times = DateUtil.getDayInterval(DateUtil.getMs(query.getDate(), "MM/dd/yyyy"), 0);
                map.put("startTime", times[0]);
                map.put("endTime", times[1]);
            }

            List<OrdersVO> list = orderDao.listOrders(map);
            return list;

        }

        return null;
    }

    @Override
    public Object findById(Object object) throws ServiceException {
        if (object instanceof String){
            String orderNo = (String) object;
            return orderDao.findById(orderNo);
        }
        return null;
    }

    @Override
    public Object update(Object object) {

        Long t = Constants.DAY_MILLISECOND*6;

        orderDao.updateOrderStatus(System.currentTimeMillis()+"", t+"");

        //
        return null;
    }

    @Override
    public Object execute(Object object) throws ServiceException {
        if (object instanceof SettlementQuery){
            SettlementQuery query = (SettlementQuery) object;
            Map<String,Object> map = new HashMap<String, Object>();
            if (!StringUtil.isNullOrEmpty(query.getEmpId())){
                map.put("empId", query.getEmpId());
            }
            Rate rate = rateDao.findById("1");
            Object[] results = DateUtil.getDayInterval(DateUtil.getMs(query.getDate(), "MM/dd/yyyy"), 0);
            map.put("startTime", results[0]);
            map.put("endTime", results[1]);
            Settlement settlement = orderDao.settlement(map);
            if (settlement != null) {
                settlement.setDate((String) results[2]);
                settlement.setRate(rate.getRate());
            }

            Admin admin = adminDao.findByEmpId(query.getEmpId());

            ManagerInfo info = infoDao.findById(admin.getId());

            return new Object[]{settlement, info};
        }else if (object instanceof Object[]){
            Object[] params = (Object[]) object;
            String date = (String) params[0];
            String empId = (String) params[1];
            Object[] times = DateUtil.getDayInterval(DateUtil.getMs(date, "MM/dd/yyyy"), 0);
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("empId", empId);
            map.put("startTime", times[0]);
            map.put("endTime", times[1]);
            orderDao.updateOrderAccount(map);
        }
        return null;
    }
}
