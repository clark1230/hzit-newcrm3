/**
 * Created by xianyaoji on 2017/2/12.
 */
/*
$(function(){
    var form = layui.form();
    form.on('select',function(data){
        console.log(data);
    });
   /!* layui.use(['form', 'layedit', 'laydate'], function() {
        var form = layui.form()
            , layer = layui.layer
            , layedit = layui.layedit
            , laydate = layui.laydate;
    });
    *!/
});
 $(function(){
     function validateForm(){
         /!*关键在此增加了一个return，返回的是一个validate对象，这个对象有一个form方法，返回的是是否通过验证*!/
         //表单校验
        return  $("#addCustomerInfoForm").validate({
             onfocusout: function(element){
                 $(element).valid();
             },
             submitHandler:function(){
                 console.log('提交表单!');
             },
             rules: {
                 realName:'required',
                 sex:'required',
                 age:{
                     'required':true,
                      pattern:/^(?:[1-9][0-9]?|1[01][0-9]|120)$/
                 },
                 nativePlace:'required',
                 tel:{
                     required:true,
                     pattern:/^0?1[2345678]\d{9}$/
                 },
                 educationBg:{
                     checkEducationBg:true
                 },
                 customerState:{
                     required:true
                 },
                 graduateTime:{
                     required:true,
                     pattern:/^(\d{4})-(\d{2})-(\d{2})$/
                 },

             },invalidHandler: function(form, validator) {  //不通过回调
                 return false;
             },messages:{
                 realName:{
                     required:'请输入用户名!',
                 },
                 age:{
                     required:'请输入年龄!',
                     pattern:'年龄超出范围了!',
                 },
                 nativePlace:{required:'请输入籍贯!'},
                 tel:{
                     required:'请输入电话号码!',
                     pattern: "电话号码格式不正确！"
                 },
                 educationBg:{
                     required:'请选择学历!'
                 } ,
                graduateTime:{
                    required:'请输入毕业时间!',
                    pattern: "日期格式不正确！"
                }
             }
         });
     }
     //注册表单验证
     $(validateForm());
     //自定义校验
     $.validator.addMethod("checkEducationBg", function(value, element) {
         console.log(value);
         return value=='';
     }, "请选择学历!");

     //------------------------------------

     //-------------------------------------
     var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
     $('#cancel').click(function(){
         parent.layer.close(index);//关闭层
     });
     $('#add').click(function(){

         //加载层
         var index2 = layer.load(0, {shade: false}); //0代表加载的风格，支持0-2
        //loading层
         var index2 = layer.load(1, {
             shade: [0.1,'#fff'] //0.1透明度的白色背景
         });

         //获取学历信息
         var result = $("input[type='text'][placeholder='请选择学历!']")[0];
         console.log(result);
         //校验表单
         if(validateForm().form()) {
             //提交表单
             $.ajax({
                 type:'post',
                 url:'/customerInfo/add',
                 data:$('#addCustomerInfoForm').serialize(),
                 success:function(result){
                     if(result.code==200){
                         layer.close(index2); //关闭当前弹层
                         layer.msg('保存成功!');
                         setTimeout(function(){
                             parent.layer.close(index);//关闭层
                         },1000);
                     }else{
                         layer.msg('保存失败!');
                         layer.close(index2); //关闭当前弹层
                     }
                 }
             });
         }else{
             layer.close(index2); //关闭当前弹层
         }
     });


     //测试
     $("#test").click(function(){
        var result = $("#educationBg option:selected")[0];
        console.log(result);
     });
 })  ;

*/
$(function(){
    var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
    if(index <200){
        $("#cancel").css('display','inline');
    }
    $('#cancel').click(function(){
        parent.layer.close(index);//关闭层

    });
    //校验
    var $tel =$("input[name='tel']");
    var $realName = $("input[name='realName']");
    $tel.blur(function(){
            var telValue = $tel.val();
            if(telValue!='' && $realName.val()!=''){
                //到服务器中检测该用户是否存在!!
                $.get('/customerInfo/checkCustomerInfo?realName='+$realName.val()+"&tel="+$tel.val(),function(result){
                    if(result.code==200){
                        layer.msg(result.msg);
                        $('#realName').css('border-color','lightgrey');
                        $('#add').css('pointer-events','visible').css('background-color','#0099FF');
                    }else{
                        $('#realName').css('border-color','red');
                        $('#add').css('pointer-events','none').css('background-color','lightgrey');
                        layer.msg(result.msg);
                    }
                });
            }
    });
    
    layui.use(['layer','form', 'layedit'], function () {
        var form = layui.form()
            , layer = layui.layer
            , layedit = layui.layedit
        form.on('select(customerState)', function(data) {
            if(data.value==34){
                layer.alert('注意，选择为<span style="color:red;">已流失</span>，会导致该数据从您的列表中消失，请慎重操作!');
            }
        });
        //创建一个编辑器
        var editIndex = layedit.build('LAY_demo_editor');
        //自定义验证规则
        form.verify({
            realName: function (value) {
                if (value.length == 0) {
                    return '请输入学员名称!';
                }
            },

            sex: function (value) {
                var i = 0;
                i++;
                if (i == 2) {
                    return '请选择性别!';
                }
            },
            nativePlace: function (value) {
                if (value == '') {
                    return '请输入籍贯!';
                }
            },
            tel: function (value) {
                var isMob = /1[2345678]\d{10}$/;
                if (value == '') {
                    return '请输入电话号码!'
                } else if (isMob.test(value)) {
                    return '电话号码格式不正确!'
                }
            },
            educationBg: function (value) {
                if (value == '') {
                    return '请选择学历!';
                }
            },
            customerState: function (value) {
                if (value == '') {
                    return '请选择学员状态!';
                }
            },
            customerLevel: function (value) {
                if (value == '') {
                    return '请选择学员级别!';
                }
            },
            targetSkill: function (value) {
                if (value == '') {
                    return '请选择目标技能!';
                }
            },
            userId: function (value) {
                if (value == '') {
                    return '请选择咨询师!';
                }
            },
            guandan: function (value) {
                if (value == '') {
                    return '请选择关单人!';
                }
            },
            recruitChannel: function (value) {
                if (value == '') {
                    return '请选择应聘渠道!';
                }
            },
            introducer: function (value) {
                if (value == '') {
                    return '请选择邀约人!';
                }
            }
            , content: function (value) {
                layedit.sync(editIndex);
            }
        });
        //监听提交
        form.on('submit(add)', function (data) {
            var index2 = layer.load(0, {shade: false}); //0代表加载的风格，支持0-2
            //loading层
            var index2 = layer.load(1, {
                shade: [0.1, '#fff'] //0.1透明度的白色背景
            });
            //校验表单
            //提交表单
            $.ajax({
                type: 'post',
                url: '',
                data: $('#addCustomerInfoForm').serialize(),
                success: function (result) {
                    if (result.code == 200) {
                        layer.close(index2); //关闭当前弹层
                        layer.msg('保存成功!');
                        setTimeout(function () {
                           // parent.layer.close(index);//关闭层
                        }, 1000);
                    } else {
                        layer.msg('保存失败!');
                        layer.close(index2); //关闭当前弹层
                    }
                }
            });
        });
    });

});
