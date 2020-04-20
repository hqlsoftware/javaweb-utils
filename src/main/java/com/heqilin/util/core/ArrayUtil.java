package com.heqilin.util.core;

import java.lang.reflect.Array;

/**
 * @author heqilin
 * @date 2020/02/20
 * @description 数组帮助类
 */
public class ArrayUtil {

    private ArrayUtil(){
        throw new AssertionError();
    }

    /**
     * 生成泛型数组
     * @param componentType
     * @param length
     * @param <T>
     * @return
     */
    @SuppressWarnings({ "unchecked", "hiding" })
    public static <T>  T[] getArray(Class<T> componentType,int length) {
        return (T[]) Array.newInstance(componentType, length);
    }
}
