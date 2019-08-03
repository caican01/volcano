<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans" class="ua-mac ua-webkit">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="referrer" content="no-referrer" />
    <title>
        发现好电影主页
    </title>
    <script src="/assets/js/jquery.js"></script>
    <script src="/assets/js/bootstrap.min.js"></script>
    <link rel="SHORTCUT ICON" href="/assets/img/knowU.ico"/>
    <!-- 电影推荐模块CSS-->
    <link href="/assets/css/bootstrap.css" rel="stylesheet">
    <link href="/assets/css/SuggestList.css" rel="stylesheet" type="text/css">
    <link href="/assets/css/wholeframe.css" rel="stylesheet" type="text/css">
    <link href="/assets/css/footer.css" rel="stylesheet" type="text/css">
    <!-- 左右模块位置排序和推荐CSS-->
    <%--<link href="https://img3.doubanio.com/f/movie/8864d3756094f5272d3c93e30ee2e324665855b0/css/movie/base/init.css" rel="stylesheet">--%>
    <link href="/assets/css/init.css" rel="stylesheet">
    <!-- 电影选择模块CSS（类型/排序/展示）-->
    <%--<link rel="stylesheet" href="https://img3.doubanio.com/f/movie/fc5a7b9631f6e089a6a047e0e701207243e3fbdf/css/movie/project/gaia/__init__.css" />--%>
    <link rel="stylesheet" href="/assets/css/__init__.css" />
    <!-- 电影推荐模块CSS-->
    <%--<link rel="stylesheet" href="https://img3.doubanio.com/misc/mixed_static/554ab01e9256e005.css">--%>
    <!-- 鼠标悬浮在<A>时背景和导航栏同步-->
    <style type="text/css">
       a.dream:hover
       {
            background-color: black;
        }
       .clash-card {
           background: white;
           width: 370px;
           display: inline-block;
           margin-left: -80px;
           margin-top: 0px ;
           border-radius: 19px;
           position: relative;
           text-align: center;
           box-shadow: -1px 15px 30px -12px black;
           z-index: 9999;
       }
       .clash-card__unit-name {
           font-size: 26px;
           color: black;
           font-weight: 900;
           margin-bottom: 5px;
       }
       .clash-card__unit-description {
           padding: 20px;
           margin-bottom: 10px;
       }
       .clash-card__unit-stats--giant {
           background: #428bca;
       }
       .clash-card__unit-stats--giant .one-third {
           border-right: 1px solid #de7b09;
       }
       .clash-card__unit-stats {
           color: white;
           font-weight: 400;
           border-bottom-left-radius: 14px;
           border-bottom-right-radius: 14px;
       }
       .clash-card__unit-stats .one-third {
           width: 33%;
           float: left;
           padding: 20px 15px;
       }
       .clash-card__unit-stats sup {
           position: absolute;
           bottom: 4px;
           font-size: 45%;
           margin-left: 2px;
       }
       .clash-card__unit-stats .stat {
           position: relative;
           font-size: 17px;
           margin-bottom: 10px;
       }
       .clash-card__unit-stats .stat-value {
           text-transform: uppercase;
           font-weight: 400;
           font-size: 17px;
       }
       .clash-card__unit-stats .no-border {
           border-right: none;
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

<body>
<!-- 导航栏-->
<nav class="navbar navbar-default" role="navigation" style="background-color: #222;margin-bottom: 0%;">
    <a class="navbar-brand" href="/" style="color: white">电影网</a>
    <div class="col-xs-4" style="margin-top: 8px">
        <input id="inp-query" class="form-control" name="search_text"  maxlength="60" placeholder="请输入片名" onkeydown="if(event.keyCode==13) searchWebSite()">
        <%--<button id="query-btn" class="search_btn">搜索</button>--%>
    </div>
    <a class="navbar-brand" href="/index" style="color: white">电影主页</a>
    <!-- 判断用户是否登录-->

    <!-- 用户未登录 -->
    <c:if test="${sessionScope.user == null}">
    <a  class="dream" href="javascript:window.location.href='/page/register'" id="register" style="float: right;color: white;font-size: 13pt;margin-top: 10px;margin-right: 10px"><span style="color: white" class="glyphicon glyphicon-user"></span> 注册</a>
    <a  class="dream" href="javascript:window.location.href='/page/login'" style="float: right;color: white;font-size: 13pt;margin-top: 10px;margin-right: 10px"><span style="color: white" class="glyphicon glyphicon-log-in"></span> 登录</a>
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
<br>


<div id="wrapper" class="col-md-12" style="margin-left: 8%">
    <div id="content" style="margin-top: 25px">
        <h1>筛选电影</h1>
        <div class="grid-16-8 clearfix">
            <!-- 左侧电影展示模块-->
            <div  class="article">
                <div class="gaia" style="width: 129%">
                    <div class="detail-pop"></div>
                    <div class="fliter-wp">
                        <div class="filter">
                            <%--autocomplete自动完成 当值为on时输入时有提示，当值为off时输入没提示--%>
                            <form action="get" class="gaia_frm" autocomplete="off">
                                <%--type="hidden"使其所修饰的元素隐藏起来--%>
                                <input type="hidden" name="type" value="movie">
                                <!-- 电影类型标签-->
                                <div class="tags">
                                    <div id="tags-list" class="tag-list">
                                        <label  class="activate" style="font-size: 13pt" value="all">全部
                                            <%--<input type="radio" name="tag" value="0">--%>
                                            <input type="radio" name="tag" value="all">
                                        </label>
                                        <!-- 从数据库到seesion读入，默认第一个选中activate-->
                                        <c:forEach var="item"   items="${sessionScope.category}" varStatus="i">
                                            <%--<label  style="font-size: 13pt" value="${i.count}">${item.categoryName}--%>
                                            <label  style="font-size: 13pt" value="${item.categoryName}">${item.categoryName}
                                                <%--<input type="radio" name="tag" value="${i.count}">--%>
                                                <input type="radio" name="tag" value="${item.categoryName}">
                                            </label>
                                        </c:forEach>
                                    </div>
                                </div>
                                <!-- 电影时序等选择radio-->
                                <div class="tool" style="">
                                    <div class="sort">
                                        <label>
                                            <%--默认是按热度排序 checked="checked" --%>
                                            <%--热度，即观看的人数的多少--%>
                                            <input  type="radio" name="sort" value="history_heat" checked="checked"> 按热度排序
                                        </label>
                                        <label>
                                            <%--按时间排序，上映的时间先后--%>
                                            <input type="radio" name="sort" value="movie_date"> 按时间排序
                                        </label>
                                        <label>
                                            <%--评价排序，即按平均评分的高低--%>
                                            <input type="radio" name="sort" value="average_rating"> 按评分排序
                                        </label>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                    <!-- 电影信息卡片展示模块 -->
                    <div   class="list-wp">
                        <div id="list" class="list">
                            <!-- 初始化或刷新页面用C:FOREACH加载电影（类似于SC模板）-->
                            <c:if test="${sessionScope.movie != null}">
                                <c:forEach var="m"   items="${sessionScope.movie}">
                                    <a class="item" target="_blank" name="imgitem" id="${m.movieId}" onclick='$.post("/Customer/Description",{movieId:$(this).attr("id")}, function (data) {
                                        if (data == "success") {
                                            location.href = "/MovieDescription"
                                        } else {
                                            //出错处理页面
                                        }
                                    })' title="${m.movieName} ${m.averageRating}">
                                        <div class="cover-wp">
                                            <img src="${m.moviePicture}" alt="${m.movieName}" data-x="1500" data-y="2200" style="width: 100%"/>
                                        </div>
                                        <p>${m.movieName}
                                            <strong>${m.averageRating}</strong>
                                        </p>
                                    </a>
                                </c:forEach>
                            </c:if>
                        </div>
                        <!-- 加载更多 -->
                        <a class="more" id="loadmore" href="javascript:">加载更多</a>
                    </div>
                </div>
            </div>

            <!-- 右侧推荐模块-->
            <div  class="aside">

                <div style="margin-top: -40px;margin-left: 55px;">
                    <h1>
                        电影推荐
                    </h1>
                </div>

                <div style="float: right;width: 360px;height: auto;margin-top: 15px;margin-right: -109px;margin-top: auto;">
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
                    <div class="tab-content" style="margin-top: 25px;">
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
                    <div class="tab-content" style="margin-top: 25px;">
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

            </div> <!--  推荐模块  -->
        </div> <!-- end container -->
    </div>
</div>

<%--智能提示框--%>
<%--<div class="suggest" id="search-suggest" style="display: none; top:43px;left: 136px;" >--%>
    <%--<ul id="search-result">--%>
    <%--</ul>--%>
<%--</div>--%>

<!-- 点击加载更多事件，通过SC模板加载电影信息-->
<script>
    $(document).on("click",'#loadmore',function() {
        $.post("/loadingmore",{limitCount:$("#list").children("a").length,type:$("label[class='activate']").attr("value"),orderColumn: $("input[name='sort']:checked").val()},
            function (data) {
            if (data.status == 200) {
                if(data.data.length!=0) {
                    $.each(data.data, function (i, item) {
                        var headHtml = $("#subject-tmpl").html();
//                        if (item.moviePicture == "http://image.tmdb.org/t/p/w185"||item.picture==null)
//                            headHtml = headHtml.replace(/{cover}/g, "https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2512283982.jpg");
//                        else
                        headHtml = headHtml.replace(/{cover}/g, item.moviePicture);
                        headHtml = headHtml.replace(/{id}/g, item.movieId);
                        headHtml = headHtml.replace(/{rate}/g,item.averageRating);
                        headHtml = headHtml.replace(/{cover_x}/g, "1500");
                        headHtml = headHtml.replace(/{cover_y}/g, "2200");
                        headHtml = headHtml.replace(/{title}/g, item.movieName);
                        headHtml = headHtml.replace(/{movieName}/g, item.movieName);
                        headHtml = headHtml.replace(/{movieRating}/g, item.averageRating);
                        $("#list").append(headHtml);
                    })
                } else {
                    alert("没有更多影片了")
                }
            } else {
                alert("资源已经挖尽了哦");
            }
        })
    })
</script>

<!-- 电影卡片模板-->
<script type="text/tmpl" id="subject-tmpl">
        <a class="item"  name="imgitem" target="_blank" id="{id}" onclick='javascript:$.post("/Customer/Description",{movieId:$(this).attr("id")}, function (data) {
            if (data=="success") {
                location.href = "/MovieDescription"
            } else {
                //出错处理页面
            }
        })' title="{movieName} {movieRating}">
            <div class="cover-wp">
                <img src="{cover}" alt={title} data-x={cover_x} data-y={cover_y} style="width:100%"/>
            </div>
            <p>{title}
               <strong>{rate}</strong>
            </p>
        </a>
</script>

<%--搜索栏--%>
<script>
//    $("#inp-query").bind("keyup", function () {
//        var width = document.getElementById("inp-query").offsetWidth + "px";
//        $("#search-suggest").show().css({
//            width: width
//        });
//        //在搜索框输入数据，提示相关搜索信息
//        var searchText = $("#inp-query").val();
//        $("#search-result").children().remove();
//        $.post("/searchLikeName", {"search_text": searchText}, function (data) {
//            if (data.status == 200) {
//                if (data.data.length != 0) {
//                    $.each(data.data, function (i, item) {
//                        var headHtml = $("#movie-tmpl").html();
//                        headHtml = headHtml.replace(/{id}/g, item.movieId);
//                        headHtml = headHtml.replace(/{cover}/g, item.moviePicture);
//                        headHtml = headHtml.replace(/{moviename}/g, item.movieName);
//                        headHtml = headHtml.replace(/{showyear}/g, item.movieDate);
//                        headHtml = headHtml.replace(/{averating}/s, item.movieRating);
//                        headHtml = headHtml.replace(/{movieName}/g, item.movieName);
//                        headHtml = headHtml.replace(/{movieRating}/g, item.averageRating);
//                        $("#search-result").append(headHtml);
//                    })
//                }
//            }
//        })
//    });
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

<%--智能提示框模板--%>
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

<!-- 电影类型标签选择事件-->
<script>
    $("input[name='tag']").click(function () {
        //设置选中标签ACTIVATE之前的remove
        $("#tags-list label").attr("class","");
        var label= $(this).parent();
        label.attr("class","activate");
        //清空电影数据
        $("#list").children().remove();
        //如果type为0请求全部刷新页面
        //请求数据对应的电影类型
        $.post("/typesortmovie", {
            limitCount:$("#list").children("a").length,
            type: $(this).attr("value"),
            orderColumn: $("input[name='sort']:checked").val()
        }, function (data) {
            if (data.status == 200) {
                if (data.data.length != 0) {
                    //返回movielist,用sc模板append
                    $.each(data.data, function (i, item) {
                        var headHtml = $("#subject-tmpl").html();
//                        if (item.moviePicture == "http://image.tmdb.org/t/p/w185"||item.moviePicture==null)
//                            headHtml = headHtml.replace(/{cover}/g, "https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2512283982.jpg");
//                        else
                        headHtml = headHtml.replace(/{cover}/g, item.moviePicture);
                        headHtml = headHtml.replace(/{id}/g, item.movieId);
                        headHtml = headHtml.replace(/{rate}/g, item.averageRating);
                        headHtml = headHtml.replace(/{cover_x}/g, "1500");
                        headHtml = headHtml.replace(/{cover_y}/g, "2200");
                        headHtml = headHtml.replace(/{title}/g, item.movieName);
                        headHtml = headHtml.replace(/{movieName}/g, item.movieName);
                        headHtml = headHtml.replace(/{movieRating}/g, item.averageRating);
                        $("#list").append(headHtml);
                    })
                } else {
                    alert("没有该类型影片资源");
                }
            } else {
                alert("没有该类型影片资源");
            }
        })
    })
</script>

<!-- 电影时序等选择radio事件-->
<script>
    $("input[name='sort']").click(function () {
        $("#list").children().remove();
        //请求数据对应的电影类型
        $.post("/typesortmovie", {
            limitCount:$("#list").children("a").length,
            orderColumn: $(this).attr("value"),
            type: $("label[class='activate']").attr("value")
        }, function (data) {
            if (data.status == 200) {
                if (data.data.length != 0) {
                    //返回movielist,用sc模板append
                    $.each(data.data, function (i, item) {
                        var headHtml = $("#subject-tmpl").html();
//                        if (item.moviePicture == "http://image.tmdb.org/t/p/w185"||item.moviePicture==null)
//                            headHtml = headHtml.replace(/{cover}/g, "https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2512283982.jpg");
//                        else
                        headHtml = headHtml.replace(/{cover}/g, item.moviePicture);
                        headHtml = headHtml.replace(/{id}/g, item.movieId);
                        headHtml = headHtml.replace(/{rate}/g,item.averageRating);
                        headHtml = headHtml.replace(/{cover_x}/g, "1500");
                        headHtml = headHtml.replace(/{cover_y}/g, "2200");
                        headHtml = headHtml.replace(/{title}/g, item.movieName);
                        headHtml = headHtml.replace(/{movieName}/g, item.movieName);
                        headHtml = headHtml.replace(/{movieRating}/g, item.averageRating);
                        $("#list").append(headHtml);
                    })
                } else {
                    alert("排序失败");
                }
            }
        })
    })
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

<!--底部 -->
<div class="footer">
    <div class="tip">
        Copyright © 2019-2020 &nbsp;&nbsp;
        <p>声明：本站不提供视频观看，所有视频播放将跳转到第三方网站</p>
    </div>
</div>

</body>
</html>
