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
            <li><a href="javaScript:void(0)">我是承包商</a></li>
            <li><a href="javaScript:void(0)">开通商家</a></li>
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
        <div class="box-content">
            <h4 class="page-header">开通商家</h4>

            <form class="form-horizontal" role="form">
                <div class="input-group col-sm-6">
                    <input type="text" class="form-control" id="member_phone" placeholder="根据手机号查找会员">
              <span class="input-group-btn">
                <button class="btn btn-default" onclick="searchMember()" type="button">搜索</button>
              </span>
                </div>
                <h5 class="page-header">会员信息</h5>

                <div id="member_info" class="col-sm-12">
                </div>
                <h5 class="page-header">要开通学校</h5>
                <c:forEach items="${schools}" var="e" varStatus="st">
                    <div class="form-group">
                        <div class="col-sm-1"><input type="checkbox" name="school" value="${e.schoolId}"></div>
                        <label class="col-sm-2 control-label">${e.schoolName}</label>
                        <label class="control-label col-sm-2">到期时间</label>

                        <div class="col-sm-2" id="${e.schoolId}">
                            <input type="text" id="input_date${st.index}" name="endTime"
                                   value="${um:format(e.endTime, 'MM/dd/yyyy')}" class="form-control"
                                   placeholder="Date">
                        </div>
                    </div>
                </c:forEach>

                <div class="form-group">
                    <div class="col-sm-9 col-sm-offset-3">
                        <button type="button" class="col-sm-2 btn btn-primary" onclick="setSeller()">提交</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</div>

<script type="text/javascript">
    $(document).ready(function () {
        $('#input_date').datepicker({setDate: new Date()});
        $('#input_date0').datepicker({setDate: new Date()});
        $('#input_date1').datepicker({setDate: new Date()});
        $('#input_date2').datepicker({setDate: new Date()});
        $('#input_date3').datepicker({setDate: new Date()});
        WinMove();
    });

    function searchMember() {
        var phone = $("#member_phone").val();

        $.ajax({
            url: "/findMemberByPhone.do",
            data: {"phone": phone},
            type: "POST",
            success: function (_data) {
                var data = $.parseJSON(_data);
                if (data.success) {
                    var member = data.data;
                    var s = JSON.stringify(member);
                    var m = $.parseJSON(s);
                    var info = "<div class='col-sm-12'><label class='control-label col-sm-2'>会员名称：</label><p class='col-sm-2'>" + m.empName + "</p></div>";
                    info += "<div class='col-sm-12'><label class='control-label col-sm-2'>联系电话：</label><p class='col-sm-2'>" + m.empMobile + "</p></div>";
                    info += "<div class='col-sm-12'><label class='control-label col-sm-2'>所属大学：</label><p class='col-sm-2'>" + m.universityName + "</p></div>";
                    info += "<input type='hidden' id='member_id' value='" + m.empId + "'>";
                    $("#member_info").empty();
                    $("#member_info").append(info);
                } else {
                    alert("没有改会员，请重新输入手机号！");
                }
            }
        });
    }

    function setSeller() {
        var memberId = $("#member_id").val();
        if (memberId == null || memberId == "") {
            alert("请先查找要开通会员");
            return;
        }
        var school_ary = new Array();
        var time_ary = new Array();
        $('input[name="school"]:checked').each(function () {
            school_ary.push($(this).val());//向数组中添加元素
            var schoolIdValue = $(this).val();
            var id = document.getElementById(schoolIdValue).firstElementChild.getAttribute("id");
            var date = document.getElementById(id).value;
            if (date == null || date == '') {
                alert("请选择到期时间");
                return;
            }
            time_ary.push(date);
        });
        var schools = school_ary.join('|');//将数组元素连接起来以构建一个字符串
        var date = time_ary.join("|");
        if (schools == null || schools == '') {
            alert("请选择要开通的学校");
            return;
        }

        $.ajax({
            url: "/setSellers.do",
            data: {"empId": memberId, "schools": schools, "date": date},
            type: "POST",
            success: function (_data) {
                var data = $.parseJSON(_data);
                if (data.success) {
                    alert("开通商家成功");
                } else {
                    var _case = {1: "请求参数有误", 2: "不能将自己设置成商家", 3: "已将将该会员设置成你的商家"};
                    alert(_case[data.code]);
                }
            }
        });
    }

</script>



