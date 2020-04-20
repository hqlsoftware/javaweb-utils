package com.heqilin.util;

import com.heqilin.util.core.LogUtil;
import com.heqilin.util.core.SystemUtil;
import org.junit.jupiter.api.Test;

class SystemUtilTest {

    @Test
    void isWindowsSystem() {
    }

    @Test
    void getLineSeparator() {
    }

    @Test
    void getSystemProperty() {
    }

    @Test
    void getProjectClassesPath() {
    }

    @Test
    void getProjectPath() {
    }

    @Test
    void getProjectName() {
        String projectName = SystemUtil.getProjectName();
        LogUtil.info(projectName);
        LogUtil.info(SystemUtil.getProjectClassesPath());
    }

    @Test
    void getMyUtilConfigPath_EhCache() {
    }

    @Test
    void getMyUtilConfigPath() {
    }

    @Test
    void getClassName() {
    }

    @Test
    void getMethodName() {
    }

    @Test
    void getTempDirectoryPath(){
        LogUtil.info(SystemUtil.getProjectPath());
    }

    @Test
    void getDefaultUploadMapperLocalDiskFolder(){
        System.out.println(SystemUtil.getDefaultUploadMapperLocalDiskFolder());
    }
}