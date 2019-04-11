package utils;

import my.utils.utils.StringUtil;
import org.junit.Test;

import static org.junit.Assert.*;

public class StringUtilTest {

    @Test
    public void encodeUrl() {
        String str="创建";
        String result = "%E5%88%9B%E5%BB%BA";
        assertTrue(result.equals(StringUtil.encodeUrl(str)));
    }

    @Test
    public void decodeUrl() {
        String str="创建";
        String result = "%E5%88%9B%E5%BB%BA";
        assertTrue(str.equals(StringUtil.decodeUrl(result)));
    }

    @Test
    public void isNotEmpty() {
        assertTrue(!StringUtil.isNotEmpty(null));
        assertTrue(!StringUtil.isNotEmpty(""));
        assertTrue(StringUtil.isNotEmpty("aaaa"));
    }

    @Test
    public void isEmpty() {
        assertTrue(StringUtil.isEmpty(null));
        assertTrue(StringUtil.isEmpty(""));
        assertTrue(!StringUtil.isEmpty("aaaa"));
    }

    @Test
    public void isAllNotEmpty() {
        assertTrue(StringUtil.isAllNotEmpty(new String[]{"aaa","bbb","ccc"}));
        assertTrue(!StringUtil.isAllNotEmpty(new String[]{"aaa","bbb",""}));
        assertTrue(!StringUtil.isAllNotEmpty(new String[]{"aaa",null,"aaa"}));
    }

    @Test
    public void isIn() {
        assertTrue(StringUtil.isIn("aa",new String[]{"bb","aa"}));
        assertTrue(StringUtil.isIn(3,new Integer[]{3,6,7}));
        assertTrue(StringUtil.isIn(3.6,new Double[]{3.5,3.6,3.7}));
    }

    @Test
    public void repeatNStr() {
        assertEquals("******",StringUtil.repeatNStr(3,"**"));
    }

    @Test
    public void replaceNStar() {
        assertEquals("a**cdefg",StringUtil.replaceNStar("abgcdefg",1,2,"*"));
    }

    @Test
    public void removePrefixString() {
        assertEquals("abefg",StringUtil.removePrefixString("ababefg","ab"));
    }

    @Test
    public void removeSuffixString() {
        assertEquals("ababefgab",StringUtil.removeSuffixString("ababefgabab","ab"));
    }

    @Test
    public void firstUpper() {
        assertEquals("Ababefgabab",StringUtil.firstUpper("ababefgabab"));
    }

    @Test
    public void firstLower() {
        assertEquals("ababefgabab",StringUtil.firstLower("Ababefgabab"));
    }

    @Test
    public void isOneEmpty() {
        assertEquals(true,StringUtil.isOneEmpty(new String[]{"aa","","cc"}));
        assertEquals(true,StringUtil.isOneEmpty(new String[]{"aa",null,"cc"}));
        assertEquals(false,StringUtil.isOneEmpty(new String[]{"aa","dd","cc"}));
    }
}