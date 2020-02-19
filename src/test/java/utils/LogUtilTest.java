package utils;

import com.heqilin.util.LogUtil;
import org.junit.jupiter.api.Test;

class LogUtilTest {

    @Test
    void writeLog() {
        LogUtil.writeLog("bbcc","11",null);
    }

    @Test
    void info() {
        LogUtil.info("info",null);
    }

    @Test
    void warm() {
        LogUtil.warm("warm",null);
    }

    @Test
    void error() {
        LogUtil.error("error",null);
    }

    @Test
    void debug() {
        LogUtil.debug("debug",null);
    }

    @Test
    void trace() {
        LogUtil.trace("trace",null);
    }
}