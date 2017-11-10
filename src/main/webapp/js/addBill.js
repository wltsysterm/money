$(function () {
    bs.select("#billproject",[]);
    $(".addbill-section").on("click",".addbill-btn",function () {
        bs.alert({
            msg:"谨慎新建账单，一经新建不能撤销",
        },  function(){
            bs.submitForm({
                id:"form",
                isClear:true,
                title:"新建账单",
                width:400
            });
            }
            );
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
        }]
    });
});

function openModal(index){
    var row = bs.tableRow("#table",index)
    $("#test .test").html(JSON.stringify(row));
    bs.submitForm({
        title:"test",
        id:"test",
        isClear:true
    });
    bs.resetDlgPosition("test");
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
    // {college:111,sn:111,trueName:1111},
    // {college:111,sn:111,trueName:1111},
    // {college:111,sn:111,trueName:1111},
    // {college:111,sn:111,trueName:1111},
    // {college:111,sn:111,trueName:1111},
    // {college:111,sn:111,trueName:1111},
    // {college:111,sn:111,trueName:1111},
    // {college:111,sn:111,trueName:1111},
    // {college:111,sn:111,trueName:1111},
    // {college:111,sn:111,trueName:1111},
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
