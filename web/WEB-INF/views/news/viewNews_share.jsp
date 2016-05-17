<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="um" uri="/unimanager-tags" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;" />
	<title>${news.title}</title>
	<style>
		*{ margin:0px; padding:0px; font-family:"微软雅黑"}
		#cont1{margin: 0 auto;padding-top: 10px;}
		.down{max-width:100%;position:fixed; bottom: 0; left: 0;}
		#cont2{margin-bottom: 5px;padding-left: 4px;}
		time{padding-left: 4px;}
		#cont3{padding-left: 4px;}
		#cont4{height: 40px;}

	</style>
</head>
<body>
<div id="cont1">
	<p id="cont2">${news.title}</p>
	<time>${um:format(news.dateLine, 'yyyy-MM-dd HH:mm:ss')}  发布者：${news.nickName}</time>
	<div id="cont3">${news.content}</div>
	<div id="cont4"></div>
</div>

</div>
	<a  href="http://www.liangxunwang.com/paopao/index.html" class="share-link">
		<img src="/img/share_download.gif" class="down">
	</a>
</body>
</html>
