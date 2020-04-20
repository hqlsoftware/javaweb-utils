package com.heqilin.util.plugin.ueditor.upload;

import com.heqilin.util.core.LogUtil;
import com.heqilin.util.plugin.storage.StorageUtil;
import com.heqilin.util.plugin.ueditor.define.State;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * 同步上传文件到阿里云OSS
 */
public class Uploader {

    private HttpServletRequest request = null;
    private Map<String, Object> conf = null;

    public Uploader(HttpServletRequest request, Map<String, Object> conf) {
        this.request = request;
        this.conf = conf;
    }

    public final State doExec() {

        String filedName = (String) this.conf.get("fieldName");
        State state = null;

        if ("true".equals(this.conf.get("isBase64"))) {
            state = Base64Uploader.save(this.request.getParameter(filedName),
                    this.conf);
        } else {
            state = StorageUtil.INSTANCE.uploadWithUEditorBackend(request, this.conf);
        }
        /*
         * { "state": "SUCCESS", "title": "1415236747300087471.jpg", "original":
         * "a.jpg", "type": ".jpg", "url":
         * "/upload/image/20141106/1415236747300087471.jpg", "size": "18827" }
         */
        LogUtil.debug(state.toJSONString());
        return state;
    }
}
