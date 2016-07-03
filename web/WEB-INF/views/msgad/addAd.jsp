<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="um" uri="/unimanager-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<div class="row">
    <div id="breadcrumb" class="col-xs-12">
        <a href="#" class="show-sidebar">
            <i class="fa fa-bars"></i>
        </a>
        <ol class="breadcrumb pull-left">
            <li><a href="javascript:void(0)" onclick="toPage('mainPage','')">主页</a></li>
            <li><a href="javascript:void(0)">承包商广告语管理</a></li>
            <li><a href="javascript:void(0)">承包商广告语管理添加</a></li>
        </ol>
        <div id="social" class="pull-right">
            <a href="javascript:void(0)"><i class="fa fa-google-plus"></i></a>
            <a href="javascript:void(0)"><i class="fa fa-facebook"></i></a>
            <a href="javascript:void(0)"><i class="fa fa-twitter"></i></a>
            <a href="javascript:void(0)"><i class="fa fa-linkedin"></i></a>
            <a href="javascript:void(0)"><i class="fa fa-youtube"></i></a>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-xs-12 col-sm-12">
        <div class="box">
            <div class="box-header">
                <div class="box-name">
                    <i class="fa fa-search"></i>
                    <span>承包商广告语管理添加</span>
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
                <h4 class="page-header">承包商广告语管理添加</h4>

                <form class="form-horizontal" role="form">

                    <div class="form-group">
                        <label class="col-sm-2 control-label">广告语</label>

                        <div class="col-sm-4">
                            <input type="text" id="msg_ad_title" class="form-control" placeholder="广告语"
                                   data-toggle="tooltip" data-placement="bottom" title="Tooltip for name">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">发布学校</label>
                        <c:forEach items="${schools}" var="e">
                            <div class="col-sm-2">
                                <input type="checkbox" value="${e.schoolId}" name="schools">${e.schoolName}
                            </div>
                        </c:forEach>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-9 col-sm-offset-3">
                            <button type="button" class="btn btn-primary" onclick="saveP()">添加</button>
                            <button type="button" class="btn btn-primary" onclick="javascript :history.back(-1)">返回
                            </button>
                        </div>
                    </div>

                </form>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    function saveP() {
        var msg_ad_title = $("#msg_ad_title").val();
        var schools_ary = new Array();

        $('input[name="schools"]:checked').each(function () {
            schools_ary.push($(this).val());//向数组中添加元素
        });
        var schools = schools_ary.join('|');//将数组元素连接起来以构建一个字符串

        if (msg_ad_title.replace(/\s/g, '') == '') {
            alert("承包商广告语不能为空");
            return;
        }

        if (schools == null || schools == '') {
            alert("请选择广告语要发布的学校");
            return;
        }
        $.ajax({
            cache: true,
            type: "POST",
            url: "/msgAdController/addAd.do",
            data: {
                "msg_ad_title": msg_ad_title,
                "schools": schools
            },
            async: false,
            success: function (_data) {
                var data = $.parseJSON(_data);
                if (data.success) {
                    alert("执行成功");
                    window.location.href = "#module=msgAdController/list"+ "&_t="+ new Date().getTime();
                } else {
                    var _case = {1: "添加失败，请检查", 2: "该学校已经添加广告语，请删除之后再添加！"};
                    alert(_case[data.code])
                }
            }
        });
    }
</script>
