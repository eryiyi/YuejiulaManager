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
            <li><a href="javaScript:void(0)">圈主管理</a></li>
            <li><a href="javaScript:void(0)">分配权限</a></li>
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
                    <span>圈主列表</span>
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
                <label class="col-sm-2 control-label">选择角色</label>

                <div class="col-sm-4">
                    <select class="populate placeholder" id="role_id">
                        <option value="">-- 选择角色 --</option>
                        <c:forEach items="${roles}" var="s">
                            <option value="${s.id}">${s.name}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <div class="col-sm-9 col-sm-offset-3">
                        <button type="button" class="btn btn-primary" onclick="savePer('${empId}')">提交</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">

    function savePer(_empId) {
        var roleId = $("#role_id").val();
        if (roleId == null || roleId == '') {
            alert("请选择角色");
            return;
        }
        $.ajax({
            url: "/updateRole.do",
            data: {"empId": _empId, "roleId": roleId},
            success: function (_data) {
                var data = $.parseJSON(_data);
                if (data.success) {
                    alert("设置权限成功");
                } else {
                    var _case = {1: "保存失败"};
                    alert(_case[data.code])
                }
            }
        });
    }
</script>