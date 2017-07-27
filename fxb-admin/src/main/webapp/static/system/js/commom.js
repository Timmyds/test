$.getFormData = function(form) {
	var o = {};
	$.each(form.serializeArray(), function(index) {
		if (o[this['name']]) {
			o[this['name']] = o[this['name']] + "," + this['value'];
		} else {
			o[this['name']] = this['value'];
		}
	});
	return o;
};
$.ValiDateISNULL = function(form,name) {
	var flag=true;
	$.each(form.serializeArray(), function(index) {
		if(flag==false){
			return false;
		}
		if (this['name']==name) {
			if(this['value']!=""&&this['value']!="-1"&&this['value']!=undefined&&this['value']!=null&&this['value']!="null"){
				flag = true;
			}else{
				flag = false;
			}
		}
	});
	return flag;
};
Array.prototype.unique3 = function(){
	 var res = [];
	 var json = {};
	 for(var i = 0; i < this.length; i++){
	  if(!json[this[i]]){
	   res.push(this[i]);
	   json[this[i]] = 1;
	  }
	 }
	 return res;
	}

$.ValiDateGroup = function(form,name) {
	var arr = [];
	$.each(form.serializeArray(), function(index) {
		if (this['name']==name) {
			if(this['value']!=""&&this['value']!=undefined&&this['value']!=null&&this['value']!="null"){
				arr.push(this['value']);
			}
		}
	});
	arr=arr.unique3();
	return arr.join(",");
};