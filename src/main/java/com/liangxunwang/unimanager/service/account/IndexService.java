package com.liangxunwang.unimanager.service.account;

import com.liangxunwang.unimanager.dao.AdvertDao;
import com.liangxunwang.unimanager.dao.MemberDao;
import com.liangxunwang.unimanager.dao.OrderDao;
import com.liangxunwang.unimanager.dao.PaopaoGoodsDao;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/3/3.
 */
@Service("indexService")
public class IndexService implements ListService {
    @Autowired
    @Qualifier("memberDao")
    private MemberDao memberDao;


    @Autowired
    @Qualifier("paopaoGoodsDao")
    private PaopaoGoodsDao paopaoGoodsDao;

    @Autowired
    @Qualifier("orderDao")
    private OrderDao orderDao;


    @Override
    public Object list(Object object) throws ServiceException {
        //总共会员数量
        long memberCount = memberDao.memberCount();
        //被关禁闭会员数量
        long closeMemberCount = memberDao.closeMemberCount();
        //今天注册的会员
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("startTime",  DateUtil.getStartDay());
        map1.put("endTime",  DateUtil.getEndDay());
        long memberCountNoDay = memberDao.countDay(map1);

        //----------------商品统计----------------
        //全部商品
        Map<String, Object> map3 = new HashMap<String, Object>();
        long countGoodsAll = paopaoGoodsDao.count(map3);
        //商家的商品
//        map3.put("is_zhiying","0");
//        long countGoods1 = paopaoGoodsDao.count(map3);
        //商城的商品
//        map3.put("is_zhiying","1");
//        long countGoods2 = paopaoGoodsDao.count(map3);
        //--------------订单统计------------------------
        //全部订单
        Map<String, Object> map4 = new HashMap<String, Object>();
        long countOrderAll = orderDao.count(map4);
        //已完成订单
        map4.put("status", "5");
        long countOrderDone = orderDao.count(map4);
        //今日订单统计
        map4.put("startTime",  DateUtil.getStartDay());
        map4.put("endTime",  DateUtil.getEndDay());
        long countOrderDay = orderDao.countDay(map4);

//        //广告数量
//        long advertCont = advertDao.count(new HashMap<String, Object>());
        List<Long> list = new ArrayList<Long>();
        list.add(memberCount);
        list.add(closeMemberCount);
        list.add(memberCountNoDay);

        list.add(countGoodsAll);
        list.add(countOrderAll);
        list.add(countOrderDone);
        list.add(countOrderDay);

        return list;
    }
}
