

$(function() {
	initPwd('../../security/getPageOperatePwd.do?randnum=' + Math.random(), null);
	    $('#searchPwd').click(function() {
	        searchPwd();
	    });
});

function searchPwd() {
	var queryParams = null;
    if ($("#tbpwd").form('validate')) {
        var name = $.trim($('#loginName').textbox('getValue'));

        if (name) {
        	name = decToHex(name);
        }
       
        queryParams = {
            'loginName': name
        };
        initPwd('../../security/getPageOperatePwd.do?randnum=' + Math.random(), queryParams);
    };
}
function initPwd(url, queryParams) {
	var columns = [ [ {field : 'operateDate',title : '密码修改时间',width : 260,align : 'center',
		formatter : function(value, row, index) {
			if (null != value) {
				var date = new Date(value);
				return date.pattern("yyyy-MM-dd HH:mm:ss");
			} else {
				value = '--';
				return value;
			}
		}
	},{field : 'operateAddress',title : '登录地点',width : 280,align : 'center'
	}, {field : 'operateIp',title : '登录IP',width : 230,align : 'center'
	},{field : 'operateUserName',title : '操作人',width : 230,align : 'center'
	} ] ];

	 $('#dataGridpwd').datagrid({
         url: url,
         queryParams: queryParams,
         rownumbers: true, // 行号
         singleSelect: true, // 是否单选
         pagination: true, // 分页控件
         pageSize: 15,
         pageList: [15, 30, 50],
         autoRowHeight: true,
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
         
         	 $(".datagrid-row").css("height", "45px");
         	
         },
         toolbar: '#tbpwd'
     });
}




