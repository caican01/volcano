
/* 这个 url 经常要用到：add 中的 save，edit 中的 save，独立出来共享 */
var url;

/* 修改用户 */
function openLinkModifyDialog() {

    var selectedRows = $("#dg").datagrid("getSelections");
    if(selectedRows.length > 1) {
        $.messager.alert("系统提示", "只能选择一条记录");
        return;
    }
    if(selectedRows.length == 0) {
        $.messager.alert("系统提示", "请选择一条要编辑的记录");
        return;
    }
    var row = selectedRows[0];
    /*EasyUI 回显信息*/
    $("#fm").form("load", row);
    $("#dlg").dialog("open").dialog("setTitle", "更新用户信息");
    url = "/user/updateUserMessage";
}

/* 3、删除用户 */
function deleteLink() {
    var selectedRows = $("#dg").datagrid("getSelections");
    if(selectedRows.length == 0) {
        $.messager.alert("系统提示", "请选择要删除的记录");
        return;
    }
    var strIds = [];
    /*SpringBoot 小于号要求*/
    for(var i = 0; i < selectedRows.length; i++) {
        strIds.push(selectedRows[i].userId);
    }
    /* 转化 "1,2,3" 这种格式  */
    var ids = strIds.join(",");
    $.messager.confirm("系统提示", "您确定要删除这<font color='red'>" + selectedRows.length + "</font>条记录吗？", function(r) {
        if(r) {
            $.post("/user/deleteOneUser", {
                userIds: ids
            }, function(result) {
                /* 后台传来一个true */
                if(result.success) {
                    $.messager.alert("系统提示", "数据已成功删除");
                    /*数据变动，需要重新加载数据，作用类似刷新网页*/
                    $("#dg").datagrid("reload");
                } else {
                    $.messager.alert("系统提示", "数据删除失败");
                }
            }, "json");
        }
    });
}

/* save 保存操作，位于 add、edit 中 */
function saveLink() {
    $.post(url,{
        userId:$("#id").val(),
        userPassword:$("#word").val()
        },function (data) {
        if (data.success){
            $.messager.alert("系统提示", "修改成功");
            resetValue();
            /*数据变动，需要重新加载数据，作用类似刷新网页*/
            $("#dg").datagrid("reload");
            closeLinkDialog();
        }else {
            $.messager.alert("系统提示", "修改失败");
            closeLinkDialog();
            /*数据变动，需要重新加载数据，作用类似刷新网页*/
            $("#dg").datagrid("reload");
        }
    })
}

/* 清空表单数据 */
function resetValue() {
    $("#id").val("");
    $("#name").val("");
    $("#word").val("");
    $("#email").val("");
    $("#register").val("");
    $("#last-login").val("");
    $("#authority").val("");
}

/* 关闭对话框 */
function closeLinkDialog() {
    resetValue();
    $("#dlg").dialog("close");
}

/* 搜索 */
function searchWebSite() {
    var name = encodeURI($("#s_name").val());
    var email = escape($("#s_email").val());
    $("#dg").datagrid("load", {
        userName: name,
        email: email
    });
    $("#s_name").val(null);
    $("#s_email").val(null)
}

/*
禁用用户登录权限
 */
function banLink(){
    var selectedRows = $("#dg").datagrid("getSelections");
    if(selectedRows.length == 0) {
        $.messager.alert("系统提示", "请选择要禁用的注册用户");
        return;
    }
    if(selectedRows.length == 1) {
        var row = selectedRows[0];
        if (row.authority == 0){
            $.messager.alert("系统提示","该用户登录权限已被禁用，不能重复禁用");
            return;
        }
    }

    var results = [];
    var errs = [];
    for(var i = 0;i<selectedRows.length;i++){
        if(selectedRows[i].authority == 1){
            var item = selectedRows[i].userId+":"+selectedRows[i].authority;
            results.push(item)
        }else {
            errs.push(selectedRows[i].userId)
        }
    }
    var users = results.join(",");
    url = "/user/updateUserAuthority";
    $.post(
        url,
        {
            users:users
        },
        function (data) {
            if (data.success){
                if (errs.length>0){
                    $.messager.alert("系统提示","部分用户登录权限存在重复禁用情况，请仔细核查")
                }else {
                    $.messager.alert("系统提示","所选用户登录权限已全部禁用")
                }
                $("#dg").datagrid("reload");
            }else {
                $.messager.alert("系统提示","登录权限禁用失败，请确认后重试")
            }
        })
}

/*
启用用户登录权限
 */
function openLink(){
    var selectedRows = $("#dg").datagrid("getSelections");
    if(selectedRows.length == 0) {
        $.messager.alert("系统提示", "请选择要启用的注册用户");
        return;
    }
    if(selectedRows.length == 1) {
        var row = selectedRows[0];
        if (row.authority == 1){
            $.messager.alert("系统提示","该用户登录权限已开启，不能重复开启");
            return;
        }
    }
    var results = [];
    var errs = [];
    for(var i = 0;i<selectedRows.length;i++){
        if(selectedRows[i].authority == 0){
            var item = selectedRows[i].userId+":"+selectedRows[i].authority;
            results.push(item)
        }else {
            errs.push(selectedRows[i].userId)
        }
    }
    var users = results.join(",");
    url = "/user/updateUserAuthority";
    $.post(url,
        {
            users:users
        },
        function (data) {
            if (data.success){
                if (errs.length>0){
                    $.messager.alert("系统提示","部分用户登录权限存在重复启用情况，请仔细核查")
                }else {
                    $.messager.alert("系统提示","所选用户登录权限已全部启用")
                }
                $("#dg").datagrid("reload");
            }else {
                $.messager.alert("系统提示","登录权限启用失败，请确认后重试")
            }
        })
}

/* 清空查询条件 */
function clearAll() {
    $("#s_name").val(null);
    $("#s_email").val(null)
}

/*
导出用户数据为excel文件操作
 */
function userDataExport() {
    window.location.href = "/exportUserExcel"
}