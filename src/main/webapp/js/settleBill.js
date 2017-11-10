var summary;
var option;
var summaryChart;
var worldMapContainer;
$(function () {
    worldMapContainer = document.getElementById('mainChart');
    // 基于准备好的dom，初始化echarts实例
    summaryChart = echarts.init(worldMapContainer);
    summaryChart.setOption(option);
    //用于使chart自适应高度和宽度
    window.onresize = function () {
        //重置容器高宽
        summaryChart.resize();
    };

    bs.select("#billproject",[]);
    $(".addbill-section").on("click",".addbill-btn",function () {
        bs.alert({msg:"确定结算所有人员未结账单？",cancelText:"取消"},function () {
            alert("已经全部结算");
        });
    })
    bs.table('#table', {
        // url: "/api/merchantTrade/queryTrade",
        // toolbar: '#toolbar',
        pagination: true,
        // params: function () {
        //     return $("#queryTradeForm").serializeObject();
        // },
        // pageSize: 5,
        // pageList: [5, 10],
        height:"100%",
        data:data,
        rows:50,
        columns: [{
            field: 'college',
            title: '交易状态',
            align: 'center',
            width: '150px',
        }, {
            field: 'sn',
            title: '交易状态',
            align: 'center',
            width: '150px',
        }, {
            field: 'trueName',
            title: '交易状态',
            align: 'center',
            width: '150px',
        }, {
            title: '操作',
            width: '150px',
            field: 'state',
            formatter: function (value, row, index) {
                var html = "<span class='projectmgr-edit-span'><i title='结算' class='glyphicon glyphicon-import' onclick='openModal("+index+");'></i><i title='查看明细' class='glyphicon glyphicon-list' onclick='delModal("+index+");'></i></span>";
                return html;
            }
        }]
    });
    bs.table('#tabledet', {
        // url: "/api/merchantTrade/queryTrade",
        // toolbar: '#toolbar',
        pagination: true,
        // params: function () {
        //     return $("#queryTradeForm").serializeObject();
        // },
        // pageSize: 5,
        // pageList: [5, 10],
        height:"100%",
        data:data,
        total:50,
        columns: [{
            field: 'college',
            title: '交易状态',
            align: 'center',
            width: '150px',
        }, {
            field: 'sn',
            title: '交易状态',
            align: 'center',
            width: '150px',
        }, {
            field: 'trueName',
            title: '交易状态',
            align: 'center',
            width: '150px',
        }]
    });
});


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
