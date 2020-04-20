package com.heqilin.util.plugin.storage;

import com.heqilin.util.core.SystemUtil;
import com.heqilin.util.model.Result;
import com.heqilin.util.model.ResultT;
import com.heqilin.util.model.UploadResult;
import com.heqilin.util.plugin.ueditor.ActionEnter;
import com.heqilin.util.plugin.ueditor.define.State;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 文件存储（本地存储/阿里OSS存储）
 *
 * @author heqilin
 * date 2020/03/31
 */
public interface IStorage {
    /**
     * 是否配置正确
     *
     * @return
     */
    Result isConfigurationSuccess();

    /**
     * 上传文件(基于UEditor),返回正常的Result
     * 注意：querystring 传递：action: uploadimage/uploadscrawl/uploadvideo
     * /uploadfile/catchimage/listfile/listimage
     *
     * @return
     */
    default ResultT<UploadResult> upload(HttpServletRequest request) {
        ResultT checkResult = StorageUtil.checkRequestParamer(request);
        if (!checkResult.isSuccess()) {
            return checkResult;
        }
        return uploadWithUEditorBackend(request, StorageUtil.getConf(request)).asUploadResult();
    }

    /**
     * 上传文件(基于UEditor) 返回格式满足于UEditor格式，前端使用
     * 注意：querystring 传递：action: config/uploadimage/uploadscrawl/uploadvideo
     * /uploadfile/catchimage/listfile/listimage
     *
     * @param request
     * @return
     */
    default String uploadWithUEditorFront(HttpServletRequest request) {
        return new ActionEnter(request, SystemUtil.getDefaultUploadPath()).exec();
    }

    /**
     * 上传文件(基于UEditor) 返回格式满足于UEditor格式
     * 注意：querystring 传递：action: config/uploadimage/uploadscrawl/uploadvideo
     * /uploadfile/catchimage/listfile/listimage
     *
     * @param request
     * @return
     */
    State uploadWithUEditorBackend(HttpServletRequest request, Map<String, Object> conf);

    /**
     * 导出Excel
     *
     * @param getWorkbook
     * @return
     */
    ResultT<UploadResult> exportExcel(HttpServletRequest request, Supplier<Workbook> getWorkbook, String originalFileName);
}
