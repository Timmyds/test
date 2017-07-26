
/**
  * 淘实惠平台验证类
  * @author chenwj@dtds.com.cn    2015-08-16
 */
var tshMessages ={
    required : function(){
         return "不能为空";
    },
    maxLength : function(length){
        return "长度不能超过"+length;
    },
    maxLengthTb : function(length){//验证字节，yuands添加
        return "长度不能超过"+length+"字";
    },
    num: function(range){
        return "数量必须为"+range[0]+"-"+range[1];
    },
    price: function(range){
        return "价格必须为"+range[0]+"-"+common.formatCurrency(range[1]);
    },
    
    bigToSupplyPrice:function(){
        return "销售价必须大于供货价";
    },
    smallToSalePrice:function(){
        return "供货价必须小于销售价";
    },
    smallToSupplyPrice:function(){
        return "批发价必须小于供货价";
    },
    commission:function(range){
    	return "佣金应该在："+range[0]+"%-"+range[1]+"%的比例之间";
    },
    psixLength:function(){
    	return "密码长度为6~16位";
    },
    psTrim:function(){
    	return "首尾不支持空格";
    }
};
var tshValidate = {
			psTrim:function(val){
				return !(/(^\s+)|(\s+$)/g).test(val);
			},
		    psixLength:function(val){
		    	return val.length>5&&val.length<17;
		    },
		    
			commission:function(val,range){
				//alert(val+"_"+(val>=range[0])&&(val<=range[1]))
			   return (val>=range[0])&&(val<=range[1])&&(/^(([0-9]|([1-9][0-9]{0,9}))((\.[0-9]{1,2})?))$/.test(val));
			},
            //判断是否为空
            required : function(val){
               return (val != null) && (val != "");
            },
            maxLength : function(val,length){
             return (val.length<=length);
            },
            maxLengthTb:function(val,length){//验证字节，yuands添加
							len=val.replace(/[^\x00-\xff]/gi,'xx').length;
							return (len<=length*2);
            },
            num : function(val,range){
                return (val>=range[0])&&(val<=range[1])&&(/^([1-9]\d*|0)$/.test(val));
            },
            price: function(val,range){ 
                return (val>=range[0])&&(val<=range[1])&&(/^(([0-9]|([1-9][0-9]{0,9}))((\.[0-9]{1,2})?))$/.test(val));
            },
            bigToSupplyPrice:function(val,elem){//大于
                var val1 = parseFloat(val);
                var val2 =parseFloat($(elem).val());
                
                if(val2){
                     if(val1>val2){
                        $(elem).next(".error-smallToSalePrice").remove();
                        return true;
                     }else{
                        return false;
                     }
                }else{

                    $(elem).next(".error-smallToSalePrice").remove();
                     return true;
                }
            },
            smallToSupplyPrice:function(val,elem){
                var val1 = parseFloat(val);
                var val2 = parseFloat($(elem).val());
                if(val2){
                     if(val1<val2){
                        $(elem).siblings(".error-smallToSupplyPrice").remove();
                        return true;
                     }else{
                        return false;
                     }
                }else{
                    $(elem).siblings(".error-smallToSupplyPrice").remove();
                     return true;
                }
            },
            smallToSalePrice:function(val,elem){
                var val1 = parseFloat(val);
                var val2 = parseFloat($(elem).val());
                
                if(val2){
                     if(val1<val2){
                        $(elem).siblings(".error-bigToSupplyPrice").remove();
                        return true;
                     }else{
                        return false;
                     }
                }else{
                    $(elem).siblings(".error-bigToSupplyPrice").remove();
                     return true;
                }
            },
            //判断是否为字符串类型
            isString: function (object) {
                return (object != null) && (object != undefined) && (typeof object == 'string') && (object.constructor == String);
            },
            //判断是否为数值类型
            isNum: function (object) {
                return !isNaN(object);
            },
            //大于0的金额格式
            isPrice: function (amount) {
                return /^(([0-9]|([1-9][0-9]{0,9}))((\.[0-9]{1,2})?))$/.test(amount) && amount > 0;
            },
            /// <summary>
            /// 大于0金额格式验证 
            /// @param validateDate 
            /// @returns boolean
            /// </summary>
            isLGZeroAmount: function (amount) {
                return /^-?\d+\.{0,}\d{0,}$/.test(amount) && amount > 0;
            },
            /// <summary>
            /// 邮箱格式验证
            /// @param email
            /// @returns boolean
            /// </summary>
            isEmail: function (email) {
                return /^\s*\w+(?:\.{0,1}[\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\.[a-zA-Z]+\s*$/i.test(email);
            },
            /// <summary>
            /// QQ号码格式验证
            /// @param email
            /// @returns boolean
            /// </summary>
            isQQNumber: function (qq) {
                return /^[1-9]\d{4,10}$/.test(qq);
            },
            /// <summary>
            /// 登录名格式验证
            /// @param loginName
            /// @returns boolean
            /// </summary>
            isLoginName: function (loginName) {
               // return /^[a-zA-Z0-9_-]{6,16}$/.test(loginName);
                return /^[a-zA-Z0-9-_\u4e00-\u9fa5]{6,16}$/.test(loginName);
                
            },

            /// <summary>
            /// 密码格式验证 验证用户密码(正确格式为：长度在6~16 之间，任意字符)  
            /// @param psw 
            /// @returns boolean
            /// </summary>
            isPassword: function (psw) {
                return /^.{6,16}/.test(psw);
            },
            /// <summary>
            /// 手机号码格式验证  
            /// @param mobile 
            /// @returns boolean
            /// </summary>
            isMoblie: function (mobile) {
                return /^1[345678]\d{9}$/.test(mobile);
            },
            /// <summary>
            /// 电话号码格式验证  
            /// @param phone 
            /// @returns boolean
            /// </summary>
            isPhone: function (phone) {
                return /(^[0-9]{3,4}\-[0-9]{7,8}$)|(^[0-9]{7,8}$)|(^\([0-9]{3,4}\)[0-9]{3,8}$)|(^0{0,1}1[3578]\d{9}$)/.test(phone);
            },
            /// <summary>
            /// 邮编格式验证  
            /// @param postCode 
            /// @returns boolean
            /// </summary>
            isPostCode: function (postCode) {
                return /^[1-9][0-9]{5}$/.test(postCode);
            },
            /// <summary>
            /// 验证码基本格式验证  
            /// @param validateCode 
            /// @returns boolean
            /// </summary>
            validateCode: function (validateCode) {
                return /^[a-zA-Z0-9]{4,4}$/.test(validateCode);
            },
            /// <summary>
            /// 验证汉字数字字母 
            /// @param validateUserName 
            /// @returns boolean
            /// </summary>
            isNumberlatterCcter: function (userName) {
                return /^[\u0391-\uFFE5A-Za-z0-9]+$/.test(userName);
            },
            /// <summary>
            /// 日期格式验证 
            /// @param validateDate 
            /// @returns boolean
            /// </summary>
            isDate: function (date) {
                return /^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-8]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))(\s(([01]\d{1})|(2[0123])):([0-5]\d):([0-5]\d))?$/.test(date);
            },
            /// <summary>
            /// 网络地址验证 
            /// @param url 
            /// @returns boolean
            /// </summary>
            isUrl: function (url) {
                return /^((http|https|ftp):\/\/)?(\w(\:\w)?@)?([0-9a-z_-]+\.)*?([a-z0-9-]+\.[a-z]{2,6}(\.[a-z]{2})?(\:[0-9]{2,6})?)((\/[^?#<>\/\\*":]*)+(\?[^#]*)?(#.*)?)?$/i.test(url);
            },
            /// <summary>
            /// 正整数格式验证 
            /// @param validateDate 
            /// @returns boolean
            /// </summary>
            isNumber: function (number) {
                return /^\d+$/g.test(number);
            },

            /// <summary>
            /// 金额格式 验证大于等于0，验证小数点后2位
            /// @param validateDate 
            /// @returns boolean
            /// </summary>
            isShipments: function (amount) {
                return /^\d+\.*(\.\d{1,2})?$/.test(amount);
            },

            /// <summary>
            /// 大于0的正整数格式验证 
            /// @param no
            /// @returns boolean
            /// </summary>
            isNo: function (number) {
                return /^([1-9]\d{0,3}|1000)$/g.test(number);
            },

            /// <summary>
            /// 是否是身份证号码  
            /// @param idCard 
            /// @returns boolean
            /// </summary>
            isIdCard: function (idCard) {
                idCard = $.trim(idCard);
                if (idCard.length == 15) {
                    return validateType.isValidityBrithBy15IdCard(idCard);
                } else if (idCard.length == 18) {
                    var a_idCard = idCard.split("");// 得到身份证数组   
                    if (validateType.isValidityBrithBy18IdCard(idCard) && validateType.isTrueValidateCodeBy18IdCard(a_idCard)) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            },
            /// <summary>
            /// 验证15位数身份证号码中的生日是否是有效生日   
            /// @param idCard 
            /// @returns boolean
            /// </summary>
            isValidityBrithBy15IdCard: function (idCard) {
                var year = idCard15.substring(6, 8);
                var month = idCard15.substring(8, 10);
                var day = idCard15.substring(10, 12);
                var temp_date = new Date(year, parseFloat(month) - 1, parseFloat(day));
                // 对于老身份证中的你年龄则不需考虑千年虫问题而使用getYear()方法   
                if (temp_date.getYear() != parseFloat(year)
                        || temp_date.getMonth() != parseFloat(month) - 1
                        || temp_date.getDate() != parseFloat(day)) {
                    return false;
                } else {
                    return true;
                }
            },
            /// <summary>
            /// 验证18位数身份证号码中的生日是否是有效生日   
            /// @param idCard 18位书身份证字符串 
            /// @returns boolean
            /// </summary>
            isValidityBrithBy18IdCard: function (idCard18) {
                var year = idCard18.substring(6, 10);
                var month = idCard18.substring(10, 12);
                var day = idCard18.substring(12, 14);
                var temp_date = new Date(year, parseFloat(month) - 1, parseFloat(day));
                // 这里用getFullYear()获取年份，避免千年虫问题   
                if (temp_date.getFullYear() != parseFloat(year)
                    || temp_date.getMonth() != parseFloat(month) - 1
                    || temp_date.getDate() != parseFloat(day)) {
                    return false;
                } else {
                    return true;
                }
            },
            /// <summary>
            /// 判断身份证号码为18位时最后的验证位是否正确   
            /// @param a_idCard 身份证号码数组   
            /// @returns boolean
            /// </summary>
            isTrueValidateCodeBy18IdCard: function (a_idCard) {
                var Wi = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1];
                // 身份证验证位值.10代表X
                var ValideCode = [1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2];
                // 声明加权求和变量   
                var sum = 0;
                if (a_idCard[17].toLowerCase() == 'x') {
                    a_idCard[17] = 10;// 将最后位为x的验证码替换为10方便后续操作   
                }
                for (var i = 0; i < 17; i++) {
                    sum += Wi[i] * a_idCard[i];// 加权求和   
                }
                var valCodePosition = sum % 11;// 得到验证码所位置   
                if (a_idCard[17] == ValideCode[valCodePosition]) {
                    return true;
                } else {
                    return false;
                }
            },
            /// <summary>
            /// 银行卡号验证 
            /// @param a_idCard 身份证号码数组   
            /// @returns boolean
            /// </summary>
            isBankCardNo: function (bankno) {
                var lastNum = bankno.substr(bankno.length - 1, 1);//取出最后一位（与luhm进行比较）

                var first15Num = bankno.substr(0, bankno.length - 1);//前15或18位
                var newArr = new Array();
                for (var i = first15Num.length - 1; i > -1; i--) {    //前15或18位倒序存进数组
                    newArr.push(first15Num.substr(i, 1));
                }
                var arrJiShu = new Array();  //奇数位*2的积 <9
                var arrJiShu2 = new Array(); //奇数位*2的积 >9

                var arrOuShu = new Array();  //偶数位数组
                for (var j = 0; j < newArr.length; j++) {
                    if ((j + 1) % 2 == 1) {//奇数位
                        if (parseInt(newArr[j]) * 2 < 9)
                            arrJiShu.push(parseInt(newArr[j]) * 2);
                        else
                            arrJiShu2.push(parseInt(newArr[j]) * 2);
                    }
                    else //偶数位
                        arrOuShu.push(newArr[j]);
                }

                var jishu_child1 = new Array();//奇数位*2 >9 的分割之后的数组个位数
                var jishu_child2 = new Array();//奇数位*2 >9 的分割之后的数组十位数
                for (var h = 0; h < arrJiShu2.length; h++) {
                    jishu_child1.push(parseInt(arrJiShu2[h]) % 10);
                    jishu_child2.push(parseInt(arrJiShu2[h]) / 10);
                }

                var sumJiShu = 0; //奇数位*2 < 9 的数组之和
                var sumOuShu = 0; //偶数位数组之和
                var sumJiShuChild1 = 0; //奇数位*2 >9 的分割之后的数组个位数之和
                var sumJiShuChild2 = 0; //奇数位*2 >9 的分割之后的数组十位数之和
                var sumTotal = 0;
                for (var m = 0; m < arrJiShu.length; m++) {
                    sumJiShu = sumJiShu + parseInt(arrJiShu[m]);
                }

                for (var n = 0; n < arrOuShu.length; n++) {
                    sumOuShu = sumOuShu + parseInt(arrOuShu[n]);
                }

                for (var p = 0; p < jishu_child1.length; p++) {
                    sumJiShuChild1 = sumJiShuChild1 + parseInt(jishu_child1[p]);
                    sumJiShuChild2 = sumJiShuChild2 + parseInt(jishu_child2[p]);
                }
                //计算总和
                sumTotal = parseInt(sumJiShu) + parseInt(sumOuShu) + parseInt(sumJiShuChild1) + parseInt(sumJiShuChild2);

                //计算Luhm值
                var k = parseInt(sumTotal) % 10 == 0 ? 10 : parseInt(sumTotal) % 10;
                var luhm = 10 - k;

                if (lastNum == luhm) {
                    //$("#banknoInfo").html("Luhm验证通过");
                    return true;
                }
                else {
                    //$("#banknoInfo").html("银行卡号必须符合Luhm校验");
                    return false;
                }
            },
            /// <summary>
            /// 验证营业执照是否合法 
            /// @param busCode 营业执照号码   
            /// @returns boolean
            /// </summary>
            isBusinessLicense: function (busCode) {
                var ret = false;
                if (busCode.length == 15) {
                    var sum = 0;
                    var s = [], p = [], a = [], m = 10;
                    p[0] = m;
                    for (var i = 0; i < busCode.length; i++) {
                        a[i] = parseInt(busCode.substring(i, i + 1), m);
                        s[i] = (p[i] % (m + 1)) + a[i];
                        if (0 == s[i] % m) {
                            p[i + 1] = 10 * 2;
                        } else {
                            p[i + 1] = (s[i] % m) * 2;
                        }
                    }
                    if (1 == (s[14] % m)) {
                        ret = true;
                    } else {
                        ret = false;
                    }
                } else {
                    ret = false;
                }
                return ret;
            },
            /// <summary>
            /// 数据库重复验证  
            /// </summary>
            isRegister: function (params) {
                var bdata = true;
                $.ajax({
                    type: "get",
                    url: params.url.indexOf("?") == -1 ? params.url + "?m=" + Math.random() : params.url + "&m=" + Math.random(),
                    data: params.data,
                    async: false,
                    dataType: "json",
                    success: function (backdata) {
                        bdata = backdata
                    }
                });
                return bdata;
            }
  
};