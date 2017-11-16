var option;
var summaryChart;
var worldMapContainer;
$(function () {
    initSummaryChart();
    worldMapContainer = document.getElementById('mainChart');
    // 基于准备好的dom，初始化echarts实例
    summaryChart = echarts.init(worldMapContainer);
    summaryChart.setOption(option);
    //用于使chart自适应高度和宽度
    window.onresize = function () {
        //重置容器高宽
        summaryChart.resize();
    };
});

//按收入类别统计
function initSummaryChart(){
    var xAry = [];
    var y1Ary = [];
    bs.ajax({
        url : '/future/money/selectCurrentSituation',
        async:false,
        success:function (result) {
            for (var x = 0 ; x<result.length;x++) {
                var row = result[x];
                xAry.push(row.HOUR);
                y1Ary.push(row.CNT);
            }
        }
    });
    option = {
        title : {
            text: '今天未结算账单趋势',
            subtext: ''
        },
        tooltip : {
            trigger: 'axis'
        },
        legend: {
            data:['金额（￥）']
        },
        toolbox: {
            show : true,
            feature : {
                mark : {show: true}
            }
        },
        calculable : true,
        xAxis : [
            {
                type : 'category',
                boundaryGap : false,
                data : xAry
            }
        ],
        yAxis : [
            {
                type : 'value',
                axisLabel : {
                    formatter: '{value}'
                }
            }
        ],
        series : [
            {
                name:'金额（￥）',
                type:'line',
                data:y1Ary,
                itemStyle : {
                    normal : {
                        color:'#D2691E',
                        lineStyle:{
                            color:'#D2691E'
                        }
                    }
                },
                markPoint : {
                    data : [
                        {type : 'max', name: '最大值'},
                        {type : 'min', name: '最小值'}
                    ]
                },
                markLine : {
                    data : [
                        {type : 'average', name : '平均值'}
                    ]
                }
            }
        ]
    };
}
function delModal(index){
    var row = bs.tableRow("#table",index);
    var width=window.innerWidth-10;
    bs.submitForm({
        title:"title",
        single:true,
        id:"detdiv",
        width:width,
        height:260
    });
    bs.resetDlgTitle("detdiv",JSON.stringify(row));
}

function openModal(index){
    var row = bs.tableRow("#table",index)
    bs.alert({msg:"确定结算【"+JSON.stringify(row)+"】",cancelText:"取消"},function () {
        alert("settle over");
    })
    // bs.resetDlgPosition("form");
}
window.parent.onscroll = function() {
    bs.resetDlgPositionByState("test");
}
// window.onscroll = function() {
//     console.log("窗口发生改变了哟！");
// }
// $(window).resize(function() {
//     $('body').find('.box').css('height',window.innerHeight - 10);
// });
window.onresize = function(){

}
function resizeIframe(iframe) {
    if (iframe) {
        var iframeWin = iframe.contentWindow || iframe.contentDocument.parentWindow;
        if (iframeWin.document.body) {
            console.log(iframeWin.document.documentElement.scrollHeight || iframeWin.document.body.scrollHeight);
            iframe.height = iframeWin.document.documentElement.scrollHeight || iframeWin.document.body.scrollHeight;
        }
    }
}
    var data = [
    {college:111,sn:111,trueName:1111},
    {college:111,sn:111,trueName:1111},
    {college:111,sn:111,trueName:1111},
    {college:111,sn:111,trueName:1111},
    {college:111,sn:111,trueName:1111},
    {college:111,sn:111,trueName:1111},
    {college:111,sn:111,trueName:1111},
    {college:111,sn:111,trueName:1111},
    {college:111,sn:111,trueName:1111},
    {college:111,sn:111,trueName:1111},
    {college:111,sn:111,trueName:1111},
    {college:111,sn:111,trueName:1111},
    // {college:111,sn:111,trueName:1111},
    // {college:111,sn:111,trueName:1111},
    // {college:111,sn:111,trueName:1111},
    // {college:111,sn:111,trueName:1111},
    // {college:111,sn:111,trueName:1111},
    // {college:111,sn:111,trueName:1111},
    // {college:111,sn:111,trueName:1111},
    // {college:111,sn:111,trueName:1111},
    // {college:111,sn:111,trueName:1111},
    {college:111,sn:111,trueName:1111},
    {college:111,sn:111,trueName:1111},
    {college:111,sn:111,trueName:1111}
];
