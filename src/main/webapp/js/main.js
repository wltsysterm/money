$(function () {
    var auth = bs.auth();
    if(!auth){
        window.location="../html/login.html";
    }else{
        bs.authMenu(auth);
        $(".header-div .user-info .money-greet").html(auth.trueName+"，"+bs.greet());
    }
    //    点击回到头部
    $(window).scroll(function(){
        if ($(this).scrollTop()>300)
        {
            $('.totop').slideDown();
        }
        else
        {
            $('.totop').slideUp();
        }
    });
    $('.totop a').click(function (e) {
        e.preventDefault();
        $('body,html').animate({scrollTop: 0}, 500);
    });
//    点击回到头部--

    $("#IndexMainDiv .aside-nav").on("click","li",function () {
        var _this = $(this);
        _this.siblings().removeClass("current");
        _this.addClass("current");
        openPage(_this);
    })
    $(".logout").on("click",function () {
        var _this = $(this);
        bs.ajax({
            url : '/future/money/logout',
            success : function(data) {
                if(data.state=="success"){
                    bs.toast("success","登出提示",data.msg);
                    window.location="../html/login.html";
                }else if(data.state=="warning"){
                    bs.toast("warning","登出提示",data.msg);
                }
            },
            error : function(data) {
                bs.toast("error","系统维护","后台升级中，请联系阿姨");
            }
        });
    })
});
function initLi() {
    //隐藏全部功能
    // $('li[money]').hide();
}
function openPage(_this) {
    //验证是否登入
    //打开页面
    var url = _this.attr("href");
    if($("#IndexMainDiv iframe").attr("src")!=("../html/"+url+".html")){
        $("#IndexMainDiv iframe").attr("src","../html/"+url+".html");
    }
}
function resizeIframe(iframe) {
    if (iframe) {
        var iframeWin = iframe.contentWindow || iframe.contentDocument.parentWindow;
        if (iframeWin.document.body) {
            iframe.height = iframeWin.document.documentElement.scrollHeight || iframeWin.document.body.scrollHeight;
            $(iframe).css("min-height",document.documentElement.clientHeight-55);
        }
    }
}
window.onresize=function () {
    $("#content iframe").css("min-height",document.documentElement.clientHeight-55);
}