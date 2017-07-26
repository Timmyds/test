
require.config({
    baseUrl: '../../static/common/js',
    paths: {
        'jquery': 'libs/jquery/jquery-1.11.3.min',
        'validate': 'libs/jquery/jquery-validation/jquery.validate.min',
        'validator.addMethod':'libs/jquery/jquery-validation/jQuery.validator.addMethod',
        'base64':'libs/base64'
    },
    shim: {
        'jquery': {
            exports: '$'
        },
        'validate' :  ['jquery'],
        'validator.addMethod':['jquery','validate']
    }
});


var login_token="";
var randomDate="";
require(['jquery','validate','validator.addMethod','base64'], function ($){
    $(document).ready(function() {
   
    	$('#loginName').blur('click', function() {
    		var loginName=  $.trim($("#loginName").val());
		   	 if(loginName=='undefined'||loginName.length==0){
		   		$(".error-text").html("请输入账号");
		   		 return;
		   	 }
    		 $.ajax({
    		     	type:'post',
    		        url:"/usercentre/randomNum.do",
    		        data:{account:loginName},
    		        async:false,
    		        success:function(data) {
    		        	randomDate=data.data;
    		    }
    		    });
    	});
    	
    	$('#denglu').on('click', function() {
    		login();
   	 	}); 
    	$("#denglu").keyup(function(){
            if(event.keyCode == 13){
            	login();
            }
        });
    	$("#passWord").keyup(function(){
            if(event.keyCode == 13){
            	login();
            }
        });
        
    });
});

function reloadImage(){
	var loginName=$.trim($("#loginName").val());
	document.getElementById('verified').src='/code.do?loginName='+loginName+'&ts='+new Date().getTime();
}
	   
var sendSMSCode = function() {
	$.ajax({
		type : "POST",
		async : "true",
		url : "/loginMessage/findUserMessage.do",
		data : {
			loginTekon:login_token
		},
		success : function(data) {
			if (data.status == 200) {
				$("#reget-code").css("display", "inline-block");
				$("#get-code").css("display", "none");
				$('#phone_div').undelegate('#get-code', 'click', sendSMSCode);
				var time = 300;
				var t = setInterval(function() {
					time = time - 1;
					$("#time").html("(" + time + ")");
					if (time == 0) {
						$("#get-code").css("display", "inline-block");
						$("#reget-code").css("display", "none");
						clearInterval(t);
						time = 300;
						$("#time").html("(" + time + ")");
						$('#phone_div').delegate('#get-code', 'click', sendSMSCode);
					}
				}, 1000);
			} else {
				if (data.msg) {
					error='<label class="error msg-error" >'+data.msg+'</label>';
	                $(".error-text1").html(error);
				} else {
					error='<label class="error msg-error" >登录过期，请重新登录</label>';
	                $(".error-text1").html(error);
				}

				$("#get-code").val("获取手机验证码");
			};
		},
		error : function(xmlHttpRequest, textStatus, errorThrown) {
			error='<label class="error msg-error" >程序异常！</label>';
            $(".error-text1").html(error);
			return;
		}
	});
};

//下一步
function loginConfirm() {
	var code = $("#phoneCode").val();
	if (code == "" || code == null) {
		error='<label class="error msg-error" >验证码为空！</label>';
        $(".error-text1").html(error);
		return;
		
	}
	$.ajax({
		type : "GET",
		async : "true",
		url : "/loginMessage/userCheckMessage.do",
		data : {
			code : code,
			loginTekon:login_token
		},
		success : function(data) {
			if (data.status == 200) {
				url = data.data;
             	window.location.href = url;
			} else {
				error='<label class="error msg-error" >' + data.msg + '</label>';
                $(".error-text1").html(error);
				return;
			}
		},
		error : function(xmlHttpRequest, textStatus, errorThrown) {
			error='<label class="error msg-error" >程序异常！</label>';
            $(".error-text1").html(error);
			return;
		}
	});
}
function login(){
	var loginName=  $.trim($("#loginName").val());
  	 if(loginName=='undefined'||loginName.length==0){
  		$(".error-text").html("请输入账号");
  		 return;
  	 }
  	 var password= $.trim($("#passWord").val());
  	 if(password=='undefined'||password.length==0){
  		$(".error-text").html("请输入密码 ");
  		 return;
	 }
  	  loginName=Base64.encode(loginName);
  	  password=Base64.encode(password+"_"+randomDate);
	var verifiedCode=$("#verifiedCode").val();
	$("#passWord").val("");
	$("#verifiedCode").val("");
   var url="/usercentre/login.do";
   $.ajax({
    	type:'post',
       url:url,
       data: {
        	'verifiedCode':verifiedCode,
        	'loginName':loginName,
        	'password':password
        		},
       async:false,
       success:function(data) {
        	if(data){
        		if(data.status == 200){
                	url = data.data;
                 	if("/views/home.html"==url){
                 		window.location.href = url;
                 	}else{
                 		$("#phone_div").show();
                 		$("#login_div").hide();
                 		var arr=url.split("|");
                 		var phone=arr[0];
                 		login_token=arr[1];
                 		$("#phone").html(phone);
                 		$('#phone_div').delegate('#get-code', 'click', sendSMSCode);
                 	}
                }else{
                	if(data.status == 500){
                        error='<label class="error msg-error" >'+data.msg+'</label>';
                         $(".error-text").html(error);
                    }
                	if(data.data!=null&&data.data.errorCount>=3){
                		$("#inputCode").show();
                    	reloadImage();
                	}
                	$("#loginName").focus();  
                }

        	}
         },
         error: function(err) {
        	 alert("登录失败！");
         }
    });
}