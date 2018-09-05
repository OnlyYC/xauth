function FaceLogin(opt){
    opt = opt || {};
    this.identifyCallback = opt.identifyCallback || this.identifyCallback;
    this.successCallback = opt.successCallback || this.successCallback;
    this.failCallback = opt.failCallback || this.failCallback;
    this.init();
    this.initialization = false


    FaceLogin.INSTANCE = this;
}

FaceLogin.prototype = {
    successCallback: function(imageData){

    },
    failCallback: function(message){

    },
    identifyCallback: function(){

    },
    resize: function(width, height){
        cloudwalkobj.width = width
        cloudwalkobj.height = height
    },
    openCamera: function(){
        //开启摄像头
        return click_startCamera();
    },
    close: function(){
        //关闭摄像头
        click_stopCamera();
    },
    show: function(){
        var _this = this;
        if(!_this.initialization){
            //初始化插件
            var el = document.getElementById("cloudwalk-obj");
            var w = el.clientWidth||el.offsetWidth;
            var h = el.clientHeight||el.offsetHeight;
            createPlugin(el, w, h, version); //初始化插件，并注册活体检测的回调
        }
    },
    //打开人脸登录页面
    open : function(){
        var _this = this;
        //显示人脸检测页面(能预览摄像头画面)
        _this.show();

        //初始化摄像头
        if(!_this.openCamera()){
            //打开失败
            setTimeout(function () {
                //关闭窗口
                _this.close();
            },2000);
            console.info('初始化摄像头失败')
            return;
        }

        setTimeout(function () {
            //注册一个定时器，不断调用红外活体检测，直到活体检测通过，如果超过指定时间都没检测到活体，提示用户
            _this.timer = window.setInterval(
                function(){
                    //调用活体检测
                    var detectResult = cloudwalkobj.cwStartLiveDetect();
                },
                1000
            )
        },1000);
        

        //得到最佳人脸图片，调用识别获取用户账号信息

    },
    //识别
    identify : function(base64){
        var _this = this;

        //关闭窗口
        setTimeout(function () {
            //回调 识别信息
            _this.identifyCallback("yckj0618", "20180823");
            _this.close();
        },500)
    },
    //环境检查
    envCheck : function(){

        //操作系统

        //浏览器

        //插件是否安装

        //摄像头

        //是否授权

    },

    init : function(){
        //环境检查

        //环境检查不通过（提示用户）


    }
}



var BrowserDetect =
    {
        init : function ()
        {
            //alert(navigator.userAgent);
            this.browser = this.searchString(this.dataBrowser) || "An unknown browser";
            this.version = this.searchVersion(navigator.userAgent) || this.searchVersion(navigator.appVersion) || "an unknown version";
            this.OS = this.searchString(this.dataOS) || "an unknown OS";
        },
        searchString : function (data)
        {
            for (var i = 0; i < data.length; i++)
            {
                var dataString = data[i].string;
                var dataProp = data[i].prop;
                this.versionSearchString = data[i].versionSearch || data[i].identity;
                if (dataString)
                {
                    if (dataString.indexOf(data[i].subString) != -1)
                        return data[i].identity;
                }
                else if (dataProp)
                    return data[i].identity;
            }
        },
        searchVersion : function (dataString)
        {
            var index = dataString.indexOf(this.versionSearchString);
            if (index == -1)
                return;
            return parseFloat(dataString.substring(index + this.versionSearchString.length + 1));
        },

        dataBrowser :
            [
                {
                    string : navigator.userAgent,
                    subString : "Chrome",
                    identity : "Chrome"
                },
                {
                    string : navigator.userAgent,
                    subString : "OmniWeb",
                    versionSearch : "OmniWeb/",
                    identity : "OmniWeb"
                },
                {
                    string : navigator.vendor,
                    subString : "Apple",
                    identity : "Safari",
                    versionSearch : "Version"
                },
                {
                    prop : window.opera,
                    identity : "Opera",
                    versionSearch : "Version"
                },
                {
                    string : navigator.vendor,
                    subString : "iCab",
                    identity : "iCab"
                },
                {
                    string : navigator.vendor,
                    subString : "KDE",
                    identity : "Konqueror"
                },
                {
                    string : navigator.userAgent,
                    subString : "Firefox",
                    identity : "Firefox"
                },
                {
                    string : navigator.vendor,
                    subString : "Camino",
                    identity : "Camino"
                },
                { // for newer Netscapes (6+)
                    string : navigator.userAgent,
                    subString : "Netscape",
                    identity : "Netscape"
                },
                {
                    string : navigator.userAgent,
                    subString : "MSIE",
                    identity : "Explorer",
                    versionSearch : "MSIE"
                }
                ,
                {
                    //IE11
                    string : navigator.userAgent,
                    subString : "Gecko",	//在navigator.userAgent中查找Gecko
                    identity : "Explorer",	//标识为哪个浏览器
                    versionSearch : "rv"	//版本查找
                }
                // ,
                // {
                // string : navigator.userAgent,
                // subString : "Gecko",
                // identity : "Mozilla",
                // versionSearch : "rv"
                // }
                // ,
                // { // for older Netscapes (4-)
                // string : navigator.userAgent,
                // subString : "Mozilla",
                // identity : "Netscape",
                // versionSearch : "Mozilla"
                // }

            ],
        dataOS : [
            {
                string : navigator.platform,
                subString : "Win",
                identity : "Windows"
            },
            {
                string : navigator.platform,
                subString : "Mac",
                identity : "Mac"
            },
            {
                string : navigator.userAgent,
                subString : "iPhone",
                identity : "iPhone/iPod"
            },
            {
                string : navigator.platform,
                subString : "Linux",
                identity : "Linux"
            }
        ]

    };
BrowserDetect.init();

//alert(BrowserDetect.browser+"  "+BrowserDetect.version+"  "+BrowserDetect.OS);




//摄像头相关错误码
var CW_ERR_CameraNotOpen			=	9000;		//摄像头未打开
var CW_ERR_CameraOpenError			=	9001;		//摄像头打开失败
var CW_ERR_CameraOpenAdy			=	9002;		//摄像头已经打开
var CW_ERR_InitSDK					=	9100;		//初始化SDK失败

//活体状态
var CW_LivenessOK 					= 	0;			//活体并正常获得最佳人脸
var CW_LivenessFailure 				= 	9301;		//未符合活体要求
var CW_LivenessNOFace 				= 	9302;		//未检测到人脸
var CW_LivenessNisNOFace			=	9303;		//红外未检测到人脸
var CW_LivenessLostFace 			= 	9304;		//人脸丢失
var CW_LivenessManyFace 			= 	9305;		//检测到多人
var CW_LivenessChangeFace			=   9307;		//检测到换人
var CW_GetBestFaceErr 				= 	117;		//获取最佳人脸失败

var version="1.1.10.0520";
var cloudwalkobj;
var rotatemode = 0;	//0-正常 1-顺时针90度 2-顺时针180度 3-顺时针270度
// $(document).ready(function(){
//
//     resultObj = document.getElementById("result");
//     bestImg = document.getElementById("bestImgObj");
//
//     var w=640;		//640  400  320
//     var h=w*3/4;	//宽高比为4：3
//     var el = document.getElementById("cloudwalkwebobj");
//     createPlugin(el, w, h, version);
//
//     $("#bt_chose0").click(function(){
//         click_liveDetect();
//     });
//
//     $("#bt_chose1").click(function(){
//         click_stopCamera();
//     });
//
//     $("#bt_chose2").click(function(){
//         click_startCamera();
//     });
//
// });


function liveDetectEvent(rt) {
    var jsonObj = eval('(' + rt + ')');

    if(jsonObj.result !=0){
        var message = ""
        switch(jsonObj.result)
        {
            case CW_LivenessFailure:
                message = "未符合活体要求！";
                break;
            case CW_LivenessNOFace:
                message = "未检测到人脸！";
                break;
            case CW_LivenessNisNOFace:
                message = "红外光未检测到人脸！";
                break;
            case CW_LivenessLostFace:
                message = "人脸丢失！";
                break;
            case CW_LivenessManyFace:
                message = "检测到多人！";
                break;
            case CW_GetBestFaceErr:
                message = "获取最佳人脸失败！";
                break;
            case CW_LivenessChangeFace:
                message = "检测到换人";
                break;
            default:
                message = "非活体，错误码："+jsonObj.result;
                break;
        }
        //返回失败信息
        FaceLogin.INSTANCE.failCallback(message)
    }else{
        //todo 人脸检测成功

        //检测成功(关闭定时器)
        clearInterval(FaceLogin.INSTANCE.timer);
        //关闭摄像头
        click_stopCamera();

        //拿到最佳人脸数据，进行识别
        FaceLogin.INSTANCE.successCallback(jsonObj.image)
    }
}

function createPlugin(targetE,width,height,version) {
    cloudwalkobj = document.createElement("object");

    if(1 == rotatemode || 3 == rotatemode){ //1    3 为竖屏
        cloudwalkobj.width = height;
        cloudwalkobj.height = width;
    }else{
        cloudwalkobj.width = width;
        cloudwalkobj.height = height;
    }

    cloudwalkobj.id = "CloudWalkSDKPlugin";

    var bDet=BrowserDetect.browser;
    if("Explorer"==bDet)
    {
        cloudwalkobj.classid = "CLSID:BD0F48B3-EC69-4107-AF33-107C35D09E2C";	//IE
    }else if("Firefox"==bDet)
    {
        cloudwalkobj.type = "application/x-cloudwalknis";		//Firefox
    }else if("Chrome"==bDet)
    {
        cloudwalkobj.type = "application/x-cloudwalknis";		//Chrome
    }else{
        cloudwalkobj.type = "application/x-cloudwalknis";
    }

    targetE.appendChild(cloudwalkobj);		//所有方法、属性的调用必须在插件添加到页面之后

    if(!cloudwalkobj.valid){
        showDownloadNotice();
        return false;
    }

    registerCallBack(cloudwalkobj, "cwLivesInfoCallBack", liveDetectEvent);

    pluginInit(width, height);
    return true;
}

function pluginInit(width,height) {
    var initRt = cloudwalkobj.cwInit();
    if(0 != initRt)
    {
        alert("ApiKey或SecretKey已过期，请联系技术支持！错误码："+initRt);
        return;
    }
}


function click_liveDetect() {
    cloudwalkobj.cwStartLiveDetect();
}

function click_stopCamera() {
    // showMessage("", resultObj);
    cloudwalkobj.cwStopCamera();
}

function click_startCamera() {
    return openCamera();
}

function openCamera()
{
    var flag = false;
    var rt = cloudwalkobj.cwStartCamera(); // 摄像头打开方式和摄像头ID写在配置文件里面
    console.info(rt)
    if (0 != rt) {
        switch(rt)
        {
            case CW_ERR_CameraNotOpen:
                showMessage("摄像头未打开！", resultObj);
                break;
            case CW_ERR_CameraOpenError:
                showMessage("摄像头打开失败！", resultObj);
                break;
            case CW_ERR_CameraOpenAdy:
                flag = true;
                showMessage("摄像头已经打开！", resultObj);
                break;
            default:
                showMessage("未认证或者摄像头初始化失败 ！错误码："+ rt, resultObj);
                break;
        }
    }else{
        flag = true;
    }
    return flag;
}



function showDownloadNotice(){
    //alert("人脸识别控件未安装");
    $("#downloadNotice").removeClass("hide");
    $("#downloadNotice").addClass("show");
    $("#rside").addClass("hide");
}

function showNewVersionNotice(){
    $("#newVersionNotice").removeClass("hide");
    $("#newVersionNotice").addClass("show");
    $("#rside").addClass("hide");
}

// 注册回调事件(插件名，插件提供的事件名称，调用的JS函数)
function registerCallBack(obj, name, proc) {
    if (typeof(proc) != "function")
        return;
    if (window.ActiveXObject || "ActiveXObject" in window) {
        if (window.ActiveXObject && obj.attachEvent) {
            obj.attachEvent(name, proc);
        } else {
            cloudWalk_AttachIE11Event(obj, name, proc);
        }
    } else {
        obj[name] = proc;
    }
}

// IE11注册回调事件
function cloudWalk_AttachIE11Event(obj, _strEventId, _functionCallback) {
    document['']
    var nameFromToStringRegex = /^function\s?([^\s(]*)/;
    var paramsFromToStringRegex = /\(\)|\(.+\)/;
    var params = _functionCallback.toString().match(paramsFromToStringRegex)[0];
    var functionName = _functionCallback.name || _functionCallback.toString().match(nameFromToStringRegex)[1];
    document[functionName]=_functionCallback;

    var handler;
    try {
        handler = document.createElement("script");
        handler.setAttribute("for", obj.id);
    } catch (ex) {
        handler = document.createElement('<script for="' + obj.id + '">');
    }
    handler.event = _strEventId + params;
    handler.appendChild(document.createTextNode('document.'+functionName + params + ";"));
    document.body.appendChild(handler);
}




export  {
    FaceLogin
}