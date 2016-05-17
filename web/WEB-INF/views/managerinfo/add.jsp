<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="um" uri="/unimanager-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
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
          <div class="form-group">
            <label class="col-sm-2 control-label">支付宝账号</label>
            <div class="col-sm-4">
              <input type="text" id="pay_number" value="${info.payNumber}" class="form-control" placeholder="支付宝账号" data-toggle="tooltip" data-placement="bottom" title="Tooltip for name">
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-2 control-label">支付宝校验姓名</label>
            <div class="col-sm-4">
              <input type="text" id="check_name"value="${info.checkName}" class="form-control" placeholder="支付宝校验姓名" data-toggle="tooltip" data-placement="bottom" title="Tooltip for name">
            </div>
          </div><div class="form-group">
          <label class="col-sm-2 control-label">银行卡号</label>
          <div class="col-sm-4">
            <input type="text" id="bank_card" value="${info.bankCard}" class="form-control" placeholder="银行卡号" data-toggle="tooltip" data-placement="bottom" title="Tooltip for name">
          </div>
        </div><div class="form-group">
          <label class="col-sm-2 control-label">开户行</label>
          <div class="col-sm-4">
            <input type="text" id="bank_type" value="${info.bankType}" class="form-control" placeholder="如:建设银行" data-toggle="tooltip" data-placement="bottom" title="Tooltip for name">
          </div>
        </div><div class="form-group">
          <label class="col-sm-2 control-label">开户行地址</label>
          <div class="col-sm-4">
            <input type="text" id="bank_address" value="${info.bankAddress}" class="form-control" placeholder="开户行的详细地址" data-toggle="tooltip" data-placement="bottom" title="Tooltip for name">
          </div>
        </div>
        <div class="form-group">
          <label class="col-sm-2 control-label">开户人姓名</label>
          <div class="col-sm-4">
            <input type="text" id="bank_name" value="${info.bankName}" class="form-control" placeholder="银行卡持有者姓名" data-toggle="tooltip" data-placement="bottom" title="Tooltip for name">
          </div>
        </div>
        <div class="form-group">
          <label class="col-sm-2 control-label">联系电话</label>
          <div class="col-sm-4">
            <input type="text" id="mobile" value="${info.mobile}" class="form-control" placeholder="联系电话" data-toggle="tooltip" data-placement="bottom" title="Tooltip for name">
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
</div>
<script type="text/javascript">
  function saveManagerInfo(){
    var infoId=$("#info_id").val();
    var realName = $("#real_name").val();
    if(realName.replace(/\s/g, '') == ''){
        alert("请填写真是姓名");
        return;
    }
    var idcard = $("#idcard").val();
    if(idcard.replace(/\s/g, '') == ''){
        alert("请填写身份证号");
        return;
    }
    var imagePath = $("img[name='imagePath']").attr("src");
    if(imagePath== ""){
      alert("请上传身份证图片");
      return;
    }
    var payNumber = $("#pay_number").val();
    if(payNumber.replace(/\s/g, '') == ''){
        alert("请填写支付宝账号");
        return;
    }
     var checkName = $("#check_name").val();
    if(checkName.replace(/\s/g, '') == ''){
        alert("请填写支付宝校验姓名");
        return;
    }
    var bankCard= $("#bank_card").val();
    if(bankCard.replace(/\s/g, '') == ''){
        alert("请填写银行卡号");
        return;
    }
    var bankType= $("#bank_type").val();
    if(bankType.replace(/\s/g, '') == ''){
        alert("请填写开户行");
        return;
    }
    var bankAddress= $("#bank_address").val();
    if(bankAddress.replace(/\s/g, '') == ''){
        alert("请填写开户行地址");
        return;
    }
    var bankName= $("#bank_name").val();
    if(bankName.replace(/\s/g, '') == ''){
        alert("请填写开户人姓名");
        return;
    }
    var mobile= $("#mobile").val();
    if(mobile.replace(/\s/g, '') == ''){
        alert("请填写联系电话");
        return;
    }

    $.ajax({
      cache: true,
      type: "POST",
      url:"/managerinfo/save.do",
      data:{"id":infoId, "realName":realName, "idcard":idcard, "idcardUrl":imagePath, "payNumber":payNumber, "checkName":checkName, "bankCard":bankCard, "bankType":bankType, "bankAddress":bankAddress, "bankName":bankName, "mobile":mobile},
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
</script>


