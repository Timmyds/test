require.config({
	baseUrl: 'js',
	paths: {
		'jquery': 'libs/jquery/jquery-1.11.3.min',
		'dialog': 'libs/jquery/jquery.dialog'
	},
	shim: {
		'jquery': {
			exports: '$'
		},
		'dialog': ['jquery']
	}
});
define("dialogAddMethod", ["jquery", "dialog", "validate", "validator.addMethod"], function($, dialog) {

	return {
		topUpConfirm:function(czstr, zsstr, yuAccMoney, str,type, parameter) { //充值申请
			var htmlTemplate = "<form id='login-form'><div class='next-price' style='line-height:64px;'></div><div class='type' style='margin-top:10px;width:400px;line-height:35px;'><img style='float:left;' src='../src/images/working.gif'></img><center>支付完成前，请不要关闭此支付验证窗口。<br/>支付完成后，请根据您支付的情况点击下面<br/>按钮。</center></div><div style='margin:0 auto'><a  id='btn-login-error' href='javascript:;' class='btn-login' style='float:left;margin:70px 0 20px 100px;width:100px;align:center;font-size:15px;background-color:gray;'>遇到支付问题</a><a  id='btn-login-ok' href='javascript:;' class='btn-login' style='float:left;margin:70px 0 20px 30px;font-size:15px;'>完成支付</a></div></form>";
			var topUpApplyDialog = $.dialog({
				title: '充值支付提示',
				style: 'dialog-general',
				content: htmlTemplate,
				cancel: function() {
					self.location.href = "#/account";
					return;
				},
				lock: true
			});
			$("#btn-login-ok").click(function(){
				$.ajax({
                  url: '/account/checkPaid.do' ,
                  data: 'payRecordId=' + czstr,
                  type: 'post' ,
                  success: function (context){
                	 console.log(context.paidStat);
                     if ('1' ==context.paidStat) {
                    	 console.log("redirect successed page!");
                    	 topUpApplyDialog.close();
                         self.location = "#/yuepaysucceed?orderno=" + context.orderNo + "&storeName=" + context.storeName + "&payInfo=" + context.payInfo;
                     }
                     else if ('2' == context.paidStat) {
                    	 console.log("redirect error page");
                    	 topUpApplyDialog.close();
                         self.location = "#/yuepayfailed?orderno=" + context.orderNo + "&storeName=" + context.storeName + "&paidStat=" + context.paidStat;
                     } else {
                    	 console.log("redirect initation page");
                    	 topUpApplyDialog.close();
                    	 self.location = "#/yuepaywait?orderno=" + context.orderNo + "&storeName=" + context.storeName + "&paidStat=" + context.paidStat;
                     }
                  }
              });
			});
			//遇到支付问题同样处理
			$("#btn-login-error").click(function(){
				  $.ajax({
                    url: '/account/checkPaid.do' ,
                    data: 'payRecordId=' + czstr,
                    type: 'post' ,
	                success: function (context){
	                     if ('1' ==context.paidStat) {
	                    	 console.log("redirect successed page!");
	                    	 topUpApplyDialog.close();
	                         self.location = "#/yuepaysucceed?orderno=" + context.orderNo + "&storeName=" + context.storeName + "&payInfo=" + context.payInfo;
	                     }
	                     else if ('2' == context.paidStat) {
	                    	 console.log("redirect error page");
	                    	 topUpApplyDialog.close();
	                         self.location = "#/yuepayfailed?orderno=" + context.orderNo + "&storeName=" + context.storeName + "&paidStat=" + context.paidStat;
	                     } else {
	                    	 console.log("redirect initation page");
	                    	 topUpApplyDialog.close();
	                    	 self.location = "#/yuepayfailed?orderno=" + context.orderNo + "&storeName=" + context.storeName + "&paidStat=" + context.paidStat;
	                     }
	                  }
	              });
			});
		},
		topUpApply: function(czstr, zsstr, yuAccMoney, str,type, parameter) { //充值申请
				/*
				czstr 充值金额
				zsstr 赠送金额
				yuAccMoney 余额支付
				str 待支付金额
				type: 0:备付金 1：进货宝 
				parameter{
				payType: 0:自充 1:代充
				payWay:  0:微信1：财务通：3:银联在线
				payPwd: 
					payAmount: 待支付金额
					cashcouponSchemeId: 
					cashcouponMoney: 
					recordNote: 备注
					masterAccountId: 
					storeUserId: 代充
					yueAccMoney 余额支付
				}
				*/
				var topUpApplyDialog;
				$.ajax({
					url: "/account/getAccFetchApplyParam.do",
					async: false,
					dataType: 'jsonp',
					jsonp: 'callback',
					success: function(data) {
						applyData = data.data;

						temp = "<form id='login-form'><div class='next-price'>";
						if (czstr > 0) {
							temp += "<span class='next-prompt' style='margin-left:6px;'>充值金额：</span><span class='next-pri' style='margin-left:1px;font-size:15px;'>" + czstr + "</span><span class='unit'>元</span>&nbsp;|";
						}
						if (zsstr > 0) {
							temp += "<span class='next-prompt' style='margin-left:6px;'>赠送金额：</span><span class='next-pri' style='margin-left:1px;font-size:15px;'>" + zsstr + "</span><span class='unit'>元</span>&nbsp;|";
						}
						if (yuAccMoney > 0) {
							temp += "<span class='next-prompt' style='margin-left:6px;'>余额支付:</span><span class='next-pri' style='margin-left:1px;font-size:15px;'>" + yuAccMoney + "</span><span class='unit'>元</span>&nbsp;|";
						}
						if (str > 0) {
							temp += "<span class='next-prompt' style='margin-left:6px;'>待支付金额:</span><span class='next-pri' style='margin-left:1px;font-size:25px;'>" + str + "</span><span class='unit'>元</span>";
						}
						temp += "</div>" +
							"<div class='type'><span>充值店铺：</span>" + applyData.loginStoreName + "</div>" +
							"<div class='type'><span>店长：</span>" + applyData.loginStoreUserName + "</div>" +
							"<div class='type'><span>收款账户：</span>淘实惠</div>" +
							"<div class='type'><span>款项类型：</span><select id='payFundsTypeList' name='payFundsTypeList'>";

						for (var i = 0; i < applyData.payFundsTypeList.length; i++) {
							temp += "<option value='" + applyData.payFundsTypeList[i].id + "'>" + applyData.payFundsTypeList[i].name + "</option>";
						}
						temp += "</select></div>" +
							"<div class='type'><span>银行卡：</span><select id='bankInfoList' name='bankInfoList'>";
						for (var i = 0; i < applyData.bankInfoList.length; i++) {
							temp += "<option value='" + applyData.bankInfoList[i].id + "'>" + applyData.bankInfoList[i].name + "</option>";
						}
						temp += "</select></div>" +
							"<div class='type'><span>卡后四位：</span><input id='bankCard' name='bankCard'><span class='errorHit'><span></div>" +
							"<div class='type'><span>手机号码：</span><input id='tel' name='tel'><span class='errorHit'><span></div>" +
							"<input style='margin-top:16px;margin-bottom:6px;' class='btn btn-org' type='submit' value='确定'></form>",
							topUpApplyDialog = $.dialog({
								title: '充值申请',
								style: 'dialog-general',
								content: temp,
								cancelText: '关闭',
								okText: '确认',
								cancel: function() {
									self.location.href = "#/account";
									return;
								},
								lock: true
							});
						$("#login-form").validate({
							/*自定义验证规则*/
							rules: {
								payFundsTypeList: {
									required: true
								},
								bankInfoList: {
									required: true
								},
								bankCard: {
									isFourInt: true,
									required: true
								},
								tel: {
									isMobile: true,
									required: true
								}
							},
							messages: {
								payFundsTypeList: {
									required: "密码只能由6-16位数字组成"
								},
								bankInfoList: {
									required: "密码只能由6-16位数字组成"
								},
								bankCard: {
									required: "请输入银行卡后四位"
								},
								tel: {
									required: "请输入手机号码",
								}

							},
							/*错误提示位置*/
							errorPlacement: function(error, element) {
								element.next("span").html('<i class="i-errors"></i>&nbsp;');
								error.appendTo(element.next("span"));
							},
							success: function(label) {
								label.parent("span").html('<i class="i-valid"></i>');
							},
							submitHandler: function(form) {
								var payRecordObj = {
									payType: parameter.payType,
									payWay: parameter.payWay,
									payPwd: parameter.payPwd,
									payAmount: str,
									cashcouponSchemeId: parameter.cashcouponSchemeId,
									cashcouponMoney: parameter.cashcouponMoney,
									recordNote: parameter.recordNote,
									masterAccountId: parameter.masterAccountId,
									bankID: $("#bankInfoList").val(),
									payFundsType: $("#payFundsTypeList").val(),
									laterBankcard: $("#bankCard").val(),
									payMsgPhone: $("#tel").val()
								};
								
								if (yuAccMoney) {
									payRecordObj.yueAccMoney = yuAccMoney;
								}
								if (parameter.storeUserId) {
									payRecordObj.storeUserId = parameter.storeUserId;
								}
								
								var payRecord = JSON.stringify(payRecordObj);
								if(type==0){
									var payUrl = "/account/payOfBeifujinAccount.do";
								}else{
									var payUrl = "/account/payOfJinhuobaoAccount.do";
								}
								$.ajax({
									contentType: "application/jsonp; charset=utf-8",
									url: payUrl,
									async: false,
									dataType: "jsonp",
									jsonp: "callback",
									data: "payRecord=" + payRecord, //备付金为1，进货宝为2
									success: function(data) {
										if (data.status == 200) {
											$.dialog({
												content: "申请充值成功！",
												time: 1000
											});
											self.location.href = "#/account";
										} else {
											$.dialog({
												content: data.msg,
												time: 1000
											});
										}
										topUpApplyDialog.close();
									},
									error: function(err) {
										alert("支付失败！");
									}
								});

							}
						});
					}
				})
			}
	}
})