<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="um" uri="/unimanager-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<div class="row">
    <div id="breadcrumb" class="col-xs-12">
        <a href="#" class="show-sidebar">
            <i class="fa fa-bars"></i>
        </a>
        <ol class="breadcrumb pull-left">
            <li><a href="javaScript:void(0)">主页</a></li>
            <li><a href="javascript:void (0);">圈子管理</a></li>
            <li><a href="javascript:void (0);">添加圈子</a></li>
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
        <div class="box ui-draggable ui-droppable">
            <div class="box-header">
                <div class="box-name ui-draggable-handle">
                    <i class="fa fa-table"></i>
                    <span>添加圈子</span>
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

                <h4 class="page-header">添加圈子</h4>

                <form id="save_form" class="form-horizontal" method="post" role="form">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">圈子名字</label>

                        <div class="col-sm-4">
                            <input type="text" id="name" name="name" class="form-control" placeholder="圈子名字"
                                   data-toggle="tooltip" data-placement="bottom" title="Tooltip for name">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">所属省份</label>

                        <div class="col-sm-4">
                            <select class="populate placeholder" name="university" id="s2_province">
                                <option value="">-- 选择省份 --</option>
                                <c:forEach items="${provinces}" var="s">
                                    <option value="${s.provinceId}">${s.pname}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-sm-2 col-sm-offset-2">
                            <button type="button" class="btn btn-primary" onclick="save();">
                                <span><i class="fa fa-clock-o"></i></span>
                                提交
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
        var name = $("#name").val();
        var s2_province = $("#s2_province").val();
        if (name.replace(/\s/g, '') == '') {
            alert("圈子名不能为空");
            return;
        }
        if (s2_province.replace(/\s/g, '') == '') {
            alert("请选择省份");
            return;
        }

        $.ajax({
            cache: true,
            type: "POST",
            url: "/saveSchools.do",
            data: {"name": name, "provinceID": s2_province},
            async: false,
            success: function (_data) {
                var data = $.parseJSON(_data);
                if (data.success) {
                    alert("添加成功");
                    window.location.href = "#module=listSchools"+ "&_t="+ new Date().getTime();
                } else {
                    var _case = {1: "添加失败"};
                    alert(_case[data.code])
                }
            }
        });
    }
    ;


</script>