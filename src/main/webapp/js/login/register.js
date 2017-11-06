$(function () {
    $("#registerform").validate(registerValid);
});

var registerValid={
    rules:{
        password:{
            required: true,
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