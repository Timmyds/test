var funAuthority = getFunAuthority();
var delAuthority=inAuthority('wm-usc-group-del',funAuthority);
var editAuthority=inAuthority('wm-usc-group-edit',funAuthority);
var areaAr=new Array();
var supplierAr=new Array();
$(function() {
	if(funAuthority){
		 $.each(funAuthority,function(index,data){ 
			 $("."+data).show();
		 });
	}
});

$(function() {
	 commoninit();
	init('../../sysGroup/list.do',null);
	$('#search').click(function() {
		search();
	});
	$(".form").tshForm();
	$('#area_tabs').hide();
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
									field : 'groupName',
									title : '用户组名称',
									width : 80,
									align : 'center'
								},
								{
									field : 'remarks',
									title : '用户组说明',
									width : 80,
									align : 'center'
								},
								{
									field : 'userNum',
									title : '用户组人数',
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
											temp+="<a class='btn'  href='javascript:;' onclick='editRolebyId(\""+ row.id + "\")'><i class='i-op i-op-edit'></i>编辑</a>&nbsp;";
										}
   										if(delAuthority){
   											temp+="<a class='btn'  href='javascript:;' onclick='delGroup(\""+ row.id + "\")'><i class='i-op i-op-del-1'></i>删除</a>&nbsp;";
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
	var groupName = $.trim($('#groupName').val());
/*	if((/[^\u4E00-\u9FA5A-Za-z0-9]/g).test(groupName)){
		$.messager.alert({
	  	      title:'提示',
	  	      msg:'<div class="content">角色名称不支持特殊字符搜索!</div>',
	  	      ok:'<i class="i-ok"></i> 确定',
	  	      icon:'error'
	  	    });
			return;
	}*/
	init('../../sysGroup/list.do',{groupName:groupName});
}	

function addPersons(){
	$("#addGroup").dialog("open");
	$("#add_groupName").textbox('setValue','');
	$('#add_remarks').val("");
	$('#add_groupId').val("");
	$(".tree-multiselect").remove();
	initialAreaList();
	initialSupplierList();
	
}

function saveGroup(){
	var text = $("#submit").html();
	if(text=="保存"){
		//提交表单
		var data = {};
		baseValidate = $(".form").tshForm('checkForm');
		if(!baseValidate) return;
		var add_groupName=$.trim($('#add_groupName').val());
		if(!$.trim(add_groupName)){
			$.messager.alert({
	  	      title:'提示',
	  	      msg:'<div class="content">用户组名称不能为空!</div>',
	  	      ok:'<i class="i-ok"></i> 确定',
	  	      icon:'error'
	  	    });
			return;
		}
		var NameReg=/^[\u4E00-\u9FA5^a-z^A-Z^0-9]{2,20}$/;
		if(add_groupName.length>20||add_groupName.length<2){
			$.messager.alert({
		  	      title:'提示',
		  	      msg:'<div class="content">用户组名称长度为2-20位!</div>',
		  	      ok:'<i class="i-ok"></i> 确定',
		  	      icon:'error'
		  	    });
				return;
		}
		if(!NameReg.test($.trim(add_groupName))){
			$.messager.alert({
		  	      title:'提示',
		  	      msg:'<div class="content">用户组名称不能有特殊字符，请重新输入!</div>',
		  	      ok:'<i class="i-ok"></i> 确定',
		  	      icon:'error'
		  	    });
				return;
		}
		if($.trim($('#add_remarks').val()).length>100){
			$.messager.alert({
		  	      title:'提示',
		  	      msg:'<div class="content">用户组说明长度不能超过100位!</div>',
		  	      ok:'<i class="i-ok"></i> 确定',
		  	      icon:'error'
		  	    });
				return;
		}
		if($.trim(add_groupName).length>20){
			$.messager.alert({
		  	      title:'提示',
		  	      msg:'<div class="content">用户组名称不能超过20位!</div>',
		  	      ok:'<i class="i-ok"></i> 确定',
		  	      icon:'error'
		  	    });
				return;
		}
		data.id=$("#add_groupId").val();
	    data.groupName=$.trim($('#add_groupName').val());
	    data.remarks=$.trim($('#add_remarks').val());
	    var authority="";
	   
	    	$('#supplier-select :selected').each(function(i, selected) {
	    		authority +="1_"+$(selected).val()+",";
	    	});
	 	    $('#area-select :selected').each(function(i, selected) {
	 	    	authority +="2_"+$(selected).val()+",";
	 	    });
	 
	    
	    data.authority=authority;
		$("#submit").html("保存中...");
		if(data.id==""){//添加
			delete data.id;
			var url = "../../sysGroup/saveGroup.do";
			$.post(url,data, function(result) {
				$("#submit").html("保存");
				if (result.status == 200) {
					$.messager.alert({
	              	      title:'提示',
	              	      msg:'<div class="content">保存成功!</div>',
	              	      ok:'<i class="i-ok"></i> 确定',
	              	      icon:'info'
	              	    });
					$("#addGroup").dialog("close");
					search();
					//$('#dataGrid').datagrid('reload');
				}else{
					if(result.msg){
						$.messager.alert({
				    	      title:'提示',
				    	      msg:'<div class="content">'+result.msg+'</div>',
				    	      ok:'<i class="i-e"></i> 确定'
				    	    });
					}else{
						$.messager.alert({
				    	      title:'提示',
				    	      msg:'<div class="content">保存失败!</div>',
				    	      ok:'<i class="i-ok"></i> 确定',
				    	      icon:'error'
				    	    });
					}
				}

			});	
		}else{
			//编辑
			editGroup(data);
		}
		
	}
}

function editRolebyId(id){
	$.ajax({
		url:'../../sysGroup/sysGroupById.do',
		type:'post',
		data:{
			'id':id
		},
		async: false,
		success:function(data){
			if(data.status==200&&data.data!=null){
				var result=data.data;
				$("#add_groupId").val(result.id);
				$("#add_groupName").textbox('setValue',result.groupName);
				$("#add_remarks").val(result.remarks);
				loadauthority(result.id);
			}
		},
		dataType:'json'		
	});
	$("#addGroup").dialog("open");
}
function loadauthority(groupById){
	
	var i=0;
	var k=0;
	$.ajax({
		url:'../../sysGroup/findDataAuthority.do',
		type:'post',
		data:{
			'id':groupById
		},
		async: false,
		success:function(data){
			if(data.status==200&&data.data!=null){
				dataAuthority=data.data;
				 areaAr=new Array();
				  supplierAr=new Array();
				 $.each(dataAuthority,function(index,data){ 
					 if(data.bizType==1){
						 i++;
						 supplierAr[i]=data.bizId;
					 }
					 if(data.bizType==2){
						 k++;
						 areaAr[k]=data.bizId;
					 }
				 });
				$(".tree-multiselect").remove();
				initialAreaList(null,areaAr);
				initialSupplierList(null,supplierAr);
			}
		},
		dataType:'json'		
	});
		 

}

function arealoadauthority(){
	//var groupById=$("#add_groupId").val();
	var areaContent=$("#areaContent").val();
	var suppilerContent=$("#supplierContent").val();
/*
	var i=0;
	var k=0;
	$.ajax({
		url:'../../sysGroup/findDataAuthority.do',
		type:'post',
		data:{
			'id':groupById
		},
		success:function(data){
			if(data.status==200&&data.data!=null){
				dataAuthority=data.data;
				 $.each(dataAuthority,function(index,data){ 
					 if(data.bizType==1){
						 i++;
						 supplierAr[i]=data.bizId;
					 }
					 if(data.bizType==2){
						 k++;
						 areaAr[k]=data.bizId;
					 }
				
				 });
				
			}
		},
		dataType:'json'		
	});*/
		$("#searcharea").removeAttr('onclick');
		$(".tree-multiselect").remove();
		console.log($(".tree-multiselect").remove());
		initialAreaList(areaContent,areaAr);
		initialSupplierList(suppilerContent,supplierAr);
}
function supplierloadauthority(){
	//var groupById=$("#add_groupId").val();
	var areaContent=$("#areaContent").val();
	var suppilerContent=$("#supplierContent").val();
	/*var areaAr=new Array();
	var supplierAr=new Array();
	var i=0;
	var k=0;
	$.ajax({
		url:'../../sysGroup/findDataAuthority.do',
		type:'post',
		data:{
			'id':groupById
		},
		success:function(data){
			if(data.status==200&&data.data!=null){
				dataAuthority=data.data;
				 $.each(dataAuthority,function(index,data){ 
					 if(data.bizType==1){
						 i++;
						 supplierAr[i]=data.bizId;
					 }
					 if(data.bizType==2){
						 k++;
						 areaAr[k]=data.bizId;
					 }
				
				 });
				
			}
		},
		dataType:'json'		
	});*/
		$("#searchsupplier").removeAttr('onclick');
		$(".tree-multiselect").remove();
		 initialAreaList(areaContent,areaAr);
		 initialSupplierList(suppilerContent,supplierAr);
	
}

function editGroup(data){
	//提交表单
	if(!$('#add_groupName').val()){
		$.messager.alert({
    	      title:'提示',
    	      msg:'<div class="content">用户组名称不能为空!</div>',
    	      ok:'<i class="i-ok"></i> 确定',
    	      icon:'error'
    	    });
		return;
	}
	if($.trim($('#add_remarks').val()).length>100){
		$.messager.alert({
	  	      title:'提示',
	  	      msg:'<div class="content">用户组说明长度不能超过100位!</div>',
	  	      ok:'<i class="i-ok"></i> 确定',
	  	      icon:'error'
	  	    });
			return;
	}
	$.post("../../sysGroup/updateGroup.do",data, function(result) {
			$("#submit").html("保存");
			if (result.status == 200) {
				$.messager.alert({
		    	      title:'提示',
		    	      msg:'<div class="content">保存成功!</div>',
		    	      ok:'<i class="i-ok"></i> 确定',
		    	      icon:'info'
		    	    });
				$("#addGroup").dialog("close");
				$('#dataGrid').datagrid('reload');
			}else{
				if(result.msg){
					$.messager.alert({
			    	      title:'提示',
			    	      msg:'<div class="content">'+result.msg+'</div>',
			    	      ok:'<i class="i-e"></i> 确定'
			    	    });
				}else{
					$.messager.alert({
			    	      title:'提示',
			    	      msg:'<div class="content">保存失败!</div>',
			    	      ok:'<i class="i-ok"></i> 确定',
			    	      icon:'error'
			    	    });
				}
			
			}
	});		
}
function delGroup(groupId){
	 $.messager.confirm({
	      title:'提示',
	      msg:'<div class="content">是否删除该用户组?</div>',
	      ok:'<i class="i-ok"></i> 确定',
	      cancel:'<i class="i-close"></i> 取消',
	      fn:function(r){
	      	if (r){
	      		$.post("../../sysGroup/delSysGroup.do",{id:groupId}, function(result) {
					if (result.status == 200) {
						$('#dataGrid').datagrid('reload');
						$.messager.alert({
					  	      title:'提示',
					  	      msg:'<div class="content">删除成功！</div>',
					  	      ok:'<i class="i-ok"></i> 确定',
					  	      icon:'info'
					  	    });
					}else {
						if(result.msg){
							$.messager.alert({
					    	      title:'提示',
					    	      msg:'<div class="content">'+result.msg+'</div>',
					    	      ok:'<i class="i-e"></i> 确定'
					    	    });
						}else{
							$.messager.alert({
					    	      title:'提示',
					    	      msg:'<div class="content">删除失败!</div>',
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
//管辖县域
function   initialAreaList(content,dataAuthority){
	
	 $.get("../../sysGroup/sysGroupfindArea.do",{content:content}, function(result) {
			if (result.status == 200) {
				$("#area-select option").remove();
				
				var areaList=result.data;
				if(areaList){
					 $.each(areaList,function(index,data){ 
						 if( $.inArray(data.id, dataAuthority )>-1){
							 if(data.areaName){
							 $("#area-select").append('<option value="'+data.id+'" data-section="'+data.province+'/'+data.city+'" selected="selected">'+data.areaName+'</option>');
							 }
						 }else{
							 if(data.areaName){
							 $("#area-select").append('<option value="'+data.id+'" data-section="'+data.province+'/'+data.city+'" >'+data.areaName+'</option>');
						
							 }
						}
					 });
				}
				$("#area-select").treeMultiselect({enableSelectAll:true,sortable: true});	
				$("#searcharea").attr('onclick',"arealoadauthority()");
			}else {
				if(result.msg){
					$.messager.alert({
			    	      title:'提示',
			    	      msg:'<div class="content">'+result.msg+'</div>',
			    	      ok:'<i class="i-e"></i> 确定'
			    	    });
				}else{
					$.messager.alert({
				  	      title:'提示',
				  	      msg:'<div class="content">获取失败！</div>',
				  	      ok:'<i class="i-ok"></i> 确定',
				  	      icon:'error'
				  	    });
				}
				
			}
		});	
	 
}
//管辖供应商
function   initialSupplierList(content,dataAuthority){
	
	$.get("../../sysGroup/sysGroupfindSupplier.do",{content:content}, function(result) {
		if (result.status == 200) {
			$("#supplier-select option").remove();
			
			var supplierList=result.data;
			
			if(supplierList){
				 $.each(supplierList,function(index,data){ 
					 if( $.inArray(data.id, dataAuthority )>-1){
						 if(data.companyName){
							 $("#supplier-select").append('<option value="'+data.id+'" data-section="'+data.province+'/'+data.city+'" selected="selected">'+data.companyName+'</option>');
						 }
					 }else{
						 if(data.companyName){
							 $("#supplier-select").append('<option value="'+data.id+'" data-section="'+data.province+'/'+data.city+'">'+data.companyName+'</option>');
						 }
					 }
				 });
			}
			$("#supplier-select").treeMultiselect({enableSelectAll: true, sortable: true });
			$("#searchsupplier").attr('onclick',"supplierloadauthority()");
		}else {
			if(result.msg){
				$.messager.alert({
		    	      title:'提示',
		    	      msg:'<div class="content">'+result.msg+'</div>',
		    	      ok:'<i class="i-e"></i> 确定'
		    	    });
			}else{
				$.messager.alert({
			  	      title:'提示',
			  	      msg:'<div class="content">获取失败！</div>',
			  	      ok:'<i class="i-ok"></i> 确定',
			  	      icon:'error'
			  	    });
			}
		}
	});	
	 
}




//时间戳改为日期函数
function formatDate(value,row,index) {
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
