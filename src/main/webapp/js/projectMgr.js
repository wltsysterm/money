$(function () {
    bs.select("#billproject",[]);
    $(".addbill-section").on("click",".addbill-btn",function () {
        bs.submitForm({
            id:"form",
            isClear:true,
            title:"新建项目",
            width:400
        },function (result) {
            bs.tableRefresh('#table');
            bs.toast("info","添加成功")
        });
    })
    bs.table('#table', {
        url: "/future/money/selectProjectByPage",
        pagination: true,
        height:"100%",
        columns: [{
            field: 'projectName',
            title: '项目名',
            align: 'center',
        }, {
            field: 'projectPrice',
            title: '收费标准(￥)',
            align: 'center',
        }, {
            title: '操作',
            field: 'state',
            formatter: function (value, row, index) {
                var html = "<span class='projectmgr-edit-span'><i title='修改' class='glyphicon glyphicon-pencil' onclick='update("+index+");'></i><i title='删除' class='glyphicon glyphicon-trash' onclick='delModal("+index+");'></i></span>";
                return html;
            }
        }]
    });
});


function delModal(index){
    var row = bs.tableRow("#table",index)
    bs.alert({msg:"确定删除【"+row.projectName+"】",cancelText:"取消"},function () {
        bs.ajax({
            url:"/future/money/deleteProject",
            data:{id:row.id},
            success:function (data) {
                bs.tableRefresh("#table");
                bs.toast("info","删除成功")
            }
        }
        );
    })
}

function update(index){
    var row = bs.tableRow("#table",index);
    $("#updateform input[name=projectName]").val(row.projectName);
    $("#updateform input[name=projectPrice]").val(row.projectPrice);
    $("#updateform input[name=id]").val(row.id);
    bs.submitForm({
        title:"修改项目",
        id:"updateform",
        // isClear:true,
        width:400
    },function (result) {
        bs.tableRefresh('#table');
        bs.toast("info","修改成功")
    });
}
window.parent.onscroll = function() {
    bs.resetDlgPositionByState("updateform");
    bs.resetDlgPositionByState("form");
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
