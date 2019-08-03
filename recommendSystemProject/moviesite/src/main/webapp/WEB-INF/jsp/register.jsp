<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%request.setCharacterEncoding("UTF-8");%>
<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <title>发现好电影用户注册界面</title>
    <link rel="SHORTCUT ICON" href="/assets/img/knowU.ico"/>
    <!-- CSS -->
    <link href="/assets/css/bootstrap.css" rel="stylesheet">
    <link rel="stylesheet" href="/assets/css/regandlogcommon.css">
    <link rel="stylesheet" href="/assets/css/register.css">
    <script src="/assets/js/jquery.js"></script>
    <script src="/assets/js/jquery.min.js"></script>
    <script src="/assets/js/bootstrap.min.js"></script>
    <style type="text/css">
        .register{
            margin-top: 80px;
        }

        body{
            background:#CCCCCC url(/assets/img/backpost.jpg) no-repeat center center;
            background-size:cover;
            background-attachment:fixed;
            text-align: center;
        }
    </style>
</head>

<body>
<div class="register">
    <h1 style="color: white">用户注册</h1>
    <form id="regForm_mod">
        <%--用户名--%>
        <div id="d1">
            <input type="text" style="width: 300px;border: none" name="userName"  id="regName" placeholder="用 户 名" required="required" />
            <span style="color: red"  class="usernameerror"></span>
        </div>

        <%--邮箱--%>
        <div  id="d2">
            <input type="email" style="width: 300px;border: none" name="email"  id="email" placeholder="电子邮箱" required="required">
            <span  style="color: red" class="emailerror"></span>
        </div>

        <%--密码--%>
        <div id="d3">
            <input type="password" style="width: 300px;border: none" name="userPassword" id="pwd" placeholder="密　码" required="required">
            <span   style="color: red" class="pwderror"></span>
        </div>

            <%--确认密码--%>
        <div id="d4">
            <input type="password" style="width: 300px;border: none" id="pwdRepeat" placeholder="确 认 密 码" required="required">
            <span   style="color: red" class="pwdRerror"></span>
        </div>

        <%--<button style="width: 300px;border: none;background-color: #f56600" data-toggle="modal" data-target="#myModal #identifier" type="button" onclick="REGISTER.reg()">注册</button>--%>
            <button style="width: 300px;border: none;background-color: #f56600" type="button" onclick="REGISTER.reg()">注册</button>

         <%--邮箱提示信息--%>
         <ul class="bRadius2 mail">
                <li data-mail="@qq.com" class="item item1" type="none">@qq.com</li>
                <li data-mail="@sina.com" class="item item2" type="none">@sina.com</li>
                <li data-mail="@126.com" class="item item3" type="none">@126.com</li>
                <li data-mail="@163.com" class="item item4" type="none">@163.com</li>
                <li data-mail="@gmail.com" class="item item5" type="none">@gmail.com</li>
                <li data-mail="@yahoo.com" class="item item6" type="none">@yahoo.com</li>
         </ul>

    </form>

    <%--//错误提示信息--%>
    <div id="mz_Float">
        <div id="tip"style="color: red" class="bRadius2"></div>
    </div>
</div>

<%--选择喜欢的电影--%>
<script type="text/javascript">

    $(".img").click(function () {
        $(this).toggleClass("imgSelected");
    });

   function movieSubmit() {
       var imgs=$(".imgSelected");
//       console.log(imgs);
       var ids = "";
       var len = imgs.size();
       for(var i =0;i< len;i++){
//           var temp=","+$(imgs[i]).attr("movieId")+".";
           var temp=i==len-1?$(imgs[i]).attr("movieId"):$(imgs[i]).attr("movieId")+",";
           ids+=temp;
       }
       if(ids!="") {
           $.post("/customer/register/movieSubmit", {'ids': ids}, function (data) {
               if(data=="ok") {
                   alert("提交成功");
                   $('#myModal').modal('hide');
                   location.href = "/page/login";
               } else
                   alert("请至少选择一部电影");
           })
       } else {
           alert("请至少选择一部电影");
       }
    }

</script>

<!-- 注册事件-->
<script type="text/javascript">

    //用户名获得焦点
    $("#regName").focus(function () {
        $("#regName").removeClass("errorC");
        $("#regName").removeClass("checkedN");
        $(".usernameerror").show();
        $(".usernameerror").text("  ");
    });

    //邮箱获得焦点
    $("#email").focus(function () {
        $("#email").removeClass("errorC");
        $("#email").removeClass("checkedN");
        $(".emailerror").show();
        $(".emailerror").text("  ");
    });

    //密码获得焦点
    $("#pwd").focus(function () {
        $("#pwd").removeClass("errorC");
        $("#pwd").removeClass("checkedN");
        $(".pwderror").show();
        $(".pwderror").text("  ");
    });

    //确认密码获得焦点
    $("#pwdRepeat").focus(function () {
        $("#pwdRepeat").removeClass("errorC");
        $("#pwdRepeat").removeClass("checkedN");
        $(".pwdRerror").show();
        $(".pwdRerror").text("  ");
    });

    //用户名失去焦点
    $("#regName").blur(function () {
        if ($("#regName").val() == "") {
            $("#regName").addClass("errorC");
            $(".usernameerror").html("<span>▲用户名不能为空</span>");
            $(".usernameerror").show();
        }
        else if($("#regName").val().length>10 || $("#regName").val().length<5){
            $("#regName").addClass("errorC");
            //$(".usernameerror").html("<span>▲用户名长度必须为6~10</span>").show();
            $(".usernameerror").html("<span>▲用户名长度必须为6~10</span>");
            $(".usernameerror").show();
        }
        else{
            $("#regName").addClass("checkedN");
            $(".usernameerror").show();
            $(".usernameerror").text("");
        }

        //判断用户名是否被占用
        var surl = "";
        var userName = encodeURI(encodeURI($("#regName").val())); //编码，防止中文乱码；后台收到数据再解码。
        //console.log(username);
        $.ajax({url:surl + "/customer/check/"+userName+"/1",
            //后台处理返回的数据就是这里的data
            success : function(data) {
                //    返回的数据为空或者数据元素的个数为0的话，说明注册项未重复
                if (data.status==200) {
                    //do nothing
                } else {  //否则报错提示
                    $(".usernameerror").show();
                    $(".usernameerror").html("<span>▲用户已被占用，请重新输入</span>");
                    $("#regName").addClass("errorC");
                }
            }
        });
    });

    //密码失去焦点
    $("#pwd").blur(function () {
        var reg1=/^.*[\d]+.*$/;
        var reg2=/^.*[A-Za-z]+.*$/;
        var reg3=/^.*[_@#%&^+-/*\/\\]+.*$/;//验证密码

        if ($("#pwd").val() == "") {
            $("#pwd").addClass("errorC");
            $(".pwderror").show();
            $(".pwderror").html("▲密码不能为空");
        }
        else if ($("#pwd").val().length>16 || $("#pwd").val().length<8){
            $("#pwd").addClass("errorC");
            $(".pwderror").show();
            $(".pwderror").html("<span style = 'position: relative ;left: 28px;'>▲密码长度必须为8~16个字符</span>");
        }
        else if (!(reg1.test($("#pwd").val()) || reg2.test($("#pwd").val()) || reg3.test($("#pwd").val()) )){
            $("#pwd").addClass("errorC");
            $(".pwderror").show();
            $(".pwderror").html("<span style = 'position: relative ;left: 28px;'>▲密码格式错误</span>");
        }
        else{
            //输入正确
            $("#pwd").addClass("checkedN");
            $(".pwderror").show();
            $(".pwderror").text("");
        }
    });

    //确认密码失去焦点
    $("#pwdRepeat").blur(function () {
        if ($("#pwd").val() != $("#pwdRepeat").val() || $("#pwdRepeat").val() =="") {
            $("#pwdRepeat").addClass("errorC");
            $(".pwdRerror").show();
            $(".pwdRerror").html("▲两次密码输入不一致");
        }
        else{
            $("#pwdRepeat").addClass("checkedN");
            $(".pwdRerror").show();
            $(".pwdRerror").text("");
        }
    });

    //邮箱栏键盘操作
    $("#email").keyup(function () {//键盘监听keyup,keydown,keypress事件
        var emailVal = $("#email").val();
        var emailValN = emailVal.replace(/\s/g,'');//去空
        emailValN = emailValN.replace(/[\u4e00-\u9fa5]/g,'');//屏蔽中文
        if(emailValN!=emailVal){
            $("#email").val(emailValN);
        }
        var mailVal = emailValN.split("@");
        var mailHtml = mailVal[0];
        if(mailHtml.length>10)
        {
            mailHtml=mailHtml.slice(0,10)+"...";//字数超出10的部分用省略号代替
        }
        for(var i=1;i<7;i++)
        {
            var M = $(".item"+i).attr("data-mail");
            $(".item"+i).html(mailHtml+M);
        }
    });

    //点击邮箱文本框，显示邮箱提示框
//    $("#email").click(function () {
//        $(".mail").show();
//        return false;
//    });

    //填充邮箱
//    $("li").click(function () {
////        var a = $(this).attr("data-mail");
////        var b = $("#email").text();
////        $("#email").val(b+a);
////        $("#email").trigger("focus");
//        alert("已点击");
//    });

    //隐藏邮箱提示框
//    $(document).click(function(){
//        $(".mail").hide();
//    });

    //邮箱失去焦点
    $("#email").blur(function () {
        $(".mail").hide();
        var reg=/^\w+[@]\w+((.com)|(.net)|(.cn)|(.org)|(.gmail))$$/;
        if ($("#email").val() == "") {
            $("#email").addClass("errorC");
            $(".emailerror").show();
            $(".emailerror").html("▲邮箱不能为空");
        }
        else if(!reg.test($("#email").val())){
            $("#email").addClass("errorC");
            $(".emailerror").show();
            $(".emailerror").html("▲邮箱格式错误");
        }
        else {
            $(".emailerror").show();
            $(".emailerror").text("");
            $("#email").addClass("checkedN");
        }

        //判断邮箱是否被占用
        var surl = "";
        $.ajax({url:surl + "/customer/check/"+escape($("#email").val())+"/2",
            success : function(data) {
                if (data.status==200) {
                } else {
                    $(".emailerror").show();
                    $(".emailerror").html("<span>▲邮箱已被注册，请重新输入</span>");
                    $("#email").addClass("errorC");
                }
            }
        });
    });

    var REGISTER={
        param:{
            surl:""
        },
        inputcheck:function(){
            var flag = true;
            var reg=/^\w+[@]\w+((.com)|(.net)|(.cn)|(.org)|(.gmail))$$/;
            //不能为空检查
            if ($("#regName").val() == "") {
                alert("用户名不能为空！");
                flag = false;
                $('#identifier').modal('hide');
                return;
            }
            if($("#regName").val().length>10 || $("#regName").val().length<6){
                alert("请输入6-10位长度用户名！");
                flag = false;
                $('#identifier').modal('hide');
                return;
            }
            if ($("#email").val() == "") {
                alert("邮箱不能为空！");
                flag = false;
                $('#identifier').modal('hide');
                return;
            }
            if(!reg.test($("#email").val())){
                alert("邮箱格式错误！");
                flag = false;
                $('#identifier').modal('hide');
                return;
            }
            if ($("#pwd").val() == "") {
                alert("密码不能为空！");
                flag = false;
                $('#identifier').modal('hide');
                return;
            }
            if($("#pwd").val().length>16 || $("#pwd").val().length<8){
                alert("密码长度应为8-16个字符");
                flag = false;
                $('#identifier').modal('hide');
                return;
            }
            //密码检查
            if ($("#pwd").val()!= $("#pwdRepeat").val() || $("#pwdRepeat").val()=="") {
                alert("两次密码不一致！");
                flag = false;
                $('#identifier').modal('hide');
                return;
            }
            return flag;
        },
        beforeSubmit:function() {
            var username = encodeURI(encodeURI($("#regName").val()));
            //检查用户和邮箱是否已经被占用
            $.ajax({
                url : REGISTER.param.surl + "/customer/checkboth/"+username+"/"+escape($("#email").val()),
                success : function(data) {
                    if (data.status==200) {
                        REGISTER.doSubmit();
                    } else {
                        alert("注册失败: "+data.msg);
                        /*
                        重置输入框
                         */
                        $('#identifier').modal('hide');
                        $("#regName").val(null);
                        $("#email").val(null);
                        $("#pwd").val(null);
                        $("#pwdRepeat").val(null);
                        /*
                        去除输入框的修饰样式
                         */
                        $("#regName").removeClass("errorC");
                        $("#regName").removeClass("checkedN");
                        $("#email").removeClass("errorC");
                        $("#email").removeClass("checkedN");
                        $("#pwd").removeClass("errorC");
                        $("#pwd").removeClass("checkedN");
                        $("#pwdRepeat").removeClass("errorC");
                        $("#pwdRepeat").removeClass("checkedN");

                    }
                }
            });
        },
        doSubmit:function() {
            $.post("/customer/register",$("#regForm_mod").serialize(), function(data){
                if(data.status == 200){
                    alert('注册成功');
                    REGISTER.login();
                    //注册成功后显示喜欢电影信息的弹框，因为注册成功后才能将该用户id与电影id关联起来
//                    $('#myModal').modal('show');
                } else {
                    alert("注册失败,"+data.msg);
                    $('#identifier').modal('hide');
                }
            });
        },
        login:function() {
            location.href = "/page/login";
        },
        reg:function() {
            if (this.inputcheck()) {
                this.beforeSubmit();
            }
        }
    };
</script>

</body>
</html>