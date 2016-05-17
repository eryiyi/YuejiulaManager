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
      <li><a href="javaScript:void(0)">活动领奖</a></li>
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
          <span>添加领奖照片</span>
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
        <h4 class="page-header">上传照片</h4>
        <form id="save_form" class="form-horizontal" method="post" role="form" >
            <div class="form-group">
            <label class="col-sm-2 control-label" >选择图片</label>
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
      var imagePath = $("img[name='imagePath']").attr("src");
      if(imagePath== ""){
          alert("请上传图片");
          return;
      }
    $.ajax({
      cache: true,
      type: "POST",
      url:"/uploadChampionPic.do",
      data:{"id":'${championId}', "pic":imagePath},// 你的formid
      async: false,
      success: function(_data) {
        var data = $.parseJSON(_data);
        if(data.success){
          alert("设置成功");
            window.location.href="#module=championList";
        }else{
          var _case = {1:"请上传类别图片", 2:"设置失败"};
          alert(_case[data.code])
        }
      }
    });
  };
</script>


