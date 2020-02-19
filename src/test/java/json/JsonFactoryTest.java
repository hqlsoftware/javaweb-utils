package json;

import lombok.var;
import com.heqilin.util.plugin.json.JsonFactory;
import org.junit.Test;

import static org.junit.Assert.*;

public class JsonFactoryTest {

    @Test
    public void create() {
        var instance =  JsonFactory.create(null);
        assertNotNull(instance);
        assertEquals("default",JsonFactory.jsonType);
    }
}