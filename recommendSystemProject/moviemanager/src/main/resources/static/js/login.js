
var url;

function login(){

    var name = $("#login-text").val();
    var pwd = $("#login-pwd").val();

    if (name==null||name.length<=0){
        alert("请输入账号");
        return;
    }

    if (pwd==null||pwd.length<=0){
        alert("请输入密码");
        return;
    }

    url = "/admin/login";

    $("#form").form("submit", {
        url: url,
        onSubmit: function() {
            return $(this).form("validate");
        },
        success: function(result) {
            var result = eval('(' + result + ')');
            if(result.success) {
                alert("登录成功");
                window.location.href = "/toIndex"
            }else {
                alert("登录失败,请输入正确的登录信息");
                window.location.href = "/login.page"
            }
        }
    });
}