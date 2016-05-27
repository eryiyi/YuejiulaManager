<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<script type="text/javascript" src="/js/Util.js"></script>
<div class="row">
    <div id="breadcrumb" class="col-xs-12">
        <a href="#" class="show-sidebar">
            <i class="fa fa-bars"></i>
        </a>
        <ol class="breadcrumb pull-left">
            <li><a href="javaScript:void(0)">主页</a></li>
            <li><a href="javaScript:void(0)">发现网址管理</a></li>
            <li><a href="javaScript:void(0)">添加网址</a></li>
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
                    <span>添加网址</span>
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
                <h4 class="page-header">添加网址</h4>

                <form id="save_form" class="form-horizontal" method="post" role="form">
                    <input id="token" name="token" hidden="true" value="">
                    <input id="key" name="key" hidden="true" value="">

                    <div class="form-group">
                        <label class="col-sm-2 control-label">标题</label>

                        <div class="col-sm-4">
                            <input type="text" id="title" class="form-control" placeholder="标题" data-toggle="tooltip"
                                   data-placement="bottom" title="Tooltip for name">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">链接</label>

                        <div class="col-sm-8">
                            <textarea id="www_url" class="form-control" placeholder="链接" data-toggle="tooltip"
                                      data-placement="bottom" title="Tooltip for name"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">置顶数字</label>

                        <div class="col-sm-8">
                            <input id="top_num" value="0" class="form-control" placeholder="置顶数字" data-toggle="tooltip"
                                   data-placement="bottom" title="Tooltip for name"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">图片</label>

                        <div class="col-sm-10">
                            <input type="file" name="file" id="fileUpload" style="float: left;"/>
                            <input type="button" value="上传" onclick="uploadImage()" style="float: left;"/>
                            <br/><br/>

                            <div id="imageDiv" style="padding: 10px"></div>
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

    function uploadImage() {
        $.ajaxFileUpload(
                {
                    url: "/uploadUnCompressImage.do?_t=" + new Date().getTime(),            //需要链接到服务器地址
                    secureuri: false,//是否启用安全提交，默认为false
                    fileElementId: 'fileUpload',                        //文件选择框的id属性
                    dataType: 'json',                                     //服务器返回的格式，可以是json, xml
                    success: function (data, status)  //服务器成功响应处理函数
                    {
                        if (data.success) {
                            var html = '<img style="cursor: pointer" onmousedown="deleteImage(event, this)" src="' + data.data + '" width="150" height="150" name="imagePath" title="点击右键删除"/>';
//                  var imageDivHtml = $("#imageDiv").html() + html;
                            $("#imageDiv").html(html);
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
            if (confirm("确定移除该文件吗？")) {
                $(node).remove();
            }
        }
    }
    ;


    function save() {
        var title = $("#title").val();
        if (title.replace(/\s/g, '') == '') {
            alert("标题不能为空");
            return;
        }
        var www_url = $("#www_url").val();
        if (www_url.replace(/\s/g, '') == '') {
            alert("链接不能为空");
            return;
        }

        var top_num = $("#top_num").val();
        if (top_num.replace(/\s/g, '') == '') {
            alert("置顶数字不能为空");
            return;
        }
        var imagePath = $("img[name='imagePath']").attr("src");
        if (imagePath == "") {
            alert("请上传图片");
            return;
        }

        $.ajax({
            cache: true,
            type: "POST",
            url: "/saveFinds.do",
            data: {"title": title, "www_url": www_url, "pic_url": imagePath, "top_num": top_num},// 你的formid
            async: false,
            success: function (_data) {
                var data = $.parseJSON(_data);
                if (data.success) {
                    alert("添加成功");
                    window.location.href = "#module=listSchoolFind&page=1&_t=" + new Date().getTime();
                } else {
                    var _case = {1: "标题不能为空", 2: "网址不能为空", 3: "置顶数字不能为空", 4: "请重新上传图片"};
                    alert(_case[data.code])
                }
            }
        });
    }
    ;
    function isInteger(str) {

        var regu = /^[-]{0,1}[0-9]{1,}$/;

        return regu.test(str);

    }
    ;
</script>

