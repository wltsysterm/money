$(function () {
    bs.authCheck();
    unSettleInfo();
    bs.select("#billproject",[]);
    $(".addbill-section").on("click",".addbill-btn",function () {
        bs.alert({msg:"确定结算所有人员未结账单？",cancelText:"取消"},function () {
            bs.ajax({
                url:"/future/money/settleAllBill",
                success:function (result) {
                    if(result && result.code){
                        bs.toast("error",result.code,result.msg);
                        return;
                    }
                    unSettleInfo();
                    bs.tableRefresh("#table");
                    bs.toast("info","结算成功");
                }
            });
        });
    })
    bs.table('#table', {
        url: "/future/money/selectMemberBySn",
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
            title: '未结金额(￥)',
            align: 'center',
        }, {
            title: '操作',
            width: '150px',
            field: 'state',
            formatter: function (value, row, index) {
                var html = "";
                if(row.note!=0) {
                    html += "<span class='projectmgr-edit-span'><i title='结算' class='glyphicon glyphicon-import' onclick='openModal(" + index + ");'></i></span>";
                    html += "<span class='projectmgr-edit-span'><i title='查看明细' class='glyphicon glyphicon-list' onclick='openDetModal(" + index + ");'></i></span>";
                }
                return html;
            }
        }]
    });
    bs.table('#tabledet', {
        url: "/future/money/selectUnsettleDetByMember",
        params: function () {
            return $("#formdet").serializeObject();
        },
        pagination: true,
        height:"100%",
        columns: [{
            field: 'projectName',
            title: '项目名称',
            align: 'center'
        }, {
            field: 'projectPrice',
            title: '收费标准(￥)',
            align: 'center'
        }, {
            field: 'projectCount',
            title: '数量',
            align: 'center'
        }, {
            field: 'createTime',
            title: '创建时间',
            align: 'center'
        }]
    });
    $("#form .selectMember").on("click",function () {
        bs.tableRefresh("#table");
    });
});
function unSettleInfo() {
    bs.ajax({
        url:"/future/money/selectUnsettleBill",
        success:function (result) {
            $(".addbill-section .unsettlemoney").html(result.projectPrice);
            $(".addbill-section .unsettlecount").html(result.projectCount);
        }
    });
}

function openDetModal(index){
    var row = bs.tableRow("#table",index);
    var width=window.innerWidth-10;
    $("#formdet input[name=id]").val(row.id);
    bs.tableRefresh("#tabledet");
    bs.submitForm({
        title:"title",
        single:true,
        id:"detdiv",
        width:width,
        height:260
    });
    bs.resetDlgTitle("detdiv","账单明细-"+row.trueName);
}

function openModal(index){
    var row = bs.tableRow("#table",index);
    var list = new Array();
    list.push(row.id);
    bs.alert({msg:"确定结算【"+row.trueName+"】账单？请确保财务已结清",cancelText:"取消"},function () {
        bs.ajax({
            url:"/future/money/settleBillByMembers",
            data:list,
            contentType:true,
            success:function (result) {
                if(result && result.code){
                    bs.toast("error",result.code,result.msg);
                    return;
                }
                unSettleInfo();
                bs.tableRefresh("#table");
                bs.toast("info","结算成功");
            }
        });
    });
}

window.parent.onscroll = function() {
    bs.resetDlgPositionByState("detdiv");
}
