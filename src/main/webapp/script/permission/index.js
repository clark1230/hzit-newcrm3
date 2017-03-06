/**
 * Created by xianyaoji on 2017/3/1.
 */
$(function(){
    var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
    $.get('/employeeInfo/resource', function (result) {
        var setting = {
            //页面上的显示效果
            view: {
                dblClickExpand: false,
                showLine: true,
                selectedMulti: false
            },
            check: {
                enable: true,
                //勾选 checkbox 对于父子节点的关联关系
                chkboxType: { "Y": "ps", "N": "s" }  //Y 属性定义 checkbox 被勾选后的情况；
                //N 属性定义 checkbox 取消勾选后的情况；
                //"p" 表示操作会影响父级节点；
                //"s" 表示操作会影响子级节点。
                //请注意大小写，不要改变
            },
            data: {
                simpleData: {
                    enable: true
                }
            },callback:{
                //beforeClick: getCurrentNode,
               // onClick : zTreeOnClick
            }
        };
        $(function () {
            $.fn.zTree.init($("#treeDemo"), setting, result);
        });
        checkNode();

    });
    $("#grantResource").click(function(){
        //获取所有已经选中的节点
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo"), nodes = treeObj.getCheckedNodes(true),ids  = "",description='';
        if(nodes.length < 1){
            layer.alert('请选择要授权的模块!');
            return false;
        }
        var ids=[];
        var appids =[];
        //角色编号信息
        for (var i = 0; i < nodes.length; i++) {
            console.log(nodes[i].appid);
            ids.push(nodes[i].id);
            console.log(typeof(nodes[i].appid));
            if(nodes[i].appid!='0'){
                if(nodes[i].appid!='1'){
                    appids.push(nodes[i].appid);
                }
            }
        }

        //一步提交数据
        $.ajax({
            method:'post',
            url:'/employeeInfo/grantResouce',
            data:"userId="+$("input[name='userId']").val()+"&resourceIds="+ids+"&appids="+appids,
            success:function(result){
                if (result.code != 200) {
                    layer.msg(result.msg);
                } else {
                    layer.msg(result.msg);
                    window.setTimeout(function () {//iframe 1秒后关闭
                        parent.layer.close(index);//关闭层
                    }, 2000);
                }
            }
        });
    });
    //关闭页面
    $("#close").click(function(){
       parent.layer.close(index);
    });

    $('#expandAll').click(function(){
        //展开全部节点
        $.fn.zTree.getZTreeObj("treeDemo").expandAll(true);
    });
    //收缩全部节点
    $('#unExpandAll').click(function () {
        $.fn.zTree.getZTreeObj("treeDemo").expandAll(false);
    });
    //取消勾选
    $('#unChecked').click(function(){
        // $.fn.zTree.getZTreeObj("treeDemo").cancelSelectedNode();
        $.fn.zTree.getZTreeObj("treeDemo").checkAllNodes(false);
    });
    /**
     * 页面记载时查看有哪些权限
     */
    function checkNode(){
        //页面加载时获取该角色的模块信息
        $.get('/employeeInfo/checkResource?userId='+$("input[name='userId']").val(),function(result){
            var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
            var arr = result.split(",");
            for(var i=0;i<arr.length;i++){
                treeObj.checkNode(treeObj.getNodeByParam("id", arr[i], null), true, false);
            }
        });
    }

});