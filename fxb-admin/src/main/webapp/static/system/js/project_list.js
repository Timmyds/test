function fixWidth(percent)     
{     
    return (document.body.clientWidth - 5) * percent ;      
} 


function rowformater(value,row,index){
	var temp='';
	if(editAuthority){
		temp += '<a  onclick="showEditDialog(\''+row.id+'\',\''+row.sysCode+'\',\''+row.sysName+'\',\''+row.domain+'\');" name="editBtn"    class="btn"><i class="i-op i-op-edit"></i>修改信息</a>&emsp; ';
	}
	if(delAuthority){
		temp += '<a class="btn"  name="delBtn"   onclick="delSystem(\''+row.id+'\');"><i class="i-op i-op-del-1"></i>删除</a>&emsp;';
	}
	return temp;
}
function orderinit(url){
	var columns  = [[
	                 {field:'sysName',title:'系统名称',width:fixWidth(0.3),align:'center'},
	                 {field:'sysCode',title:'系统编码',width:fixWidth(0.13),align:'center'},
	                 {field:'domain',title:'系统域名',width:fixWidth(0.2),align:'center'},
	                 {field:'opt',title:'操作',width:fixWidth(0.25),align:'center', formatter:function(value, row, index) {
	                	 return rowformater(value,row,index);
	                 }}
	                ]];
	
	$('#dataGrid').datagrid({
		url: url,
		rownumbers: true, //行号
		singleSelect: true, //是否单选
		pagination: false, //分页控件
		autoRowHeight: false,
		fit: true,
		striped: false, //设置为true将交替显示行背景
		fitColumns: true,
		nowrap: false,
		remotesort: false,
		checkOnSelect: false,
		method: "get", //请求数据的方法
		loadMsg: '数据加载中,请稍候......',
		idField:'id',
		rowStyler: function(index,row){
			return 'font-size: 20px;';
		},
		columns:columns,
		onLoadError: function() {
			$.messager.alert('操作提示', '获取信息失败...请联系管理员!', 'error');
		},
		onLoadSuccess:function(data){
			
			if(data==null||data.total==0){
        		$.messager.alert({
           	      title:'提示',
           	      msg:'<div class="content">当前没有查询到记录!</div>',
           	      ok:'<i class="i-ok"></i> 确定',
           	      icon:'warning'
           	    });
        	}
			/*$('.datagrid').undelegate('a[name=editBtn]','click',showEditDialog);
			$('.datagrid').undelegate('a[name=delBtn]','click',delSystem);
			
			$('.datagrid').delegate('a[name=editBtn]','click',showEditDialog);
			$('.datagrid').delegate('a[name=delBtn]','click',delSystem);*/
		},
        toolbar:'#tb'
	});
	$("#search").bind('keypress',function(e){
		e = e||event;  
		if(e.keyCode=='13'){
			doSearch();
		}
	});
	$("#tradeSelect").combobox({
		onChange:function(){
			doSearch();
		}
	});
}
//搜索
var doSearch = function (){
	orderinit("../../systemProject/findProject.do");
};
//页面加载时，立即加载数据
var funAuthority = getFunAuthority();
var editAuthority=inAuthority('wm-usc-project-edit',funAuthority);
var delAuthority=inAuthority('wm-usc-project-del',funAuthority);
$(function() {
	if(funAuthority){
		 $.each(funAuthority,function(index,data){ 
			 $("."+data).show();
		 });
	}

	
	var showSystemDialog = function(){
		var html = $('#system-dialog-template').html();
		$(html).dialog({  
		    title: '新增系统信息',  
		    width: 450,  
		    height: 275,  
		    closed: false,  
		    cache: false,  
		    //href: 'get_content.php',  
		    modal: true,
		    buttons: [{
				text:'提交',
				iconCls:'icon-ok',
				handler:function(){
					var url = "../../systemProject/addProject.do";
					addSystem(url);
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){
					$('#system-dialog').dialog('close');
				}
			}],
			onClose:function(){
				$('#system-dialog').dialog('destroy');
		    }
		});  
	};
	
	
	var initUI = function(){
		$('#form').delegate('#addSystem','click',showSystemDialog);
	}
	initUI();
	doSearch();
});

var delSystem = function(value){
	$.messager.confirm({
	      title:'删除系统信息',
	      msg:'<div class="content">你确定要删除该系统信息吗?</div>',
	      ok:'<i class="i-ok"></i> 确定',
	      cancel:'<i class="i-close"></i> 取消',
	      fn:function(r){
	      	if (r){
	      		var url = '../../systemProject/delSystem.do';
	    		$.ajax({
	    			url:url,
	    			traditional:true,
	    			type:'post',
	    			async:true,
	    			data:"id="+value,
	    			cache:false,
	    			success:function(data){
	    				if(data.status == 200){
	    					$('#dataGrid').datagrid('reload'); 
	    					$.messager.alert({
						  	      title:'提示',
						  	      msg:'<div class="content">删除成功！</div>',
						  	      ok:'<i class="i-ok"></i> 确定',
						  	      icon:'info'
						  	    });
	    				}else{
	    					$.messager.alert({
						  	      title:'提示',
						  	      msg:'<div class="content">'+data.msg+'</div>',
						  	      ok:'<i class="i-ok"></i> 确定',
						  	      icon:'error'
						  	    });
						}
	    			},error:function(data){
	    				$.messager.alert({
					  	      title:'操作提示',
					  	      msg:'<div class="content">操作失败!</div>',
					  	      ok:'<i class="i-ok"></i> 确定',
					  	      icon:'error'
					  	    });
	    			}
	    		});
	            return true;  
	      	}
	      }
	    });
	    return false;  
	}
var showEditDialog = function(id,sysCode,sysName,domain){
		var html = $('#system-dialog-template').html();
		$(html).dialog({  
		    title: '修改系统名称',  
		    width: 450,  
		    height: 275,  
		    closed: false,  
		    cache: false,  
		    //href: 'get_content.php',  
		    modal: true,
		    buttons: [{
				text:'提交',
				iconCls:'icon-ok',
				handler:function(){
					var url = "../../systemProject/updateSystem.do";
					addSystem(url,id);
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){
					$('#system-dialog').dialog('close');
				}
			}],
			onOpen:function(){
				//$('#system-dialog input[name=sysCode]').attr('readOnly','true');
				$('#system-dialog input[name=sysName]').val(sysName);
				$('#system-dialog input[name=sysCode]').val(sysCode);
				$('#system-dialog input[name=domain]').val(domain);
				$('#sysType-content').hide();
		    },
			onClose:function(){
				$('#system-dialog').dialog('destroy');
		    }
		});  
	}
	var addSystem = function(url,id){
		var sysName = $.trim($('#system-dialog input[name=sysName]').val());
		var sysCode = $.trim($('#system-dialog input[name=sysCode]').val());
		var domain = $.trim($('#system-dialog input[name=domain]').val());
		
		if(sysName == ''||sysName.length<2||sysName.length>20){
			$.messager.alert({
		  	      title:'操作提示',
		  	      msg:'<div class="content">系统名称不能为空且在2~20位!</div>',
		  	      ok:'<i class="i-ok"></i> 确定',
		  	      icon:'error'
		  	    });
			return false;
		}
		if(sysCode == ''){
			$.messager.alert({
		  	      title:'操作提示',
		  	      msg:'<div class="content">系统编码不能为空!</div>',
		  	      ok:'<i class="i-ok"></i> 确定',
		  	      icon:'error'
		  	    });
			return false;
		}
		if(domain == ''){
			$.messager.alert({
		  	      title:'操作提示',
		  	      msg:'<div class="content">系统编码不能为空!</div>',
		  	      ok:'<i class="i-ok"></i> 确定',
		  	      icon:'error'
		  	    });
			return false;
		}
		var dataModel = {};
		dataModel.id =id;
		dataModel.sysName =sysName;
		dataModel.sysCode = sysCode;
		dataModel.domain = domain;
		$.ajax({
			url:url,
			traditional:true,
			type:'POST',
			async:true,
			data:dataModel,
			contentType: "application/x-www-form-urlencoded",
			cache:false,
			success:function(data){
				if(data.status == 200){
					$('#system-dialog').dialog('close');
					$('#system-dialog').dialog('destroy');
					doSearch();
					$.messager.alert({
				  	      title:'提示',
				  	      msg:'<div class="content">保存成功！</div>',
				  	      ok:'<i class="i-ok"></i> 确定',
				  	      icon:'info'
				  	    });
				}else{
					if(data.msg){
					$.messager.alert({
				  	      title:'提示',
				  	      msg:'<div class="content">'+data.msg+'</div>',
				  	      ok:'<i class="i-ok"></i> 确定',
				  	      icon:'error'
				  	    });
					}else{
						$.messager.alert({
					  	      title:'操作提示',
					  	      msg:'<div class="content">保存失败！</div>',
					  	      ok:'<i class="i-ok"></i> 确定',
					  	      icon:'error'
					  	    });
					}
				}
			},error:function(data){
				$.messager.alert({
			  	      title:'操作提示',
			  	      msg:'<div class="content">操作失败！</div>',
			  	      ok:'<i class="i-ok"></i> 确定',
			  	      icon:'error'
			  	    });
			}
		});
	}