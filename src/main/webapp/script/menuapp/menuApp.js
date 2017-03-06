/**
 * Created by xianyaoji on 2017/2/9.
 */
$(function(){
    var result = {
        id:1,
        appid:'m101',
        icon:'fdf'
    }
    //$('#table').bootstrapTable('destroy');//销毁表格
    var $table =$('#table').bootstrapTable({
        url: '/menuApp/getPageData',
        method: 'get', //这里要设置为get，不知道为什么 设置post获取不了
        toolbar: '#toolbar',                //工具按钮用哪个容器
        pagination: true,                   //是否显示分页（*）
        striped: true,                      //是否显示行间隔色
        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
        idField:'id',
        pageNumber: 1,                       //初始化加载第一页，默认第一页
        pageSize: 20,
        pageList: [5,10,20,30,50],        //可供选择的每页的行数（*）
        //clickToSelect: true,                //是否启用点击选中行
        columns: [{
            field:'operate',
            title:'操作',
            checkbox:true
        },{
            field: 'id',
            title: '编号',
            align:'center',
            visible:false
        }, {
            field: 'appid',
            title: 'app编号',
            align:'center'
        },{
            field:'icon',
            title:'图标',
            width:60,
            align:'center',
            formatter:function(value,row,index){
                var value = row.icon;
                if(value!='null' || value!=''){
                    return '<i style="font-size: 25px;" class="layui-icon">'+value+'</i>';
                }else {
                    return '---';
                }
                //value = value.replace('&','&amp');

            }
        },{
            field:'iconbg',
            title:'图标背景',
            width:200,
            formatter:function(value,row,index){
                 var value = row.iconbg;
                 //if(value =='null'){
                     return '<div style="width:200px;height:30px;border-radius:5px;background-color: '+value+'"></div>';
                // }else{
                //     return '---';
                // }
            }
        },{
            field:'name',
            title:'模块名称'
        },{
            field:'url',
            title:'地址'
        }]

    });
    /***************添加模块*****************/
    $('#add').click(function(){
      layer.open({
          type: 2,
          title: '添加',
          shadeClose: true,
          shade: 0,
          maxmin: true,
          area: ['40%', '70%'],
          content: ['/menuApp/addMenuApp','on'], //iframe的url
          end:function(layer,index){
              $("#table").bootstrapTable("refresh"); //刷新
          }
      });
    });

}) ;

