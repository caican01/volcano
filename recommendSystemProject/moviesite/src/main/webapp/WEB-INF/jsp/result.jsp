<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh" data-theme="light">
<head>
    <%--<meta charset="utf-8"/>--%>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="referrer" content="no-referrer" />
    <title data-react-helmet="true">发现好电影搜索结果页</title>
    <link rel="SHORTCUT ICON" href="/assets/img/knowU.ico"/>
    <link data-react-helmet="true" rel="prefetch" href="/assets/img/user_cover_image.jpg"/>
    <%--引入js文件--%>
    <script src="/assets/js/jquery.js"></script>
    <script src="/assets/js/bootstrap.min.js"></script>
    <script src="/assets/js/star-rating.min.js" type="text/javascript"></script>
    <%--引入css文件--%>
    <link href="/assets/css/star-rating.css" media="all" rel="stylesheet" type="text/css"/>
    <%--<link href="/assets/css/douban.main.css" rel="stylesheet"/>--%>
    <link href="/assets/css/bootstrap.css" rel="stylesheet">
    <link href="/assets/css/footer.css" rel="stylesheet" type="text/css">
    <style type="text/css">
        .component-poster-detail .nav-tabs > li {
            width: 50% !important;
        }
        #list-title {
            width: 318px;
            height: 56px;
            margin: 0;
            list-style-type: none;
            padding-left: 0;
        }
        .list-item {
            float: left;
            width: 120px;
            height: 50px;
            margin: 2px;
            line-height: 50px;
            font-size: 20px;
            text-align: center;
            border: none;
            cursor: pointer;
        }
        .list-item-checked {
            background-color: #70adff;
            color: #ffffff;
        }
    </style>
</head>

<body class="Entry-body">
<div id="root">
    <div data-reactid="5">
        <!-- 导航栏-->
        <nav class="navbar navbar-default" role="navigation" style="background-color: black;margin-bottom: 0%">
            <a class="navbar-brand" href="/" style="color: white">电影网</a>
            <div class="col-xs-4" style="margin-top: 8px">
                <input id="inp-query" class="form-control"  name="search_text"  maxlength="60" placeholder="请输入片名" onkeydown="if(event.keyCode==13) searchWebSite()">
                <%--<button id="query-btn" class="search_btn">搜索</button>--%>
            </div>
            <a class="navbar-brand" href="/index" style="color: white">电影主页</a>
            <!-- 判断用户是否登录-->
            <!--  用户未登录  -->
            <c:if test="${sessionScope.user == null}">
                <a class="dream" href="javascript:window.location.href='/page/register'" id="register" style="float: right;color: white;font-size: 13pt;margin-top: 10px;margin-right: 10px">
                    <span style="color: white" class="glyphicon glyphicon-user"></span> 注册</a>
                <a class="dream" href="javascript:window.location.href='/page/login'" style="float: right;color: white;font-size: 13pt;margin-top: 10px;margin-right: 10px">
                    <span style="color: white" class="glyphicon glyphicon-log-in"></span> 登录</a>
            </c:if>
            <!-- 用户已登录 -->
            <c:if test="${sessionScope.user != null}">
                <a class="dream" onclick='$.post("/page/profile",{"userId":"${sessionScope.user.userId}"}, function (data) {
                        if (data=="success") {
                        location.href = "/profile"
                        } else {
                        //出错处理页面
                        }
                        })' style="float: right;color: white;font-size: 13pt;margin-top: 10px;margin-right: 10px">
                    <span style="color: white" class="glyphicon glyphicon-user"></span> ${sessionScope.user.userName}
                </a>
                <a class="dream" id="logout" href="javascript:window.location.href='/page/logout'" style="float: right;color: white;font-size: 13pt;margin-top: 10px;margin-right: 10px"><span style="color: white" class="glyphicon glyphicon-log-in"></span>  退出</a>
            </c:if>
        </nav>
    </div>

    <!-- 导航栏下方的留白部分 -->
    <div style="overflow:hidden;transition:height 300ms ease-out;width: 100%;" class="ProfileHeader-contentBody" data-reactid="48">
        <div data-reactid="49" style="width: 100%">
            <div class="ProfileHeader-info" data-reactid="50" style="width: 100%">
                <div class="ProfileHeader-infoItem" data-reactid="51" style="width: 100%">
                    <div class="ProfileHeader-iconWrapper" data-reactid="52">
                    </div>
                    <div class="ProfileHeader-divider" data-reactid="53">
                    </div>
                    <div class="ProfileHeader-divider" data-reactid="54">
                    </div>
                    <div class="ProfileHeader-iconWrapper" data-reactid="55">
                    </div>
                    <div style="margin-left: 140px">
                        <div style="float: left;">
                            <h1>
                                <font face="宋体">
                                    搜索结果
                                </font>
                            </h1>
                        </div>
                        <div style="float: right;margin-right: 345px;">
                            <h1>
                                <font face="宋体">
                                    电影推荐
                                </font>
                            </h1>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!--搜索结果-->
    <div style="float: left;width: 460px;height: auto;margin-left: 145px;">
        <div class="Profile-lightList" data-reactid="329">
            <c:if test="${sessionScope.movieSearchResult == null || sessionScope.movieSearchResult.size()==0}">
                <div class="row" align="center">
                    <h2>查找不到该电影的相关资源,试试其他关键词吧</h2>
                </div>
            </c:if>
            <c:if test="${sessionScope.movieSearchResult != null && sessionScope.movieSearchResult.size()>0}">
                <c:forEach var="item" items="${sessionScope.movieSearchResult}">
                    <div class="List-item" data-reactid="166">
                        <p class="ul first"></p>
                        <table width="100%" class="">
                            <tr class="item">
                                <td width="100" valign="top">
                                    <a class="nbg" value="${item.movieId}" onclick='$.post("/Customer/Description",{movieId:$(this).attr("value")}, function (data) {
                                                                          if (data=="success") {
                                                                              location.href = "/MovieDescription"  //进入详情页
                                                                          } else {
                                                                              //出错进入出错处理页
                                                                          }
                                                                      })'
                                       title="${item.movieName}">
                                        <img src="${item.moviePicture}" width="75" alt="${item.movieName}" class=""/>
                                    </a>
                                </td>
                                <td valign="top">
                                    <div class="pl2">
                                        <a value="${item.movieId}" onclick='$.post("/Customer/Description",{movieId:$(this).attr("value")}, function (data) {
                                                                          if (data=="success") {
                                                                              location.href = "/MovieDescription"
                                                                          } else {
                                                                              //进入出错处理页
                                                                          }
                                                                       })' class="">
                                                ${item.movieName}
                                        </a>
                                        <div class="star clearfix">
                                            <p><span class="allstar40"></span></p>
                                            <p>
                                                <span class="rating_nums">评分: ${item.averageRating}</span>
                                            </p>
                                            <p>
                                                <span class="pl">热度: ${item.historyHeat}</span>
                                            </p>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                        </table>
                        <div id="collect_form_11584017"></div>
                    </div>
                </c:forEach>
            </c:if>
        </div>
    </div>

    <!--右侧推荐电影-->
    <div style="float: right;width: 360px;height: auto;margin-top: 15px;margin-right: 125px;">
        <!-- 滑动标签<li> -->
        <ul id="list-title">
            <li class="list-item list-item-checked" id="history">
                    历史热度榜
            </li>
            <li class="list-item" id="recent">
                    近期热度榜
            </li>
        </ul>

        <!-- 历史热度榜 -->
        <div class="tab-content">
            <div class="tab-pane active" id="history-heat">
                <div>
                    <c:if test="${sessionScope.historyHeatMovie != null}">
                        <c:forEach var="item" items="${sessionScope.historyHeatMovie}">
                            <div class="List-item">
                                <p class="ul first"></p>
                                <table width="100%" class="">
                                    <tr class="item">
                                        <td width="100" valign="top">
                                            <a class="nbg" value="${item.movieId}" onclick='$.post("/Customer/Description",{movieId:$(this).attr("value")}, function (data) {
                                                                          if (data=="success") {
                                                                              location.href = "/MovieDescription"  //进入详情页
                                                                          } else {
                                                                              //出错进入出错处理页
                                                                          }
                                                                      })'
                                               title="${item.movieName}">
                                                <img src="${item.moviePicture}" width="75" alt="${item.movieName}" class=""/>
                                            </a>
                                        </td>
                                        <td valign="top">
                                            <div class="pl2">
                                                <span>
                                                    片名:
                                                </span>
                                                <a value="${item.movieId}" target="_blank" onclick='$.post("/Customer/Description",{movieId:$(this).attr("value")}, function (data) {
                                                                          if (data=="success") {
                                                                              location.href = "/MovieDescription"
                                                                          } else {
                                                                              //进入出错处理页
                                                                          }
                                                                       })' class="">
                                                        ${item.movieName}
                                                </a>
                                                <div class="star clearfix">
                                                    <p>
                                                        <span class="allstar40"></span>
                                                    </p>
                                                    <p>
                                                        <span class="pl">上映日期: ${item.movieDate}</span>
                                                    </p>
                                                    <p>
                                                        <span class="rating_nums">评分: ${item.averageRating}</span>
                                                    </p>
                                                    <p>
                                                        <span class="pl">热度: ${item.historyHeat}</span>
                                                    </p>
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                        </c:forEach>
                    </c:if>
                </div>
            </div>
        </div>

        <!-- 近期热度榜 -->
        <div class="tab-content">
            <div class="tab-pane" id="recent-heat">
                <div>
                    <c:if test="${sessionScope.recentHeatMovie != null}">
                        <c:forEach var="item" items="${sessionScope.recentHeatMovie}">
                            <div class="List-item">
                                <p class="ul first"></p>
                                <table width="100%" class="">
                                    <tr class="item">
                                        <td width="100" valign="top">
                                            <a class="nbg" value="${item.movieId}" onclick='$.post("/Customer/Description",{movieId:$(this).attr("value")}, function (data) {
                                                                          if (data=="success") {
                                                                              location.href = "/MovieDescription"  //进入详情页
                                                                          } else {
                                                                              //出错进入出错处理页
                                                                          }
                                                                      })'
                                               title="${item.movie.movieName}">
                                                <img src="${item.movie.moviePicture}" width="75" alt="${item.movie.movieName}" class=""/>
                                            </a>
                                        </td>
                                        <td valign="top">
                                            <div class="pl2">
                                                <span>
                                                    片名:
                                                </span>
                                                <a value="${item.movieId}" target="_blank" onclick='$.post("/Customer/Description",{movieId:$(this).attr("value")}, function (data) {
                                                                          if (data=="success") {
                                                                              location.href = "/MovieDescription"
                                                                          } else {
                                                                              //进入出错处理页
                                                                          }
                                                                       })' class="">
                                                        ${item.movie.movieName}
                                                </a>
                                                <div class="star clearfix">
                                                    <p>
                                                        <span class="allstar40"></span>
                                                    </p>
                                                    <p>
                                                        <span class="pl">上映日期: ${item.movie.movieDate}</span>
                                                    </p>
                                                    <p>
                                                        <span class="rating_nums">评分: ${item.movie.averageRating}</span>
                                                    </p>
                                                    <p>
                                                        <span class="pl">近期热度: ${item.recentHeat}</span>
                                                    </p>
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                        </c:forEach>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>

<%--智能提示框--%>
<%--<div class="suggest" id="search-suggest" style="display: none; top:43px;left: 136px;">--%>
    <%--<ul id="search-result">--%>
    <%--</ul>--%>
<%--</div>--%>

<!--底部 -->
<div class="footer">
    <div class="tip">
        Copyright © 2019-2020 &nbsp;&nbsp;
        <p>声明：本站不提供视频观看，所有视频播放将跳转到第三方网站</p>
    </div>
</div>

</body>

<%--搜索栏--%>
<script>
    $("#inp-query").bind("keyup", function () {
        var width = document.getElementById("inp-query").offsetWidth + "px";
        $("#search-suggest").show().css({
            width: width
        });
        //在搜索框输入数据，提示相关搜索信息
        var searchText = $("#inp-query").val();
        $("#search-result").children().remove();
        $.post("/searchLikeName", {"search_text": searchText}, function (data) {
            if (data.status == 200) {
                if (data.data.length != 0) {
                    $.each(data.data, function (i, item) {
                        var headHtml = $("#movie-tmpl").html();
                        headHtml = headHtml.replace(/{id}/g, item.movieId);
                        headHtml = headHtml.replace(/{cover}/g, item.moviePicture);
                        headHtml = headHtml.replace(/{moviename}/g, item.movieName);
                        headHtml = headHtml.replace(/{showyear}/g, item.movieDate);
                        headHtml = headHtml.replace(/{averating}/g, item.movieRating);
                        $("#search-result").append(headHtml);
                    })
                }
            }
        })
    });
</script>

<%--智能提示框模板--%>
<script type="text/tmpl" id="movie-tmpl">
 <li id="searchResult">
   <div>
      <a value="{id}" style="text-decoration:none" onclick='javascript:$.post("/Customer/Description",{movieId:$(this).attr("value")}, function (data) {
            if (data=="success") {
                location.href = "/MovieDescription"
            } else {
                //出错处理页
            }
        })'>
         <div style="float:left">
            <img src="{cover}" style="width:80px;height:120px">
         </div>
         <div  style="padding:12px">
            <span>&nbsp;&nbsp;&nbsp;&nbsp;电影名称: {moviename}</span>
            <br>
            <span>&nbsp;&nbsp;&nbsp;&nbsp;上映时间: {showyear}</span>
             <br>
            <span>&nbsp;&nbsp;&nbsp;&nbsp;评分: {averating}</span>
         </div>
       </a>
   </div>
 </li>
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
        })
    }
</script>

<script>

    $(function () {
        $("#history").click(function () {
            if($(this).attr("class").indexOf("list-item-checked")>0){
            }else {
                $("#recent-heat").removeClass("active");
                $("#recent").removeClass("list-item-checked");
                $("#history-heat").addClass("active");
                $("#history").addClass("list-item-checked");
            }
        });

        $("#recent").click(function () {
            if($(this).attr("class").indexOf("list-item-checked")>0){
            }else {
                $("#history-heat").removeClass("active");
                $("#history").removeClass("list-item-checked");
                $("#recent-heat").addClass("active");
                $("#recent").addClass("list-item-checked");
            }
        })
    })

</script>

</html>