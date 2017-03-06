/**
 * Created by xianyaoji on 2017/3/6.
 */
$(function(){
    //页面加载时获取该公司的统计数据
    var companyId = $("input[name='companyId']").val();
    var date = new Date();
    var month = date.getMonth()+1;
    if(month <10){
        month = "0"+month;
    }
    var currentMonth = date.getFullYear()+"-"+month;
    $("select[name='company']").change(function(){
       var value = $(this).children("option:selected").val();
       if(value== "-1"){
           layer.msg('请选择分校!');
           return false;
       }
       initData(value,currentMonth);
    });
    initData(companyId,currentMonth);
    function initData(companyId,currentMonth){
        //到服务器中获取数据'
        $.get('/statistics/companyCount?companyId='+companyId+"&month="+currentMonth,function(result){
            if(result.length==0){
                layer.alert('该分校暂无数据!');
                $("select[name='company']").children('option[value="-1"]').attr("selected","selected");
                return false;
            }
            var tartgetSkillData = [];
            for(var i=0;i<result.length;i++){
                tartgetSkillData.push(result[i].name);
            }
            showCompanyCount(result,tartgetSkillData);
        });
    }

    $("#search").click(function(){
        //获取分公司数据
        var companyValue= $("select[name='company']").children("option:selected").val();
        if(companyValue =='-1'){
            companyValue = companyId;
        }
        $("select[name='company']").children('option[value="'+companyId+'"]').attr("selected","selected");

        //获取month
        var monthValue = $("#month").val();
        //调用函数
        initData(companyValue,monthValue);
    });
    $("div[class='WdateDiv']").click(function(){
       alert('哈哈');
    });
    function datePick(){
        //获取分公司数据
        var companyValue= $("select[name='company']").children("option:selected").val();
        if(companyValue =='-1'){
            companyValue = companyId;
        }
        $("select[name='company']").children('option[value^="宝安"]').attr("selected","selected");
        //获取month
        var monthValue = $("#month").val();
        //调用函数
        initData(companyValue,monthValue);
    }
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('showCompanyCount'));
    function showCompanyCount(result,tartgetSkillData){
        var  option = {
            backgroundColor: '#F9F8F8',
            title: {
                text: '合众艾特咨询系统统计',
                textStyle:
                    {fontSize:22},
                subtext: '单位：人',
                x:'20%',
            },
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            color:['#8fc31f','#f35833','#00ccff','#ffcc00','#045FB4','#FF8000'],
            legend: {
                orient: 'vertical',
                x: 'right',
                y:'10%',
                data: tartgetSkillData,
                formatter:function(name){
                    var oa = option.series[0].data;
                    var num = oa[0].value + oa[1].value + oa[2].value + oa[3].value;
                    for(var i = 0; i < option.series[0].data.length; i++){
                        if(name==oa[i].name){
                            return name  ;
                        }
                    }
                }
            },
            series : [
                {
                    name: '目标技能',
                    type: 'pie',
                    radius : '55%',
                    center: ['48%', '60%'],
                    data:result,
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    },
                    itemStyle: {
                        normal: {
                            label:{
                                textStyle:{fontSize:16},
                                show: true,
                                //  position:'inside',
                                formatter: '{b} : {c} ({d}%)'
                            }
                        },
                        labelLine :{show:true}
                    }
                }
            ]
        };
        myChart.setOption(option);
    }

});