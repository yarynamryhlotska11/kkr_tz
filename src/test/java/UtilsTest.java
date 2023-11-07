import org.auth.Utils;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UtilsTest {
    @Test
    public void testConvertStreamToString() {
        String testString = "Hello, this is a test string.";
        InputStream inputStream = new ByteArrayInputStream(testString.getBytes());
        String convertedString = Utils.convertStreamToString(inputStream);
        assertEquals(testString, convertedString.trim());
    }
    @Test

    public void testParseQuery() {
        String testQuery = "key1=value1&key2=value2&key3=value3";
        Map<String, String> resultMap = Utils.parseQuery(testQuery);
        assertEquals(3, resultMap.size());
        assertTrue(resultMap.containsKey("key1"));
        assertTrue(resultMap.containsValue("value2"));
        assertTrue(resultMap.containsKey("key3"));
        assertEquals("value1", resultMap.get("key1"));
    }
}
