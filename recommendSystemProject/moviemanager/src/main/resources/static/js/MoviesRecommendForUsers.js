function searchWebSite() {
    var userName = encodeURI($("#s_userName").val());
    $("#dg").datagrid("load",{
        userName:userName
    });
    $("#s_userName").val(null)
}