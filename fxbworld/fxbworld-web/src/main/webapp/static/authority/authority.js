
//获取权限值
function getAuthority(selector){
	var authority=[];
	$(selector).find(":input:checked").each(function(i,n){
		if($(n).attr("disabled")!="disabled"){
			var id = $(n).attr("id");
			var pid = $(n).attr("pid");
			authority.push({"menuId":id,"pid":pid});
		}
	});
	return JSON.stringify(authority);
}

//初始化权限
function getInitAuthority(){	
	var url = "../../sysRole/getMenuList.do";
	var initAuthority=null;
	$.ajax({
			url:url,
			traditional:true,
			type:'post',
			async:false,
			data:null,
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



//初始化渲染
function renderInitAuthority(initAuthority){
	//console.log(initAuthority);
	var tpl ="";
	var length1= initAuthority.length;
	tpl+='<div class="content">';
	 for(var i =0;i<length1;i++){
		 tpl +='<div class="row1">'+
	 	'<div class="th">'+
        '<label for="'+initAuthority[i].id+'">'+
        '<input id="'+initAuthority[i].id+'" pid="-1" type="checkbox" value="checkbox"> '+initAuthority[i].menuName+'</label>'+
        '</div>';
	 	if("childNode" in initAuthority[i]){
	 		var length2 = initAuthority[i].childNode.length;
		 	for(var j =0;j<length2;j++){
		 		if(initAuthority[i].childNode[j].sysName){
		 			tpl +='<div class="row2" ><div class="th" style=" width: 560px;">';
		 			tpl +='<div class="sysrow" style=" width:160px;"><label for="'+initAuthority[i].childNode[j].id+'">'+initAuthority[i].childNode[j].sysName+'</label></div>';
		 		}else{
		 			tpl +='<div class="row2" ><div class="th" style=" width: 220px;"><label for="'+initAuthority[i].childNode[j].id+'">';
		 		}
		 			
		        if(initAuthority[i].childNode[j].aliases){
		        	tpl+='<label  for="'+initAuthority[i].childNode[j].id+'">';
		        	tpl +='<input id="'+initAuthority[i].childNode[j].id+'" pid="'+initAuthority[i].id+'" type="checkbox" value="checkbox"> '
		        	+initAuthority[i].childNode[j].menuName+"("+initAuthority[i].childNode[j].aliases+')</label>';
		        }else{
		        	tpl+='<label for="'+initAuthority[i].childNode[j].id+'">';
		        	tpl +='<input id="'+initAuthority[i].childNode[j].id+'" pid="'+initAuthority[i].id+'" type="checkbox" value="checkbox"> '
		        	+initAuthority[i].childNode[j].menuName+'</label>';
		        }
		 		tpl +='</div>';
		 		
		 		
		 		tpl +='<div class="row3">';
		 		if("childNode" in initAuthority[i].childNode[j]){
			 		var length3 = initAuthority[i].childNode[j].childNode.length;
			 		for(var k =0;k<length3;k++){
				       tpl +='<label for="'+initAuthority[i].childNode[j].childNode[k].id+'">'+
				        '<input id="'+initAuthority[i].childNode[j].childNode[k].id+'" pid="'+initAuthority[i].childNode[j].id+'" type="checkbox" value="checkbox"> '
				        +initAuthority[i].childNode[j].childNode[k].menuName+'</label>';
				 	}
		 		}else{
		 			tpl+="&nbsp";
		 		}
		 		tpl +='</div>';
		 	
		 		tpl +='</div>';
		 	}
	 	}
	 	
	 	tpl+='</div>';
	 }
	 tpl+='</div>';
	return tpl;
}