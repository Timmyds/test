/**
 * 数据模板绑定
 * @author 叶委
 * @param templateId       当前模板Id
 * @param appendTempId     当前数据绑定列表Id
 * @param actionUrl        当前请求列表
 * @param datas            请求提交参数 JSON 格式 
 * @param isLazyImg        图片是否懒加载
 * @returns
 */
var templateGridView = function templateGridView(templateId,appendTempId,actionUrl,datas,isLazyImg){
	this._initialize(templateId,appendTempId,actionUrl,datas,isLazyImg);
};

/**
 * 绑定数据列表
 * @param dataSource
*/
templateGridView.dataBind = function (dataSource,isLazyImg){
	templateGridView.pagInation = {          
		 page : dataSource.page + 1,     
		 pageSize :	dataSource.pageSize,            
   		 rowTotal :	dataSource.rowTotal,            
   		 pageTotal : dataSource.pageTotal
	};
    templateGridView.bindTemplate(dataSource);
    templateGridView.isFinish = true;
};

templateGridView.prototype = {
	_initialize : function(templateId,appendTempId,actionUrl,datas,isLazyImg){
		if(!actionUrl && $.trim(actionUrl).length > 0){
			alert("error:请求地址不能为空！");
			return ;
		}
		if(templateId == null || typeof $("#"+templateId) == "undefined"){
			alert("error:不存数据模板！");
			return ;
		}
		if(appendTempId == null || typeof $("#"+appendTempId) == "undefined"){
			alert("error:不存数据填充模板！");
			return ;
		}
		
		templateGridView.isFinish = null;
		templateGridView.isDataFinish = null ;                        //初始化有无数据
		templateGridView.template = $("#"+templateId);                //套用的模板对象
		templateGridView.appendTemp = $("#"+appendTempId);            //填充的模版对象
		templateGridView.isLazyImg = isLazyImg ? true : false ;       //图片懒加载效果
		templateGridView.engineTemplate.compile(templateId,null);     //预编辑模板引擎
		templateGridView.appendTemp.after(templateGridView.loading);  //加载中显示的层
        this._pagInation();                                           //初始化翻页对象
		this._bindTemplate();                                         //初始化模板方法
		this._loadData(actionUrl,datas,isLazyImg);                    //初始化加载方法
		this._loadDataOnScroll(actionUrl,datas,isLazyImg);            //绑定滚动条方法
	},_loadData: function (actionUrl,datas,isLazyImg){
		this.loadData = function (){
			this.setPagInation();
			templateGridView.laodData(actionUrl,datas,isLazyImg);
		};
	},_loadDataOnScroll:function (actionUrl,datas,isLazyImg){
		this.loadDataOnScroll = function (){
			window.onscroll = function() { 
				//采用jQuery 方式获取相关参数，提高兼容性
				var bt_Height = templateGridView.appendTemp.offset().top - $(window).scrollTop() + templateGridView.appendTemp.height();
				var windowHeight = $(window).height();
				if ( bt_Height- 10 < windowHeight) {
					templateGridView.laodData(actionUrl,datas,isLazyImg);
				}
			};
		};
	},_bindTemplate:function (){
		this.bindTemplate = function (datas){
			this.setPagInation();
			templateGridView.bindTemplate(datas);
		};
	},_pagInation:function (){
		this.pagInation ={
				 isPagInit : true ,          //是否初始化
				 page : 0 ,                  //当前页
				 pageSize :	15,              //页大小
				 rowTotal :	0 ,              //总行数
				 pageTotal : 0               //总页数
		};
		this.setPagInation = function (){
			templateGridView.pagInation = {
				 isPagInit : this.pagInation.isPagInit ,         
				 page : this.pagInation.page ,                   
				 pageSize :	this.pagInation.pageSize,            
				 rowTotal :	this.pagInation.rowTotal ,             
				 pageTotal : this.pagInation.pageTotal            
			};
		};
	}
};


/**
 * 获取传递参数，Controller,只接当前JSON对象,
 * 对象里面的属性 用字基本 符串代替  
 * @param datas
 */
templateGridView.getParams = function (datas){
	var param="{";
    //翻页基本属性
    for(var attr in templateGridView.pagInation){
    	param += attr +":" + templateGridView.pagInation[attr] + ",";
    }
    //用户提交参数 
    if(datas != null ){
        $.each(datas,function (i,o){
        	param += "'" + i + "':'" + o + "',";	
        });
    }
	param += "math_num:'"+Math.random()+"'}" ;
    return  eval("(" +param +")");
};

/**
 * 绑定数据列表
 * @param dataSource
*/
templateGridView.laodData = function (actionUrl,datas,isLazyImg){
	if((templateGridView.isFinish == null || templateGridView.isFinish) && (templateGridView.isDataFinish == null || templateGridView.isDataFinish)){
		templateGridView.isFinish = false ;
		if(templateGridView.pagInation.pageTotal == 0 || (templateGridView.pagInation.page <= templateGridView.pagInation.pageTotal)){
			$("#showOther").show();
			$postByAsync(actionUrl,templateGridView.getParams(datas),
				function (backdata){
					if(backdata.datas != null && backdata.datas.length > 0){
						templateGridView.dataBind(backdata,isLazyImg);   
					}else{
						templateGridView.isDataFinish = false ;
						$("#showOther").hide();
					}
			});
		}
	}
};

/**
 * 绑定到模板
 */
templateGridView.bindTemplate = function (dataSource){
	if(dataSource != null){
		   var html = templateGridView.engineTemplate.render(templateGridView.template.attr("id"), dataSource);
		   templateGridView.appendTemp.append(html);
	}
	templateGridView.isLazyImg ? templateGridView.applyLazyImg(dataSource.page): templateGridView.disLazyImg();
    $("#showOther").hide();
};

/**
 * 设置第三方模板引擎
 */
templateGridView.engineTemplate = template ;

/**
 * 设置图片懒加载功能
 */
templateGridView.applyLazyImg = function(page){
	$("img[page = " + page + "]").lazyload({ 
 	   effect : "fadeIn",
 	   threshold : 200 ,
 	   /*failure_limit : 10,*/
 	   placeholder : WEB_ROOT + "images/loading_m.gif"
    });
};

templateGridView.disLazyImg = function(){
	var divimg = templateGridView.appendTemp.attr("id") +" img";
    $("#"+divimg).lazyload();
};


templateGridView.loading = "<div id='showOther' class='listloading' style='display:none;' >" 
	+"<img src='"+WEB_ROOT+"images/list_loading.gif'/>" 
	+"&nbsp;&nbsp;更多产品加载中...</div>";

/*templateGridView.nulldata = "<div class='baoqian f16'>抱歉，没有找到相关的产品</div>" ;*/

 





