package com.fxb.world.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;





import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import com.alibaba.fastjson.JSONObject;

public class FindIpUtil {
	private static Logger logger = Logger.getLogger("");

	/**
	 * @param urlAll
	 *            :请求接口
	 * @param httpArg
	 *            :参数
	 * @return 返回结果
	 */
	public static String request(String httpUrl, String httpArg) {
		BufferedReader reader = null;
		String result = null;
		StringBuffer sbf = new StringBuffer();
		httpUrl = httpUrl + "?" + httpArg;

		try {
			URL url = new URL(httpUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			// 填入apikey到HTTP header
			connection.setRequestProperty("apikey", "d6ec396cdcafaa52909e52160e0a3c16");
			connection.connect();
			InputStream is = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sbf.append(strRead);
				sbf.append("\r\n");
			}
			reader.close();
			result = sbf.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 获取IP地址包括运营商
	 * @param ip
	 * @return
	 */
	public static String getIpAddress(String ip) {
		if(StringUtils.isBlank(ip)){
			return "";
		}
		String httpUrl ="http://apis.baidu.com/apistore/iplookup/iplookup_paid";
		 String httpArg ="ip="+ip;
		String jsonResult = request(httpUrl, httpArg);
		JSONObject json = JSONObject.parseObject(jsonResult);
		StringBuffer ipAddress = new StringBuffer();
		if (json.isEmpty()) {
			ipAddress.append("未知地址");
			return ipAddress.toString();
		}
		String adJson = json.getString("retData");
		if ("[\"无效的IP地址\"]".equals(adJson)||"[]".equals(adJson)) {
			ipAddress.append("未知地址");
			return ipAddress.toString();
		}
		logger.info("地址解析内容："+adJson);
		JSONObject addressJson = JSONObject.parseObject(adJson);
		if (addressJson==null||addressJson.isEmpty()) {
			ipAddress.append("未知地址");
			return ipAddress.toString();
		}
		ipAddress.append(addressJson.getString("country") + " ");
		ipAddress.append(addressJson.getString("province") + " ");
		ipAddress.append(addressJson.getString("city") + " ");
		ipAddress.append(addressJson.getString("district") + " ");
		ipAddress.append("("+addressJson.getString("carrier") + " )");
		String address=ipAddress.toString();
		if (address.indexOf("None") != -1) {
			address="未知地址";
		}
		return address;
	}
	/**
	 * 获取IP地址到市（如：中国 广东 深圳）
	 * @param ip
	 * @return
	 */
	public static String getAddressByIP(String ip) {
		if(StringUtils.isBlank(ip)){
			return "";
		}
		String httpUrl ="http://apis.baidu.com/apistore/iplookup/iplookup_paid";
		 String httpArg ="ip="+ip;
		String jsonResult = request(httpUrl, httpArg);
		JSONObject json = JSONObject.parseObject(jsonResult);
		StringBuffer ipAddress = new StringBuffer();
		if (json.isEmpty()) {
			ipAddress.append("未知地址");
			return ipAddress.toString();
		}
		String adJson = json.getString("retData");
		if ("[\"无效的IP地址\"]".equals(adJson)||"[]".equals(adJson)) {
			ipAddress.append("未知地址");
			return ipAddress.toString();
		}
		logger.info("地址解析内容："+adJson);
		JSONObject addressJson = JSONObject.parseObject(adJson);
		if (addressJson==null||addressJson.isEmpty()) {
			ipAddress.append("未知地址");
			return ipAddress.toString();
		}
		ipAddress.append(addressJson.getString("country") + " ");
		ipAddress.append(addressJson.getString("province") + " ");
		ipAddress.append(addressJson.getString("city"));
	
		String address=ipAddress.toString();
		if (address.indexOf("None") != -1) {
			address="未知地址";
		}
		return address;
	}
	public static void main(String[] args) {
		
		//for (int i = 0; i < 5; i++) {
			System.out.println(getIpAddress("218.21.128.125"));
			System.out.println(getAddressByIP("218.21.129.34"));
			System.out.println(getAddressByIP("163.125.253.168").equals(getAddressByIP("58.251.48.20")));
		//}

	}

}
