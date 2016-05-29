<%@ taglib prefix="um" uri="/unimanager-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>童心堂后台管理系统</title>
    <meta name="description" content="description">
    <meta name="author" content="DevOOPS">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="/plugins/bootstrap/bootstrap.css" rel="stylesheet">
    <link href="/plugins/jquery-ui/jquery-ui.min.css" rel="stylesheet">
    <link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="/plugins/fancybox/jquery.fancybox.css" rel="stylesheet">
    <link href="/plugins/fullcalendar/fullcalendar.css" rel="stylesheet">
    <link href="/plugins/xcharts/xcharts.min.css" rel="stylesheet">
    <link href="/plugins/select2/select2.css" rel="stylesheet">
    <link href="/plugins/justified-gallery/justifiedGallery.css" rel="stylesheet">
    <link href="/css/style_v2.css" rel="stylesheet">
    <link href="/plugins/chartist/chartist.min.css" rel="stylesheet">
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
            <script src="http://getbootstrap.com/docs-assets/js/html5shiv.js"></script>
            <script src="http://getbootstrap.com/docs-assets/js/respond.min.js"></script>
    <![endif]-->

    <%--<link href="/umeditor/themes/default/css/umeditor.min.css" type="text/css" rel="stylesheet">--%>
    <%--<script src="/umeditor/third-party/jquery.min.js"></script>--%>
    <%--<script type="text/javascript" charset="utf-8" src="/umeditor/umeditor.config.js"></script>--%>
    <%--<script type="text/javascript" charset="utf-8" src="/umeditor/umeditor.min.js"></script>--%>
    <%--<script type="text/javascript" src="/umeditor/lang/zh-cn/zh-cn.js"></script>--%>

</head>
<body>
<!--Start Header-->
<div id="screensaver">
    <canvas id="canvas"></canvas>
    <i class="fa fa-lock" id="screen_unlock"></i>
</div>
<div id="modalbox">
    <div class="devoops-modal">
        <div class="devoops-modal-header">
            <div class="modal-header-name">
                <span>Basic table</span>
            </div>
            <div class="box-icons">
                <a class="close-link">
                    <i class="fa fa-times"></i>
                </a>
            </div>
        </div>
        <div class="devoops-modal-inner">
        </div>
        <div class="devoops-modal-bottom">
        </div>
    </div>
</div>
<header class="navbar">
    <div class="container-fluid expanded-panel">
        <div class="row">
            <div id="logo" class="col-xs-12 col-sm-2">
                <a href="javascript:void(0);">童心堂</a>
            </div>
            <div id="top-panel" class="col-xs-12 col-sm-10">
                <div class="row">
                    <div class="col-xs-8 col-sm-4">
                        <%--<div id="search">--%>
                        <%--<input type="text" placeholder="search"/>--%>
                        <%--<i class="fa fa-search"></i>--%>
                        <%--</div>--%>
                    </div>
                    <div class="col-xs-8 col-sm-8 top-panel-right">
                        <%--<a href="javascript:void(0);" class="about">about</a>--%>
                        <%--<a href="javascript:void(0);" class="style1"></a>--%>
                        <ul class="nav navbar-nav pull-right panel-menu">
                            <li class="dropdown">
                                <a href="javascript:void(0);" class="dropdown-toggle account" data-toggle="dropdown">
                                    <div class="avatar">
                                        <img src="/img/avatar.jpg" class="img-circle" alt="avatar"/>
                                    </div>
                                    <i class="fa fa-angle-down pull-right"></i>

                                    <div class="user-mini pull-right">
                                        <span class="welcome">Welcome,</span>
                                        <span>${sessionScope.account.username}</span>
                                    </div>
                                </a>
                                <ul class="dropdown-menu">
                                    <li>
                                        <a href="/logout.do">
                                            <i class="fa fa-power-off"></i>
                                            <span>Logout</span>
                                        </a>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</header>
<!--End Header-->
<!--Start Container-->
<div id="main" class="container-fluid">
    <div class="row">
        <div id="sidebar-left" class="col-xs-2 col-sm-2">
            <ul class="nav main-menu">
                <li>
                    <a href="javascript:void(0);" class="active" onclick="toPage('mainPage','')">
                        <i class="fa fa-dashboard"></i>
                        <span class="hidden-xs">主页</span>
                    </a>
                </li>
                <c:if test="${um:permission('LIST_ROLE', sessionScope.powers)||um:permission('ADD_ROLE', sessionScope.powers)}">
                    <li class="dropdown">
                        <a href="javascript:void (0);" class="dropdown-toggle">
                            <i class="fa fa-table"></i>
                            <span class="hidden-xs">角色管理</span>
                        </a>
                        <ul class="dropdown-menu">
                            <c:if test="${um:permission('ADD_ROLE', sessionScope.powers)}">
                                <li><a href="javascript:void(0);" onclick="toPage('/role/add','')">添加角色</a></li>
                            </c:if>
                            <c:if test="${um:permission('LIST_ROLE', sessionScope.powers)}">
                                <li><a href="javascript:void(0);" onclick="toPage('/role/list','')">角色列表</a></li>
                            </c:if>
                        </ul>
                    </li>
                </c:if>

                <c:if test="${um:permission('LIST_EMPLOYEE', sessionScope.powers)}">
                    <li class="dropdown">
                        <a href="javascript:void (0);" class="dropdown-toggle">
                            <i class="fa fa-table"></i>
                            <span class="hidden-xs">会员管理</span>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="javascript:void(0);" onclick="toPage('ajax/listMember','1')">会员列表</a></li>
                        </ul>
                    </li>
                </c:if>
                <c:if test="${um:permission('CONTRACT_LIST', sessionScope.powers)}">
                    <li class="dropdown">
                        <a href="javascript:void (0);" class="dropdown-toggle">
                            <i class="fa fa-table"></i>
                            <span class="hidden-xs">承包商管理</span>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="javascript:void(0);" onclick="toPage('listContracts','1')">承包商列表</a></li>
                        </ul>
                    </li>
                </c:if>
                <c:if test="${um:permission('ADVERT_LIST', sessionScope.powers)||um:permission('ADD_ADVERT', sessionScope.powers)||um:permission('LX_ADVERT', sessionScope.powers)}">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle">
                            <i class="fa fa-pencil-square-o"></i>
                            <span class="hidden-xs">广告管理</span>
                        </a>
                        <ul class="dropdown-menu">
                            <c:if test="${um:permission('ADVERT_LIST', sessionScope.powers)}">
                                <li><a href="javascript:void(0);" onclick="toPage('ajax/listAdvert','1')">广告列表</a></li>
                            </c:if>
                            <c:if test="${um:permission('ADD_ADVERT', sessionScope.powers)}">
                                <li><a href="javascript:void(0);" onclick="toPage('ajax/toAddAdvert','')">添加广告</a></li>
                            </c:if>
                            <c:if test="${um:permission('LX_ADVERT', sessionScope.powers)}">
                                <li><a href="javascript:void(0);"
                                       onclick="toPage('ajax/toAddDefaultAdvert','')">良讯广告</a></li>
                            </c:if>
                        </ul>
                    </li>
                </c:if>
                <c:if test="${um:permission('ACTIVE_LIST', sessionScope.powers)||um:permission('ADD_ACTIVE', sessionScope.powers)||um:permission('CHINA_CHAMPION', sessionScope.powers)}">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle">
                            <i class="fa fa-pencil-square-o"></i>
                            <span class="hidden-xs">PK活动管理</span>
                        </a>
                        <ul class="dropdown-menu">
                            <c:if test="${um:permission('ACTIVE_LIST', sessionScope.powers)}">
                                <li><a href="javascript:void(0);" onclick="toPage('listTheme','1')">活动列表</a></li>
                            </c:if>
                            <c:if test="${um:permission('ADD_ACTIVE', sessionScope.powers)}">
                                <li><a href="javascript:void(0);" onclick="toPage('toAddTheme','')">添加活动</a></li>
                            </c:if>
                            <c:if test="${um:permission('CHINA_CHAMPION', sessionScope.powers)}">
                                <li><a href="javascript:void(0);" onclick="toPage('championList','1')">全国冠军</a></li>
                            </c:if>
                        </ul>
                    </li>
                </c:if>
                <c:if test="${um:permission('LEVEL_LIST', sessionScope.powers)||um:permission('ADD_LEVEL', sessionScope.powers)}">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle">
                            <i class="fa fa-desktop"></i>
                            <span class="hidden-xs">等级维护</span>
                        </a>
                        <ul class="dropdown-menu">
                            <c:if test="${um:permission('LEVEL_LIST', sessionScope.powers)}">
                                <li><a href="javascript:void(0);" onclick="toPage('listLevel','1')">等级列表</a></li>
                            </c:if>
                            <c:if test="${um:permission('ADD_LEVEL', sessionScope.powers)}">
                                <li><a href="javascript:void(0);" onclick="toPage('toAddLevel','')">添加等级</a></li>
                            </c:if>
                        </ul>
                    </li>
                </c:if>
                <c:if test="${um:permission('LIST_CLASS', sessionScope.powers)||um:permission('ADD_CLASS', sessionScope.powers)||um:permission('GOODS_LIST_CLASS', sessionScope.powers)}">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle">
                            <i class="fa fa-list"></i>
                            <span class="hidden-xs">商城管理</span>
                        </a>
                        <ul class="dropdown-menu">
                            <c:if test="${um:permission('LIST_CLASS', sessionScope.powers)}">
                                <li><a href="javascript:void(0);" onclick="toPage('listType','')">分类列表</a></li>
                            </c:if>
                            <c:if test="${um:permission('ADD_CLASS', sessionScope.powers)}">
                                <li><a href="javascript:void(0);" onclick="toPage('toAddGoodsType','')">添加列表</a></li>
                            </c:if>
                            <c:if test="${um:permission('GOODS_LIST_CLASS', sessionScope.powers)}">
                                <li><a href="javascript:void(0);" onclick="toPage('/paopaogoods/listsgoods','1')">商品列表</a></li>
                            </c:if>
                        </ul>
                    </li>
                </c:if>
                <c:if test="${um:permission('NEWS_LIST_CLASS', sessionScope.powers)||um:permission('NEWS_ADD_CLASS', sessionScope.powers)}">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle">
                            <i class="fa fa-map-marker"></i>
                            <span class="hidden-xs">新闻类别</span>
                        </a>
                        <ul class="dropdown-menu">
                            <c:if test="${um:permission('NEWS_LIST_CLASS', sessionScope.powers)}">
                                <li><a href="javascript:void(0);" onclick="toPage('listNewsType','')">类别列表</a></li>
                            </c:if>
                            <c:if test="${um:permission('NEWS_ADD_CLASS', sessionScope.powers)}">
                                <li><a href="javascript:void(0);" onclick="toPage('toAddNewsType','')">添加类别</a></li>
                            </c:if>
                        </ul>
                    </li>
                </c:if>
                <c:if test="${um:permission('NEWS_LIST', sessionScope.powers)||um:permission('ADD_NEWS', sessionScope.powers)}">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle">
                            <i class="fa fa-map-marker"></i>
                            <span class="hidden-xs">新闻管理</span>
                        </a>
                        <ul class="dropdown-menu">
                            <c:if test="${um:permission('NEWS_LIST', sessionScope.powers)}">
                                <li><a href="javascript:void(0);" onclick="toPage('listNews','1')">新闻列表</a></li>
                            </c:if>
                            <c:if test="${um:permission('ADD_NEWS', sessionScope.powers)}">
                                <li><a href="javascript:void(0);" onclick="toPage('toAddNews','')">添加新闻</a></li>
                            </c:if>
                        </ul>
                    </li>
                </c:if>
                <c:if test="${um:permission('POINT_LIST', sessionScope.powers)||um:permission('ADD_POINT', sessionScope.powers)}">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle">
                            <i class="fa fa-picture-o"></i>
                            <span class="hidden-xs">积分管理</span>
                        </a>
                        <ul class="dropdown-menu">
                            <c:if test="${um:permission('POINT_LIST', sessionScope.powers)}">
                                <li><a href="javascript:void(0);" onclick="toPage('listRule','')">积分列表</a></li>
                            </c:if>
                            <c:if test="${um:permission('ADD_POINT', sessionScope.powers)}">
                                <li><a href="javascript:void(0);" onclick="toPage('toAddRule','')">添加类型</a></li>
                            </c:if>
                        </ul>
                    </li>
                </c:if>
                <c:if test="${um:permission('NOTICE_LIST', sessionScope.powers)||um:permission('ADD_NOTICE', sessionScope.powers)}">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle">
                            <i class="fa fa-picture-o"></i>
                            <span class="hidden-xs">公告管理</span>
                        </a>
                        <ul class="dropdown-menu">
                            <c:if test="${um:permission('NOTICE_LIST', sessionScope.powers)}">
                                <li><a href="javascript:void(0);" onclick="toPage('listNotice','1')">公告列表</a></li>
                            </c:if>
                            <c:if test="${um:permission('ADD_NOTICE', sessionScope.powers)}">
                                <li><a href="javascript:void(0);" onclick="toPage('ajax/toAddNotice','')">添加公告</a></li>
                            </c:if>
                        </ul>
                    </li>
                </c:if>
                <c:if test="${um:permission('PART_TIME_LIST_CLASS', sessionScope.powers)||um:permission('PART_TIME_ADD_CLASS', sessionScope.powers)||um:permission('PART_TIME_ADD_CLASS_LIST', sessionScope.powers)}">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle">
                            <i class="fa fa-picture-o"></i>
                            <span class="hidden-xs">招聘管理</span>
                        </a>
                        <ul class="dropdown-menu">
                            <c:if test="${um:permission('PART_TIME_LIST_CLASS', sessionScope.powers)}">
                                <li><a href="javascript:void(0);" onclick="toPage('listPartTimeType','')">招聘类别列表</a></li>
                            </c:if>
                            <c:if test="${um:permission('PART_TIME_ADD_CLASS', sessionScope.powers)}">
                                <li><a href="javascript:void(0);" onclick="toPage('toAddType','')">添加招聘类别</a></li>
                            </c:if>

                            <c:if test="${um:permission('PART_TIME_ADD_CLASS_LIST', sessionScope.powers)}">
                                <li><a href="javascript:void(0);" onclick="toPage('listPartTimeList','1')">招聘列表</a></li>
                            </c:if>
                        </ul>
                    </li>
                </c:if>
                <c:if test="${um:permission('LOG_LIST', sessionScope.powers)}">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle">
                            <i class="fa fa-picture-o"></i>
                            <span class="hidden-xs">日志记录</span>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="javascript:void(0);" onclick="toPage('listLog','1')">类别列表</a></li>
                        </ul>
                    </li>
                </c:if>
                <c:if test="${um:permission('ADD_RECORD', sessionScope.powers) || um:permission('LIST_RECORD_RECORD', sessionScope.powers)}">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle">
                            <i class="fa fa-picture-o"></i>
                            <span class="hidden-xs">动态管理</span>
                        </a>
                        <ul class="dropdown-menu">
                                <%--<li><a href="javascript:void(0);" onclick="toPage('toRecord','')">生成动态</a></li>--%>
                            <li><a href="javascript:void(0);"
                                   onclick="toPage('/schoolRecordsController/list','1')">动态列表</a></li>
                        </ul>
                    </li>
                </c:if>
                <c:if test="${um:permission('VIEWPAGER_ADD', sessionScope.powers) || um:permission('VIEWPAGER_LIST', sessionScope.powers)}">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle">
                            <i class="fa fa-picture-o"></i>
                            <span class="hidden-xs">轮播管理</span>
                        </a>
                        <ul class="dropdown-menu">
                            <c:if test="${um:permission('VIEWPAGER_LIST', sessionScope.powers)}">
                                <li><a href="javascript:void(0);" onclick="toPage('/viewpager/list','')">轮播列表</a></li>
                            </c:if>
                            <c:if test="${um:permission('VIEWPAGER_ADD', sessionScope.powers)}">
                                <li><a href="javascript:void(0);" onclick="toPage('/viewpager/toSave','')">添加轮播</a></li>
                            </c:if>
                        </ul>
                    </li>
                </c:if>
                <c:if test="${sessionScope.account.type!='1' && (um:permission('SCHOOL_LIST', sessionScope.powers)||um:permission('MINE_MERCHANT', sessionScope.powers)||um:permission('OPEN_MERCHANT', sessionScope.powers))}">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle">
                            <i class="fa fa-picture-o"></i>
                            <span class="hidden-xs">我是承包商</span>
                        </a>
                        <ul class="dropdown-menu">
                            <c:if test="${um:permission('SCHOOL_LIST', sessionScope.powers)}">
                                <li><a href="javascript:void(0);" onclick="toPage('listSchool','')">我的学校</a></li>
                            </c:if>
                            <c:if test="${um:permission('MINE_MERCHANT', sessionScope.powers)}">
                                <li><a href="javascript:void(0);" onclick="toPage('listSeller','')">我的商家</a></li>
                            </c:if>
                            <c:if test="${um:permission('OPEN_MERCHANT', sessionScope.powers)}">
                                <li><a href="javascript:void(0);" onclick="toPage('toSetSeller','')">开通商家</a></li>
                            </c:if>
                        </ul>
                    </li>
                </c:if>
                <c:if test="${um:permission('MODIFY_PASS', sessionScope.powers) || sessionScope.account.type=='3'}">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle">
                            <i class="fa fa-picture-o"></i>
                            <span class="hidden-xs">系统管理</span>
                        </a>
                        <ul class="dropdown-menu">
                            <c:if test="${um:permission('MODIFY_PASS', sessionScope.powers)}">
                                <li><a href="javascript:void(0);" onclick="toPage('toChangePass','')">修改密码</a></li>
                            </c:if>
                            <c:if test="${um:permission('MANAGER_INFO', sessionScope.powers) || sessionScope.account.type=='3'}">
                                <li><a href="javascript:void(0);" onclick="toPage('managerinfo/add','')">信息维护</a></li>
                            </c:if>
                            <c:if test="${um:permission('MANAGER_DIANPU_INFO', sessionScope.powers) || sessionScope.account.type=='3'}">
                                <li><a href="javascript:void(0);" onclick="toPage('adObj/list','')">店铺广告列表</a></li>
                            </c:if>
                            <c:if test="${um:permission('MANAGER_DIANPU_PT_INFO', sessionScope.powers) || sessionScope.account.type=='3'}">
                                <li><a href="javascript:void(0);" onclick="toPage('listThreePingtaiBd','')">绑定第三方平台</a>
                                </li>
                            </c:if>
                        </ul>
                    </li>
                </c:if>
                <c:if test="${um:permission('MODIFY_RATE', sessionScope.powers)}">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle">
                            <i class="fa fa-picture-o"></i>
                            <span class="hidden-xs">费率管理</span>
                        </a>
                        <ul class="dropdown-menu">
                            <c:if test="${um:permission('MODIFY_RATE', sessionScope.powers)}">
                                <li><a href="javascript:void(0);" onclick="toPage('rate/edit','')">修改费率</a></li>
                            </c:if>
                        </ul>
                    </li>
                </c:if>
                <c:if test="${sessionScope.account.type=='3'}">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle">
                            <i class="fa fa-picture-o"></i>
                            <span class="hidden-xs">我是商家</span>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="javascript:void(0);" onclick="toPage('/paopaogoods/list','1')">我的商品</a></li>
                            <li><a href="javascript:void(0);" onclick="toPage('/paopaogoods/toAdd','')">发布商品</a></li>
                            <li><a href="javascript:void(0);" onclick="toPage('/order/list','1')">订单列表</a></li>
                        </ul>
                    </li>
                </c:if>
                <c:if test="${sessionScope.account.type=='2'}">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle">
                            <i class="fa fa-picture-o"></i>
                            <span class="hidden-xs">直营商品</span>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="javascript:void(0);" onclick="toPage('/paopaogoods/listZhiying','1')">我的商品</a>
                            </li>
                            <li><a href="javascript:void(0);" onclick="toPage('/paopaogoods/toAddZhiying','')">发布商品</a>
                            </li>
                        </ul>
                    </li>
                </c:if>
                <%--<li class="dropdown">--%>
                <%--<a href="#" class="dropdown-toggle">--%>
                <%--<i class="fa fa-picture-o"></i>--%>
                <%--<span class="hidden-xs">营业收入</span>--%>
                <%--</a>--%>
                <%--<ul class="dropdown-menu">--%>
                <%--<li><a href="javascript:void(0);" onclick="toPage('/income/list','1')">收入查询</a></li>--%>
                <%--<li><a href="javascript:void(0);" onclick="toPage('/settlement/list','1')">结算查询</a></li>--%>
                <%--</ul>--%>
                <%--</li>--%>
                <c:if test="${um:permission('REMIT_SELLERLIST', sessionScope.powers)|| um:permission('REMIT_CONTRACTLIST', sessionScope.powers)}">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle">
                            <i class="fa fa-picture-o"></i>
                            <span class="hidden-xs">汇款结算</span>
                        </a>
                        <ul class="dropdown-menu">
                            <c:if test="${um:permission('REMIT_SELLERLIST', sessionScope.powers)}">
                                <li><a href="javascript:void(0);"
                                       onclick="toPage('/settlement/sellerlist','1')">商家结算</a></li>
                            </c:if>
                                <%--<c:if test="${um:permission('REMIT_CONTRACTLIST', sessionScope.powers)}">--%>
                                <%--<li><a href="javascript:void(0);" onclick="toPage('','')">承包商结算</a></li>--%>
                                <%--</c:if>--%>
                        </ul>
                    </li>
                </c:if>

                <c:if test="${um:permission('LIST_VIDEOS_LIST', sessionScope.powers)|| um:permission('VIDEOS_ADD_LIST', sessionScope.powers)}">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle">
                            <i class="fa fa-picture-o"></i>
                            <span class="hidden-xs">视频上传</span>
                        </a>
                        <ul class="dropdown-menu">
                            <c:if test="${um:permission('LIST_VIDEOS_LIST', sessionScope.powers)}">
                                <li><a href="javascript:void(0);" onclick="toPage('listVideos','1')">视频列表</a></li>
                            </c:if>
                            <c:if test="${um:permission('VIDEOS_ADD_LIST', sessionScope.powers)}">
                                <li><a href="javascript:void(0);" onclick="toPage('toAddVideos','')">添加视频</a></li>
                            </c:if>
                        </ul>
                    </li>
                </c:if>

                <c:if test="${um:permission('ADD_SCHOOL_LIST', sessionScope.powers)|| um:permission('SCHOOLS_LIST_LIST', sessionScope.powers)|| um:permission('REG_HUANXIN_COLLEGE', sessionScope.powers)}">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle">
                            <i class="fa fa-picture-o"></i>
                            <span class="hidden-xs">学校管理</span>
                        </a>
                        <ul class="dropdown-menu">

                            <c:if test="${um:permission('ADD_SCHOOL_LIST', sessionScope.powers)}">
                                <li><a href="javascript:void(0);" onclick="toPage('toAddSchools','')">添加学校</a></li>
                            </c:if>
                            <c:if test="${um:permission('SCHOOLS_LIST_LIST', sessionScope.powers)}">
                                <li><a href="javascript:void(0);" onclick="toPage('listSchools','1')">学校列表</a></li>
                            </c:if>
                                <%--<c:if test="${um:permission('REG_HUANXIN_COLLEGE', sessionScope.powers)}">--%>
                                <%--<li><a href="javascript:void(0);" onclick="toPage('regHxCollege','')">添加学校环信</a></li>--%>
                                <%--</c:if>--%>

                        </ul>
                    </li>
                </c:if>

                <c:if test="${um:permission('LIST_SCHOOL_RECORD_MOOD_LIST', sessionScope.powers)|| um:permission('SCHOOL_RECORD_MOOD_ADD_LIST', sessionScope.powers)}">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle">
                            <i class="fa fa-picture-o"></i>
                            <span class="hidden-xs">心情管理</span>
                        </a>
                        <ul class="dropdown-menu">
                            <c:if test="${um:permission('LIST_SCHOOL_RECORD_MOOD_LIST', sessionScope.powers)}">
                                <li><a href="javascript:void(0);" onclick="toPage('listSchoolRecordMood','1')">心情列表</a>
                                </li>
                            </c:if>
                            <c:if test="${um:permission('SCHOOL_RECORD_MOOD_ADD_LIST', sessionScope.powers)}">
                                <li><a href="javascript:void(0);" onclick="toPage('toAddSchoolRecordMood','')">添加心情</a>
                                </li>
                            </c:if>
                        </ul>
                    </li>
                </c:if>
                <c:if test="${um:permission('THREE_PINGTAI_GL_LIST', sessionScope.powers)|| um:permission('THREE_PINGTAI_GL_ADD', sessionScope.powers)}">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle">
                            <i class="fa fa-picture-o"></i>
                            <span class="hidden-xs">第三方平台管理</span>
                        </a>
                        <ul class="dropdown-menu">
                            <c:if test="${um:permission('THREE_PINGTAI_GL_LIST', sessionScope.powers)}">
                                <li><a href="javascript:void(0);" onclick="toPage('listThreePingtai','1')">平台列表</a></li>
                            </c:if>
                            <c:if test="${um:permission('THREE_PINGTAI_GL_ADD', sessionScope.powers)}">
                                <li><a href="javascript:void(0);" onclick="toPage('toAddThreePingtai','')">添加平台</a></li>
                            </c:if>
                        </ul>
                    </li>
                </c:if>

                <c:if test="${um:permission('GET_FIND_WWW_LIST', sessionScope.powers)|| um:permission('ADD_FIND_WWW_ADD', sessionScope.powers)}">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle">
                            <i class="fa fa-picture-o"></i>
                            <span class="hidden-xs">发现网址管理</span>
                        </a>
                        <ul class="dropdown-menu">
                            <c:if test="${um:permission('GET_FIND_WWW_LIST', sessionScope.powers)}">
                                <li><a href="javascript:void(0);" onclick="toPage('listSchoolFind','1')">网址列表</a></li>
                            </c:if>
                            <c:if test="${um:permission('ADD_FIND_WWW_ADD', sessionScope.powers)}">
                                <li><a href="javascript:void(0);" onclick="toPage('toAddFinds','')">添加网址</a></li>
                            </c:if>
                        </ul>
                    </li>
                </c:if>

            </ul>
        </div>
        <!--Start Content-->
        <div id="content" class="col-xs-12 col-sm-10">
            <div id="about">
                <div class="about-inner">
                    <h4 class="page-header">Open-source admin theme for you</h4>

                    <p>DevOOPS team</p>

                    <p>Homepage - <a href="http://devoops.me" target="_blank">http://devoops.me</a></p>

                    <p>Email - <a href="mailto:devoopsme@gmail.com">devoopsme@gmail.com</a></p>

                    <p>Twitter - <a href="http://twitter.com/devoopsme" target="_blank">http://twitter.com/devoopsme</a>
                    </p>

                    <p>Donate - BTC 123Ci1ZFK5V7gyLsyVU36yPNWSB5TDqKn3</p>
                </div>
            </div>
            <div class="preloader">
                <img src="/img/devoops_getdata.gif" class="devoops-getdata" alt="preloader"/>
            </div>
            <div id="ajax-content"></div>
        </div>
        <!--End Content-->
    </div>
</div>
<!--End Container-->
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<%--<!--<script src="http://code.jquery.com/jquery.js"></script>-->--%>
<script src="/plugins/jquery/jquery.min.js"></script>
<script src="/plugins/jquery-ui/jquery-ui.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="/plugins/bootstrap/bootstrap.min.js"></script>
<script src="/plugins/justified-gallery/jquery.justifiedGallery.min.js"></script>
<script src="/plugins/tinymce/tinymce.min.js"></script>
<script src="/plugins/tinymce/jquery.tinymce.min.js"></script>
<!-- All functions for this theme + document.ready processing -->
<script src="/js/devoops.js"></script>
<script src="/js/china2.js"></script>
<script type="text/javascript" src="/js/md5.js"></script>
<script type="text/javascript" src="/js/cookie.js"></script>
<script type="text/javascript" src="/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="/js/Util.js"></script>

</body>
<script type="text/javascript">
    (function (window, undefined) {
        var currentHash;

        function decodeChineseWords(params) {
            if (params["cn"] == undefined || params["cn"] == "") {
                return;
            }
            var cns = params["cn"].split(","), i;
            for (i = 0; i < cns.length; i++) {
                params[cns[i]] = decodeURIComponent(params[cns[i]]);
            }
        }

        function checkHash() {
            var newHash = window.location.hash;
            if (newHash == "") {
//                window.location.hash = "#module=main";
                return;
            }
            if (newHash == currentHash) return;
            currentHash = newHash;
            var paramsString = currentHash.substring(1);
            var paramsArray = paramsString.split("&");
            var params = {};
            for (var i = 0; i < paramsArray.length; i++) {
                var tempArray = paramsArray[i].split("=");
                params[tempArray[0]] = tempArray[1];
            }
            var _url = params["module"].replace(/\./g, "/") + ".do?_t=" + new Date().getTime();
            delete params["module"];
            decodeChineseWords(params);
            $.ajax({
                url: _url, type: "post", data: params, success: function (data) {
                    var editor;
                    while ((editor = Util.editors.shift()) != undefined) {
                        editor.destroy();
                    }
                    $("#content").html(data);
                }
            });
        }

        window.setInterval(checkHash, 100);
    })(window);
    function toPage(_url, _page) {
        if (_page != '') {
            window.location.href = "#module=" + _url + "&page=" + _page;
        } else {
            window.location.href = "#module=" + _url;
        }

//		$.ajax({
//			type: "post",
//			url: _url,
//			data:{"page":_page},
//			success: function(response){
//				$("#content").html(response);
//			}
//
//		});
//		history.pushState('', 'New URL: '+_url, "/#"+_url);
    }

    function loadNotice() {
        window.location.href = "/#/ajax/toAddNotice.do"
        window.location.reload();
    }
</script>
</html>
