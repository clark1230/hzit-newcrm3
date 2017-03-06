/**
 * Created by xianyaoji on 2017/3/4.
 */
 $(function(){
     var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
     //关闭窗口
     $("#cancel").click(function(){
          parent.layer.close(index);
     });

     //targetSkill
     layui.use(['layer','form', 'layedit'], function () {
         var form = layui.form(), layer = layui.layer, layedit = layui.layedit ;
         //监听提交
         form.on('submit(add)', function (data) {
             //loading层
             var index2 = layer.load(1,{time:15*1000}, {shade: [0.1, '#fff']}); //15秒后自动消失
             //校验表单
             //提交表单
             $.ajax({
                 type: 'post',
                 url: '/employeeInfo/grantRole',
                 data: $('#grantRoleForm').serialize(),
                 success: function (result) {
                     if (result.code == 200) {
                         layer.close(index2); //关闭当前弹层
                         layer.msg(result.msg);
                         setTimeout(function () {
                             parent.layer.close(index);//关闭层
                         }, 1000);
                     } else {
                         layer.msg(result.msg);
                         layer.close(index2); //关闭当前弹层
                     }
                 }
             });
         });
     });
 });