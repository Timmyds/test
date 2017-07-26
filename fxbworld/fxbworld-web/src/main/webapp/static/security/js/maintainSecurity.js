$(function() {
		getSecurityInfo();
		findQuestion();
		answerVerification();
		questionVerification();
		$('#div1').delegate('#get-code', 'click', sendSMSCode);
	});

	var codev="";
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

		$.get("/getUserMessage.do?phone=" + $("#phone").html(), "", function(backdata) {
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
		})
	}

	//获取问题列表
	function findQuestion() {
		$('.question').combobox({
			editable : false, //不可编辑状态
			cache : false,
			valueField : 'id',
			textField : 'question',
		});
		$.ajax({
			type : "GET",
			async : "true",
			url : "../../question/findQuestion.do",
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

	function saveSecurity() {
		
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
		$("#saveSec").removeAttr("onclick");
		$.ajax({
			type : "POST",
			url : "../../question/saveSecurityAnswer.do",
			data : {
				"code" : codev,
				"security" : security,
			},
			async : false,
			success : function(backdata) {
				 
				if (backdata.status == 200) {
					stepdiv(3);
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
				setTimeout("$('#saveSec').attr('onclick','saveSecurity()')",3000); //设置三秒后提交按钮 显示  
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
				checkQuestion(1)
			}
		});
		$('#question2').combobox({
			onSelect: function(param){
				checkQuestion(2)
			}
		});
		$('#question3').combobox({
			onSelect: function(param){
				checkQuestion(3)
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
				$("#question"+i+"-ck").removeClass("ok").addClass("red").html("密保问题不能重复");
				return false;
			}
		}
		if(i>2){
			var n=i-2;
			var question_n=$("#question"+n).combobox('getValue');
			if(question==question_n){
				$("#question"+i+"-ck").removeClass("ok").addClass("red").html("密保问题不能重复");
				return false;
			}
		}
		$("#question"+i+"-ck").html("");
		return true;
	}