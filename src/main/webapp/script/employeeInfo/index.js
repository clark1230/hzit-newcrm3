/**
 * Created by xianyaoji on 2017/2/23.
 */
$(function(){
    //页面加载时根据用户编号获取用户所能访问的按钮,移除隐藏样式
    $.get('/showButton/show?userId='+$("input[name='userId']").val(),function(result){
        var $span ="";
        $.each(result,function(item){
            var buttonResource =   result[item];
            if(buttonResource.indexOf("employeeInfo")!=-1){
                //console.log(result[item]);
                //显示隐藏的按钮
                //截取字符串
                var button  = buttonResource.substr(buttonResource.indexOf(":")+1,buttonResource.length);
                $("#span-"+button).removeClass('span-hidden');

            }
        });
    });
    var $table = $('#table').bootstrapTable({
        url:'/employeeInfo/getAjaxData',
        toolbar: '#toolbar',                //工具按钮用哪个容器
        pagination: true,                   //是否显示分页（*）
        striped: true,                      //是否显示行间隔色
        sortable: true,                     //是否启用排序
        sortOrder: "asc",                   //排序方式
        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
        idField:'userId',
        pageNumber: 1,                       //初始化加载第一页，默认第一页
        pageSize: 20,                       //每页的记录行数（*）
        pageList: [5, 10 ,20 , 30, 50, 100],        //可供选择的每页的行数（*）
        clickToSelect: true,                //是否启用点击选中行
        showToggle:true,
        showRefresh:true,
        showColumns:true,
        silent : true, // 刷新事件必须设置
        onDblClickRow:function(row, $element){//双击事件
            /*$('#hiddenUserId').val(row.userId);
            layer.open({
                type: 2,
                title: '编辑用户信息',
                shadeClose: true,
                shade: 0.1,
                maxmin: true,
                area: ['60%', '60%'],
                content: '/employeeInfo/editEmployeeInfo',//iframe的url
                end:function() {
                    $("#table").bootstrapTable("refresh"); //刷新
                }
            });*/
        },
        onClickRow:function(row,$selement){
         // console.log(row);
          var isLocked = row.isLocked;
          var $disabled = $("#disabled");
          if(isLocked =='0'){  //禁用
               $disabled.html('<i class="layui-icon">ဇ</i>禁用');
               $disabled.css("background-color",'orange');
          }else if(isLocked == '1'){  //启用
              $disabled.html('<i class="layui-icon"></i>启用');
              $disabled.css('background-color','green');
          }
        },
        columns: [{
            field:'check',
            title:'勾选',
            checkbox:true
        },{
            filed:'userId',
            title:'编号',
            visible:false

        } ,{
            field: 'name',
            title: '用户姓名',
            width:100
        },{
            field:'gender',
            title:'性别',
            formatter:function(value,row,index){
                var gender;
                if(row.gender =='1'){
                    gender = '男';
                }else if(row.gender =='2'){
                    gender = '女';
                }else{
                    gender='<span style="color:orangered;">非法数据</span>';
                }
                return gender;
            },
            width:50
        },{
            field:'wechatNo',
            title:'微信号',
            width:80
        },{
            field:'tel',
            title:'电话号码',
            width:80
        },{
            field:'email',
            title:'邮箱',
            width:80
        },{
            field:'isEmailActive',
            title:'邮件发送',
            align:'center',
            width:80,
            formatter:function(value,row,index){
                var emailActive = row.isEmailActive;
                if(emailActive == '0'){
                    emailActive = '<i style="font-size: 20px;color:red" class="layui-icon">ဇ</i>';
                }else if(emailActive =='1'){
                    emailActive = '<i style="font-size: 20px;color:green" class="layui-icon"></i>';
                }
                return emailActive;
            }
        },{
            field:'isPermission',
            title:'是否授权',
            align:'center',
            width:80,
            formatter:function(value,row,index){
                var isPermission = row.isPermission;
                if(isPermission == '0'){
                    isPermission = '<span style="color:red;">尚未授权</span>';
                }else if(isPermission =='1'){
                    isPermission = '<span style="color:orange;">权限授权</span>';
                }else if(isPermission =='2'){
                    isPermission='<span style="color:purple;">角色授权</span>';
                }
                return isPermission;
            }
        },{
            field:'dept',
            title:'部门' ,
            width:90
        },{
            field:'company',
            title:'公司',
            width:150
        },{
            field:'isLocked',
            title:'禁用',
            width:50,
            align:'center',
            formatter:function(values,row,index){
                var isLocked = row.isLocked;
                if(row.isLocked == '0'){
                     isLocked = '<i style="font-size: 20px;color:red;" class="layui-icon">ဇ</i>';
                } else if(row.isLocked=='1'){
                    isLocked = '<i style="font-size: 20px;color:green;" class="layui-icon"></i>';
                }
                return isLocked;
            }

        },{
            field:'isConsultant',
            title:'咨询师',
            width:30,
            align:'center',
            formatter:function(value,row,index){
                var isConsultant;
                if(row.isConsultant == '1'){
                    isConsultant = '<i style="font-size: 20px;color:green;" class="layui-icon"></i>';
                }else{
                    isConsultant = '<i style="font-size: 20px;color:red;" class="layui-icon">ဇ</i>';
                }
                return isConsultant;
            }
        },{
            field:'createBy',
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
            field:'updateBy',
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
        }/*,{
            field:'operate',
            title:'操作',
            width:100,
            align:'center',
            formatter:function(value,row,index){
                var operate = '<button class="layui-btn layui-btn-normal layui-btn-mini"><i class="layui-icon"></i>' +
                    '</button><button class="layui-btn layui-btn-warm layui-btn-mini"><i class="layui-icon"></i></button>';
                return operate;
            }
        }*/]
    });
    //添加用户
    $('#add').click(function(){
        layer.open({
            type: 2,
            title: '添加用户',
            shadeClose: true,
            shade: 0,
            maxmin: true,
            area: ['50%', '80%'],
            content: '/employeeInfo/add', //iframe的url
            success:function(layer,index){
            },end:function() {
                $("#table").bootstrapTable("refresh"); //刷新
            }
        });
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
    //编辑用户
    $('#edit').click(function(){
        var msg = "编辑用户信息";
        if(checkSelection(msg)) {
            var $selectionsData = $table.bootstrapTable('getSelections');
            layer.open({
                type: 2,
                title: '编辑用户信息',
                shadeClose: true,
                shade: 0,
                maxmin: true,
                area: ['50%', '80%'],
                content: '/employeeInfo/edit?userId='+$selectionsData[0].userId, //iframe的url
                end: function () {
                    $("#table").bootstrapTable("refresh"); //刷新
                }
            });
        }
    });
    /**
     * 禁用用户
     */
    $("#disabled").click(function(){
        var $selectionsData = $table.bootstrapTable('getSelections');
        var userIdArr =[];
        if($selectionsData.length !=0){
            $.each($selectionsData,function(item){
                userIdArr.push($selectionsData[item].userId);
            });
            var isLocked= "1";
            console.log(typeof($(this).html()));
            if($(this).html().indexOf('禁用')==-1){
                isLocked = '1';
            } else if($(this).html().indexOf("启用")==-1){
                 isLocked = "0";
            }
            $.get("/employeeInfo/locked?userIdArr="+userIdArr+"&isLocked="+isLocked,function(result){
               if(result.code == 200){
                   layer.msg(result.msg);
                   //刷新数据'
                   $("#table").bootstrapTable("refresh"); //刷新
               } else{
                   layer.msg(result.msg);
               }
            });
        }else{
            layer.msg('请选择要禁用的用户!');
        }
    });

    /**
     * 重置密码
     */
    $("#resetPwd").click(function(){

        var msg = "重置密码";
        if(checkSelection(msg)) {
            var $selectionsData = $table.bootstrapTable('getSelections');
            if($selectionsData[0].email != '') {
                layer.confirm('是否要重置密码!', {
                    btn: ['确定','取消'] //按钮
                }, function(){
                    layer.msg('开始重置密码!');
                    resetPwd();
                }, function(){
                    layer.msg('已经取消了!');
                    $("#table").bootstrapTable("refresh"); //刷新
                });
                function  resetPwd(){
                    var index2 = layer.load(0, {
                        
                        shade: [0.1, '#fff'] //0.1透明度的白色背景
                    });
                    $.get("/employeeInfo/resetPwd?userId="+$selectionsData[0].userId+"&email="+$selectionsData[0].email,function(result){
                        if(result.code ==200){
                            layer.msg(result.msg) ;
                            layer.close(index2); //关闭当前弹层
                            $("#table").bootstrapTable("refresh"); //刷新
                        } else{
                            layer.msg(result.msg);
                            layer.close(index2); //关闭当前弹层
                        }
                    });
                }
            }
            else{
                layer.msg('还没有邮箱，请添加邮箱!');
            }

        }
    });
    //分配权限
    $('#grantResource').click(function () {
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
                content: '/employeeInfo/grantResouce?userId='+$selectionsData[0].userId, //iframe的url
                end: function () {
                    $("#table").bootstrapTable("refresh"); //刷新
                }
            });
        }
    });


    //分配角色
    $('#grantRole').click(function(){
        var msg = '分配角色';
        if(checkSelection(msg)) {
            var $selectionsData = $table.bootstrapTable('getSelections');
            layer.open({
                type: 2,
                title: '分配角色',
                shadeClose: true,
                shade: 0,
                maxmin: true,
                area: ['400px', '250px'],
                content: '/employeeInfo/grantRole?userId='+$selectionsData[0].userId, //iframe的url
                end: function () {
                    $("#table").bootstrapTable("refresh"); //刷新
                }
            });
        }
    });
    //删除用户
    $('#btnRemoveEmployeeInfo').click(function(){
        alert('fdgg');
        if(checkSelection('删除')){
            var $selectionsData = $table.bootstrapTable('getSelections');
            $.get('',function(result){
                if(typeof(result)=='object'){

                }else{
                    layer.msg('对不起，您没有权限删除该用户!');
                }
            });
        }
    });
});