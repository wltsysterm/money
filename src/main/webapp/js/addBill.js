$(function () {
    var infoList = new Array();
    infoList.push({id:"A4",text:"A4纸张"})
    bs.select("#form select[name=projectName]",bs.addArrFull(infoList,""));
    $(".addbill-section").on("click",".addbill-btn",function () {
        bs.alert({
            msg:"谨慎新建账单，一经新建不能撤销",
        },  function(){
            bs.submitForm({
                id:"form",
                isClear:true,
                title:"新建账单",
                width:400
            },function () {
                bs.tableRefresh("#table");
            });
            }
            );
    })
    bs.table('#table', {
        url: "/future/money/selectBillByMember",
        pagination: true,
        height:"100%",
        columns: [{
            field: 'projectName',
            title: '项目名称',
            align: 'center',
            width: '150px',
        }, {
            field: 'projectPrice',
            title: '收费标准(￥)',
            align: 'center',
            width: '150px',
        }, {
            field: 'projectCount',
            title: '数量',
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
