<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<script type="text/javascript" src="/js/Util.js"></script>
<div class="row">
  <div id="breadcrumb" class="col-xs-12">
    <a href="#" class="show-sidebar">
      <i class="fa fa-bars"></i>
    </a>
    <ol class="breadcrumb pull-left">
      <li><a href="javaScript:void(0)">主页</a></li>
      <li><a href="javaScript:void(0)">积分规则</a></li>
      <li><a href="javaScript:void(0)">添加规则</a></li>
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
          <span>添加积分表单</span>
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
        <h4 class="page-header">积分信息</h4>
        <form id="save_form" class="form-horizontal" method="post" role="form" >
          <div class="form-group">
            <label class="col-sm-2 control-label">类型名称</label>
            <div class="col-sm-4">
              <input type="text" id="ruleName" name="ruleName" class="form-control" placeholder="类型名称" data-toggle="tooltip" data-placement="bottom" title="Tooltip for name">
            </div>
          </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">类型</label>
                <div class="col-sm-4">
                    <input type="text" id="ruleType" name="ruleType" class="form-control" placeholder="类型" data-toggle="tooltip" data-placement="bottom" title="Tooltip for name">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">分数</label>
                <div class="col-sm-4">
                    <input type="text" id="ruleScore" name="ruleScore" class="form-control" placeholder="分数" data-toggle="tooltip" data-placement="bottom" title="Tooltip for name">
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
  function save(){
      var ruleName = $("#ruleName").val();
      var ruleType = $("#ruleType").val();
      var ruleScore = $("#ruleScore").val();
      if(ruleName.replace(/\s/g, '') == ''){
          alert("类别名称不能为空");
          return;
      }
      if(ruleType.replace(/\s/g, '') == ''){
          alert("类别类型");
          return;
      }
      if(ruleScore.replace(/\s/g, '') == ''){
          alert("积分不能为空");
          return;
      }
    $.ajax({
        cache: true,
        type: "POST",
        url:"/saveRule.do",
        data:{"type":ruleType, "name":ruleName, "score":ruleScore },
        async: false,
        success: function(_data) {
        var data = $.parseJSON(_data);
            if(data.success){
              alert("添加成功");
                window.location.href="#module=listRule";
//                $.ajax({
//                    type: "GET",
//                    url: "/listRule.do",
//                    success: function(response){
//                        $("#content").html(response);
//                    }
//
//                });
//                history.pushState('', 'New URL: '+"/listRule.do", "/listRule.do");

            }else{
              var _case = {1:"添加失败"};
              alert(_case[data.code])
            }
        }
    });
  };
</script>


