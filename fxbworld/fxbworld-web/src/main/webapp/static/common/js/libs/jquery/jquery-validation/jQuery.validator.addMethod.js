define(function(require, exports, module) {
	var $ = require('jquery');
(function($) {

// 字符验证    
jQuery.validator.addMethod("stringCheck", function(value, element) {       
    return this.optional(element) || /^[u0391-uFFE5w]+$/.test(value);       
}, "只能包括中文字、英文字母、数字和下划线");   
  
// jQuery.validator.addMethod("isPwd", function(value, element) {       
//     return this.optional(element) || /^[0-9a-zA-Z\._\$\~`!@#=\\$^\-%+\|\(\)\}\{\[\]\'\",<>;\?/&#\^\*\!]{6,20}$/.test(value);       
// }, "由6-20位英文字母、数字或符号组成");


jQuery.validator.addMethod("isPwd", function(value, element) {       
    return this.optional(element) || /^[0-9]{6,16}$/.test(value);       
}, "密码只能由6-16位数字组成");
// 中文字两个字节       
jQuery.validator.addMethod("byteRangeLength", function(value, element, param) {       
    var length = value.length;       
    for(var i = 0; i < value.length; i++){       
        if(value.charCodeAt(i) > 127){       
        length++;       
        }       
    }       
    return this.optional(element) || ( length >= param[0] && length <= param[1] );       
}, "请确保输入的值在6-20个字节之间");   
  
// 身份证号码验证       
jQuery.validator.addMethod("isIdCardNo", function(value, element) {       
    return this.optional(element) || isIdCardNo(value);       
}, "请正确输入您的身份证号码");    
     
// 手机号码验证       
$.validator.addMethod("isMobile", function(value, element) {       
    var length = value.length;   
    var mobile = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(14[0-9]{1})|(17[0-9]{1}))[0-9]{8})$/;   
    return this.optional(element) || (length == 11 && mobile.test(value));       
}, "请正确填写您的手机号码");         
     
// 电话号码验证       
jQuery.validator.addMethod("isTel", function(value, element) {       
    var tel = /^d{3,4}-?d{7,9}$/;    //电话号码格式010-12345678   
    return this.optional(element) || (tel.test(value));       
}, "请正确填写您的电话号码");   

// 必须四位整数     
$.validator.addMethod("isFourInt", function(value, element) {       
    var length = value.length;   
    var mobile = /^([0-9]{4})$/;    
    return this.optional(element) || (length == 4 && mobile.test(value));       
}, "必须为四位整数");
  
// 联系电话(手机/电话皆可)验证   
jQuery.validator.addMethod("isPhone", function(value,element) {   
    var length = value.length;   
    var mobile = /^(((13[0-9]{1})|(15[0-9]{1}))+d{8})$/;   
    var tel = /^d{3,4}-?d{7,9}$/;   
    return this.optional(element) || (tel.test(value) || mobile.test(value));   
  
}, "请正确填写您的联系电话");   
     
// 邮政编码验证       
jQuery.validator.addMethod("isZipCode", function(value, element) {       
    var tel = /^[0-9]{6}$/;       
    return this.optional(element) || (tel.test(value));       
}, "请正确填写您的邮政编码"); 

//自定义remote
jQuery.validator.addMethod("remote2", function( value, element, param ) {
            if ( this.optional( element ) ) {
                return "dependency-mismatch";
            }
            var previous = this.previousValue( element ),
                validator, data;

            if (!this.settings.messages[ element.name ] ) {
                this.settings.messages[ element.name ] = {};
            }
            previous.originalMessage = this.settings.messages[ element.name ].remote;
            this.settings.messages[ element.name ].remote = previous.message;

            param = typeof param === "string" && { url: param } || param;

            if ( previous.old === value ) {
                return previous.valid;
            }

            previous.old = value;
            validator = this;
            this.startRequest( element );
            data = {};
            data[ element.name ] = value;
            $.ajax( $.extend( true, {
                url: param,
                mode: "abort",
                port: "validate" + element.name,
                dataType: "json",
                data: data,
                context: validator.currentForm,
                success: function( response ) {
                    var valid = response === true || response === "true"|| response.data === true || response.data === "true",
                        errors, message, submitted;

                    validator.settings.messages[ element.name ].remote = previous.originalMessage;
                    if ( valid ) {
                        submitted = validator.formSubmitted;
                        validator.prepareElement( element );
                        validator.formSubmitted = submitted;
                        validator.successList.push( element );
                        delete validator.invalid[ element.name ];
                        validator.showErrors();
                    } else {
                        errors = {};
                        message = response.msg || validator.defaultMessage( element, "remote2" );
                        errors[ element.name ] = previous.message = $.isFunction( message ) ? message( value ) : message;
                        validator.invalid[ element.name ] = true;
                        validator.showErrors( errors );
                    }
                    previous.valid = valid;
                    validator.stopRequest( element, valid );
                }
            }, param ) );
            return "pending";
})
 
})(jQuery);
module.exports = jQuery.validator;});