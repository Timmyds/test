//页面加载时，立即加载数据

var userId=null;
var groupNum=1;
$(function() {
	commoninit();
	$("#loginName").textbox({readonly:true});
	$("#loginPwd").textbox({readonly:true});
	getUserRole();
	$("#addgroupBtn").click(function() {
		addgroupBtn();
	});
	if(userInfo.roleType==2||userInfo.roleType==3){//除去平台和县域 其他类型账号无用户组
		$("#user_group").show();
	}
});



function loadUser(){	
	//获取用户信息
	userId = acceptParam("userId");
	$.get("../../user/findUser.do?userId="+userId,function(data){
		if(data.status=200&&data.data!=null){
			
			if(userInfo.roleType==1){//超级管理员
				$('#updateGUserForm').form('load',{
					id: data.data.id,
					loginName: data.data.loginName,
					loginPwd: data.data.loginPwd,
					userName: data.data.userName,
					phone: data.data.phone,
					status: data.data.status+"",
					roleId: data.data.roleId==null?"-1":data.data.roleType+""
				});
			}else{
				$('#updateGUserForm').form('load',{
					id: data.data.id,
					loginName: data.data.loginName,
					loginPwd: data.data.loginPwd,
					userName: data.data.userName,
					phone: data.data.phone,
					status: data.data.status+"",
					roleId: data.data.roleId==null?"-1":data.data.roleId+""
				});
			}
			
		/*	if(data.data.roleId){
				$('#roleId').combobox("setValue", data.data.roleId+"");
			}*/
			
			if(data.data.groupIds!=null){
				var groupIds=data.data.groupIds.split(",");
				$('#userGroup'+groupNum).combobox("setValue", groupIds[0]);
				if(groupIds.length>1){
					for(var i=1;i<groupIds.length;i++){
						addgroupBtn();
						$('#userGroup'+groupNum).combobox("setValue", groupIds[i]);
					}
				}
			}else{
				$('#userGroup'+groupNum).combobox("setValue", "-1");
			}
		}else{
			$.messager.alert({
      	      title:'提示',
      	      msg:'<div class="content">获取信息失败...请联系管理员!</div>',
      	      ok:'<i class="i-ok"></i> 确定',
      	      icon:'warning'
      	    });
		}
	},"JSON");
	
	$("input", $("#userName").next("span")).bind("keyup",function(e){
		
	});
	
	
	
	
	$("#submit").click(
			function() {	
				//if ($("#updateGUserForm").form('validate')) {
		
					var logReg=/^[0-9a-zA-Z][\_0-9a-zA-Z|0-9a-zA-Z]{1,19}$/;
					var nameReg=/^[\u4E00-\u9FA5\w]{2,20}$/;
					/*var pwdReg=/^[\S]{6,16}$/;*/
					var phoneReg=/^(13[0-9]|14[0-9]|15[0-9]|18[0-9]|17[0-9])\d{8}$/;
					//提交表单
				    var roleId =$('#roleId').combobox('getValue');
					var status =$('#status').combobox('getValue');
				
					 if(!logReg.test($.trim($('#loginName').val()))){
						 $.messager.alert({
		              	      title:'提示',
		              	      msg:'<div class="content">账号名称无效，请重新输入</div>',
		              	      ok:'<i class="i-ok"></i> 确定',
		              	      icon:'error'
		              	    });
				     }else if(!nameReg.test($.trim($('#userName').val()))){
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
					 }else if(status==null||status=='null'||status=='-1'){
						 $.messager.alert({
		              	      title:'提示',
		              	      msg:'<div class="content">账号状态不能为空</div>',
		              	      ok:'<i class="i-ok"></i> 确定',
		              	      icon:'error'
		              	    });
					 }else if(roleId==null||roleId=='null'||roleId==''||roleId=='-1'){
						 $.messager.alert({
		              	      title:'提示',
		              	      msg:'<div class="content">角色类型不能为空</div>',
		              	      ok:'<i class="i-ok"></i> 确定',
		              	      icon:'error'
		              	    });
					 }else{
						$("#submit").hide();
						_ajaxSubmit();
					}				
				//}
		});
}

//添加用户组组件
function addgroupBtn() {
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
}
//删除用户组组件
function delgroupBtn(obj) {
	$(obj).parent().parent().remove();
}
//获取角色类型
function getUserRole(){
	$('#roleId').combobox({
		url:'../../sysRole/sysRoleVo.do',
		method:'post',
		editable: false,
		panelHeight: 200,
		valueField:'id',
		textField:'value'     
		});
	$('#userGroup1').combobox({
		url:'../../sysGroup/groupSelectList.do',
		cache: false,
		panelHeight: 200,
		method:'post',
		editable: false,
		valueField:'id',
		textField:'value',
		onLoadSuccess:function(data){
			groupData=data;
		}
	});
	loadUser();
	if(userInfo.roleType==1){//超级管理员
		$("#roleId").combobox({disabled:true});
	}
}

function _ajaxSubmit(){
	var formData = $.getFormData($("#updateGUserForm"));
	var arrNum = new Array();
	$.each($('#updateGUserForm input[name=groupIds]'),function(i,v){
		var temNo=$(this).val();
		arrNum[arrNum.length] = temNo;
	});
	var nary=arrNum.sort();
	for(var i=0;i<arrNum.length;i++){
		if (nary[i]==nary[i+1]){
			$.messager.alert({
        	      title:'提示',
        	      msg:'<div class="content">你选择用户组重复，请重新选择!</div>',
        	      ok:'<i class="i-ok"></i> 确定',
        	      icon:'error'
        	    });
			$("#submit").show();
			return;
		}
	}
	formData.groupIds=$.ValiDateGroup($("#updateGUserForm"),"groupIds");
	$.post("../../user/updateUser.do", formData, function(result) {
			if (result.status == 200) {
				$.messager.alert({
		      	      title:'提示',
		      	      msg:'<div class="content">修改成功!</div>',
		      	      ok:'<i class="i-ok"></i> 确定',
		      	      icon:'info',
		      	      fn:function(){
		      	    	window.location = "guser_list.html";
		      	      }
		      	    });
			} else {
				$.messager.alert({
		      	      title:'提示',
		      	      msg:'<div class="content">修改失败!</div>',
		      	      ok:'<i class="i-ok"></i> 确定',
		      	      icon:'error'
		      	    });
			}
			$("#submit").show();
	});		
}