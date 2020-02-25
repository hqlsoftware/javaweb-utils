package com.heqilin.util.json;

import com.heqilin.util.plugin.json.IJson;
import com.heqilin.util.plugin.json.JsonFactory;
import org.junit.Test;

import static org.junit.Assert.*;

public class JsonFactoryTest {

    @Test
    public void create() {
        IJson instance =  JsonFactory.newInstance(null);
        assertNotNull(instance);
        assertEquals("default",JsonFactory.JSON_TYPE);
    }
}