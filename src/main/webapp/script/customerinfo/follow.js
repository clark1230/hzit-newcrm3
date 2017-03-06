/**
 * Created by xianyaoji on 2017/2/18.
 */
/*
 *  学员跟进
 * */

$(function () {
    var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
    $('#cancel').click(function () {
        parent.layer.close(index);//关闭层
    });
    //获取学员编号和咨询师编号
    var userId = $("input[name='userId']").val();
    var customerId = $("input[name='customerId']").val();
    //加载层
    var index2 = layer.load(0, {shade: false}); //0代表加载的风格，支持0-2
    //loading层
    var index2 = layer.load(1, {
        shade: [0.1,'#fff'] //0.1透明度的白色背景
    });
    showContent();
    function  showContent() {
        var $showContent = $('#showContent');
        $.get('/customerTraceRecord/traceRecord?userId='+userId+"&customerId="+customerId,function(result){ //页面加载时异步获取数据!!
            layer.close(index2);//关闭加载层
            if(typeof(result)=='object'){
                $showContent.html('');
                for(var item in result){
                    var recordDate = new Date(parseInt(result[item].recordDate)).toLocaleString().replace(/年|月/g, "-").replace(/日/g, " ");
                    var p = $('<li class="content-li">跟进日期:'+recordDate+'&nbsp;&nbsp;&nbsp;&nbsp;跟进内容:'+result[item].content+'</li>' +
                        '<hr style="padding: 0px;margin:0px;"/>');
                    $showContent.append(p);
                }
            } else{
                layer.msg('获取数据错误!!');
            }
        }) ;
    }
    /*---------------------保存跟进信息--------------------------*/
    $("#follow").click(function () {
        //获取跟进记录
        var content = $("textarea[name='content']").val();
        console.log(content);
        if (content.trim('') == '') { //判断是否有值
            $("textarea[name='content']").css('border-color', 'red');
            layer.msg('请输入跟进信息!');
            return false;
        }else{
            console.log(userId);
            console.log(customerId);
            //提交跟进记录
            var data = "content="+content+"&userId="+userId+"&customerId="+customerId;
            $.post('/customerInfo/follow',data,function(result){
                 showContent();
                 //清空文本框内容
                $("textarea[name='content']").val('');
                  layer.msg('跟进记录添加成功!');
            });
        }
    });
});