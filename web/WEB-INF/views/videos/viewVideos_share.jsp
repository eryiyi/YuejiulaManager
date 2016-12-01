<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="um" uri="/unimanager-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;"/>
    <title>${videos.title}</title>
    <link rel="stylesheet" href="/css/reset.css">
    <link rel="stylesheet" href="/css/video.css">
</head>
<body>
<div class="container">
    <div class="content">
        <h1 class="title">${videos.title}</h1>
        <video class="video" preload="preload" src="${videos.videoUrl}" controls="controls"
               poster="${videos.picUrl}"></video>
        <div class="article">${videos.content}</div>

    </div>
    <dl class="comment">
        <dt class="head-title">最新评论</dt>

        <c:forEach items="${list}" var="e">
            <dd class="clearfix">
                <div class="left"><img class="head-icon" src="${e.cover}" alt="头像"></div>
                <div class="right">
                    <div class="top clearfix">
                        <span class="name">${e.nickName}</span>
                            <%--<i class="floor">3#</i>--%>
                    </div>
                    <p class="crap">${e.content}</p>
                </div>
            </dd>
        </c:forEach>

    </dl>
    <a href="http://www.liangxunwang.com/paopao/index.html" class="share-link">
        <img src="/img/share_download.png" alt="下载约酒啦"></a>
</div>
</body>
</html>
