package my.utils.plugin.poi;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 单元格的属性
 * @author heqilin
 * date 2019/04/27
 */
@Data
@Accessors(chain = true)
public class CellProperty {
    /**
     * 显示的文字
     */
    private String title;
    /**
     * 显示方式
     */
    private  String align;
    /**
     * 垂直显示方式
     */
    private String valign;
    /**
     * 背景颜色
     */
    private String bgcolor;
    /**
     * 字体大小
     */
    private String fontsize;
    /**
     * 字体颜色
     */
    private String fontcolor;
    /**
     * 单元格合并位置 （fromRow,toRow,fromColumn,toColumn）
     */
    private String cellregion;
    /**
     * 字体名称
     */
    private String fontname;
    /**
     * 字体大小，默认加粗
     */
    private String fontweight;
    /**
     * 宽度
     */
    private Integer width;
    /**
     * 高度
     */
    private Integer height;
    /**
     * 斜体
     */
    private  Boolean italic;
    /**
     * 中间线
     */
    private  Boolean strikeout;
    /**
     * 下划线
     */
    private Boolean underline;
}
