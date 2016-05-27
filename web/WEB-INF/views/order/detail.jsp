<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="um" uri="/unimanager-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<script type="text/javascript" src="/js/Util.js"></script>
<style>
    form {
        margin: 0;
    }

    textarea {
        display: block;
    }
</style>
<div class="row">
    <div id="breadcrumb" class="col-xs-12">
        <a href="#" class="show-sidebar">
            <i class="fa fa-bars"></i>
        </a>
        <ol class="breadcrumb pull-left">
            <li><a href="javaScript:void(0)">主页</a></li>
            <li><a href="javaScript:void(0)">订单管理</a></li>
            <li><a href="javaScript:void(0)">订单详情</a></li>
        </ol>
        <div id="social" class="pull-right">
            <a href="#"><i class="fa fa-google-plus"></i></a>
            <a href="#"><i class="fa fa-facebook"></i></a>
            <a href="#"><i class="fa fa-twitter"></i></a>
            <a href="#"><i class="fa fa-linkedin"></i></a>
            <a href="#"><i class="fa fa-youtube"></i></a>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-xs-12 col-sm-12">
        <div class="box">
            <div class="box-header">
                <div class="box-name">
                    <i class="fa fa-search"></i>
                    <span>订单详情</span>
                </div>
                <div class="box-icons">
                    <a class="collapse-link">
                        <i class="fa fa-chevron-up"></i>
                    </a>
                    <a class="expand-link">
                        <i class="fa fa-expand"></i>
                    </a>
                    <a class="close-link">
                        <i class="fa fa-times"></i>
                    </a>
                </div>
                <div class="no-move"></div>
            </div>
            <div class="box-content">
                <h4 class="page-header">订单详情</h4>
                <dl class="dl-horizontal">
                    <dt>订单号</dt>
                    <dd>${vo.order_no}</dd>
                </dl>
                <dl class="dl-horizontal">
                    <dt>买家名称</dt>
                    <dd>${vo.empName}</dd>
                </dl>
                <dl class="dl-horizontal">
                    <dt>买家电话</dt>
                    <dd>${vo.phone}</dd>
                </dl>
                <dl class="dl-horizontal">
                    <dt>宝贝名称</dt>
                    <dd>${vo.goodsName}</dd>
                </dl>
                <dl class="dl-horizontal">
                    <dt>购买数量</dt>
                    <dd>${vo.goods_count}</dd>
                </dl>
                <dl class="dl-horizontal">
                    <dt>商品总金额</dt>
                    <dd>${vo.payable_amount}</dd>
                </dl>
                <dl class="dl-horizontal">
                    <dt>下单时间</dt>
                    <dd>${um:format(vo.create_time, "yyyy-MM-dd HH:mm:ss")}</dd>
                </dl>
                <dl class="dl-horizontal">
                    <dt>付款时间</dt>
                    <dd>${um:format(vo.pay_time, "yyyy-MM-dd HH:mm:ss")}</dd>
                </dl>
                <dl class="dl-horizontal">
                    <dt>发货时间</dt>
                    <dd>${um:format(vo.send_time, "yyyy-MM-dd HH:mm:ss")}</dd>
                </dl>
                <dl class="dl-horizontal">
                    <dt>收货时间</dt>
                    <dd>${um:format(vo.accept_time, "yyyy-MM-dd HH:mm:ss")}</dd>
                </dl>
                <dl class="dl-horizontal">
                    <dt>订单状态</dt>
                    <dd>
                        <c:if test="${vo.status=='1'}">订单生成</c:if>
                        <c:if test="${vo.status=='2'}">订单支付</c:if>
                        <c:if test="${vo.status=='3'}">订单取消</c:if>
                        <c:if test="${vo.status=='4'}">订单作废</c:if>
                        <c:if test="${vo.status=='5'}">订单完成</c:if>
                    </dd>
                </dl>

                <dl class="dl-horizontal">
                    <dt>支付状态</dt>
                    <dd>
                        <c:if test="${vo.pay_status=='0'}">未支付</c:if>
                        <c:if test="${vo.pay_status=='1'}">已支付</c:if>
                        <c:if test="${vo.pay_status=='2'}">退款</c:if>
                    </dd>
                </dl>
                <dl class="dl-horizontal">
                    <dt>配送状态</dt>
                    <dd>
                        <c:if test="${vo.distribution_status=='0'}">未发货</c:if>
                        <c:if test="${vo.distribution_status=='1'}">已发货</c:if>
                        <c:if test="${vo.distribution_status=='2'}">已到达</c:if>
                    </dd>
                </dl>
                <dl class="dl-horizontal">
                    <dt>买家留言</dt>
                    <dd>${vo.postscript}</dd>
                </dl>

                <dl class="dl-horizontal">
                    <dt>收货地址</dt>
                    <dd>${vo.provinceName}${vo.cityName}${vo.areaName}${vo.address}</dd>
                </dl>
                <dl class="dl-horizontal">
                    <dt>卖家名称</dt>
                    <dd>${vo.sellerName}</dd>
                </dl>

                <c:if test="${vo.status == '1'}">
                <div class="form-group">
                    <div class="col-sm-2 col-sm-offset-1">
                        <button type="button" class="btn btn-primary" onclick="changeOrder('${vo.order_no}','3');">
                            <span><i class="fa fa-clock-o"></i></span>
                            取消订单
                        </button>
                    </div>
                    </c:if>
                    <c:if test="${vo.distribution_status=='0' && (vo.status=='1' || vo.status=='2')}">
                        <div class="col-sm-2 col-sm-offset-1">
                            <button type="button" class="btn btn-primary" onclick="changeOrder('${vo.order_no}','6');">
                                <span><i class="fa fa-clock-o"></i></span>
                                确认发货
                            </button>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //3 取消订单 6确认发货
    function changeOrder(orderNo, _type) {
        $.ajax({
            url: "/updateOrder.do",
            type: "POST",
            data: {"order_no": orderNo, "status": _type},
            success: function (_data) {
                var data = $.parseJSON(_data);
                if (data.success) {
                    if (_type == '3') {
                        alert("取消订单成功");
                    }
                    if (_type == '6') {
                        alert("已确认发货");
                    }
                    window.location.href = "#module=/order/detail&id=${vo.order_no}&_time=" + new Date().getTime;
                } else {
                    if (_type == '3') {
                        alert("取消订单失败");
                    }
                    if (_type == '6') {
                        alert("确认发货失败");
                    }
                }
            }
        });
    }
    ;
</script>

