package com.liangxunwang.unimanager.mvc.vo;

import com.liangxunwang.unimanager.model.Order;

/**
 * Created by liuzh on 2015/8/20.
 * 后台订单列表详情
 */
public class OrdersVO extends Order {
    private String empName;//买家名称
    private String sellerName;//卖家名称
    private String phone;//买家电话
    private String provinceName;//省
    private String cityName;//市
    private String areaName;//区
    private String address;//详细地址
    private String goodsName;//宝贝名称

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
