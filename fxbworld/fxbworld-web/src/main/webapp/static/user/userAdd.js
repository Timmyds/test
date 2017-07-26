
var groupData=null;
var groupNum=1;
$(function(){
	commoninit();
	$('#addGUserForm').delegate('#loginName_td input','blur',checkLoginName);
	getUserRole();
	submitForm();
	addgroupBtn();
	if(userInfo.roleType==2||userInfo.roleType==3){//除去平台和县域 其他类型账号无用户组
		$("#user_group").show();
	}
});
/*$.extend($.fn.validatebox.defaults.rules, {    
    phoneNum: { //验证手机号   
        validator: function(value, param){ 
         return /^1[3-8]+\d{9}$/.test(value);
        },    
        message: '请输入正确的手机号码。'   
    },
    
    telNum:{ //既验证手机号，又验证座机号
      validator: function(value, param){ 
          return /^(13[0-9]|14[0-9]|15[0-9]|18[0-9])\d{8}$/.test(value);
         },    
         message: '请输入正确的电话号码。' 
    }  
});*/
//页面加载时，立即加载数据
function submitForm(){	
	$("#submit").click(
			function() {
				if ($("#addGUserForm").form('validate')) {
					var logReg=/^[0-9a-zA-Z][\_0-9a-zA-Z|0-9a-zA-Z]{1,19}$/;
					var nameReg=/^[\u4E00-\u9FA5\w]{2,20}$/;
					/*var pwdReg=/^[\S]{6,16}$/;*/
					var phoneReg=/^(13[0-9]|14[0-9]|15[0-9]|18[0-9]|17[0-9])\d{8}$/;
					//提交表单
				    var roleType =$('#roleType').combobox('getValue');
					//var status =$('#status').combobox('getValue');
				
					 if(!logReg.test($.trim($('#loginName').val()))){
						 $.messager.alert({
		              	      title:'提示',
		              	      msg:'<div class="content">账号名称无效，请重新输入</div>',
		              	      ok:'<i class="i-ok"></i> 确定',
		              	      icon:'error'
		              	    });
				     }/*else if(!pwdReg.test($.trim($('#loginPwd').val()))){
						$.messager.alert('提示','密码无效，请重新输入');
					 }*/else if(!nameReg.test($.trim($('#userName').val()))){
						$.messager.alert({
		              	      title:'提示',
		              	      msg:'<div class="content">用户名称无效，请重新输入</div>',
		              	      ok:'<i class="i-ok"></i> 确定',
		              	      icon:'error'
		              	    });
					 }else if(!phoneReg.test($.trim($('#phone').val()))){
						 $.messager.alert({
		              	      title:'提示',
		              	      msg:'<div class="content">手机号码无效，请重新输入</div>',
		              	      ok:'<i class="i-ok"></i> 确定',
		              	      icon:'error'
		              	    });
				/*	 }else if(status==null||status=='null'||status=='-1'){
						 $.messager.alert({
		              	      title:'提示',
		              	      msg:'<div class="content">账号状态不能为空</div>',
		              	      ok:'<i class="i-ok"></i> 确定',
		              	      icon:'error'
		              	    });*/
					 }else if(roleType==null||roleType=='null'||roleType==''||roleType=='-1'){
						 $.messager.alert({
		              	      title:'提示',
		              	      msg:'<div class="content">角色类型不能为空</div>',
		              	      ok:'<i class="i-ok"></i> 确定',
		              	      icon:'error'
		              	    });
					 }/*else if(!$.ValiDateISNULL($("#addGUserForm"),"groupIds")){
						 $.messager.alert({
		              	      title:'提示',
		              	      msg:'<div class="content">用户组不能为空</div>',
		              	      ok:'<i class="i-ok"></i> 确定',
		              	      icon:'error'
		              	    });
					 }*/else{
						$("#submit").hide();
						var loginName=$.trim($('#loginName').val());
						$.ajax({
							url:'../../user/findUserIsRegister.do',
							type:'post',
							data:{
								loginName:loginName
							},
							success:function(data){
								if(data.data!=null&&data.data>0){
									 $.messager.alert({
					              	      title:'提示',
					              	      msg:'<div class="content">账户名称已存在，请重新输入</div>',
					              	      ok:'<i class="i-ok"></i> 确定',
					              	      icon:'error'
					              	    });
								}else if(data.data!=null&&data.data==0){
									_ajaxSubmit();
								}
							},
							complete:function(){
								$("#submit").show();
							},
							dataType:'json'		
						});
					}			
				}
		});
}

function checkLoginName(){
	var loginName=$.trim($('#loginName_td input.textbox-text').val());
/*	var logReg = /^[0-9a-zA-Z]{1,19}$/;
	if(!logReg.test(loginName)){
		 $.messager.alert({
     	      title:'提示',
     	      msg:'<div class="content">账户名称无效，请输入2-20位的字母，数字和下划线组成</div>',
     	      ok:'<i class="i-ok"></i> 确定',
     	      icon:'error'
     	    });
		return;
	}*/
	$.ajax({
		url:'../../user/findUserIsRegister.do',
		type:'post',
		data:{
			loginName:loginName
		},
		success:function(data){
			if(data.data!=null&&data.data>0){
				$.messager.alert({
		     	      title:'提示',
		     	      msg:'<div class="content">账户名称已存在，请重新输入</div>',
		     	      ok:'<i class="i-ok"></i> 确定',
		     	      icon:'error'
		     	    });
			}
		},
		dataType:'json'		
	});
}

//获取角色类型
function getUserRole(){
	$('#roleType').combobox({
		url:'../../sysRole/sysRoleVo.do',
		cache: false,
        //panelHeight: 'auto',//自动高度适合
		panelHeight: 200,
		method:'post',
		editable: false,
		valueField:'id',
		textField:'value'
		});
	$('#userGroup1').combobox({
		url:'../../sysGroup/groupSelectList.do',
		cache: false,
        //panelHeight: 'auto',//自动高度适合
        panelHeight: 200,
		method:'post',
		editable: false,
		valueField:'id',
		textField:'value',
		onLoadSuccess:function(data){
			groupData=data;
		}
	});
};
function clearForm() {
    $('#addGUserForm').form('clear');
}
//添加用户组组件
function addgroupBtn() {
	$("#addgroupBtn").click(function() {
		groupNum++;
		var str='<tr><td>&nbsp;</td><td><input id="userGroup'+groupNum+'" name="groupIds" prompt="请选择用户组" '
		+ 'validType="checkSelect[\'请选择用户组\',\'请选择用户组\']" style="width: 270px;height:30px">&nbsp;<a href="javascript:void(0)" '
		+'class="btn" onclick="delgroupBtn(this);">删除</a></td></tr>';
		$("table tr").eq($(this).size()-3).after(str);
		$('#userGroup'+groupNum).combobox({
			cache: false,
	        //panelHeight: 'auto',//自动高度适合
			panelHeight: 200,
			editable: false,
			valueField:'id',
			textField:'value'
		});
		$('#userGroup'+groupNum).combobox("loadData", groupData);
	});
}
//删除用户组组件
function delgroupBtn(obj) {
	$(obj).parent().parent().remove();
}
function _ajaxSubmit(){
	var formData = $.getFormData($("#addGUserForm"));
	var arrNum = new Array();
	$.each($('#addGUserForm input[name=groupIds]'),function(i,v){
		var temNo=$(this).val();
		arrNum[arrNum.length] = temNo;
	});
	var nary=arrNum.sort();
	for(var i=0;i<arrNum.length;i++){
		if (nary[i]==nary[i+1]){
			alert("你选择用户组重复，请重新选择!");
			return;
		}
	}
	formData.groupIds=$.ValiDateGroup($("#addGUserForm"),"groupIds");
	
	$.post("../../user/addUser.do", formData, function(result) {
		console.log(result);
		if (result.status == 200) {
			$.messager.alert({
	     	      title:'提示',
	     	      msg:'<div class="content">添加成功!</div>',
	     	      ok:'<i class="i-ok"></i> 确定',
	     	      icon:'info',
	     	     fn:function(){
	     	    	window.location = "guser_list.html";	 
	     	     }
	     	    });
			} else {
				$.messager.alert({
		     	      title:'提示',
		     	      msg:'<div class="content">添加失败!</div>',
		     	      ok:'<i class="i-ok"></i> 确定',
		     	      icon:'error'
		     	    });
			}
	});		
}