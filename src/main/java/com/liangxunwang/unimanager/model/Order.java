package com.liangxunwang.unimanager.model;

/**
 * Created by Administrator on 2015/8/14.
 * 订单表
 */
public class Order {
    private String order_no;//订单号
    private String goods_id;//宝贝ID关联
    private String emp_id;//买家ID
    private String seller_emp_id;//卖家ID
    private String provinceId;//省ID
    private String cityId;//市ID
    private String areaId;//区ID
    private String address_id;//地址ID关联
    private String goods_count;//购买数量
    private float payable_amount;//应付商品总金额
    private String create_time;//下单时间
    private String pay_time;//付款时间
    private String send_time;//发货时间
    private String accept_time;//收货时间
    private String completion_time;//订单完成时间
    private String status;//1生成订单,2支付订单,3取消订单,4作废订单,5完成订单',
    private String pay_status;//支付状态 0：未支付，1：已支付，2：退款'
    private String distribution_type;//0配送 1自取
    private String distribution_status;//配送状态 0：未发送,1：已发送,2：到达
    private String postscript;//用户附言
    private String point;
    private String trade_no;
    private String invoice;
    private String invoice_title;
    private String taxes;
    private String out_trade_no;
    private String schoolId;
    private String isAccount;

    public String getIsAccount() {
        return isAccount;
    }

    public void setIsAccount(String isAccount) {
        this.isAccount = isAccount;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    public String getSeller_emp_id() {
        return seller_emp_id;
    }

    public void setSeller_emp_id(String seller_emp_id) {
        this.seller_emp_id = seller_emp_id;
    }

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    public String getGoods_count() {
        return goods_count;
    }

    public void setGoods_count(String goods_count) {
        this.goods_count = goods_count;
    }

    public float getPayable_amount() {
        return payable_amount;
    }

    public void setPayable_amount(float payable_amount) {
        this.payable_amount = payable_amount;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public String getSend_time() {
        return send_time;
    }

    public void setSend_time(String send_time) {
        this.send_time = send_time;
    }

    public String getAccept_time() {
        return accept_time;
    }

    public void setAccept_time(String accept_time) {
        this.accept_time = accept_time;
    }

    public String getCompletion_time() {
        return completion_time;
    }

    public void setCompletion_time(String completion_time) {
        this.completion_time = completion_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPay_status() {
        return pay_status;
    }

    public void setPay_status(String pay_status) {
        this.pay_status = pay_status;
    }

    public String getDistribution_type() {
        return distribution_type;
    }

    public void setDistribution_type(String distribution_type) {
        this.distribution_type = distribution_type;
    }

    public String getDistribution_status() {
        return distribution_status;
    }

    public void setDistribution_status(String distribution_status) {
        this.distribution_status = distribution_status;
    }

    public String getPostscript() {
        return postscript;
    }

    public void setPostscript(String postscript) {
        this.postscript = postscript;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getInvoice_title() {
        return invoice_title;
    }

    public void setInvoice_title(String invoice_title) {
        this.invoice_title = invoice_title;
    }

    public String getTaxes() {
        return taxes;
    }

    public void setTaxes(String taxes) {
        this.taxes = taxes;
    }
}
