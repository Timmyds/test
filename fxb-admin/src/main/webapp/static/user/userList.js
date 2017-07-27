var funAuthority = getFunAuthority();
var viewsAuthority=inAuthority('wm-usc-user-views',funAuthority);
var editAuthority=inAuthority('wm-usc-user-edit',funAuthority);
var startorstopAuthority=inAuthority('wm-usc-user-stop-start',funAuthority);
var resetpwdAuthority=inAuthority('wm-usc-user-resetpwd',funAuthority);
var authorizationAuthority=inAuthority('wm-usc-user-authorization',funAuthority);
$(function() {
	commoninit();
	if(funAuthority){
		 $.each(funAuthority,function(index,data){ 
			 $("."+data).show();
		 });
		 
	}
	if(userInfo.roleType==2||userInfo.roleType==3){//除去平台和县域 其他类型账号无用户组
		$("#seach_userGroup").show();
	}

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
    
    
    //页面加载时，立即加载数据
Date.prototype.pattern = function(fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份           
        "d+": this.getDate(), //日           
        "h+": this.getHours() % 12 == 0 ? 12 : this.getHours() % 12, //小时           
        "H+": this.getHours(), //小时           
        "m+": this.getMinutes(), //分           
        "s+": this.getSeconds(), //秒           
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度           
        "S": this.getMilliseconds() //毫秒           
    };
    var week = {
        "0": "/u65e5",
        "1": "/u4e00",
        "2": "/u4e8c",
        "3": "/u4e09",
        "4": "/u56db",
        "5": "/u4e94",
        "6": "/u516d"
    };
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    if (/(E+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, ((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "/u661f/u671f" : "/u5468") : "") + week[this.getDay() + ""]);
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return fmt;
};


$(function() {
    //加载下拉框
	getSelectVo();
    var status=-1;
    init('../../user/getPageForGUserInfo.do?randnum=' + Math.random() + '&status='+status, null);
    $('#search').click(function() {
        search();
    });
});

function getSelectVo() {
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
	$('#userGroup').combobox({
		url:'../../sysGroup/groupSelectList.do',
		cache: false,
        //panelHeight: 'auto',//自动高度适合
		panelHeight: 200,
		method:'post',
		editable: false,
		valueField:'id',
		textField:'value'     
		});
}


function init(url, queryParams) {
	var columns;
	if(userInfo.roleType==2||userInfo.roleType==3){//除去平台和县域 其他类型账号无用户组
		columns  = [ [{field: 'loginName', title: '账号名', width: 100, align: 'center'},
	                  {field: 'userName',title: '用户名称',width: 100, align: 'center'}, 
	                  {field: 'createDate',title: '创建时间',width: 100,align: 'center',
	                      formatter: function(value, row, index) {
	                          if (null != value) {
	                              var date = new Date(value);
	                              return date.pattern("yyyy-MM-dd hh:mm:ss");
	                          } else {
	                              value = '--';
	                              return value;
	                          }
	                      }
	                  }, 
	                  {field: 'roleName', title: '角色', width: 80,align: 'center'}, 
	                  { field: 'groupName', title: '用户组',width: 80,align: 'center'}, 
	                 
	              	  { field: 'status',title: '状态',width: 80,align: 'center',
	  	                formatter: function(value, row, index) {
	  	                    var statusContent = "";
	  	                    if (value == 1) {
	  	                        statusContent += "<span class='goodsStatus'>已启用</span>";
	  	                    } else if (value == 0) {
	  	                        statusContent += "<span style='color: red;' class='goodsStatus'>已停用</span>";
	  	                    } else {
	  	                        statusContent += "<span class='goodsStatus'>未知</span>";
	  	                    }
	  	                    return statusContent;
	  	                }
	                  },
	                  {field: 'opts',title: '操作', width: 250,align: 'center',
	                	  formatter: function(value, row, index) {
	                		  var temp ="";
		                      	if(viewsAuthority){
		                      	temp += "<a class='btn' href='javascript:;'   onclick='viewsUser(\"" + row.id + "\")'><i class='i-op i-op-view'></i>查看</a>&nbsp;";
		                      	}
		                      	if(editAuthority){
		                      	temp += "<a class='btn' href='javascript:;'  onclick='viewGuser(\"" + row.id + "\")'><i class='i-op i-op-edit'></i>编辑</a>&nbsp;";
		                      	}
		                      	if(startorstopAuthority){
		                      	if (row.status == 0) {
		                      		temp += "<a class='btn' href='javascript:;'   onclick='frozenGUser(\"" + row.id + "\"," + '1,' + index + ")'><i class='i-op i-op-unlock'></i>启用</a>&nbsp;";
		                          } else {
		                          	temp += "<a class='btn' href='javascript:;'   onclick='frozenGUser(\"" + row.id + "\"," + '0,' + index + ")'><i class='i-op i-op-lock'></i>停用</a>&nbsp;";
		                          }
		                      	}
		                    	if(resetpwdAuthority){
		                      	temp += "<a class='btn' href='javascript:;'     onclick='resetPwd("+row.id+");'><i class='i-op i-op-set'></i>重置密码</a>&nbsp;";
		                    	}
		                    	if(authorizationAuthority){
		                    	temp +=   "<a class='btn' href='javascript:;'  onclick='editUserAuthority(\""+ row.id + "\")'><i class='i-op i-op-edit'></i>授权</a>&nbsp;";
		                    	}
		                    	return temp;
	                      }
	                  }]
	              ];
	}else if(userInfo.roleType==1){
		columns = [ [{field: 'loginName', title: '账号名', width: 100, align: 'center'},
	                  {field: 'userName',title: '用户名称',width: 100, align: 'center'}, 
	                  {field: 'createDate',title: '创建时间',width: 100,align: 'center',
	                      formatter: function(value, row, index) {
	                          if (null != value) {
	                              var date = new Date(value);
	                              return date.pattern("yyyy-MM-dd hh:mm:ss");
	                          } else {
	                              value = '--';
	                              return value;
	                          }
	                      }
	                  }, 
	                  {field: 'roleName', title: '角色', width: 80,align: 'center'}, 
	                  { field: 'manager', title: '是否为管理员',width: 80,align: 'center',
		  	                formatter: function(value, row, index) {
		  	                    var statusContent = "";
		  	                    if (value==1) {
		  	                        statusContent += "<span class='goodsStatus'>是</span>";
		  	                    } else {
		  	                        statusContent += "<span class='goodsStatus'>否</span>";
		  	                    }
		  	                    return statusContent;
		  	                }
		                  },
	              		{ field: 'status',title: '状态',width: 80,align: 'center',
	  	                formatter: function(value, row, index) {
	  	                    var statusContent = "";
	  	                    if (value == 1) {
	  	                        statusContent += "<span class='goodsStatus'>已启用</span>";
	  	                    } else if (value == 0) {
	  	                        statusContent += "<span style='color: red;' class='goodsStatus'>已停用</span>";
	  	                    } else {
	  	                        statusContent += "<span class='goodsStatus'>未知</span>";
	  	                    }
	  	                    return statusContent;
	  	                }
	                  },
	                  {field: 'opts',title: '操作', width: 250,align: 'center',
	                	  formatter: function(value, row, index) {
	                      	var temp ="";
	                      	if(viewsAuthority){
	                      	temp += "<a class='btn' href='javascript:;'   onclick='viewsUser(\"" + row.id + "\")'><i class='i-op i-op-view'></i>查看</a>&nbsp;";
	                      	}
	                      	if(editAuthority){
	                      	temp += "<a class='btn' href='javascript:;'  onclick='viewGuser(\"" + row.id + "\")'><i class='i-op i-op-modify'></i>编辑</a>&nbsp;";
	                      	}
	                      	if(startorstopAuthority){
	                      	if (row.status == 0) {
	                      		temp += "<a class='btn' href='javascript:;'   onclick='frozenGUser(\"" + row.id + "\"," + '1,' + index + ")'><i class='i-op i-op-unlock'></i>启用</a>&nbsp;";
	                          } else {
	                          	temp += "<a class='btn' href='javascript:;'   onclick='frozenGUser(\"" + row.id + "\"," + '0,' + index + ")'><i class='i-op i-op-lock'></i>停用</a>&nbsp;";
	                          }
	                      	}
	                    	if(resetpwdAuthority){
	                      	temp += "<a class='btn' href='javascript:;'     onclick='resetPwd("+row.id+");'><i class='i-op i-op-set'></i>重置密码</a>&nbsp;";
	                    	} 
	                    	console.log(row.manager+"||"+ userInfo.roleType);
	                    	if(authorizationAuthority&&row.manager==1&&row.roleName!='网点'&&row.roleName!='会员'){
	                    		temp +=   "<a class='btn' href='javascript:;'  onclick='editUserAuthority(\""+ row.id + "\")'><i class='i-op i-op-edit'></i>授权</a>&nbsp;";
	                    	}
	                      	return temp;
	                      }
	                  }]
	              ];
	}else{
		columns = [ [{field: 'loginName', title: '账号名', width: 100, align: 'center'},
	                  {field: 'userName',title: '用户名称',width: 100, align: 'center'}, 
	                  {field: 'createDate',title: '创建时间',width: 100,align: 'center',
	                      formatter: function(value, row, index) {
	                          if (null != value) {
	                              var date = new Date(value);
	                              return date.pattern("yyyy-MM-dd hh:mm:ss");
	                          } else {
	                              value = '--';
	                              return value;
	                          }
	                      }
	                  }, 
	                  {field: 'roleName', title: '角色', width: 80,align: 'center'}, 
	              	  { field: 'status',title: '状态',width: 80,align: 'center',
	  	                formatter: function(value, row, index) {
	  	                    var statusContent = "";
	  	                    if (value == 1) {
	  	                        statusContent += "<span class='goodsStatus'>已启用</span>";
	  	                    } else if (value == 0) {
	  	                        statusContent += "<span style='color: red;' class='goodsStatus'>已停用</span>";
	  	                    } else {
	  	                        statusContent += "<span class='goodsStatus'>未知</span>";
	  	                    }
	  	                    return statusContent;
	  	                }
	                  },
	                  {field: 'opts',title: '操作', width: 250,align: 'center',
	                	  formatter: function(value, row, index) {
	                      	var temp ="";
	                      	if(viewsAuthority){
	                      	temp += "<a class='btn' href='javascript:;'   onclick='viewsUser(\"" + row.id + "\")'><i class='i-op i-op-view'></i>查看</a>&nbsp;";
	                      	}
	                      	if(editAuthority){
	                      	temp += "<a class='btn' href='javascript:;'  onclick='viewGuser(\"" + row.id + "\")'><i class='i-op i-op-modify'></i>编辑</a>&nbsp;";
	                      	}
	                      	if(startorstopAuthority){
	                      	if (row.status == 0) {
	                      		temp += "<a class='btn' href='javascript:;'   onclick='frozenGUser(\"" + row.id + "\"," + '1,' + index + ")'><i class='i-op i-op-unlock'></i>启用</a>&nbsp;";
	                          } else {
	                          	temp += "<a class='btn' href='javascript:;'   onclick='frozenGUser(\"" + row.id + "\"," + '0,' + index + ")'><i class='i-op i-op-lock'></i>停用</a>&nbsp;";
	                          }
	                      	}
	                    	if(resetpwdAuthority){
	                      	temp += "<a class='btn' href='javascript:;'     onclick='resetPwd("+row.id+");'><i class='i-op i-op-set'></i>重置密码</a>&nbsp;";
	                    	} 
	                    	if(authorizationAuthority){
	                    	temp +=   "<a class='btn' href='javascript:;'  onclick='editUserAuthority(\""+ row.id + "\")'><i class='i-op i-op-edit'></i>授权</a>&nbsp;";
	                    	}
	                    	return temp;
	                      }
	                  }]
	              ];
	}
	
    $('#dataGrid')
        .datagrid({
            url: url,
            queryParams: queryParams,
            rownumbers: true, // 行号
            singleSelect: true, // 是否单选
            pagination: true, // 分页控件
            pageSize: 15,
            pageList: [15, 30, 50],
            autoRowHeight: false,
            fit: true,
            striped: false, // 设置为true将交替显示行背景
            fitColumns: true,
            nowrap: false,
            remotesort: false,
            checkOnSelect: false,
            selectOnCheck: false,
            method: "GET", // 请求数据的方法
            loadMsg: '数据加载中,请稍候......',
            idField: 'id',
            rowStyler: function(index, row) {
                return 'font-size: 20px;';
            },
            columns:columns ,
            onLoadError: function() {
            	 $.messager.alert({
            	      title:'提示',
            	      msg:'<div class="content">获取信息失败...请联系管理员!</div>',
            	      ok:'<i class="i-ok"></i> 确定',
            	      icon:'warning'
            	    });
            },
            onLoadSuccess: function(data) {
            	if(funAuthority){
					 $.each(funAuthority,function(index,data){ 
						 $("."+data).show();
					 });
				}
            	 $(".datagrid-row").css("height", "45px");
            	if(data==null||data.total==0){
            		$.messager.alert({
               	      title:'提示',
               	      msg:'<div class="content">当前没有查询到记录!</div>',
               	      ok:'<i class="i-ok"></i> 确定',
               	      icon:'warning'
               	    });
            	}
            },
            toolbar: '#tb'
        });
}

function viewGuser(id) {
    window.location = 'guser_edit.html?userId=' + id;
}
function viewsUser(id) {
    window.location = 'guser_views.html?userId=' + id;
}

function frozenGUser(id, status, index, createType) {
    var statusText = null;
    var mess = "";
    if (status == 1) {
        mess = "启用";
        statusText = "已启用";
    } else if (status == 0) {
        mess = "停用";
        statusText = "已停用";
    }
    $.messager.confirm({
        title:'修改用户状态',
        msg:'<div class="content">是否确认修改用户状态?</div>',
        ok:'<i class="i-ok"></i> 确定',
        cancel:'<i class="i-close"></i> 取消',
        fn:function(r){
        	if (r){
        		$.ajax({
                    url: '../../user/frozenGUser.do',
                    type: 'post',
                    data: {
                        'id': id,
                        'status': status
                    },
                    success: function(data) {
                        if (data && data.status == 200) {
                        	$.messager.alert({
                         	      title:'提示',
                         	      msg:'<div class="content">'+mess+'成功</div>',
                         	      ok:'<i class="i-ok"></i> 确定',
                         	      icon:'info'
                         	    });
                            $('#dataGrid').datagrid('updateRow', {
                                index: index,
                                row: {
                                    statusText: statusText,
                                    status: status
                                }
                            });
                            if(funAuthority){
	           					 $.each(funAuthority,function(index,data){ 
	           						 $("."+data).show();
	           					 });
                            }
                            $(".datagrid-row").css("height", "45px");
                        } else {
                            $.messager.alert({
                       	      title:'提示',
                       	      msg:'<div class="content">'+mess+'失败，请重试</div>',
                       	      ok:'<i class="i-ok"></i> 确定',
                       	      icon:'error'
                       	    });
                        }
                    },
                    dataType: 'json'
                });
        	}
        }
      });


}





var queryParams = null;

function search() {
    if ($("#tb").form('validate')) {
        var name = $.trim($('#name').textbox('getValue'));
    /*    if((/[^\u4E00-\u9FA5A-Za-z0-9]/g).test(name)){
    		$.messager.alert({
    	  	      title:'温馨提示',
    	  	      msg:'<div class="content">账号不支持特殊字符搜索!</div>',
    	  	      ok:'<i class="i-ok"></i> 确定',
    	  	      icon:'error'
    	  	    });
    			return;
    	}*/
        if (name) {
        	name = decToHex(name);
        }
        var status = $('#status').textbox('getValue');
        if (!status) {
            status = null;
        }
        if(status==null||status=='undefined'){
        	status = -1;
        }
        var roleType = $('#roleType').textbox('getValue');
        if (!roleType) {
        	roleType = null;
        }
        if(roleType==null||roleType=='undefined'){
        	roleType = -1;
        }
        var userGroup = $('#userGroup').textbox('getValue');
        if (!userGroup) {
        	userGroup = null;
        }
        if(userGroup==null||userGroup=='undefined'){
        	userGroup = -1;
        }
        queryParams = {
            'name': name,
            'status': status,
            'roleType':roleType,
            'userGroup':userGroup
        };
        init('../../user/getPageForGUserInfo.do?randnum=' + Math.random() + '', queryParams);
    };
}
function clearForm(){
	 $('#name').textbox('setValue','');
	 $('#status').combobox('clear');
	 $('#roleType').combobox('clear');
	 $('#userGroup').combobox('clear');
}
$.extend($.fn.validatebox.defaults.rules, {
    maxLength: {
        validator: function(value, param) {
            return param[0] > value.length;
        },
        message: '请输入最大{0}位字符.'
    }
});
//重置密码
function resetPwd(id){
	$.messager.confirm({
	      title:'重置密码提示',
	      msg:'<div class="content">你确定要重置该用户的密码吗?</div>',
	      ok:'<i class="i-ok"></i> 确定',
	      cancel:'<i class="i-close"></i> 取消',
	      fn:function(r){
	    	  if (r) { 
	    	  $.ajax({
	    	         url: '../../user/resetPwd.do',
	    	         type: 'post',
	    	         data: {
	    	             'userId': id
	    	         },
	    	         success: function(data) {
	    	             if (data.status == 200) {
	    	            	 $.messager.alert({
	    	              	      title:'提示',
	    	              	      msg:'<div class="content">重置密码成功！</div>',
	    	              	      ok:'<i class="i-ok"></i> 确定',
	    	              	      icon:'info'
	    	              	    });
	    	             } else {
	    	            	 $.messager.alert({
	    	             	      title:'提示',
	    	             	      msg:'<div class="content">重置密码失败，请重试！</div>',
	    	             	      ok:'<i class="i-ok"></i> 确定',
	    	             	      icon:'error'
	    	             	    });
	    	             }
	    	         },
	    	         dataType: 'json'
	    	     });
	    	  }
	      }});
}

function editUserAuthority(id){
	
	var initAuthority = getInitAuthority();
	var tplInitAuthority = renderInitAuthority(initAuthority);
	$("#editUserAuthority").dialog("open");
	$("#editUserAuthorityForm .content").html(tplInitAuthority);
	$("#autUserId").val(id);
	//查询已有权限，并默认勾选
	getAuthorityMenuByUserId(id);
	getAuthorityMenuByRoleId(id);
}

//获取已有权限
function getAuthorityMenuByUserId(id){
	$.ajax({
			url:"../../user/userAuthority.do",
			traditional:true,
			type:'post',
			async:false,
			data:{userId:id},
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
//获取已有权限
function getAuthorityMenuByRoleId(id){
	$.ajax({
			url:"../../user/roleAuthority.do",
			traditional:true,
			type:'post',
			async:false,
			data:{userId:id},
			cache:false,
			success:function(data){
				if(null!=data&&data.length>0){
					for (var i = 0; i < data.length; i++) {
						$("#"+data[i]).attr("checked",true);
						$("#"+data[i]).attr('disabled','disabled'); 
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
function saveUserAuthority(){
	var text = $("#submit").html();
	if(text=="保存"){
		//提交表单
		var data = {};
		var authority = getAuthority("#editUserAuthority .content");//获取权限
		data.userId=$("#autUserId").val();
		data.status=1;
	    data.authority = authority;
		$("#submit").html("保存中...");
		$.post("../../user/updateUserAuthority.do",data, function(result) {
				$("#submit").html("保存");
				if (result.status == 200) {
					$.messager.alert({
				  	      title:'提示',
				  	      msg:'<div class="content">保存成功!</div>',
				  	      ok:'<i class="i-ok"></i> 确定',
				  	      icon:'info'
				  	    });
					$("#editUserAuthority").dialog("close");
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
}
