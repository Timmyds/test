$(function() {
		pwdVerification();
		answerVerification();
		questionVerification();
		$('#div1').delegate('#get-code', 'click', sendSMSCode);
		
		$('#account').blur(function() {
			checkAccount();
		});
		$('#account1').blur(function() {
			checkAccount1();
			findQuestion();
		});
		$('#phone').blur(function() {
			checkPhoe();
		});
		
		$('#code').blur(function() {
			checkCode();
		});
	});

	var sendSMSCode = function() {
		if(!checkPhoe()){return false;}
		if(!checkAccount()){return false;}
		$.post("/forgetMessage.do?phone=" + $("#phone").val()+"&account="+$("#account").val(),  "", function(backdata) {
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
		var vphone=$("#phone").val();
		var vcode=$("#code").val();
		var vaccount=$("#account").val();
		if(!checkAccount()){return false;}
		if(!checkPhoe()){return false;}
		if(!checkCode()){return false;}
		$.ajax({
			type : "GET",
			async : "true",
			url : "/checkMessage.do",
			 data:{phone:vphone,code:vcode,account:vaccount},
			success : function(data) {
				if (data.status == 200) {
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
		if(i==11){
			i=1;
		}
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
	
	function checkAccount(){
		var vaccount=$.trim($("#account").val());
		var isCheck = true;
		if(vaccount == ''){
			$("#account-ck").removeClass("ok").addClass("red").html("请输入您的账户");
			isCheck =  false;
		}else if(vaccount.length < 2 ||vaccount.length>20){
			$("#account-ck").removeClass("ok").addClass("red").html("账户在2~20位之间");
			isCheck = false;
		}
		if(isCheck){
			$("#account-ck").removeClass("red").addClass("ok").html("");
		}
		return isCheck;
	}
	
	function checkAccount1(){
		var vaccount=$.trim($("#account1").val());
		var isCheck = true;
		if(vaccount == ''){
			$("#account1-ck").removeClass("ok").addClass("red").html("请输入您的账户");
			isCheck =  false;
		}else if(vaccount.length < 2 ||vaccount.length>20){
			$("#account1-ck").removeClass("ok").addClass("red").html("账户在2~20位之间");
			isCheck = false;
		}
		if(isCheck){
			$("#account1-ck").removeClass("red").addClass("ok").html("");
		}
		return isCheck;
	}
	function checkPhoe(){
		var vphone=$.trim($("#phone").val());
		var isCheck = true;
		var patrn=/^1[34578]\d{9}$/;
		if(vphone == ''){
			$("#phone-ck").removeClass("ok").addClass("red").html("请输入手机号码");
			isCheck = false;
		}else if(!patrn.exec(vphone)){
			$("#phone-ck").removeClass("ok").addClass("red").html("请输入正确的手机号码");
			isCheck = false;
		}
		if(isCheck){
			$("#phone-ck").removeClass("red").addClass("ok").html("");
		}
		return isCheck;
	}
	function checkCode (){
		var vcode=$.trim($("#code").val());
		var isCheck = true;
		var patrn=/^\d{6}$/;
		if(vcode == ''){
			$("#code-ck").removeClass("ok").addClass("red").html("请输入验证码");
			isCheck =  false;
		}else if(!patrn.exec(vcode)){
			$("#code-ck").removeClass("ok").addClass("red").html("请输入正确的验证码");
			isCheck = false;
		}
		if(isCheck){
			$("#code-ck").removeClass("red").addClass("ok").html("");
		}
		return isCheck;
	}
function checkStep(){
	var loginName=$("#account").val();
	var loginName1=$("#account1").val();
		if(!checkPwd()){
			return false;
		}
		if(!checkRepwd()){
			return false;
		}
		if(loginName){
			 phoneRecoverPwd();
		}	
		if(loginName1){
			securityRecoverPwd();
		}
		
	}
	//密保找回密码
function  securityRecoverPwd(){
	var loginName1=$("#account1").val();
	var question1 = $("#question1").combobox('getValue');
	var question2 = $("#question2").combobox('getValue');
	var question3 = $("#question3").combobox('getValue');

	var answer1 = $("#answer1").val();
	var answer2 = $("#answer2").val();
	var answer3 = $("#answer3").val();
	if (question1 <= 0) {
		return false;
	}
	if (question2 <= 0) {
		return false;
	}
	if (question3 <= 0) {
		return false;
	}
	if(answer1){
		answer1=Base64.encode($.trim(answer1));
	}else{
		return false;
	}
	if(answer2){
		answer2=Base64.encode($.trim(answer2));
	}else{
		return false;
	}
	if(answer3){
		answer3=Base64.encode($.trim(answer3));
	}else{
		return false;
	}
	
	var security=question1+"_"+answer1+"-"+question2+"_"+answer2+"-"+question3+"_"+answer3;	

	if(loginName1==""||loginName1.length==0){
			stepdiv(1);
			return false;
		}
		var pwd= Base64.encode($("#repwd").val());
		if(pwd==""||pwd.length==0){
			stepdiv(1);
			return false;
		}
		
	    $("#securitybtn").removeAttr("onclick");
		 $.ajax({
			 	type : "POST",
				async : "true",
	            url: "/recoverPwd/forgetPassword.do",
	            data: {"password":pwd,"security":security,"account":loginName1},
	            success: function(backdata) {
	            	  if (backdata.status == 200) {
	            		  stepdiv(3);
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
	            	  setTimeout("$('#securitybtn').attr('onclick','checkStep()')",3000); //设置三秒后提交按钮 显示  
	            }
	               
	        });
	}
//手机找回密码
function  phoneRecoverPwd(){
	var phonev=$("#phone").val();
	var codev=$("#code").val();
	var loginName=$("#account").val();
	if(phonev==""||phonev.length==0){
		stepdiv(1);
		return false;
	}
	
	if(codev==""||codev.length==0){
		stepdiv(1);
		return false;
	}
	if(loginName==""||loginName.length==0){
		stepdiv(1);
		return false;
	}
	var pwd= Base64.encode($("#repwd").val());
	if(pwd==""||pwd.length==0){
		stepdiv(1);
		return false;
	}
	  $("#securitybtn").removeAttr("onclick");
	 $.ajax({
		 	type : "POST",
			async : "true",
            url: "/recoverPwd/recoverPwdPassword.do",
            data: {"password":pwd,"code":codev,"phone":phonev,"account":loginName},
            success: function(backdata) {
            	  if (backdata.status == 200) {
            		  stepdiv(3);
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
            	  setTimeout("$('#securitybtn').attr('onclick','checkStep()')",3000); //设置三秒后提交按钮 显示  
            }
               
        });
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
function recoveredType(i){
	$('div[id^="div"]').each(function() {
		$(this).hide();
	});
	$('#div' + i).show();
	$("#account").val("");
	$("#account1").val("");
	$("#account-ck").removeClass("red").removeClass("ok").html("");
	$("#account1-ck").removeClass("red").removeClass("ok").html("");
	if(i==11){
		findQuestion();
	}
}
	

//获取问题列表
function findQuestion() {
	var vaccount1=$("#account1").val();
	$('.question').combobox({
		editable : false, //不可编辑状态
		cache : false,
		valueField : 'id',
		textField : 'question',
	});
	$.ajax({
		type : "GET",
		async : "true",
		data : {
			"account":vaccount1,
		},
		url : "../../question/getQuestion.do",
		success : function(data) {
			if (data.status == 200) {
				$(".question").combobox("loadData", data.data);
				$(".question").combobox("setValue", data.data[0].id);
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

function checkSecurityAnswer() {
	var vaccount1=$("#account1").val();
	if(!checkAccount1()){return false;}
	var question1 = $("#question1").combobox('getValue');
	var question2 = $("#question2").combobox('getValue');
	var question3 = $("#question3").combobox('getValue');

	var answer1 = $("#answer1").val();
	var answer2 = $("#answer2").val();
	var answer3 = $("#answer3").val();
	if (question1 <= 0) {
		checkQuestion(1);
		return false;
	}
	if (question2 <= 0) {
		checkQuestion(2);
		return false;
	}
	if (question3 <= 0) {
		checkQuestion(3);
		return false;
	}
	
	if(checkAnswer(1)){
		answer1=Base64.encode($.trim(answer1));
	}else{
		return false;
	}
	
	if(checkAnswer(2)){
		answer2=Base64.encode($.trim(answer2));
	}else{
		return false;
	}
	
	if(checkAnswer(3)){
		answer3=Base64.encode($.trim(answer3));
	}else{
		return false;
	}
	
	var security=question1+"_"+answer1+"-"+question2+"_"+answer2+"-"+question3+"_"+answer3;

	$.ajax({
		type : "POST",
		url : "../../question/checkSecurityAnswer.do",
		data : {
			"account":vaccount1,
			"security" : security,
		},
		async : false,
		success : function(backdata) {
			console.log(backdata);
			if (backdata.status == 200) {
				stepdiv(2);
			} else {
				$.messager
						.alert({
							title : '提示',
							width : 420,
							msg : '<div class="content">' + backdata.msg
									+ '</div>',
							ok : '<i class="i-ok"></i> 确定',
							icon : 'error'
						});
			}
		}
	});

}
function  answerVerification(){
	$('#answer1').blur(function() {
		checkAnswer(1);
	});
	$('#answer2').blur(function() {
		checkAnswer(2);
	});
	$('#answer3').blur(function() {
		checkAnswer(3);
	});
}


function checkAnswer(i){
	var answer=$("#answer"+i).val();
	var re = /^[\u4e00-\u9fa5a-z1-9]{2,20}$/gi;//只能输入汉字和英文字母、数字
	if(answer == ''){
		$("#answer"+i+"-ck").removeClass("ok").addClass("red").html("密保答案不能为空！");
		return false;
	}
	if(!re.test(answer)){
		$("#answer"+i+"-ck").removeClass("ok").addClass("red").html("密保答案为2-20位字符（不包括特殊字符）");
		return false;
	}
	if(i>1){
		var n=i-1;
		var answer_n=$("#answer"+n).val();
		if(answer==answer_n){
			$("#answer"+i+"-ck").removeClass("ok").addClass("red").html("密保答案不能重复");
			return false;
		}
	}
	
	if(i>2){
		var n=i-2;
		var answer_n=$("#answer"+n).val();
		if(answer==answer_n){
			$("#answer"+i+"-ck").removeClass("ok").addClass("red").html("密保答案不能重复");
			return false;
		}
	}
	$("#answer"+i+"-ck").removeClass("red").addClass("ok").html("");
	return true;
}
function  questionVerification(){

	$('#question1').combobox({
		onSelect: function(param){
			checkQuestion(1);
		}
	});
	$('#question2').combobox({
		onSelect: function(param){
			checkQuestion(2);
		}
	});
	$('#question3').combobox({
		onSelect: function(param){
			checkQuestion(3);
		}
	});
}

function checkQuestion(i){
	var question=$("#question"+i).combobox('getValue');
	if(question <= 0){
		$("#question"+i+"-ck").removeClass("ok").addClass("red").html("请选择问题！");
		return false;
	}

	if(i>1){
		var n=i-1;
		var question_n=$("#question"+n).combobox('getValue');
		if(question==question_n){
			$("#question"+i+"-ck").removeClass("ok").addClass("red").html("密保答案不能重复");
			return false;
		}
	}
	if(i>2){
		var n=i-2;
		var question_n=$("#question"+n).combobox('getValue');
		if(question==question_n){
			$("#question"+i+"-ck").removeClass("ok").addClass("red").html("密保答案不能重复");
			return false;
		}
	}
	$("#question"+i+"-ck").html("");
	return true;
}