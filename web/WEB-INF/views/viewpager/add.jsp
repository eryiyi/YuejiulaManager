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
            <li><a href="javaScript:void(0)">商城轮播</a></li>
            <li><a href="javaScript:void(0)">添加轮播</a></li>
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
                    <span>添加轮播</span>
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
                <h4 class="page-header">轮播详情</h4>

                <form class="form-horizontal" role="form">
                    <c:if test="${sessionScope.account.type=='2' || sessionScope.account.type=='1'}">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">轮播类别</label>

                            <div class="col-sm-10">
                                <select class="populate placeholder" id="viewpager_type" onchange="selectType()">
                                    <option value="">-- 选择类别 --</option>
                                    <c:if test="${sessionScope.account.type=='2'}">
                                        <option value="1">PK活动</option>
                                    </c:if>
                                    <c:if test="${sessionScope.account.type=='2'}">
                                        <option value="2">商品详情</option>
                                    </c:if>
                                    <c:if test="${sessionScope.account.type=='1'}">
                                        <option value="3">广告</option>
                                    </c:if>
                                </select>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${sessionScope.account.type=='2' }">
                        <div class="form-group" id="select_school_div">
                            <label class="col-sm-2 control-label">发布学校</label>

                            <div class="col-sm-10">
                                <select class="populate placeholder" id="viewpager_school">
                                    <option value="">-- 选择学校 --</option>
                                    <c:forEach items="${vo}" var="s">
                                        <option value="${s.schoolId}">${s.schoolName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </c:if>

                    <div class="form-group" style="display: none" id="search_goods_div">
                        <label class="col-sm-2 control-label">搜索产品</label>

                        <div class="col-sm-10">
                            <input type="text" id="goods_keyWords" class="form-control" placeholder="产品关键字"/>
                            <input type="button" onclick="searchGoods()" value="查找"/>
                        </div>
                    </div>
                    <div class="form-group" style="display: none" id="select_goods_div">
                        <label class="col-sm-2 control-label">选择产品</label>

                        <div class="col-sm-10" id="goods_list_div">

                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">轮播图片</label>

                        <div class="col-sm-10">
                            <input type="file" name="file" id="fileUpload" style="float: left;"/>
                            <input type="button" value="上传" onclick="uploadImage()" style="float: left;"/><br/><br/>

                            <div id="imageDiv" style="padding: 10px"></div>
                        </div>
                    </div>
                    <div class="form-group" style="display: none" id="pic_url_div">
                        <label class="col-sm-2 control-label">图片链接</label>

                        <div class="col-sm-6">
                            <input type="text" id="ad_net" class="form-control" placeholder="图片链接" data-toggle="tooltip"
                                   data-placement="bottom" title="Tooltip for name">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="viewpager_desc" class="col-sm-2 control-label">轮播介绍</label>

                        <div class="col-sm-10">
                            <textarea id="viewpager_desc" class="form-control" rows="3"
                                      placeholder="轮播介绍..."></textarea>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-sm-9 col-sm-offset-3">
                            <button type="button" class="btn btn-primary" onclick="saveViewpager()">提交</button>
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

    function saveViewpager() {
        var imagePath = $("img[name='imagePath']").attr("src");
        if (imagePath == "") {
            alert(imagePath);
            alert("请上传图片");
            return;
        }
        var type = $("#viewpager_type").val();
        var schoolId = $("#viewpager_school").val();

        var desc = $("#viewpager_desc").val();
        if (type == '1') {//PK活动
            if (schoolId == '' || schoolId == null) {
                alert("请先选择发布学校");
                return
            }
            $.ajax({
                cache: true,
                type: "POST",
                url: "/viewpager/save.do",
                data: {"picAddress": imagePath, "schoolId": schoolId, "desc": desc, "type": type},
                async: false,
                success: function (_data) {
                    var data = $.parseJSON(_data);
                    if (data.success) {
                        alert("添加成功");
                        window.location.href = "#module=/viewpager/list";
                    } else {
                        var _case = {1: "请上传图片", 2: "链接地址不能为空"};
                        alert(_case[data.code])
                    }
                }
            });
        }
        if (type == '2') {//商品详情
            if (schoolId == '' || schoolId == null) {
                alert("请先选择发布学校");
                return
            }

            var goodsId = $('input[name="inlineRadioOptions"]:checked ').val();
            if (goodsId == null || goodsId == '') {
                alert("请选择产品");
                return;
            }
            $.ajax({
                cache: true,
                type: "POST",
                url: "/viewpager/save.do",
                data: {"picAddress": imagePath, "schoolId": schoolId, "goodsId": goodsId, "desc": desc, "type": type},
                async: false,
                success: function (_data) {
                    var data = $.parseJSON(_data);
                    if (data.success) {
                        alert("添加成功");
                        window.location.href = "#module=/viewpager/list";
                    } else {
                        var _case = {1: "请上传图片"};
                        alert(_case[data.code])
                    }
                }
            });
        }
        if (type == '3') {//后台添加广告
            var picUrl = $("#ad_net").val();
            $.ajax({
                cache: true,
                type: "POST",
                url: "/viewpager/save.do",
                data: {"picAddress": imagePath, "picUrl": picUrl, "desc": desc, "type": type},
                async: false,
                success: function (_data) {
                    var data = $.parseJSON(_data);
                    if (data.success) {
                        alert("添加成功");
                        window.location.href = "#module=/viewpager/list";
                    } else {
                        var _case = {1: "请上传图片"};
                        alert(_case[data.code])
                    }
                }
            });
        }
    }
    ;

    function selectType() {
        var type = $("#viewpager_type").val();
        if (type == '1') {//PK活动
            $("#search_goods_div").hide();
            $("#select_goods_div").hide();
//      $("#pic_url_div").hide();
        }
        if (type == '2') {//商品详情
            $("#search_goods_div").show();
            $("#select_goods_div").show();
//      $("#pic_url_div").show();
        }
        if (type == '3') {
            $("#pic_url_div").show();
        }
    }

    function searchGoods() {
        var type = $("#viewpager_type").val();
        if (type == null || type == '') {
            alert("请选择轮播类型");
            return;
        }
        var schoolId = $("#viewpager_school").val();
        if (schoolId == '' || schoolId == null) {
            alert("请先选择发布学校");
            return
        }
        var keyWords = $("#goods_keyWords").val();
        if (keyWords.replace(/\s/g, '') == '') {
            alert("产品关键字不能为空");
            return;
        }

        $.ajax({
            url: '/viewpager/searchGoods.do',
            data: {"cont": keyWords, "schoolId": schoolId},
            type: 'post',
            success: function (_data) {
                var data = $.parseJSON(_data);
                if (data) {
                    var newNode = "";
                    for (var i = 0; i < data.length; i++) {
                        var per = data[i];
                        newNode += "<input type='radio' name='inlineRadioOptions' value='" + per.id + "'>" + per.name;
                    }
                    $("#goods_list_div").append(newNode);
                }
            }
        });
    }
</script>


