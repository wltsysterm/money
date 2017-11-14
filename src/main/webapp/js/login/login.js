$(function () {
    $("#loginform").validate(loginValid);
    document.onkeydown=function(event){
        var e = event || window.event || arguments.callee.caller.arguments[0];
        if(e && e.keyCode==13){ // enter 键
            submitLogin();
        }
    };
});
function submitLogin() {
    if(!$('#loginform').valid()){
        $('#loginform').removeClass('animated fadeInDownBig').addClass('animated shake').
        one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function(){
            $(this).removeClass('animated shake');
        });
        return;
    }
    var pwd = $("#loginform input[name='password']").val();
    $("#loginform input[name='password']").val(md5(pwd));
    bs.ajax({
        url : '/future/money/login',
        data: $('#loginform').serialize(),
        success : function(data) {
            if(data.state=="ok"){
                window.location="/";
            }else{
                bs.toast("warning",data.code,data.msg);
            }
        },
        error : function(data) {
            bs.toast("error","系统维护","后台升级中，请联系阿姨");
        }
    });
}
var loginValid={
    rules:{
        password:{
            required: true,
        },
        sn:{
            required:true
        }

    },
    messages:{
    }
};