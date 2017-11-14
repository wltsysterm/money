$(function () {
    var states = new Aray();
    states.push({id:1,text:"待审核"});
    states.push({id:2,text:"审核通过"});
    states.push({id:3,text:"审核未过"});
    bs.select("#form select[name=state]",bs.addArrFull(states,"0"));
    bs.table('#table', {
        url: "/future/money/selectMemberByPage",
        pagination: true,
        params: function () {
            return $("#form").serializeObject();
        },
        height:"100%",
        columns: [{
            field: 'trueName',
            title: '姓名',
            align: 'center',
        }, {
            field: 'sn',
            title: '学号',
            align: 'center',
        }, {
            field: 'college',
            title: '学院',
            align: 'center',
        }, {
            field: 'major',
            title: '专业',
            align: 'center',
        }, {
            title: '操作',
            width: '150px',
            field: 'state',
            formatter: function (value, row, index) {
                var html = "<button class='btn btn-warning' onclick='verify("+index+");'>审核</button>";
                html+="<button class='btn btn-danger' onclick='delete("+index+");'>删除</button>";
                return html;
            }
        }]
    });
});

function verify(index){
    var row = bs.tableRow("#table",index);
    bs.submitForm({
        id:"verify",
        isClear:true
    },function () {
        bs.tableRefresh("table");
    },function () {
        $("#verify textarea[name=note]").val("审核通过");
        $("#verify input[name=id]").val(row.id);
    });
    bs.resetDlgTitle("verify",row.trueName+"-信息审核");
    bs.resetDlgPosition("verify");
}
window.parent.onscroll = function() {
    bs.resetDlgPositionByState("verify");
}