var funAuthority = getFunAuthority();
var authorizationAuthority=inAuthority('wm-usc-user-authorization',funAuthority);
$(function() {
	commoninit();
	if(funAuthority){
		 $.each(funAuthority,function(index,data){ 
			 $("."+data).show();
		 });
		 
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
    init('../../user/getPageForBusinessUser.do?randnum=' + Math.random() , null);
    $('#search').click(function() {
        search();
    });
});

function init(url, queryParams) {
	var columns = [ [{field: 'loginName', title: '账号名', width: 100, align: 'center'},
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
	              	  { field: 'status',title: '权限是否禁用',width: 80,align: 'center',
	  	                formatter: function(value, row, index) {
	  	                    var statusContent = "";
	  	                    if (value == 1) {
	  	                    	statusContent += "<span style='color: red;' class='goodsStatus'>是</span>";
	  	                    } else if (value == 0) {
	  	                    	statusContent += "<span class='goodsStatus'>否</span>";
	  	                    } else {
	  	                        statusContent += "<span class='goodsStatus'>未知</span>";
	  	                    }
	  	                    return statusContent;
	  	                }
	                  },
	                  {field: 'opts',title: '操作', width: 250,align: 'center',
	                	  formatter: function(value, row, index) {
	                		  var temp ="";
		                    	if(authorizationAuthority){
		                    	temp +=   "<a class='btn' href='javascript:;'  onclick='editUserAuthority(\""+ row.id + "\",\""+ row.roleType + "\")'><i class='i-op i-op-edit'></i>权限禁用</a>&nbsp;";
		                    	}
		                    	return temp;
	                      }
	                  }]
	              ];

	
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







var queryParams = null;

function search() {
    if ($("#tb").form('validate')) {
        var name = $.trim($('#name').textbox('getValue'));
   
        if (name) {
        	name = decToHex(name);
        }
        var roleType = $('#roleType').textbox('getValue');
        if (!roleType) {
        	roleType = null;
        }
        if(roleType==null||roleType=='undefined'){
        	roleType = -1;
        }
        queryParams = {
            'name': name,
            'roleType':roleType
        };
        init('../../user/getPageForBusinessUser.do?randnum=' + Math.random() + '', queryParams);
    };
}
function clearForm(){
	 $('#name').combobox('clear');
	 $('#roleType').combobox('clear');
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


function editUserAuthority(id,roleType){
	
	var initAuthority = getUserAuthority(id,roleType);
	var tplInitAuthority = renderInitAuthority(initAuthority);
	$("#editUserAuthority").dialog("open");
	$("#editUserAuthorityForm .content").html(tplInitAuthority);
	$("#autUserId").val(id);
	//查询已有权限，并默认勾选
	getLimitAuthorityByUserId(id);
}

//初始化权限
function getUserAuthority(id,roleType){
	var url = "../../user/getUserMenuList.do";
	var initAuthority=null;
	$.ajax({
			url:url,
			traditional:true,
			type:'post',
			async:false,
			data:{userId:id,roleType:roleType},
			cache:false,
			success:function(data){
				initAuthority = data;
			},error:function(data){
				$.messager.alert({
			  	      title:'提示',
			  	      msg:'<div class="content">操作失败！</div>',
			  	      ok:'<i class="i-ok"></i> 确定',
			  	      icon:'error'
			  	    });
			}
		});
	return initAuthority;
}
//获取已有权限
function getLimitAuthorityByUserId(id){
	$.ajax({
			url:"../../user/userLimitAuthority.do",
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
