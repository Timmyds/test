package com.fxb.world.util;
/*package com.fxb.work.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.StringUtils;


import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.model.OSSObject;
import com.aliyun.openservices.oss.model.ObjectMetadata;

public class OSSClientUtil  {

	private String accessKeyId;
	private String accessKeySecret;
	private String wyhImgBucketName;
	private String articleDesBucketName;
	private String endpoint;
	private OSSClient instance;

	public OSSClient getOSSClient() {
		if (this.instance == null) {
			// 初始化一个OSSClient
			this.accessKeyId = PropertiesUtil.get("accessKeyId");
			this.accessKeySecret = PropertiesUtil.get("accessKeySecret");
			this.wyhImgBucketName = PropertiesUtil.get("imgBucketName");
			this.articleDesBucketName = PropertiesUtil.get("articleDesBucketName");
			this.endpoint = PropertiesUtil.get("endpoint");
//			// 初始化一个OSSClient
			this.instance = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		}
		return this.instance;
	}
	

	*//**
	 * 接受图片流并保存到OSS
	 * 
	 * @param content
	 *            图片数据流
	 * @param size
	 *            文件件大小
	 * @param picName
	 *            文件名称
	 * @throws IOException
	 *//*
	public String putImage(InputStream content,String suffix,String userId) throws RuntimeException {
		OSSClient ossClient = getOSSClient();
		String desName = "";
		try {
			ObjectMetadata meta = new ObjectMetadata();
			// 必须设置ContentLength
			meta.setContentLength(content.available());
			meta.setContentType("image/jpeg");
			// 上传Object.
			if (StringUtils.isNoneBlank(userId)){
				String hashStr = String.valueOf(StringTools.hash(userId));
				desName = UUIDUtil.getUUID() + hashStr.substring(hashStr.length() - 4)+suffix;
			}else{
				desName = UUIDUtil.getUUID()+suffix;
			}
			ossClient.putObject(wyhImgBucketName, desName, content, meta);

			    // 打印ETag
			   // System.out.println(result.getETag());
		} catch (Exception e) {
			e.printStackTrace();
			//logger.error("图片保存到OSS时异常", e);
			throw new RuntimeException("图片保存异常");
		} finally {
			if (content != null) {
				try {
					content.close();
				} catch (IOException e) {
					//logger.error("保存图片时流关闭异常", e);
				}
			}
		}
		return desName;
	}
	
	public String getFilePath(String fileName) {
		return PropertiesUtil.get("picDomain") + fileName;
	}

	*//**
	 * 将文章描述信息保存到OSS
	 * 
	 * @param content
	 *            文件流
	 * @param desName
	 *            保存名称
	 * @throws IOException
	 *//*
	public String putGoodsDes(InputStream content) throws RuntimeException {
		OSSClient ossClient = getOSSClient();

		String desName = "";

		try {
			ObjectMetadata meta = new ObjectMetadata();

			// 必须设置ContentLength
			meta.setContentLength(content.available());
			meta.setContentType("text/html");

			desName = "DES_" + UUIDUtil.getUUID();

			ossClient.putObject(articleDesBucketName, desName, content, meta);
		} catch (Exception e) {
			throw new RuntimeException("文章类容");
		} finally {
			if (content != null) {
				try {
					content.close();
				} catch (IOException e) {
				}
			}
		}
		return desName;
	}

	public void deleteImage(String name) {
		OSSClient ossClient = getOSSClient();
		ossClient.deleteObject(wyhImgBucketName, name);
	}

	*//**
	 * 根据描述路径返回在OSS中的描述内容
	 * 
	 * @param desName
	 * @return
	 * @throws IOException
	 *//*
	public String getGoodsDes(String desName) throws RuntimeException {
		OSSClient ossClient = getOSSClient();
		InputStream objectContent = null;
		try {
			OSSObject object = ossClient.getObject(articleDesBucketName, desName);
			objectContent = object.getObjectContent();
			String result = inputStreamUtil(objectContent);
			return result;
		} catch (Exception e) {
			throw new RuntimeException("获取文章描述时异常");
		} finally {
			if (objectContent != null) {
				try {
					objectContent.close();
				} catch (IOException e) {
					// logger.error("获取文章描述时流关闭异常", e);
				}
			}
		}
	}
	
	public static String getSuffix(String fileName){
		int pos = fileName.lastIndexOf(".");
		if (pos!=-1) {
			return fileName.substring(pos);
		}
		return ".unknow";
	}

	*//**
	 * 
	 * @Title: inputStreamUtil 文件流转字符串
	 * @Description: TODO
	 * @param @param
	 *            inputStream
	 * @param @return
	 * @param @throws
	 *            IOException
	 * @return String
	 * @throw
	 *//*
	public static String inputStreamUtil(InputStream inputStream) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int i = -1;
		try {
			while ((i = inputStream.read()) != -1) {
				baos.write(i);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return baos.toString();
	}
	
	*//**
	 * 
	 * @Title: convertStringToStream 字符串转文件流
	 * @Description: TODO
	 * @param @param content
	 * @return InputStream
	 * @throw
	 *//*
	public static InputStream convertStringToStream(String content) {
		
		InputStream is = null;
		try {
			is = new ByteArrayInputStream(content.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return is;
		
	}
	
	public static void main(String[] args) {
		File file = new File("D:/test.jpg");
		
		OSSClientUtil cclient = new OSSClientUtil();
		try {
			FileInputStream fli=new FileInputStream(file);
			String str= cclient.putImage(fli, "test", "sdf");
			System.out.println(str);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	// OSSClient client = new OSSClient("http://oss-cn-shenzhen.aliyuncs.com",accessKeyId, accessKeySecret);

	}

}
*/