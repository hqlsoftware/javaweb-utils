package com.heqilin.util.plugin.poi;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 *
 * @author heqilin
 * date 2019/04/27
 */
@Data
@Accessors(chain = true)
public class HeadRoot {

    /**
     * 根节点
     */
    private HeadInfo root;

    /**
     * 表格头部信息
     */
    public class HeadInfo{
        /**
         * 表格名称
         */
        private String sheetname;
        /**
         * 表头属性
         */
        private List<CellProperty> head;
        /*
        * 一行几列
        */
        private Integer rowspan;
        /**
         * 默认宽度
         */
        private Integer defaultwidth;
        /**
         * 默认高度
         */
        private Integer defaultheight;
        /**
         * 边框颜色
         */
        private String bordercolor;
        /**
         * 边框风格 thin
         */
        private String borderstyle;
    }


}
