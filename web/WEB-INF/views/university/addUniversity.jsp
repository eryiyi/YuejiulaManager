<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<script type="text/javascript" src="/js/Util.js"></script>
<div class="row">
    <div id="breadcrumb" class="col-xs-12">
        <a href="#" class="show-sidebar">
            <i class="fa fa-bars"></i>
        </a>
        <ol class="breadcrumb pull-left">
            <li><a href="javaScript:void(0)">主页</a></li>
            <li><a href="javaScript:void(0)">圈子管理</a></li>
            <li><a href="javaScript:void(0)">添加圈子</a></li>
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
                    <span>添加圈子表单</span>
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
                <h4 class="page-header">圈子信息</h4>

                <form id="save_form" class="form-horizontal" method="post" role="form">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">圈子名称</label>

                        <div class="col-sm-4">
                            <input type="text" id="universityName" name="universityName" class="form-control"
                                   placeholder="圈子名称" data-toggle="tooltip" data-placement="bottom"
                                   title="Tooltip for name">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">所在地区</label>

                        <div class="col-sm-4" id="region"></div>
                        <script type="text/javascript">
                            var district = new District("region");
                            district.initSelectProvince();
                            district.setRegion('110101');
                        </script>
                        <input type="hidden" id="regionCode" name=regionCode>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">是否禁用</label>

                        <div class="col-sm-2">
                            <div class="toggle-switch toggle-switch-success">
                                <label>
                                    <input id="isUse" name="isUse" type="checkbox">

                                    <div class="toggle-switch-inner"></div>
                                    <div class="toggle-switch-switch"><i class="fa fa-check"></i></div>
                                </label>
                            </div>
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
        $("#regionCode").val(district.getRegion());
        var schoolName = $("#universityName").val().replace(/\s/g, '');
        var regionCode = $("#regionCode").val();
        var isUse = '';
        if ($('#isUse').is(':checked')) {
            isUse = '1';
        } else {
            isUse = '0';
        }
        if (schoolName == '') {
            alert("圈子名称不能为空");
            return;
        }
        if (regionCode == '') {
            alert("请选择圈子所在地区");
            return;
        }
        $.ajax({
            cache: true,
            type: "POST",
            url: "/saveUniversity.do",
            data: {"isUse": isUse, "schoolName": schoolName, "regionCode": regionCode},
            async: false,
            success: function (_data) {
                var data = $.parseJSON(_data);
                if (data.success) {
                    alert("添加成功");
                    window.location.href = "#module=ajax/listUniversity&page=1"+ "&_t="+ new Date().getTime();
                } else {
                    var _case = {1: "圈子名称不能为空", 2: "请选择圈子地区", 3: "添加失败", 4: "该圈子已添加"};
                    alert(_case[data.code])
                }
            }
        });
    }
    ;
</script>


