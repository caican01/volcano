<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="zh-cmn-Hans" class="ua-mac ua-webkit">
<head>
    <meta name="referrer" content="no-referrer" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>发现好电影详情页/${sessionScope.movieDescription.movieName}</title>
    <link rel="SHORTCUT ICON" href="/assets/img/knowU.ico"/>
    <!-- 评分星形控件CSS-->
    <link href="/assets/css/star-rating.css" media="all" rel="stylesheet" type="text/css"/>
    <!-- 整体DIV CSS-->
    <link href="/assets/css/bootstrap.css" rel="stylesheet">
    <link href="/assets/css/wholeframe.css" rel="stylesheet" type="text/css">
    <link href="/assets/css/MovieDescription.css" rel="stylesheet" type="text/css">
    <link href="/assets/css/SuggestList.css" rel="stylesheet" type="text/css">
    <!-- JS-->
    <script src="/assets/js/jquery.js"></script>
    <script src="/assets/js/bootstrap.min.js"></script>
    <script src="/assets/js/star-rating.min.js" type="text/javascript"></script>

    <!-- 页面一开始加载star类和切换喜欢按钮样式-->
    <script type="text/javascript">
        function  load() {
            $("#allstar").rating({
                    showClear: false,
                    size: 'xs',
                    showCaption: false,
                    readonly: true,
                }
            );
            $("#Evaluation").rating({
                min: 0,
                max: 5,
                step: 0.5,
                size: 'sm',
            });
            if("${sessionScope.booluserunlikedmovie}"==1)
                $("#liked").toggleClass('likedactive');
        }
    </script>

</head>

<body onload="load()">
<!-- 导航栏-->
<!--  使用了bootstrap的组件 -->
<nav class="navbar navbar-default" role="navigation" style="background-color: black;margin-bottom: 0%">
    <a class="navbar-brand" href="/" style="color: white">电影网</a>

    <div class="col-xs-4" style="margin-top: 8px">
        <!--  form-control也是bootstrap的类 -->
        <input id="inp-query" class="form-control"  name="search_text" maxlength="60" placeholder="请输入片名" onkeydown="if(event.keyCode==13) searchWebSite()">
        <%--<button id="query-btn" class="search_btn">搜索</button>--%>
    </div>
    <!-- navbar-brand也是bootstrap的类 -->
    <a class="navbar-brand" href="/index" style="color: white">电影主页</a>

    <!-- 如果没有登录，显示的是登录、注册按钮 -->
    <!-- 未登录 -->
    <c:if test="${sessionScope.user == null}">
        <a class="dream" href="javascript:window.location.href='/page/register'" id="register" style="float: right;color: white;font-size: 13pt;margin-top: 10px;margin-right: 10px">
            <span style="color: white" class="glyphicon glyphicon-user"></span> 注册</a>
        <a class="dream" href="javascript:window.location.href='/page/login'" style="float: right;color: white;font-size: 13pt;margin-top: 10px;margin-right: 10px">
            <span style="color: white" class="glyphicon glyphicon-log-in"></span> 登录</a>
    </c:if>

    <!-- 如果登录了，则显示的是个人中心按钮和退出按钮 -->
    <c:if test="${sessionScope.user != null}">
        <a class="dream" onclick='$.post("/page/profile",{"userId":"${sessionScope.user.userId}"}, function (data) {
                if (data=="success") {
                    location.href = "/profile"   <!--跳转到/profile-->
                }
                })'
           style="float: right;color: white;font-size: 13pt;margin-top: 10px;margin-right: 10px">
            <span style="color: white" class="glyphicon glyphicon-user"></span> ${sessionScope.user.userName}
        </a>
        <a class="dream" id="logout" href="javascript:window.location.href='/page/logout'"
           style="float: right;color: white;font-size: 13pt;margin-top: 10px;margin-right: 10px">
            <span style="color: white" class="glyphicon glyphicon-log-out"></span> 退出
        </a>
    </c:if>
</nav>
<br>
<br>

<div class="component-poster-detail">
    <!--bt-->
    <div class="container">
        <br><br>
        <div class="row">
            <!--电影海报和其他信息/喜欢播放提交按钮 -->
            <div class="col-sm-4">
                <div class="row">
                    <!--最左侧电影图片和星星评分控件 -->
                    <div class="col-md-7 col-sm-12">
                        <div class="movie-poster">
                            <!--电影图片 -->
                            <a title="${sessionScope.movieDescription.movieName}">
                                <img src="${sessionScope.movieDescription.moviePicture}" alt="${sessionScope.movieDescription.movieName}" style="width: 100%">
                            </a>
                            <!-- 评分控件，如果用户登录且未评分显示 -->
                            <%--<c:if test="${sessionScope.user != null&&sessionScope.userstar==null}">--%>
                            <c:if test="${sessionScope.userstar==null}">
                                <div id="evalutiondiv">
                                    <input id="Evaluation">
                                </div>
                            </c:if>
                        </div>
                    </div>
                    <!--左侧电影信息 -->
                    <div class="col-md-5 col-sm-12 film-stats" style="">
                        <!--电影信息div -->
                        <div><font style="font-size: 11pt" face="宋体">片名: </font>
                            <span style="font-size: 9pt">
                                <font face="宋体">
                                    ${sessionScope.movieDescription.movieName}
                                </font>
                            </span>
                        </div>
                        <div><font style="font-size: 11pt" face="宋体">类型: </font>
                            <span style="font-size: 9pt">
                                <font face="宋体">
                                    ${sessionScope.movieDescription.typeList}
                                </font>
                            </span>
                        </div>
                        <div><font style="font-size: 11pt" face="宋体">上映日期: </font>
                            <span style="font-size: 9pt">
                                <font face="宋体">
                                    ${sessionScope.movieDescription.movieDate}
                                </font>
                            </span>
                        </div>
                        <div><font style="font-size: 11pt" face="宋体">评价人数: </font>
                            <span style="font-size: 9pt">
                                <font face="宋体">
                                    ${sessionScope.movieDescription.historyHeat}
                                </font>
                            </span>
                        </div>
                        <div><font style="font-size: 11pt" face="宋体">评分:</font>
                            <span style="font-size: 9pt">
                                <font face="宋体">
                                    ${sessionScope.movieDescription.averageRating}
                                </font>
                            </span>
                        </div>
                        <div>
                            <input id="allstar" value="${sessionScope.movieDescription.averageRating}">
                        </div>
                        <!-- 用户评分，如果用户登录且评分过则显示评分信息 -->
                        <c:if test="${sessionScope.user != null&&sessionScope.userstar!=null}">
                            <div><font style="font-size: 11pt" face="宋体">我的评分:</font>
                                <span style="font-size: 9pt">${sessionScope.userstar.rating}</span></div>
                            <div><font style="font-size: 11pt" face="宋体">时间:</font>
                                <span style="font-size: 9pt">
                                    <jsp:useBean id="dateValue" class="java.util.Date"/> <!-- 通过jsp:userBean标签引入java.util.Date日期类 -->
                                    <jsp:setProperty name="dateValue" property="time" value="${sessionScope.userstar.timestamp}"/> <!--如果是单位问题导致出错的话，可以这样解决：单位为秒，就按标签这样写就对了；单位为毫秒，须在value后补3个0--><!-- 使用jsp:setProperty标签将时间戳设置到Date的time属性中 -->
                                    <fmt:formatDate value="${dateValue}" pattern="yyyy-MM-dd HH:mm:ss"/> <!-- 转换格式 -->
                                </span>
                            </div>
                        </c:if>
                        <br>

                        <!--喜欢按钮，如果用户登录则显示 -->
                        <%--<c:if test="${sessionScope.user != null}">--%>
                            <!-- 这里使用a标签的作用主要是为了当鼠标移动到这个位置时会将箭头变成手的效果 -->
                        <a  class="btn btn-default btn-md" id="liked" onclick="likedclick()" >
                            <span class="glyphicon glyphicon-heart"></span>
                            <span class="fm-opt-label">喜欢</span>
                        </a>
                        <%--</c:if>--%>
                        <br><br>

                        <!--播放按钮 -->
                        <c:if test="${sessionScope.movieDescription.playId != ''}">
                            <a class="btn btn-default btn-md"
                               href="https://www.iqiyi.com/${sessionScope.movieDescription.playId}.html"
                               target="_Blank">
                                <span class="glyphicon glyphicon-play-circle"></span>
                                <span class="fm-opt-label"> 播放</span>
                            </a>
                        </c:if>
                        <c:if test="${sessionScope.movieDescription.playId == ''}">
                            <a class="btn btn-default btn-md"
                               href="http://so.iqiyi.com/so/q_${sessionScope.movieDescription.movieName}"
                               target="_Blank">
                                <span class="glyphicon glyphicon-play-circle"></span>
                                <span class="fm-opt-label"> 播放</span>
                            </a>
                        </c:if>
                        <br><br>

                        <!--提交按钮，如果用户登录且未评分显示 -->
                        <%--<c:if test="${sessionScope.user != null&&sessionScope.userstar==null}">--%>
                        <c:if test="${sessionScope.userstar==null}">
                            <button id="submitevalutionstar" class="btn btn-default btn-md" onclick="ratingSubmit()">
                                <span class="glyphicon glyphicon-ok-circle"></span><span class="fm-opt-label"> 提交</span>
                            </button>
                        </c:if>
                    </div>
                </div>
            </div>

            <!--右侧电影信息等栏目 -->
            <div class="col-sm-6" style="margin-left: 70px;width: 60%">
                <!-- 分享链接栏 -->
                <div id="atstbx2" style="float: right;margin-top: -7%" class="at-share-tbx-element addthis-smartlayers addthis-animated at4-show">
                    <div class="at-share-btn-elements" style="float: right;margin-top: 43%">
                        <!--微博分享-->
                        <a id="wbshareBtn" href="javascript:weiboShare()" target="_blank" class="at-icon-wrapper at-share-btn at-svc-email" style=" border-radius: 0%;">
                            <img style="line-height: 32px; height: 32px; width: 32px;" src="https://www.vmovier.com/Public/Home/images/baidu-weibo-v2.png?20160109"/>
                        </a>
                        <!--qq空间分享-->
                        <%--<a id="qzoneshareBtn" href="javascript:qzoneShare()" target="_blank" class="at-icon-wrapper at-share-btn at-svc-bitly" style=" border-radius: 0%;">--%>
                            <%--<img style="line-height: 32px; height: 32px; width: 32px;" src="https://www.vmovier.com/Public/Home/images/baidu-qzone-v2.png?20160109"/>--%>
                        <%--</a>--%>
                        <!--qq分享-->
                        <a id="qqshareBtn" href="javascript:qqShare()" target="_blank" class="at-icon-wrapper at-share-btn at-svc-bitly" style=" border-radius: 0%;">
                            <img style="line-height: 32px; height: 32px; width: 32px;" src="https://www.vmovier.com/Public/Home/images/baibu-qq-v2.png?20160109"/>
                        </a>
                    </div>
                </div>

                <!-- Nav tabs 信息切换栏-->
                <ul class="nav nav-tabs" role="tablist">
                    <li role="presentation" class="active" style="text-align: center">
                        <a href="#resource"
                           aria-controls="resource"
                           data-toggle="tab"
                           aria-expanded="false">
                            <strong>电影资源</strong>
                        </a>
                    </li>
                </ul>

                <!-- 标签页面板 -->
                <!-- Tab panes -->
                <div class="tab-content">
                    <!--电影资源-->
                    <div role="tabpanel" class="tab-pane fade active in" id="resource">
                        <br>
                        <div class="全网搜索 clear none" id="qlink" style="display: block;">
                            <fieldset class="qBox qwatch">
                                <legend>《<span class="keyword">${sessionScope.movieDescription.movieName}</span>》外链观看
                                </legend>
                                <a href="http://so.iqiyi.com/so/q_${sessionScope.movieDescription.movieName}"
                                   target="_blank" rel="nofllow">爱奇艺</a>
                                <a href="http://v.sogou.com/v?query=${sessionScope.movieDescription.movieName}"
                                   target="_blank" rel="nofllow">搜狗影视</a>
                                <a href="http://www.quankan.tv/index.php?s=vod-search-wd-${sessionScope.movieDescription.movieName}.html"
                                   target="_blank" rel="nofllow">全看网</a>
                                <a href="http://www.soku.com/search_video/q_${sessionScope.movieDescription.movieName}?f=1&kb=020200000000000__${sessionScope.movieDescription.movieName}"
                                   target="_blank" rel="nofllow">优酷</a>
                                <a href="http://www.acfun.cn/search/?#query=${sessionScope.movieDescription.movieName}"
                                   target="_blank" rel="nofllow">AcFun</a>
                                <a href="http://search.bilibili.com/all?keyword=${sessionScope.movieDescription.movieName}"
                                   target="_blank" rel="nofllow">Bilibili</a></fieldset>
                            <fieldset class="qBox qdown">
                                <legend>《<span class="keyword">${sessionScope.movieDescription.movieName}</span>》资源下载&nbsp;
                                </legend>
                                <c:if test="${sessionScope.movieDescription.download != ''}">
                                    <a href="${sessionScope.movieDescription.download}" mc="${sessionScope.movieDescription.mc}" target="_blank">迅雷下载</a>
                                </c:if>
                                <c:if test="${sessionScope.movieDescription.download == ''}">
                                    <a href="http://www.atugu.com/infos/${sessionScope.movieDescription.movieName}" target="_blank" rel="nofllow">迅雷下载</a>
                                </c:if>
                                <a href="http://www.xilinjie.com/s?t=pan&amp;q=${sessionScope.movieDescription.movieName}"
                                   target="_blank" rel="nofllow">搜网盘</a>
                                <a href="https://www.ziyuanmao.com/#/result/${sessionScope.movieDescription.movieName}"
                                   target="_blank" rel="nofllow">资源猫</a>
                                <a href="https://www.ziyuanmao.com/#/result/${sessionScope.movieDescription.movieName}"
                                   target="_blank" rel="nofllow">资源猫</a>
                                <a href="http://www.zimuku.cn/search?q=${sessionScope.movieDescription.movieName}"
                                   target="_blank" rel="nofllow">字幕库</a>
                                <a href="http://www.zimuzu.tv/search?keyword=${sessionScope.movieDescription.movieName}"
                                   target="_blank" rel="nofllow">字幕组</a></fieldset>
                            <fieldset class="qBox qdata">
                                <legend>《<span class="keyword">${sessionScope.movieDescription.movieName}</span>》资料介绍&nbsp;
                                </legend>
                                <a href="http://baike.baidu.com/search/word?word=${sessionScope.movieDescription.movieName}"
                                   target="_blank" rel="nofllow">百度百科</a>
                                <a href="http://www.baike.com/wiki/${sessionScope.movieDescription.movieName}"
                                   target="_blank" rel="nofllow">互动百科</a>
                                <a href="https://zh.wikipedia.org/wiki/${sessionScope.movieDescription.movieName}"
                                   target="_blank" rel="nofllow">维基百科</a>
                                <a href="https://en.wikipedia.org/wiki/${sessionScope.movieDescription.movieName}"
                                   target="_blank" rel="nofllow">Wiki</a></fieldset>
                            <fieldset class="qBox qreview">
                                <legend>《<span class="keyword">${sessionScope.movieDescription.movieName}</span>》评分影评
                                </legend>
                                <a href="https://m.douban.com/search/?query=${sessionScope.movieDescription.movieName}&amp;type=movie"
                                   target="_blank" rel="nofllow">豆瓣电影</a>
                                <a href="http://search.mtime.com/search/?q=${sessionScope.movieDescription.movieName}"
                                   target="_blank" rel="nofllow">时光网</a>
                                <a href="http://www.imdb.com/find?q=${sessionScope.movieDescription.movieName}"
                                   target="_blank" rel="nofllow">IMDB</a>
                                <a href="https://www.rottentomatoes.com/search/?search=${sessionScope.movieDescription.movieName}"
                                   target="_blank" rel="nofllow">烂番茄</a></fieldset>
                        </div>
                    </div>
                </div>
            </div>
        </div> <!-- /row -->

        <div style="height: 15px;"></div>
        <!--电影推荐模块-->
        <div class="row" style="margin-left: 5px">
            <!--推荐电影-->
            <div>
                <h1>
                   <font face="宋体">
                       猜你喜欢
                   </font>
                </h1>
            </div>
            <div></div>
            <!-- 推荐电影模块 -->
            <div class="list-wp">
                <div id="list" class="list">
                    <!-- 初始化或刷新页面用C:FOREACH加载电影 -->
                    <c:if test="${sessionScope.SimilarMovies != null}">
                        <c:forEach var="item"   items="${sessionScope.SimilarMovies}">
                            <div style="float: left;width: 150px;margin-right: 50px;">
                                <a class="item" name="imgitem" id="${item.movieId}" onclick='$.post("/Customer/Description",{movieId:$(this).attr("id")}, function (data) {
                                    if (data == "success") {
                                        location.href = "/MovieDescription"
                                    } else {
                                        //出错处理页面
                                    }
                                })' title="${item.movieName}">
                                    <div class="cover-wp">
                                        <img src="${item.moviePicture}" alt="${item.movieName}" style="width: 100%;height: auto;"/>
                                    </div>
                                </a>
                                <p></p><p></p>
                                <p>
                                    <span style="color: #258BE9">${item.movieName}</span>
                                    <span class="rating_nums">
                                        <strong>${item.averageRating}</strong>
                                    </span>
                                </p>
                            </div>
                        </c:forEach>
                    </c:if>
                </div>
            </div>
        </div> <!-- /row -->

        <div style="height: 15px;"></div>
        <div style="height: 15px;"></div>
        <!--影片评论模块-->
        <div class="row" style="margin-left: 5px;">
            <div>
                <h1>
                    <font face="宋体">
                        影评区
                    </font>
                </h1>
            </div>
            <div></div>
            <div class="row" style="width: auto;">
                <div style="float: left;margin-left: 13px;">
                    <!-- 滑动标签<li> -->
                    <ul id="list-title">
                        <li class="list-item list-item-checked" id="allReviews">
                            全部
                        </li>
                        <li class="list-item" id="newReviews">
                            最新
                        </li>
                    </ul>
                </div>
                <div style="float: right;margin-right: 40px;">
                        <span id="write_review_btn" class="list-item list-item-checked">
                        写评论
                    </span>
                </div>
            </div>

            <!--评论列表-->
            <!--全部评论-->
            <div class="tab-content">
                <div class="tab-pane active" id="all-reviews">
                    <div>
                        <div class="List-item">
                            <input type="hidden" id="review_content" value="${sessionScope.reviews}"/>
                            <p class="ul first"></p>
                            <table id="all_reviews" width="100%" class="">
                                <c:if test="${sessionScope.reviews == null || sessionScope.reviews.size() == 0}">
                                    <tr id="no-review" class="item" style="padding-top: 20px">
                                        <td heigth="auto" width="auto" valign="top">
                                            <span style="font-size: 20px;margin-top: auto;margin-left: 40%;">
                                                还没有用户发布过评论哦
                                            </span>
                                        </td>
                                    </tr>
                                </c:if>
                                <c:if test="${sessionScope.reviews != null && sessionScope.reviews.size()>0}">
                                    <c:forEach var="item" items="${sessionScope.reviews}">
                                        <tr class="item" style="padding-top: 20px">
                                            <td width="100" valign="top">
                                                <div style="height: auto;width: 100%;">
                                                    <span style="float: left;">
                                                        <font style="font-size: 15pt;" face="宋体" color="#258BE9">
                                                            <b>${item.user.userName}</b>
                                                        </font>
                                                    </span>
                                                    <div style="float: right;margin-right: 22px;margin-top: 9px;">
                                                        <span class="zan">
                                                            <b>${item.count}</b>
                                                        </span>
                                                        <a class="no_decoration" value="${item.user.userId}" >
                                                            有用
                                                        </a>
                                                    </div>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr class="item" style="padding-top: 20px">
                                            <td width="100" valign="top">
                                                <div style="height: auto;width: auto;margin-left: 15px;margin-right: 45px;">
                                                    <span style="float: left;">
                                                        <font style="font-size: 10pt" face="宋体">
                                                            ${item.content}
                                                        </font>
                                                    </span>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr class="item" style="padding-top: 15px">
                                            <td width="100" valign="top">
                                                <div style="height: 100%;margin-right: 22px;">
                                                    <span style="float: right;">
                                                        评论时间:
                                                        <jsp:useBean id="allReviewDate" class="java.util.Date"/> <!-- 通过jsp:userBean标签引入java.util.Date日期类 -->
                                                        <jsp:setProperty name="allReviewDate" property="time" value="${item.timestamp}"/> <!--如果是单位问题导致出错的话，可以这样解决：单位为秒，就按标签这样写就对了；单位为毫秒，须在value后补3个0--><!-- 使用jsp:setProperty标签将时间戳设置到Date的time属性中 -->
                                                        <fmt:formatDate value="${allReviewDate}" pattern="yyyy-MM-dd HH:mm:ss"/> <!-- 转换格式 -->
                                                    </span>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:if>
                            </table>
                        </div>
                    </div>
                </div>
            </div>

            <!--最新评论-->
            <div class="tab-content">
                <div class="tab-pane" id="new-reviews">
                    <div>
                        <div class="List-item">
                            <p class="ul first"></p>
                            <table id="new_reviews" width="100%" class="">
                                <c:if test="${sessionScope.reviews == null || sessionScope.reviews.size() == 0}">
                                    <tr id="no-review" class="item" style="padding-top: 20px">
                                        <td heigth="auto" width="auto" valign="top">
                                            <span style="font-size: 20px;margin-top: auto;margin-left: 40%;">
                                                还没有用户发布过评论哦
                                            </span>
                                        </td>
                                    </tr>
                                </c:if>
                                <c:forEach var="item" items="${sessionScope.reviews}">
                                    <tr class="item" style="padding-top: 20px">
                                        <td width="100" valign="top">
                                            <div style="height: auto;">
                                                <span style="float: left;">
                                                     <font style="font-size: 15pt;" face="宋体" color="#258BE9">
                                                        <b>${item.user.userName}</b>
                                                    </font>
                                                </span>
                                                <div style="float: right;margin-right: 22px;margin-top: 9px;">
                                                    <span class="zan">
                                                        <b>${item.count}</b>
                                                    </span>
                                                    <a class="no_decoration" value="${item.user.userId}" >
                                                        有用
                                                    </a>
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr class="item" style="padding-top: 15px">
                                        <td width="100" valign="top">
                                            <div style="height: auto;width: auto;margin-left: 15px;margin-right: 45px;">
                                                <span style="float: left;">
                                                    <font style="font-size: 10pt" face="宋体">
                                                        ${item.content}
                                                    </font>
                                                </span>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr class="item" style="padding-top: 15px">
                                        <td width="100%" valign="top">
                                            <div style="height: auto;margin-right: 22px;">
                                                <span style="float: right;">
                                                    评论时间:
                                                    <jsp:useBean id="newReviewDate" class="java.util.Date"/> <!-- 通过jsp:userBean标签引入java.util.Date日期类 -->
                                                    <jsp:setProperty name="newReviewDate" property="time" value="${item.timestamp}"/> <!--如果是单位问题导致出错的话，可以这样解决：单位为秒，就按标签这样写就对了；单位为毫秒，须在value后补3个0--><!-- 使用jsp:setProperty标签将时间戳设置到Date的time属性中 -->
                                                    <fmt:formatDate value="${newReviewDate}" pattern="yyyy-MM-dd HH:mm:ss"/> <!-- 转换格式 -->
                                                </span>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div><!--影评区-->
    </div> <!-- /container -->
</div>
<br><br><br>
<br><br><br>

<!--底部 -->
<div class="footer">
    <div class="tip">
        Copyright © 2019-2020 &nbsp;&nbsp;
        <p>声明：本站不提供视频观看，所有视频播放将跳转到第三方网站</p>
    </div>
</div>

<!--写评论模态框-->
<div class="modal fade" id="writeReview" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h2>写评论</h2>
            </div>
            <div class="modal-body">
                <textarea cols="92" rows="5" id="textarea" name="textarea" placeholder="#在这里写影评，影评不能超过500个字符"></textarea> <!--  PS:textarea的头尾标签要在同一行，不然会产生默认的空格  -->
            </div>
            <div class="count_area" align="center">
                <span class="leave_count">已输入字符数：0</span>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">放弃</button>
                <button type="button" class="btn btn-primary" onclick="publishReview()">发布</button>
            </div>
        </div>
    </div>
</div>

<!--登录模态框-->
<div class="modal fade" id="login" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h2>用户登录</h2>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="loginForm">
                    <div class="form-group">
                        <div class="col-sm-12">
                            <input type="text" class="form-control" id="login_email" placeholder="邮箱账号" name="email">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <input type="password" class="form-control" id="login_password" placeholder="密 码" name="userPassword">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" onclick="MODALLOGIN.modalLogin()">登录</button>
            </div>
        </div>
    </div>
</div>

<%--当在搜索框中输入搜索内容时，自动以列表的形式提示匹配的电影--%>
<%--<div class="suggest" id="search-suggest" style="display: none; top:43px;left: 136px;" >--%>
    <%--<ul id="search-result">--%>
    <%--</ul>--%>
<%--</div>--%>

</body>


<script>
    $(function () {

        //全部评论
        $("#all-reviews").on("click",".no_decoration",function () {
            if (${sessionScope.user==null}){
                $("#login").modal("show");
            }else{
                var userId = $(this).attr("value");
                // 在外部获取元素，再在回调函数中调用。
                // 直接在回调函数中调用$(this).siblings(".zan").html(text);$(this).toggleClass("like");
                // 不起作用，因为这样的写法在回调函数中根本获取不到元素。
                var html = $(this).siblings(".zan");
                var a = $(this);
                $.ajax({
                    type: "post",
                    url: "/updateUsefulCount",
                    data: {
                        "specifiedUserId":"${sessionScope.user.userId}",
                        "userId":userId,
                        "movieId":"${sessionScope.movieDescription.movieId}"
                    }, success: function (data) {
                        if(data.status==200) {
                            html.html(data.data.count);
                        }else {
                            alert('您已经点过"有用了"');
                        }
                    }
                });
            }
        });

        //最新评论
        $("#new-reviews").on("click",".no_decoration",function () {
            if (${sessionScope.user==null}){
                $("#login").modal("show");
            }else{
                var userId = $(this).attr("value");
                var html = $(this).siblings(".zan");
                var a = $(this);
                $.ajax({
                    type: "post",
                    url: "/updateUsefulCount",
                    data: {
                        "specifiedUserId":"${sessionScope.user.userId}",
                        "userId":userId,
                        "movieId":"${sessionScope.movieDescription.movieId}"
                    }, success: function (data) {
                        if (data.status==200) {
                            html.html(data.data.count);
                        }else{
                            alert('您已经点过"有用"了');
                        }
                    }
                });
            }
        });

    });
</script>

<!--评论按钮选择 全部、最新、写评论按钮、关闭写评论模态窗口-->
<script type="text/javascript">

    $(function () {
        //全部评论
        $("#allReviews").click(function () {
            if($(this).attr("class").indexOf("list-item-checked")>0){
            }else {
                $.post("/selectMovieReviews",{
                    "movieId":"${sessionScope.movieDescription.movieId}",
                    "sort":0
                },function (data) {
                    if (data.status==200){
                        $("#new-reviews").removeClass("active");
                        $("#newReviews").removeClass("list-item-checked");
                        $("#all-reviews").addClass("active");
                        $("#allReviews").addClass("list-item-checked");
                        if(data.data.length>0){
                            $("#all_reviews").children().remove();
                            //局部刷新评论模块的内容
                            var length = data.data.length;
                            var items = data.data;
                            for(var i=0;i<length;i++){
                                var headHtml = $("#review-tmpl").html();
                                headHtml = headHtml.replace(/{userId}/g,items[i].user.userId);
                                headHtml = headHtml.replace(/{userName}/g,items[i].user.userName);
                                headHtml = headHtml.replace(/{count}/g, items[i].count);
                                headHtml = headHtml.replace(/{content}/g, items[i].content);
                                headHtml = headHtml.replace(/{timestamp}/g, dateTransform(items[i].timestamp));
                                $("#all_reviews").append(headHtml);
                            }
                        }
                    }else{
                        alert("按钮失效了");
                    }
                });
            }
        });

        //最新评论
        $("#newReviews").click(function () {
            if($(this).attr("class").indexOf("list-item-checked")>0){
            }else {
                $.post("/selectMovieReviews",{
                    "movieId":"${sessionScope.movieDescription.movieId}",
                    "sort":1
                },function (data) {
                    if (data.status==200){
                        $("#all-reviews").removeClass("active");
                        $("#allReviews").removeClass("list-item-checked");
                        $("#new-reviews").addClass("active");
                        $("#newReviews").addClass("list-item-checked");
                        if(data.data.length>0){
                            $("#new_reviews").children().remove();
                            //局部刷新评论模块的内容
                            var length = data.data.length;
                            var items = data.data;
                            for(var i=0;i<length;i++){
                                var headHtml = $("#review-tmpl").html();
                                headHtml = headHtml.replace(/{userId}/g,items[i].user.userId);
                                headHtml = headHtml.replace(/{userName}/g,items[i].user.userName);
                                headHtml = headHtml.replace(/{count}/g, items[i].count);
                                headHtml = headHtml.replace(/{content}/g, items[i].content);
                                headHtml = headHtml.replace(/{timestamp}/g, dateTransform(items[i].timestamp));
                                $("#new_reviews").append(headHtml);
                            }
                        }
                    }else{
                        alert("按钮失效了");
                    }
                });
            }
        });
    });

    //用户未登录，点击后弹出登录模态框，否则弹出写评论模态框
    $("#write_review_btn").click(function () {
        if(${sessionScope.user==null}){
            $("#login").modal("show");
        } else if(${sessionScope.userReview==null}){//未评论过才弹出模态框
            $("#writeReview").modal("show");
        } else{
            alert("你已经评论过这部电影了哦");
        }
    });

    //登录操作
    var MODALLOGIN = {
        checkInput: function () {

            if (!$("#login_email").val()) {
                alert("请输入账号");
                return false;
            }
            if ($("#login_email").val() && !$("#login_password").val()) {
                alert("请输入密码");
                return false;
            }
            return true;
        },
        doLogin: function () {
            $.post("/customer/login", {
                "email":$("#login_email").val(),
                "userPassword":$("#login_password").val(),
                "movieId":'${sessionScope.movieDescription.movieId}'
            }, function (data) {
                if (data.status == 200) {
                    $('#login').modal("hide");
                    window.location.href=window.location.href
                } else {
                    alert("登录失败，原因是：" + data.msg);
                    $("#login_email").val(null);
                    $("#login_password").val(null);
                }
            });
        },
        modalLogin: function () {
            if (this.checkInput()) {
                this.doLogin();
            }
        }
    };

    //实时统计影评字符数，输入不能超过500个字符
    $("#textarea").bind("keyup",function () {
        var input_count = $("#textarea").val().length;
        if (input_count>500){
            alert("影评字符数不能大于500");
        }else{
            $(".count_area").children().remove();
            var html = $("#count-tmpl").html();
            html = html.replace(/{input_count}/g,"已输入字符数为："+input_count);
            $(".count_area").append(html);
        }
    });
    
    //发布评论
    function publishReview() {
        var sort = 0;
        if ($("#allReviews").attr("class").indexOf("list-item-checked")>0){
            sort = 0;
        }else {
            sort = 1;
        }
        var textarea = $("#textarea").val();
        if(textarea==""||textarea==null){
            alert("评论内容不能为空");
        }else if (textarea.length>0&&textarea.length<500){
            $.post("/insertMovieReview",{
                "userId":"${sessionScope.user.userId}",
                "movieId":"${sessionScope.movieDescription.movieId}",
                "content":$("#textarea").val(),
                "sort":sort
            },function (data) {
                if (data.status==200){
                    alert("发布成功");
                    //清空影评区
                    $("#textarea").val(null);
                    //已输入字符数归0
                    $(".count_area").children().remove();
                    var html = $("#count-tmpl").html();
                    html = html.replace(/{input_count}/g,"已输入字符数为："+0);
                    $(".count_area").append(html);

                    $('#writeReview').modal("hide");
                    if(data.data!=null&&data.data.length>0){
                        if (sort==0) {
                            $("#all_reviews").children().remove();
                            //局部刷新评论模块的内容
                            var length = data.data.length;
                            var items = data.data;
                            for (var i = 0; i < length; i++) {
                                var headHtml = $("#review-tmpl").html();
                                headHtml = headHtml.replace(/{userId}/g,items[i].user.userId);
                                headHtml = headHtml.replace(/{userName}/g, items[i].user.userName);
                                headHtml = headHtml.replace(/{count}/g, items[i].count);
                                headHtml = headHtml.replace(/{content}/g, items[i].content);
                                headHtml = headHtml.replace(/{timestamp}/g, dateTransform(items[i].timestamp));
                                $("#all_reviews").append(headHtml);
                            }
                        }else{
                            $("#new_reviews").children().remove();
                            //局部刷新评论模块的内容
                            var length = data.data.length;
                            var items = data.data;
                            for (var i = 0; i < length; i++) {
                                var headHtml = $("#review-tmpl").html();
                                headHtml = headHtml.replace(/{userId}/g,items[i].user.userId);
                                headHtml = headHtml.replace(/{userName}/g, items[i].user.userName);
                                headHtml = headHtml.replace(/{count}/g, items[i].count);
                                headHtml = headHtml.replace(/{content}/g, items[i].content);
                                headHtml = headHtml.replace(/{timestamp}/g, dateTransform(items[i].timestamp));
                                $("#new_reviews").append(headHtml);
                            }
                        }
                    }
                }else {
                    alert("发布失败");
                    $('#writeReview').modal("hide");
                }
            });
        }else {
            alert("影评不能多于500个字符");
        }
    }

</script>

<!--时间格式转换-->
<script>
    function dateTransform(timestamp) {
        var date = new Date(timestamp);//如果date为13位不需要乘1000
        var Y = date.getFullYear() + '-';
        var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
        var D = (date.getDate() < 10 ? '0' + (date.getDate()) : date.getDate()) + ' ';
        var h = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':';
        var m = (date.getMinutes() <10 ? '0' + date.getMinutes() : date.getMinutes()) + ':';
        var s = (date.getSeconds() <10 ? '0' + date.getSeconds() : date.getSeconds());
        return Y+M+D+h+m+s;
    }
</script>


<!--评分提交-->
<script>
    function ratingSubmit() {

        if (${sessionScope.user==null}){
            $("#login").modal("show");
        }else{
            $.post("/getstar",{
                "userId":'${sessionScope.user.userId}',
                "movieId":'${sessionScope.movieDescription.movieId}',
                "star":$("#Evaluation").val()
            },function (data) {
                if (data=="success"){
                    alert("提交成功");
                    window.location.href=window.location.href;
                }else{
                    alert("提交失败")
                }
            });
        }
}
</script>

<!-- 喜欢按钮事件-->
<script>
    function  likedclick() {
        if (${sessionScope.user==null}){
            $("#login").modal("show");
        }else{
            var color=$("#liked").css("background-color");
            var boollike;
            if(color=="rgb(230, 230, 230)")
                boollike=0;  //定义0为未收藏
            else
                boollike=1;
            $.post("/likedmovie", {"movieId":"${sessionScope.movieDescription.movieId}","boollike":boollike,"userId":"${sessionScope.user.userId}"},function (data) {
                if(data=="success") {
                    if (boollike == 0)
                        alert("收藏成功");
                    else
                        alert("取消收藏");
                } else {
                    alert("按钮失效")
                }
            });
            //切换按钮的背景颜色
            $("#liked").toggleClass('likedactive');
        }
    }
</script>

<%--搜索栏--%>
<script>
//    $(document).ready(function () {
//        $("#inp-query").keyup(function () {
//
//            $("#search-result").hide();
//
//            <!--获取输入文本框的宽度-->
//            var width = document.getElementById("inp-query").offsetWidth + "px";
//
//            //在搜索框输入数据，提示相关搜索信息
//            var searchText = $("#inp-query").val();
//
//            if (searchText == null || searchText == "") {
//                $("#search-result").children().remove();
//                $("#search-result").hide();
//                return;
//            }
//            <!--使提示列表盒子显示出来，并设定它的宽度为搜索输入框的宽度，通过css()方法设置-->
//            $("#search-suggest").show().css({
//                width: width
//            });
//            <!--删除列表子元素-->
//            $("#search-result").children().remove();
//
//            $.post("/searchLikeName", {"search_text": searchText}, function (data) {
//                if (data.status == 200) {
//                    if (data.data.length != 0) {
//                        $.each(data.data, function (i, item) {
//                            var headHtml = $("#movie-tmpl").html();
//                            headHtml = headHtml.replace(/{id}/g, item.movieId);
//                            headHtml = headHtml.replace(/{cover}/g, item.moviePicture);
//                            headHtml = headHtml.replace(/{moviename}/g, item.movieName);
//                            headHtml = headHtml.replace(/{showyear}/g, item.movieDate);
//                            headHtml = headHtml.replace(/{averating}/s, item.averageRating);
//                            $("#search-result").append(headHtml);
//                        })
//                    }
//                }
//            })
//        });
//    })
</script>

<!--  搜索按钮  -->
<script>
function searchWebSite() {
    var search_text = $("#inp-query").val();
    //获取新的提示信息前需先将<ul>的所有直接子元素即<li>全都删除
    $("#search-result").children().remove();
    $.post("/searchByName", {"search_text": search_text}, function (data) {
        if (data == null) {
            alert("请输入片名")
        } else {
            if (data.status == 200) {
                location.href = "/searchResult"
            } else {
                location.href = "/searchResult"
            }
        }
    });
}
</script>

<%--影评已输入字符数模板--%>
<script type="text/tmpl" id="count-tmpl">
    <span class="leave_count">{input_count}</span>
</script>

<!--电影评论模板-->
<script type="text/tmpl" id="review-tmpl">
    <tr class="item" style="padding-top: 20px">
        <td width="100" valign="top">
            <div style="height: auto;">
                <span style="float: left;">
                    <font style="font-size: 15pt;" face="宋体" color="#258BE9">
                        <b>{userName}</b>
                    </font>
                </span>
                <div style="float: right;height: auto;margin-right: 22px;margin-top: 9px;">
                    <span class="zan">
                        <b>{count}</b>
                    </span>
                    <a class="no_decoration" value="{userId}" >
                        有用
                    </a>
                </div>
            </div>
        </td>
    </tr>
    <tr class="item" style="padding-top: 20px">
        <td width="100" valign="top">
            <div style="height: auto;width: auto;margin-left: 15px;margin-right: 45px;">
                <span style="float: left;">
                    <font style="font-size: 10pt" face="宋体">
                        {content}
                    </font>
                </span>
            </div>
        </td>
    </tr>
    <tr class="item" style="padding-top: 15px">
        <td width="100" valign="top">
            <div style="height: auto;margin-right: 22px;">
                <span style="float: right;">
                    评论时间:
                    {timestamp}
                </span>
            </div>
        </td>
    </tr>
</script>

<%--智能提示框电影信息模板--%>
<script type="text/tmpl"  id="movie-tmpl">
 <li id="searchResult">
   <div>
      <a value="{id}" style="text-decoration:none" onclick='javascript:$.post("/Customer/Description",{movieId:$(this).attr("value")}, function (data) {
            if (data=="success") {
                location.href = "/MovieDescription"
            } else {
                //出错处理页面
            }
        })'>
         <div style="float:left">
            <img src="{cover}" style="width:80px;height:120px">
         </div>
         <div  style="padding:12px">
            <span>&nbsp;&nbsp;&nbsp;&nbsp;电影名称：{moviename}</span>
            <br>
            <span>&nbsp;&nbsp;&nbsp;&nbsp;上映时间:{showyear}</span>
             <br>
            <span>&nbsp;&nbsp;&nbsp;&nbsp;评分：{averating}</span>
         </div>
       </a>
   </div>
 </li>
</script>

<!-- 分享连接栏-->
<script>
    <%--定义函数--%>
    function qzoneShare(){
        var qzone_shareBtn = document.getElementById("qzoneshareBtn");
        var qzone_url = document.URL;
        <%--var qzone_title = "电影：${sessionScope.movieDescription.movieName}（发现好电影）";--%>
        <%--var qzone_pic = "${sessionScope.movieDescription.moviePicture}";--%>
        <%--var qzone_language = "zh_cn";--%>
//        qzone_shareBtn.setAttribute("href","https://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?url="+qzone_url+"?sharesource=qzone"+
//            "&title="+qzone_title+"&pics="+qzone_pic+"&summary="+"分享好电影"+"&language="+qzone_language);
        qzone_shareBtn.setAttribute("href","https://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?url="+qzone_url+"?sharesource=qzone");
    }
//    调用上面定义的函数
//    qzoneShare();

    function qqShare(){
        var qq_shareBtn = document.getElementById("qqshareBtn");
        var qq_url = document.URL;
        var qq_title = "电影名称：${sessionScope.movieDescription.movieName}";
        var qq_pic = "${sessionScope.movieDescription.moviePicture}";
        var qq_language = "zh_cn";
        qq_shareBtn.setAttribute("href","http://connect.qq.com/widget/shareqq/index.html?url="+qq_url+"&sharesource=qzone"+"&title="+qq_title+"&pic="+qq_pic+"&language="+qq_language+"");
    }
    qqShare();

    function weiboShare(){
        var wb_shareBtn = document.getElementById("wbshareBtn");
        var wb_url = document.URL;
        var wb_appkey = "3118689721";
        var wb_title = "电影：${sessionScope.movieDescription.movieName}（发现好电影）";
        var wb_pic = "${sessionScope.movieDescription.moviePicture}";
        var wb_language = "zh_cn";
        wb_shareBtn.setAttribute("href","http://service.weibo.com/share/share.php?url="+wb_url+"&appkey="+wb_appkey+"&title="+wb_title+"&pic="+wb_pic+"&language="+wb_language+"");
    }
    weiboShare();

</script>

<!-- Go to www.addthis.com/dashboard to customize your tools -->
<script type="text/javascript" src="//s7.addthis.com/js/300/addthis_widget.js#pubid=kinointernational"></script>
</html>
