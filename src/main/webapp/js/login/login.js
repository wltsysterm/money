$(function () {
    $("#loginform").validate();
});
function submitLogin() {
    $("#loginform").valid();
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