//页面加载时，立即加载数据
var funAuthority = getFunAuthority();

var delAuthority=inAuthority('wm-usc-role-del',funAuthority);
var editAuthority=inAuthority('wm-usc-role-edit',funAuthority);
$(function() {
	if(funAuthority){
		 $.each(funAuthority,function(index,data){ 
			 $("."+data).show();
		 });
	}
});


$(function() {
	init('../../sysRole/list.do',null);
	$('#search').click(function() {
		search();
	});
	$(".form").tshForm();
	//全选
	$("#checkAll").on("change",function(){
		
		if($(this).is(':checked')){
	        $("#addRole .content :input").prop("checked",true);
	    }else{
	        $("#addRole .content :input").prop("checked",false);
	    }
	})
	//控制一级全选
	$(".content").on("change",".row1 > .th input",function(){
		if($(this).is(':checked')){
	        $(this).closest(".row1").find("input").prop("checked",true);
	    }else{
	    		$("#checkAll").prop("checked",false);
	         $(this).closest(".row1").find("input").prop("checked",false);
	    }
	})
	//控制二级全选
	$(".content").on("change",".row2 .th input",function(){
		if($(this).is(':checked')){
	        $(this).closest(".row2").find("input").prop("checked",true);
	        $(this).closest(".row1").find("> .th  input").prop("checked",true);
	    }else{
	    	$("#checkAll").prop("checked",false);
	         $(this).closest(".row2").find("input").prop("checked",false);
	    }
	})
	//控制三级选择
	$(".content").on("change",".row3 input",function(){
		if($(this).is(':checked')){
			$(this).closest(".row1").find("> .th  input").prop("checked",true);
	        $(this).closest(".row2").find(".th input").prop("checked",true);
	    }else{
	    	$("#checkAll").prop("checked",false);
	    }
	})

});
function init(url,queryParams) {
	$('#dataGrid')
			.datagrid(
					{
						url : url,
						queryParams:queryParams,
						rownumbers : true, // 行号
						singleSelect : true, // 是否单选
						pagination : true, // 分页控件
						pageSize:15,
						pageList : [15 ,30,50],
						autoRowHeight : false,
						fit : true,
						striped : false, // 设置为true将交替显示行背景
						fitColumns : true,
						nowrap : false,
						remotesort : false,
						checkOnSelect : false,
						selectOnCheck : false,
						method : "GET", // 请求数据的方法
						loadMsg : '数据加载中,请稍候......',
						idField : 'id',
						rowStyler : function(index, row) {
							return 'font-size: 20px;';
						},
						columns : [ [	
								{
									field : 'roleName',
									title : '角色名称',
									width : 150,
									align : 'center'
								},
								{
									field : 'remarks',
									title : '角色说明',
									width : 150,
									align : 'center'
								},
								{
									field : 'userNum',
									title : '角色绑定用户人数',
									width : 80,
									align : 'center'
								},
								{
									field : 'createDate',
									title : '创建时间',
									width : 80,
									align : 'center',
									formatter:formatDate
								} ,								
								{
									field : 'opts',
									title : '操作',
									width : 130,
									align : 'center',
									formatter : function(value, row, index) {
										var temp="";
										if(editAuthority){
											temp +=   "<a class='btn' href='javascript:;'  onclick='editRolebyId(\""+ row.id + "\")'><i class='i-op i-op-edit'></i>编辑</a>&nbsp;";
										}
										if(delAuthority&&row.bizId>-2){
												temp +=   "<a class='btn' href='javascript:;'  onclick='delRole(\""+ row.id + "\")'><i class='i-op i-op-del-1'></i>删除</a>&nbsp;";
										}
										return temp;
									}
								} ] ],
						onLoadError : function() {
							$.messager.alert({
			              	      title:'提示',
			              	      msg:'<div class="content">获取信息失败...请联系管理员!</div>',
			              	      ok:'<i class="i-ok"></i> 确定',
			              	      icon:'warning'
			              	    });
						},
						onLoadSuccess : function(data) {
							if(data==null||data.total==0){
			            		$.messager.alert({
			               	      title:'提示',
			               	      msg:'<div class="content">当前没有查询到记录!</div>',
			               	      ok:'<i class="i-ok"></i> 确定',
			               	      icon:'warning'
			               	    });
			            	}
				
						},
						toolbar : '#tb'
					});
} 
function search() {
	var roleName = $.trim($('#roleName').textbox('getValue'));
/*	if((/[^\u4E00-\u9FA5A-Za-z0-9]/g).test(roleName)){
		$.messager.alert({
	  	      title:'提示',
	  	      msg:'<div class="content">角色名称不支持特殊字符搜索!</div>',
	  	      ok:'<i class="i-ok"></i> 确定',
	  	      icon:'error'
	  	    });
			return;
	}*/
	init('../../sysRole/list.do',{roleName:roleName});
}	

function addPersons(){
	$("#addRole").dialog("open");
	$("#add_roleName").textbox('setValue','');
	$('#add_remarks').val("");
	$('#add_roleId').val("");
	var initAuthority = getInitAuthority();
	var tplInitAuthority = renderInitAuthority(initAuthority);
	$("#addSysRoleForm .content").html(tplInitAuthority);
}

function saveRole(){
	var text = $("#submit").html();
	if(text=="保存"){
		//提交表单
		var data = {};
		baseValidate = $(".form").tshForm('checkForm');
		if(!baseValidate) return;
		if($.trim($('#add_remarks').val()).length>100){
			$.messager.alert({
		  	      title:'提示',
		  	      msg:'<div class="content">角色说明长度不能超过100位!</div>',
		  	      ok:'<i class="i-ok"></i> 确定',
		  	      icon:'error'
		  	    });
				return;
		}
		if($.trim($('#add_roleName').val()).length>20||$.trim($('#add_roleName').val()).length<2){
			$.messager.alert({
		  	      title:'提示',
		  	      msg:'<div class="content">角色名称长度在2~20位之间!</div>',
		  	      ok:'<i class="i-ok"></i> 确定',
		  	      icon:'error'
		  	    });
				return;
		}
		if((/[^\u4E00-\u9FA5A-Za-z0-9]/g).test($.trim($('#add_roleName').textbox('getValue')))){
			$.messager.alert({
		  	      title:'提示',
		  	      msg:'<div class="content">角色名称不支持特殊字符!</div>',
		  	      ok:'<i class="i-ok"></i> 确定',
		  	      icon:'error'
		  	    });
				return;
		}
		authority = getAuthority("#addSysRoleForm .content");//获取权限
		data.id=$("#add_roleId").val();
	    data.roleName=$.trim($('#add_roleName').textbox('getValue'));
	    data.remarks=$.trim($('#add_remarks').val());
	    data.authority = authority;
		if(data.id==""){//添加
			$("#submit").html("保存中...");
			delete data.id;
			var url = "../../sysRole/saveRole.do";
			$.post(url,data, function(result) {
				$("#submit").html("保存");
				if (result.status == 200) {
					$.messager.alert({
	              	      title:'提示',
	              	      msg:'<div class="content">保存成功</div>',
	              	      ok:'<i class="i-ok"></i> 确定',
	              	      icon:'info'
	              	    });
					$("#addRole").dialog("close");
					search();
					//$('#dataGrid').datagrid('reload');
				}else{
					if(result.msg){
					$.messager.alert({
	              	      title:'提示',
	              	      msg:'<div class="content">'+result.msg+'</div>',
	              	      ok:'<i class="i-ok"></i> 确定'
	              	    });
					}else{
						$.messager.alert({
		              	      title:'提示',
		              	      msg:'<div class="content">保存失败</div>',
		              	      ok:'<i class="i-ok"></i> 确定',
		              	      icon:'error'
		              	    });
					}
				}
			});	
		}else{
			//编辑
			editRole(data);
		}
		
	}
}

function editRolebyId(id){
	$.ajax({
		url:'../../sysRole/sysRoleById.do',
		type:'post',
		data:{
			'id':id
		},
		success:function(data){
			if(data.status==200&&data.data!=null){
				var result=data.data;
				$("#add_roleId").val(result.id);
				$("#add_roleName").textbox('setValue',result.roleName);
				$("#add_remarks").val(result.remarks);
			}
		},
		dataType:'json'		
	});
	var initAuthority = getInitAuthority();
	var tplInitAuthority = renderInitAuthority(initAuthority);
	$("#addRole").dialog("open");
	$("#addSysRoleForm .content").html(tplInitAuthority);
	//查询已有权限，并默认勾选
	getAuthorityMenu(id);
}

//获取已有权限
function getAuthorityMenu(id){
	$.ajax({
			url:"../../sysRole/authorityList.do",
			traditional:true,
			type:'post',
			async:false,
			data:{roleId:id},
			cache:false,
			success:function(data){
				if(null!=data&&data.length>0){
					for (var i = 0; i < data.length; i++) {
						$("#"+data[i]).attr("checked",true);
					}
				}
			},error:function(data){
				$.messager.alert({
            	      title:'提示',
            	      msg:'<div class="content">操作失败!</div>',
            	      ok:'<i class="i-ok"></i> 确定',
            	      icon:'error'
            	    });
			}
		});
}

function editRole(data){
	//提交表单
	if(!$.trim($('#add_roleName').textbox('getValue'))){
		$.messager.alert({
  	      title:'提示',
  	      msg:'<div class="content">角色名称不能为空!</div>',
  	      ok:'<i class="i-ok"></i> 确定',
  	      icon:'error'
  	    });
		return;
	}
	if($.trim($('#add_remarks').val()).length>100){
		$.messager.alert({
	  	      title:'提示',
	  	      msg:'<div class="content">角色说明长度不能超过100位!</div>',
	  	      ok:'<i class="i-ok"></i> 确定',
	  	      icon:'error'
	  	    });
			return;
	}
	$("#submit").html("保存中...");
	$.post("../../sysRole/updateRole.do",data, function(result) {
			$("#submit").html("保存");
			if (result.status == 200) {
				$.messager.alert({
			  	      title:'提示',
			  	      msg:'<div class="content">保存成功!</div>',
			  	      ok:'<i class="i-ok"></i> 确定',
			  	      icon:'info'
			  	    });
				$("#addRole").dialog("close");
				$('#dataGrid').datagrid('reload');
			}else if (result.status == 5000) {
				$.messager.alert({
			  	      title:'提示',
			  	      msg:'<div class="content">'+result.msg+'</div>',
			  	      ok:'<i class="i-ok"></i> 确定'
			  	    });
			}else {
				$.messager.alert({
			  	      title:'提示',
			  	      msg:'<div class="content">保存失败!</div>',
			  	      ok:'<i class="i-ok"></i> 确定',
			  	      icon:'error'
			  	    });
			}
	});		
}
function delRole(roleId){
	 $.messager.confirm({
	      title:'提示',
	      msg:'<div class="content">是否删除该角色?</div>',
	      ok:'<i class="i-ok"></i> 确定',
	      cancel:'<i class="i-close"></i> 取消',
	      fn:function(r){
	      	if (r){
	      		$.post("../../sysRole/delSysRole.do",{id:roleId}, function(result) {
					if (result.status == 200) {
						$('#dataGrid').datagrid('reload');
						$.messager.alert({
					  	      title:'提示',
					  	      msg:'<div class="content">删除成功！</div>',
					  	      ok:'<i class="i-ok"></i> 确定',
					  	      icon:'info'
					  	    });
					}else{
						if(result.msg){
							$.messager.alert({
						  	      title:'提示',
						  	      msg:'<div class="content">'+result.msg+'</div>',
						  	      ok:'<i class="i-ok"></i> 确定'
						  	    });
							}else {
								$.messager.alert({
							  	      title:'提示',
							  	      msg:'<div class="content">删除失败！</div>',
							  	      ok:'<i class="i-ok"></i> 确定',
							  	      icon:'error'
							  	    });
							}
						}
				});	
	      	}
	      }
	    });
}


//时间戳改为日期函数
function formatDate(value,row,index) {
	//console.log(value);
	var valueNew=new Date(value);
	var year = valueNew.getFullYear();
	var month = valueNew.getMonth() + 1;
	var date = valueNew.getDate();
	var hour = valueNew.getHours();
	var minute = valueNew.getMinutes();
	var second = valueNew.getSeconds();
	var monthStr = "";
	monthStr = month < 10?"0"+month:month;
	dateStr = date < 10?"0"+date:date;
	hourStr = hour < 10?"0"+hour:hour;
	minuteStr = minute < 10?"0"+minute:minute;
	secondStr = second < 10?"0"+second:second;
	
	return year + "-" + monthStr + "-" + dateStr + "   " + hourStr + ":" + minuteStr + ":" + secondStr;
}
