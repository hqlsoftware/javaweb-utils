package com.heqilin.util.plugin.storage.aliyun.oss;

import com.aliyun.oss.OSSClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.heqilin.util.core.LogUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * 异步上传文件到阿里云OSS
 *
 */
public class OssAsynUploaderThreader extends Thread {

	private JsonNode stateJson = null;
	private OSSClient client = null;
	private HttpServletRequest request = null;

	public OssAsynUploaderThreader() {
	}

	public void init(JsonNode stateJson, OSSClient client,
					 HttpServletRequest request) {
		this.stateJson = stateJson;
		this.client = client;
		this.request = request;
	}

	@Override
	public void run() {
		OssSynUploader ossSynUploader = new OssSynUploader();
		ossSynUploader.upload(stateJson, client, request);
		LogUtil.debug("asynchronous upload image to aliyun oss success.");
	}

}
