/**
 * art-template帮助类  
 * @author 叶委          
 * add by date 2015-01-15
**/

/**
* 验证权限
* @param pcode 权限编码
* @return String
**/
template.helper('VerifyPermission', function (pcode) {
    var hasp = false;
    if (TSH_G.AuthCode != "") {
        var pcodes = TSH_G.AuthCode.split(',');
        $.each(pcodes, function (i, o) {
            if (pcode == o) {
                hasp = true;
                return;
            }
        });
    }
    return hasp;
}); 
/**
* josn字符串截取
* @param str 被截取的字符串
* @param len 截取的字符串长度
* @return String
**/
template.helper('cutSubString', function (str, len) {
    return TSH_G.Util.cutSubString(str, len);
});
/**
* josn日期格式化
* @param datetime json序列化后的日期
* json序列化格式日期 例如：/Date(1410192000000)/
* @return String
**/
template.helper('ChangeDateFormat', function (datetime) {
    return TSH_G.Util.Date.ChangeDateFormat(datetime);
});
/**
*显示Json完整时间
**/
template.helper('ChangeCompleteDateFormat', function (datetime) {
    return TSH_G.Util.Date.ChangeCompleteDateFormat(datetime);
});
/**
* josn日期格式化
* @param datetime 当前值
* @return String
**/
template.helper('DateFormat', function (date, format) {
    return TSH_G.Util.Date.DateFormat(date, format);
});
/**
* 金额格式转换
* @param s 需要格式的字符串
* @param n 保留小数点位数
* @return String
**/
template.helper('formaToMoney', function (s, n) {
    return TSH_G.Util.formaToMoney(s, n);
});
/**
* 获取小图地址
* @param s 原图片地址
* @return String
**/
template.helper('getImgUrl_s', function (s) {
    return TSH_G.Util.getImgUrl_s(s);
});
/**
* 获取OSS图片地址
* @param s 原图片名称，OSS图片此存参数
* @return String
**/
template.helper('getOssImgUrl', function (s, p) {
    return TSH_G.Util.getOssImgUrl(s, p);
});

/**
* 获取中图地址
* @param s 原图片地址
* @return String
**/
template.helper('getImgUrl_m', function (s) {
    return TSH_G.Util.getImgUrl_m(s);
});
/**
* 金额格式转换
**/
template.helper('formaToMoney', function (s, n) {
    return TSH_G.Util.formaToMoney(s, n);
});
/**
* 改变订单商品表订单是否到货（true：到货 false：未到货）
**/
template.helper('ChangeIsReceipt', function (IsReceipt) {
    return IsReceipt==true ? "到货":"未到货";
});
