var index_tabs;

$(function() {
    index_tabs = $('#index_tabs').tabs({
        fit : true,
        border : false
    });
});

//点击树形菜单的响应方法
function treeNodeClick(node){
    console.log(node);
    if (node.attributes && node.attributes.url) {
        var url=node.attributes.url;
        addTab({
            url : url,
            title : node.text,
            iconCls : node.iconCls
        });
    }
}

//添加动态标签的方法，在菜鸟教程中都可以找到
function addTab(params) {
		// var iframe = '<iframe src="' + params.url + '" frameborder="0" style="border:none;margin-top: 75px;margin-left: 28px;width:100%;height: 95%;box-shadow: 0 0 15px 3px rgba(51, 51, 51, 0.7);"></iframe>';//99.4
    var iframe = '<iframe src="' + params.url + '" frameborder="0" style="border:none;width:100%;height: 98%;margin-top: 5px;"></iframe>';
	var t = $('#index_tabs');
		var opts = {
			title : params.title,
			closable : true,
			iconCls : params.iconCls,
			content : iframe,
			border : false,
			fit : true
		};
		if (t.tabs('exists', opts.title)) {
			t.tabs('select', opts.title);
			var current_tab = $('#index_tabs').tabs('getSelected');
			$('#index_tabs').tabs('update',{
			   tab:current_tab,
			   options : {
			     content : iframe,
			   }
			});
			parent.$.messager.progress('close');
		} else {
			t.tabs('add', opts);
		}
	}
