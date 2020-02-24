package com.heqilin.util;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

class LogUtilTest {

    @Test
    void writeLog() {
        FileUtil.delete("c:\\logs\\test.txt");
        LogUtil.writeLog("test","test","c:\\logs");
        String result = FileUtil.getFileContent("c:\\logs\\test.txt");
        result = StringUtil.removeSuffixString(result,System.lineSeparator());
        assertTrue(result.equals("test"));
    }

    @Test
    void trace() {
        LogUtil.trace("我是trace{0}","我是占位符号0");
    }

    @Test
    void debug() {
        LogUtil.debug("我是debug{},{0}");
    }

    @Test
    void info() {
        LogUtil.info("我是info","我是占位符号0");
    }

    @Test
    void warn() {
        LogUtil.warn("我是warn");
    }

    @Test
    void error() {
        try{
            double d = 1/0;
        }catch (Exception ex){
            LogUtil.error("我是error {0}","我是占位符号0",ex);
        }
    }
}