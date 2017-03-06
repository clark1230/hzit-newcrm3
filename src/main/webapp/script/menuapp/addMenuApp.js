/**
 * Created by xianyaoji on 2017/2/10.
 */
$(function(){
    var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
    $('#cancel').click(function(){
        parent.layer.close(index);//关闭层
    });
  /* $("input[name=icon]").blur(function(){
         console.log('哈哈');
         //加载图标列表
   });
    $('#demo').hide();
    $('#picker').farbtastic('#color');
    $("#addMenuApp").click(function(){
       $("#addMenuAppForm").submit();
    }) ;*/

  /*------------------添加图标---------------*/
  $("#add").click(function(){
      //表单验证
      //提交数据
      $.post('/menuApp/addMenuApp',$("#addMenuAppForm").serialize(),function(result){
           console.log(result);
      });
  }) ;
});
