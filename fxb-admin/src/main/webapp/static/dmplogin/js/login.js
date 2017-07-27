
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
	$("#passWord").val("");
   var url="/usercentre/login.do";
   $.ajax({
    	type:'post',
       url:url,
       data: {
        	'reqSource':"dmp",
        	'loginName':loginName,
        	'password':password
        		},
       async:false,
       success:function(data) {
        	if(data){
        		if(data.status == 200){
                	url = data.data;
                 		window.location.href = url;
                 	
                }else{
                	if(data.status == 500){
                        error='<label class="error msg-error" >'+data.msg+'</label>';
                         $(".error-text").html(error);
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