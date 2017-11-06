/**
 * 将json数据解析成树形格式返回
 */
function parseByTree(rs,id){
    var rsobj = rs;
    if(!isJson(rs))
        rsobj= eval('('+rs+')');
    /*添加无级树*/
    var str = "";
    var html = forTree(rsobj,str);
    document.getElementById(id).innerHTML = html;
    menuTree(id);
}
/*递归实现获取无级树数据并生成DOM结构*/
function forTree(o,str) {
    var urlstr = "";
    var keys = [];
    for (var key in o) {//使用 for in 循环遍历对象的属性时，原型链上的所有属性都将被访问,推荐总是使用 hasOwnProperty 方法， 这将会避免原型对象扩展带来的干扰:
        //只遍历对象自身的属性，而不包含继承于原型链上的属性
        if (o.hasOwnProperty(key) === true)
            keys.push(key);
    }
    for (var j = 0; j < keys.length; j++) {
        k = keys[j];
        if (typeof o[k] == "object") {
            urlstr = "<div><span>" + k + "</span><ul>";
        } else {
            urlstr = "<div><span><lable class='messkey'>" + k + "</lable>：" + o[k] + "</span><ul>";
        }
        str += urlstr;
        var kcn = 0;
        if (typeof o[k] == "object") {
            for (var kc in o[k]) {
                kcn++;
            }
        }
        if (kcn > 0) {
            str = forTree(o[k],str);
        }
        str += "</ul></div>";
    }
    return str;
}
/*树形菜单*/
function menuTree(id) {
//给有子对象的元素加[+-]
    $("#"+id+" ul").each(function (index, element) {
        var ulContent = $(element).html();
        var spanContent = $(element).siblings("span").html();
        if (ulContent) {
            $(element).siblings("span").html("[+] " + spanContent).css({"margin-left":"-20px","font-weight":"bolder"});
        }
    });

    $("#"+id).find("div span").click(function () {
        var ul = $(this).siblings("ul");
        var spanStr = $(this).html();
        var spanContent = spanStr.substr(3, spanStr.length);
        if (ul.find("div").html() != null) {
            if (ul.css("display") == "none") {
                ul.show(300);
                $(this).html("[-] " + spanContent);
            } else {
                ul.hide(300);
                $(this).html("[+] " + spanContent);
            }
        }
    });
}

/*全部展开*/
$("#btn_open").click(function () {
    $("#menuTree ul").show(300);
    curzt("-");
})
/*全部收缩*/
$("#btn_close").click(function () {
    $("#menuTree ul").hide(300);
    curzt("+");
})
function curzt(v) {
    $("#menuTree span").each(function (index, element) {
        var ul = $(this).siblings("ul");
        var spanStr = $(this).html();
        var spanContent = spanStr.substr(3, spanStr.length);
        if (ul.find("div").html() != null) {
            $(this).html("[" + v + "] " + spanContent);
        }
    });
}
/**
 * 判断obj是否为json对象
 * @param obj
 * @returns {Boolean}
 */
function isJson(obj){
    var isjson = typeof(obj) == "object" && Object.prototype.toString.call(obj).toLowerCase() == "[object object]" && !obj.length;
    return isjson;
}