/**
 * jquery 翻页插件
 * @author 叶委
 * @param before           请求前方法
 * @param url              当前页码
 * @param currentPage      当前页码
 * @param pageSize         每页大小
 * @param totalRow         总行数
 * @param pageTotal        总页数 
 * @param data             提交参数
 * @param callback         回调方法 
 */
(function ($) {
    $.extend($.fn, {
        pager: function (options) {
            options = $.extend(options, {
                before: options.before,
                url: options.url,
                data: options.data,
                currentPage: options.currentPage,
                pageSize: options.pageSize,
                totalRow: options.totalRow,
                pageTotal: options.pageTotal,
                callback: options.callback
            });
         
            var jq_pager;
            this.each(function () {
                //获取当前元素
                var $this = $(this);

                var _options = {};
                _options["before"] = options.before;
                _options["url"] = options.url;
                _options["data"] = options.data;
                _options["currentPage"] = options.currentPage;
                _options["pageSize"] = options.pageSize;
                _options["totalRow"] = options.totalRow;
                _options["pageTotal"] = options.pageTotal;
                _options["callback"] = options.callback;

                //初始控件
                jq_pager = pagerExtend(_options, $(this));
                jq_pager.onInit(_options, $(this));
            });
            return jq_pager;
        }
    });

    /// <summary>
    /// 翻页控件
    /// </summary>
    var pagerExtend = function (options, obj) {
        $.extend(pagerExtend, {
            methods: {
                //初始化
                onInit: function (options, obj) {
                    var $this = this;
                    page_temp.pagination.currentPage = options.currentPage;
                    page_temp.pagination.pageSize = options.pageSize;
                    $this.onLoad(options, obj);
                },
                //加载数据
                onLoad: function (options, obj) {
                    var $this = this;

                    var param = {};
                    param.currentPage = page_temp.pagination.currentPage;
                    param.pageSize = page_temp.pagination.pageSize;

                    for (var attr in options.data) {
                        param[attr] = options.data[attr];
                    }
                    
                    $.ajax({
                        url: options.url,
                        beforeSend: undefined == options.before ? function () { } : options.before,
                        type: "GET",
                        dataType: "json",
                        async: false,
                        data: param,
                        success: function (data) {
                            $this.dataBind(options, data, obj);
                            options.callback(data);
                        }
                    });
                },
                //数据绑定 
                dataBind: function (options, data, obj) {
                    var $this = this;
                    page_temp.pagination.data = data.data;
                    page_temp.pagination.totalRow = data.totalRow;
                    page_temp.pagination.pageTotal = parseInt(page_temp.pagination.totalRow / options.pageSize) + (page_temp.pagination.totalRow % options.pageSize == 0 ? 0 : 1);

                    //暂无数据时候当前页设置为-1
                    var curPage = page_temp.pagination.currentPage + 1;
                    if (data.data.length == 0) {
                        curPage = 0;
                    }

                    //删除当前元素
                    obj.find("div.pageBar").remove();
                    $(page_temp.pageBar).appendTo(obj);
                    obj.find("#current_page").text(curPage);
                    obj.find("#total_page").text(page_temp.pagination.pageTotal);
                    obj.find("#total_row").text(page_temp.pagination.totalRow);

                    var go_text = obj.find("#go_page_text");
                    go_text.keyup(function () {
                        if (/[^\d]/g.test(go_text.val()) || go_text.val() <= 1) {
                            go_text.val('1');
                        }
                    });
                    go_text.blur(function () {
                        if (/[^\d]/g.test(go_text.val()) || go_text.val() <= 1) {
                            go_text.val('1');
                        }
                    });

                    var pageNumber = "";
                    var start10Page = parseInt(page_temp.pagination.currentPage / 10) * 10;
                    for (var i = 0; i < 10; i++) {
                        if (start10Page + i + 1 > page_temp.pagination.pageTotal) break;
                        if (page_temp.pagination.currentPage == start10Page + i) {
                            pageNumber += $(page_temp.pageNumber.replace("{0}", start10Page + i + 1)).css({
                                "color": "red",
                                "font-weight": "800",
                                "text-decoration": "underline "
                            })[0].outerHTML;
                        } else {
                            pageNumber += page_temp.pageNumber.replace("{0}", start10Page + i + 1);
                        }
                    }

                    //翻页码
                    $(pageNumber).insertAfter(obj.find("#priv_btn_ten"));

                    $this.clickBind(options, data, obj);

                    $this.is_enable(obj);
                    //单击事件绑定
                }, clickBind: function (options, data, obj) {
                    var $this = this;
                    obj.find("[name=page_btn]").click(function () {

                        switch ($(this).attr("id")) {
                            case "first_btn":
                                if (page_temp.pagination.currentPage == 0) return;
                                page_temp.pagination.currentPage = 0;
                                break;
                            case "priv_btn":
                                if (page_temp.pagination.currentPage == 0) return;
                                page_temp.pagination.currentPage -= 1;
                                break;
                            case "priv_btn_ten":
                                if (page_temp.pagination.currentPage <= 0) return;

                                if (page_temp.pagination.currentPage - 10 < 0) {
                                    page_temp.pagination.currentPage = 0;
                                } else {
                                    page_temp.pagination.currentPage -= 10;
                                }
                                break;
                            case "next_btn_ten":
                                if (page_temp.pagination.currentPage >= page_temp.pagination.pageTotal - 1) return;

                                if (page_temp.pagination.currentPage + 10 > page_temp.pagination.pageTotal) {
                                    page_temp.pagination.currentPage = page_temp.pagination.pageTotal;
                                } else {
                                    page_temp.pagination.currentPage += 10;
                                }
                                break;
                            case "next_btn":
                                if (page_temp.pagination.currentPage == page_temp.pagination.pageTotal - 1) return;
                                page_temp.pagination.currentPage += 1;
                                break;
                            case "last_btn":
                                if (page_temp.pagination.currentPage == page_temp.pagination.pageTotal - 1) return;
                                page_temp.pagination.currentPage = page_temp.pagination.pageTotal - 1;
                                break;
                            case "go_page_btn":
                                var gopage = $.trim(obj.find("#go_page_text").val()) == "" ? 0 : parseInt(obj.find("#go_page_text").val() - 1);
                                if (page_temp.pagination.pageTotal == 1 || page_temp.pagination.currentPage == gopage) return;
                                page_temp.pagination.currentPage = gopage;
                                break;
                            default:
                                if (page_temp.pagination.currentPage == parseInt($(this).text() - 1)) return;
                                page_temp.pagination.currentPage = parseInt($(this).text() - 1);
                                break;
                        }

                        $this.executePage(page_temp.pagination.currentPage, options, obj);
                    });

                    //执行查询页面
                }, executePage: function (currentPage, options, obj) {
                    var $this = this;
                    if (currentPage >= page_temp.pagination.pageTotal) {
                        page_temp.pagination.currentPage = (page_temp.pagination.pageTotal - 1) < 0 ? 0 : (page_temp.pagination.pageTotal - 1);
                    }
                    if (currentPage < 0) {
                        page_temp.pagination.currentPage = 0;
                    }
                    $this.onLoad(options, obj);
                    //查询事件
                }, execute: function (data, url, currentPage) {
                    var $this = this;
                    options.data = data;
                    if (undefined != url) { options.url = url; }

                    if (undefined == currentPage) $this.executePage(page_temp.pagination.currentPage, options, obj);
                    else $this.executePage(currentPage, options, obj);
                        
                }, is_enable: function (obj) {
                    //一页
                    if (page_temp.pagination.pageTotal == 1 || page_temp.pagination.pageTotal == 0) {
                        obj.find("#first_btn").addClass("disabled");
                        obj.find("#priv_btn").addClass("disabled");
                        obj.find("#priv_btn_ten").addClass("disabled");

                        obj.find("#next_btn_ten").addClass("disabled");
                        obj.find("#next_btn").addClass("disabled");
                        obj.find("#page_last").addClass("disabled");

                        obj.find("[name=page_btn]").addClass("disabled");
                    }

                    //当前要页为零
                    if (page_temp.pagination.currentPage == 0 || page_temp.pagination.pageTotal == 0) {
                        obj.find("#first_btn").addClass("disabled");
                        obj.find("#priv_btn").addClass("disabled");
                        obj.find("#priv_btn_ten").addClass("disabled");
                    }

                    //当前要等于总页数
                    if (page_temp.pagination.currentPage == page_temp.pagination.pageTotal - 1 || page_temp.pagination.pageTotal == 0) {
                        obj.find("#next_btn_ten").addClass("disabled");
                        obj.find("#next_btn").addClass("disabled");
                        obj.find("#last_btn").addClass("disabled");
                    }
                },
                //翻页信息
                getPager: function () {
                    return page_temp.pagination;
                }
            }
        });
        return pagerExtend.methods;
    }


    /// <summary>
    /// 变量
    /// </summary>
    var page_temp = {
        pageBar: '<div class="pageBar" >\
        <a name=\"page_btn\" id="first_btn" title="首页" class="first page_first"  >首页</a>\
        <a name=\"page_btn\" id="priv_btn" title="上一页" >上一页</a>\
        <a name=\"page_btn\" id="priv_btn_ten" title="上十页" >&lt;&lt;</a>\
        <a name=\"page_btn\" id="next_btn_ten" title="下十页" >&gt;&gt;</a>\
        <a name=\"page_btn\" id="next_btn" href="javascript:;" title="下一页"  >下一页</a>\
        <a name=\"page_btn\" id="last_btn" href="javascript:;" title="尾页" class="page_last" >尾页</a>\
        <span id="DataPager_PageInfoLabel">当前 <span id="current_page"></span>/<span id="total_page"></span> 页 总共 <span id="total_row"></span> 条</span>\
        <input id="go_page_text" type="text" style="width:30px;"  maxlength="5" name="goPageText">\
        <input name=\"page_btn\" id="go_page_btn" type="submit" class="btn btn-small btn-primary btn-go" value="Go" >\
        </div>',
        pageNumber: "<a href=\"javascript:;\" name=\"page_btn\" class=\"current\" >{0}</a>",
        pagination: {
            data: null,
            currentPage: 0,
            pageSize: 15,
            totalRow: 0,
            pageTotal: 0
        }
    }

})(jQuery)







