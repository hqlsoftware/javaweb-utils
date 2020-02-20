package com.heqilin.util.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author heqilin
 * date 2019/11/06
 * description 前端下拉列表框视图
 */

@Data
@Accessors(chain = true)
public class SelectVO<ExtensionObj> {
    @ApiModelProperty("键名（前端显示）")
    private String key;
    @ApiModelProperty("键值（传给后台）")
    private String value;
    @ApiModelProperty("额外字段")
    private ExtensionObj extensionObj;
    @ApiModelProperty("是否选中 1选中 0不选中")
    private int selected;
}
