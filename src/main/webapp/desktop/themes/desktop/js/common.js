layui.use(['layer','laytpl'],function(){
	var $= layui.jquery,
		layer = layui.layer,
		laytpl = layui.laytpl
		per = parent.layui.layer;//获取父级的layer
	$(".top-open-app").on("click",function(){
		var url = $(this).attr("href"),
		title=$(this).text(),
		pindex = per.getFrameIndex(window.name),
		width = $(this).data("width"),
		height = $(this).data("height");
		url += (url.indexOf("?")!=-1?"&":"?")+"rindex="+pindex;
		height =height?height:parent.layui.jquery(".desktop-container").height()*0.9;//获取桌面高度 赋值给弹窗
		width = width?width:parent.layui.jquery(".desktop-container").width()*0.6;//获取桌面宽度 赋值给弹
		per.open({
			type: 2,//弹窗类型 iframe
			title: title,//标题
			shadeClose: true,//是否关闭背景
			shade: false,
			maxmin: true, //开启最大化最小化按钮
			area: [width+'px', height+'px'],
			content: url,//弹窗引用url
			zIndex: per.zIndex+1,//索引
			success: function(layero, index){
				//var itself = Array([]);
				var alldesktopapp = layui.data('desktop-app')['desktop-app-'+pindex];//获取窗口缓存信息集合
				if(!alldesktopapp){
					alldesktopapp = [index];
				}else{
					alldesktopapp.push(index);
				}
				layui.data('desktop-app', {
				  key: 'desktop-app-'+pindex
				  ,value: alldesktopapp
				});//保存缓存信息
			}	
		});
		return false;
		//var index = per.getFrameIndex(window.name); //先得到当前iframe层的索引
		//per.close(index); 
	});	
});