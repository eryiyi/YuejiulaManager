<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="um" uri="/unimanager-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <c:if test="${record.recordCont != null && record.recordVideo !=''}">
    <title>${record.recordCont}</title>
  </c:if>

  <style>
    *{margin:0px;padding:0px;font-family:"微软雅黑"}
    .down{max-width:100%;position:fixed;bottom:0;left:0}
    .h{width:94%;padding:10px 3%;}
    .h1{width:50px;height:50px;border-radius:30px;float:left}
    .h2{width:70%;float:left;margin-left:10px}
    .h21{width:100%;height:20px;line-height:20px;display:block;overflow:hidden;font-size:14px;color:#00CCFF}
    .h22{width:100%;height:20px;line-height:20px;display:block;overflow:hidden;font-size:12px;color:#999}
    .m{width:94%;padding:0px 3% 10px;}
    .m1 img{max-width:100%;max-height:500px;display:block;margin:5px 0px}
    .m1,.m2{font-size:18px;color:#242424;line-height:150%;width:100%;}
    #video1{width:100%;max-height:500px;background:#000}
    .m3{display:block;margin:5px 0px;max-width:100%;float:left}
    .download-button{position:fixed;bottom:0;left:0;width:100%;display:block;}
    .clearfix::after{content:'';display:block;height:0;visibility:hidden;clear:both;}
    .blank{height:60px;background-color:#eee;}

    /*add in 2015-12-17*/
    .comment{padding:2rem 0;}
    .comment dt{text-align:center;line-height:200%;}
    .comment dd{padding:.5rem 1rem;border-top:1px solid #ededed;}
    .comment dd:last-child{border-bottom:1px solid #ededed;}
    .comment dd .left{margin-right:5%;float:left;width:10%;}
    .comment dd .left .head-icon{height:3rem;width:3rem;border-radius:5rem;overflow:hidden;}
    .comment dd .left .head-icon img{display:block;width:100%;}
    .comment dd .right{float:right;width:85%;}
    .comment dd .right .top{line-height:200%;font-size:.9rem;color:#969696;}
    .comment dd .right .top .name{float:left;}
    .comment dd .right .top .floor{float:right;}
    .comment dd .right .top .crap{font-size:1rem;display:block;line-height:150%;color:#444444;}
    .fav{background-color:#fafafa;width:98%;padding:3px 0;margin:1rem auto;}
    .fav li{margin:5px;float:left;overflow:hidden;position:relative;display:block;height:2.5rem;width:2.5rem;border-radius:3rem;-webkit-box-shadow:0 0 5px rgba(0,0,0,.2);box-shadow:0 0 5px rgba(0,0,0,.2);}
    .fav li img{width:100%;display:block;position:absolute;border-radius:3rem;}
    .fav li span{border-radius:3rem;background-color:rgba(0,0,0,0.2);z-index:10;width:100%;text-shadow:1px 1px 2px rgba(0,0,0,0.3);line-height:2.5rem;text-align:center;color:#ffffff;font-weight:bold;position:absolute;}

    /*ADD @ 2016-01-09 09:33:48*/
    .videos-street{font-size:.9rem;margin:0 auto;width:94%;padding:10px 2%;}
    .videos-street .title{color:#999;text-align:center;line-height:200%;font-weight:normal;}
    .videos-street a{-webkit-box-shadow:0 0 5px rgba(0,0,0,.2);box-shadow:0 0  5px rgba(0,0,0,.2);width:100%;-webkit-box-sizing:border-box;-moz-box-sizing:border-box;box-sizing:border-box;display:block;margin:0 auto;padding:3px;border-radius:2px;background-color:#fff;}
    .videos-street a img{width:100%;display:block;}
    .recommends{width:100%;background-color:#eee;}
    .recommends .recommends-content{}
    .title-divider{display:block;text-shadow:1px 1px  2px rgba(0,0,0,.2);text-align:center;margin:0 auto 1rem;padding:5px 0;border-radius:3px;background-color:#39C4FB;color:#fff;font-weight:normal;}
    .recommends .recommends-content .recommends-item{border-radius:3px;display:block;width:95%;margin:0 auto 1rem;text-decoration:none;-webkit-box-sizing:border-box;box-sizing:border-box;-webkit-box-shadow:0 0 5px rgba(0,0,0,.15);box-shadow:0 0 5px rgba(0,0,0,.15);background-color:#fff;}
    .recommends .recommends-content .recommends-item .img-holder{height:6rem;width:25%;float:left;overflow:hidden;}
    .recommends .recommends-content .recommends-item .img-holder img{display:block;width:150%;}
    .recommends .recommends-content .recommends-item p{overflow:auto;height:6rem;width:75%;display:block;float:left;-webkit-box-sizing:border-box;-moz-box-sizing:border-box;box-sizing:border-box;padding:10px;font-size:.8rem;color:#333;}
  </style>
<script>

  window.onload=function(){

  }
</script>
</head>

<body>

<div class="h clearfix">
  <img class="h1"src="${record.empCover}"/>
  <div class="h2">
    <span class="h21">${record.empName}</span>
    <span class="h22">${um:format(record.dateLine, 'yyyy-MM-dd')}</span>
  </div>
</div>
<div class="m clearfix">
  <div class="m1">
    ${record.recordCont}</div>
  <c:if test="${record.recordVideo == null || record.recordVideo ==''}">
    <c:if test="${record.recordPicUrl != null && record.recordVideo !=''}">
      <div class="m2">
        <c:forEach items="${pics}" var="e">
          <img src="${e}"  class="m3"/>
        </c:forEach>
      </div>
    </c:if>
  </c:if>
  <div>
    <c:if test="${record.recordVideo != null && record.recordVideo !=''}">
      <video width="100%" src="${record.recordVideo}" controls="controls" poster="${record.recordPicUrl}"></video>
    </c:if>
  </div>


</div>
<div class="videos-street">
<h4 class="title">推广</h4>
<a href="${advert.adUrl}" target="_blank"><img src="${advert.adPic}"/></a>
  </div>
<!-- kyle add 2015-12-17 21:24:16 -->

<!--赞的集合-->
<ol class="fav clearfix">
  <c:forEach items="${listZan}" var="e" varStatus="st">
    <li><img src="${e.cover}" alt=""></li>
  </c:forEach>
</ol>

<!--评论的集合-->
<dl class="comment">
  <dt class="head-title">最新评论</dt>

  <c:forEach items="${list}" var="e" varStatus="st">
    <dd class="clearfix">
      <div class="left">
        <div class="head-icon">
          <img src="${e.cover}" alt="头像">
        </div>
      </div>
      <div class="right">
        <div class="top clearfix">
          <span class="name">${e.nickName}</span>
          <%--<i class="floor">#{}#</i>--%>
        </div>
        <p class="crap">${e.content}</p>
      </div>
    </dd>
  </c:forEach>
</dl>
<!-- end -->
<div class="recommends">
  <h4 class="title-divider">※ 精彩推荐 ※</h4>
  <div class="recommends-content clearfix">

      <c:forEach items="${listRecord}" var="e" varStatus="st">
        <a href="http://www.liangxunwang.com/paopao/index.html" class="recommends-item clearfix">
          <div class="img-holder">
            <img src="${e.recordPicUrl}" alt="动态图片">
          </div>
          <p>${e.recordCont}</p>
        </a>
      </c:forEach>

  </div>
  <div class="blank"></div>
</div>


<a  href="http://www.liangxunwang.com/paopao/index.html" >
  <img class="download-button" src="/img/share_download.png" alt="下载泡泡手机客户端">
</a>

</body>
</html>

