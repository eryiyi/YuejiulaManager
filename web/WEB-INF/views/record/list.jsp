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
            <li><a href="javascript:void (0);">动态管理</a></li>
            <li><a href="javascript:void (0);">动态列表</a></li>
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
                    <span>动态列表</span>
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
                <table class="table">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>发布者</th>
                        <th>发布内容</th>
                        <th>大学</th>
                        <th>赞数量</th>
                        <th>评论数量</th>
                        <th>发布类别</th>
                        <th>发布时间</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${list}" var="e" varStatus="st">
                        <tr>
                            <td>${st.index+1}</td>
                            <td>${e.empName}</td>
                            <td>${e.recordCont}</td>
                            <td>${e.schoolName}</td>
                            <td>${e.zanNum}</td>
                            <td>${e.plNum}</td>
                            <td>${e.school_record_mood_name}</td>
                            <td>${e.dateLine}</td>
                            <td>
                                <button class="btn btn-primary" type="button" onclick="deleteCollege('${e.recordId}')">
                                    删除
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

                <div style="margin-top: 20px;border-top: 1px solid #dedede;padding-bottom:15px; height: 50px">
                    <span style="line-height:28px;margin-top:25px;padding-left:10px; float: left">共${page.count}条/${page.pageCount}页</span>
                    <ul class="pagination" style="padding-left:100px; float: right">
                        <li>
                            <a style="margin-right:20px">每页显示&nbsp;<select name="size" id="size"
                                                                           onchange="nextPage('1')">
                                <option value="10" ${query.size==10?'selected':''}>10</option>
                                <option value="20" ${query.size==20?'selected':''}>20</option>
                                <option value="30" ${query.size==30?'selected':''}>30</option>
                                <option value="100" ${query.size==100?'selected':''}>100</option>
                            </select>&nbsp;条</a>
                        </li>
                        <c:choose>
                            <c:when test="${page.page == 1}">
                                <li><a href="javascript:void(0)">首页</a></li>
                                <li><a href="javascript:void(0)"><span class="left">《</span></a></li>
                            </c:when>
                            <c:otherwise>
                                <li><a href="javascript:void(0);" onclick="nextPage('1')">首页</a></li>
                                <li><a href="javascript:void(0);" onclick="nextPage('${page.page-1}')"><span
                                        class="left">《</span></a></li>
                            </c:otherwise>
                        </c:choose>
                        <li><a style="height: 30px; width: 100px">第<input style="width: 40px;height:20px;" type="text"
                                                                          id="index" name="index"
                                                                          onkeyup="searchIndex(event)"
                                                                          value="${page.page}"/> 页</a></li>

                        <c:choose>
                            <c:when test="${page.page == page.pageCount}">
                                <li><a href="javascript:void(0)"><span class="right">》</span></a></li>
                                <li><a href="javascript:void(0)">末页</a></li>
                            </c:when>
                            <c:otherwise>
                                <li><a href="javascript:void(0);" onclick="nextPage('${page.page+1}')"><span
                                        class="right">》</span></a></li>
                                <li><a href="javascript:void(0);" onclick="nextPage('${page.pageCount}')">末页</a></li>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    function deleteCollege(id) {
        if (!confirm("确定删除该动态？")) {
            return;
        }
        $.ajax({
            cache: true,
            type: "POST",
            url: "/schoolRecordsController/delete.do",
            data: {"id": id},
            async: false,
            success: function (_data) {
                var data = $.parseJSON(_data);
                if (data.success) {
                    alert("删除成功！");
                    window.location.href = "#module=/schoolRecordsController/list.do&page=1" + "&size=10" + "&_t=" + new Date().getTime();
                } else {
                    var _case = {1: "删除失败"};
                    alert(_case[data.code])
                }
            }
        });
    }

    function nextPage(_page) {
        var page = parseInt(_page);
        var size = $("#size").val();
        addCookie("contract_size", size, 36);
        if ((page <= ${page.pageCount} && page >= 1)) {
            window.location.href = "#module=/schoolRecordsController/list.do&page=" + page + "&size=" + size+ "&_t="+ new Date().getTime();
        } else {
            alert("请输入1-${page.pageCount}的页码数");
        }
    }

    function searchIndex(e) {
        if (e.keyCode != 13) return;
        var _index = $("#index").val();
        var size = getCookie("contract_size");
        if (_index <= ${page.pageCount} && _index >= 1) {
            window.location.href = "#module=/schoolRecordsController/list.do&page=" + _index + "&size=" + size+ "&_t="+ new Date().getTime();
        } else {
            alert("请输入1-${page.pageCount}的页码数");
        }
    }

</script>