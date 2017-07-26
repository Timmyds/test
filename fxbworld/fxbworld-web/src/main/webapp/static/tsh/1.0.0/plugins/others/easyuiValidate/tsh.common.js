tshSearchCondition = {
    stringType: [{
        conditionText: "等于",
        conditionValue: "eq"
    },
    {
        conditionText: "包含",
        conditionValue: "contain",
        selected: true
    }],
    numberType: [{
        conditionText: "等于",
        conditionValue: "eq",
        selected: true
    },
    {
        conditionText: "不等于",
        conditionValue: "ne"
    },
    {
        conditionText: "大于",
        conditionValue: "gt"
    },
    {
        conditionText: "大于等于",
        conditionValue: "ge"
    },
    {
        conditionText: "小于",
        conditionValue: "lt"
    },
    {
        conditionText: "小于等于",
        conditionValue: "le"
    }],
    dateType: [{
        conditionText: "等于",
        conditionValue: "eq",
        selected: true
    },
    {
        conditionText: "不等于",
        conditionValue: "ne"
    },
    {
        conditionText: "大于",
        conditionValue: "gt"
    },
    {
        conditionText: "大于等于",
        conditionValue: "ge"
    },
    {
        conditionText: "小于",
        conditionValue: "lt"
    },
    {
        conditionText: "小于等于",
        conditionValue: "le"
    }],
    dropdownType: [{
        conditionText: "等于",
        conditionValue: "eq",
        selected: true
    },
    {
        conditionText: "包含",
        conditionValue: "contain"
    }]
};

/*
 * 查询表达式值类型
 */
tshConditionTypeEnum = {
    Number: "Number",
    Text: "Text",
    Object: "Object"

};

/*
 * 全局变量
 */
tshGlobal = {
	//等于
	eqOperatorCode:"eq",
	//大于等于运算编码
    geOperatorCode:"ge",
    //小于等于运算编码
    leOperatorCode:"le",
    //包含运算编码
    containOperatorCode:"contain",
    //往来单位类别
    platformCode:0,//平台
    supplierCode:1,//供应商
    storeCode:2,//中心门店
    childStoreCode:3,//二级门店
    siteCode:4,//网点
    expressCode:5,//物流
    dealCompanyType: [
    {
        typeId: -1,
        descript: "--选择所有--",
        selected:true
    },
    {
        typeName:"platform",
        typeId: 0,
        descript: "平台"
    },
    {
    	typeName:"supplier",
        typeId: 1,
        descript: "供应商"
    },
    {
    	typeName:"store",
        typeId: 2,
        descript: "中心门店"
    },
    {
        typeName:"childStore",
        typeId: 3,
        descript: "二级门店"
    },
    {
    	typeName:"site",
        typeId:4,
        descript: "网点"
    },
    {
    	typeName:"express",
        typeId: 5,
        descript: "物流"
    }],
    dealCompanyTypeOfPlatform: [//平台用户
                      {
                          typeId: -1,
                          descript: "--选择所有--",
                          selected:true
                      },
                      {
                      	typeName:"supplier",
                          typeId: 1,
                          descript: "供应商"
                      },
                      {
                      	typeName:"store",
                          typeId: 2,
                          descript: "中心门店"
                      },
                      {
                          typeName:"childStore",
                          typeId: 3,
                          descript: "二级门店"
                      },
                      {
                      	typeName:"site",
                          typeId:4,
                          descript: "网点"
                      },
                      {
                      	typeName:"express",
                          typeId: 5,
                          descript: "物流"
                      }],
    dealCompanyTypeOfCenterStore: [//中心门店
                                      {
                                          typeId: -1,
                                          descript: "--选择所有--",
                                          selected:true
                                      },
                                      {
                                          typeName:"platform",
                                          typeId: 0,
                                          descript: "平台"
                                      },
                                      {
                                      	typeName:"supplier",
                                          typeId: 1,
                                          descript: "供应商"
                                      },
                                     
                                      {
                                          typeName:"childStore",
                                          typeId: 3,
                                          descript: "二级门店"
                                      },
                                      {
                                      	typeName:"site",
                                          typeId:4,
                                          descript: "网点"
                                      },
                                      {
                                      	  typeName:"express",
                                          typeId: 5,
                                          descript: "物流"
                                      }],  
    dealCompanyTypeOfNetPoint: [//网点
                                    {
                                        typeId: -1,
                                        descript: "--选择所有--",
                                        selected:true
                                    },
                                    {
                                        typeName:"platform",
                                        typeId: 0,
                                        descript: "平台"
                                    },
                                    {
                                    	typeName:"supplier",
                                        typeId: 1,
                                        descript: "供应商"
                                    },
                                    {
                                    	typeName:"store",
                                        typeId: 2,
                                        descript: "中心门店"
                                    },
                                    {
                                    	typeName:"express",
                                        typeId: 5,
                                        descript: "物流"
                                    }],
    dealCompanyTypeOfAccountCenter:[
                                     {
                                         typeId: -1,
                                         descript: "--选择所有--",
                                         selected:true
                                     },                                  
                                     {
                                     	typeName:"supplier",
                                         typeId: 1,
                                         descript: "供应商"
                                     },
                                     {
                                      	typeName:"store",
                                          typeId: 2,
                                          descript: "中心门店"
                                      },
                                     {
                                         typeName:"childStore",
                                         typeId: 3,
                                         descript: "二级门店"
                                     },
                                     {
                                     	typeName:"site",
                                         typeId:4,
                                         descript: "网点"
                                     }
                                     ],
    reportPlatformSupllier:[//平台供应商
                             {
                                 typeId: -1,
                                 descript: "--选择所有--",
                                 selected:true
                             },
                             {
                                 typeName:"platform",
                                 typeId: 0,
                                 descript: "平台"
                             },
                             ],
    reportCenterStore:[//中心门店
                          {
                              typeId: -1,
                              descript: "--选择所有--",
                              selected:true
                          },
                          {
                              typeName:"platform",
                              typeId: 0,
                              descript: "平台"
                          },
                          {
                          	typeName:"supplier",
                              typeId: 1,
                              descript: "供应商"
                          },
                          {
                              typeName:"childStore",
                              typeId: 3,
                              descript: "二级门店"
                          },
                          {
                          	typeName:"site",
                              typeId:4,
                              descript: "网点"
                          }
                          ],
    reportSupplier:[//本地供应商
                        {
                            typeId: -1,
                            descript: "--选择所有--",
                            selected:true
                        },                     
                        {
                        	typeName:"store",
                            typeId: 2,
                            descript: "中心门店"
                        }
                        ],
    reportNetPoint:[//网点
                        {
                            typeId: -1,
                            descript: "--选择所有--",
                            selected:true
                        },
                        {
                            typeName:"platform",
                            typeId: 0,
                            descript: "平台"
                        },
                        {
                         	typeName:"store",
                             typeId: 2,
                             descript: "中心门店"
                         }
                        ],
    reportChildStore:[//二级门店
                      {
                          typeId: -1,
                          descript: "--选择所有--",
                          selected:true
                      },
                      {
                          typeName:"platform",
                          typeId: 0,
                          descript: "平台"
                      },
                      {
                       	typeName:"store",
                           typeId: 2,
                           descript: "中心门店"
                      }
                    ],
   //业务类型    
    businessType: [{ 
        typeId: -1,
        descript: "--选择所有--",
        selected:true
    },{
        typeId: 1,
        descript: "销售订单"
    },
    {
        typeId: 2,
        descript: "采购单"
    },
    {
        typeId: 3,
        descript: "销售退货单"
    },
    {
        typeId: 4,
        descript: "采购退货单"
    },
    {
        typeId: 5,
        descript: "物流"
    },
    {
        typeId: 6,
        descript: "充值"
    },
    {
        typeId: 7,
        descript: "提现"
    },
    {
    	typeId:8,
    	descript:"充值赠送"
    }
    ],
    /**
     * 应收应付业务类别
     */
    billBusinessType: [{ 
        typeId: -1,
        descript: "--选择所有--",
        selected:true
	    },{
	        typeId: 1,
	        descript: "销售订单"
	    },
	    {
	        typeId: 2,
	        descript: "采购单"
	    },
	    {
	        typeId: 3,
	        descript: "销售退货单"
	    },
	    {
	        typeId: 4,
	        descript: "采购退货单"
	    },
	    {
	        typeId: 5,
	        descript: "物流"
	    }],
    //收款单状态
    gatherStatus: [
     {
        typeId: "",
        descript: "--选择所有--",
        selected:true
    },
    {
        typeId: 0,
        descript: "待复核"
    },
    {
        typeId: 1,
        descript: "待审核"
    },
    {
        typeId: 2,
        descript: "已收款"
    },
    {
	    typeId: 3,
	    descript: "已冲销"
	 },
	 {
		 typeId: -2,
	     descript: "审核未通过"
	 }],
    //付款状态
    payStatus:[
        {
          typeId: -1,
          descript: "--选择所有--",
          selected:true
       },
       {
		    typeId: 0,
		    descript: "待复核"
		},
		{
		    typeId: 1,
		    descript: "待审核"
		},
		{
		    typeId: 2,
		    descript: "已付款"
		},
		{
		    typeId: 3,
		    descript: "已冲销"
		 },
		 {
			 typeId: -2,
			 descript: "审核未通过"
		 }],
    //收款OR付款审核状态 
	billStatus:{
		UnGeneratePay:0, //待复核
		UnExamine:1,     //待审核
		HasPay:2,	     //已付款
		HasWriteOff:3,   //已冲销
		UnPass:-2        //审核未通过
	},
	BillType:{
		Receive:1, //收款单
		Pay:2      //付款单
	},
	BackOrderStatus:{
		Normal:0,   //"未退货"),
		PartBack:1, // "部份退货"),
		AllBack:2,  // "全部退货"),
		WaitAudit:3 // "退货待审核");
	},
	FrozenStatus:{
		UnFrozen:0,//未冻洁
		Frozen:1//冻洁
	
	},
	storeType:[
		 {
             typeId: -1,
             descript: "--选择所有--",
             selected:true
         },
         {
             typeName:"store",
             typeId:2 ,
             descript: "中心门店"
         },
         {
         	typeName:"childStore",
             typeId: 3,
             descript: "二级门店"
         },
         {
             typeName:"site",
             typeId: 4,
             descript: "网店"
         }],
    settleStatus:[{
	                 typeId: -1,
	                 descript: "--选择所有--",
	                 
	             },{
	
	                 typeId:0 ,
	                 descript: "未生成单据",
	                 selected:true
	             },{
	       
	                 typeId: 1,
	                 descript: "已生成单据"
	             }
     ],
    billAccounts:[{
            typeId: -1,
            descript: "--选择所有--",
            selected:true
        },{
  
            typeId: 3,
            descript: "分润账户"
        },{
  
            typeId: 4,
            descript: "利润账户"
        },{
  
            typeId: 5,
            descript: "采购账户",
        },{
  
            typeId: 6,
            descript: "银行账户",
        },{
  
            typeId: 7,
            descript: "现金账户",
        },{
  
            typeId: 8,
            descript: "补贴差价账户",
        },{
  
            typeId: 9,
            descript: "物流费用",
        }],
    /**
     * 应收应付收款项目
     */
    billIncomeSubject:[{
            typeId: -1,
            descript: "--选择所有--",
            selected:true
        },{
  
            typeId: 2,
            descript: "销售扣款"
        },{
  
            typeId: 3,
            descript: "采购"
        },{
  
            typeId: 4,
            descript: "物流"
        },{
  
            typeId: 6,
            descript: "销售退款",
        },{
  
            typeId: 7,
            descript: "采购退款",
        },{
  
            typeId: 9,
            descript: "二次销售",
        },{
  
            typeId: 10,
            descript: "现金收银",
        },{
  
            typeId: 11,
            descript: "退还现金",
        },{
  
            typeId: 12,
            descript: "销售分润",
        },{
  
            typeId: 13,
            descript: "销售分润冲销",
        }]
};

var tshFunction={
	/**
	 * 验证是否有冻结状态的
	 * @param rows
	 */
	validationFrozen:function(rows){
		var flag=true;
		var businessIds=[];
		for(var i=0;i<rows.length;i++){
	    	 if(rows[i].frozenStatus==1){
	    		 flag=false;
	    		 businessIds.push(rows[i].businessId);
	    	  }
	     }
		return {'result':flag,'businessIds':businessIds};
	}	
};
/*
 * easy 单元格formatter(格式化器)函数
 */
var easyFormatter = {
    //根据keyID获取单位来类别 
    dealCompanyType: function (value, row, index) {
        switch (value) {
            case 0:
                return "平台";
                break;
            case 1:
                return "供应商";
                break;
            case 2:
                return "中心门店";
                break;
            case 3:
                return "二级门店";
                break;
            case 4:
                return "网点";
                break;
            case 5:
                return "物流";
                break;
            default:
                return value;
                break;
        }
    },
    //根据keyID获取业务 
    businessType:function(value, row, index){
    	var val=(value||"").toLocaleString();
    	 switch (val) {
           case "1":
               return "销售订单";
               break;
           case "2":
               return "采购单";
               break;
           case "3":
               return "销售退货单";
               break;
           case "4":
               return "采购退货单";
               break;
           case "5":
               return "物流";
               break;
           case "6":
               return "充值";
               break;
           case "7":
               return "提现";
               break;
           default:
               return value;
               break;
       }
    },
    //收款状态
    gatherStatus:function(value, row, index){
    	 switch (value) {
    	 case 0:
             return "未生成单据";
             break;
         case 1:
             return "未审核";
             break;
         case 2:
             return "已付款";
             break;
         case 3:
             return "已冲销";
             break;
         case -2:
             return "审核不通过";
             break;
         default:
             return value;
             break;
     }
    },
    //付款状态
   payStatus:function(value, row, index){
   	 switch (value) {
      	case 0:
            return "未生成单据";
            break;
        case 1:
            return "未审核";
            break;
        case 2:
            return "已收款";
            break;
        case 3:
            return "已冲销";
            break;
        case -2:
            return "审核未通过";
            break;
        default:
            return value;
            break;
     }
   },
   backOrderStatus:function(value,row,index){  
	   switch (value) {
     	case 0:
           return "未退货";
           break;
       case 1:
           return "部份退货(<span style='color:red'>"+row.backCount+"</span>)";
           break;
       case 2:
           return "全部退货";
           break;
       case 3:
           return "退货待审核";
           break;
       default:
           return "";
           break;
    }
   },
   frozenStatus:function(value,row,index){

	   switch (value) {
    	case 0:
          return "正常";
          break;
      case 1:
          return "<span style='color:red'>冻结</span>";
          break;
      default:
          return "";
          break;
     } 
   },
   frozenStatusUsing:function(value,row,index){
	 if(value=="冻结"){
		   return  "<span style='color:red'>冻结</span>";
	  }else{
		   return value;
	  }
   },
   //EasyUI用DataGrid用日期格式化
   dateFormat: function (value, rec, index) {
	   if (!value) return value;
       return dateFormat(value,"yyyy-MM-dd HH:mm:ss"); 

   },
   //将数值四舍五入(保留2位小数)后格式化成金额形式
   formatCurrency:function(value, rec, index){
	   if(!value) return "--";
	    value = value.toString().replace(/\$|\,/g,'');
	    if(isNaN(value))
	    	value = "0";
	    sign = (value == (value = Math.abs(value)));
	    value = Math.floor(value*100+0.50000000001);
	    cents = value%100;
	    value = Math.floor(value/100).toString();
	    if(cents<10)
	    cents = "0" + cents;
	    for (var i = 0; i < Math.floor((value.length-(1+i))/3); i++)
	    	value = value.substring(0,value.length-(4*i+3))+','+
	    	value.substring(value.length-(4*i+3));
	    return (((sign)?'':'-') + value + '.' + cents); 
   }, 
   //所属店铺
   belongStore:function(value,rowData,rowIndex)
   {
	   if(rowData.childStoreName)
		{
		   return rowData.childStoreName;
		}
	   else
		{
		    return rowData.storeName;
		}
   },
   statusFormat:function(value,rowData,rowIndex)
   {
	   if(value=="生成付款单")
		   {
		    return "复核";
		   }
	   else
		   {
		    return value;
		   }
   }

};

var easyuiCombobox={
    //绑定定combobox下拉列表
	bind:function(id, url, valueField, textField, editable){
	  $.getJSON(url, function (json) {
	        $('#' + id).combobox({
	            data: json.data,
	            valueField: valueField,
	            textField: textField,
	            editable: editable
	        });
	    });
	},
	//绑定收支项目
	bindIncomeSubject:function(id){$.getJSON('/finance/balanceproject/getAllBalanceProjectList.do',
	    function (json) {
		   if(json.data){
			   json.data.insert(0,{id:-1,projectName:'--选择所有--',selected:true});
		   }
		   if(id.indexOf("#")==-1){
			   id="#"+id;
		   }
	        $(id).combobox({
	            data: json.data,
	            valueField: "id",
	            textField: "projectName",
	            editable: false,
	            panelHeight:'auto'
	        });
	    });
	},
	bindBillIncomeSubject:function(id){
		 if(id.indexOf("#")==-1){
			   id="#"+id;
		   }
	        $(id).combobox({
	            data:tshGlobal.billIncomeSubject ,
	            valueField: "typeId",
	            textField: "descript",
	            editable: false,
	            panelHeight:'auto'
	        });
	},
	//绑定往来单位类型
	bindDealCompanyType:function(id,userTypeId){
		if(id.indexOf("#")==-1){
			   id="#"+id;
		 }
		var dataArray=null;
		if(userTypeId==0){
			dataArray=tshGlobal.dealCompanyTypeOfPlatform;
	    }
		else {
			dataArray=tshGlobal.dealCompanyTypeOfCenterStore;
	     }
		$(id).combobox({
            data:dataArray,
            valueField: 'typeId',
            textField: 'descript',
            editable: false,
            panelHeight:'auto'
        });
	},
	//绑定账款报表往来单位类型
	bindDealCompanyTypeOFReport:function(id,userTypeId){
		if(id.indexOf("#")==-1){
			   id="#"+id;
		 }
		switch(userTypeId)
		{
		 case 0:
			 $(id).combobox({
		            data:tshGlobal.dealCompanyType,
		            valueField: 'typeId',
		            textField: 'descript',
		            editable: false,
		            panelHeight:'auto'
		        });
			 break;
		 case 1:
    		   var organizationId=userLoginInfo.currentUser.organizationId;
    		   $.post("/finance/company/ getsupplierType.do",{supplierId:organizationId},function(r){
    			   var result=r-0;
    			   if(result!=2)//平台供应商
    				   {
    				   $(id).combobox({
    			            data:tshGlobal.reportPlatformSupllier,
    			            valueField: 'typeId',
    			            textField: 'descript',
    			            editable: false,
    			            panelHeight:'auto'
    			        });
    				   }
    			   else
    				   {
    				   $(id).combobox({
   			            data:tshGlobal.reportSupplier,
   			            valueField: 'typeId',
   			            textField: 'descript',
   			            editable: false,
   			            panelHeight:'auto'
   			        });
    				   }
    		   });
    		 break;
		 case 2:
			 $(id).combobox({
		            data:tshGlobal.reportCenterStore,
		            valueField: 'typeId',
		            textField: 'descript',
		            editable: false,
		            panelHeight:'auto'
		        });
			 break;
		 case 3:
		 case 4:
			 $(id).combobox({
		            data:tshGlobal.reportNetPoint,
		            valueField: 'typeId',
		            textField: 'descript',
		            editable: false,
		            panelHeight:'auto'
		        });
			 break;
			
		}
	},
	//绑定核算账户
	bindAccounting:function(id){
		if(id.indexOf("#")==-1){
			   id="#"+id;
		 }
		$.getJSON('/finance/fundsaccount/queryAllAccounts.do',
		    function (json) {
			   if(json.data){
				   json.data.insert(0,{id:-1,accountName:'--选择所有--',selected:true});
			   }
		       $(id).combobox({
		            data: json.data,
		            valueField: "id",
		            textField: "accountName",
		            editable: false,
		            panelHeight:'auto'
		       });
	    });
	},
	//绑定核算账户
	bindBillAccounting:function(id){
		if(id.indexOf("#")==-1){
			   id="#"+id;
		 }
		 $(id).combobox({
	            data: tshGlobal.billAccounts,
	            valueField: "typeId",
	            textField: "descript",
	            editable: false,
	            panelHeight:'auto'
	       });
	},
	//绑定账户单位
	bindAccountUnit:function(userTypeId){
		if(userTypeId==0){
			$("#accountUnitType").combobox({
	            data:tshGlobal.dealCompanyType,
	            valueField: 'typeId',
	            textField: 'descript',
	            editable: false,
	            panelHeight:'auto'
	        });
		}
 		else if (userTypeId == 2) {
			$("#accountUnitType").combobox({
				data : tshGlobal.dealCompanyTypeOfAccountCenter,
				valueField : 'typeId',
				textField : 'descript',
				editable : false,
				panelHeight : 'auto'
			});
		}
	},
	// 绑定查询表条件表达式
	bindSearchCondition:function(id, data, valueField, textField){
		if(id.indexOf("#")==-1){
			   id="#"+id;
		 }
		 $(id).combobox({
		        data: data,
		        valueField: valueField || 'conditionValue',
		        textField: textField || 'conditionText',
		        panelHeight:'auto'
		  });
	},
	//绑定业务类型
	bindBusinesstype:function(id){
		if(id.indexOf("#")==-1){
			   id="#"+id;
		 }
		 $(id).combobox({
         	data: tshGlobal.businessType,
             valueField: 'typeId',
             textField: 'descript',
             editable: false,
             panelHeight:'auto'
         });
	},
	bindBillBusinessType:function(id){
		if(id.indexOf("#")==-1){
			   id="#"+id;
		 }
		 $(id).combobox({
      	data: tshGlobal.billBusinessType,
          valueField: 'typeId',
          textField: 'descript',
          editable: false,
          panelHeight:'auto'
      });	
	},
	//绑定收款项
	bindGatherStatus:function(id){
		if(id.indexOf("#")==-1){
			   id="#"+id;
		 }
		$(id).combobox({
        	data: tshGlobal.gatherStatus,
            valueField: 'typeId',
            textField: 'descript',
            editable: false
        });	
	},
	//绑定付款项
	bindPayStatus:function(id){
	   if(id.indexOf("#")==-1) id="#"+id;
		$(id).combobox({
     	 data: tshGlobal.gatherStatus,
         valueField: 'typeId',
         textField: 'descript',
         editable: false
     });	
	},
	/**
	 * 绑定店铺类型
	 * @param id
	 */
	bindStoreType:function(id){
		if(id.indexOf("#")==-1) id="#"+id;
		$(id).combobox({
	     	data: tshGlobal.storeType,
	         valueField: 'typeId',
	         textField: 'descript',
	         editable: false,
	         panelHeight:'auto'
	     });	
	},
	/**
	 * 绑定单据状态
	 * @param id
	 */
	bindSettleStatus:function(id){
		if(id.indexOf("#")==-1) id="#"+id;
		$(id).combobox({
	     	data: tshGlobal.settleStatus,
	         valueField: 'typeId',
	         textField: 'descript',
	         editable: false,
	         panelHeight:'auto'
	     });	
	}
}; 


/**
 * 获取url参数
 */
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    var context = "";
    if (r != null) context = r[2];
    reg = null;
    r = null;
    return context == null || context == "" || context == "undefined" ? "" : context;
}

//设置当前窗口url中param的值
function setQueryString(param,value){
    var query = location.search.substring(1);
    var p = new RegExp("(^|&"+param+")=[^&]*");
    if(p.test(query)){
        query = query.replace(p,"$1="+value);
        location.search = '?'+query;
    }else{
        if(query == ''){
            location.search = '?'+param+'='+value;
        }else{
            location.search = '?'+query+'&'+param+'='+value;
        }
    }   
}
/**
 *绑定判断条件  id控件ID type数据类型
 */
function bindCondition(id,type){
    switch(type)
    {
        case "number":
            $("#"+id).combobox({
                data: tshSearchCondition.numberType,
                valueField: 'conditionValue',
                textField: 'conditionText',
                editable: false
            });
            break;
        case "string":
            $("#"+id).combobox({
                data: tshSearchCondition.stringType,
                valueField: 'conditionValue',
                textField: 'conditionText',
                editable: false
            });
            break;
        case "dropdown":
            $("#"+id).combobox({
                data: tshSearchCondition.dropdownType,
                valueField: 'conditionValue',
                textField: 'conditionText',
                editable: false
            });
            break;
    }
}
   

/**
 * 数组插入扩展方法
 */
Array.prototype.insert = function (index, item) {  
    this.splice(index, 0, item);  
 }; 
 
 /**
 *  方法:Array.remove(dx) 通过遍历,重构数组
 *  功能:删除数组元素.
 *  参数:dx删除元素的下标.
 */
 Array.prototype.remove=function(index){
     if(isNaN(index)||index>this.length){
    	 return false;
     }
     for(var i=0,n=0;i<this.length;i++){
         if(this[i]!=this[index]){
             this[n++]=this[i];
         }
     }
     this.length-=1;
 }; 
 /**
  *将豪秒转抱成指定的格式 
  *@millisecond: 豪秒
  *@format：格式化字符
  *  用法示意：dateFormat(1433232328000,"yyyy-MM-dd HH:mm") 
  */
   var dateFormat = function(millisecond, format){
       var t = new Date(millisecond);
       var tf = function(i){return (i < 10 ? '0' : '') + i;};
   return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function(a){
       switch(a){
           case 'yyyy':
               return tf(t.getFullYear());
               break;
           case 'MM':
               return tf(t.getMonth() + 1);
               break;
           case 'mm':
               return tf(t.getMinutes());
               break;
           case 'dd':
               return tf(t.getDate());
               break;
           case 'HH':
               return tf(t.getHours());
               break;
           case 'ss':
                 return tf(t.getSeconds());
             break;
           }
       });
   };
   /**
    * 动态创建表单    
    * @formId             表单ID
    * @url                请求地址
    * @type               请求类型
    * @data               提交参数
   */
   var tshForm = {
   	//创建表单
   	createForm: function (options) {
   		var form = document.createElement("form");
   		form.action =options.url;
   		form.method = options.type || "post";
   		form.target = "_blank";
   		form.style.display="none";
   		//设置控件ID
   		form.id=options.formId||"form_"+Math.random();;

   	   //提交参数
   	   for (var item in options.data) {
          var el = document.createElement("input");
          el.setAttribute("name", item);
          el.setAttribute("type", "hidden");
          el.setAttribute("value", options.data[item]);
          form.appendChild(el);
      }
   	  return form;
     }
   };
   
   var viewDialog={
		   /**
		    * 打开销售订单明细页面
		    * @param serialNumber
		    */
		   openSealDetailView:function(serialNumber){
			  this.openOrderDetailView(1,serialNumber);
		   },
		   /**
		    * 打开销售退货单明细页面
		    * @param serialNumber
		    */
		   openBackSealDetailView:function(serialNumber){
			   this.openOrderDetailView(3,serialNumber);  
		   },
		   /**
		    * 打开采购单明细页面
		    * @param serialNumber
		    */
		   openPurDetailView:function(serialNumber){
			   this.openOrderDetailView(2,serialNumber); 
		   },
		   /**
		    * 打开采购退货单明细页面
		    * @param serialNumber
		    */
		   openBackPurDetailView:function(serialNumber){
			   this.openOrderDetailView(4,serialNumber); 
		   },
		   /**
		    * 打开打单明细页面
		    * @param type  1：销售订单  2:采购单  3:销售退货单  4:采购退货单
		    * @param serialNumber
		    */
		   openOrderDetailView:function(type,serialNumber){
			 var viewUrl="";
		     switch(type){
		           case 1:
		           case 3:
		        	   viewUrl="/finance/sealOrderDetails.do";
		               break;
		           case 2:
		           case 4:
		        	   viewUrl="/finance/purchaseOrderDetails.do";
		               break;
		           default:  
		               break;
	           }
			   $('#dialog_OrderDetail').dialog({    
		              title: '查看订单明细',   
		              closed:false,
		              width: 860,    
		              height: 500,    
		              modal: true ,
		              href:viewUrl,
		              onOpen:function(){
		            	  var typeEl=document.getElementById("dialog_OrderDetail_type");
		            	  var serialNumberEl=document.getElementById("dialog_OrderDetail_serialNumber");
		            	  if(!typeEl){
		            		  typeEl = document.createElement("input"); 
		            		  typeEl.id="dialog_OrderDetail_type";
			            	  typeEl.type="hidden";
			            	  typeEl.value=type;
			                  document.body.appendChild(typeEl);
		            	  }else{
		            		  typeEl.value=type;  
		            	  }
		            	  if(!serialNumberEl){
		            		  serialNumberEl = document.createElement("input"); 
		            		  serialNumberEl.id="dialog_OrderDetail_serialNumber";
		            		  serialNumberEl.type="hidden";
		            	  	  serialNumberEl.value=serialNumber;
		                      document.body.appendChild(serialNumberEl);
		            	  }else{
		            	     serialNumberEl.value=serialNumber;
		            	  }
		             }
		         }); 
		   },
		   /**
		    * 是否可以查看订单明细  1：销售订单  2:采购单  3:销售退货单  4:采购退货单 
		    */
		   isOrderDetailView:function(type){
			   if(type==1||type==2||type==3||type==4){
				   return true;
			   }
			   return false;
		   }
   };
   
   /**
    * 提示信息
    */
   tipBox={
        error: function (msg) {
            if (msg == undefined || msg == null) {
                msg = "操作失败";
            }

            if (!$('.msgbox_layer_wrap_e').length) {
                var remindhtml = "<div class='msgbox_layer_wrap_e' style='display:none;' >"
                + "<span style='z-index: 10000;' class='msgbox_layer_e'><span class='gtl_ico_succ_e'></span>"
                + msg + "<span class='gtl_end_e'></span></span></div>";
                $(remindhtml).appendTo('body');
            }

            var remind = $('.msgbox_layer_wrap_e');
            remind.fadeIn('slow');

            setTimeout("$('.msgbox_layer_wrap_e').fadeOut('slow');", 3000);
            setTimeout("$('.msgbox_layer_wrap_e').remove();", 3000);
        },
        success: function (msg) {
            if (msg == undefined || msg == null) {
                msg = "操作成功";
            }

            if (!$('.msgbox_layer_wrap').length) {
                var remindhtml = "<div class='msgbox_layer_wrap' style='display:none;' >"
                + "<span style='z-index: 10000;' class='msgbox_layer'><span class='gtl_ico_succ'></span>"
                + msg + "<span class='gtl_end'></span></span></div>";
                $(remindhtml).appendTo('body');
            }
            var remind = $('.msgbox_layer_wrap');
            remind.fadeIn('slow');
            setTimeout("$('.msgbox_layer_wrap').fadeOut('slow');", 3000);
            setTimeout("$('.msgbox_layer_wrap').remove();", 3000);
        }
    };
   /**
    * easyui textbox 扩展方法
    */
   easyuiTextBox = {
		    //选择往来公司
		    bindDealCompany: function(dialogId, data) {
		    	if (dialogId && dialogId.indexOf("#") == -1) {
		            dialogId = "#" + dialogId;
		        }
		
		        data.dealCompany.textbox({
		            buttonText: '...',
		            onClickButton: function() {
		                $(dialogId).dialog({
		                    title: data.title || '选择往来单位',
		                    width: data.width || 800,
		                    height: data.height || 500,
		                    closed: false,
		                    cache: false,
		                    href: '/finance/selectorCompany.do?',
		                    modal: true,
		                    buttons: [{
		                        text: '确定',
		                        iconCls: 'icon-ok',
		                        handler: function() {
		                            var rows = $(data.CompanyDataGridId || '#selectorCompanyDataGrid').datagrid("getChecked");
		                            if (rows.length == 0) {
		                                $.messager.alert('提示信息', '请选择往来单位!', 'info');
		                            } else {
		                                data.dealCompany.textbox("setValue", rows[0].companyName);
		                                data.dealCompanyId.textbox("setValue", rows[0].companyId);
		                                data.dealCompanyCondition.combobox("setValue", tshGlobal.eqOperatorCode);
		                                $(dialogId).dialog('close');
		                            }
		                        }
		                    },
		                    {
		                        text: '取消',
		                        iconCls: 'icon-cancel',
		                        handler: function() {
		                            $('#dialog').dialog('close');
		                        }
		                    }]
		                });
		            }
		        }).textbox('textbox').bind("keydown",
			        function() {
			            var contactsUnitId = data.dealCompanyId.textbox("getValue");//隐藏值
			            if (contactsUnitId) {
			                data.dealCompanyId.textbox("setValue", "");
			                data.dealCompanyCondition.combobox("setValue", tshGlobal.containOperatorCode);
			            }
			        });
		     },
		     bindSelectSupplier:function(){
		    	 
		     }
		};
   function showOrHideDiv()
   {
 	  $('.showOrHideCondtions').click(function(){
       	if($('.showOrHideCondtions').text()=="收起筛选")
       		{
       		    $('.bug').hide();
       		    $('#divControl').attr('class','icon2');
       			$('.showOrHideCondtions').text('显示筛选');
       		 	$('#seach-conditions').hide();
       		}
       	else
       		{
       			$('.bug').show();
       			$('#divControl').attr('class','icon1');
           		$('.showOrHideCondtions').text('收起筛选');
           		$('#seach-conditions').show();
       		}
       	setSearchBoxHeight();
       });
   }
   function setSearchBoxHeight(){
       var body = $('body');
       var p = body.layout('panel','north');    // get the center panel
       var oldHeight = p.panel('panel').outerHeight();
       p.panel('resize', {height:'auto'});
       var newHeight = p.panel('panel').outerHeight();
       body.layout('resize',{
           height: (body.height() + newHeight - oldHeight)
       });
   };
   
   /** 
    * 使用方法: 
    * 开启:MaskUtil.mask(); 
    * 关闭:MaskUtil.unmask(); 
    *  
    * MaskUtil.mask('其它提示文字...'); 
    */
   var MaskUtil = (function () {

       var $mask, $maskMsg;

       var defMsg = '正在处理，请稍待。。。';

       function init() {
           if (!$mask) {
               $mask = $("<div class=\"datagrid-mask mymask\"></div>").appendTo("html");
           }
           if (!$maskMsg) {
               $maskMsg = $("<div class=\"datagrid-mask-msg mymask\">" + defMsg + "</div>")
                   .appendTo("body").css({ 'font-size': '12px' });
           }

           $mask.css({ width: "100%", height: $(document).height() });

           $maskMsg.css({
               left: ($(document.body).outerWidth(true) - 190) / 2, top: ($(window).height() - 45) / 2
           });

       }

       return {
           mask: function (msg) {
               if (msg) {
                   defMsg = msg;
               }
               init();
               $mask.show();
               $maskMsg.html(msg || defMsg).show();
           }
           , unmask: function () {
               $mask.hide();
               $maskMsg.hide();
           }
       }

   }());
   function mobileOrPc(mobileOrPc) {
		if ((navigator.userAgent.match(/(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i))) {
			$('#'+mobileOrPc).hide();
			return true;
	}
		else {
			$('#'+mobileOrPc).show();
			return false;
			}
	}
   /*
    * 根据页面大小设定dialog的位置
    */
   function dialogPosition(dialogId)
   {
	   var height=window.innerHeight;
	   var width=window.innerWidth;
	   $('#'+dialogId).dialog('move',{
		   left:width/4,
		   top:height/6
	   })
   }
$(function(){
	   $.extend($.fn.datebox.defaults.rules, {         
       endToStart: {
           validator: function (value, param) {               
               var startDate = $(param[0]).datebox('getValue');
               if (!startDate)
               {
                   return true;
               }
                startDate=new Date($(param[0]).datebox('getValue'));
               var endDate = new Date(value);
               return startDate>=endDate;
           },
           message: '开始值不能大于结束值'
       },
       startToEnd:{
       	 validator: function (value, param) {               
                var startDate = $(param[0]).datebox('getValue');
                if (!startDate)
                {
                    return true;
                }
                startDate=new Date($(param[0]).datebox('getValue'));
                var endDate = new Date(value);
                return startDate<=endDate;
            },
            message: '开始值不能大于结束值'
       }  

       });
});
