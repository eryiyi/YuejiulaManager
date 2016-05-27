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
            <li><a href="javaScript:void(0)">广告管理</a></li>
            <li><a href="javaScript:void(0)">广告列表</a></li>
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
        <div class="box ui-draggable ui-droppable">
            <div class="box-header">
                <div class="box-name ui-draggable-handle">
                    <i class="fa fa-table"></i>
                    <span>广告列表</span>
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
                <%--<p>For basic styling add the base class <code>.table</code> to any <code>&lt;table&gt;</code>.</p>--%>
                <table class="table">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>广告预览</th>
                        <th>学校名称</th>
                        <th>是否大图</th>
                        <th>是否禁用</th>
                        <th>过期时间</th>
                        <th>广告链接</th>
                        <th>添加时间</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${list}" var="e" varStatus="st">
                        <tr>
                            <td>${st.index + 1}</td>
                            <td><a href="javascript:void (0);"><img class="img-rounded" width="50" height="50"
                                                                    src="${e.adPic}" onclick="ImgShow(event)"></a></td>
                            <td>${e.schoolName}</td>
                            <td>
                                <c:if test="${e.adTypeId == '0'}">否</c:if>
                                <c:if test="${e.adTypeId == '1'}">是</c:if>
                            </td>
                            <td>
                                <c:if test="${e.adIsUse == '0'}">未禁用</c:if>
                                <c:if test="${e.adIsUse == '1'}">已禁用</c:if>
                            </td>
                            <td>${um:format(e.endTime, "yyyy-MM-dd")}</td>
                            <td><a href="${e.adUrl}" target="_blank">${e.adUrl}</a></td>
                            <td>${um:format(e.dateline, "yyyy-MM-dd HH:mm:ss")}</td>
                            <td>
                                    <%--<button class="btn btn-info" type="button" onclick="toUpdate('${e.adId}')">修改</button>--%>
                                <button class="btn btn-primary" type="button" onclick="deleteAdvert('${e.adId}')">删除
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

                <div style="margin-top: 20px;border-top: 1px solid #dedede;padding-bottom:15px; height: 50px">
                    <span style="line-height:28px;margin-top:25px;padding-left:10px; float: left">共${page.count}条/${page.pageCount}页</span>
                    <ul class="pagination" style="padding-left:100px; float: right">
                        <li>
                            <a style="margin-right:20px">每页显示&nbsp;<select name="size" id="size"
                                                                           onchange="nextPage('1')">
                                <option value="10" ${query.size==10?'selected':''}>10</option>
                                <option value="20" ${query.size==20?'selected':''}>20</option>
                                <option value="30" ${query.size==30?'selected':''}>30</option>
                                <option value="100" ${query.size==100?'selected':''}>100</option>
                            </select>&nbsp;条</a>
                        </li>
                        <c:choose>
                            <c:when test="${page.page == 1}">
                                <li><a href="javascript:void(0)">首页</a></li>
                                <li><a href="javascript:void(0)"><span class="left">《</span></a></li>
                            </c:when>
                            <c:otherwise>
                                <li><a href="javascript:void(0);" onclick="nextPage('1')">首页</a></li>
                                <li><a href="javascript:void(0);" onclick="nextPage('${page.page-1}')"><span
                                        class="left">《</span></a></li>
                            </c:otherwise>
                        </c:choose>
                        <li><a style="height: 30px; width: 100px">第<input style="width: 40px;height:20px;" type="text"
                                                                          id="index" name="index"
                                                                          onkeyup="searchIndex(event)"
                                                                          value="${page.page}"/> 页</a></li>

                        <c:choose>
                            <c:when test="${page.page == page.pageCount}">
                                <li><a href="javascript:void(0)"><span class="right">》</span></a></li>
                                <li><a href="javascript:void(0)">末页</a></li>
                            </c:when>
                            <c:otherwise>
                                <li><a href="javascript:void(0);" onclick="nextPage('${page.page+1}')"><span
                                        class="right">》</span></a></li>
                                <li><a href="javascript:void(0);" onclick="nextPage('${page.pageCount}')">末页</a></li>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    function searchIndex(e) {
        if (e.keyCode != 13) return;
        var _index = $("#index").val();
        var size = getCookie("contract_size");
        if (_index <= ${page.pageCount} && _index >= 1) {
            window.location.href = "#module=ajax/listAdvert&page=" + _index + "&size=" + size + "&_t=" + new Date().getTime();
        } else {
            alert("请输入1-${page.pageCount}的页码数");
        }
    }
    function nextPage(_page) {
        var page = parseInt(_page);
        var size = $("#size").val();
        var schoolId = $("#s2_country").val();
        addCookie("contract_size", size, 36);
        if ((page <= ${page.pageCount} && page >= 1)) {
            window.location.href = "#module=ajax/listAdvert&page=" + page + "&size=" + size + "&schoolId=" + schoolId + "&_t=" + new Date().getTime();
        } else {
            alert("请输入1-${page.pageCount}的页码数");
        }
    }

    function ImgShow(evt) {
        var imgTag = (window.event) ? event.srcElement : evt.target;
        var imgPath = imgTag.src.replace(/\_\d\./, "_4.");//取得弹出的大图url
        var tagTop = Math.max(document.documentElement.scrollTop, document.body.scrollTop);
        var tag = document.createElement("div");
        tag.style.cssText = "width:100%;z-index:221;height:" + Math.max(document.body.clientHeight, document.body.offsetHeight, document.documentElement.clientHeight) + "px;position:absolute;background:black;top:0;filter: Alpha(Opacity=80);Opacity:0.8;";
        tag.ondblclick = closes;
        var tagImg = document.createElement("div");
        tagImg.style.cssText = "font:12px /18px verdana;z-index:222;overflow:auto;text-align:center;position:absolute;border:5px solid white;background:white;color:white;left:" + (parseInt(document.body.offsetWidth) / 2 - 100) + "px;top:" + (document.documentElement.clientHeight / 3 + tagTop) + "px;"
        tagImg.innerHTML = "<div style='padding:10px;background:#cccccc;border:1px solid white'><img src='/img/loading.gif' /><br /><br /><b style='color:#999999;font-weight:normal'>Image loading...</b><br /></div>";
        tagImg.oncontextmenu = function () {
            var clsOK = confirm("确定要取消图片显示吗?点击确定将关闭图片。\n如果您是想缩放图片请在图片上双击。");
            if (clsOK) {
                closes();
            }
            ;
            return false
        };
        var closeTag = document.createElement("div");
        closeTag.style.cssText = "display:none;position:absolute;left:10px;top:10px;color:black;";
        var closesHtml = "<b style='background:red;border:1px solid white;filter:Alpha(Opacity=50);Opacity:0.5;cursor:pointer;'>&nbsp;关闭&nbsp;</b>";
        closeTag.innerHTML = closesHtml + " 提示:双击图片缩放";
        closeTag.onclick = closes;
        document.body.appendChild(tag);
        document.body.appendChild(tagImg);
        var img = new Image();
        img.src = imgPath;
        img.style.cssText = "border:1px solid #cccccc;filter: Alpha(Opacity=0);Opacity:0;cursor:pointer";
        var barShow, imgTime;
        img.complete ? ImgOK() : img.onload = ImgOK;
        function ImgOK() {
            var Stop1 = false, Stop2 = false, temp = 0;
            var tx = tagImg.offsetWidth, ty = tagImg.offsetHeight;
            var ix = img.width, iy = img.height;
            var sx = document.documentElement.clientWidth, sy = window.innerHeight || document.documentElement.clientHeight;
            if (iy > sy || ix > sx) {
                var yy = sy - 100;
                var xx = (ix / iy) * yy;
            } else {
                var xx = ix + 4;
                var yy = iy + 3;
            }
            img.style.width = xx - 10 + 'px';
            img.style.height = yy - 10 + 'px';
            if (ix < sx && iy < sy) {
                tagImg.alt = "";
                closeTag.innerHTML = closesHtml;
            }
            ;
            var maxTime = setInterval(function () {
                temp += 35;
                if ((tx + temp) < xx) {
                    tagImg.style.width = (tx + temp) + "px";
                    tagImg.style.left = (sx - (tx + temp)) / 2 + "px";
                } else {
                    Stop1 = true;
                    tagImg.style.width = xx + "px";
                    tagImg.style.left = (sx - xx) / 2 + "px";
                }
                if ((ty + temp) < yy) {
                    tagImg.style.height = (ty + temp) + "px";
                    tagImg.style.top = (tagTop + ((sy - (ty + temp)) / 2)) + "px";
                } else {
                    Stop2 = true;
                    tagImg.style.height = yy + "px";
                    tagImg.style.top = (tagTop + ((sy - yy) / 2)) + "px";
                }
                if (Stop1 && Stop2) {
                    clearInterval(maxTime);
                    tagImg.appendChild(img);
                    temp = 0;
                    imgOPacity();
                }
            }, 1);

            function imgOPacity() {
                temp += 10;
                img.style.filter = "alpha(opacity=" + temp + ")";
                img.style.opacity = temp / 100;
                imgTime = setTimeout(imgOPacity, 1)
                if (temp > 100) clearTimeout(imgTime);
            }

            tagImg.innerHTML = "";
            tagImg.appendChild(closeTag);

            if (ix > xx || iy > yy) {
                img.alt = "左键拖动,双击放大缩小";
                img.ondblclick = function () {
                    if (tagImg.offsetWidth < img.offsetWidth || tagImg.offsetHeight < img.offsetHeight) {
                        img.style.width = "auto";
                        img.style.height = "100%";
                        img.alt = "双击可以放大";
                        img.onmousedown = null;
                        closeTag.style.top = "10px";
                        closeTag.style.left = "10px";
                        tagImg.style.overflow = "visible";
                        tagImg.style.width = img.offsetWidth + "px";
                        tagImg.style.left = ((sx - img.offsetWidth) / 2) + "px";
                    } else {
                        img.style.width = ix + "px";
                        img.style.height = iy + "px";
                        img.alt = "双击可以缩小";
                        img.onmousedown = dragDown;
                        tagImg.style.overflow = "auto";
                        tagImg.style.width = (ix < sx - 100 ? ix + 20 : sx - 100) + "px";
                        tagImg.style.left = ((sx - (ix < sx - 100 ? ix + 20 : sx - 100)) / 2) + "px";
                    }
                }
                img.onmousedown = dragDown;
                tagImg.onmousemove = barHidden;
                tagImg.onmouseout = moveStop;
                document.onmouseup = moveStop;
            } else {
                tagImg.style.overflow = "visible";
                tagImg.onmousemove = barHidden;
            }
        }

        function dragDown(a) {
            img.style.cursor = "move";
            var evts = a || window.event;
            var onx = evts.clientX, ony = evts.clientY;
            var sox = tagImg.scrollLeft, soy = tagImg.scrollTop;
            var sow = img.width + 2 - tagImg.clientWidth, soh = img.height + 2 - tagImg.clientHeight;
            var xxleft, yytop;
            document.onmousemove = function (e) {
                var evt = e || window.event;
                xxleft = sox - (evt.clientX - onx) < 0 ? "0" : sox - (evt.clientX - onx) > sow ? sow : sox - (evt.clientX - onx);
                yytop = soy - (evt.clientY - ony) < 0 ? "0" : soy - (evt.clientY - ony) > soh ? soh : soy - (evt.clientY - ony);
                tagImg.scrollTop = yytop;
                tagImg.scrollLeft = xxleft;
                closeTag.style.top = (yytop + 10) + "px";
                closeTag.style.left = (xxleft + 10) + "px";
                return false;
            }
            return false;
        }

        function barHidden() {
            clearTimeout(barShow);
            closeTag.style.top = (tagImg.scrollTop + 10) + "px";
            closeTag.style.left = (tagImg.scrollLeft + 10) + "px";
            if (closeTag.style.display == "none")closeTag.style.display = "block";
            barShow = setTimeout(function () {
                closeTag.style.display = "none";
            }, 1000);
        }

        function closes() {
            document.body.removeChild(tag);
            document.body.removeChild(tagImg);
            clearTimeout(barShow);
            clearTimeout(imgTime);
            document.onmouseup = null;
            tag = img = tagImg = closeTag = null;
        }

        function moveStop() {
            document.onmousemove = null;
            tagImg.onmousemove = barHidden;
            img.style.cursor = "pointer";
        }
    }

    function toUpdate(_id) {
        $.ajax({
            type: "GET",
            data: {"adId": _id},
            url: "/toUpdateAdvert.do",
            success: function (response) {
                $("#content").html(response);
            }

        });
//    history.pushState('', 'New URL: '+"/ajax/listAdvert.do", "/#/ajax/listAdvert.do?page=1");
    }

    function deleteAdvert(_id) {
        if (!confirm("确定要删除该广告？")) {
            return;
        }
        $.ajax({
            cache: true,
            type: "POST",
            url: "/deleteAdvert.do",
            data: {"adId": _id},
            async: false,
            success: function (_data) {
                var data = $.parseJSON(_data);
                if (data.success) {
                    alert("删除成功");
                    window.location.href = "#module=ajax/listAdvert&page="+${page.page}+
                    "&_t=" + new Date().getTime();
                } else {
                    var _case = {1: "删除失败"};
                    alert(_case[data.code])
                }
            }
        });
    }
</script>


