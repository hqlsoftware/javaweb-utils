package com.heqilin.util;

import com.heqilin.util.model.ResultEnum;
import com.heqilin.util.model.SelectVO;
import com.heqilin.util.model.TreeVO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author heqilin
 * date:  2019-01-19
 **/
public class EnumUtil {

    /**
     * 获取下拉列表数据(枚举表中)
     * @param enumInstance
     * @param getKey
     * @param getValue
     * @param selectedPredicate
     * @param <T>
     * @return
     */
    public static <T extends Enum<T>> List<SelectVO> getSelectVOList(T[] enumInstance, Function<T, Object> getKey, Function<T, Object> getValue, Function<T, Boolean> selectedPredicate) {
        if (enumInstance == null || enumInstance.length <= 0) {
            return null;
        }
        return getSelectVOListSub(Arrays.asList(enumInstance), getKey, getValue, selectedPredicate);
    }

    /**
     * 获取下拉列表数据(枚举表中)(可根据分组来取出部分枚举)
     * @param enumInstance
     * @param filterEnumInstance 可根据分组来取出部分枚举
     * @param getKey
     * @param getValue
     * @param selectedPredicate
     * @param <T>
     * @return
     */
    public static <T extends Enum<T>> List<SelectVO> getSelectVOList(T[] enumInstance,Function<T, Boolean> filterEnumInstance, Function<T, Object> getKey, Function<T, Object> getValue, Function<T, Boolean> selectedPredicate) {
        if (enumInstance == null || enumInstance.length <= 0) {
            return null;
        }
        if(filterEnumInstance==null){
            return getSelectVOList(enumInstance,getKey,getValue,selectedPredicate);
        }
        List<T> list = Arrays.stream(enumInstance).filter(t->filterEnumInstance.apply(t)).collect(Collectors.toList());
        return getSelectVOListSub(list, getKey, getValue, selectedPredicate);
    }

    private static <T> List<SelectVO> getSelectVOListSub(List<T> enumInstance, Function<T, Object> getKey, Function<T, Object> getValue, Function<T, Boolean> selectedPredicate) {
        if (enumInstance == null || enumInstance.size() <= 0) {
            return null;
        }
        List<SelectVO> selectVOList = new ArrayList<>();
        for (T t : enumInstance) {
            if(selectedPredicate!=null){
                boolean selectedState = selectedPredicate.apply(t);
                if(selectedState){
                    selectVOList.add(new SelectVO().setKey(String.valueOf(getKey.apply(t))).setValue(String.valueOf(getValue.apply(t))).setSelected(1));
                }else{
                    selectVOList.add(new SelectVO().setKey(String.valueOf(getKey.apply(t))).setValue(String.valueOf(getValue.apply(t))));
                }
            }else{
                selectVOList.add(new SelectVO().setKey(String.valueOf(getKey.apply(t))).setValue(String.valueOf(getValue.apply(t))));
            }
        }

        return selectVOList;
    }

    public static void main(String[] args) {
        List<SelectVO> selectVOList = getSelectVOList(ResultEnum.values(),ResultEnum::getCode, ResultEnum::getMessage,null);
        for (SelectVO selectVO : selectVOList) {
            System.out.println(selectVO.toString());
        }
    }

}
