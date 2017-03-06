/**
 * Created by 冼耀基 on 2016/11/10.
 */
$(function(){

    //window.setTimeout(getConsultant(),500);
    var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
    //关闭iframe

    //异步修改数据
    $("#btnEditCustomerInfo").click(function(){
        //表单校验
        var realName = $('#realName').val();
        if(realName === ''){
            $('#realName').css('border-color','red');
            layer.msg('请输入用户名!');
            return false;
        }
        //校验电话号码
        var $tel = $('#tel');
        if($tel.val() ==''){
            $tel.css("border-color",'red');
            layer.msg('请输入电话号码!');
            return false;
        }
        var isMob =/^0?1[2345678]\d{9}$/;
        if(!isMob.test($tel.val())){
            layer.msg('电话号码格式不正确!');
            $tel.css('border-color','red');
          return false;
        }
        var index2 = layer.load(1,
            {time: 20*1000},{
            shade: [0.1, '#fff'] //0.1透明度的白色背景
        });
        $.ajax({
            type:'post',
            url:'/foreground/editCustomerInfo',
            data:$("#editCustomerInfoForm").serialize(),
            success:function(result){
                layer.close(index2);
                if(result.code != 200){
                    layer.msg(result.msg);
                }else{
                    layer.msg(result.msg);
                    parent.document.getElementById('hiddenIframeCloseState').text='1';
                    window.setTimeout(function(){
                        parent.layer.close(index);//关闭层
                    },2000);

                }
            },
            error:function(result){
                layer.close(index2);
                layer.msg('矮油,出错啦，请联系管理员!');
            }
        })
    });
    /**
     * 让所有的select标签与input标签的长度一致
     */
    $('.am-selected').each(function () {
        $(this).css('width', $('#realName').innerWidth())
    });
    //关闭iframe窗口
    $("#btnClose").click(function(){
        parent.layer.close(index);//关闭层
    });
});