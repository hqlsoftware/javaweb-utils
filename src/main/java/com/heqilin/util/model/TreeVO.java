package com.heqilin.util.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author heqilin
 * date 2019/11/06
 * description 前端树形菜单视图
 */

@Data
@Accessors(chain = true)
public class TreeVO<PrimaryKeyType,ExtensionObj> {
    @ApiModelProperty("id")
    @JsonSerialize(using = ToStringSerializer.class)
    private PrimaryKeyType id;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("父级id")
    private PrimaryKeyType parentId;
    @ApiModelProperty("子项内容")
    private List<TreeVO> children;
    @ApiModelProperty("额外字段对象")
    private ExtensionObj extensionObj;
}
