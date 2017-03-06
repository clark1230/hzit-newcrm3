/**
 * Created by xianyaoji on 2016/12/10.
 */
$(function () {
    //页面加载时根据用户编号获取用户所能访问的按钮,移除隐藏样式
    //---------------------------start-----------------------
    $.get('/showButton/show?userId='+$("input[name='userId']").val(),function(result){
        var $span ="";
        $.each(result,function(item){
            var buttonResource =   result[item];
            if(buttonResource.indexOf("dict")!=-1){
                //console.log(result[item]);
                //显示隐藏的按钮
                //截取字符串
                var button  = buttonResource.substr(buttonResource.indexOf(":")+1,buttonResource.length);
                console.log(button);
                $("#span-"+button).removeClass('span-hidden');

            }
        });
    });
    //--------------------end---------------------------------
    var tbdictPid;
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
    getTbDictTree();
  function getTbDictTree(){
       $.get('/dict/getTreeList', function (result) {
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
                   chkboxType: { "Y": "", "N": "" }  //Y 属性定义 checkbox 被勾选后的情况；
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
                   onClick : zTreeOnClick
               }
           };
           $(function () {
               $.fn.zTree.init($("#treeDemo"), setting, result);
           });
       });

   }
 //  window.setTimeout(getTbDictTree(),100);
    /*function getCurrentNode(treeId, treeNode) {
        var curNode = treeNode;
        zTreeOnClick(curNode);
    }*/
    function zTreeOnClick(treeNode){
        alert('hello');
        console.log(treeNode.id);
    }

    /**
     * 添加数据字典
     */
    $("#add").click(function(){
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo"), nodes = treeObj.getCheckedNodes(true);//获取所有勾选的节点
        if(nodes.length ==0){
           layer.msg('添加之前请选择父节点!');
           return false;
        } else  if(nodes.length >1){
           layer.msg('所选节点数大于1');
        }else if(nodes.length==1){
            //保存数据到隐藏域中
            $("#id").val(nodes[0].id);
            $("#name").val(nodes[0].name);
            //获取父级编号
            layer.open({
                type: 2,
                content: '/dict/add?pId='+nodes[0].pId,
                area: ['700px', '500px'],
                shade:0,
                anim:2,
                end:function(layero, index){
                    getTbDictTree();//重新获取数据
                }
            });
        }
    });
    /**
     * 编辑数据字典
     */
    $("#edit").click(function(){
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo"), nodes = treeObj.getCheckedNodes(true);//获取所有勾选的节点
        if(nodes.length ==0){
            layer.msg('请选择要修改的节点!');
            return false;
        } else  if(nodes.length >1){
            layer.msg('所选节点数大于1');
        }else if(nodes.length==1){
            //保存数据到隐藏域中
            $("#id").val(nodes[0].id);
            $("#name").val(nodes[0].name);
            $("#pId").val(nodes[0].pId);
            //获取父级编号
            layer.open({
                type: 2,
                content: '/dict/edit?id='+nodes[0].id,
                area: ['700px', '500px'],
                shade:0,
                anim:2,
                end:function(layer, index){
                    getTbDictTree();//重新获取数据
                }
            });
        }
    });

    /**
     * 删除数据字典
     *
     */
    $("#delete").click(function(){
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo"), nodes = treeObj.getCheckedNodes(true);//获取所有勾选的节点
        var ids =[];
        if(nodes.length ==0){
            layer.msg('请选择要修改的节点!');
            return false;
        } else{
           //批量删除
           for(var i=0;i<nodes.length;i++){
               ids.push(nodes[i].id);
           }

            layer.confirm('是否要把所选数据彻底删除!', {
                btn: ['确定','取消'] //按钮
            }, function(){
                var index2 = layer.load(1,{time:15*1000}, {
                    shade: [0.1, '#fff'] //0.1透明度的白色背景
                });
                //提交到服务器中
                $.ajax({
                    type:'get',
                    url:'/dict/delete?ids='+ids,
                    success:function(result){
                        layer.close(index2);
                        if(result.code==200){
                            layer.msg(result.msg);
                            getTbDictTree();
                        }else{
                            layer.msg(result.msg);
                        }
                    },
                    error:function(){
                        layer.msg('矮油，出错啦!');
                    }
                });
            }, function(){
                layer.msg('已经取消了!');
            });

        }
    });
});
