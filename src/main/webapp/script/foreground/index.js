/**
 * Created by 冼耀基 on 2016/11/10.
 */
$(function(){
    //权限验证 -----start---------------------
    //页面加载时根据用户编号获取用户所能访问的按钮,移除隐藏样式
    var isEditCustomerInfo =false;
    $.get('/showButton/show?userId='+$("input[name='userId']").val(),function(result){
        var $span ="";
        $.each(result,function(item){
            var buttonResource =   result[item];
            if(buttonResource.indexOf("foreground")!=-1){
                //显示隐藏的按钮
                //截取字符串
                var button  = buttonResource.substr(buttonResource.indexOf(":")+1,buttonResource.length);
                $("#"+button+"Div").show('fast');
                $("#showCustomerInfo").removeClass('am-u-sm-12 am-u-md-12 am-u-lg-12');
                $("#showCustomerInfo").addClass('am-u-sm-12 am-u-md-12 am-u-lg-7');
                if(button=='editCustomerInfo'){
                   isEditCustomerInfo = true;
                }
            }
        });
    });
    //权限验证--------------end---------------------------

    showWaitTime();
    getConsultant();
    var companyId = $("input[name='companyId']").val();
    function getConsultant(){
        //获取咨询师信息
        $.get('/foreground/consultantData?companyId='+$("input[name='companyId']").val(),function(data){
            if(typeof(data) =='object'){//有数据返回
                var $userId = $("#userId");
                for(var item in data){
                    var $option = $('<option value="'+data[item].userId+'">'+data[item].name+'</option>');
                    $userId.append($option);
                }
            }else{
                $('#showCustomerInfo').removeClass('am-u-sm-12 am-u-md-12 am-u-lg-6');
                $('#showCustomerInfo').addClass('am-u-sm-12 am-u-md-12 am-u-lg-12');
                $('#addCustomerInfoDiv').remove();
            }
        });
    }
     //setInterval(showWaitTime,600);//每隔一段时间重新获取等待者列表!
    //显示应聘者等待列表
    function showWaitTime(){
       // layer.msg('从服务器获取最新等待者数据!');
        $.get('/foreground/customerInfoWaitList?CompanyId='+$("input[name='companyId']").val(),function(data){
            //获取客户等待列表
            var $customerWaitList = $("#customerWaitList");
            $customerWaitList.html('应聘者等待列表&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+'当前人数<span class="am-badge am-badge-warning am-radius">'+data.length+'</span>人');
            var currentDate;
            var hour;
            var minute;
            var second;
            var leftTime="";
            var $showCustomerInfoNum = $("#showCustomerInfoNum");
            $showCustomerInfoNum.html('');
            var $liBasic = $('<li style="height:50px;"><a style="color:purple;" href="#"<span>应聘者姓名</span>' +
                '<span style="float: right;margin-right;">状态</span>'+
                '<span style="float:right;margin-right:140px;color:orange">等待时间</span>' +
                '<span style="float: right;margin-right:60px;">应聘者电话</span>' +
                '<span style="float:right;display:block;width:50px;text-align:left;margin-right:110px;">咨询师</span></a></li>');
            $customerWaitList.append($liBasic);
            for(var item in data){
                //获取展示ul标签
                //清空数据
                var waitState = '';
                if(data[item].customerStateMsg=='待面试'){
                    waitState='red';
                }else
                {
                    waitState='#FF66CC';
                }
                var $li = $('<li id="liCustomerInfo'+item+'"><a href="#" id="aCustomerInfo'+item+'">'+data[item].realName+'' +
                    '<span style="color:'+waitState+';float: right;margin-right;">'+data[item].customerStateMsg+'</span>'+
                    '<span style="float:right;margin-right:60px;color:orange"  id="timeSpan'+item+'">等待:'+ myTime()+'</span>'+
                    '<span style="float: right;margin-right:50px;">'+data[item].tel+'</span>'+
                    '<span  style="float:right;display:block;width:50px;text-align:left;margin-right:100px;">'+data[item].userIdMsg+'</span></a></li>');
                var $hiddenDate = $('<input type="hidden" id="hiddenDate'+item+'" value="'+data[item].createTime+'">');
                var $hiddenUserId = $('<input type="hidden" id="hiddenUserId'+item+'" value="'+data[item].userId+'">');
                var $hiddenCustomerId = $('<input type="hidden" id="hiddenCustomerId'+item+'" value="'+data[item].customerId+'">');
                //$li.append($span);
                $li.append($hiddenDate);
                $li.append($hiddenUserId);
                $li.append($hiddenCustomerId);

                $customerWaitList.append($li);
            }
            //显示时间
            function myTime(){
                for(var item in data){
                    var newDate = new Date();
                    newDate.setTime($("#hiddenDate"+item).val());
                    currentDate = newDate;
                    hour = Math.floor((new Date().getTime() - currentDate.getTime()) / 1000 / 60 / 60);
                    minute = Math.floor((new Date().getTime() - currentDate.getTime()) / 1000 / 60 - (hour * 60));
                    second = Math.floor((new Date().getTime() - currentDate.getTime()) / 1000 - (hour * 60 * 60 + minute * 60));
                    if(hour < 10){
                        hour = "0"+hour;
                    }
                    if(minute < 10){
                        minute = '0'+minute;
                    }
                    if(second < 10){
                        second = '0'+second;
                    }
                    leftTime = hour + "小时" + minute + "分钟" + second + "秒";
                    $("#timeSpan"+item).html(leftTime);
                }
            }
            window.setInterval(myTime,1000);//每秒执行一次
            //修改客户等待状态
            for(var item in data){
                $("#aCustomerInfo"+item).click(function(){
                    //layer.alert("点击我");
                    if(!isEditCustomerInfo){
                       layer.msg('对不起，你没有权限修改应聘者信息!');
                       return false;
                    }
                    //判断
                    if($("#changeCompanySelect").children("option:selected").val()!=$("input[name='companyId2']").val()){
                        layer.msg('对不起您没有去权限修改其他校区的数据!');
                        return false;
                    }
                    var aIdValue = $(this).attr('id');
                    aIdValue = aIdValue.substr(aIdValue.length-3,aIdValue.length-1);

                    if(isNaN(aIdValue)){
                        aIdValue = aIdValue.substr(aIdValue.length-2,aIdValue.length-1);
                        if(isNaN(aIdValue)){
                            aIdValue = aIdValue.substr(aIdValue.length-1,aIdValue.length-1);
                        }
                    }
                    $hiddenCustomerId = $("#hiddenCustomerId"+aIdValue).val();
                    $("#hiddenIframeCustomerId").val($hiddenCustomerId);
                    layer.open({
                        type: 2,
                        title: '修改录入信息',
                        shadeClose: true,
                        shade: 0.1,
                        maxmin: false,
                        area: ['30%', '50%'],
                        content: '/foreground/editCustomerInfo?customerId='+$hiddenCustomerId,//iframe的url
                        end:function() {
                            //获取id为hiddenIframeCloseState的值
                            //var $hiddenIframeCloseState = $("#hiddenIframeCloseState").val();

                            showWaitTime();//重新获取数据
                        }
                    });
                });
            }
        });
    }
    //window.setTimeout(showWaitTime,1000);
    window.setInterval(function(){
        layer.msg('页面即将刷新数据!');
        showWaitTime;
    },5*60*1000);//每3分钟刷新一次*/
    $('#realName').blur(function(){
       if($(this).val() ==''){
           layer.msg('请输入姓名!');
           $('#realName').css('border-color','red');
       }else{
           $('#realName').css('border-color','lightgrey');
       }
    });
    $('#tel').blur(function(){
      checkTel();
      if($('#tel').val() !=''){
          $('#tel').css('border-color','lightgrey');
          if($("#realName").val()!=''){
              //到服务器中检测该用户是否存在!!
              $.get('/customerInfo/checkCustomerInfo?realName='+$("#realName").val()+"&tel="+$(this).val(),function(result){
                  if(result.code==200){
                      layer.msg(result.msg);
                      $('#realName').css('border-color','lightgrey');
                      $('#btnAddCustomer').css('pointer-events','visible').css('background-color','#0099FF');
                  }else{
                      $('#realName').css('border-color','red');
                      $('#btnAddCustomer').css('pointer-events','none').css('background-color','lightgrey');
                      layer.msg(result.msg);
                  }
              });
          }
      }
    });
    function checkTel(){
        if($('#tel').val()==''){
            layer.msg('请输入电话!');
            $('#tel').css('border-color','red');
            return false;
        }
    }
    //异步添加客户信息
    $("#btnAddCustomer").click(function(){
        //表单验证
        var $realName = $('#realName');//姓名
        var $tel = $('#tel');//电话号码
        var isMob =/^0?1[2345678]\d{9}$/;
        if($realName.val() == ''){
            layer.msg('请输入姓名!');
            $realName.css('border-color','red');
            return false;
        }
        if($tel.val()==''){
           layer.msg('请输入电话!');
            $tel.css('border-color','red');
            return false;
        }else {
            //电话号码校验
            var result = isMob.test($tel.val());
            if(!result){
                layer.msg('电话号码格式不正确!');
                $tel.css('border-color','red');
                return false;
            }
        }
        //选择所属咨询师
        var $userId = $('#userId option:selected');
        if($userId.val() == -1){
            $('#userId').css({'border':'solid 1px red'});
            layer.msg('请选择咨询师!');
            return false;
        }
        var index2 = layer.load(1,{time: 20*1000},{shade: [0.1, '#fff']}); //0.1透明度的白色背景
        //表单验证
        //异步提交表单
        $.ajax({
            type:'post',
            url:'/foreground/addCustomerInfo',
            data:$("#addCustomerForm").serialize(),
            success:function(result){
                layer.close(index2); //关闭当前弹层
                if(typeof(result)=='object'){
                    $('#userId').css({'border':'solid 1px lightgrey'});
                    if(result.code != 200){
                        layer.msg(result.msg);
                    }else{
                        //清空表单内容
                        $("#realName").val('');
                        $("#tel").val('');
                        $('#userId option[value="-1"]').attr("selected", true);
                        layer.msg(result.msg);
                        showWaitTime();//重新获取数据
                    }
                }else{
                    layer.msg('对不起，您没有权限保存学员信息!');
                }
            },
            error:function(){
                layer.close(index2); //关闭当前弹层
                layer.msg('对不起，您没有权限保存学员信息!');
            }
        });
    });

    /**
     * 让所有的select标签与input标签的长度一致
     */
    $('.am-selected').each(function () {
        $(this).css('width', $('#realName').innerWidth())
    });

    $("#btnRefresh").click(function(){
       showWaitTime();//重新获取数据
    });
    //当选择其他校区时，查看信息
    $("#changeCompanySelect").change(function(){
        //设置隐藏域
        $("input[name='companyId']").val($(this).children("option:selected").val());
        showWaitTime();
    });

});
