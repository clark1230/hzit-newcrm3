/**
 * Created by xianyaoji on 2017/2/18.
 */
/*
* 修改学员信息
* */
/*$(function(){
    var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
    $('#cancel').click(function(){
        parent.layer.close(index);//关闭层
    });
    $("#edit").click(function(){
        //表单验证
        //加载层
        var index2 = layer.load(0, {shade: false}); //0代表加载的风格，支持0-2
        //loading层
        var index2 = layer.load(1, {
            shade: [0.1,'#fff'] //0.1透明度的白色背景
        });

        $.ajax({
            type:'post',
            url:'/customerInfo/edit',
            data:$('#editCustomerInfoForm').serialize(),
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
    }) ;

}) ;*/
var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
layui.use(['layer','form', 'layedit'], function () {
    var form = layui.form()
        , layer = layui.layer
        , layedit = layui.layedit
    var form = layui.form();
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
        age:function(value){
            if(value==''|| value<=0 && value>=120){
                return '请输入年龄!';
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
    form.on('submit(edit)', function (data) {
        var index2 = layer.load(0, {shade: false}); //0代表加载的风格，支持0-2
        //loading层
        var index2 = layer.load(1, {
            shade: [0.1, '#fff'] //0.1透明度的白色背景
        });
        //校验表单
        //提交表单
        $.ajax({
            type: 'post',
            url: '/customerInfo/edit',
            data: $('#editCustomerInfoForm').serialize(),
            success: function (result) {
                if (result.code == 200) {
                    layer.close(index2); //关闭当前弹层
                    layer.msg('保存成功!');
                    setTimeout(function () {
                        parent.layer.close(index);//关闭层
                    }, 1000);
                } else {
                    layer.msg('保存失败!');
                    layer.close(index2); //关闭当前弹层
                }
            }
        });
    });
});
