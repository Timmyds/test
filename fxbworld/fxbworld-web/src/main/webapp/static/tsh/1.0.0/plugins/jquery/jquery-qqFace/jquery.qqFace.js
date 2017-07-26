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
            areaContentId: (options.areaContentId == null || $.trim(options.areaContentId) == "") ? "areaContentId" : options.areaContentId,
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
    var emotion = "dd /::) /::~ /::B /::| /:8-) /::< /::$ /::X /::Z /::-'("
                    + " /::-| /::@ /::P /::D /::O /::( /::+ /:--b /::Q /::T"
                    + " /:,@P /:,@-D /::d /:,@o /::g /:|-) /::! /::L /::> /::,@"
                    + " /:,@f /::-S /:? /:,@x /:,@@ /::8 /:,@! /:!!! /:xx /:bye"
                    + " /:wipe /:dig /:handclap /:&-( /:B-) /:<@ /:@> /::-O /:>-| /:P-("
                    + " /:X-) /::* /:@x /:8* /:pd /:<W> /:beer /:basketb /:oo /:coffee"
                    + " /:eat /:pig /:rose /:fade /:heart /:showlove /:break /:cake /:li /:bome"
                    + " /:kn /:footb /:ladybug /:shit /:moon /:sun /:gift /:hug /:strong /:weak"
                    + " /:share /:v /:@) /:jj /:@@ /:bad /:lvu /:no /:ok /:love"
                    + " /:<L> /:jump /:shake /:<O> /:circle /:kotow /:turn /:skip /:oY"
                    + " /:#-0 /:hiphot /:kiss /:<& /:&>";

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
                           + "剩余字数<em><span id='" + options.areaContentId + "_num'>300</span></em>"
                       + "</div>"
                  + " <input id='_hval" + options.areaContentId + "' type='hidden' />"
                  + "</div>"
                  + "<div class='editArea'>"
                     + "<div class='test_box' contenteditable='true' id='" + options.areaContentId + "'></div>"
                  + "</div>"
                + "</div>";
            $(areahtml).appendTo(obj);
        }

        //表情合作ID
        if (!$('#' + options.faceboxId).length > 0) {
            var strFace, imgItem;
            imgItem = emotionItem();
            if (!$('#' + options.faceboxId).length > 0) {
                strFace = '<div id="' + options.faceboxId + '" style="position:absolute;display:none;z-index:1000;display:none;"  class="qqFace">' +
                              '<table border="0" cellspacing="0" cellpadding="0"><tr>';
                for (var i = 1; i <= imgItem.length; i++) {
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

        var sel, range;

        //表情图片集合
        var imgs = $('#' + options.faceboxId).find("[name=face_img]");
        $.each(imgs, function (i, o) {
            $(o).click(function () {
                areaContent.focus();

                //要插入的表情表情
                var imgTxt = o.outerHTML;

                if (window.getSelection) {
                    sel = areaContent.cur.selection;
                    range = areaContent.cur.range;

                    var imgDom = $(imgTxt);

                    //删除选中文字
                    range.deleteContents();

                    //插入 :光标处 插入 元素
                    range.insertNode(imgDom[0]);

                    //移动光标
                    range = range.cloneRange();
                    range.setStartAfter(imgDom[0]);//移动到指定 元素 后面
                    range.collapse(true);
                    sel.removeAllRanges();
                    sel.addRange(range);

                    areaContent.cur.range = range;
                } else {
                    document.selection.createRange().pasteHTML(imgTxt);
                }
            });
        });


        //文本域
        var areaContent = $("#" + options.areaContentId);

        areaContent.cur = {};
        areaContent.click(function () {
            if (window.getSelection) {

                //光标选区对象
                sel = window.getSelection();

                //选区 操作对象
                range = sel.getRangeAt(0);

                areaContent.cur.selection = sel;
                areaContent.cur.range = range;
            }
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
        var num = $("#" + options.areaContentId + "_num");

        //表情占用5个字符
        var imgs = areaContent.html().match(/<img\b[^>]*src\s*=\s*"[^>"]*\.(?:png|jpg|bmp|gif)"[^>]*>/g);
        var count = imgs ? parseInt(imgs.length) * 5 : 0;

        var len = areaContent.html().replace(/<img\b[^>]*src\s*=\s*"[^>"]*\.(?:png|jpg|bmp|gif)"[^>]*>/g, "").length;

        if (len + count >= 300) {
            var _len = 0;
            if (count > 0) {
                for (var i = 0; i < imgs.length; i++) {
                    _len += imgs[i].length;
                }
            }
            areaContent.html(areaContent.html().substr(0, _len + 299 - (count)));
        }
        num.text(300 - (len + count));

        if (window.getSelection) {

            //光标选区对象
            sel = window.getSelection();

            //选区 操作对象
            range = sel.getRangeAt(0);

            areaContent.cur.selection = sel;
            areaContent.cur.range = range;
        }
    }


})(jQuery);


jQuery.extend({
    unselectContents: function () {
        if (window.getSelection)
            window.getSelection().removeAllRanges();
        else if (document.selection)
            document.selection.empty();
    }
});
jQuery.fn.extend({
    selectContents: function () {
        $(this).each(function (i) {
            var node = this;
            var selection, range, doc, win;
            if ((doc = node.ownerDocument) && (win = doc.defaultView) && typeof win.getSelection != 'undefined' && typeof doc.createRange != 'undefined' && (selection = window.getSelection()) && typeof selection.removeAllRanges != 'undefined') {
                range = doc.createRange();
                range.selectNode(node);
                if (i == 0) {
                    selection.removeAllRanges();
                }
                selection.addRange(range);
            } else if (document.body && typeof document.body.createTextRange != 'undefined' && (range = document.body.createTextRange())) {
                range.moveToElementText(node);
                range.select();
            }
        });
    },

    setCaret: function () {
        if (!$.browser.msie) return;
        var initSetCaret = function () {
            var textObj = $(this).get(0);
            textObj.caretPos = document.selection.createRange().duplicate();
        };
        $(this).click(initSetCaret).select(initSetCaret).keyup(initSetCaret);
    },

    insertAtCaret: function (textFeildValue) {
        var textObj = $(this).get(0);
        if (document.all && textObj.createTextRange && textObj.caretPos) {
            var caretPos = textObj.caretPos;
            caretPos.text = caretPos.text.charAt(caretPos.text.length - 1) == '' ?
			textFeildValue + '' : textFeildValue;
        } else if (textObj.setSelectionRange) {
            var rangeStart = textObj.selectionStart;
            var rangeEnd = textObj.selectionEnd;
            var tempStr1 = textObj.value.substring(0, rangeStart);
            var tempStr2 = textObj.value.substring(rangeEnd);
            textObj.value = tempStr1 + textFeildValue + tempStr2;
            textObj.focus();
            var len = textFeildValue.length;
            textObj.setSelectionRange(rangeStart + len, rangeStart + len);
            textObj.blur();
        } else {
            textObj.value += textFeildValue;
        }
    }
});