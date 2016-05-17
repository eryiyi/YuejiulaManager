<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<script type="text/javascript" src="/js/Util.js"></script>
<div class="row">
  <div id="breadcrumb" class="col-xs-12">
    <a href="#" class="show-sidebar">
      <i class="fa fa-bars"></i>
    </a>
    <ol class="breadcrumb pull-left">
      <li><a href="javaScript:void(0)">主页</a></li>
      <li><a href="javaScript:void(0)">活动管理</a></li>
      <li><a href="javaScript:void(0)">添加活动</a></li>
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
          <span>添加活动表单</span>
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
        <h4 class="page-header">活动信息</h4>
        <form id="save_form" class="form-horizontal" method="post" role="form" >
          <div class="form-group">
            <label class="col-sm-2 control-label">活动主题</label>
            <div class="col-sm-4">
              <input type="text" id="theme_title"  class="form-control" placeholder="活动主题" data-toggle="tooltip" data-placement="bottom" title="Tooltip for name">
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-2 control-label">活动期次</label>
            <div class="col-sm-4">
              <input type="text" id="theme_number" class="form-control" placeholder="活动期次" data-toggle="tooltip" data-placement="bottom" title="Tooltip for name">
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-2 control-label">活动目的</label>
            <div class="col-sm-8">
              <input type="text" id="theme_mudi" class="form-control" placeholder="活动目的" data-toggle="tooltip" data-placement="bottom" title="Tooltip for name">
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-2 control-label">活动类别</label>
            <div class="col-sm-4">
              <select class="populate placeholder" name="themeType" id="theme_type">
                <option value="">-- 选择活动类别 --</option>
                <option value="0">文字</option>
                <option value="1">图片</option>
                <option value="2">视频</option>
              </select>
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-2 control-label">活动开始时间</label>
            <div class="col-sm-2">
                <input type="date" id="theme_start_date" class="form-control">
            </div>
            <div class="col-sm-2">
              <input type="time" id="theme_start_time" class="form-control">
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-2 control-label">活动结束时间</label>
            <div class="col-sm-2">
              <input type="date" id="theme_end_date" class="form-control">
            </div>
            <div class="col-sm-2">
              <input type="time" id="theme_end_time" class="form-control">
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-2 control-label">活动介绍</label>
            <div class="col-sm-8">
              <input type="text" id="theme_content" class="form-control" placeholder="活动介绍" data-toggle="tooltip" data-placement="bottom" title="Tooltip for name">
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-2 control-label" >主题图片</label>
            <div class="col-sm-10">
              <input type="file" name="file" id="fileUpload" style="float: left;" />
              <input type="button" value="上传" onclick="uploadImage()" style="float: left;"/><br/><br/><div id="imageDiv" style="padding: 10px"></div>
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
//  $(document).ready(function() {
//    // Initialize datepicker
//    $('#theme_start_date').datepicker({setDate: new Date()});
//    $('#theme_end_date').datepicker({setDate: new Date()});
//    WinMove();
//  });
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

  function deleteImage(e, node) {
    if(e.button == 2) {
      if(confirm("确定移除该图片吗？")) {
        $(node).remove();
      }
    }
  };
  function save(){
    var themeTitle = $("#theme_title").val();
    if(themeTitle.replace(/\s/g, '')==''){
      alert("活动主题不能为空");
      return;
    }
    var themeNumber = $("#theme_number").val();
    if(themeNumber.replace(/\s/g, '')==''){
      alert("活动期次不能为空");
      return;
    }
    if(!isInteger(themeNumber)){
      alert("活动期次必须是整数");
      return;
    }
    var themeMudi = $("#theme_mudi").val();
    if(themeMudi.replace(/\s/g, '')==''){
      alert("活动目的不能为空");
      return;
    }
    var themeType = $("#theme_type").val();
    if(themeType.replace(/\s/g, '') == ''){
      alert("请选择活动类别");
      return;
    }
    var startTime = $("#theme_start_date").val();
    var startTime2 = $("#theme_start_time").val();
    if(startTime== "" || startTime2 == ""){
      alert("请选择开始时间");
      return;
    }
    var endTime = $("#theme_end_date").val();
    var endTime2 = $("#theme_end_time").val();
    if(endTime == "" || endTime2 == ""){
      alert("请选择结束时间");
      return;
    }
    var themeContent = $("#theme_content").val();
    if(themeContent.replace(/\s/g, '')==''){
      alert("活动介绍不能为空");
      return;
    }

    var imagePath = $("img[name='imagePath']").attr("src");
    if(imagePath== ""){
      alert("请上传图片");
      return;
    }
    $.ajax({
      cache: true,
      type: "POST",
      url:"/addPkTheme.do",
      data:{"title":themeTitle,"number":themeNumber,"type":themeType,"startTime":startTime+" "+startTime2, "endTime":endTime+" "+endTime2,"mudi":themeMudi, "content":themeContent, "picUrl":imagePath},// 你的formid
      async: false,
      success: function(_data) {
        var data = $.parseJSON(_data);
        if(data.success){
          alert("添加成功");
          window.location.href="#module=listTheme";
        }else{
          var _case = {1:"主题不能为空", 2:"期次不能为空", 3:"内容不能为空", 4:"目的不能为空", 5:"添加失败", 6:"该期次活动已存在", 7:"本期活动开始时间不能早于上期结束时间"};
          alert(_case[data.code])
        }
      }
    });
  };
  function isInteger( str ){

    var regu = /^[-]{0,1}[0-9]{1,}$/;

    return regu.test(str);

  };
</script>

