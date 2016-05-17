<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="um" uri="/unimanager-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>兼职</title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>

<body style="background:#fafafa">
<div class="container">
    <div class="h">

        <div class="b">
            <span class="b1">${partTime.title}</span>
        </div>
    </div>
    <div class="m clearfix">
        <div class="m1">
            <div class="b3 clearfix">
            <span class="b31">
                <img src="${partTime.empCover}" />
                <i class="b311">${partTime.empName}</i>
                <span class="b32"><i></i>发布时间:${partTime.dateline}</span>
            </span>
            </div>
        </div>
        <div class="m2">
            <div class="m21">
                <p class="m211">${partTime.money}<b></b></p>
                <p class="m212"><i>兼职类型：</i>${partTime.typeName}</p>
            </div>
            <div class="m22">
                <div class="m221">
                    岗位职责：<br />
                    ${partTime.cont}
                </div>
            </div>
        </div>
        <div class="m23">
            <p class="b"><span class="b1">联系信息</span></p>
            <p class="m212"><i>联系人：</i>${partTime.contact}</p>
            <p class="m212"><i>电　话：</i>${partTime.mobile}</p>
            <p class="m212"><i>地　址：</i>${partTime.address}</p>
        </div>
        <br>
        <a href="http://www.liangxunwang.com/paopao/index.html" class="conta1">
            <img src="upload/20150504/1430681974978.jpg">
        </a>
    </div>
    <div class="f"></div>
</div>
<style>*{padding:0px;margin:0px;font-family:"微软雅黑"}
.clearfix::after{content:'';display:block;height:0;visibility:hidden;clear:both}
.h{width:100%;background:#fff;border-bottom:1px solid #e9e9e9}
.a{width:100%;-webkit-box-sizing:border-box;-moz-box-sizing:border-box;box-sizing:border-box;padding:0 1rem;height:45px;background:#eaeaea}
.b{width:95%;margin:0 auto;padding:5px 0}
.b1{height:20px;line-height:20px;border-left:5px solid #3499eb;text-align:left;display:block;font-size:12px;padding-left:10px}
.b2{height:20px;line-height:20px;border-left:5px solid #3499eb;text-align:left;display:inline-block;float:left;margin-top:5px;font-size:12px;padding-left:10px}
.b3{padding:.8rem 0;z-index:;width:95%;margin:0 auto}
.b31{float:left;font-size:12px;color:#bababa;width:100%}
.b31 img{width:40px;height:40px;border-radius:20px;vertical-align:middle;margin-right:5px;float:left}
.b32{font-size:12px;color:#bababa;line-height:30px;float:left;margin-right:15px}
.b32 i{background:url(/img/mine_time.png);width:20px;height:20px;display:inline-block;vertical-align:text-bottom;margin-right:5px;background-repeat:no-repeat}
.b311{width:80%;height:20px;line-height:20px;display:block;overflow:hidden;vertical-align:middle;font-style:normal;float:left}
.h1{float:left;height:45px;line-height:45px;width:45px;text-align:center;font-size:25px;color:#b0b0b0;font-family:"宋体"}
.h2{display:block;height:45px;line-height:45px;margin:0 auto;width:100px;text-align:center}
.m{width:100%;margin:10px auto}
.m1{width:100%;background:#fff;border-bottom:1px solid #e9e9e9;border-top:1px solid #e9e9e9}
.m2{width:100%;background:#fff;border-bottom:1px solid #e9e9e9;border-top:1px solid #e9e9e9;margin-top:10px}
.m21,
.m22{width:95%;background:#fff;margin:10px auto}
.m211{color:#ff8302;font-size:14px;width:100%;height:25px;line-height:25px;border-bottom:1px solid #e9e9e9}
.m212{color:#333;font-size:12px;width:95%;margin:0 auto;line-height:25px}
.m211 b{font-size:25px;font-style:normal}
.m212 i{font-style:normal;color:#aeaeae}
.m221{color:#aeaeae;font-size:12px;width:100%;line-height:25px}
.m23{margin:10px 0;background-color:#fff;border-bottom:1px solid #e9e9e9;border-top:1px solid #e9e9e9;padding:10px 0px}
.conta1{width:100%;display:block;margin-top:10px}
.conta1 img{width:100%}
</style>
</body>
</html>



