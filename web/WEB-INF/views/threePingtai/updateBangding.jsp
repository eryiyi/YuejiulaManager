<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<script type="text/javascript" src="/js/Util.js"></script>

<script type="text/javascript" charset="utf-8" src="/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript" charset="utf-8" src="/ueditor/lang/zh-cn/zh-cn.js"></script>
<link rel="stylesheet" href="/ueditor/themes/default/css/ueditor.css" type="text/css">
<script type="text/javascript" charset="utf-8">
    window.UEDITOR_HOME_URL = location.protocol + '//' + document.domain + (location.port ? (":" + location.port) : "") + "/ueditor/";
</script>
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
            <li><a href="javaScript:void(0)">第三方平台管理</a></li>
            <li><a href="javaScript:void(0)">修改绑定的第三方平台</a></li>
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
                    <span>修改绑定的第三方平台</span>
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
                <h4 class="page-header">修改绑定的第三方平台</h4>

                <form id="save_form" class="form-horizontal" method="post" role="form">

                    <div class="form-group">
                        <label class="col-sm-2 control-label">第三方平台</label>
                        <input type="hidden" id="emp_pingtai_mng_id" name="emp_pingtai_mng_id"
                               value="${schoolThreeTingtai.emp_pingtai_mng_id}">

                        <div class="col-sm-4">
                            <select class="populate placeholder" name="school_three_pingtai_id"
                                    id="school_three_pingtai_id" disabled="disabled">
                                <option value="">-- 选择平台 --</option>
                                <c:forEach items="${listpt}" var="s">
                                    <option value="${s.school_three_pingtai_id}" ${schoolThreeTingtai.school_three_pingtai_id==s.school_three_pingtai_id?'selected':''}>${s.school_three_pingtai_name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">平台链接</label>

                        <div class="col-sm-4">
                            <input type="text" id="pingtai_url" name="school_three_pingtai_name"
                                   value="${schoolThreeTingtai.pingtai_url}" class="form-control" placeholder="平台链接"
                                   data-toggle="tooltip" data-placement="bottom" title="Tooltip for name">
                        </div>
                    </div>


                    <div class="form-group">
                        <div class="col-sm-2 col-sm-offset-2">
                            <button type="button" class="btn btn-primary" onclick="save();">
                                <span><i class="fa fa-clock-o"></i></span>
                                提交
                            </button>
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
    function save() {
        var emp_pingtai_mng_id = $("#emp_pingtai_mng_id").val();
        var school_three_pingtai_id = $("#school_three_pingtai_id").val();

        if (school_three_pingtai_id.replace(/\s/g, '') == '') {
            alert("请选择第三方平台");
            return;
        }

        var pingtai_url = $("#pingtai_url").val();
        if (pingtai_url.replace(/\s/g, '') == '') {
            alert("请输入平台链接");
            return;
        }

        $.ajax({
            cache: true,
            type: "POST",
            url: "/updateThreePingtaiBd.do",
            data: {
                "emp_pingtai_mng_id": emp_pingtai_mng_id,
                "school_three_pingtai_id": school_three_pingtai_id,
                "pingtai_url": pingtai_url
            },
            async: false,
            success: function (_data) {
                var data = $.parseJSON(_data);
                if (data.success) {
                    alert("修改成功");
                    window.location.href = "#module=listThreePingtaiBd&page=1"+ "&_t="+ new Date().getTime();
                } else {
                    var _case = {1: "修改失败"};
                    alert(_case[data.code])
                }
            }
        });
    }
</script>


