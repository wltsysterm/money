$(function () {
    bs.authCheck();
    var states = new Array();
    states.push({id:1,text:"审核通过"});
    states.push({id:2,text:"待审核"});
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
            align: 'center'
        }, {
            field: 'sn',
            title: '学号',
            align: 'center'
        }, {
            field: 'college',
            title: '学院',
            align: 'center'
        }, {
            field: 'major',
            title: '专业',
            align: 'center'
        }, {
            field: 'note',
            title: '意见',
            align: 'left'
        }, {
            field: 'state',
            title: '状态',
            align: 'center',
            formatter:function (value,row,index) {
                if(value==1){
                    return "审核通过";
                }else if(value==2){
                    return "待审核";
                }else if(value==3){
                    return "审核未过";
                }else {
                    return "未知";
                }
            }
        }, {
            title: '操作',
            width: '150px',
            field: 'state',
            formatter: function (value, row, index) {
                var html="";
                if(row.state==2){
                    html+= "<button class='btn btn-warning' onclick='verify("+index+");'>审核</button>";
                }
                html+="<button class='btn btn-danger' onclick='delModal("+index+");'>删除</button>";
                return html;
            }
        }]
    });
    $("#form .selectMember").on("click",function () {
        bs.tableRefresh("#table");
    });
});

function verify(index){
    var row = bs.tableRow("#table",index);
    $("#verify")[0].reset();
    bs.submitForm({
        id:"verify",
    },function (result) {
        bs.errorHandle(result);
    },function () {
        $("#verify input[name=id]").val(row.id);
    });
    bs.resetDlgTitle("verify","信息审核-"+row.trueName);
    bs.resetDlgPosition("verify");
}

function delModal(index){
    var row = bs.tableRow("#table",index)
    bs.alert({msg:"确定删除【"+row.trueName+"】",cancelText:"取消"},function () {
        bs.ajax({
                url:"/future/money/deleteMember",
                data:{id:row.id},
                success:function (data) {
                    bs.errorHandle(data,null,"删除成功");
                }
            }
        );
    })
}
window.parent.onscroll = function() {
    bs.resetDlgPositionByState("verify");
}