package com.heqilin.util.plugin.storage;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.Bucket;
import com.fasterxml.jackson.databind.JsonNode;
import com.heqilin.util.LogUtil;
import com.heqilin.util.model.Result;
import com.heqilin.util.model.ResultT;
import com.heqilin.util.model.UploadResult;
import com.heqilin.util.plugin.storage.aliyun.oss.*;
import com.heqilin.util.plugin.ueditor.define.State;
import com.heqilin.util.plugin.ueditor.upload.BinaryUploader;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author heqilin
 * date 2020/04/01
 */
public class AliyunStorageImpl implements IStorage {

    @Override
    public Result isConfigurationSuccess() {
        return null;
    }

    @Override
    public State uploadWithUEditorBackend(HttpServletRequest request, Map<String, Object> conf) {
        State state = BinaryUploader.save(request, conf);
        return handleState(request, state, conf);
    }

    @Override
    public ResultT<UploadResult> exportExcel(HttpServletRequest request, Supplier<Workbook> getWorkbook, String originalFileName) {
        Map<String, Object> conf = StorageUtil.getConf(request);
        State state = StorageUtil.saveExcel(getWorkbook.get(), conf,originalFileName);
        return handleState(request, state, conf).asUploadResult();
    }

    // region 内部方法
    /**
     * 处理State，上传到OSS
     * @param request
     * @param state
     * @param conf
     * @return
     */
    private State handleState(HttpServletRequest request, State state, Map<String, Object> conf) {
        JsonNode stateJson = StorageUtil.getStateJsonNode(state);
        String bucketName = OssClientProperties.bucketName;
        OSSClient client = OssClientFactory.newInstance();
        // auto create Bucket to default zone
        if (OssClientProperties.autoCreateBucket) {
            Bucket bucket = BucketService.create(client, bucketName);
            LogUtil.debug("Bucket 's {0} Created.", bucket.getName());
        }

        // upload type
        if (OssClientProperties.useAsynUploader) {
            OssAsynUploaderThreader asynThreader = new OssAsynUploaderThreader();
            asynThreader.init(stateJson, client, request);
            Thread uploadThreader = new Thread(asynThreader);
            uploadThreader.start();
        } else {
            OssSynUploader ossSynUploader = new OssSynUploader();
            ossSynUploader.upload(stateJson, client, request);
        }

        // storage type
        if (false == OssClientProperties.useLocalStorager) {
            String uploadFilePath = conf.get("rootPath") + stateJson.path("url").asText();
            File uploadFile = new File(uploadFilePath);
            if (uploadFile.isFile() && uploadFile.exists()) {
                uploadFile.delete();
            }
        }

        state.putInfo(
                "url",
                OssClientProperties.ossEndPoint
                        + stateJson.path("url").asText());

        /*
         * { "state": "SUCCESS", "title": "1415236747300087471.jpg", "original":
         * "a.jpg", "type": ".jpg", "url":
         * "/upload/image/20141106/1415236747300087471.jpg", "size": "18827" }
         */
        return state;
    }

    // endregion
}
