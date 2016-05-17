<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="um" uri="/unimanager-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<title>${vo.title}</title>

	<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
	<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">

	<style>
		* { padding: 0; margin: 0; border: 0; font-family: "Microsoft Yahei"; }
		body { width: 100%; margin: 0 auto; }
		a { text-decoration: none; }
		table { border-collapse: collapse; }
		ul, ol { list-style: none; }
		h1, h2, h3, h4, h5, h6 { font-weight: normal; font-size: 1rem; }
		code, kbd, pre, samp, tt { font-family: "Courier New", Courier, monospace; }
		:focus, :active { -webkit-tap-highlight-color: rgba(0, 0, 0, 0); }
		.clearfix::after { content: ''; display: block; height: 0; visibility: hidden; clear: both; }
		.container { width: 96%; margin: 0 auto; padding: 1rem 0; }
		.container .video video { display: block; width: 100%; margin-bottom: 1rem; -webkit-box-shadow: 0 1px 3px rgba(0, 0, 0, .2); box-shadow: 0 1px 3px rgba(0, 0, 0, .2); }
		.container .user-info { color: #7d7d7d; }
		.container .user-info .title { width: 95%; color: #444; margin: 1rem auto; font-size: 1.2rem; line-height: 150%; margin-bottom: 1rem; word-break: break-all; word-wrap: break-word; }
		.container .user-info .person .head-pic { float: left; width: 4rem; height: 4rem; padding: 0 .5rem; }
		.container .user-info .person .head-pic img { width: 4rem; height: 4rem; border-radius: 4rem; }
		.container .user-info .person .info { line-height: 1.5rem; float: left; padding: 0 .5rem; }
		.container .user-info .person .info .nick-name { padding-top: .5rem; color: #6cbbe3; font-size: 1.1rem; display: block; }
		.container .user-info .person .info .school { padding-bottom: .5rem; display: block; }
		.container .user-info .person .time { float: right; font-size: .8rem; text-align: right; color: #ccc;line-height: 2rem; }
		.container .user-info .status { width: 95%; margin: 0 auto; line-height: 150%; padding: 1rem 1rem 0; box-sizing: border-box; }
		.container .user-info .status .price { color: #f2437e; }
		.container .user-info .status .price .big { font-size: 1.3rem; }
		.container .user-info .status .left { float: right; }
		.container .user-info .address { padding: 0 1rem; box-sizing: border-box; border-bottom: 1px solid #ddd; width: 95%; margin: 0 auto; }
		.container .user-info .address .addr { line-height: 200%; }
		.container .content { font-size: 1rem; line-height: 150%; padding: 1rem 0; border-bottom: 1px solid #ddd;}
		.container .content img { display: block; max-width: 100%; margin: 0 auto; padding-top: .6rem; }
		.container .status { color: #555; margin-top: .3rem; }
		.container .status span{ padding: 0 1rem;font-size: .8rem; }
		.container .status span:first-child{border-right: 1px solid #ccc; }
		.container .blank { line-height: 3rem; }
		.container .ad { width: 100%; padding: .5rem 0; }
		.container .ad img { display: block; width: 100%; }
		.download-button { position: fixed; bottom: 0; left: 0; width: 100%; display: block; }
		.blank{height: 40px; }

		/*ADD IN 2015-12-17 BY KYLE*/
		.container .comment{padding:2rem 0;}
		.container .comment dt{text-align:center;line-height:200%;}
		.container .comment dd{padding:.5rem 1rem;border-top:1px solid #ededed;}
		.container .comment dd:last-child{border-bottom:1px solid #ededed;}
		.container .comment dd .left{margin-right:5%;float:left;width:10%;}
		.container .comment dd .left .head-icon{width:100%;border-radius:100%;}
		.container .comment dd .right{float:right;width:85%;}
		.container .comment dd .right .top{line-height:200%;font-size:.9rem;color:#969696;}
		.container .comment dd .right .top .name{float:left;}
		.container .comment dd .right .top .floor{float:right;}
		.container .comment dd .right .top .crap{font-size:1rem;display:block;line-height:150%;color:#444444;}
	</style>
</head>
<body>
<div class="container">
	<!--视频-->
	<c:if test="${vo.videoUrl != null}">
		<div class="video"><video class="video" src="${vo.videoUrl}" preload="auto" controls="controls" poster="${vo.picUrl}"></video></div>
	</c:if>

	<!-- 用户信息 -->
	<div class="user-info">
		<!-- <h2 class="title">Title</h2> -->
		<div class="person clearfix">
			<a href="#" class="head-pic"><img src="${vo.empCover}" /></a>
			<div class="info">
				<span class="nick-name">${vo.empName}</span>
				<span class="school">${vo.schoolName}</span>
			</div>
			<span class="time">${um:format(vo.dateline, 'yyyy-MM-dd')}</span>
		</div>
	</div>
	<div class="content">
		${vo.title}
		<!--图片-->
		<%--<c:if test="${pics != null}">--%>
		<%--<c:forEach items="${pics}" var="e">--%>
		<%--<img src="${e}" alt="">--%>
		<%--</c:forEach>--%>
		<%--</c:if>--%>

		<c:forEach items="${pics}" var="e">
			<img src="${e}"  alt=""/>
		</c:forEach>

	</div>
	<div class="status"><span>评论：${vo.plNum}</span><span>票数：${vo.zanNum}</span></div>
	<!-- 广告 -->
	<div class="ad">
		<a href="http://www.liangxunwang.com/paopao/index.html" class="conta1">
			<img src="upload/20150504/1430681974978.jpg" alt="下载泡泡客户端">
		</a>
	</div>
	<!-- ADD IN 2015-12-17 BY KYLE -->

	<dl class="comment">
		<dt class="head-title">最新评论</dt>
		<c:forEach items="${list}" var="e" varStatus="st">
			<dd class="clearfix">
				<div class="left"><img class="head-icon" src="${e.cover}" alt="头像"></div>
				<div class="right">
					<div class="top clearfix">
						<span class="name">${e.nickName}</span>
						<%--<i class="floor">3#</i>--%>
					</div>
					<p class="crap">${e.commentCont}</p>
				</div>
			</dd>
		</c:forEach>

	</dl>
	<!-- end -->
	<div class="blank"></div>
</div>
<!-- 悬浮下载按钮 -->
<a  href="http://www.liangxunwang.com/paopao/index.html" >
	<img class="download-button" src="/img/share_download.png" alt="下载泡泡手机客户端">
</a>
</body>
</html>
