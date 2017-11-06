function showFreeSetting() {
    $("#testarea .showFreeSetting").css("display","none");
    $("#testarea .hideFreeSetting").css("display","block");
    $("#testarea .freeSettingArea").slideDown();
}
function hideFreeSetting() {
    $("#testarea .showFreeSetting").css("display","block");
    $("#testarea .hideFreeSetting").css("display","none");
    $("#testarea .freeSettingArea").slideUp();
}
function updateInter() {
    //获取子窗口信息
    var data = getIframeInfo();
    $("#interfaceCName").html(data.interfaceCName);
    if(!data || !data.interfaceId){bs.toast("warning","提醒","请先选择接口"); return;}
    window.location="../html/interfaceSetting.html?operation=1&interfaceId="+data.interfaceId;
}
function delInter() {
    //获取子窗口信息
    var data = getIframeInfo();
    $("#interfaceCName").html(data.interfaceCName);
    if(!data || !data.interfaceId){bs.toast("warning","提醒","请先选择接口"); return;}
    bs.alert({msg:"确定删除接口【"+data.interfaceCName+"】",cancelText:"取消"},function () {
        //删除
        $.ajax({
            url: bs.apiRoot() + '/api/interfaceSettingDel',
            type:"post",
            dataType : "json",
            async:false,
            contentType: "application/json",
            data:JSON.stringify({id:data.interfaceId}),
            success:function(obj){
                closableTab.closeTab($('.nav-tabs i[tabclose=tab_seed_'+data.interfaceId+']'));
                $("#interfaceCName").html("欢迎使用模拟工具-mock");
                initMenu();
            },
            error : function(xhr, type) {
                bs.toast('error',xhr.status,xhr.statusText);
            }
        });
    })
}
function mockInter() {
    //获取子窗口信息
    var data = getIframeInfo();
    $("#interfaceCName").html(data.interfaceCName);
    if(!data || !data.interfaceId){bs.toast("warning","提醒","请先选择接口"); return;}
    var iframewindow = data.iframewindow;

    var url = $("#testarea input[name=url]").val();
    url=url.replace(/\s/g,'');
    if(url==''){bs.toast("warning",'警告','请输入测试url');return;}

    //获取子窗口参数
    var reqParam = iframewindow.getStructuredNodes("publtree");

    //启用业务参数
    var isUseData = $("#testarea input[name=isUseData]:checked").val();
    if(isUseData==1){
        var busiParam = iframewindow.getStructuredNodes("busitree");
        busiParam = JSON.stringify(busiParam);
        if(busiParam!="{}"){
            reqParam.data  = busiParam;
        }
    }

    //签名
    var isSign = $("#testarea input[name=isSign]:checked").val();
    if(isSign==1){
        var sign  = signParam(reqParam);
        if(sign=="签名异常") {
            parseByTree({error:sign}, "messtree1");
            return;
        }
        reqParam.sign = sign;
    }

    //隐藏请求参数
    var isShowReq = $("#testarea input[name=isShowReq]:checked").val();
    if(isShowReq==1){$("#testarea .reqParamArea").css("display","none");}
    else{$("#testarea .reqParamArea .reqParam").html(JSON.stringify(reqParam));$("#testarea .reqParamArea").css("display","block");}

    //构建请求类型：form or ajax
    var childType = $("#testarea input[name=childType]:checked").val();
    var isPost = $("#testarea input[name=isPost]:checked").val();
    var submitType="GET";
    if(isPost==1){submitType="POST";}
    var respParam = mockPost(childType,url,reqParam,submitType);
    if(respParam){
        parseByTree(respParam,"messtree1")
    }
}

function mockPost(childType,url,reqParam,submitType){
    $("#loading-msg").html("正在加载数据 ......");
    showShadeDiv();
    //参数预字符化
    $.each(reqParam,function(key,value){
        reqParam[key]=((typeof value=='string')&&value.constructor==String)?value:JSON.stringify(value);
    });
    var respParam = null;
    if(childType==1){//ajax
        $.ajax({
            url:bs.apiRoot()+url , 
            type:submitType,
            dataType : "json",
            async:false,
            data:reqParam,
//                contentType: "application/json",
            beforeSend: function(request) {
            // request.setRequestHeader("wlttttt", "11111111");
    },
            complete:function() {
                // console.log(this);
            },
            success:function(obj, status, xhr){
                respParam=obj;
                // console.log(xhr);
            },
            error : function(xhr, type) {
                // console.log(xhr.getAllRequestHeaders());
                // console.log(xhr.getResponseHeader('Accept'));
                // console.log(xhr.getResponseHeader('Accept-Language'));
                // console.log(xhr.getResponseHeader('Connection'));
                // console.log(xhr.getResponseHeader('Cookie'));
                // console.log(xhr.getResponseHeader('Host'));
                // console.log(xhr.getResponseHeader('Referer'));
                // console.log(xhr.getResponseHeader('User-Agent'));
                // console.log(xhr.getResponseHeader('Accept-Charset'));
                // console.log(xhr.getResponseHeader('Accept-Encoding'));
                // console.log(xhr.getAllResponseHeaders());
                var respTitle = {};
                respTitle.Server = xhr.getResponseHeader("Server");
                respTitle.Date = xhr.getResponseHeader("Date");
                respTitle["Content-Type"] = xhr.getResponseHeader("Content-Type");
                respTitle["Content-Length"] = xhr.getResponseHeader("Content-Length");
                respTitle.Connection = xhr.getResponseHeader("Connection");
                respParam={status:xhr.status,statusText:xhr.statusText,"响应头":respTitle};
                // respParam.error1={error2:"ajax请求异常",status:xhr.status,statusText:xhr.statusText};
                // respParam.error1.error2={error:"ajax请求异常请求异常请求异常请求异常fadfadfadfadfadfadsfadfadfadfa",status:xhr.status,statusText:xhr.statusText};
                // respParam.error1.error2.error={error:"ajax请求异常请求异常请求异常请求异常fadfadfadfadfadfadsfadfadfadfa",status:xhr.status,statusText:xhr.statusText};
                // respParam.error1.error2.error.error={error:"ajax请求异常请求异常请求异常请求异常fadfadfadfadfadfadsfadfadfadfa",status:xhr.status,statusText:xhr.statusText};
                // respParam.error1.error2.error.error.error={error:"ajax请求异常请求异常请求异常请求异常fadfadfadfadfadfadsfadfadfadfa",status:xhr.status,statusText:xhr.statusText};
                // respParam.error1.error2.error.error.error.error={error:"ajax请求异常请求异常请求异常请求异常fadfadfadfadfadfadsfadfadfadfa",status:xhr.status,statusText:xhr.statusText};
                // respParam.error1.error2.error.error.error.error.error={error:"ajax请求异常请求异常请求异常请求异常fadfadfadfadfadfadsfadfadfadfa",status:xhr.status,statusText:xhr.statusText};
            }
        });
    }
    else if(childType==2){//form  可以解决跨域问题
        //动态构建表单
        $("#mockform").empty();
        $.each(reqParam,function(key,value){
            var html = '<input name="'+key+'">';
            $("#mockform").append(html);
            $("#mockform input[name="+key+"]").val(value);
        });
        $("#mockform").attr({"action":url,"method":submitType}).submit();
    }
    hideShadeDiv();
    return respParam;
}

function signParam(param) {
    var sign = null;
    //参数预字符化
    $.each(param,function(key,value){
        param[key]=((typeof value=='string')&&value.constructor==String)?value:JSON.stringify(value);
    });
    $.ajax({
        url: bs.apiRoot() + '/api/signParam',
        type:"post",
        dataType : "json",
        async:false,
        data:JSON.stringify(param),
        contentType: "application/json",
        success:function(obj){
            sign = obj.data;
        },
        error : function(xhr, type) {
            sign={error:"签名异常",status:xhr.status,statusText:xhr.statusText};
        }
    });
    return sign;
}

function add(id,name,uri){
    var item = {'id':id,'name':name,'url':uri,'closable':true};
    closableTab.addTab(item);
}
function right() {
    $("#testarea").animate({"right":-300},500);
    $("#testarea .rightmock").css("display","none");
    $("#testarea .leftmock").css("display","block");
}
function left() {
    $("#testarea").animate({"right":0},500);
    $("#testarea .leftmock").css("display","none");
    $("#testarea .rightmock").css("display","block");
}
function getIframeInfo() {
    var data = {};
    var iframeInterfaceId ;
    var activeIframe = $("section article section[class=tab-content]>.active>iframe").eq(0);
    var iframeName = activeIframe.attr("name");
    //iframe窗口对象
    var activeIframeObj = activeIframe[0].contentWindow;
    data.interfaceId = activeIframeObj.interfaceId;
    data.iframeName = iframeName;
    data.iframeId = activeIframe.attr("id");;
    data.interfaceCName = activeIframe.contents().find("#interfaceCName").html();
    data.iframecontent = activeIframe.contents();
    data.iframewindow=activeIframeObj;
    return data;

}

function generateTreeStructure(data) {
    if(data.length==0) return
    $.each(data,function (index,obj) {
        obj.state={expanded:true};
        obj.tags=[obj.note];
        obj.text=obj.icname;
        if(obj.pid!=0){
            obj.href = '../html/mock.html?interfaceId='+obj.id;
        }
        generateTreeStructure(obj.nodes);
    });
    return data;
}

function initMenu() {
    var treedata = [];
    $.ajax({
        url: bs.apiRoot() + '/api/interfaceSettingSelect',
        type:"post",
        dataType : "json",
        async:false,
        success:function(obj){
            if(obj.data!=null){
               treedata=generateTreeStructure(obj.data);
            }
        },
        error : function(xhr, type) {
            bs.toast('error',xhr.status,xhr.statusText);
        }
    });

    var $Tree=$('#treeview').treeview({
        expandIcon: 'glyphicon glyphicon-menu-right',
        collapseIcon: 'glyphicon glyphicon-menu-down',
        onhoverColor: "#E0FFFF",
        levels:100,
        enableLinks: false,
        data: treedata,
        onNodeSelected: function(event, node) {
            if(node.href && node.href.length!=0){
                if(node.pid!=0)
                    add(node.id,node.text,node.href)
            }
        }
    });
}