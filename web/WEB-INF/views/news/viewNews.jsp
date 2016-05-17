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
		.cont{ padding:5px;}
		.cont1{ font-size:20px; color:#242424; font-weight:bold; float:left;width: 100%}
		.cont2{ font-size:16px; color:#343434; float:left; line-height:23px}
		.cont2 p{width: 100%;float: left;text-indent: 25px;}
		.cont time{ font-size:12px; color:#565656; margin:5px 0px 10px 0px;;width: 100%; float:left}
		.cont2 img{ width:100%; margin:5px 0px;  display: block; }
		/*.conta1{ width: 100%;display: block;float: left;margin-top: 10px}*/
		/*.bottom{position:relative; height:auto;min-height: 100%;margin:0;widht:100%;height: 38px;}*/
		/*.down{max-width:100%;padding-bottom: 0px }*/
		/*.bot01{width:100%; position: fixed;  bottom: 0;left:0;  }*/

	</style>
</head>

<body>

		<div class="cont">
			<p class="cont1">${news.title}</p><time>${um:format(news.dateLine, 'yyyy-MM-dd HH:mm:ss')}  发布者：${news.nickName}</time>
			<div class="cont2">${news.content}</div>
		</div>


</body>
</html>
