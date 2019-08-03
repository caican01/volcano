function searchWebSite() {
    var userName = encodeURI($("#s_userName").val());
    var movieName = encodeURI($("#s_movieName").val());
    $("#dg").datagrid("load",{
        type:userName,
        orderColumn:movieName
    });
    $("#s_userName").val(null);
    $("#s_movieName").val(null)
}