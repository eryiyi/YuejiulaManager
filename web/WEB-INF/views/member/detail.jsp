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
      <li><a href="javascript:void(0)"  onclick="toPage('mainPage','')">主页</a></li>
      <li><a href="javascript:void (0);">会员管理</a></li>
      <li><a href="javascript:void (0);">会员详情</a></li>
    </ol>
    <div id="social" class="pull-right">
      <a href="javascript:void(0)"><i class="fa fa-google-plus"></i></a>
      <a href="javascript:void(0)"><i class="fa fa-facebook"></i></a>
      <a href="javascript:void(0)"><i class="fa fa-twitter"></i></a>
      <a href="javascript:void(0)"><i class="fa fa-linkedin"></i></a>
      <a href="javascript:void(0)"><i class="fa fa-youtube"></i></a>
    </div>
  </div>
</div>

<div class="row">
  <div class="col-xs-12 col-sm-12">
    <div class="box">
      <div class="box-header">
        <div class="box-name">
          <i class="fa fa-search"></i>
          <span>会员详情</span>
        </div>
      </div>
      <div class="box-content">
        <%--<h4 class="page-header">会员详情</h4>--%>
        <form class="form-horizontal" role="form">
          <input type="hidden" value="${empVO.empId}" id="emp_id">
          <input type="hidden" value="${empVO.empCover}" id="emp_cover">

          <div class="form-group">
            <label class="col-sm-2 control-label">用户名</label>
              <div class="col-sm-4">
                  <input type="text" id="emp_name" class="form-control" value="${empVO.empName}" data-toggle="tooltip" data-placement="bottom" title="Tooltip for name">
              </div>
          </div>
          <div class="form-group">
            <label class="col-sm-2 control-label">用户手机号</label>
            <div class="col-sm-4">
              <input type="text" id="emp_mobile" readonly="true" class="form-control" value="${empVO.empMobile}" data-toggle="tooltip" data-placement="bottom" title="Tooltip for name">
            </div>
          </div>
            <div class="form-group">
            <label class="col-sm-2 control-label">签名</label>
            <div class="col-sm-4">
              <input type="text" id="emp_sign" placeholder="用户签名" class="form-control" value="${empVO.empSign}" data-toggle="tooltip" data-placement="bottom" title="Tooltip for name">
            </div>
          </div>
            <div class="form-group">
                <label class="col-sm-2 control-label" >头像</label>
                <div class="col-sm-10 col-md-2">
                     <img class="img-thumbnail" name="imagePath" id="imageDiv"   style="cursor: pointer"  src="${empVO.empCover}"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label" ></label>
                <div class="col-sm-10">
                    <input type="file" name="file" id="fileUpload" style="float: left;" />
                    <input type="button" value="上传" onclick="uploadImage('fileUpload','imageDiv')" style="float: left;"/><br/><br/>
                </div>
            </div>

          <div class="form-group">
            <label class="col-sm-2 control-label">性别</label>
            <div class="col-sm-4">
              <select class="form-control" id="emp_sex">
                <option value="">--选择--</option>
                <option value="0" ${empVO.empSex=='0'?'selected':''}>男</option>
                <option value="1" ${empVO.empSex=='1'?'selected':''}>女</option>
              </select>
            </div>
          </div>

          <div class="form-group">
            <label class="col-sm-2 control-label">QQ</label>
            <div class="col-sm-4">
              <input type="text" id="emp_qq" placeholder="用户QQ" class="form-control" value="${empVO.empQQ}" data-toggle="tooltip" data-placement="bottom" title="Tooltip for name">
            </div>
          </div>


          <div class="form-group">
            <label class="col-sm-2 control-label">学校</label>
            <div class="col-sm-4">
              <input type="text" readonly="true" class="form-control" value="${empVO.universityName}">
            </div>
          </div>

          <div class="form-group">
            <label class="col-sm-2 control-label">注册日期</label>
            <div class="col-sm-4">
              <input type="text" readonly="true" class="form-control" value="${um:format(empVO.dateline, "yyyy-MM-dd HH:mm:ss")}" >
            </div>
          </div>

          <div class="form-group">
            <label class="col-sm-2 control-label">等级</label>
            <div class="col-sm-4">
              <input type="text" readonly="true" class="form-control" value="${empVO.levelName}" >
            </div>
          </div>


          <div class="form-group">
            <label class="col-sm-2 control-label">会员类别</label>
            <div class="col-sm-4">
              <select class="form-control" id="emp_typeid" disabled="disabled">
                <option value="0" ${empVO.empTypeId=='0'?'selected':''}>管理员</option>
                <option value="1" ${empVO.empTypeId=='1'?'selected':''}>会员</option>
                <option value="2" ${empVO.empTypeId=='2'?'selected':''}>商家</option>
                <option value="3" ${empVO.empTypeId=='3'?'selected':''}>承包商</option>
              </select>
            </div>
          </div>

          <div class="form-group">
            <label class="col-sm-2 control-label">是否禁用</label>
            <div class="col-sm-4">
              <select class="form-control" id="is_use" disabled="disabled">
                <option value="">--请选择--</option>
                <option value="0" ${empVO.isUse=='0'?'selected':''}>否</option>
                <option value="1" ${empVO.isUse=='1'?'selected':''}>是</option>
              </select>
            </div>
          </div>

          <div class="form-group">
            <label class="col-sm-2 control-label">会员经纬度</label>
            <div class="col-sm-4">
              <input type="text" readonly="true" placeholder="经度" id="lat" class="form-control" value="${empVO.lat}" data-toggle="tooltip" data-placement="bottom" title="Tooltip for name">
              <input type="text" readonly="true" placeholder="纬度" id="lng" class="form-control" value="${empVO.lng}" data-toggle="tooltip" data-placement="bottom" title="Tooltip for name">
            </div>
          </div>

          <div class="form-group">
            <div class="col-sm-9 col-sm-offset-3">
              <button type="button" class="btn btn-primary" onclick="saveRole('${empVO.empId}')">确定修改</button>
              <button type="button" class="btn btn-primary" onclick="javascript :history.back(-1)">返回</button>
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>



</div>

<script type="text/javascript">

  function saveRole(mm_emp_id){
    var emp_id = $("#emp_id").val();
    var emp_name = $("#emp_name").val();
    var emp_sex = $("#emp_sex").val();
    var emp_sign = $("#emp_sign").val();
    var emp_qq = $("#emp_qq").val();
    var is_use = $("#is_use").val();

//    var lat_company = $("#lat_company").val();
//    var lng_company = $("#lng_company").val();
//    var company_address = $("#company_address").val();
//    var company_tel = $("#company_tel").val();
//    var company_person = $("#company_person").val();
//    var company_detail = $("#company_detail").val();
//    var company_name = $("#company_name").val();


    if(emp_name.replace(/\s/g, '') == ''){
        alert("名称不能为空");
        return;
    }
    if(emp_sex.replace(/\s/g, '') == ''){
      alert("请选择性别");
      return;
    }

    if(emp_sign.replace(/\s/g, '') == ''){
      alert("请输入签名");
      return;
    }

    if(is_use.replace(/\s/g, '') == ''){
      alert("请选择是否禁用");
      return;
    }

      var imagePath = $("img[name='imagePath']").attr("src");

      if(imagePath== "" || imagePath==null){
          imagePath = $("#emp_cover").val();
          return;
      }

    $.ajax({
      cache: true,
      type: "POST",
      url:"/updateEmpById.do",
      data:{
        "empId":emp_id,
        "empName":emp_name,
        "empSex":emp_sex,
        "empCover":imagePath,
        "empSign":emp_sign,
        "empQQ":emp_qq,
        "isUse":is_use
      },
      async: false,
      success: function(_data) {
        var data = $.parseJSON(_data);
        if(data.success){
          alert("修改成功");
            history.go(-1);
        }else{
          var _case = {1:"修改失败"};
          alert(_case[data.code])
        }
      }
    });
  };

</script>

<script type="text/javascript">
    function uploadImage(_fileUpload,_imageDiv) {
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

    function deleteImage(e, node) {
        if(e.button == 2) {
            if(confirm("确定移除该图片吗？")) {
                $(node).remove();
            }
        }
    };


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