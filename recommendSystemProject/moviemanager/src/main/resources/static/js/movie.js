/* 这个 url 经常要用到：add 中的 save，edit 中的 save，独立出来共享 */
var url;

/*
添加新电影
 */
function openLinkAddDialog() {

    $("#dlg-add").dialog("open").dialog("setTitle","添加新电影");
    url = "/movie/addOneMovie";
}

/* 添加的save保存操作 */
function addSaveLink() {
    $("#fm-add").form("submit", {
        url: url,
        onSubmit: function() {
            /*前端验证，再次使用EasyUI 提供的校验 class*/
            return $(this).form("validate");
        },
        success: function(result) {
            var result = eval('(' + result + ')');
            /* 后台传来一个true */
            if(result.success) {
                $.messager.alert("系统提示", "添加成功");
                resetAddValue();
                $("#dlg-add").dialog("close");
                /*数据变动，需要重新加载数据，作用类似刷新网页*/
                $("#dg").datagrid("reload");
            }
        }
    });
}

/*
添加的关闭弹框操作
 */
function addCloseLinkDialog() {
    resetAddValue();
    $("#dlg-add").dialog("close");
}

/* 修改电影信息 */
function openLinkModifyDialog() {
    var selectedRows = $("#dg").datagrid("getSelections");
    if(selectedRows.length > 1) {
        $.messager.alert("系统提示", "只能选择一条记录");
        return;
    }else if(selectedRows.length == 0) {
        $.messager.alert("系统提示", "请选择一条要编辑的记录");
        return;
    }
    var row = selectedRows[0];
    /*EasyUI 回显信息*/
    $("#fm-modify").form("load", row);
    $("#dlg-modify").dialog("open").dialog("setTitle", "更新电影信息");
    url = "/movie/updateOneMovie";
}

/*
修改的保存操作
 */
function modifySaveLink() {

    $("#fm-modify").form("submit", {
        url: url,
        onSubmit: function() {
            /*前端验证，再次使用EasyUI 提供的校验 class*/
            return $(this).form("validate");
        },
        success: function(result) {
            var result = eval('(' + result + ')');
            /* 后台传来一个true */
            if(result.success) {
                $.messager.alert("系统提示", "修改成功");
                resetModifyValue();
                $("#dlg-modify").dialog("close");
                /*数据变动，需要重新加载数据，作用类似刷新网页*/
                $("#dg").datagrid("reload");
            }else {
                $.messager.alert("系统提示", "修改失败");
                resetModifyValue();
                $("#dlg-modify").dialog("close");
            }
        }
    });
}

/*
关闭
 */
function modifyCloseLinkDialog() {
    resetModifyValue();
    $("#dlg-modify").dialog("close");
}
/* 删除电影 */
function deleteLink() {
    var selectedRows = $("#dg").datagrid("getSelections");
    if(selectedRows.length == 0) {
        $.messager.alert("系统提示", "请选择要删除的记录");
        return;
    }
    var strIds = [];
    /*SpringBoot 小于号要求*/
    for(var i = 0; i < selectedRows.length; i++) {
        strIds.push(selectedRows[i].movieId);
    }
    /* 转化 "1,2,3" 这种格式  */
    var ids = strIds.join(",");
    $.messager.confirm("系统提示", "您确定要删除这<font color='red'>" + selectedRows.length + "</font>条记录吗？", function(r) {
        if(r) {
            $.post("/movie/deleteOneMovie", {
                movieIds: ids
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

/* 清空添加操作表单数据 */
function resetAddValue() {
    $("#add-name").val("");
    $("#add-date").val("");
    $("#add-picture").val("");
    $("#add-type").val("");
}

/*
清空修改操作表单数据
 */
function resetModifyValue() {
    $("#modify-id").val("");
    $("#modify-name").val("");
    $("#modify-date").val("");
    $("#modify-picture").val("");
    $("#modify-type").val("");
    $("#modify-heat").val("");
    $("#modify-rating").val("");
}


/* 搜索 */
function searchWebSite() {

    var movieName = encodeURI($("#s_name").val());
    $("#dg").datagrid("load", {
        movieName: movieName,
        type: $("#s_type").val(),
        movieDate: $("#s_date").val(),
        orderColumn:$("#s_order").val()
    });
}

/*
下拉框实现
 */
/*
选项
 */
$(function () {
    // var type_data = [
    //     {'text':'类 型','value':''},
    //     {'text':'Action','value':'1'},
    //     {'text':'Adventure','value':'2'},
    //     {'text':'Animation','value':'3'},
    //     {'text':"Children's",'value':'4'},
    //     {'text':'Comedy','value':'5'},
    //     {'text':'Crime','value':'6'},
    //     {'text':'Documentary','value':'7'},
    //     {'text':'Drama','value':'8'},
    //     {'text':'Fantasy','value':'9'},
    //     {'text':'Musical','value':'12'},
    //     {'text':'Mystery','value':'13'},
    //     {'text':'Romance','value':'14'},
    //     {'text':'Thriller','value':'16'},
    //     {'text':'War','value':'17'},
    //     {'text':'Western','value':'18'}
    // ]

    var type_data = [
        {'text':'类 型','value':''},
        {'text':'动作','value':'动作'},
        {'text':'冒险','value':'冒险'},
        {'text':'动画','value':'动画'},
        {'text':"儿童",'value':"儿童"},
        {'text':'喜剧','value':'喜剧'},
        {'text':'犯罪','value':'犯罪'},
        {'text':'记录','value':'记录'},
        {'text':'戏剧','value':'戏剧'},
        {'text':'奇幻','value':'奇幻'},
        {'text':'音乐','value':'音乐'},
        {'text':'神秘','value':'神秘'},
        {'text':'浪漫','value':'浪漫'},
        {'text':'惊悚','value':'惊悚'},
        {'text':'战争','value':'战争'},
        {'text':'西部','value':'西部'}
    ];

    $("#s_type").combobox({
        textField : 'text',
        valueField : 'value',
        panelHeight : 'auto',
        data : type_data
    })
});

$(function () {
    var date_data = [
        {'text':'年 代','value':''},
        {'text':'2000','value':'2000'},
        {'text':'1999','value':'1999'},
        {'text':'1998','value':'1998'},
        {'text':'1997','value':'1997'},
        {'text':'1996','value':'1996'},
        {'text':'1995','value':'1995'},
        {'text':'1994','value':'1994'},
        {'text':'1993','value':'1993'},
        {'text':'1992','value':'1992'}
    ];

    $("#s_date").combobox({
        textField : 'text',
        valueField : 'value',
        panelHeight : 'auto',
        data : date_data
    })
});

$(function () {
    var date_data = [
        {'text':'排 序','value':''},
        {'text':'热 度','value':'history_heat'},
        {'text':'评 分','value':'average_rating'}
    ];

    $("#s_order").combobox({
        textField : 'text',
        valueField : 'value',
        panelHeight : 'auto',
        data : date_data
    })
});


/* 清空查询条件 */
function clearAll() {
    $("#s_name").val(null);
    $("#s_type").combobox('clear');
    $("#s_date").combobox('clear');
    $("#s_order").combobox('clear')
}