/**
 * Created by xianyaoji on 2016/12/13.
 */
$(function(){
    //关闭窗体
    var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
    //关闭iframe
    $('#close').click(function(){
        parent.layer.close(index);//关闭层
    });
    layui.use(['layer','form', 'layedit'], function () {
        var form = layui.form(), layer = layui.layer, layedit = layui.layedit;
        //监听提交
        form.on('submit(edit)', function (data) {
            //loading层
            var index2 = layer.load(1, {
                shade: [0.1, '#fff'] //0.1透明度的白色背景
            });
            //校验表单
            //提交表单
            $.post('/dict/edit',$("#editDictForm").serialize(),function(result){
                if(result.code ==200){
                    layer.close(index2);
                    layer.msg(result.msg);
                    setTimeout(function(){
                        parent.layer.close(index);
                    },2000);
                }else{
                    layer.close(index2);
                    layer.msg(result.msg);
                }
            });
        });
    });

});