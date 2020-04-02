package com.heqilin.util.plugin.ueditor.define;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heqilin.util.LogUtil;
import com.heqilin.util.ResultTUtil;
import com.heqilin.util.ResultUtil;
import com.heqilin.util.model.ResultT;
import com.heqilin.util.model.UploadResult;

/**
 * 处理状态接口
 *
 */
public interface State {
	
	public boolean isSuccess();
	
	public void putInfo(String name, String val);
	
	public void putInfo(String name, long val);
	
	public String toJSONString();

	/**
	 * 生成UploadResult对象
	 * @return
	 */
	default ResultT<UploadResult> asUploadResult(){
		JsonNode jsonNode = null;
		try {
			jsonNode = new ObjectMapper().readTree(this.toJSONString());
		} catch (JsonProcessingException e) {
			LogUtil.error(null,e);
			return ResultTUtil.errorWithOperationFailed("State解析异常");
		}
		if(this.isSuccess()){
            ResultT<UploadResult> result= ResultTUtil.success();
            UploadResult uploadResult = new UploadResult();
			uploadResult.setName(jsonNode.path("title").asText());
			uploadResult.setOriginalName(jsonNode.path("original").asText());
			uploadResult.setType(jsonNode.path("type").asText());
			uploadResult.setUrl(jsonNode.path("url").asText());
			uploadResult.setSize(jsonNode.path("size").asLong());
            result.setData(uploadResult);
            return result;
		}else{
            return ResultTUtil.errorWithOperationFailed(jsonNode.path("state").asText());
		}
	}

}
