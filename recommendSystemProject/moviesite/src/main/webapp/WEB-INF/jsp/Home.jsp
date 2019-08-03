<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<head>
    <meta name="referrer" content="no-referrer"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>
        发现好电影首页
    </title>
    <script src="/assets/js/jquery.js"></script>
    <script src="/assets/js/bootstrap.min.js"></script>
    <link rel="SHORTCUT ICON" href="/assets/img/knowU.ico"/>
    <link href="/assets/css/bootstrap.css" rel="stylesheet">
    <link href="/assets/css/Homediscovery.css" rel="stylesheet">
    <link href="/assets/css/SuggestList.css" rel="stylesheet" type="text/css">
</head>

<body class="fm-discovery" id="wholediv" style="background-image: url('${sessionScope.homePageRecommendedMovies[0].moviePicture}');">

<%--导航栏--%>
<nav class="navbar navbar-default" role="navigation" style="background-color: #222;margin: 0px;padding: 0px;">
    <a class="navbar-brand" href="/" style="color: white">电影网</a>
    <div class="col-xs-4" style="margin-top: 8px">
        <%--<input id="inp-query" class="form-control" style="margin-bottom: 8px;margin-top: 8px;border-radius: 5px;" name="search_text"  maxlength="60" placeholder="请输入片名" value=""><button class="search_btn">搜索</button>--%>
        <input id="inp-query" class="form-control"  name="search_text"  maxlength="60" placeholder="请输入片名" onkeydown="if(event.keyCode==13) searchWebSite()">
        <%--<button id="query-btn" class="search_btn">搜索</button>--%>
    </div>
    <a class="navbar-brand" href="/index" style="color: white">电影主页</a>
    <!-- 判断用户是否登录-->
    <!--  未登录 -->
    <c:if test="${sessionScope.user == null}">
        <a  class="dream" href="javascript:window.location.href='/page/register'" id="register" style=" text-decoration:none;float: right;color: white;font-size: 13pt;margin-top: 12px;margin-right: 10px"><span style="color: white" class="glyphicon glyphicon-user"></span> 注册</a>
        <a  class="dream" href="javascript:window.location.href='/page/login'" style=" text-decoration:none;float: right;color: white;font-size: 13pt;margin-top: 12px;margin-right: 10px"><span style="color: white" class="glyphicon glyphicon-log-in"></span> 登录</a>
    </c:if>
    <!-- 已登录 -->
    <c:if test="${sessionScope.user != null}">
        <a class="dream" onclick='$.post("/page/profile",{"userId":"${sessionScope.user.userId}"}, function (data) {
            if (data=="success") {
                location.href = "/profile"
            }
            })' style=" text-decoration:none;float: right;color: white;font-size: 13pt;margin-top: 12px;margin-right: 10px">
            <span style="color: white" class="glyphicon glyphicon-user"></span> ${sessionScope.user.userName}
        </a>
        <a class="dream" id="logout" href="javascript:window.location.href='/page/logout'" style=" text-decoration:none;float: right;color: white;font-size: 13pt;margin-top: 12px;margin-right: 10px"><span style="color: white" class="glyphicon glyphicon-log-out"></span>  退出</a>
    </c:if>
</nav>

<%--&lt;%&ndash;智能提示框&ndash;%&gt;--%>
<%--<div class="suggest" id="search-suggest" style="display: none; top:43px;left: 136px;" >--%>
    <%--<ul id="search-result">--%>
    <%--</ul>--%>
<%--</div>--%>

<%--主体内容显示区--%>
<div>
    <!-- 左侧电影信息卡片-->
    <div class="x-kankan">
        <!-- 左侧电影信息卡片-->
        <div id="x-kankan-detail" class="x-kankan-detail">
            <p class="x-kankan-title">
                <a name="movienametag" onclick='$.post("/Customer/Description",{movieId:$(this).attr("value")}, function (data) {
                    if (data=="success") {
                        location.href = "/MovieDescription"
                    } else {
                        //这里应该添加个error页面，不至于返回fail点击了没反应，出错了的话跳到出错页面
                    }
                })'
                class="q" data-toggle="tooltip" value="${sessionScope.homePageRecommendedMovies[0].movieId}" data-placement="top" data-original-title="点击查看${sessionScope.homePageRecommendedMovies[0].movieName}详情">
                    ${sessionScope.homePageRecommendedMovies[0].movieName}
                </a>
                <span class="revision-score">
                    <span class="fm-rating">
                        <span class="fm-green" name="movieaverating"  rel="nofollow">
                            评分: ${sessionScope.homePageRecommendedMovies[0].averageRating}
                        </span>
                    </span>
                </span>
            </p>
            <p  name="moviedescription" class="x-kankan-desc">
                <span>为您暖心推荐的影片，赶紧点进去看看吧，祝您有个愉快的观影过程(^v^)</span>
            </p>
        </div>
    </div>

    <!-- 右侧按钮-->
    <div class="x-usermovie-controls x-kankan-buttons">
        <!-- 右侧上部分按钮-->
        <div class="btn-group fm-discovery-actions" style="height: auto;">
            <!-- 点击搜索影片资源按钮跳转到电影详情页面-->
            <a name="moviedesc" data-placement="top" onclick='$.post("/Customer/Description",{movieId:$(this).attr("value")}, function (data) {
                    if (data=="success") {
                        location.href = "/MovieDescription"
                    } else {
                        //出错的话跳转到出错处理页面
                    }
                })'
               class="revision-btn-history" value="${sessionScope.homePageRecommendedMovies[0].movieId}" title="" data-toggle="tooltip"
               data-movie="the-other-guys" data-cat="watched" data-class="btn-success" data-original-title="电影详情" style="height: 70px;">
                <span class="glyphicon glyphicon-search"></span>
            </a>
            <!-- 右上右小图标播放按钮-->
            <c:if test="${sessionScope.homePageRecommendedMovies[0].playId != ''}">
                <a name="moviehref" target="_blank" href="https://www.iqiyi.com/${sessionScope.homePageRecommendedMovies[0].playId}.html" data-placement="top"
                   class="btn-default revision-btn-next" title="" data-toggle="tooltip" data-movie="the-other-guys" data-cat="liked" data-class="btn-danger" data-original-title="播放影片"
                   style="height: 70px;">
                    <span class="glyphicon glyphicon-film"></span>
                </a>
            </c:if>
            <c:if test="${sessionScope.homePageRecommendedMovies[0].playId == ''}">
                <a name="moviehref" target="_blank" href="http://so.iqiyi.com/so/q_${sessionScope.homePageRecommendedMovies[0].movieName}" data-placement="top"
                   class="btn-default revision-btn-next" title="" data-toggle="tooltip" data-movie="the-other-guys" data-cat="liked" data-class="btn-danger" data-original-title="播放影片"
                   style="height: 70px;">
                    <span class="glyphicon glyphicon-film"></span>
                </a>
            </c:if>
        </div>

        <!-- 右侧下部分按钮-->
        <div class="btn-group x-kankan-navigator" style="height: auto;">
            <!-- 切换到上一部电影-->
            <a class="revision-btn-history" id="pre" style="height: 78px;">
                <span class="glyphicon glyphicon-chevron-left"></span>
                <span>上一部</span>
            </a>
            <!-- 下一部电影-->
            <a  class="btn-default revision-btn-next" id="next" style="height: 78px;">
                <span>下一部</span>
                <span class="glyphicon glyphicon-chevron-right"></span>
            </a>
        </div>
    </div>

    <!-- 中央区域可隐藏的播放按钮-->
    <div class="xx-play-button">
        <%--<a name="moviehref" href="http://so.iqiyi.com/so/q_${sessionScope.TopDefaultMovie[0].moviename}" target="_blank" class="q" data-title="全网资源搜索" style="display: none;">--%>
        <%--使用了style="display: none;"这个样式，会使得这个a标签默认在网页上是被隐藏的--%>
        <%--<c:if test="${sessionScope.homePageRecommendedMovies[0].playId != ''}">--%>
            <%--<a name="moviehref" href="https://www.iqiyi.com/${sessionScope.homePageRecommendedMovies[0].playId}.html" target="_blank" class="q" data-original-title="全网资源搜索" style="display: none;">--%>
                <%--<img src="/assets/img/Homeimg/kankan_play.7b61b6e9285d.png" alt="播放按钮">--%>
            <%--</a>--%>
        <%--</c:if>--%>
        <%--<c:if test="${sessionScope.homePageRecommendedMovies[0].playId == ''}">--%>
            <a name="moviehref" href="http://so.iqiyi.com/so/q_${sessionScope.homePageRecommendedMovies[0].movieName}" target="_blank" class="q" data-original-title="全网资源搜索" style="display: none;">
                <img src="/assets/img/Homeimg/kankan_play.7b61b6e9285d.png" alt="播放按钮">
            </a>
        <%--</c:if>--%>
    </div>

</div>

<!--页面按钮hover提示 -->
<script>
    $(function(){
//        'ontouchstart' in window的作用是判断是否支持触屏
//        !('ontouchstart' in window) 不支持触屏的话
        if(!('ontouchstart' in window)) {
            //激活提示工具
            $('[data-toggle="tooltip"]').tooltip();
        }
        if($('.top_messages').length > 0){
            setTimeout(function () {
                fadeOut(); //实现一种淡出式的隐藏效果
                $('.top_messages').fadeOut();
            }, 5000);
        }
        $('.fm-lazy-img').each(function(i,e){
            $(e).attr('src', $(e).attr('data-src'));
        });
    });
</script>

<!--播放前进后退按钮事件 -->
<script>
    //播放按钮
    //setTimeout(function(){},time)方法用于在指定的time时间后，执行指定的function方法
    window.setTimeout(function(){
        //xx-play-button中央区域的播放按钮区域
        //$('.xx-play-button a')获取中央播放按钮区域的a标签
        //fadeIn()方法的作用是按淡入效果显示，第一个参数表示的是显示的速度，第二个参数是回调函数，即fadeIn()方法执行完之后要执行的方法
        $('.xx-play-button a').fadeIn(1000, function(){
            window.setTimeout(function(){
                if(! $('.xx-play-button a').attr('data-hover')){
                    $('.xx-play-button a').hide();
                }
            }, 10*1000);
        });
    }, 1000);

    //这里就是控制中央播放按钮的显示与隐藏的，但是这里是控制显示与隐藏效果的(同上方法，重复？)
    $('.xx-play-button').mouseenter(function(){
        $(this).children('a').show();
        $(this).children('a').attr('data-hover', 'true');
    }).mouseleave(function(){
        $(this).children('a').hide();
    });

<!--上一部电影 按钮 -->
    $('#pre').click(function(){
        //将map键值对转换为json格式
        var m=JSON.parse('${sessionScope.homePageRecommendedMovieMap}');
        //a[name='movienametag']表示的意思是获取name='movienametag'的a元素
        //再获取它的value属性的值，即电影id
        var movieid=$("a[name='movienametag']").attr("value");
        //如果id是0，那么它的上一部应该是id为4的电影了
        if(m[movieid]==0)
        {
            //获取id为4的电影的背景图片链接
            var url="${sessionScope.homePageRecommendedMovies[4].moviePicture}";
            var playId = "${sessionScope.homePageRecommendedMovies[4].playId}";
            //设置背景图片为id为4的电影的海报
            $("#wholediv").css('background-image',"url("+url+")");
            //右下播放小图标，修改播放的电影链接
            <%--if (playId!=''){--%>
                <%--$("a[name=\"moviehref1\"]").attr("href","https://www.iqiyi.com/${sessionScope.homePageRecommendedMovies[4].playId}.html");--%>
            <%--}else{--%>
                $("a[name=\"moviehref\"]").attr("href","http://so.iqiyi.com/so/q_${sessionScope.homePageRecommendedMovies[4].movieName}");
//            }

            $("a[name=\"moviedesc\"]").attr("value","${sessionScope.homePageRecommendedMovies[4].movieId}");

            $("a[name='movienametag']").attr("value","${sessionScope.homePageRecommendedMovies[4].movieId}");

            $("a[name='movienametag']").attr("data-original-title","点击查看${sessionScope.homePageRecommendedMovies[4].movieName}的详细资料");

            $("a[name='movienametag']").text("${sessionScope.homePageRecommendedMovies[4].movieName}");

            $("span[name='movieaverating']").text("评分:${sessionScope.homePageRecommendedMovies[4].averageRating}");

            <%--$("p[name='moviedescription']").text("${sessionScope.homePageRecommendedMovies[4].movieDescription}");--%>
        }

        if(m[movieid]==1)
        {
            var url="${sessionScope.homePageRecommendedMovies[0].moviePicture}";
            var playId = "${sessionScope.homePageRecommendedMovies[0].playId}";
            $("#wholediv").css('background-image',"url("+url+")" );
            <%--$("a[name=\"moviehref\"]").attr("href","http://so.iqiyi.com/so/q_${sessionScope.homePageRecommendedMovies[0].movieName}");--%>

            <%--if (playId!=''){--%>
                <%--$("a[name=\"moviehref1\"]").attr("href","https://www.iqiyi.com/${sessionScope.homePageRecommendedMovies[0].playId}.html");--%>
            <%--}else{--%>
                $("a[name=\"moviehref\"]").attr("href","http://so.iqiyi.com/so/q_${sessionScope.homePageRecommendedMovies[0].movieName}");
//            }

            $("a[name=\"moviedesc\"]").attr("value","${sessionScope.homePageRecommendedMovies[0].movieId}");
            $("a[name='movienametag']").attr("value","${sessionScope.homePageRecommendedMovies[0].movieId}");
            $("a[name='movienametag']").attr("data-original-title","点击查看${sessionScope.homePageRecommendedMovies[0].movieName}的详细资料");
            $("a[name='movienametag']").text("${sessionScope.homePageRecommendedMovies[0].movieName}");
            $("span[name='movieaverating']").text("评分:${sessionScope.homePageRecommendedMovies[0].averageRating}");
            <%--$("p[name='moviedescription']").text("${sessionScope.homePageRecommendedMovies[0].movieDescription}");--%>
        }

        if(m[movieid]==2)
        {
            var url="${sessionScope.homePageRecommendedMovies[1].moviePicture}";
            var playId = "${sessionScope.homePageRecommendedMovies[1].playId}";
            $("#wholediv").css('background-image',"url("+url+")" );
            <%--$("a[name=\"moviehref\"]").attr("href","http://so.iqiyi.com/so/q_${sessionScope.homePageRecommendedMovies[1].movieName}");--%>

            <%--if (playId!=''){--%>
                <%--$("a[name=\"moviehref1\"]").attr("href","https://www.iqiyi.com/${sessionScope.homePageRecommendedMovies[1].playId}.html");--%>
            <%--}else{--%>
                $("a[name=\"moviehref\"]").attr("href","http://so.iqiyi.com/so/q_${sessionScope.homePageRecommendedMovies[1].movieName}");
//            }

            $("a[name=\"moviedesc\"]").attr("value","${sessionScope.homePageRecommendedMovies[1].movieId}");
            $("a[name='movienametag']").attr("value","${sessionScope.homePageRecommendedMovies[1].movieId}");
            $("a[name='movienametag']").attr("data-original-title","点击查看${sessionScope.homePageRecommendedMovies[1].movieName}的详细资料");
            $("a[name='movienametag']").text("${sessionScope.homePageRecommendedMovies[1].movieName}");
            $("span[name='movieaverating']").text("评分:${sessionScope.homePageRecommendedMovies[1].averageRating}");
            <%--$("p[name='moviedescription']").text("${sessionScope.homePageRecommendedMovies[1].movieDescription}");--%>
        }

        if(m[movieid]==3)
        {
            var url="${sessionScope.homePageRecommendedMovies[2].moviePicture}";
            var playId = "${sessionScope.homePageRecommendedMovies[2].playId}";
            $("#wholediv").css('background-image',"url("+url+")" );
            <%--$("a[name=\"moviehref\"]").attr("href","http://so.iqiyi.com/so/q_${sessionScope.homePageRecommendedMovies[2].movieName}");--%>

            <%--if (playId!=''){--%>
                <%--$("a[name=\"moviehref1\"]").attr("href","https://www.iqiyi.com/${sessionScope.homePageRecommendedMovies[2].playId}.html");--%>
            <%--}else{--%>
                $("a[name=\"moviehref\"]").attr("href","http://so.iqiyi.com/so/q_${sessionScope.homePageRecommendedMovies[2].movieName}");
//            }

            $("a[name=\"moviedesc\"]").attr("value","${sessionScope.homePageRecommendedMovies[2].movieId}");
            $("a[name='movienametag']").attr("value","${sessionScope.homePageRecommendedMovies[2].movieId}");
            $("a[name='movienametag']").attr("data-original-title","点击查看${sessionScope.homePageRecommendedMovies[2].movieName}的详细资料");
            $("a[name='movienametag']").text("${sessionScope.homePageRecommendedMovies[2].movieName}");
            $("span[name='movieaverating']").text("评分:${sessionScope.homePageRecommendedMovies[2].averageRating}");
            <%--$("p[name='moviedescription']").text("${sessionScope.homePageRecommendedMovies[2].movieDescription}");--%>
        }

        if(m[movieid]==4)
        {
            var url="${sessionScope.homePageRecommendedMovies[3].moviePicture}";
            var playId = "${sessionScope.homePageRecommendedMovies[3].playId}";
            $("#wholediv").css('background-image',"url("+url+")" );
            <%--$("a[name=\"moviehref\"]").attr("href","http://so.iqiyi.com/so/q_${sessionScope.homePageRecommendedMovies[3].movieName}");--%>

            <%--if (playId!=''){--%>
                <%--$("a[name=\"moviehref1\"]").attr("href","https://www.iqiyi.com/${sessionScope.homePageRecommendedMovies[3].playId}.html");--%>
            <%--}else{--%>
                $("a[name=\"moviehref\"]").attr("href","http://so.iqiyi.com/so/q_${sessionScope.homePageRecommendedMovies[3].movieName}");
//            }

            $("a[name=\"moviedesc\"]").attr("value","${sessionScope.homePageRecommendedMovies[3].movieId}");
            $("a[name='movienametag']").attr("value","${sessionScope.homePageRecommendedMovies[3].movieId}");
            $("a[name='movienametag']").attr("data-original-title","点击查看${sessionScope.homePageRecommendedMovies[3].movieName}的详细资料");
            $("a[name='movienametag']").text("${sessionScope.homePageRecommendedMovies[3].movieName}");
            $("span[name='movieaverating']").text("评分:${sessionScope.homePageRecommendedMovies[3].averageRating}");
            <%--$("p[name='moviedescription']").text("${sessionScope.homePageRecommendedMovies[3].movieDescription}");--%>
        }
    });

    <!--下一部电影按钮 -->
    $('#next').click(function(){
        var m=JSON.parse('${sessionScope.homePageRecommendedMovieMap}');
        var movieid=$("a[name='movienametag']").attr("value");

        if(m[movieid]==0)
        {
            var url="${sessionScope.homePageRecommendedMovies[1].moviePicture}";
            var playId = "${sessionScope.homePageRecommendedMovies[1].playId}";
            $("#wholediv").css('background-image',"url("+url+")" );
            <%--$("a[name=\"moviehref\"]").attr("href","http://so.iqiyi.com/so/q_${sessionScope.homePageRecommendedMovies[1].movieName}");--%>

            <%--if (playId!=''){--%>
                <%--$("a[name=\"moviehref1\"]").attr("href","https://www.iqiyi.com/${sessionScope.homePageRecommendedMovies[1].playId}.html");--%>
            <%--}else{--%>
                $("a[name=\"moviehref\"]").attr("href","http://so.iqiyi.com/so/q_${sessionScope.homePageRecommendedMovies[1].movieName}");
//            }

            $("a[name=\"moviedesc\"]").attr("value","${sessionScope.homePageRecommendedMovies[1].movieId}");
            $("a[name='movienametag']").attr("value","${sessionScope.homePageRecommendedMovies[1].movieId}");
            $("a[name='movienametag']").attr("data-original-title","点击查看${sessionScope.homePageRecommendedMovies[1].movieName}的详细资料");
            $("a[name='movienametag']").text("${sessionScope.homePageRecommendedMovies[1].movieName}");
            $("span[name='movieaverating']").text("评分:${sessionScope.homePageRecommendedMovies[1].averageRating}");
            <%--$("p[name='moviedescription']").text("${sessionScope.homePageRecommendedMovies[1].movieDescription}");--%>
        }

        if(m[movieid]==1)
        {
            var url="${sessionScope.homePageRecommendedMovies[2].moviePicture}";
            var playId = "${sessionScope.homePageRecommendedMovies[2].playId}";
            $("#wholediv").css('background-image',"url("+url+")" );
            <%--$("a[name=\"moviehref\"]").attr("href","http://so.iqiyi.com/so/q_${sessionScope.homePageRecommendedMovies[2].movieName}");--%>

            <%--if (playId!=''){--%>
                <%--$("a[name=\"moviehref1\"]").attr("href","https://www.iqiyi.com/${sessionScope.homePageRecommendedMovies[2].playId}.html");--%>
            <%--}else{--%>
                $("a[name=\"moviehref\"]").attr("href","http://so.iqiyi.com/so/q_${sessionScope.homePageRecommendedMovies[2].movieName}");
//            }

            $("a[name=\"moviedesc\"]").attr("value","${sessionScope.homePageRecommendedMovies[2].movieId}");
            $("a[name='movienametag']").attr("value","${sessionScope.homePageRecommendedMovies[2].movieId}");
            $("a[name='movienametag']").attr("data-original-title","点击查看${sessionScope.homePageRecommendedMovies[2].movieName}的详细资料");
            $("a[name='movienametag']").text("${sessionScope.homePageRecommendedMovies[2].movieName}");
            $("span[name='movieaverating']").text("评分:${sessionScope.homePageRecommendedMovies[2].averageRating}");
            <%--$("p[name='moviedescription']").text("${sessionScope.homePageRecommendedMovies[2].movieDescription}");--%>
        }

        if(m[movieid]==2)
        {
            var url="${sessionScope.homePageRecommendedMovies[3].moviePicture}";
            var playId = "${sessionScope.homePageRecommendedMovies[3].playId}";
            $("#wholediv").css('background-image',"url("+url+")" );
            <%--$("a[name=\"moviehref\"]").attr("href","http://so.iqiyi.com/so/q_${sessionScope.homePageRecommendedMovies[3].movieName}");--%>

            <%--if (playId!=''){--%>
                <%--$("a[name=\"moviehref1\"]").attr("href","https://www.iqiyi.com/${sessionScope.homePageRecommendedMovies[3].playId}.html");--%>
            <%--}else{--%>
                $("a[name=\"moviehref\"]").attr("href","http://so.iqiyi.com/so/q_${sessionScope.homePageRecommendedMovies[3].movieName}");
//            }

            $("a[name=\"moviedesc\"]").attr("value","${sessionScope.homePageRecommendedMovies[3].movieId}");
            $("a[name='movienametag']").attr("value","${sessionScope.homePageRecommendedMovies[3].movieId}");
            $("a[name='movienametag']").attr("data-original-title","点击查看${sessionScope.homePageRecommendedMovies[3].movieName}的详细资料");
            $("a[name='movienametag']").text("${sessionScope.homePageRecommendedMovies[3].movieName}");
            $("span[name='movieaverating']").text("评分:${sessionScope.homePageRecommendedMovies[3].averageRating}");
            <%--$("p[name='moviedescription']").text("${sessionScope.homePageRecommendedMovies[3].movieDescription}");--%>
        }
        if(m[movieid]==3)
        {
            //获取id为4的电影的背景图片链接
            var url="${sessionScope.homePageRecommendedMovies[4].moviePicture}";
            var playId = "${sessionScope.homePageRecommendedMovies[4].playId}";
            //设置背景图片为id为4的电影的海报
            $("#wholediv").css('background-image',"url("+url+")");
            //右下播放小图标，修改播放的电影链接
            <%--$("a[name=\"moviehref\"]").attr("href","http://so.iqiyi.com/so/q_${sessionScope.homePageRecommendedMovies[4].movieName}");--%>

            <%--if (playId!=''){--%>
                <%--$("a[name=\"moviehref1\"]").attr("href","https://www.iqiyi.com/${sessionScope.homePageRecommendedMovies[4].playId}.html");--%>
            <%--}else{--%>
                $("a[name=\"moviehref\"]").attr("href","http://so.iqiyi.com/so/q_${sessionScope.homePageRecommendedMovies[4].movieName}");
//            }


            $("a[name=\"moviedesc\"]").attr("value","${sessionScope.homePageRecommendedMovies[4].movieId}");

            $("a[name='movienametag']").attr("value","${sessionScope.homePageRecommendedMovies[4].movieId}");

            $("a[name='movienametag']").attr("data-original-title","点击查看${sessionScope.homePageRecommendedMovies[4].movieName}的详细资料");

            $("a[name='movienametag']").text("${sessionScope.homePageRecommendedMovies[4].movieName}");

            $("span[name='movieaverating']").text("评分:${sessionScope.homePageRecommendedMovies[4].averageRating}");

            <%--$("p[name='moviedescription']").text("${sessionScope.homePageRecommendedMovies[4].movieDescription}");--%>
        }
        if(m[movieid]==4)
        {
            var url="${sessionScope.homePageRecommendedMovies[0].moviePicture}";
            var playId = "${sessionScope.homePageRecommendedMovies[0].playId}";
            $("#wholediv").css('background-image',"url("+url+")" );
            <%--$("a[name=\"moviehref\"]").attr("href","http://so.iqiyi.com/so/q_${sessionScope.homePageRecommendedMovies[0].movieName}");--%>

            <%--if (playId!=''){--%>
                <%--$("a[name=\"moviehref1\"]").attr("href","https://www.iqiyi.com/${sessionScope.homePageRecommendedMovies[0].playId}.html");--%>
            <%--}else{--%>
                $("a[name=\"moviehref2\"]").attr("href","http://so.iqiyi.com/so/q_${sessionScope.homePageRecommendedMovies[0].movieName}");
//            }

            $("a[name=\"moviedesc\"]").attr("value","${sessionScope.homePageRecommendedMovies[0].movieId}");
            $("a[name='movienametag']").attr("value","${sessionScope.homePageRecommendedMovies[0].movieId}");
            $("a[name='movienametag']").attr("data-original-title","点击查看${sessionScope.homePageRecommendedMovies[0].movieName}的详细资料");
            $("a[name='movienametag']").text("${sessionScope.homePageRecommendedMovies[0].movieName}");
            $("span[name='movieaverating']").text("评分:${sessionScope.homePageRecommendedMovies[0].averageRating}");
            <%--$("p[name='moviedescription']").text("${sessionScope.homePageRecommendedMovies[0].movieDescription}");--%>
        }
    });

</script>

<%--搜索栏--%>
<script>
    <%--通过id获取搜索输入文本框，将其绑定一个keyup事件，当keyup事件发生后调用回调函数--%>
    <%--keyup事件：键盘触发事件，在chorm浏览器中，当输入字符就会触发keyup事件--%>
//    $("#inp-query").bind("keyup",function () {
////        获取搜索框的长度
//        var width = document.getElementById("inp-query").offsetWidth+"px";
////        将智能提示框显示出来，并将其长度设置为搜索框的长度
//        $("#search-suggest").show().css({
//            width:width
//        });
//
//        //在搜索框输入数据，提示相关搜索信息
//        var searchText=$("#inp-query").val();
//
//        //获取新的提示信息前需先将<ul>的所有直接子元素即<li>全都删除
//        $("#search-result").children().remove();
//
//        $.post("/searchLikeName",{"search_text":searchText},function (data) {
//            if (data.status == 200) {
//                if(data.data.length!=0) {
//                    $.each(data.data, function (i, item) {
//                        var headHtml = $("#movie-tmpl").html();
//                        headHtml = headHtml.replace(/{id}/g, item.movieId);
//                        headHtml = headHtml.replace(/{cover}/g, item.moviePicture);
//                        headHtml = headHtml.replace(/{moviename}/g, item.movieName);
//                        headHtml = headHtml.replace(/{showyear}/g, item.movieDate);
//                        headHtml = headHtml.replace(/{averating}/s, item.averageRating);
//                        $("#search-result").append(headHtml);
//                    })
//                }
//            }
//        })
//    });

</script>

<%--智能提示框模板--%>
<script type="text/tmpl"  id="movie-tmpl">
 <li id="searchResult">
   <div>
      <a value="{id}" style="text-decoration:none" onclick='javascript:$.post("/Customer/Description",{movieId:$(this).attr("value")}, function (data) {
            if (data=="success") {
                location.href = "/MovieDescription"
            } else {
                //出错的话跳到错误处理页面
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

</body>
</html>
