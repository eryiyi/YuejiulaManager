<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
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
            <li><a href="javaScript:void(0)">商品管理</a></li>
            <li><a href="javaScript:void(0)">修改商品</a></li>
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
                    <span>修改商品</span>
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
                <h4 class="page-header">商品详情</h4>

                <form id="save_form" class="form-horizontal" method="post" role="form">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">商品名称</label>

                        <div class="col-sm-4">
                            <input type="text" id="goods_title" class="form-control" value="${goods.name}"
                                   placeholder="商品名字" data-toggle="tooltip" data-placement="bottom"
                                   title="Tooltip for name">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">商品类别</label>

                        <div class="col-sm-4">
                            <select class="populate placeholder" name="university" id="goods_type">
                                <option value="">-- 选择商品类别 --</option>
                                <c:forEach items="${list}" var="s">
                                    <option value="${s.typeId}" ${goods.typeId==s.typeId?'selected':''}>${s.typeName}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">宝贝图片</label>

                        <div class="col-sm-10">
                            <input type="file" name="file" id="fileUpload" style="float: left;"/>
                            <input type="button" value="上传" onclick="uploadImage()" style="float: left;"/><br/><br/>

                            <div id="imageDiv" style="padding: 10px">
                                <script type="text/javascript">
                                    var imagePath = '${goods.cover}';
                                    if (imagePath != null && imagePath != "") {
                                        var html = '<img style="cursor: pointer" onmousedown="deleteImage(event, this)" src="' + imagePath + '" width="150" height="150" name="imagePath" title="点击右键删除"/>';
                                        $("#imageDiv").html(html);
                                    }
                                </script>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">销售价格</label>

                        <div class="col-sm-4">
                            <input type="text" id="goods_seller_price" value="${goods.sellPrice}" class="form-control"
                                   placeholder="销售价格" data-toggle="tooltip" data-placement="bottom"
                                   title="Tooltip for name">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">市场价格</label>
                        <div class="col-sm-4">
                            <input type="text" id="goods_market_price" value="${goods.marketPrice}" class="form-control"
                                   placeholder="市场价格" data-toggle="tooltip" data-placement="bottom"
                                   title="Tooltip for name">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">代理价格</label>
                        <div class="col-sm-4">
                            <input type="text" id="daili_price" value="${goods.daili_price}" class="form-control"
                                   placeholder="代理价格" data-toggle="tooltip" data-placement="bottom"
                                   title="Tooltip for name">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">宝贝数量</label>

                        <div class="col-sm-4">
                            <input type="text" id="goods_count" value="${goods.count}" class="form-control"
                                   placeholder="宝贝数量" data-toggle="tooltip" data-placement="bottom"
                                   title="Tooltip for name">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">宝贝介绍</label>

                        <div class="col-sm-8">
                            <script id="editor" name="editor" type="text/plain"
                                    style="width:100%;height:250px;">${goods.cont}</script>
                        </div>
                    </div>
                    <input type="hidden" id="goods_id" value="${goods.id}">

                    <div class="form-group">
                        <label class="col-sm-2 control-label">上架下架</label>

                        <div class="col-sm-4">
                            <select class="form-control" id="isUse">
                                <option value="">--选择--</option>
                                <option value="0" ${goods.isUse=='0'?'selected':''}>上架</option>
                                <option value="1" ${goods.isUse=='1'?'selected':''}>下架</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-sm-2 col-sm-offset-2">
                            <button type="button" class="btn btn-primary" onclick="savePaopaoGoods();">
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

    function savePaopaoGoods() {
        var goodsId = $("#goods_id").val();
        var isUse = $("#isUse").val();
        var goodsTitle = $("#goods_title").val();
        var type = $("#goods_type").val();
        var sellerPrice = $("#goods_seller_price").val();
        var marketPrice = $("#goods_market_price").val();
        var daili_price = $("#daili_price").val();
        var count = $("#goods_count").val();
        var content = UE.getEditor('editor').getContent();
        if (goodsTitle.replace(/\s/g, '') == '') {
            alert("商品名称不能为空");
            return;
        }
        if (type.replace(/\s/g, '') == '') {
            alert("请选择商品类别");
            return;
        }
        var imagePath = $("img[name='imagePath']").attr("src");
        if (imagePath == "") {
            alert("请上传图片");
            return;
        }
        var reg = /(^[-+]?[1-9]\d*(\.\d{1,2})?$)|(^[-+]?[0]{1}(\.\d{1,2})?$)/;
        if (sellerPrice.replace(/\s/g, '') == '') {
            alert("销售价格不能为空");
            return;
        } else {
            if (!reg.test(sellerPrice)) {
                alert("销售价格必须为合法数字(正数，最多两位小数)！");
                return;
            }
        }
        if (marketPrice.replace(/\s/g, '') == '') {
            alert("市场价格不能为空");
            return;
        } else {
            if (!reg.test(marketPrice)) {
                alert("市场价格必须为合法数字(正数，最多两位小数)！");
                return;
            }
        } if (daili_price.replace(/\s/g, '') == '') {
            alert("代理价格不能为空");
            return;
        } else {
            if (!reg.test(daili_price)) {
                alert("代理价格必须为合法数字(正数，最多两位小数)！");
                return;
            }
        }

        var regInt = /^([1-9]\d*)$/;
        if (count.replace(/\s/g, '') == '') {
            alert("商品数量不能为空");
            return;
        } else {
            if (!regInt.test(count)) {
                alert("商品数量必须是正整数，大于0！");
                return;
            }
        }
        if (content.replace(/\s/g, '') == '') {
            alert("商品详细介绍不能为空");
            return;
        }
        if (isUse.replace(/\s/g, '') == '') {
            alert("请选择是否上架");
            return;
        }

        $.ajax({
            cache: true,
            type: "POST",
            url: "/paopaogoods/update.do",
            data: {
                "id": goodsId,
                "name": goodsTitle,
                "isUse": isUse,
                "typeId": type,
                "cover": imagePath,
                "sellPrice": sellerPrice,
                "marketPrice": marketPrice,
                "daili_price": daili_price,
                "address": "",
                "person": "",
                "tel": "",
                "qq": "",
                "count": count,
                "cont": content
            },
            async: false,
            success: function (_data) {
                var data = $.parseJSON(_data);
                if (data.success) {
                    var str = data.data;
                    if (str == null || str == '') {
                        alert("修改成功");
                        window.location.href = "#module=/paopaogoods/list&page=1"+
                        "&_t=" + new Date().getTime();
                    } else {
                        alert(str + " 商品数量已达上限，无法添加商品");
                    }
                } else {
                    var _case = {1: "修改失败"};
                    alert(_case[data.code])
                }
            }
        });
    }

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
</script>


