package com.heqilin.util.plugin.storage.aliyun.oss;

import com.aliyun.oss.OSSClient;
import com.heqilin.util.core.LogUtil;

/**
 * OSSClient是OSS服务的Java客户端，它为调用者提供了一系列的方法，用于和OSS服务进行交互<br>
 *
 */
public class OssClientFactory {

	private static OSSClient client = null;

	/**
	 * 新建OSSClient
	 * 
	 * @return client
	 */
	public static OSSClient newInstance(){
		if ( null == client){
			client = new OSSClient(OssClientProperties.ossCliendEndPoint, OssClientProperties.accessKeyId, OssClientProperties.accessKeySecret);
			LogUtil.info("OSSClient 创建成功.");
		}
		return client;
	}

}
