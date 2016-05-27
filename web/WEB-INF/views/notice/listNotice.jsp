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
            <li><a href="javaScript:void(0)">公告管理</a></li>
            <li><a href="javaScript:void(0)">公告列表</a></li>
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
                    <span>公告列表</span>
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
                <%--<p>For basic styling add the base class <code>.table</code> to any <code>&lt;table&gt;</code>.</p>--%>
                <table class="table">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>标题</th>
                        <th>发布时间</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${list}" var="e" varStatus="st">
                        <tr>
                            <td>${st.index + 1}</td>
                            <td>${e.title}</td>
                            <td>${um:format(e.dateline, "yyyy-MM-dd HH:mm:ss")}</td>
                            <td>
                                <button class="btn btn-info" type="button" onclick="toUpdate('${e.id}')">修改</button>
                                <button class="btn btn-primary" type="button" onclick="deleteUniversity('${e.id}')">删除
                                </button>
                                <a href="/viewNotice.do?noticeId=${e.id}" target="_blank">查看</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    function toUpdate(_id) {
        $.ajax({
            type: "GET",
            data: {"noticeId": _id},
            url: "/toUpdateNotice.do",
            success: function (response) {
                $("#content").html(response);
            }

        });
//    history.pushState('', 'New URL: '+"/toUpdateNotice.do", "/#/toUpdateNotice.do?noticeId="+_id);
    }

    function deleteUniversity(_noticeId) {
        if (!confirm("确定要删除该公告么？")) {
            return;
        }
        $.ajax({
            type: "post",
            data: {"noticeId": _noticeId},
            url: "/deleteNotice.do",
            success: function (_data) {
                var data = $.parseJSON(_data);
                if (data.success) {
                    alert("删除成功");
                    window.location.href = "#module=listNotice" + "&_t=" + new Date().getTime();
//          $.ajax({
//            type: "GET",
//            url: "/listNotice.do",
//            success: function(response){
//              $("#content").html(response);
//            }
//
//          });
//          history.pushState('', 'New URL: '+"/listNotice.do", "/#/listNotice.do");
                } else {
                    var _case = {1: "删除失败"};
                    alert(_case[data.code])
                }
            }
        });
    }
</script>
