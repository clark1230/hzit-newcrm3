/**
 * Created by xianyaoji on 2016/11/28.
 */
$(function(){
    //日历组件
     $("#getYearDay").datetimepicker({
         language: 'zh-CN',
         autoclose: true,
         startDate:new Date(2016,0),
         startView: 'year',
         minView: "year", //选择日期后，不会再跳转去选择时分秒
         todayHighlight: true,
         format: 'yyyy-mm',
     }).on('changeMonth',function(env){
        //日期格式转换
         var date = env.date;
         var formatDate = function (date) {
             var y = date.getFullYear();
             var m = date.getMonth() + 1;
             m = m < 10 ? '0' + m : m;
             return y+''+m;
         };
         date = formatDate(date);
         getYearMonth(date);
     });
        //年统计
        function getCurrentData(){
            $.get('/foreground/currentYearCustomerInfo',function(result) {
                if(typeof(result)== 'object'){
                    showCustomerInfoByYear(result);
                }else{
                    $('#yearTotalCustomerInfo').remove();//删除年统计信息
                }

            });
        }
        function showCustomerInfoByYear(result){
              var customerChart =  echarts.init(document.getElementById('showCustomerInfoByYear'));
              customerChart.showLoading();
              var data = [];
              var interview =[];
              var invalidData = [];

              for(var item in result){
                  data.push(result[item].total);
                  invalidData.push(result[item].invalid);
                  interview.push(result[item].viewed);
              }
              customerChart.hideLoading();
                var option = {
                    backgroundColor: "#2f4554",
                    "title": {
                        "text": "接待人数年统计",
                        x: "4%",
                        textStyle: {
                            color: '#fff',
                            fontSize: '22'
                        },
                        subtextStyle: {
                            color: '#919191',
                            fontSize: '16',

                        },
                    },
                    "tooltip": {
                        "trigger": "axis",
                        "axisPointer": {
                            "type": "shadow",
                            textStyle: {
                                color: "#fff"
                            }

                        },
                    },
                    "grid": {
                        "borderWidth": 0,
                        "top": 110,
                        "bottom": 95,
                        textStyle: {
                            color: "#fff"
                        }
                    },
                    "legend": {
                        x: '4%',
                        top: '11%',
                        textStyle: {
                            color: '#787878',
                        },
                        "data": ['已面试', '无效', '总数']
                    },
                     toolbox: {
                        show : true,
                        feature : {
                            dataView : {show: true, readOnly: false},
                            magicType : {show: true, type: ['line', 'bar']},
                            dataZoom:{
                                show:true,
                                title: {
                                    zoom: '区域缩放',
                                    back: '区域缩放还原',
                                },
                            },
                            restore : {show: true},
                            saveAsImage : {show: true}
                        }
                    },

                    "calculable": true,
                    "xAxis": [{
                        "type": "category",
                        "axisLine": {
                            lineStyle: {
                                color: '#afafaf'
                            }
                        },
                        "splitLine": {
                            "show": false
                        },
                        "axisTick": {
                            "show": false
                        },
                        "splitArea": {
                            "show": false
                        },
                        "axisLabel": {
                            "interval": 0,

                        },
                        "data": ['一月份','二月份','三月份','四月份','五月份','六月份','七月份','八月份','九月份','十月份','十一月份','十二月份'],
                    }],
                    "yAxis": [{
                        "type": "value",
                        "splitLine": {
                            "show": false
                        },
                        "axisLine": {
                            lineStyle: {
                                color: '#959595'
                            }
                        },
                        "axisTick": {
                            "show": false
                        },
                        "axisLabel": {
                            "interval": 0,

                        },
                        "splitArea": {
                            "show": false
                        },

                    }],
                    "dataZoom": [{
                        "show": true,
                        "height": 30,
                        "xAxisIndex": [
                            0
                        ],
                        bottom: 30,
                        "start": 0,
                        "end": 100,
                        //handleIcon: 'path://M306.1,413c0,2.2-1.8,4-4,4h-59.8c-2.2,0-4-1.8-4-4V200.8c0-2.2,1.8-4,4-4h59.8c2.2,0,4,1.8,4,4V413z',
                        handleSize: '110%',
                        handleStyle:{
                            color:"#af296e",

                        },
                        textStyle:{
                            color:"#fff"},
                        borderColor:"#25ceea"
                    }, {
                        "type": "inside",
                        "show": true,
                        "height": 15,
                        "start": 1,
                        "end": 35
                    }],
                    "series": [{
                        "name": "已面试",
                        "type": "bar",
                        "stack": "总量",
                        "barMaxWidth": 35,
                        "barGap": "10%",
                        "itemStyle": {
                            "normal": {
                                "color": "rgba(255,144,128,1)",
                                "label": {
                                    "show": true,
                                    "textStyle": {
                                        "color": "#fff"
                                    },
                                    "position": "insideTop",
                                    formatter: function(p) {
                                        return p.value > 0 ? (p.value) : '';
                                    }
                                }
                            }
                        },
                        "data":interview,
                    },

                        {
                            "name": "无效",
                            "type": "bar",
                            "stack": "总量",
                            "itemStyle": {
                                "normal": {
                                    "color": "rgba(0,191,183,1)",
                                    "barBorderRadius": 0,
                                    "label": {
                                        "show": true,
                                        "position": "top",
                                        formatter: function(p) {
                                            return p.value > 0 ? (p.value) : '';
                                        }
                                    }
                                }
                            },
                            "data":invalidData ,
                        }, {
                            "name": "总数",
                            "type": "line",
                            "stack": "总量",
                            symbolSize:10,
                            symbol:'circle',
                            "itemStyle": {
                                "normal": {
                                    "color": "rgba(252,230,48,1)",
                                    "barBorderRadius": 0,
                                    "label": {
                                        "show": true,
                                        "position": "top",
                                        formatter: function(p) {
                                            return p.value > 0 ? (p.value) : '';
                                        }
                                    }
                                }
                            },
                            "data": data
                        },
                    ]
                }
                customerChart.setOption(option);


            }
        //月统计
        function getYearMonth(date){
            if(date==null){
                var date1 = new Date();
                var y = date1.getFullYear();
                var m = date1.getMonth() + 1;
                m = m < 10 ? '0' + m : m;
                date = y+''+m;
            }
            $.get('/foreground/currentMonthCustomerInfo?yearMonth='+date,function(result){
               if(typeof(result)=='object'){
                   showCustomerInfoByMonth(result);
               }else{
                   $('#monthTotalCustomerInfo').remove();//删除月统计显示信息
               }
            });
        }
        function showCustomerInfoByMonth(result){
            var dayArr = [];
            var totalArr = [];
            var invalidArr =[];
            for(var item in result){
                totalArr.push(result[item].total);
                dayArr.push(result[item].day+'号');
                invalidArr.push(result[item].invalid);
            }
            if(dayArr.length <31){
            }
            var customerInfoMonthChart = echarts.init(document.getElementById('showCustomerInfoByMonth'));
            var option ={

                tooltip:{

                },
                toolbox: {
                    show : true,
                    feature : {
                        dataView : {show: true, readOnly: false},
                        magicType : {show: true, type: ['line', 'bar']},
                        dataZoom:{
                            show:true,
                            title: {
                                zoom: '区域缩放',
                                back: '区域缩放还原',
                            },
                        },
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                legend:{
                    data:['接待人数','无效人数']
                },
                xAxis:{
                    data:dayArr
                },
                yAxis:{},
                series:[{
                    name:'接待人数',
                    type:'line',
                    smooth:true,
                    symbolSize: 8,
                    data:totalArr,
                    markPoint: {
                        data: [
                            {type: 'max', name: '月最大接待人数'},
                            {type: 'min', name: '月最小接待人数'}
                        ]
                    },
                    markLine: {
                        data: [
                            {type: 'average', name: '月平均接待人数'}
                        ]
                    }
                },{
                    name: '无效人数',
                    type: 'line',
                    smooth:true,
                    symbolSize: 8,
                    data:invalidArr
                }]
          }
            customerInfoMonthChart.setOption(option);
        }
         //获取当年的每一月的天数
        function getDate(){
        var d = new Date()
        var y = d.getFullYear()
        var m = d.getMonth() + 1
        var t1 = Date.parse(m + "/28/" + y)
        var t2 = Date.parse(m + 1 + "/1/" + y)
        var thisMonthDays = 27 + (t2 - t1) / (60 * 60 * 24 * 1000)
        return thisMonthDays;
    }
        //日统计
        function customerInfoECharts(){
            //echarts 图表
            $.get('/foreground/customerInfoEChartss',function(result){
               if(typeof(result) =='object'){
                    getCustomerInfo(result);
                }else{
                    $('#dayTotalCustomerInfo').remove();//删除日统计信息
               }
            });
            // 指定图表的配置项和数据
            function getCustomerInfo(result){
                // 基于准备好的dom，初始化echarts实例
                var customerInfoDayChart = echarts.init(document.getElementById('showCustomerInfoDay'));
                // 基于准备好的dom，初始化echarts实例
                // 指定图表的配置项和数据
                var option = {
                    title : {
                        text: '当日接待人数',
                        subtext: '仅供参考',
                        x:'center'
                    },
                    tooltip : {
                        trigger: 'item',
                        formatter: "{a} <br/>{b} : {c}人 ({d}%)"
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left',
                        data: ['已面试','待面试','已无效']
                    },
                    series : [
                        {
                            name: '人数',
                            type: 'pie',
                            radius : '55%',
                            center: ['50%', '60%'],
                            data:result,
                            itemStyle: {
                                emphasis: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0.5, 0.5)'
                                }
                            }
                        }
                    ]
                };
                customerInfoDayChart.setOption(option);
             }
        }
        window.setTimeout(function(){
            getCurrentData();//年统计
            getYearMonth(null);
            customerInfoECharts();//日统计
    },100);
});