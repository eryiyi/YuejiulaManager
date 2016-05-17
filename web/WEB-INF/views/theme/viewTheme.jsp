<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="um" uri="/unimanager-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
  <style>
    * { padding: 0; margin: 0; border: 0; font-family: "Microsoft Yahei"; }
    body { width: 100%; margin: 0 auto; }
    a { text-decoration: none; }
    table { border-collapse: collapse; }
    ul, ol { list-style: none; }
    h1, h2, h3, h4, h5, h6 { font-weight: normal; font-size: 1rem; }
    code, kbd, pre, samp, tt { font-family: "Courier New", Courier, monospace; }
    :focus, :active { -webkit-tap-highlight-color: rgba(0, 0, 0, 0);outline: none; }
    .container {padding-bottom: 60px;}
    .container .pk-img img{width:100%;display:block;}
    .container .detail .title{font-size:1.5rem;text-align:center;color:#333333;padding:1rem 0;line-height:95%;}
    .container .detail .time{margin-bottom:.5rem;}
    .container .detail .time h5{display:block;text-align:right;padding-right:.5rem;font-size:.7rem;color:#aaaaaa;}
    .container .detail .time h5 i.green{color:#5cb85c;}
    .container .detail .time h5 i.orange{color:#f0ad4e;}
    .container .detail .topic,.container .detail .brief{display:table;width:90%;margin:0 auto 1rem;}
    .container .detail .topic .name,.container .detail .brief .name{white-space:nowrap;display:table-cell;font-style:normal;font-size:1.1rem;}
    .container .detail .topic .content,.container .detail .brief .content{display:table-cell;font-size:.9rem;color:#666666;line-height: 150%;}
    .container .rewards{width:95%;margin:1.5rem auto;border-radius:5px;border:1px solid #cccccc;}
    .container .rewards:hover{border-color:#f0ad4e;}
    .container .rewards legend{font-size:1.6rem;color:#f0ad4e;text-align:center;}
    .container .rewards .each{width:90%;margin:2rem auto;}
    .container .rewards .each .area{font-size:1.3rem;padding:.5rem 0;text-align:center;border-bottom:1px solid #aaa;margin:1rem auto 0;}
    .container .rewards .each .content{color:#666666;margin:0 auto;}
    .container .rewards .each .content p{padding:1rem 0;}
    .container .rewards .each .content img{display:block;width:100%;padding:3px;border-radius:2px;background-color:#fff;border:solid 1px #eee;box-sizing:border-box;}
    .container .rules{width:90%;margin:0 auto 2rem;}
    .container .rules .name{line-height:200%;font-size:1.1rem;}
    .container .rules p{color:#666666;font-size:.7rem;line-height: 150%;}
  </style>
  <title>PK Notice</title>
</head>
<body>
<div class="container">
  <!-- 页头大图 -->
  <div class="pk-img">
    <img src="${theme.picUrl}" alt="${theme.title}">
  </div>
  <!-- 活动详情 -->
  <div class="detail">
    <h2 class="title">${theme.title}</h2>
    <div class="time">
      <h5><i class="green">开始时间</i>：${theme.startTime}</h5>
      <h5><i class="orange">结束时间</i>：${theme.endTime}</h5>
    </div>
    <div class="topic">
      <i class="name">主题：</i>
      <p class="content">${theme.mudi}</p>
    </div>
    <div class="brief">
      <i class="name">简介：</i>
      <p class="content">${theme.content}</p>
    </div>
  </div>
  <!-- 奖品设置 -->
  <fieldset class="rewards">
    <legend>奖品区</legend>

          <c:forEach items="${listPrize}" var="e" varStatus="st">
            <c:if test="${e.type == '0'}">
              <div class="each">
                <h3 class="area">※ 江湖总舵 ※</h3>
                <div class="content">
                  <p>${e.content}</p>
                  <img src="${e.pic}">
                </div>
              </div>
            </c:if>
            <c:if test="${e.type == '1'}">
              <div class="each">
                <h3 class="area">※ 学校本部 ※</h3>
                <div class="content">
                  <p>${e.content}</p>
                  <img src="${e.pic}">
                </div>
              </div>
            </c:if>
        </c:forEach>

  </fieldset>
  <!-- 参赛规则 -->
  <div class="rules">
    <h2 class="name">参赛规则：</h2>
    <p>
      1.大赛以丰富在校大学生的娱乐生活、才艺展示位宗旨，仅针对校内师生设立，其他参赛者无参赛资格<br>
      2.参赛者可发布多个参赛作品，但不可偏离主题，不可进行恶意刷票，否则无效<br>
      3.获奖作品版权归大赛组委会所有，大赛组委会具有发布权<br>
      4.上传作品严禁违法违规，作品发布者将承担相应的法律责任\<br>
      5.参赛者需要保证本人手机号注册，并确保通讯畅通，以便我们及时发放奖品，奖品发放期限为活动结束后一周，过期视作自动放弃<br>
      6.活动最终解释权归良讯网所有<br>
    </p>
  </div>
</div>
</body>
</html>
