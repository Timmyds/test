//得到url地址，拿出指定参数的值
function acceptParam(paras){
	var url = location.href;
	var paraString = url.substring(url.indexOf("?")+1,url.length).split("&");
	var paraObj = {}
	for (i=0; j=paraString[i]; i++){
		paraObj[j.substring(0,j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=")+1,j.length);
	}
	var returnValue = paraObj[paras.toLowerCase()];
	if(typeof(returnValue)=="undefined"){
		return "";
	}else{
		return returnValue;
	}
}
//EasyUI DataGrid根据字段动态合并单元格
// param tableID 要合并table的id
//@param fldList 要合并的列,用逗号分隔(例如："name,department,office");
function MergeCells(tableID, fldList) {
    var Arr = fldList.split(",");
    var dg = $('#' + tableID);
    var fldName;
    var RowCount = dg.datagrid("getRows").length;
    var span;
    var PerValue = "";
    var CurValue = "";
    var length = Arr.length - 1;
    for (i = length; i >= 0; i--) {
        fldName = Arr[i];
        PerValue = "";
        span = 1;
        for (row = 0; row <= RowCount; row++) {
            if (row == RowCount) {
                CurValue = "";
            }
            else {
                CurValue = dg.datagrid("getRows")[row][fldName];
            }
            if (PerValue == CurValue) {
                span += 1;
            }
            else {
                var index = row - span;
                dg.datagrid('mergeCells', {
                    index: index,
                    field: fldName,
                    rowspan: span,
                    colspan: null
                });
                span = 1;
                PerValue = CurValue;
            }
        }
    }
}

/*
*js Unicode编码转换
*/ 
var decToHex = function(str) {
	str =str.replace(/[\r\n]/g,"");//替换回车换行
	str =str.replace(/[\t]/g,"");//替换tab占位符
    var res=[];
    if(str){
    for(var i=0;i < str.length;i++)
        res[i]=("00"+str.charCodeAt(i).toString(16)).slice(-4);
	}
    return "\\u"+res.join("\\u");
};
var hexToDec = function(str) {
    str=str.replace(/\\/g,"%");
    return unescape(str);
};
function getSessionId() {
    var c_name = 'JSESSIONID';
    if (document.cookie.length > 0) {
        c_start = document.cookie.indexOf(c_name + "=");
        if (c_start != -1) {
            c_start = c_start + c_name.length + 1;
            c_end = document.cookie.indexOf(";", c_start);
            if (c_end == -1)
                c_end = document.cookie.length;
            return unescape(document.cookie.substring(c_start, c_end));
        }
    }
}

function uploadImages(selector,url,view,imgUrl,usessionId){	
    var sessionId = getSessionId();
    $(selector).uploadify({
        'auto': true, // 是否自动上传   
        'multi': false, // 是否支持多文件上传    
        'buttonText':'上传图片', // 按钮上的文字  
        'fileSizeLimit':'2MB',
        'width':80,
        'height':25,
        'margin-left': 37,
        'fileObjName':'fileUpload', // 传到后台的对象名    
        'fileTypeExts':'*.jpg;*.jpeg',
        'fileTypeDesc':'请选择图片文件,格式jpg、jpeg',
//        'fileTypeExts':'*.jpg;*.jpeg;*.gif;*.png;*.bmp',
//        'fileTypeDesc':'请选择图片文件,格式png、gif、jpg、jpeg、bmp',
        'swf'      : imgUrl,
        'uploader': url+';jsessionid='+sessionId, // 上传到后台的处理类    
        
//        formData    附带值，需要通过get or post传递的额外数据，需要结合onUploadStart事件一起使用
//        'formData'      : {'someKey' : 'someValue', 'someOtherKey' : 1},
//        'onUploadStart' : function(file) {
//            $("#fileUpload").uploadify("settings", "someOtherKey", 2);
//        }
        'onUploadSuccess' : function(file, data, response) {
            //alert(data);
//            $('#imgUrl').val(data);
            $(view).attr("src",data);
        },
        'overrideEvents': ['onSelectError','onDialogClose'],  
        'onSelectError': function (file, errorCode, errorMsg) {  
              switch (errorCode) {  
                  case -100:  
                      $.messager.alert('操作提示', '上传的文件数量已经超出系统限制的'+$('#fileUpload').uploadify('settings', 'queueSizeLimit')+'个文件！', 'error');
                      break;  
                  case -110:  
                      $.messager.alert('操作提示', '文件大小超出系统限制的2M大小！', 'error');
                      break;  
                  case -120:  
                      $.messager.alert('操作提示', '文件大小异常！', 'error');
                      break;  
                  case -130:  
                      $.messager.alert('操作提示', '文件类型不正确！', 'error');
                      break;  
              }  
              return false;  
         }, 
         //检测FLASH失败调用   
         'onFallback': function () {  
//           $("#resultMessage").html('<span style="color:red">您未安装FLASH控件，无法上传！请安装FLASH控件后再试。<span>');
             $.messager.alert('操作提示', '您未安装FLASH控件，无法上传！请安装FLASH控件后再试。', 'error');
         } 
    
    });
}

/**
 *将豪秒转抱成指定的格式 
 *@millisecond: 豪秒
 *@format：格式化字符
 *  用法示意：dateFormat(1433232328000,"yyyy-MM-dd HH:mm:ss") 
 */
function dateFormat(millisecond, format){
      var t = new Date(millisecond);
      var tf = function(i){return (i < 10 ? '0' : '') + i;};
  return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function(a){
      switch(a){
          case 'yyyy':
              return tf(t.getFullYear());
              break;
          case 'MM':
              return tf(t.getMonth() + 1);
              break;
          case 'mm':
              return tf(t.getMinutes());
              break;
          case 'dd':
              return tf(t.getDate());
              break;
          case 'HH':
              return tf(t.getHours());
              break;
          case 'ss':
                return tf(t.getSeconds());
            break;
          }
      });
  };
  /** 
   * 使用方法: 
   * 开启:MaskUtil.mask(); 
   * 关闭:MaskUtil.unmask(); 
   *  
   * MaskUtil.mask('其它提示文字...'); 
   */
  var MaskUtil = (function () {

      var $mask, $maskMsg;

      var defMsg = '正在处理，请稍待。。。';

      function init() {
          if (!$mask) {
              $mask = $("<div class=\"datagrid-mask mymask\"></div>").appendTo("body");
          }
          if (!$maskMsg) {
              $maskMsg = $("<div class=\"datagrid-mask-msg mymask\">" + defMsg + "</div>")
                  .appendTo("body").css({ 'font-size': '12px' });
          }

          $mask.css({ width: "100%", height: $(document).height() });

          $maskMsg.css({
              left: ($(document.body).outerWidth(true) - 190) / 2, top: ($(window).height() - 45) / 2
          });

      }

      return {
          mask: function (msg) {
              if (msg) {
                  defMsg = msg;
              }
              init();
              $mask.show();
              $maskMsg.html(msg || defMsg).show();
          }
          , unmask: function () {
              $mask.hide();
              $maskMsg.hide();
          }
      }
  })