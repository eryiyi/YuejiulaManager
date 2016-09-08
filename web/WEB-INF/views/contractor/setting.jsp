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
            <li><a href="javaScript:void(0)">我是圈主</a></li>
            <li><a href="javaScript:void(0)">我的商家</a></li>
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
                    <span>商家信息</span>
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
                <h4 class="page-header">商家信息</h4>
                <dl class="dl-horizontal">
                    <dt>商家名称</dt>
                    <dd>${vo.empName}</dd>
                </dl>
                <dl class="dl-horizontal">
                    <dt>商家电话</dt>
                    <dd>${vo.mobile}</dd>
                </dl>
                <dl class="dl-horizontal">
                    <dt>经营学校</dt>
                    <dd>${vo.schoolName}</dd>
                </dl>
                <dl class="dl-horizontal">
                    <dt>到期时间</dt>
                    <dd>${um:format(vo.endTime, "yyyy-MM-dd")}</dd>
                </dl>
                <dl class="dl-horizontal">
                    <dt>可发布商品数量</dt>
                    <dd>${count}</dd>
                </dl>
                <input id="emp_id" type="hidden" value="${vo.empId}">
                <input id="seller_goods_id" type="hidden" value="${vo.id}">
            </div>
        </div>
    </div>

    <div class="col-xs-12 col-sm-12">
        <div class="box">
            <div class="box-header">
                <div class="box-name">
                    <i class="fa fa-search"></i>
                    <span>修改可发布宝贝数量</span>
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
                <h4 class="page-header">可发布宝贝数量</h4>

                <form class="form-horizontal" role="form">
                    <div class="form-group has-feedback">
                        <label class="col-sm-2 control-label">可发布数量</label>

                        <div class="col-sm-2">
                            <input type="text" id="goods_count" name="endTime" class="form-control" placeholder="数量">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-9 col-sm-offset-3">
                            <button type="button" class="btn btn-primary" onclick="updateGoodsCount()">修改</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <div class="col-xs-12 col-sm-12">
        <div class="box">
            <div class="box-header">
                <div class="box-name">
                    <i class="fa fa-search"></i>
                    <span>续费</span>
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
                <h4 class="page-header">续费</h4>

                <form class="form-horizontal" role="form">
                    <div class="form-group has-feedback">
                        <label class="col-sm-2 control-label">到期时间</label>

                        <div class="col-sm-2">
                            <input type="text" id="end_time" name="endTime" class="form-control" placeholder="Date">
                            <span class="fa fa-calendar txt-danger form-control-feedback"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-9 col-sm-offset-3">
                            <button type="button" class="btn btn-primary" onclick="updateEndTime()">修改</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function () {
        $('#end_time').datepicker({setDate: new Date()});
        WinMove();
    });

    function updateGoodsCount() {
        if (confirm("确定要修改可发宝贝数量么？")) {
            var empId = $("#emp_id").val();
            var count = $("#goods_count").val();
            $.ajax({
                url: "/sellerGoodsSetting.do",
                type: "POST",
                data: {"empId": empId, "count": count},
                success: function (_data) {
                    var data = $.parseJSON(_data);
                    if (data.success) {
                        alert("修改成功");
                        window.location.href = "#module=/toSellerGoodsSetting&empId=${vo.empId}&schoolId=${vo.schoolId}&_time=" + new Date().getTime();
                    } else {
                        alert("修改失败");
                    }
                }
            });
        }

    }
    ;

    function updateEndTime() {
        if (confirm("确定要修改到期时间么？")) {
            var id = $("#seller_goods_id").val();
            var endTime = $("#end_time").val();
            $.ajax({
                url: "/sellerGoodsSetting.do",
                type: "POST",
                data: {"id": id, "endTime": endTime},
                success: function (_data) {
                    var data = $.parseJSON(_data);
                    if (data.success) {
                        alert("修改成功");
                        window.location.href = "#module=/toSellerGoodsSetting&empId=${vo.empId}&schoolId=${vo.schoolId}&_time=" + new Date().getTime();
                    } else {
                        alert("修改失败");
                    }
                }
            });
        }
    }
    ;

</script>


