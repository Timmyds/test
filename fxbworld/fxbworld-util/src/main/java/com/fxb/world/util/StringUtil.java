package com.fxb.world.util;

import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.lang.StringUtils;

import com.fxb.world.util.bean.RegexpConstants;


public class StringUtil {

	private static DecimalFormat formatDouble = new DecimalFormat("0.00");
	private static DecimalFormat dFormat2 = new DecimalFormat("0.00");
	private static DecimalFormat dFormat1 = new DecimalFormat("0.0");

	private static final String STR_FORMAT = "000000";

	/**
	 * 清除掉所有特殊字符
	 * @param str
	 * @return
	 * @throws PatternSyntaxException
	 */
	public static String cleanSpecialChar(String str){
		try {
			str = StringUtils.trimToEmpty(str);
			str = str.replaceAll(" ", "");
			String regEx = "[`~!@#$%^&*()+|';'\\?~！@#￥%……&*（）——+|【】‘；：”“’。，、？]";
			Pattern p = Pattern.compile(regEx);
			Matcher m = p.matcher(str);
			str = StringUtils.trimToEmpty(m.replaceAll(""));
			str = StringUtils.trimToEmpty(str.replaceAll("&nbsp;", ""));
			return str;
		} catch (Exception e) {
			throw new RuntimeException("清理特殊字符出错"); 
		}
	}

	/**
	 * 产生四位随机数
	 */
	public static String randomCode4() {
		int intCount = new Random().nextInt(9999);
		if (intCount < 1000)
			intCount += 1000;
		return intCount + "";
	}

	/**
	 * 产生六位随机数
	 */
	public static String randomCode6() {
		int intCount = new Random().nextInt(999999);
		if (intCount < 100000)
			intCount += 100000;
		return intCount + "";
	}

	// length表示生成字符串的长度
	public static String getRandomStr(int length) {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	// 一位小数点
	public static String formatDouble(Object obj) {
		if (obj != null) {
			return formatDouble.format(obj);
		}
		return "0.00";
	}

	public static boolean isEmail(String str) {
		if (StringUtils.isBlank(str))
			return false;
		if (str.matches(RegexpConstants.REGEXP_EMAIL[0]))
			return true;
		return false;
	}

	/**
	 * 是否是中文
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isChinese(String str) {
		if (StringUtils.isBlank(str))
			return false;
		if (str.matches(RegexpConstants.REGEXP_CHINESE[0]))
			return true;
		return false;
	}

	/**
	 * 必须是中文、数字、字母、下划线
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNormalWord(String str) {
		if (!StringUtils.isBlank(str)) {
			return str.matches(RegexpConstants.REGEXP_CHINESE_AND_WORD[0]);
		}
		return false;
	}

	// 获取ARRAY
	public static String[] getArray(String fileName) {
		if (!StringUtils.isBlank(fileName)) {
			return fileName.split(",");
		}
		return new String[0];
	}

	// 一位小数点
	public static String dFormat1(Double val) {
		if (val != null) {
			return dFormat1.format(val);
		}
		return "0.0";
	}

	// 两位小数点
	public static String dFormat2(Double val) {
		if (val != null) {
			return dFormat2.format(val);
		}
		return "0.00";
	}

	// 二进制转字符串
	public static String byte2hex(byte[] b) {
		StringBuffer sb = new StringBuffer();
		String tmp = "";
		for (int i = 0; i < b.length; i++) {
			tmp = Integer.toHexString(b[i] & 0XFF);
			if (tmp.length() == 1) {
				sb.append("0" + tmp);
			} else {
				sb.append(tmp);
			}

		}
		return sb.toString();
	}

	// 字符串转二进制
	public static byte[] hex2byte(String str) {
		if (str == null) {
			return null;
		}

		str = str.trim();
		int len = str.length();

		if (len == 0 || len % 2 == 1) {
			return null;
		}

		byte[] b = new byte[len / 2];
		try {
			for (int i = 0; i < str.length(); i += 2) {
				b[i / 2] = (byte) Integer.decode("0X" + str.substring(i, i + 2)).intValue();
			}
			return b;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * unicode 转字符串
	 * 
	 * @param utfString
	 * @return
	 */
	public static String unicodeToString(String utfString) {
		StringBuilder sb = new StringBuilder();
		int i = -1;
		int pos = 0;

		while ((i = utfString.indexOf("\\u", pos)) != -1) {
			sb.append(utfString.substring(pos, i));
			if (i + 5 < utfString.length()) {
				pos = i + 6;
				sb.append((char) Integer.parseInt(utfString.substring(i + 2, i + 6), 16));
			}
		}

		return sb.toString();
	}

	public static synchronized String numberAddOne(String serialNumber) {
		Integer newSerialNumber = Integer.parseInt(serialNumber);
		newSerialNumber++;
		DecimalFormat df = new DecimalFormat(STR_FORMAT);
		return df.format(newSerialNumber);
	}

	/**
	 * MurMurHash算法，是非加密HASH算法，性能很高，
	 * 比传统的CRC32,MD5，SHA-1（这两个算法都是加密HASH算法，复杂度本身就很高，带来的性能上的损害也不可避免）
	 * 等HASH算法要快很多，而且据说这个算法的碰撞率很低. http://murmurhash.googlepages.com/
	 */
	public static Long hash(String key) {

		ByteBuffer buf = ByteBuffer.wrap(key.getBytes());
		int seed = 0x1234ABCD;

		ByteOrder byteOrder = buf.order();
		buf.order(ByteOrder.LITTLE_ENDIAN);

		long m = 0xc6a4a7935bd1e995L;
		int r = 47;

		long h = seed ^ (buf.remaining() * m);

		long k;
		while (buf.remaining() >= 8) {
			k = buf.getLong();

			k *= m;
			k ^= k >>> r;
			k *= m;

			h ^= k;
			h *= m;
		}

		if (buf.remaining() > 0) {
			ByteBuffer finish = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN);
			// for big-endian version, do this first:
			// finish.position(8-buf.remaining());
			finish.put(buf).rewind();
			h ^= finish.getLong();
			h *= m;
		}

		h ^= h >>> r;
		h *= m;
		h ^= h >>> r;

		buf.order(byteOrder);
		return h;
	}

	/**
	 * List<String> 转字符串','隔开
	 * 
	 * @param list<String>
	 * @return
	 */
	public static String listToString(List<String> list) {
		if (list == null) {
			return null;
		}
		StringBuilder result = new StringBuilder();
		for (String str : list) {
			result.append("'" + str.toString() + "',");
		}
		String str = result.toString();
		if (str.length() > 0) {
			str = str.substring(0, str.length() - 1);
		}
		return str;
	}
	
	/**
	 * 编码导出文件名 　　 *　ie采用URLEncoder编码输出中文 　　 *　opera采用filename 　　
	 * *　safari采用iso-8859-1 　　 *　chrome采用base64或iso-8859-1 　　 *
	 * firefox采用base64或iso-8859-1
	 * 
	 * @param fileName
	 * @param userAgent
	 * @return
	 */
	public static String encodeFileName(String fileName, String userAgent) {
		try {
			if (userAgent != null && fileName != null) {
				userAgent = userAgent.toLowerCase();
				if (userAgent.indexOf("firefox") != -1) {
					fileName = new String(fileName.getBytes(), "iso8859-1");
				} else if (userAgent.indexOf("msie") != -1) {
					fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
				} else {
					fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
				}
			}
			return fileName;
		} catch (Exception e) {
			return fileName;
		}
	}

	public static String getDecimalFormat(BigDecimal dec) {
		DecimalFormat fmt = new DecimalFormat("##,###,###,###,##0.00");
		return fmt.format(dec);
	}
}
