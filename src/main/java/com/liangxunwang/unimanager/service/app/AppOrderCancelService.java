package com.liangxunwang.unimanager.service.app;

import com.liangxunwang.unimanager.dao.AppOrderCancelDao;
import com.liangxunwang.unimanager.dao.AppOrderMakeDao;
import com.liangxunwang.unimanager.model.CancelOrder;
import com.liangxunwang.unimanager.model.Order;
import com.liangxunwang.unimanager.model.OrderInfoAndSign;
import com.liangxunwang.unimanager.service.ExecuteService;
import com.liangxunwang.unimanager.service.SaveService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.service.UpdateService;
import com.liangxunwang.unimanager.util.StringUtil;
import com.liangxunwang.unimanager.util.UUIDFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by Administrator on 2015/8/15.
 */
@Service("appOrderCancelService")
public class AppOrderCancelService implements SaveService {

    @Autowired
    @Qualifier("appOrderCancelDao")
    private AppOrderCancelDao appOrderCancelDao;

    @Autowired
    @Qualifier("appOrderMakeDao")
    private AppOrderMakeDao appOrderMakeSaveDao;


    @Override
    public Object save(Object object) throws ServiceException {
        CancelOrder cancelOrder = (CancelOrder) object;
        cancelOrder.setOrder_cancel_no(UUIDFactory.random());
        cancelOrder.setDateline(System.currentTimeMillis() + "");
        //记录取消订单记录
        appOrderCancelDao.save(cancelOrder);
        // 取消订单
        appOrderMakeSaveDao.cancelOrderById(cancelOrder.getOrder_no());
        return null;
    }

}
