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
            <li><a href="javaScript:void(0)">修改平台</a></li>
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
                    <span>修改平台</span>
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
                <h4 class="page-header">修改平台</h4>

                <form id="save_form" class="form-horizontal" method="post" role="form">
                    <input type="hidden" id="school_three_pingtai_id" name="school_three_pingtai_id"
                           value="${schoolThreeTingtai.school_three_pingtai_id}">
                    <input type="hidden" id="school_three_pingtai_pic" name="school_three_pingtai_pic"
                           value="${schoolThreeTingtai.school_three_pingtai_pic}">

                    <div class="form-group">
                        <label class="col-sm-2 control-label">平台标题</label>

                        <div class="col-sm-4">
                            <input type="text" id="school_three_pingtai_name"
                                   value="${schoolThreeTingtai.school_three_pingtai_name}"
                                   name="school_three_pingtai_name" class="form-control" placeholder="平台标题"
                                   data-toggle="tooltip" data-placement="bottom" title="Tooltip for name">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">平台图标</label>

                        <div class="col-sm-10 col-md-2">
                            <img class="img-thumbnail" name="imagePath" id="imageDiv" style="cursor: pointer"
                                 src="${schoolThreeTingtai.school_three_pingtai_pic}"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label"></label>

                        <div class="col-sm-10">
                            <input type="file" name="file" id="fileUpload" style="float: left;"/>
                            <input type="button" value="上传" onclick="uploadImage('fileUpload','imageDiv')"
                                   style="float: left;"/><br/><br/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">平台域名关键字</label>

                        <div class="col-sm-4">
                            <input type="text" id="school_three_key"
                                   value="${schoolThreeTingtai.school_three_key}"
                                   name="school_three_key" class="form-control" placeholder="平台域名关键字"
                                   data-toggle="tooltip" data-placement="bottom" title="Tooltip for name">
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
    function uploadImage(_fileUpload, _imageDiv) {
        $.ajaxFileUpload(
                {
                    url: "/uploadUnCompressImage.do?_t=" + new Date().getTime(),            //需要链接到服务器地址
                    secureuri: false,//是否启用安全提交，默认为false
                    fileElementId: _fileUpload,                        //文件选择框的id属性
                    dataType: 'json',                                     //服务器返回的格式，可以是json, xml
                    success: function (data, status)  //服务器成功响应处理函数
                    {
                        if (data.success) {
                            document.getElementById(_imageDiv).src = data.data;
                        } else {
                            if (data.code == 1) {
                                alert("上传图片失败");
                            } else if (data.code == 2) {
                                alert("上传图片格式只能为：jpg、png、gif、bmp、jpeg");
                            } else if (data.code == 3) {
                                alert("请选择上传图片");
                            } else {
                                alert("上传失败");
                            }
                        }
                    }
                }
        );
    }

    function deleteImage(e, node) {
        if (e.button == 2) {
            if (confirm("确定移除该图片吗？")) {
                $(node).remove();
            }
        }
    }
    ;

    function save() {
        var school_three_pingtai_id = $("#school_three_pingtai_id").val();
        var school_three_pingtai_name = $("#school_three_pingtai_name").val();
        var school_three_key = $("#school_three_key").val();

        if (school_three_pingtai_name.replace(/\s/g, '') == '') {
            alert("请输入第三方平台名称");
            return;
        }  if (school_three_key.replace(/\s/g, '') == '') {
            alert("请输入第三方平台域名关键字");
            return;
        }

        var imagePath = $("img[name='imagePath']").attr("src");

        if (imagePath == "" || imagePath == null) {
            imagePath = $("#school_three_pingtai_pic").val();
            return;
        }

        $.ajax({
            cache: true,
            type: "POST",
            url: "/updateThreePingtai.do",
            data: {
                "school_three_pingtai_id": school_three_pingtai_id,
                "school_three_pingtai_name": school_three_pingtai_name,
                "school_three_key": school_three_key,
                "school_three_pingtai_pic": imagePath
            },
            async: false,
            success: function (_data) {
                var data = $.parseJSON(_data);
                if (data.success) {
                    alert("修改成功");
                    window.location.href = "#module=listThreePingtai&page=1" + "&_t=" + new Date().getTime();
                } else {
                    var _case = {1: "修改失败"};
                    alert(_case[data.code])
                }
            }
        });
    }
</script>


