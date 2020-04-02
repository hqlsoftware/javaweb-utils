package com.heqilin.util.plugin.storage;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heqilin.util.*;
import com.heqilin.util.model.Result;
import com.heqilin.util.model.ResultT;
import com.heqilin.util.model.UploadResult;
import com.heqilin.util.plugin.storage.IStorage;
import com.heqilin.util.plugin.storage.StorageFactory;
import com.heqilin.util.plugin.storage.aliyun.oss.ObjectService;
import com.heqilin.util.plugin.storage.aliyun.oss.OssClientFactory;
import com.heqilin.util.plugin.storage.aliyun.oss.OssClientProperties;
import com.heqilin.util.plugin.ueditor.ActionEnter;
import com.heqilin.util.plugin.ueditor.PathFormat;
import com.heqilin.util.plugin.ueditor.define.AppInfo;
import com.heqilin.util.plugin.ueditor.define.BaseState;
import com.heqilin.util.plugin.ueditor.define.FileType;
import com.heqilin.util.plugin.ueditor.define.State;
import com.heqilin.util.plugin.ueditor.upload.StorageManager;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 阿里云Oss上传
 *
 * @author heqilin
 * date 2020/03/19
 */
public class StorageUtil {

    private StorageUtil() {
        throw new AssertionError();
    }

    public static final IStorage INSTANCE = getStorageClient(null);

    /**
     * 获取自定义Storage客户端实现
     *
     * @param storageType
     * @return
     */
    public static IStorage getStorageClient(String storageType) {
        return StorageFactory.newInstance(storageType);
    }


    // region 静态方法

    /**
     * 检测 Request对象中的 action 参数是否正确
     *
     * @param request
     * @return
     */
    protected static ResultT<UploadResult> checkRequestParamer(HttpServletRequest request) {
        String action = request.getParameter("action");
        if (StringUtil.isEmpty(action)) {
            return ResultTUtil.errorWithInvalidParamer("action参数不能为空");
        }
        if (StringUtil.isIn(action, new String[]{"uploadimage", "uploadscrawl", "uploadvideo"
                , "uploadfile", "catchimage", "listfile", "listimage"})) {
            return ResultTUtil.success();
        }
        return ResultTUtil.errorWithInvalidParamer("action参数目前只支持：uploadimage/uploadscrawl/uploadvideo/uploadfile/catchimage/listfile/listimage");
    }

    /**
     * 获取配置文件集合
     *
     * @param request
     * @return
     */
    protected static Map<String, Object> getConf(HttpServletRequest request) {
        Map<String, Object> conf = new ActionEnter(request, SystemUtil.getDefaultUploadPath())
                .getUEditorConf(request.getParameter("action"));
        return conf;
    }

    protected static Map<String, Object> getConfWithExcel(HttpServletRequest request) {
        Map<String, Object> conf = new ActionEnter(request, SystemUtil.getDefaultUploadPath())
                .getUEditorConf("uploadfile");
        return conf;
    }

    protected static JsonNode getStateJsonNode(State state) {
        JsonNode stateJson = null;
        try {
            stateJson = new ObjectMapper().readTree(state.toJSONString());
        } catch (JsonProcessingException e) {
            AssertUtil.isTrue(false, "StateJsonNode解析异常：" + e.getMessage());
        }
        return stateJson;
    }

    protected static final State saveExcel(Workbook workbook,
                                           Map<String, Object> conf, String originFileName) {
        try {
            if (workbook == null) {
                return new BaseState(false, AppInfo.NOTFOUND_UPLOAD_DATA);
            }

            String savePath = (String) conf.get("savePath");
            String suffix = ".xlsx";
            savePath = savePath + suffix;

            long maxSize = ((Long) conf.get("maxSize")).longValue();

            savePath = PathFormat.parse(savePath, originFileName);

            String physicalPath = (String) conf.get("rootPath") + savePath;

            // 特殊处理文字名字
            if (StringUtil.isNotEmpty(originFileName)) {
                String physicalPathHandle = "";
                String[] temp = physicalPath.split("/");
                for (int i = 0; i < temp.length; i++) {
                    if (i == temp.length - 1) {
                        physicalPathHandle += originFileName
                                + temp[i];
                    } else {
                        physicalPathHandle += temp[i] + "/";
                    }
                }
                physicalPath = physicalPathHandle;
                savePath =physicalPath.replace((String) conf.get("rootPath"),"");
            }

            // FileOutputStream不会创建文件夹，要提前创建多层文件夹(win系统上其实路径配置是反着的)
            FolderUtil.create(physicalPath.replace(physicalPath.substring(physicalPath.lastIndexOf("/") + 1), ""));

            FileOutputStream fileOutputStream = new FileOutputStream(physicalPath);
            workbook.write(fileOutputStream);
            fileOutputStream.close();

            File tmpFile = new File(physicalPath);
            if (tmpFile.length() > maxSize) {
                tmpFile.delete();
                return new BaseState(false, AppInfo.MAX_SIZE);
            }

            State state = new BaseState(true);
            state.putInfo("size", tmpFile.length());
            state.putInfo("title", tmpFile.getName());

            if (state.isSuccess()) {
                state.putInfo("url", PathFormat.format(savePath));
                state.putInfo("type", suffix);
                state.putInfo("original", originFileName + suffix);
            }

            return state;
        } catch (IOException e) {
        }
        return new BaseState(false, AppInfo.IO_ERROR);
    }

    // endregion
}
