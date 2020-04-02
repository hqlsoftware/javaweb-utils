package com.heqilin.util.plugin.storage.aliyun.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.heqilin.util.LogUtil;
import com.heqilin.util.SystemUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

/**
 * 同步上传文件到阿里云OSS
 *
 */
public class OssSynUploader extends Thread {

	public boolean upload(JsonNode stateJson, OSSClient client,
						  HttpServletRequest request) {
		String key = stateJson.path("url").asText().replaceFirst("/", "");
		try {
			FileInputStream fileInputStream = new FileInputStream(new File(
					SystemUtil.getDefaultUploadPath() + key));
			PutObjectResult result = ObjectService.putObject(client,
					OssClientProperties.bucketName, key, fileInputStream);
			LogUtil.debug("upload file to aliyun OSS object server success. ETag: {0}",result.getETag());
			return true;
		} catch (FileNotFoundException e) {
			LogUtil.error("upload file to aliyun OSS object server occur FileNotFoundException.");
		} catch (NumberFormatException e) {
			LogUtil.error("upload file to aliyun OSS object server occur NumberFormatException.");
		} catch (IOException e) {
			LogUtil.error("upload file to aliyun OSS object server occur IOException.");
		}
		return false;
	}

}
