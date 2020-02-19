package com.heqilin.util;

/**
 * TODO
 *
 * @author heqilin
 * date:  2019-01-19
 **/
public class EnumUtil<T> {

    public  Class <T>  entityClass;

    public  EnumUtil(){
        System.out.println(getClass().getGenericSuperclass());
        //entityClass  =  (Class < T > ) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[ 0 ];
    }

    public static void main(String[] args) {
        //System.out.println((ParameterizedType)new EnumUtil().getClass().getGenericSuperclass());
        System.out.println(new EnumUtil<Integer>().entityClass);
    }

}
