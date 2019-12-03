package my.utils.plugin.poi;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Excel属性
 * @author heqilin
 * date 2019/04/27
 */
@Data
@Accessors(chain = true)
public class ExcelProperty {
    /**
     * 公司
     */
    private String company = "";
    /**
     * 作者
     */
    private String author = "";
    /**
     * 应用程序名称
     */
    private String applicationName = "";
    /**
     * 最后修改人
     */
    private String lastAuthor = "";
    /**
     * 备注
     */
    private String comments = "";
    /**
     * 标题
     */
    private String title = "";
    /**
     * 主题
     */
    private String subject = "";
    /**
     * 关键词
     */
    private String keyWord = "";
    /**
     * 目录
     */
    private String catagory = "";
    /**
     * 管理员
     */
    private String manager = "";
}
