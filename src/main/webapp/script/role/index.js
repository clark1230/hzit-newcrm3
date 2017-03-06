/**
 * Created by xianyaoji on 2017/3/3.
 */
 $(function(){

     var $table = $('#table').bootstrapTable({
         url:'/role/ajaxData',
         toolbar: '#toolbar',                //工具按钮用哪个容器
         pagination: true,                   //是否显示分页（*）
         striped: true,                      //是否显示行间隔色
         sortable: true,                     //是否启用排序
         sortOrder: "asc",                   //排序方式
         sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
         idField:'id',
         pageNumber: 1,                       //初始化加载第一页，默认第一页
         pageSize: 20,                       //每页的记录行数（*）
         pageList: [5, 10 ,20 , 30, 50],        //可供选择的每页的行数（*）
         clickToSelect: true,                //是否启用点击选中行
         showToggle:true,
         showRefresh:true,
         showColumns:true,
         silent : true, // 刷新事件必须设置
         onClickRow:function(row,$selement){
             // console.log(row);
             var available = row.available;
             var $disabled = $("#disabled");
             if(available =='1'){  //禁用
                 $disabled.html('<i class="layui-icon">ဇ</i>禁用角色');
                 $disabled.css("background-color",'orange');
             }else if(available == '0'){  //启用
                 $disabled.html('<i class="layui-icon"></i>启用角色');
                 $disabled.css('background-color','green');
             }
         },
         columns: [{
             field:'check',
             title:'勾选',
             checkbox:true
         },{
             filed:'id',
             title:'编号',
             visible:false

         } ,{
             field: 'role',
             title: '角色名称',
             width:100
         },{
             field:'description',
             title:'描述',
             width:200
         },{
             field:'available',
             title:'禁用',
             align:'center',
             width:70,
             formatter:function(values,row,index){
                 var available = row.available;
                 if(row.available == '1'){
                     available = '<i style="font-size: 20px;color:red;" class="layui-icon">ဇ</i>';
                 } else if(row.available=='0'){
                     available = '<i style="font-size: 20px;color:green;" class="layui-icon"></i>';
                 }
                 return available;
             }
         },{
             field:'createByMsg',
             title:'录入人'
         },{
             field:'createTime',
             title:'录入时间',
             sortable:true,
             formatter: function (value, row,index) {
                 var createTime;
                 if(row.createTime == ''|| row.createTime == null){
                     createTime = '---';
                 }else{
                     createTime =  new Date(parseInt(row.createTime)).toLocaleString().replace(/年|月/g, "-").replace(/日/g, " ");
                 }
                 return  createTime;
             },
         },{
             field:'updateByMsg',
             title:'修改人'
         },{
             field:'updateTime',
             title:'修改时间',
             sortable:true,
             formatter: function (value, row,index) {
                 var updateTime;
                 if(row.updateTime == '' || row.updateTime == null){
                     updateTime = "---";
                 } else{
                     updateTime = new Date(parseInt(row.updateTime)).toLocaleString().replace(/年|月/g, "-").replace(/日/g, " ");
                 }
                 return   updateTime;
             }
         }]
     });
     /**
      * 添加角色
      */
       $("#add").click(function(){
           layer.open({
               type: 2,
               title: '添加角色',
               shadeClose: true,
               shade: 0,
               maxmin: true,
               area: ['500px', '450px'],
               content: '/role/add', //iframe的url
               end: function () {
                   $("#table").bootstrapTable("refresh"); //刷新
               }
           });
       });
     /**
      * 修改角色
      */
     $("#edit").click(function(){
         var msg = "编辑角色信息";
         if(checkSelection(msg)) {
             var $selectionsData = $table.bootstrapTable('getSelections');
             layer.open({
                 type: 2,
                 title: '编辑角色',
                 shadeClose: true,
                 shade: 0,
                 maxmin: true,
                 area: ['500px', '450px'],
                 content: '/role/edit?id='+$selectionsData[0].id, //iframe的url
                 end: function () {
                     $("#table").bootstrapTable("refresh"); //刷新
                 }
             });
         }
     });

     //判断
     function checkSelection(msg){
         var $selectionDataArr = $table.bootstrapTable('getAllSelections');
         if($selectionDataArr.length==1){
             return true
         }else if($selectionDataArr.length >1){
             layer.alert('所选数据大于1，请选择要'+msg+'的数据!');
             return false;
         }else{
             layer.alert('请选择要'+msg+'的数据!');
             return false;
         }
     }
     /**
      * 角色授权
      */

     $("#grantResource").click(function(){
         var msg = '分配权限';
         if(checkSelection(msg)) {
             var $selectionsData = $table.bootstrapTable('getSelections');
             layer.open({
                 type: 2,
                 title: '分配权限',
                 shadeClose: true,
                 shade: 0,
                 maxmin: true,
                 area: ['500px', '600px'],
                 content: '/role/grantResource?id='+$selectionsData[0].id, //iframe的url
                 end: function () {
                     $("#table").bootstrapTable("refresh"); //刷新
                 }
             });
         }
     });
     /**
      * 禁用角色
      *
      */
       $("#disabled").click(function(){
           var $selectionsData = $table.bootstrapTable('getSelections');
           var roleIds =[];
           if($selectionsData.length !=0){
               $.each($selectionsData,function(item){
                   roleIds.push($selectionsData[item].id);
               });
               var available= "0";
               if($(this).html().indexOf("禁用")==-1){
                   available = '1';
               } else if($(this).html().indexOf("启用")==-1){
                   available = "0";
               }
               $.get("/role/available?roleIds="+roleIds+"&available="+available,function(result){
                   if(result.code == 200){
                       layer.msg(result.msg);
                       //刷新数据'
                       $("#table").bootstrapTable("refresh"); //刷新
                   } else{
                       layer.msg(result.msg);
                   }
               });
           }else{
               layer.msg('请选择要禁用的角色!');
           }
       });
     /**
      * 删除角色
      */

     $("#delete").click(function(){
         var $selectionsData = $table.bootstrapTable('getSelections');
         var roleIds =[];
         if($selectionsData.length !=0){
             $.each($selectionsData,function(item){
                 roleIds.push($selectionsData[item].id);
             });
             layer.confirm('是否要彻底删除这些数据!', {
                 btn: ['确定','取消'] //按钮
             }, function(){
                 var index2 = layer.load(0,{time:15*1000}, {
                     shade: [0.1, '#fff'] //0.1透明度的白色背景
                 });
                 $.get("/role/delete?roleIds="+roleIds,function(result){
                     layer.close(index2);
                     if(result.code == 200){
                         layer.msg(result.msg);
                         //刷新数据'
                         $("#table").bootstrapTable("refresh"); //刷新
                     } else{
                         layer.msg(result.msg);
                     }
                 });
             }, function(){
                 layer.msg('已经取消了!');
                 $("#table").bootstrapTable("refresh"); //刷新
             });

         }else{
             layer.msg('请选择要彻底的角色!');
         }
     });


 });