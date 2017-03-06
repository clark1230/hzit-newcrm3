/**
 * Created by xianyaoji on 2017/3/2.
 */
$(function () {
    var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
    //获取父窗口中的相应对象!!
    var $pSearchValue = $("#searchValue",parent.document);
    var $pSearchParam = $("#searchParam",parent.document);
    //给父窗口中的按钮绑定事件
    var value = $("#searchCustomerInfo",parent.document);
    $("#search").click(function () {
        //本窗口中相应的对象!!
        var $searchValue = $("#searchValue");

        if($searchValue.val() ==''){
            return false;
        } else{
            //获取值:
            $pSearchValue.val($searchValue.val());
            disabled();
        }
        value.click();//自动调用父窗口中的事件
    });
    function disabled(){
        var $searchParam = $("#searchParam");
        //清空父窗口中的下拉列表
        //获取本窗口选中的搜索条件
        var condition = $searchParam.children("option:selected");
        var result = $pSearchParam.children("option[value='"+condition.val()+"']");
        $("#searchValue",parent.document).css('display','none');
        console.log($pSearchValue);
        result.attr("selected","selected");
        $pSearchParam.addClass('search-param-disabled');
    }
    //监听学员状态下拉列表框的change事件
    $("#customerState").change(function(){
        if($(this).val() != '-1'){
            disabled();

            //给父窗口的搜索文本框设置值
            $pSearchValue.val($(this).val());
            var $option = $("<option value='customer_state' selected='selected'>学员状态</option>");
            $pSearchParam.append($option);
            value.click();//自动调用父窗口中的事件
        }else{
            layer.msg('请选择搜索值!');
        }


    });
    //监听学员级别下拉列表框的change事件
    $("#customerLevel").change(function(){
        if($(this).val() != '-1'){
            disabled();
            $("#searchValue",parent.document).addClass('search-value-display');
            //给父窗口的搜索文本框设置值
            $pSearchValue.val($(this).val());
            var $option = $("<option value='customer_level' selected='selected'>学员级别</option>");
            $pSearchParam.append($option);
            value.click();//自动调用父窗口中的事件
        }else{
            layer.msg('请选择搜索值!');
        }

    });
//
    //监听目标技能下拉列表框的change事件
    $("#targetSkill").change(function(){
        if($(this).val() != '-1'){
            disabled();
            //给父窗口的搜索文本框设置值
            $pSearchValue.val($(this).val());
            var $option = $("<option value='target_skill' selected='selected'>目标技能</option>");
            $pSearchParam.append($option);
            value.click();//自动调用父窗口中的事件
        }else{
            layer.msg('请选择搜索值!');
        }

    });
    //introducer
    //监听目标技能下拉列表框的change事件
    $("#introducer").change(function(){
        if($(this).val() != '-1'){
            disabled();
            //给父窗口的搜索文本框设置值
            $pSearchValue.val($(this).val());
            var $option = $("<option value='introducer' selected='selected'>邀约人</option>");
            $pSearchParam.append($option);
            value.click();//自动调用父窗口中的事件
        }else{
            layer.msg('请选择搜索值!');
        }

    });
    //recruit_channel
    $("#recruit_channel").change(function(){
        if($(this).val() != '-1'){
            disabled();
            //给父窗口的搜索文本框设置值
            $pSearchValue.val($(this).val());
            var $option = $("<option value='recruit_channel' selected='selected'>应聘渠道</option>");
            $pSearchParam.append($option);
            value.click();//自动调用父窗口中的事件
        }else{
            layer.msg('请选择搜索值!');
        }

    });

    //关单人
    $("#guandan").change(function(){
        if($(this).val() != '-1'){
            disabled();
            //给父窗口的搜索文本框设置值
            $pSearchValue.val($(this).val());
            var $option = $("<option value='guandan' selected='selected'>关单人</option>");
            $pSearchParam.append($option);
            value.click();//自动调用父窗口中的事件
        }else{
            layer.msg('请选择搜索值!');
        }

    });
    //咨询师  user_id
    $("#user_id").change(function(){
        if($(this).val() != '-1'){
            disabled();
            //给父窗口的搜索文本框设置值
            $pSearchValue.val($(this).val());
            var $option = $("<option value='user_id' selected='selected'>咨询师</option>");
            $pSearchParam.append($option);
            value.click();//自动调用父窗口中的事件
        }else{
            layer.msg('请选择搜索值!');
        }

    });
    $("#close").click(function () {
        parent.layer.close(index);
    });
});