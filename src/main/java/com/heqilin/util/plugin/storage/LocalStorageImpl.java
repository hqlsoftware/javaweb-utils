package com.heqilin.util.plugin.storage;

import com.fasterxml.jackson.databind.JsonNode;
import com.heqilin.util.*;
import com.heqilin.util.model.Result;
import com.heqilin.util.model.ResultT;
import com.heqilin.util.model.UploadResult;
import com.heqilin.util.plugin.ueditor.define.State;
import com.heqilin.util.plugin.ueditor.upload.BinaryUploader;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author heqilin
 * date 2020/03/31
 */
public class LocalStorageImpl implements IStorage {

    @Override
    public Result isConfigurationSuccess() {
        return ResultUtil.success();
    }

    @Override
    public State uploadWithUEditorBackend(HttpServletRequest request, Map<String, Object> conf) {
        State state = BinaryUploader.save(request, conf);
        return handleState(request, state);
    }

    @Override
    public ResultT<UploadResult> exportExcel(HttpServletRequest request, Supplier<Workbook> getWorkbook, String originalFileName) {
        State state = StorageUtil.saveExcel(getWorkbook.get(), StorageUtil.getConf(request),originalFileName);
        return handleState(request, state).asUploadResult();
    }


    // region 内部方法

    /**
     * 处理url路径
     *
     * @param request
     * @param state
     * @return
     */
    private State handleState(HttpServletRequest request, State state) {
        JsonNode stateJson = StorageUtil.getStateJsonNode(state);
        String url = "/" + SystemUtil.getDefaultUploadMapperLocalDiskFolder() + stateJson.path("url").asText();
        if (StringUtil.isNotEmpty(SystemUtil.getProjectName())) {
            url = "/" + SystemUtil.getProjectName() + url;
        }
        url = request.getScheme() + "://" + request.getServerName() + ":"
                + request.getServerPort() + request.getContextPath() + url;
        state.putInfo("url", url);
        return state;
    }
    // endregion
}
