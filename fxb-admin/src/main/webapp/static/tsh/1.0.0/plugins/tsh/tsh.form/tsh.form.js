
/**
  * 基于jquery的表单验证插件
  * 依赖tsh.validate
  * 使用$(".form");$(".form").("checkForm")
  * <input class="form-control required validate" rules='{"required":true,"minLength":30}' >
  * @author chenwj@dtds.com.cn    2015-09-11
 */
(function ($) {
	var methods = {
        init: function (options) {
        	var defaults ={}
            var settings = $.extend(defaults, options);
            _this = this;
            elems = this.find(".validate");
            elems.each(function(i,elem){
            	var rules = $(this).attr("rules");
            	if(rules){
            		rules = JSON.parse(rules);
            		methods.onkeyUpAndDown($(elem),rules);
            	}
            })
            
        },
        onkeyUpAndDown: function(elem,rules){
        	elem.on("keyup blur focus",function(){
            	methods.check(elem,rules);
            })
        },
        check: function (elem,rules) {//检查

        	var cheakResult = true;
            var val = elem.val();
          
            for(var rule in rules){
            	if(rule =="required"){
            		if(tshValidate[rule](val,rules[rule])){//tshValiDate[规则](表单值,规则值);
		            	elem.siblings(".error").remove();
		            }else{
		            	elem.siblings(".error").remove();
		            	var errorElement= '<div class="error text-danger error-'+rule+'">*'+tshMessages[rule](rules[rule])+'</div>';
		            	elem.after(errorElement);
		            	cheakResult = false;
		            	break;
		            }
            	}
            	else{
            		if(val!=null&&val!=""){
            			if(tshValidate[rule](val,rules[rule])){//tshValiDate[规则](表单值,规则值);
		            		elem.siblings(".error").remove();
		            	}else{
		            		elem.siblings(".error").remove();
		            		var errorElement= '<div class="error text-danger error-'+rule+'">*'+tshMessages[rule](rules[rule])+'</div>';
		            		elem.after(errorElement);
		            		cheakResult = false;
		            		break;
		            	}
            		}else{
            			elem.siblings(".error").remove();
            		}
            		
            	}
            	
            }
            return cheakResult;
        },
        checkElem : function(p){
        	var rules = this.attr("rules");
        	if(rules){
        		rules = JSON.parse(rules);
            	methods.check(this,rules);
            }
        },
        checkForm :function(){//用来检测所有表单元素

        	var isResult = true;
        	elems = this.find(".validate");
            elems.each(function(i,elem){
            	var rules = $(this).attr("rules");
            	if(rules){
            		rules = JSON.parse(rules);
            		cheakResult = methods.check($(elem),rules);
            		if(!cheakResult){
            			elem.focus();
            			isResult = false;
            		}
            	}
            })
            return isResult;
        },
        show: function () {
            // is
        },
        hide: function () {
            // good
        }
        
    };

	$.fn.tshForm = function (method) {
		//创建一些默认值，拓展任何被提供的选项
         // 方法调用
        if (methods[method]) {
            return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
        } else if (typeof method === 'object' || !method) {
            return methods.init.apply(this, arguments);
        } else {
            $.error('Method' + method + 'does not exist on jQuery.tshForm');
        }

	}
})(window.jQuery || window.Zepto, window);