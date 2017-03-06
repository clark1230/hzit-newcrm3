/**
 * Created by xianyaoji on 2017/2/25.
 */
$(function(){
    var index = parent.layer.getFrameIndex(window.name);
    $("#cancel").click(function(){
       parent.layer.close(index);
    });

    layui.use(['layer','form', 'layedit'], function () {
        var form = layui.form()
            , layer = layui.layer
            , layedit = layui.layedit

        //创建一个编辑器
        var editIndex = layedit.build('LAY_demo_editor');
        //自定义验证规则
        form.verify({
            name: function (value) {
                if (value.length == 0) {
                    return '请输入姓名!';
                }
            },
            wechatNo:function(value){
                if(value==''|| value<=0 && value>=120){
                    return '请输入微信号!';
                }
            },
            sex: function (value) {
                var i = 0;
                i++;
                if (i == 2) {
                    return '请选择性别!';
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

        });
        //监听提交
        form.on('submit(add)', function (data) {
            //var index2 = layer.load(0, {shade: false}); //0代表加载的风格，支持0-2
            //loading层
            var index2 = layer.load(1, {
                shade: [0.1, '#fff'] //0.1透明度的白色背景
            });
            //校验表单
            //提交表单
            $.ajax({
                type: 'post',
                url: '/employeeInfo/edit',
                data: $('#editEmployeeInfo').serialize(),
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
}) ;

