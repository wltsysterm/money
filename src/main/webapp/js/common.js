$(function () {
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
});
function openPage(_this) {
    //验证是否登入
    //打开页面
    var url = _this.attr("href");
    $("#IndexMainDiv iframe").attr("src",url);
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