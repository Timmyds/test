/**
  * Jquery.QQFace    
  * @author 叶委          2013-10-18
  * @faceAreaId           QQ表情域合作ID
  * @faceboxId            表情盒子的ID
  * @facePath             表情存放的路径
  * @areaContentId        文本域ID(此处用div模拟textarea)
  * @em_                  表情ID
 */
(function ($) {
    //$.extend($.fn, {});
    $.fn.qqFace = function (options) {
        //相关参数配置
        options = $.extend(options, {
            faceAreaId: (options.faceAreaId == null || $.trim(options.faceAreaId) == "") ? "faceAreaId" : options.faceAreaId,
            faceBoxId: (options.faceboxId == null || $.trim(options.faceboxId) == "") ? "faceboxId" : options.faceboxId,
            facePath: (options.facePath == null || $.trim(options.facePath) == "") ? WF_G.Util.getDomain() + "js/plugin/jquery-qqFace/face/" : options.facePath,
            tip: 'em_'
        });

        this.each(function () {
            //获取当前元素
            var $this = $(this);

            var _qqFaceArea = qqFaceArea();
            _qqFaceArea.bind(options, $this);
        });
    };

    //qq表情富文本
    var qqFaceArea = function () {
        $.extend(qqFaceArea, {
            initialize: {
                bind: function (options, obj) {
                    //绑定html元素
                    bindFace(options, obj);
                }
            }
        });
        return qqFaceArea.initialize;
    }


    //表情编码根据索引对应图片
    var emotion = "test /微笑 /撇嘴 /色 /发呆 /得意 /流泪 /害羞 /闭嘴 /睡 /大哭"
                    + " /尴尬 /发怒 /调皮 /呲牙 /惊讶 /难过 /酷 /冷汗 /抓狂 /吐"
                    + " /偷笑 /可爱 /白眼 /傲慢 /饥饿 /困 /惊恐 /流汗 /憨笑 /大兵"
                    + " /奋斗 /咒骂 /疑问 /嘘 /晕 /折磨 /衰 /骷髅 /敲打 /再见"
                    + " /擦汗 /抠鼻 /鼓掌 /糗大了 /坏笑 /左哼哼 /右哼哼 /哈欠 /鄙视 /委屈 /快哭了"
                    + " /阴险 /亲亲 /吓 /可怜 /菜刀 /西瓜 /啤酒 /篮球 /乒乓 /咖啡"
                    + " /饭 /猪头 /玫瑰 /凋谢 /示爱 /爱心 /心碎 /蛋糕 /闪电 /炸弹"
                    + " /刀 /足球 /瓢虫 /便便 /月亮 /太阳 /礼物 /拥抱 /强 /弱"
                    + " /握手 /胜利 /抱拳 /勾引 /拳头 /差劲 /爱你 /NO /OK /爱情"
                    + " /飞吻 /跳跳 /发抖 /怄火 /转圈 /磕头 /回头 /跳绳 /挥手"
                    + " /激动 /街舞 /献吻 /左太极 /右太极";

    //表情编码数组
    var emotionItem = function () {
        return emotion.split(' ');
    }

    //绑定表情表格
    var bindFace = function (options, obj) {

        //页面是否存在faceArea，隐藏域约定ID_hval+options.faceAreaId
        if (!$("#" + options.faceAreaId).length > 0) {
            var areahtml = "<div class='txtArea' id='" + options.faceAreaId + "' >"
                + "<div class='functionBar'>"
                  + "<span class='emotion'>表情</span>"
                       + "<div class='com_count fr'>"
                           + "剩余字数<em><span id='" + obj.prop("id") + "_num'>300</span></em>"
                       + "</div>"
                  + "</div>"
                  + "<div class='editArea'>"
                  + "</div>"
                + "</div>";
            obj.parent().append($(areahtml)).find(".editArea").append(obj.removeClass("test_box").addClass("test_box"));
        }

        //表情合作ID
        if (!$('#' + options.faceboxId).length > 0) {
            var strFace, imgItem;
            imgItem = emotionItem();
            if (!$('#' + options.faceboxId).length > 0) {
                strFace = '<div id="' + options.faceboxId + '" style="position:absolute;display:none;z-index:1000;display:none;"  class="qqFace">' +
                              '<table border="0" cellspacing="0" cellpadding="0"><tr>';
                for (var i = 1; i <= imgItem.length - 1 ; i++) {
                    strFace += '<td><img src="' + options.facePath + i + '.gif"  data="' + imgItem[i] + '" name="face_img"  /></td>';
                    if (i % 15 == 0) strFace += '</tr><tr>';
                }
                strFace += '</tr></table></div>';
            }
            $(obj).parent().append(strFace);
        }

        var selectface = $("#" + options.faceAreaId).find(".emotion");

        selectface.click(function (e) {
            $('#' + options.faceboxId).show();
            e.stopPropagation();
        });

        //文本域
        var areaContent = obj;
        var sel, range;

        //验证剩余字数
        validateArea(areaContent, options);

        //表情图片集合
        var imgs = $('#' + options.faceboxId).find("[name=face_img]");
        $.each(imgs, function (i, o) {
            $(o).click(function () {
                areaContent.setCaret();
                areaContent.insertAtCaret(" " + $(o).attr("data") + " ");

                validateArea(areaContent, options);
            });
        });

        areaContent.keyup(function () {
            validateArea(areaContent, options);
        });

        areaContent.keydown(function () {
            validateArea(areaContent, options);
        });

        $("body").click(function () {
            $('#' + options.faceboxId).hide();
        });

        var offset = selectface.parent().position();
        var top = offset.top + selectface.parent().outerHeight();
        $('#' + options.faceboxId).css('top', top);
        $('#' + options.faceboxId).css('left', offset.left + selectface.position().left);
    }

    //验证文本域
    var validateArea = function (areaContent, options) {

        //剩余字数
        var num = $("#" + areaContent.prop("id") + "_num");

        if (areaContent.val().length >= 300) {
            areaContent.val(areaContent.val().substr(0,300));
        }
        num.text(300 - areaContent.val().length);
    }

})(jQuery);





