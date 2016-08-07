<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<script type="text/javascript" src="/js/Util.js"></script>

<%--<script type="text/javascript" charset="utf-8" src="/ueditor/ueditor.config.js"></script>--%>
<%--<script type="text/javascript" charset="utf-8" src="/ueditor/ueditor.all.min.js"></script>--%>
<%--<script type="text/javascript" charset="utf-8" src="/ueditor/lang/zh-cn/zh-cn.js"></script>--%>
<%--<link rel="stylesheet" href="/ueditor/themes/default/css/ueditor.css" type="text/css">--%>
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
            <li><a href="javaScript:void(0)">学校公告</a></li>
            <li><a href="javaScript:void(0)">添加公告</a></li>
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
                    <span>添加公告表单</span>
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
                <h4 class="page-header">公告详情</h4>

                <form id="save_form" class="form-horizontal" method="post" role="form">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">标题</label>

                        <div class="col-sm-4">
                            <input type="text" id="notice_title" class="form-control" placeholder="标题"
                                   data-toggle="tooltip" data-placement="bottom" title="Tooltip for name">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">内容</label>

                        <div class="col-sm-8">
                            <script id="editor" name="editor" type="text/plain"
                                    style="width:100%;height:250px;"></script>
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
    (function () {
        var editor = UE.getEditor('editor');
        Util.editors.push(editor);
    })();

    function save() {
        var title = $("#notice_title").val();
        var content = UE.getEditor('editor').getContent();
        if (title.replace(/\s/g, '') == '') {
            alert("请输入公告标题");
            return;
        }
        if (content.replace(/\s/g, '') == '') {
            alert("请输入公告内容");
            return;
        }
        $.ajax({
            cache: true,
            type: "POST",
            url: "/saveNotice.do",
            data: {"title": title, "content": content},
            async: false,
            success: function (_data) {
                var data = $.parseJSON(_data);
                if (data.success) {
                    alert("添加成功");
                    window.location.href = "#module=listNotice"+ "&_t="+ new Date().getTime();
                } else {
                    var _case = {1: "添加失败"};
                    alert(_case[data.code])
                }
            }
        });
    }
</script>


