$(function () {
    $("#registerform").validate(registerValid);
});
function regist() {
    if(!$('#registerform').valid()){
        $('#registerform').removeClass('animated fadeInDownBig').addClass('animated shake').
        one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function(){
            $(this).removeClass('animated shake');
        });
        return;
    }
    var pwd = $("#registerform input[name='password']").val();
    // (?!^\d+$)  排除全数字
    // (?!^[a-zA-Z]+$) 排除全字母
    //     [0-9a-zA-Z]{4,23} 字符或字母4-23位，不考虑全为数字和全为字符情况。
    if(!/(?!^\d+$)(?!^[a-zA-Z]+$)[0-9a-zA-Z]{6,8}/.test(pwd)){
        bs.toast("error","格式错误","密码必须是6-8位的数字和字母,如：1111aa");
        return;
    }
    $("#registerform input[name='password']").val(md5(pwd));
    bs.ajax({
        url : '/future/money/register',
        data: $('#registerform').serialize(),
        success : function(data) {
            if(data.code){
                bs.toast("error","注册提示",data.msg);
            }else{
                bs.toast("success","注册提示",data);
                logoutAuto();
            }
        },
        error : function(data) {
            bs.toast("error","系统维护","后台升级中，请联系阿姨");
        }
    });
}
var registerValid={
    rules:{
        password:{
            required: true,
            minlength:6,
            maxlength:8
        },
        confirm_password:{
            equalTo:"#registerform input[name=password]"
        },
        trueName:{
            required:true
        },
        college:{
            required:true
        },
        major:{
            required:true
        },
        sn:{
            required:true
        }

    },
    messages:{
        password:{
        }
    }
};