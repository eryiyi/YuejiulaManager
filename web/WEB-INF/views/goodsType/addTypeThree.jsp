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
            <li><a href="javascript:void (0);">商城第三方网址</a></li>
            <li><a href="javascript:void (0);">添加商城第三方网址</a></li>
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
                    <span>添加商城第三方网址</span>
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
                <h4 class="page-header">商城第三方网址详情</h4>

                <form class="form-horizontal" role="form">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">第三方网址名称</label>

                        <div class="col-sm-4">
                            <input type="text" id="type_name" class="form-control" placeholder="第三方网址名称"
                                   data-toggle="tooltip" data-placement="bottom" title="Tooltip for name">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">第三方网址介绍</label>

                        <div class="col-sm-6">
                            <input type="text" id="type_content" class="form-control" placeholder="第三方网址介绍"
                                   data-toggle="tooltip" data-placement="bottom" title="Tooltip for name">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">第三方网址链接</label>

                        <div class="col-sm-6">
                            <input type="text" id="lx_goods_type_url" class="form-control" placeholder="第三方网址链接"
                                   data-toggle="tooltip" data-placement="bottom" title="Tooltip for name">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">打开方式</label>

                        <div class="col-sm-4">
                            <select class="form-control" id="istype">
                                <option value="0">软件内部浏览器</option>
                                <option value="1">软件外部浏览器</option>
                            </select>
                        </div>
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
                    <%--<div class="form-group">--%>
                        <%--<label class="col-sm-2 control-label">是否商家</label>--%>

                        <%--<div class="col-sm-2">--%>
                            <%--<div class="toggle-switch toggle-switch-success">--%>
                                <%--<label>--%>
                                    <%--<input id="isBusiness" name="isBusiness" type="checkbox" checked>--%>

                                    <%--<div class="toggle-switch-inner"></div>--%>
                                    <%--<div class="toggle-switch-switch"><i class="fa fa-check"></i></div>--%>
                                <%--</label>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">第三方网址图片</label>
                        <div class="col-sm-10">
                            <input type="file" name="file" id="fileUpload" style="float: left;"/>
                            <input type="button" value="上传" onclick="uploadImage()" style="float: left;"/><br/><br/>

                            <div id="imageDiv" style="padding: 10px"></div>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">发布学校</label>
                        <c:forEach items="${schools}" var="e">
                            <div class="col-sm-2">
                                <input type="checkbox" value="${e.schoolId}" name="schools">${e.schoolName}
                            </div>
                        </c:forEach>
                    </div>

                    <div class="form-group">
                        <div class="col-sm-9 col-sm-offset-3">
                            <button type="button" class="btn btn-primary" onclick="saveAdvert()">提交</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<%--<div class="col-xs-4 col-sm-4">--%>
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
            if (confirm("确定移除该图片吗？")) {
                $(node).remove();
            }
        }
    }
    ;

    function saveAdvert() {
        var typeName = $("#type_name").val();
        if (typeName.replace(/\s/g, '') == '') {
            alert("第三方网址名称不能为空");
            return;
        }
        var typeContent = $("#type_content").val()
        if (typeContent.replace(/\s/g, '') == '') {
            alert("第三方网址介绍不能为空");
            return;
        }

        var lx_goods_type_url = $("#lx_goods_type_url").val()
        if (lx_goods_type_url.replace(/\s/g, '') == '') {
            alert("第三方网址链接不能为空");
            return;
        }

        var istype = $("#istype").val()
        if (istype.replace(/\s/g, '') == '') {
            alert("第三方网址打开类型不能为空");
            return;
        }

        var isUse = '';
        if ($('#isUse').is(':checked')) {
            isUse = '1';
        } else {
            isUse = '0';
        }
//        var isBusiness = '';
//        if ($('#isBusiness').is(':checked')) {
//            isBusiness = '1';
//        } else {
//            isBusiness = '0';
//        }
        var imagePath = $("img[name='imagePath']").attr("src");
        if (imagePath == "") {
            alert("请上传图片");
            return;
        }

        var schools_ary = new Array();

        $('input[name="schools"]:checked').each(function () {
            schools_ary.push($(this).val());//向数组中添加元素
        });
        var schools = schools_ary.join('|');//将数组元素连接起来以构建一个字符串

        if (schools == null || schools == '') {
            alert("请选择要添加第三方网址的学校");
            return;
        }
        $.ajax({
            cache: true,
            type: "POST",
            url: "/addGoodsTypeThree.do",
            data: {
                "typeName": typeName,
                "typeIsBusiness": '0',
                "typeIsUse": isUse,
                "typeContent": typeContent,
                "lx_goods_type_type": '1',
                "lx_goods_type_url": lx_goods_type_url,
                "schools": schools,
                "istype": istype,
                "typeCover": imagePath
            },
            async: false,
            success: function (_data) {
                var data = $.parseJSON(_data);
                if (data.success) {
                    alert("添加成功");
                    window.location.href = "#module=listType&lx_goods_type_type=1";
//            $.ajax({
//                type: "GET",
//                url: "/listType.do",
//                success: function(response){
//                    $("#content").html(response);
//                }
//
//            });
//            history.pushState('', 'New URL: '+"/listType.do", "/#/listType.do");
                } else {
                    var _case = {1: "网址名称不能为空", 2: "网址介绍不能为空", 3: "网址图片不能为空", 4: "您不是圈主，不能设置第三方网址", 5:"保存失败"};
                    alert(_case[data.code])
                }
            }
        });


    }
    ;

</script>


