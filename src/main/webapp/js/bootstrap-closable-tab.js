var closableTab = {
    maxTab:1000,
    frameLoad:function (iframe){
        //frame加载完成后设置父容器的高度，使iframe页面与父页面无缝对接
        // var mainheight = $(frame).contents().find('body').height();
        // $(frame).parent().height(mainheight);
		//高度自适应
        if (iframe) {
           var iframeWin = iframe.contentWindow || iframe.contentDocument.parentWindow;
           if (iframeWin.document.body) {
               iframe.height = window.parent.innerHeight-127-5;//;iframeWin.document.documentElement.scrollHeight || iframeWin.document.body.scrollHeight;
               // iframe.height=800;
           }
        }
    },

    //添加tab
    addTab:function(tabItem){ //tabItem = {id,name,url,closable}
        if(!closableTab.maxTab){
            alert("请先关闭部分tab");
            return;
        }
        var id = "tab_seed_" + tabItem.id;
        var container ="tab_container_" + tabItem.id;

        $("li[id^=tab_seed_]").removeClass("active");
        $("div[id^=tab_container_]").removeClass("active");

        if(!$('#'+id)[0]){
            var li_tab = '<li role="presentation" class="" id="'+id+'" name="li'+id+'"><a href="#'+container+'"  role="tab" data-toggle="tab" style="position: relative;padding:2px 20px 2px 15px;font-size: 15px;" title="'+tabItem.name+'"><div style="overflow: hidden; white-space: nowrap; text-overflow: ellipsis;width:50px;">'+tabItem.name+"</div>";
            if(tabItem.closable){
                li_tab = li_tab + '<i title="关闭" class="glyphicon glyphicon-remove small" tabclose="'+id+'" style="position: absolute;right:4px;top: 4px;"  onclick="closableTab.closeTab(this)"></i></a></li> ';
            }else{
                li_tab = li_tab + '</a></li>';
            }

            var tabpanel = '<div role="tabpanel" class="tab-pane" id="'+container+'" style="width: 100%;">'+
                '<iframe  src="'+tabItem.url+'" id="iframe'+container+'" name="iframename'+container+'" frameborder="0" width="100%" scrolling="auto"  onload="closableTab.frameLoad(this)"></iframe>'+
                '</div>';


            $('.nav-tabs').append(li_tab);
            $('.tab-content').append(tabpanel);
        }
        $("#"+id).addClass("active");
        $("#"+container).addClass("active");
        closableTab.maxTab--;
    },

    //关闭tab
    closeTab:function(item){
        closableTab.maxTab++;
        var val = $(item).attr('tabclose');
        var containerId = "tab_container_"+val.substring(9);

        if($('#'+containerId).hasClass('active')){
            $('#'+val).prev().addClass('active');
            $('#'+containerId).prev().addClass('active');
        }


        $("#"+val).remove();
        $("#"+containerId).remove();
    }
}