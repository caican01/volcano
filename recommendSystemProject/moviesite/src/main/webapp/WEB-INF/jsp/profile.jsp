<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh" data-theme="light">
<head>
    <%--<meta charset="utf-8"/>--%>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="referrer" content="no-referrer" />
    <title data-react-helmet="true">发现好电影/个人主页</title>
    <link rel="SHORTCUT ICON" href="/assets/img/knowU.ico"/>
    <link data-react-helmet="true" rel="prefetch" href="/assets/img/user_cover_image.jpg"/>
    <script src="/assets/js/jquery.js"></script>
    <script src="/assets/js/bootstrap.min.js"></script>
    <script src="/assets/js/star-rating.min.js" type="text/javascript"></script>
    <link href="/assets/css/star-rating.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="/assets/css/douban.main.css" rel="stylesheet"/>
    <link href="/assets/css/bootstrap.css" rel="stylesheet">
    <link href="/assets/css/SuggestList.css" rel="stylesheet" type="text/css">
    <link href="/assets/css/wholeframe.css" rel="stylesheet" type="text/css">
    <link href="/assets/css/MovieDescription.css" rel="stylesheet" type="text/css">
    <style>
        .component-poster-detail .nav-tabs > li {
            width: 50% !important;
        }
    </style>
    <%--star rating 类--%>
    <script type="text/javascript">
        window.onload = function () {
            $("input[name='allstar']").rating({
                        showClear: false,
                        size: 'xs',
                        showCaption: false,
                        readonly: true,
                    }
            );
        }
    </script>
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
            <!--  用户已登录  -->
            <c:if test="${sessionScope.user != null}">
                <a class="dream" href="javascript:" style="float: right;color: white;font-size: 13pt;margin-top: 10px;margin-right: 10px">
                    <span style="color: white" class="glyphicon glyphicon-user"></span> ${sessionScope.user.userName}
                </a>
                <a class="dream" id="logout" href="javascript:window.location.href='/page/logout'" style="float: right;color: white;font-size: 13pt;margin-top: 10px;margin-right: 10px">
                    <span style="color: white" class="glyphicon glyphicon-log-in"></span> 退出</a>
            </c:if>
        </nav>
    </div>

    <main role="main" class="App-main" data-reactid="48">
        <div data-reactid="49">
            <div id="ProfileHeader" class="ProfileHeader" data-reactid="61">
                <div class="Card" data-reactid="62">
                    <div class="ProfileHeader-userCover" data-reactid="63">
                        <div class="UserCoverEditor" data-reactid="64">
                            <!-- 背景图片 -->
                            <div data-reactid="65">
                                <div class="UserCover" data-reactid="71">
                                    <!-- 背景图片 -->
                                    <div class="VagueImage UserCover-image" data-src="/assets/img/user_cover_image.jpg" data-reactid="72">
                                        <img src="/assets/img/user_cover_image.jpg">
                                        <div class="VagueImage-mask" data-reactid="73"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="ProfileHeader-wrapper" data-reactid="75">
                        <!-- 背景图片以下的用户信息部分-->
                        <div class="ProfileHeader-main" data-reactid="76" style="margin-bottom: 0px;">
                            <!-- 用户头像 -->
                            <div class="UserAvatarEditor ProfileHeader-avatar" style="top:-57px;margin-left: 20px;" data-reactid="77">
                                <div class="UserAvatar" data-reactid="78">
                                    <img class="Avatar Avatar--large UserAvatar-inner" width="160" height="160"
                                         src="/assets/img/user_pic.jpg" srcset="/assets/img/user_pic.jpg 2x"
                                         data-reactid="79"/>
                                </div>
                            </div>

                            <div class="ProfileHeader-content" data-reactid="87">
                                <!-- 用户名称 -->
                                <div class="ProfileHeader-contentHead" data-reactid="88">
                                    <h1 class="ProfileHeader-title" data-reactid="89">
                                        <span class="ProfileHeader-name"
                                              data-reactid="90">${sessionScope.user.userName}
                                        </span>
                                    </h1>
                                </div>

                                <!-- 头像下的留白空间 -->
                                <div style="overflow:hidden;transition:height 300ms ease-out;" class="ProfileHeader-contentBody" data-reactid="93">
                                    <div data-reactid="94">
                                        <div class="ProfileHeader-info" data-reactid="95">
                                            <div class="ProfileHeader-infoItem" data-reactid="96">
                                                <div class="ProfileHeader-iconWrapper" data-reactid="97">
                                                </div>
                                                <div class="ProfileHeader-divider" data-reactid="102">
                                                </div>
                                                <div class="ProfileHeader-divider" data-reactid="104">
                                                </div>
                                                <div class="ProfileHeader-iconWrapper" data-reactid="105">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <!--编辑个人资料按钮 -->
                                <div class="ProfileHeader-contentFooter">
                                    <div class="ProfileButtonGroup ProfileHeader-buttons" style="bottom: 30px;">
                                        <a href="#" class="Button Button--blue" onclick="editUser(${sessionScope.user.userId})">
                                            修改个人信息
                                        </a>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="Profile-main" data-reactid="120">
                <div class="Profile-mainColumn" data-reactid="121">
                    <div class="Card ProfileMain" id="ProfileMain" data-reactid="122">
                        <div class="ProfileMain-header" data-reactid="123">
                            <!-- 滑动标签<li> -->
                            <ul class="nav nav-tabs" role="tablist">
                                <li role="presentation" class="active" style="text-align: center">
                                    <a href="#film-info"
                                         aria-controls="film-info"
                                         data-toggle="tab"
                                         aria-expanded="true">
                                        已收藏
                                    </a>
                                </li>
                                <li role="presentation" class="" style="text-align: center">
                                    <a id="reviewsId"
                                       href="#reviews"
                                       aria-controls="reviews"
                                       data-toggle="tab"
                                       aria-expanded="false">
                                        已评价
                                    </a>
                                </li>
                            </ul>

                            <!-- 喜欢的电影 模板<li> -->
                            <div class="tab-content">
                                <div class="tab-pane fade active in" id="film-info" data-zop-feedlistfather="1" data-reactid="158">
                                    <div class="List-header" data-reactid="159">
                                        <h4 class="List-headerText" data-reactid="160">
                                            <span data-reactid="161">
                                  <!-- react-text: 162 -->我收藏的电影
                                            </span>
                                        </h4>
                                        <div class="List-headerOptions" data-reactid="164"></div>
                                    </div>

                                    <div class="" data-reactid="165">
                                        <c:if test="${sessionScope.userLikedList != null}">
                                            <c:forEach var="item" items="${sessionScope.userLikedList}">
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
                                                                   title="${item.movie.movieName}">
                                                                    <img src="${item.movie.moviePicture}" width="75" alt="${item.movie.movieName}" class=""/>
                                                                </a>
                                                            </td>
                                                            <td valign="top">
                                                                <div class="pl2">
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
                                                                            <span class="pl">热度: ${item.movie.historyHeat}</span>
                                                                        </p>
                                                                    </div>
                                                                </div>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                    <div id="collect_form_11584016"></div>
                                                </div>
                                            </c:forEach>
                                        </c:if>
                                    </div>
                                </div>

                                <!-- 评价过的电影 模板<li> -->
                                <div class="tab-pane fade" id="reviews" data-zop-feedlistfather="1" data-reactid="158">
                                    <div class="List-header" data-reactid="159">
                                        <h4 class="List-headerText" data-reactid="160">
                                            <span data-reactid="161">
                                <!-- react-text: 162 -->我评价过的电影
                                            </span>
                                        </h4>
                                        <div class="List-headerOptions" data-reactid="164"></div>
                                    </div>
                                    <div class="" data-reactid="165">
                                        <!-- 评价过的电影 -->
                                        <c:if test="${sessionScope.userRatingList != null}">
                                            <c:forEach var="item" items="${sessionScope.userRatingList}">
                                                <div class="List-item" data-reactid="166">
                                                    <p class="ul first"></p>
                                                    <table width="100%" class="">
                                                        <tr class="item">
                                                            <td width="100" valign="top">
                                                                <a class="nbg" value="${item.movieId}" onclick='$.post("/Customer/Description",{movieId:$(this).attr("value")}, function (data) {
                                                                      if (data=="success") {
                                                                          location.href = "/MovieDescription"
                                                                      } else {
                                                                          //跳转到出错处理页面
                                                                      }
                                                                    })' title="${item.movieName}">
                                                                    <img src="${item.moviePicture}" width="75" class=""/>
                                                                </a>
                                                            </td>
                                                            <td valign="top">
                                                                <div class="pl2">
                                                                    <div>
                                                                        <p>
                                                                            <span class="p1">
                                                                                <a value="${item.movieId}" onclick='$.post("/Customer/Description",{movieId:$(this).attr("value")}, function (data) {
                                                                                      if (data=="success") {
                                                                                          location.href = "/MovieDescription"
                                                                                      } else {
                                                                                          //跳转到出错处理页面
                                                                                      }
                                                                                    })'>
                                                                                    ${item.movieName}
                                                                                </a>
                                                                            </span>
                                                                        </p>
                                                                        <p>
                                                                            <span class="p1">
                                                                                    我的的评分: ${item.averageRating}
                                                                            </span>
                                                                        </p>
                                                                        <p>
                                                                            评价时间:
                                                                          <span property="v:dtreviewed" content="2018-03-19" class="main-meta p1">
                                                                              <jsp:useBean id="dateValue" class="java.util.Date"/> <!-- 通过jsp:userBean标签引入java.util.Date日期类 -->
                                                                              <jsp:setProperty name="dateValue" property="time" value="${item.timestamp}"/> <!--如果是单位问题导致出错的话，可以这样解决：单位为秒，就按标签这样写就对了；单位为毫秒，须在value后补3个0-->
                                                                                  <!-- 使用jsp:setProperty标签将时间戳设置到Date的time属性中 -->
                                                                              <fmt:formatDate value="${dateValue}" pattern="yyyy-MM-dd HH:mm:ss"/> <!-- 转换格式 -->
                                                                          </span>
                                                                        </p>
                                                                    </div>
                                                                    <div>
                                                                        <input name="allstar" value="${item.averageRating}">
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
                </div>

                <!-- 右侧模块 -->
                <div class="Profile-sideColumn" data-za-module="RightSideBar" data-reactid="294">
                    <div class="Card" data-reactid="295" style="margin-top: 41px;height: 51px;">
                        <div class="Card-header Profile-sideColumnTitle" data-reactid="296">
                            <div class="Card-headerText" data-reactid="297" style="margin-top: auto;margin-bottom: auto;">
                                <span style="text-align: center;margin-left: 80px;">
                                    <font size="4px">
                                        为你推荐
                                </font>
                                </span>
                            </div>
                        </div>
                    </div>
                    <!-- 右侧电影推荐列表 -->
                    <div class="Profile-lightList" data-reactid="329" style="margin-top: -10px;">
                        <!-- 右侧电影推荐列表 -->
                        <c:if test="${sessionScope.homePageRecommendedMovies != null}">
                            <c:forEach var="item" items="${sessionScope.homePageRecommendedMovies}">
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
            </div>
        </div>
    </main>
</div>

<!--修改用户信息模态框-->
<div class="modal fade" id="updateUserMessage" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h2>更新用户信息</h2>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="loginForm">
                    <div class="form-group">
                        <div class="col-sm-12">
                            <input type="text" class="form-control" id="edit_username" placeholder="用户名" name="userName">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <input type="password" class="form-control" id="edit_password" placeholder="新密码" name="userPassword">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <input type="password" class="form-control" id="repeat_edit_password" placeholder="确认新密码" name="repeatUserPassword">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="updateUserMessage(${sessionScope.user.userId})">修改</button>
            </div>
        </div>
    </div>
</div>

<%--智能提示框--%>
<div class="suggest" id="search-suggest" style="display: none; top:43px;left: 136px;">
    <ul id="search-result">
    </ul>
</div>

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
//    $("#inp-query").bind("keyup", function () {
//        var width = document.getElementById("inp-query").offsetWidth + "px";
//        $("#search-suggest").show().css({
//            width: width
//        });
//        //在搜索框输入数据，提示相关搜索信息
//        var searchText = $("#inp-query").val();
//
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
//                        $("#search-result").append(headHtml);
//                    })
//                }
//            }
//        })
//    });
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

<script type="text/javascript">

    //打开用户信息编辑模态框
    function editUser(id) {
        $("#updateUserMessage").modal("show");
        $.ajax({
            type: "get",
            url: "/user/edit",
            data: {"id": id},
            success: function (data) {
                  $("#edit_username").val(data.userName);
            }
        });
    }

    //修改用户密码
    function updateUserMessage(id) {

        //新密码
        var newPwd = $("#edit_password").val();
        //确认新密码
        var repeatNewPwd = $("#repeat_edit_password").val();
        //新昵称
        var newName = $("#edit_username").val();

        //密码不为null
        if (newPwd!=""&&newPwd!=null&&newName!=""&&newName!=null) {
            if (newName.length < 6 || newName.length > 10){
                alert("用户名长度必须为6~10位");
                $("#edit_username").val(null);
            }else if (newPwd.length < 8 || newPwd.length > 16) {
                alert("密码长度必须为8~16位");
                $("#edit_password").val(null);
            }else if(newPwd!=repeatNewPwd){
                alert("两次密码输入不一致");
                $("#edit_password").val(null);
                $("#repeat_edit_password").val(null);
            }else {
                $.ajax({
                    type: "get",
                    url: "/user/update",
                    data: {
                        "userId":id,
                        "userName":$("#edit_username").val(),
                        "userPassword":$("#edit_password").val()
                    },
                    success: function (data) {
                        if (data.status == 200){
                            //在这里执行不了
                        }
                    }
                });
                var oldPwd = "${sessionScope.user.userPassword}";
                if (oldPwd!= newPwd) {
                    alert("检测到您的密码已更改，出于安全性考虑，请重新登录");
                    window.location.replace("/page/login");
                } else {
                    alert("信息修改成功");
                }
                $("#updateUserMessage").modal("hide");
            }
        }else if(newName==""||newName==null) {
            alert("用户名不能为空");
        }else {
            alert("密码不能为空");
        }
    }

</script>

</html>