package com.liangxunwang.unimanager.data;

import com.liangxunwang.unimanager.model.Order;
import com.liangxunwang.unimanager.model.SellerGoods;

import java.util.List;

/**
 * 商品订单集合
 */
public class OrdersDATA {
    private List<Order> list;

    public List<Order> getList() {
        return list;
    }

    public void setList(List<Order> list) {
        this.list = list;
    }
}
