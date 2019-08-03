/* 搜索 */
function searchWebSite() {
    /*UTF-8编码，防止传输中文出现乱码*/
    var userName = encodeURI($("#s_userName").val());
    var movieName = encodeURI($("#s_movieName").val());
    $("#dg").datagrid("load", {
        type: userName,
        orderColumn:movieName
    });
    $("#s_userName").val(null);
    $("#s_movieName").val(null)
}