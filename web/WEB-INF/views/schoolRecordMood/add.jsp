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
            <li><a href="javascript:void (0);">心情管理</a></li>
            <li><a href="javascript:void (0);">添加心情</a></li>
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
                    <span>添加心情</span>
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

                <h4 class="page-header">添加心情</h4>

                <form id="save_form" class="form-horizontal" method="post" role="form">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">心情标签</label>

                        <div class="col-sm-4">
                            <input type="text" id="school_record_mood_name" name="school_record_mood_name"
                                   class="form-control" placeholder="心情标签" data-toggle="tooltip" data-placement="bottom"
                                   title="Tooltip for name">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">心情标签</label>

                        <div class="col-sm-4">
                            <input type="text" id="top_num" name="top_num" class="form-control" value="0"
                                   placeholder="置顶数字，越大越靠前" data-toggle="tooltip" data-placement="bottom"
                                   title="Tooltip for name">
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
        var school_record_mood_name = $("#school_record_mood_name").val();
        var top_num = $("#top_num").val();
        if (school_record_mood_name.replace(/\s/g, '') == '') {
            alert("心情标签不能为空");
            return;
        }


        $.ajax({
            cache: true,
            type: "POST",
            url: "/saveSchoolRecordMood.do",
            data: {"school_record_mood_name": school_record_mood_name, "top_num": top_num},
            async: false,
            success: function (_data) {
                var data = $.parseJSON(_data);
                if (data.success) {
                    alert("添加成功");
                    window.location.href = "#module=listSchoolRecordMood&page=1&size=10";
                } else {
                    var _case = {1: "添加失败"};
                    alert(_case[data.code])
                }
            }
        });
    }
    ;


</script>