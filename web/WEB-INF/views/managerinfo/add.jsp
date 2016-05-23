<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="um" uri="/unimanager-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<link rel="stylesheet" href="http://cache.amap.com/lbs/static/main.css?v=1.0"/>
<script type="text/javascript"
        src="http://webapi.amap.com/maps?v=1.3&key=6d8bfe3105c44dc4a54a930e322eaeec"></script>


<div class="row">
  <div id="breadcrumb" class="col-xs-12">
    <a href="#" class="show-sidebar">
      <i class="fa fa-bars"></i>
    </a>
    <ol class="breadcrumb pull-left">
      <li><a href="javascript:void (0);">主页</a></li>
      <li><a href="javascript:void (0);">系统管理</a></li>
      <li><a href="javascript:void (0);">信息维护</a></li>
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
          <span>信息维护</span>
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
        <h4 class="page-header">信息详情</h4>
        <form class="form-horizontal" role="form">
          <div class="form-group">
            <label class="col-sm-2 control-label">真实姓名</label>
              <div class="col-sm-4">
                  <input type="text" id="real_name" value="${info.realName}" class="form-control" placeholder="真实姓名" data-toggle="tooltip" data-placement="bottom" title="Tooltip for name">
              </div>
          </div>
          <div class="form-group">
            <label class="col-sm-2 control-label">身份证号</label>
            <div class="col-sm-4">
              <input type="text" id="idcard" value="${info.idcard}" class="form-control" placeholder="身份证号" data-toggle="tooltip" data-placement="bottom" title="Tooltip for name">
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-2 control-label" >身份证正面图</label>
            <div class="col-sm-10">
              <input type="file" name="file" id="fileUpload" style="float: left;" />
              <input type="button" value="上传" onclick="uploadImage()" style="float: left;"/><br/><br/>
              <div id="imageDiv" style="padding: 10px">
                <script type="text/javascript">
                  var imagePath = '${info.idcardUrl}';
                  if(imagePath != null && imagePath != "") {
                    var html = '<img style="cursor: pointer" onmousedown="deleteImage(event, this)" src="'+imagePath+'" width="150" height="150" name="imagePath" title="点击右键删除"/>';
                    $("#imageDiv").html(html);
                  }</script>
              </div>
            </div>
          </div>
          <%--<div class="form-group" >--%>
            <%--<label class="col-sm-2 control-label">支付宝账号</label>--%>
            <%--<div class="col-sm-4">--%>
              <%--<input type="text" id="pay_number" value="${info.payNumber}" class="form-control" placeholder="支付宝账号" data-toggle="tooltip" data-placement="bottom" title="Tooltip for name">--%>
            <%--</div>--%>
          <%--</div>--%>
          <%--<div class="form-group">--%>
            <%--<label class="col-sm-2 control-label">支付宝校验姓名</label>--%>
            <%--<div class="col-sm-4">--%>
              <%--<input type="text" id="check_name"value="${info.checkName}" class="form-control" placeholder="支付宝校验姓名" data-toggle="tooltip" data-placement="bottom" title="Tooltip for name">--%>
            <%--</div>--%>
          <%--</div><div class="form-group">--%>
          <%--<label class="col-sm-2 control-label">银行卡号</label>--%>
          <%--<div class="col-sm-4">--%>
            <%--<input type="text" id="bank_card" value="${info.bankCard}" class="form-control" placeholder="银行卡号" data-toggle="tooltip" data-placement="bottom" title="Tooltip for name">--%>
          <%--</div>--%>
        <%--</div><div class="form-group">--%>
          <%--<label class="col-sm-2 control-label">开户行</label>--%>
          <%--<div class="col-sm-4">--%>
            <%--<input type="text" id="bank_type" value="${info.bankType}" class="form-control" placeholder="如:建设银行" data-toggle="tooltip" data-placement="bottom" title="Tooltip for name">--%>
          <%--</div>--%>
        <%--</div><div class="form-group">--%>
          <%--<label class="col-sm-2 control-label">开户行地址</label>--%>
          <%--<div class="col-sm-4">--%>
            <%--<input type="text" id="bank_address" value="${info.bankAddress}" class="form-control" placeholder="开户行的详细地址" data-toggle="tooltip" data-placement="bottom" title="Tooltip for name">--%>
          <%--</div>--%>
        <%--</div>--%>
        <%--<div class="form-group">--%>
          <%--<label class="col-sm-2 control-label">开户人姓名</label>--%>
          <%--<div class="col-sm-4">--%>
            <%--<input type="text" id="bank_name" value="${info.bankName}" class="form-control" placeholder="银行卡持有者姓名" data-toggle="tooltip" data-placement="bottom" title="Tooltip for name">--%>
          <%--</div>--%>
        <%--</div>--%>
        <%--<div class="form-group">--%>
          <%--<label class="col-sm-2 control-label">联系电话</label>--%>
          <%--<div class="col-sm-4">--%>
            <%--<input type="text" id="mobile" value="${info.mobile}" class="form-control" placeholder="联系电话" data-toggle="tooltip" data-placement="bottom" title="Tooltip for name">--%>
          <%--</div>--%>
        <%--</div>--%>

          <div class="form-group">
            <label class="col-sm-2 control-label">*店铺名称*</label>
            <div class="col-sm-4">
              <input type="text" id="company_name" placeholder="店铺名称" class="form-control" value="${info.company_name}" data-toggle="tooltip" data-placement="bottom" title="Tooltip for name">
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-2 control-label">*店铺联系人*</label>
            <div class="col-sm-4">
              <input type="text" id="company_person" placeholder="店铺联系人" class="form-control" value="${info.company_person}" data-toggle="tooltip" data-placement="bottom" title="Tooltip for name">
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-2 control-label">*店铺电话*</label>
            <div class="col-sm-4">
              <input type="text" id="company_tel" placeholder="店铺电话" class="form-control" value="${info.company_tel}" data-toggle="tooltip" data-placement="bottom" title="Tooltip for name">
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-2 control-label">*店铺地址*</label>
            <div class="col-sm-4">
              <input type="text" id="company_address" placeholder="店铺地址" class="form-control" value="${info.company_address}" data-toggle="tooltip" data-placement="bottom" title="Tooltip for name">
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-2 control-label">*店铺介绍*</label>
            <div class="col-sm-4">
              <input type="text" id="company_detail" placeholder="店铺介绍" class="form-control" value="${info.company_detail}" data-toggle="tooltip" data-placement="bottom" title="Tooltip for name">
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-2 control-label">*公司经纬度*</label>
            <div class="col-sm-4">
              <input type="text" id="lat_company" placeholder="公司经度" class="form-control" value="${info.lat_company}" data-toggle="tooltip" data-placement="bottom" title="Tooltip for name">
              <input type="text" id="lng_company" placeholder="公司纬度" class="form-control" value="${info.lng_company}" data-toggle="tooltip" data-placement="bottom" title="Tooltip for name">
            </div>
          </div>


          <div class="form-group">
            <label class="col-sm-2 control-label">*营业时间*</label>
            <div class="col-sm-4">
              <input type="text" id="yingye_time_start" placeholder="开始营业时间：例如 早8点" class="form-control" value="${info.yingye_time_start}" data-toggle="tooltip" data-placement="bottom" title="Tooltip for name">
              <input type="text" id="yingye_time_end" placeholder="结束营业时间：例如 晚9点" class="form-control" value="${info.yingye_time_end}" data-toggle="tooltip" data-placement="bottom" title="Tooltip for name">
            </div>
          </div>

          <div class="form-group">
            <label class="col-sm-2 control-label">*优惠承诺*</label>
            <div class="col-sm-4">
              <input type="text" id="shouhui" placeholder="优惠承诺" class="form-control" value="${info.shouhui}" data-toggle="tooltip" data-placement="bottom" title="Tooltip for name">
            </div>
          </div>

          <%--<div class="form-group">--%>
            <%--<label class="col-sm-2 control-label" ></label>--%>
            <%--<div class="col-sm-10 col-md-2">--%>
              <%--<img class="img-thumbnail" name="" id="company_pic"   style="cursor: pointer"  src="${info.company_pic}"/>--%>
            <%--</div>--%>
          <%--</div>--%>
          <input type="hidden" id="company_pic" name="company_pic" value="${info.company_pic}">
          <div class="form-group">
            <label class="col-sm-2 control-label" >店铺主图</label>
            <div class="col-sm-10 col-md-2">
              <img class="img-thumbnail" name="imagePath1" id="imageDiv1"   style="cursor: pointer"  src="${info.company_pic}"/>
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-2 control-label" ></label>
            <div class="col-sm-10">
              <input type="file" name="file" id="fileUpload1" style="float: left;" />
              <input type="button" value="上传" onclick="uploadImageT('fileUpload1','imageDiv1')" style="float: left;"/><br/><br/>
            </div>
          </div>

          <input type="hidden" id="info_id" value="${info.id}">

          <div class="form-group">
            <div class="col-sm-9 col-sm-offset-3">
              <button type="button" class="btn btn-primary" onclick="saveManagerInfo()">更新</button>
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>
  <div class="col-xs-12 col-sm-12">
    <div class="box">
      <div class="box-header">
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
      <div class="box-content" style="height: 500px">
        <h4 class="page-header">地图</h4>
        <div id="mapContainer"></div>
      </div>
    </div>
  </div>

</div>
<script type="text/javascript">
  function saveManagerInfo(){
    var infoId=$("#info_id").val();
    var realName = $("#real_name").val();
    if(realName.replace(/\s/g, '') == ''){
        alert("请填写真实姓名");
        return;
    }
    if(realName.length > 20){
      alert("请填写真实姓名");
      return;
    }
    var idcard = $("#idcard").val();
    if(idcard.replace(/\s/g, '') == ''){
        alert("请填写身份证号");
        return;
    }
    if(idcard.length != 18){
      alert("请填写身份证号!18位");
      return;
    }
    var imagePath = $("img[name='imagePath']").attr("src");
    if(imagePath== ""){
      alert("请上传身份证图片");
      return;
    }
    var company_name= $("#company_name").val();
    if(company_name.replace(/\s/g, '') == ''){
        alert("请填写店铺名称");
        return;
    }
    if(company_name.length > 25){
      alert("店铺名称超出字段限制");
      return;
    }
    var company_person= $("#company_person").val();
    if(company_person.replace(/\s/g, '') == ''){
      alert("请填写店铺联系人");
      return;
    }
    if(company_person.length > 20){
      alert("联系人超出字段限制");
      return;
    }
    var company_tel= $("#company_tel").val();
    if(company_tel.replace(/\s/g, '') == ''){
      alert("请填写店铺电话");
      return;
    }
    if(company_tel.length > 20){
      alert("店铺电话超出字段限制");
      return;
    }
    var company_address= $("#company_address").val();
    if(company_address.replace(/\s/g, '') == ''){
      alert("请填写店铺地址");
      return;
    }
    if(company_address.length > 50){
      alert("店铺地址超出字段限制");
      return;
    }
    var company_detail= $("#company_detail").val();
    if(company_detail.replace(/\s/g, '') == ''){
      alert("请填写店铺介绍");
      return;
    }
    if(company_detail.length > 1000){
      alert("店铺介绍超出字段限制");
      return;
    }
    var lat_company= $("#lat_company").val();
    if(lat_company.replace(/\s/g, '') == ''){
      alert("请选择店铺位置");
      return;
    }
    var lng_company= $("#lng_company").val();
    if(lng_company.replace(/\s/g, '') == ''){
      alert("请选择店铺位置");
      return;
    }

    var yingye_time_start= $("#yingye_time_start").val();
    if(yingye_time_start.replace(/\s/g, '') == ''){
      alert("请选择开始营业时间");
      return;
    }
    var yingye_time_end= $("#yingye_time_end").val();
    if(yingye_time_end.replace(/\s/g, '') == ''){
      alert("请选择结束营业时间");
      return;
    }
    var shouhui= $("#shouhui").val();
    if(shouhui.replace(/\s/g, '') == ''){
      alert("请填写优惠承诺");
      return;
    }
    if(shouhui.length > 100){
      alert("优惠承诺超出字段限制");
      return;
    }

    var imagePath1 = $("img[name='imagePath1']").attr("src");

    if(imagePath1== "" || imagePath1==null){
      imagePath1 = $("#company_pic").val();
      return;
    }

    $.ajax({
      cache: true,
      type: "POST",
      url:"/managerinfo/save.do",
      data:{"id":infoId, "realName":realName, "idcard":idcard,
        "idcardUrl":imagePath, "company_name":company_name,
        "company_person":company_person, "company_tel":company_tel,
        "company_address":company_address, "company_detail":company_detail,
        "yingye_time_end":yingye_time_end, "shouhui":shouhui,
        "yingye_time_start":yingye_time_start,
        "company_pic":imagePath1,
        "lat_company":lat_company, "lng_company":lng_company},

      async: false,
      success: function(_data) {
        var data = $.parseJSON(_data);
        if(data.success){
          alert("信息维护成功");
            window.location.href = "#module=/managerinfo/add";
        }else{
          var _case = {1:"类别名称不能为空",2:"保存失败"};
          alert(_case[data.code])
        }
      }
    });
  };

  function uploadImage() {
    $.ajaxFileUpload(
            {
              url:"/uploadUnCompressImage.do?_t=" + new Date().getTime(),            //需要链接到服务器地址
              secureuri:false,//是否启用安全提交，默认为false
              fileElementId:'fileUpload',                        //文件选择框的id属性
              dataType: 'json',                                     //服务器返回的格式，可以是json, xml
              success: function (data, status)  //服务器成功响应处理函数
              {
                if(data.success) {
                  var html = '<img style="cursor: pointer" onmousedown="deleteImage(event, this)" src="'+data.data+'" width="150" height="150" name="imagePath" title="点击右键删除"/>';
//                  var imageDivHtml = $("#imageDiv").html() + html;
                  $("#imageDiv").html(html);
                } else {
                  if(data.code == 1) {
                    alert("上传图片失败");
                  } else if(data.code == 2) {
                    alert("上传图片格式只能为：jpg、png、gif、bmp、jpeg");
                  } else if(data.code == 3) {
                    alert("请选择上传图片");
                  }else {
                    alert("上传失败");
                  }
                }
              }
            }
    );
  }

  function uploadImageT(_fileUpload,_imageDiv) {
    $.ajaxFileUpload(
            {
              url:"/uploadUnCompressImage.do?_t=" + new Date().getTime(),            //需要链接到服务器地址
              secureuri:false,//是否启用安全提交，默认为false
              fileElementId:_fileUpload,                        //文件选择框的id属性
              dataType: 'json',                                     //服务器返回的格式，可以是json, xml
              success: function (data, status)  //服务器成功响应处理函数
              {
                if(data.success) {
                  document.getElementById(_imageDiv).src= data.data;
                } else {
                  if(data.code == 1) {
                    alert("上传图片失败");
                  } else if(data.code == 2) {
                    alert("上传图片格式只能为：jpg、png、gif、bmp、jpeg");
                  } else if(data.code == 3) {
                    alert("请选择上传图片");
                  }else {
                    alert("上传失败");
                  }
                }
              }
            }
    );
  }
</script>

<script type="text/javascript">
  //初始化地图对象，加载地图
  ////初始化加载地图时，若center及level属性缺省，地图默认显示用户当前城市范围
  var map = new AMap.Map('mapContainer', {
    resizeEnable: true
  });
  //地图中添加地图操作ToolBar插件
  map.plugin(['AMap.ToolBar'], function() {
    //设置地位标记为自定义标记
    var toolBar = new AMap.ToolBar();
    map.addControl(toolBar);
  });

  var mapObj = new AMap.Map("mapContainer",{
    rotateEnable:true,
    dragEnable:true,
    zoomEnable:true,
    zooms:[3,18]
    //二维地图显示视口
//    view: new AMap.View2D({
//      center:new AMap.LngLat(118.783897, 32.058875),//地图中心点
//      zoom:15 //地图显示的缩放级别
//    })
  });
  //  mapObj.plugin(["AMap.ToolBar"],function(){
  //    toolBar = new AMap.ToolBar();
  //    mapObj.addControl(toolBar);
  //  });
  var marker = new AMap.Marker({
    position:mapObj.getCenter()
  });
  marker.setMap(mapObj);

  //为地图注册click事件获取鼠标点击出的经纬度坐标
  var clickEventListener=AMap.event.addListener(mapObj,'click',function(e){
    document.getElementById("lat_company").value=e.lnglat.getLat();
    document.getElementById("lng_company").value=e.lnglat.getLng();

    marker.setMap(null);
    mapObj.setCenter( new AMap.LngLat(e.lnglat.getLng(), e.lnglat.getLat()));
    marker = new AMap.Marker({
      position:new AMap.LngLat(e.lnglat.getLng(), e.lnglat.getLat())
    });
    marker.setMap(mapObj);
  });
</script>
