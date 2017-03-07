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
        $.get('/statistics/baoming?companyId='+companyId+"&month="+currentMonth,function(result){
             if(result.length ==0){
                 layer.msg('报名人数');
                 $("select[name='company']").children('option[value="-1"]').attr("selected","selected");
                 return false;
             }
             var baoMingData = [];
             var baoMingCount =[];
             for(var i =0;i<result.length;i++){
                 baoMingData.push(result[i].name);
                 baoMingCount.push(result[i].value);
             }
             showBaoMing(baoMingCount,baoMingData);
        });
    }

    /**
     * 中间操作(点击日期时，其实就是调用该按钮的点击事件)
     */
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

    /**
     * 日期选择
     */
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
                text: '学员意向',
                textStyle:
                    {fontSize:22},
                subtext: '单位：人',
                x:'20%',
            },
            toolbox: {
                show: true,
                feature: {
                    dataView: {readOnly: false},
                    restore: {},
                    saveAsImage: {}
                }
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

    /**
     * 每月每个校区每个咨询师的报名量
     * @param result
     * @param baoMingData
     */
    var myChart2 = echarts.init(document.getElementById('showBaoMing'));
    function showBaoMing(baoMingCount,baoMingData){
        var option = {
            title:{
                text:'咨询师月报名量',
                textStyle:
                    {fontSize:22},
                subtext: '单位：人',
                x:'20%',
            },
            color: ['#3398DB'],
            tooltip : {
                trigger: 'axis',
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            toolbox: {
                show: true,
                feature: {
                    dataView: {readOnly: false},
                    restore: {},
                    saveAsImage: {}
                }
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis : [
                {
                    type : 'category',
                    data : baoMingData,
                    axisTick: {
                        alignWithLabel: true
                    }
                }
            ],
            yAxis : [
                {
                    type : 'value'
                }
            ],
            series : [
                {
                    name:'报名人数',
                    type:'bar',
                    barWidth: '60%',
                    data:baoMingCount
                }
            ]
        };
        myChart2.setOption(option);
    }
});