 
/**
 * 商品SKU组件
 * @author 叶委     2015-04-05
 * @disablehead     sku头是否可以编辑
 * @speccustoms     编辑初始化规格值
 * @skudataset      编辑初始化sku数据
 * @specnames       规格名称集合
 * @specvalues      规格值集合
 * @skuresult       初始化sku
 * @skucustom       自定义别名
 * @skucolumn       自定义sku列头
*/
(function ($) {
    $.extend($.fn, {
        skuengine: function (options) {
            options = $.extend(options, {
                disablehead: options.disablehead,
                speccustoms: options.speccustoms,
                skudataset: options.skudataset,
                specnames: options.specnames,
                specvalues: options.specvalues,
                skuresult: options.skuresult,
                skucustom: options.skucustom,
                skucolumn: options.skucolumn
            });

            var skuer;
            this.each(function () {
                var $this = $(this);
                var tmp_options = {};
                tmp_options["disablehead"] = options.disablehead;
                tmp_options["speccustoms"] = options.speccustoms;
                tmp_options["skudataset"] = options.skudataset;
                tmp_options["specnames"] = options.specnames;
                tmp_options["specvalues"] = options.specvalues;
                tmp_options["skuresult"] = options.skuresult;
                tmp_options["skucustom"] = options.skucustom;
                tmp_options["skucolumn"] = options.skucolumn;
                skuer = skuenginer(options, $this);
                skuer.onInit(options, $this);
            });
            return skuer;
        }
    });

    /**
     * SKU插件对象
    */
    var skuenginer = function (options, wrap) {
        $.extend(skuenginer, {
            methods: {
                //初始化
                onInit: function (options, obj) {
                    sku_tmp.sku.skuelement = obj;
                    sku_tmp.sku.spec_names = options.specnames;
                    sku_tmp.sku.spec_values = options.specvalues;
                    this.onLoad(options.specnames, options.specvalues, options.speccustoms, options.skudataset);
                },
                //加载数据
                onLoad: function (specnames, specvalues, speccustoms, skudataset) {
                    sku_tmp.sku.spec_names = specnames;
                    sku_tmp.sku.spec_values = specvalues;
                    sku_tmp.sku.speccustoms = speccustoms;
                    sku_tmp.sku.skudataset = skudataset;
                    //生成SKU
                    this.createSpecModule(options, sku_tmp.sku.skuelement);
                },
                /// <summary>
                /// SKU组合算法引擎
                /// a母集，b子集
                /// </summary>
                skuEngine: function (a, b) {
                    //存放组合分组
                    var ab = [];
                    //存放结果集合
                    var rt = [];
                    //生成sku结果集合变量
                    var hasone = true;

                    //组合分组
                    $.each(a, function (i, o) {
                        ab[i] = new Array();
                        $.each(b, function (j, v) {
                            //此处根据自己业务参数判断
                            if (v.SpecNameID == o.ID) {
                                ab[i].push(v);
                            }
                        });
                    });

                    //计算出sku集合数组
                    $.each(ab, function (ii, o) {
                        if (hasone) {
                            $.each(ab[ii], function (j, k) {
                                sku_tmp.sku.tmp_result[j] = new Array(k);
                            });
                            hasone = false;
                        } else {

                            //临时数据对象
                            var t = new Array();
                            var index = 0;

                            $.each(sku_tmp.sku.tmp_result, function (i, o) {
                                $.each(ab[ii], function (j, v) {

                                    var sp = new Array();
                                    sp.push(v);

                                    $.each(sku_tmp.sku.tmp_result[i], function (l, n) {
                                        sp.push(n);
                                    });

                                    t[index] = sp;
                                    index++;
                                });
                            });
                            sku_tmp.sku.tmp_result = t;
                        }
                    });

                    //处理规格列排序
                    var tmp = new Array();
                    $.each(sku_tmp.sku.tmp_result, function (i, o) {
                        var sp = new Array();
                        $.each(a, function (k, l) {
                            $.each(sku_tmp.sku.tmp_result[i], function (j, v) {
                                if (l.ID == v.SpecNameID) {
                                    sp[k] = v;
                                }
                            });
                        });
                        tmp[i] = sp;
                    });

                    //重置临时对象
                    sku_tmp.sku.tmp_result = [];
                    //处理规格列排序
                    return tmp;
                },
                //获取规格名称
                getSepcName: function (id) {
                    var val = {};
                    $.each(sku_tmp.sku.spec_names, function (i, o) {
                        if (o.ID == id) {
                            val = o;
                            return;
                        }
                    });
                    return val;
                },
                //获取规格值
                getSepcValue: function (id) {
                    var val = {};
                    $.each(sku_tmp.sku.spec_values, function (i, o) {
                        if (o.ID == id) {
                            val = o;
                            return;
                        }
                    });
                    return val;
                },
                //是否能够创建
                hasCreateSku: function () {
                    var has_create = true;
                    $.each($("#" + sku_tmp.sku.options.controlId).find("div.right_s"), function (i, o) {
                        if (!$(this).next().find("input[type=checkbox]:checked").length) {
                            has_create = false;
                            return;
                        }
                    });
                    return has_create;
                },
                //获取选中值
                getSelectValue: function (options) {
                    var $this = this;
                    //集合
                    var a = [], b = [];
                    //存放结果集合
                    var rt = [];
                     
                    $.each($("#" + options.controlId).find("div[data-sku-spid]"), function (i, o) {
                        var parent = $(this);
                        var has_id = true;
                         
                        //复选框集合
                        var checkedboxs = $(this).find("input[type=checkbox]:checked");
                        if (checkedboxs.length) {
                            $.each(checkedboxs, function (j, k) {
                                b.push($this.getSepcValue($(k).val()));
                            });
                            if (has_id) {
                                a.push($this.getSepcName(parent.attr("data-sku-spid")));
                            }
                            has_id = false;
                        }
                    });
                    rt = $this.skuEngine(a, b);
                    return rt;
                },
                //获取已存在sku行
                getCurrentSku: function (specset) {
                    var tr = null;
                    //遍历当前行
                    $.each(sku_tmp.sku.sku_trs, function (i, o) {
                        if ($(o).attr("data-set") == specset) {
                            tr = o;
                        }
                    });
                    return tr;
                },
                //自定义规格名
                coustomSpecName: function (options) {
                    $.each($("#" + options.controlId).find("input[type=checkbox]:checked"), function (i, o) {
                        var title = $(this).attr("title");
                        //自定义名称
                        var cname = $("#" + "c_" + $(this).val()).find("input");
                        if (cname.length) {
                            var td = $("td[" + "data-vid=" + $(this).val() + "]");
                            if (td.length) {
                                if ($.trim(cname.val()).length == 0) {
                                    td.text(title);
                                } else {
                                    td.text(cname.val());
                                }
                            }
                        }
                    });
                },
                //自动增长字段
                autofield: function (options) {
                    $.each(options.skucolumn, function (i, o) {
                        if (o.isauto) {
                            //遍历自动编码字段
                            $.each($("#" + options.skuId).find("tr[data-set]").find("input[name=" + o.no + "]"), function (j, k) {
                                var val = o.value + "-" + (j + 1);
                                $(k).val(val);
                                $(k).attr("value", val);
                            });
                        }
                    });
                },
                //创建规格模块
                createSpecModule: function (options, obj) {
                    var $this = this;
           
                    //规格头
                    if ($("#" + options.controlId).length) {
                        $("#" + options.controlId).children().remove();
                    }

                    //sku列表
                    if ($("#" + options.skuId).length) {
                        $("#" + options.skuId).children().remove();
                    }

                    //设置控件ID
                    if (obj.attr("id") == undefined) {
                        options.controlId = new Date().getTime();
                        $(sku_tmp.skumodule_tmp).attr("id", options.controlId).appendTo(obj);
                    } else {
                        options.controlId = obj.attr("id");
                    }
 
                    var skumodule = $(sku_tmp.skumodule_tmp);
                    $.each($(sku_tmp.sku.spec_names), function (i, o) {
                        var name = $(sku_tmp.specname_tmp);
                        name.find("label").text(o.Name);
                        name.appendTo(skumodule.find("div:eq(1)"));
                        //规格值集合
                        var vals = $(sku_tmp.specvals_tmp);
                        //设在复选框
                        vals.attr("data-sku-spid", o.ID);
                        $.each(sku_tmp.sku.spec_values, function (j, k) {
                            if (k.SpecNameID == o.ID) {
                                //规格模板
                                var val = $(sku_tmp.specval_tmp);

                                //复选框是否可用 
                                if (options.disablehead) val.find("[type=checkbox]").prop("disabled", true);

                                //设置val
                                val.find("[type=checkbox]").val(k.ID).attr("title", k.Val);

                                //设在文本
                                val.find("label").text(k.Val);
                                val.appendTo(vals);
                            }
                        });
                         
                        vals.appendTo(skumodule.find("div:eq(1)"));
                    });

                    //创建页面元素
                    skumodule.appendTo(obj);

                    //规格头初始化绑定
                    if (undefined != sku_tmp.sku.speccustoms && sku_tmp.sku.speccustoms.length) {
                        $.each($("#" + options.controlId).find("input[type=checkbox]"), function (i, o) {
                            $.each(sku_tmp.sku.speccustoms, function (j, v) {
                                if ($(o).val() == v.SpecValueID) {
                                    $(o).prop("checked", true);
                                     
                                    $(o).parent().find("#c_" + $(o).val()).find("input").val(v.CustomValue);

                                    var cname = $(sku_tmp.custom_name);
                                    cname.attr("id", "c_" + $(o).val()).find("input").attr("name", "cname");
                                    cname.find("input").attr("data-id", $(o).parent().parent().attr("data-sku-spid") + "_" + $(o).val()).val(v.CustomValue);
                                    if (options.disablehead) cname.find("input").prop("disabled", true);

                                    cname.insertAfter($(o).next());
                                }
                            });
                        });
                    }

                    //sku集合初始化绑定
                    if (undefined != sku_tmp.sku.skudataset && sku_tmp.sku.skudataset.length) {
                        $this.createSku(sku_tmp.sku.skudataset, options, obj);
                    }

                    //选择选中生成
                    $("#" + options.controlId).off().on("click", "[type=checkbox]", (function () {
                        //自定义名称
                        var cname;
                        //选中
                        if ($(this).prop("checked")) {
                            cname = $(sku_tmp.custom_name);
                            cname.attr("id", "c_" + $(this).val()).find("input").attr("name", "cname");
                            cname.find("input").attr("data-id", $(this).parent().parent().attr("data-sku-spid") + "_" + $(this).val());
                            cname.insertAfter($(this).next());
                        } else {
                            $("#c_" + $(this).val()).remove();
                        }

                        //将生成后的行存放在
                        sku_tmp.sku.sku_trs = $("#" + options.skuId).find("tr[data-set]");

                        //移除原有sku层
                        $("#" + options.skuId).remove();

                        //是否能生成
                        if (!$this.hasCreateSku()) return;

                        //获取生成的值
                        var skuresult = $this.getSelectValue(options);

                        //生成SKU
                        $this.createSku(skuresult, options, obj);

                        //绑定SKU文本框输入值
                        $("#" + options.skuId).on("keyup", "input.input_wr", (function () {
                            $(this).attr("value", $(this).val());
                        }));

                        //自定义规格名称
                        $this.coustomSpecName(options);

                    }));

                    //规格名按键事件
                    $this.bindSepcNameKey(options);
                    //设置相关设置
                    sku_tmp.sku.options = options;
                },
                //创建sku模块
                createSku: function (skuresult, options, obj) {
                    var $this = this;

                    //设置sku控件ID
                    options.skuId = options.controlId + "_sku";
                    $(sku_tmp.sku_div).attr("id", options.skuId).insertAfter($("#" + options.controlId));
            
                    //获取sku 模板
                    var sku_div = $("#" + options.skuId);

                    //生成表头
                    if (sku_tmp.sku.spec_names.length > 0) {
                        var tr_head = "";

                        //sku 列头
                        $.each(sku_tmp.sku.spec_names, function (i, o) {
                            tr_head += "<th role='row'>" + o.Name + "</th>";
                        });
                  
                        //自定义列头
                        $.each(options.skucolumn, function (i, o) {
                            tr_head += "<th>" + o.name + "</th>";
                        });

                        tr_head += "<th>操作</th>";
                        //填充表头
                        $(tr_head).appendTo(sku_div.find($("thead")));
                    }

                    var tr_data = "";
                    var td_data = "";

                    $.each(skuresult, function (i, o) {
                        var sset = "";
                        var u_id = "";
 
                        //非编辑状态
                        if (undefined == o.SpecSet) {
                            $.each(skuresult[i], function (j, v) {
                                td_data += "<td name = 'attr_spec_td' data-vid='" + skuresult[i][j].ID + "' id='" + v.SpecNameID + "_" + v.ID + "' >" + skuresult[i][j].Val + "</td>";
                                if (sset.length > 0) {
                                    sset += "," + v.SpecNameID + "_" + v.ID;
                                } else {
                                    sset += v.SpecNameID + "_" + v.ID;
                                }
                                u_id += v.SpecNameID + "_" + v.ID;
                            });
                        } else {

                            //重新排序
                            $.each(sku_tmp.sku.spec_names, function (ii, oo) {
                                sset = o.SpecSet;
                                var specArray = o.SpecSet.split(',');
                                $.each(specArray, function (j, v) {
                                    var spec_name_id = v.split('_')[0];
                                    var spec_value_id = v.split('_')[1];

                                    if (spec_name_id == oo.ID) {
                                        td_data += "<td name = 'attr_spec_td' data-vid='" + spec_value_id + "' id='" + spec_name_id + "_" + spec_value_id + "' >"
                                        + $this.getSepcValue(spec_value_id).Val + "</td>";

                                        u_id += spec_name_id + "_" + spec_value_id;
                                        return;
                                    }
                                });
                            });

                        }

                        //sku自定义列
                        $.each(options.skucolumn, function (j, v) {
                            var input = "";
                            var val = undefined == v.value ? "" : v.value;
                            $.each(o, function (ii, oo) {
                                if (v.no == ii) {
                                    val = oo;
                                    return false;
                                }
                            });

                            //自定义格式输出
                            if (TSH_G.Util.isFunction(v.stringformt)) {
                                val = v.stringformt(val);
                            }

                            //是否是只读
                            if (undefined == v.isenable || v.isenable != false) {
                                input = "<input style='" + (undefined == v.ip_style ? "" : v.ip_style) + ";' class='input_wr' type='text' "
                                    + ((undefined != o.IsEnable && !o.IsEnable) ? "disabled='disabled'" : "") + " name='" + v.no + "' id='" + v.no + "_" + u_id + "' value='" + val + "' />";
                            } else {
                                input = "<label name='" + v.no + "'>" + val + "</label>";
                            }

                            td_data += "<td style='" + (undefined == v.td_style ? "" : v.td_style)
                                    + "; overflow:hidden;'>" + input + "</td>";
                        });

                        td_data += "<td name='operation_td' >" + ((undefined != o.IsEnable && !o.IsEnable) ? sku_tmp.sku_enable_btn : sku_tmp.sku_dis_btn) + "</td>";
                        //根据sku集合获取当前
                        var cur_tr = $this.getCurrentSku(sset);
                        if (cur_tr == null) {
                            tr_data += "<tr data-set='" + sset + "' " + (undefined == o.ID ? "" : "id='" + o.ID + "'") + ((undefined != o.IsEnable && !o.IsEnable) ? "disabled='disabled'" : "") + ">" + td_data + "</tr>";
                        } else {
                            tr_data += "<tr data-set='" + sset + "' " + (undefined == o.ID ? "" : "id='" + o.ID + "'") + ((undefined != o.IsEnable && !o.IsEnable) ? "disabled='disabled'" : "")+">" + $(cur_tr).html() + "</tr>";
                        }
                        td_data = "";
                    });

                    //添加SKU行到页面
                    $(tr_data).appendTo(sku_div.find($("tbody")));

                    //充值规格名
                    $this.coustomSpecName(options);

                    //设置禁用按钮
                    $("a[name=disable_btn]").digbox({
                        Selector: "div.mpContent.mt10",
                        Title: "提示信息",
                        Context: "确定禁用该条数据吗？",
                        CallBack: function (submit_btnid, current, panel) {
                            current.parent().parent().attr("disabled", true);
                            current.parent().parent().find("input").prop("disabled", true);
                            $(sku_tmp.sku_enable_btn).appendTo(current.parent());
                            current.remove();
                        }
                    });

                    //设置启用按钮
                    $("[name=enable_btn]").digbox({
                        Selector: "div.mpContent.mt10",
                        Title: "提示信息",
                        Context: "确定启用该条数据吗？",
                        CallBack: function (submit_btnid, current, panel) {
                            current.parent().parent().attr("disabled", false);
                            current.parent().parent().find("input").prop("disabled", false);
                            $(sku_tmp.sku_dis_btn).appendTo(current.parent());
                            current.remove();
                        }
                    });

                    //绑定验证
                    $this.autofield(options);

                    //绑定验证
                    $this.bindValidate(options);

                    //设置相关设置
                    sku_tmp.sku.options = options;
                },
                //自定义规格名按键事件
                bindSepcNameKey: function (options) {
                    var $this = this;
                    //绑定文本框
                    $("#" + options.controlId).on("keyup", "[name=cname]", (function () {
                        if (!$this.hasCreateSku()) return;
                        $this.coustomSpecName(options);
                    }));
                    //绑定文本框
                    $("#" + options.controlId).on("blur", "[name=cname]", (function () {
                        if (!$this.hasCreateSku()) return;
                        $this.coustomSpecName(options);
                    }));
                },
                //绑定验证
                bindValidate: function () {
                    $.each(options.skucolumn, function (j, v) {

                        if (undefined != v.validate && v.validate != null) {
                            $("input[name=" + v.no + "]").validate({
                                tabindex: v.validate.tabindex,
                                focusmessage: v.validate.focusmessage, errormessage: v.validate.errormessage, validatetype: v.validate.validatetype
                            });
                        }

                    });
                },
                //获取设置自定义规格值
                getSpecCustoms: function () {

                    var params = [];
                    $.each($("#" + options.controlId).find("input[type=checkbox]:checked"), function (i, o) {
                        //自定义数据
                        var data = {};
                        //自定义名称
                        var cname = $("#" + "c_" + $(this).val()).find("input");
                        data["SpecNameID"] = cname.attr("data-id").split('_')[0];
                        data["SpecValueID"] = cname.attr("data-id").split('_')[1];
                        data["Customvalue"] = cname.val();
                        params.push(data);
                    });

                    return params;
                },
                //获取sku数据
                getSkuData: function () {
                    var params = [];
                    if (sku_tmp.sku.options == null) return params;
                    $.each($("#" + sku_tmp.sku.options.skuId).find("tr[data-set]"), function (i, o) {
                        var data = {};
                        var specSet = "";
                        $.each($(o).find("td").not("[name=operation_td]"), function (j, v) {

                            if ($(v).attr("name") == "attr_spec_td") {
                                if (specSet == "") {
                                    specSet += $(v).attr("id");
                                } else {
                                    specSet += "," + $(v).attr("id");
                                }
                            } else {
                                if ($(v).find("input").length) {
                                    data[$(v).find("input").attr("name")] = $(v).find("input").val();
                                } else {
                                    data[$(v).find("label").attr("name")] = null;
                                }
                            }
                        });
                         
                        data["IsEnable"] = $(o).attr("disabled") == undefined;
                        data["SpecSet"] = specSet;
                        data["ID"] = (TSH_G.Util.isNum($(o).attr("ID")) ? $(o).attr("ID") : 0);
                        
                        params.push(data);
                    });
                    return params;
                }
            }
        });
        return skuenginer.methods;
    };


    /// <summary>
    /// 变量
    /// </summary>
    var sku_tmp = {
        //SKU集合模板
        skumodule_tmp : '<div class="m_pFormat mt20">\
                        <div class="m_opBar"><a href="javascript:;">商品规格</a></div>\
                        <div style="border:1px solid #ddd;padding-bottom:10px;" ></div></div>',
        //规格名称模版
        specname_tmp :'<div class="right_s" data-sku-specname=""><label></label></div>',
        //规格值集合模版
        specvals_tmp: '<div data-sku-spid="" style="text-align:left;"></div>',
        //规格值模版
        specval_tmp: '<div class="disBlock" style="min-width:160px;text-indent: 28px;" ><input type="checkbox"  ><label></label></div>',
        //自定义别名
        custom_name: '<div style="border:1px solid #ddd;line-height: 24px;margin-left:28px;"><span class="color-note-text" style="visibility: visible;line-height: 18px;margin-left:-25px">备注</span>\
                      <input type="text" maxlength="30" style="width:90px;line-height: 17px;border-top:1px;border-bottom:0px;border-right:0px;" /></div>',

        //sku模板
        sku_div: '<div class="mpContent mt10" >\
                            <div style="height: auto;overflow-x: auto; overflow-y: hidden;">\
                                <table id="DataTables_Table_0" class="table table-bordered table_s1">\
                                    <thead></thead>\
                                    <tbody role="alert" aria-live="polite" aria-relevant="all"></tbody>\
                                </table>\
                            </div>\
                        </div>',
        //禁用按钮
        sku_dis_btn: '<a href="javascript:;" name="disable_btn" ><i class="icon-lock"></i>禁用</a>',
        //启用按钮
        sku_enable_btn: '<a href="javascript:;" name="enable_btn" ><i class="icon-ok"></i>启用</a>',
       
        //sku数据集合
        sku: {
            //当前sku控件对象
            skuelement: null,
            //返回sku集合对象
            options: null,
            //选中的规格名集合
            spec_names: null,
            //选中的规格值集合
            spec_values: null,
            //生成后的规格集合
            spec_array: new Array(),
            //生成后的SKU行集合
            sku_trs: new Array(),
            //由于js算法不能引用递归传递参数
            //所有定义临时全局变量结果集合 计算完后清空
            tmp_result: new Array(),
            //存放初始化sku集合
            skudataset: new Array()
        }
    }

 
})(jQuery);
