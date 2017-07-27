var funAuthority = getFunAuthority();
		$(function(){
			getSecurityInfo();
			if(funAuthority){
				 $.each(funAuthority,function(index,data){ 
					 $("."+data).show();
				 });
			}
		});
		var isProtection;
		var  phone="";
		function  userOperateList(){
			newTab("商家账号修改记录","security/user_operate_log.html");
		}
		function  operateList(){
			newTab("账号修改记录","security/operate_log.html");
		}
		function  loginLogList(){
			newTab("历史登录记录","security/loginLog_list.html");
		}
		
	
		function getSecurityInfo(){
			var url = '../../security/getUserSecurityInfo.do';
			  $.ajax({
			    url:url,
			    traditional:true,
			    type:'get',
			    async:true,
			    cache:false,
			    dataType:"json",
			    success:function(data){
			    	if(data){
			    		setSecurityInfo(data);
			    	}
			    },error:function(data){
			    	
			      $.messager.alert('提示', '操作失败!', 'error');
			    }
			  });
		}
	
		function protectionLogin(){
			var tmp="";
			if(isProtection==0){
				if(phone==""||!phone){
					 $.messager.alert({
	             	      title:'提示',
	             	      msg:'<div class="content">未绑定手机号，不能开启登录保护！</div>',
	             	      ok:'<i class="i-ok"></i> 确定',
	             	      icon:'warning'
	             	    });
					 return false;
				}
				isProtection=1;
				tmp="启用";
			}else{
				isProtection=0;
				tmp="停用";
			}
			$.messager.confirm({
			      title:'提示信息',
			      msg:'<div class="content">你确定要'+tmp+'登录保护吗?</div>',
			      ok:'<i class="i-ok"></i> 确定',
			      cancel:'<i class="i-close"></i> 取消',
			      fn:function(r){
			      	if (r){
			    		var url = '../../security/protectionLogin.do';
			    		$.ajax({
						    url:url,
						    traditional:true,
						    type:'get',
						    async:true,
						    cache:false,
						    data:{
						    	isProtection:isProtection
							},
						    dataType:"json",
						    success:function(data){
						    	if(data){
						    		if(isProtection==1){
						    			$("#isProtection").removeClass("icon-02");
						         	   	$("#isProtection").addClass("icon-01");
						    			 $("#protection").html("停用");
						    		}else{
						    			$("#isProtection").removeClass("icon-01");
						         	   	$("#isProtection").addClass("icon-02");
						    			 $("#protection").html("启用");
						    		}
						    	}
						    },error:function(data){
						    	
						      $.messager.alert('提示', '操作失败!', 'error');
						    }
						  });
			            return true;  
			      	}
			      }
			    });
			
			  
		}
		function  setSecurityInfo(data){
			var date = new Date(data.gmtLast);
           $("#logintime").html(date.pattern("yyyy-MM-dd HH:mm:ss"));
           $("#loginIp").html(data.lastIp);
           $("#loginAddress").html(data.loginAddress);
           if(data.phone){
	           $("#mobile").html(data.phone);
	           phone=data.phone;
           }else{
        	   $("#isphone").removeClass("icon-01");
        	   $("#isphone").addClass("icon-02");
        	   $("#phone").html("绑定手机号，为了您的账号安全，请添加手机号码，如有疑问请联系客户。");
           }
           if(data.mail){
	           $("#mail").html(data.mail);
           }else{
        	   $("#isMail").removeClass("icon-01");
        	   $("#isMail").addClass("icon-02");
        	   $("#sec_mail").html("绑定安全邮箱可用于找回密码或修改绑定手机号码，如有疑问请联系客户。");
           }
           if(data.isProtection==1){
        	   $("#isProtection").removeClass("icon-02");
        	   $("#isProtection").addClass("icon-01");
        	   $("#protection").html("停用");
        	   isProtection=1;
           }else{
        	   isProtection=0;
           }
           if(data.isSecurity==1){
        	   $("#isSecurity").removeClass("icon-02");
        	   $("#isSecurity").addClass("icon-01");
        	   $("#security").html("修改");
           }
           if(data.securityGrade==10){
        	   $("#cla").removeClass("safe-rank05");
        	   $("#cla").addClass("safe-rank05");
        	   $("#rank-text").html("高");
           }
			if(data.securityGrade<10&&data.securityGrade>=7){
				$("#cla").removeClass("safe-rank05");
				$("#cla").addClass("safe-rank04");
	        	$("#rank-text").html("高");
           }
			if(data.securityGrade<7&&data.securityGrade>=4){
				$("#cla").removeClass("safe-rank05");
				$("#cla").addClass("safe-rank03");
	        	$("#rank-text").html("中");
	       }
			if(data.securityGrade<4){
				  $("#cla").removeClass("safe-rank05");
				$("#cla").addClass("safe-rank02");
	        	   $("#rank-text").html("低");
	        }
			if(data.securityGrade<1){
				$("#cla").removeClass("safe-rank05");
				$("#cla").addClass("safe-rank01");
	        	   $("#rank-text").html("低");
	        }
           
		}