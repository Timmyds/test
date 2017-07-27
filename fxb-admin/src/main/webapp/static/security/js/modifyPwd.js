
$(function() {
		getSecurityInfo();
		pwdVerification();
		$('#div1').delegate('#get-code', 'click', sendSMSCode);
	});
	
	function getSecurityInfo() {
		var url = '../../security/getUserSecurityInfo.do';
		$.ajax({
			url : url,
			traditional : true,
			type : 'get',
			async : true,
			cache : false,
			dataType : "json",
			success : function(data) {
				if (data) {
					$("#phone").html(data.phone);
				}
			},
			error : function(data) {

				$.messager.alert('提示', '操作失败!', 'error');
			}
		});
	}
	var sendSMSCode = function() {
		$.get("/getUserMessage.do?phone=" +$("#phone").html(), "", function(backdata) {
			if (backdata.status == 200) {
				$("#reget-code").css("display", "inline-block");
				$("#get-code").css("display", "none");
				$('#div1').undelegate('#get-code', 'click', sendSMSCode);
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
						$('#div1').delegate('#get-code', 'click', sendSMSCode);
					}
				}, 1000);
			} else {
				getSecurityInfo();
				if (backdata.msg) {
					$.messager
							.alert({
								title : '提示',
								width : 420,
								msg : '<div class="content">' + backdata.msg
										+ '</div>',
								ok : '<i class="i-ok"></i> 确定',
								icon : 'error'
							});
				} else {
					$.messager.alert({
						title : '提示',
						width : 420,
						msg : '<div class="content">账号手机号不匹配，或者账号无效！</div>',
						ok : '<i class="i-ok"></i> 确定',
						icon : 'error'
					});
				}

				$("#get-code").val("获取手机验证码");
			}
		});
	};
	//下一步
	function clickNext() {
		var code = $("#code").val();
		if (code == "" || code == null) {
			$.messager.alert({
				title : '提示',
				width : 420,
				msg : '<div class="content">验证码为空！</div>',
				ok : '<i class="i-ok"></i> 确定',
				icon : 'error'
			});
			return false;
		}
		$.ajax({
			type : "GET",
			async : "true",
			url : "/userCheckMessage.do",
			data : {
				code : code
			},
			success : function(data) {
				if (data.status == 200) {
					codev=code;
					stepdiv(2);
				} else {
					$.messager.alert({
						title : '提示',
						width : 420,
						msg : '<div class="content">' + data.msg + '</div>',
						ok : '<i class="i-ok"></i> 确定',
						icon : 'error'
					});
					return;
				}
			},
			error : function(xmlHttpRequest, textStatus, errorThrown) {
				$.messager.alert({
					title : '提示',
					width : 420,
					msg : '<div class="content">程序异常！</div>',
					ok : '<i class="i-ok"></i> 确定',
					icon : 'error'
				});
				return;
			}
		});
	}

	

	function stepdiv(i) {
		$('div[id^="div"]').each(function() {
			$(this).hide();
		});
		$('#div' + i).show();
		$('.bg').each(function() {
			$(this).removeClass('step_sp_active');
		});
		$('.xs').each(function() {
			$(this).removeClass('step_xs_active');
		});
		$("#bg" + i).addClass('step_sp_active');
		$('#s' + i).addClass('step_xs_active');

	}
	function  pwdVerification(){
		$('#pwd').blur(function() {
			checkPwd();
		});
		$('#repwd').blur(function() {
			checkRepwd();
		});
		
	}
	

	function checkPwd(){
		var vpwd=$("#pwd").val();
		var isCheck = true;
		
		if(vpwd == ''){
			$("#pwd-ck").removeClass("ok").addClass("red").html("请输入您的新密码");
			isCheck = false;
		}else if(vpwd.length < 6){
			$("#pwd-ck").removeClass("ok").addClass("red").html("密码最小长度为6");
			isCheck = false;
		}

		if(validatePwd(vpwd,'new')){
			$("#pwd-ck").removeClass("ok").addClass("red").html(validatePwd(vpwd,'new'));
			return false;
		}
		if(isCheck){
			$("#pwd-ck").removeClass("red").addClass("ok").html("");
		}
		
		return isCheck;
	}
	
	function checkRepwd (){
		var vpwd=$("#pwd").val();
		var vrepwd=$("#repwd").val();
		var isCheck = true;
		
		if(vrepwd == ''){
			$("#repwd-ck").removeClass("ok").addClass("red").html("请再次输入您的密码");
			isCheck = false;
		}else if(vrepwd.length < 6){
			$("#repwd-ck").removeClass("ok").addClass("red").html("密码最小长度为6");
			isCheck = false;
		}else if(vrepwd != vpwd){
			$("#repwd-ck").removeClass("ok").addClass("red").html("两次密码输入不一致");
			isCheck = false;
		}
		if(validatePwd(vrepwd,'new')){
			$("#repwd-ck").removeClass("ok").addClass("red").html(validatePwd(vrepwd,'new'));
			return false;
		}
		if(isCheck){
			$("#repwd-ck").removeClass("red").addClass("ok").html("");
		}
		
		return isCheck;
	}
	
	function checkStep(){
		if(checkPwd()&&checkRepwd()){

			var pwd= Base64.encode($("#repwd").val());
			if(pwd==""||pwd.length==0){
				stepdiv(1);
				return false;
			}
			$("#setPwd").removeAttr("onclick");
			 $.ajax({
		            type: "POST",
		            url: "/recoverPwd/modifyPassword.do",
		            data: { "password": pwd,"code":codev },
		            async: false,
		            success: function(backdata) {
		                if (backdata.status == 200) {
		                	stepdiv(3);
		                	
		                	$.messager.alert({
								title : '提示',
								width : 420,
								msg : '<div class="content">密码修改成功,5秒后返回登录页面</div>',
								ok : '<i class="i-ok"></i> 确定',
								icon : 'info'
							});
		                	setTimeout(function() {
			                	window.parent.location="/usercentre/outLogin.do";
		                	}, 5000);
		                } else {
		                	$.messager.alert({
						  	      title:'提示',
						  	      width:420,
						  	      msg:'<div class="content">'+backdata.msg+'</div>',
						  	      ok:'<i class="i-ok"></i> 确定',
						  	      icon:'error'
						  	    });
		                	stepdiv(2);
		                }
		                setTimeout("$('#setPwd').attr('onclick','checkStep()')",3000); //设置三秒后提交按钮 显示  
		            }
		        });
		
		}
	}


function validatePwd(password,pwdtype){
	var pwd=['123456','234567','345678','456789','987654','876543','765432','654321'];
	
	if(!/^[0-9a-zA-Z]{6,15}$/.test(password)){
		return "密码为6-16个字符，不支持空格";
	}
	if(pwdtype=='new'&&(/^([0-9a-zA-Z])\1{5}$/.test(password)||$.inArray(password, pwd )>-1)){
		return "密码过于简单";
	}
	
}
function  outLogin(){
	window.parent.location="/usercentre/outLogin.do";
}