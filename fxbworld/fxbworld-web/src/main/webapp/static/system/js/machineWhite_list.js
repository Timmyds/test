

function fixWidth(percent)     
{     
    return (document.body.clientWidth - 5) * percent ;      
} 


function rowformater(value,row,index){
	var temp='';
	if(editAuthority){
		temp += '<a  onclick="showEditDialog(\''+row.id+'\',\''+row.machineNo+'\',\''+row.remarks+'\');" name="editBtn"    class="btn"><i class="i-op i-op-edit"></i>修改</a>&emsp; ';
	}
	if(delAuthority){
		temp += '<a class="btn"  name="delBtn"   onclick="delSystem(\''+row.id+'\',\''+row.machineNo+'\');"><i class="i-op i-op-del-1"></i>删除</a>&emsp;';
	}
	return temp;
}
function orderinit(url){
	var columns  = [[
	                 {field:'machineNo',title:'序列号',width:fixWidth(0.3),align:'center'},
	                 {field:'remarks',title:'备注',width:fixWidth(0.13),align:'center'},
	                 {field:'createDate',title:'添加时间',width:fixWidth(0.2),align:'center',
	                	 formatter : function(value, row, index) {
	             			if (null != value) {
	             				var date = new Date(value);
	             				return date.pattern("yyyy-MM-dd HH:mm:ss");
	             			} else {
	             				value = '--';
	             				return value;
	             			}
	             		}},
	             	{field:'operateUserName',title:'操作人',width:fixWidth(0.13),align:'center'},
	                 {field:'opt',title:'操作',width:fixWidth(0.25),align:'center', formatter:function(value, row, index) {
	                	 return rowformater(value,row,index);
	                 }}
	                ]];
	
	$('#dataGrid').datagrid({
		url: url,
		rownumbers: true, //行号
		singleSelect: true, //是否单选
		pagination: true, //分页控件
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
	 if ($("#tb").form('validate')) {
	     var machineNo = $.trim($('#machineNo').textbox('getValue'));
	     if (machineNo) {
	    	 machineNo = decToHex(machineNo);
	     }
	     orderinit("../../machineWhite/findMachineWhite.do?machineNo="+machineNo);
	 }
};
//页面加载时，立即加载数据
var funAuthority = getFunAuthority();
var editAuthority=inAuthority('wm-usc-machine-edit',funAuthority);
var delAuthority=inAuthority('wm-usc-machine-del',funAuthority);
$(function() {
	if(funAuthority){
		 $.each(funAuthority,function(index,data){ 
			 $("."+data).show();
		 });
	}

	$('#search').click(function() {
		doSearch();
	});
	
	var showSystemDialog = function(){
		var html = $('#system-dialog-template').html();
		$(html).dialog({  
		    title: '添加系列号',  
		    width: 450,  
		    height: 240,  
		    closed: false,  
		    cache: false,  
		    //href: 'get_content.php',  
		    modal: true,
		    buttons: [{
				text:'提交',
				iconCls:'icon-ok',
				handler:function(){
					var url = "../../machineWhite/addMachineWhite.do";
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
	};
	initUI();
	doSearch();
});

var delSystem = function(value,machineNo){
	$.messager.confirm({
	      title:'删除系列号信息',
	      msg:'<div class="content">你确定要删除系列号'+machineNo+'吗?</div>',
	      ok:'<i class="i-ok"></i> 确定',
	      cancel:'<i class="i-close"></i> 取消',
	      fn:function(r){
	      	if (r){
	      		var url = '../../machineWhite/delMachineWhite.do';
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
	};
var showEditDialog = function(id,machineNo,remarks){
		var html = $('#system-dialog-template').html();
		$(html).dialog({  
		    title: '修改系列号',  
		    width: 450,  
		    height: 240,   
		    closed: false,  
		    cache: false,  
		    //href: 'get_content.php',  
		    modal: true,
		    buttons: [{
				text:'提交',
				iconCls:'icon-ok',
				handler:function(){
					var url = "../../machineWhite/updateMachineWhite.do";
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
				$('#system-dialog input[name=machineNo]').val(machineNo);
				$('#system-dialog input[name=remarks]').val(remarks);
				$('#sysType-content').hide();
		    },
			onClose:function(){
				$('#system-dialog').dialog('destroy');
		    }
		});  
	};
	var addSystem = function(url,id){
		var machineNo = $.trim($('#system-dialog input[name=machineNo]').val());
		var remarks = $.trim($('#system-dialog input[name=remarks]').val());
		var re = /^[0-9a-zA-Z]{5,20}$/gi;//只能输入英文字母、数字
		if(!re.test(machineNo)){
			$.messager.alert({
		  	      title:'操作提示',
		  	      msg:'<div class="content">序列号由5~20位字母及数字组成!</div>',
		  	      ok:'<i class="i-ok"></i> 确定',
		  	      icon:'error'
		  	    });
			return false;
		}
		if(machineNo == ''||machineNo.length<5||machineNo.length>20){
			
		}
		
		var dataModel = {};
		if(id){
			dataModel.id =id;
		}
		dataModel.machineNo =machineNo;
		dataModel.remarks=remarks;
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
	};