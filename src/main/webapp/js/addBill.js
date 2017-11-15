$(function () {
    bs.authCheck();
    unSettleInfo();
    $("#form").validate(paramValid);
    bs.ajax({
        url:"/future/money/selectProject",
        success:function (result) {
            var infoList = new Array();
            $.each(result,function (index,obj) {
                if(index==0){
                    $("#form input[name=projectPrice]").val(obj.projectPrice);
                    $("#form .projectPrice").html(obj.projectPrice);
                    $("#form input[name=projectName]").val(obj.projectName);
                }
                infoList.push({id:obj.id,text:obj.projectName});
            })
            bs.select("#form select[name=projectNameSelect]",infoList);
            $("#form").on("change","select[name=projectName]",function () {
                var _this = $(this);
                bs.ajax({
                    url:"/future/money/selectProjectByID",
                    data:{id:_this.val()},
                    success:function (result) {
                        $("#form input[name=projectPrice]").val(result.projectPrice);
                        $("#form .projectPrice").html(result.projectPrice);
                        $("#form input[name=projectName]").val(result.projectName);
                    }
                });
            });
        }
    });
    $(".addbill-section").on("click",".addbill-btn",function () {
        bs.alert({
            msg:"谨慎新建账单，一经新建不能撤销",
        },  function(){
            bs.submitForm({
                id:"form",
                isClear:true,
                title:"新建账单",
                width:400
            },function (result) {
                if(result && result.code){
                    bs.toast("error",result.code,result.msg);
                    return;
                }
                bs.tableRefresh("#table");
                bs.toast("info","添加成功");
                unSettleInfo();
            },function () {
                if(!bs.validMoney($("#form input[name=projectPrice]").val(),true)){
                    return false;
                };
                return bs.validNumber($("#form input[name=projectCount]").val(),true);
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
        }, {
            field: 'settleTime',
            title: '结算时间',
            align: 'center'
        }]
    });
});
function unSettleInfo() {
    bs.ajax({
        url:"/future/money/selectUnsettleBillByMember",
        success:function (result) {
            $(".addbill-section .unsettlemoney").html(result.projectPrice);
            $(".addbill-section .unsettlecount").html(result.projectCount);
        }
    });
}
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
var paramValid={
    rules:{
        projectName:{
            required: true,
        },
        projectCount:{
            required:true
        }

    },
    messages:{
    }
};