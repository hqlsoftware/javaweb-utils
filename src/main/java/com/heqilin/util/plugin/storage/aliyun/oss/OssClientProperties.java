package com.heqilin.util.plugin.storage.aliyun.oss;

import java.util.Properties;

import com.heqilin.util.core.LogUtil;
import com.heqilin.util.core.PropUtil;
import com.heqilin.util.core.SystemUtil;

public class OssClientProperties {

	private static Properties prop = PropUtil.getProp(SystemUtil.getMyUtilConfigPath());
	// 阿里云是否启用配置
	public static boolean useStatus = false;
	public static String bucketName = "";
	public static String accessKeyId = "";
	public static String accessKeySecret = "";
	public static boolean autoCreateBucket = false;
	
	public static String ossCliendEndPoint = "";
	public static String ossEndPoint = "";
	public static boolean useCDN = false;
	public static String cdnEndPoint = "";
	
	public static boolean useLocalStorager = false;
	public static String uploadBasePath = "upload";
	public static boolean useAsynUploader = false;

	static {
		try {
			useStatus = "aliyunoss".equalsIgnoreCase((String) prop.get("my.util.plugin.storage.storageType")) ? true : false;
			useLocalStorager = "true".equalsIgnoreCase((String) prop.get("my.util.plugin.storage.useLocalStorager")) ? true : false;
			useAsynUploader = "true".equalsIgnoreCase((String) prop.get("my.util.plugin.storage.useAsynUploader")) ? true : false;

			bucketName = (String) prop.get("my.util.plugin.storage.bucketName");
			accessKeyId = (String) prop.get("my.util.plugin.storage.accessKeyId");
			accessKeySecret = (String) prop.get("my.util.plugin.storage.accessKeySecret");
			autoCreateBucket = "true".equalsIgnoreCase((String) prop.get("my.util.plugin.storage.autoCreateBucket")) ? true : false;
			
			ossCliendEndPoint = (String) prop.get("my.util.plugin.storage.ossCliendEndPoint");
			ossEndPoint = (String) prop.get("my.util.plugin.storage.ossEndPoint");
			useCDN = "true".equalsIgnoreCase((String) prop.get("my.util.plugin.storage.useCDN")) ? true : false;
			cdnEndPoint = (String) prop.get("my.util.plugin.storage.cdnEndPoint");
		} catch (Exception e) {
			LogUtil.error("找不到配置文件 --> 系统按照ueditor默认配置执行。");
		}
	}

}
