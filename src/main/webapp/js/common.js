$(function () {
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
            console.log(iframeWin.document.documentElement.scrollHeight || iframeWin.document.body.scrollHeight);
            iframe.height = iframeWin.document.documentElement.scrollHeight || iframeWin.document.body.scrollHeight;
        }
    }
}