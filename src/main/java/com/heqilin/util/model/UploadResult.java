package com.heqilin.util.model;

import com.heqilin.util.plugin.ueditor.define.BaseState;
import com.heqilin.util.plugin.ueditor.define.State;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author heqilin
 * date 2020/03/31
 */
@Data
@Accessors(chain = true)
public class UploadResult {
    /**
     * 文件名称
     */
    @ApiModelProperty("文件名称")
    public String name;
    /**
     * 文件原始名称
     */
    @ApiModelProperty("文件原始名称")
    public String originalName;
    /**
     * 文件类型：.jpg
     */
    @ApiModelProperty("文件类型：.jpg")
    public String type;
    /**
     * 文件地址
     */
    @ApiModelProperty("文件地址")
    public String url;
    /**
     * 文件大小：字节
     */
    @ApiModelProperty("文件大小：字节")
    public long size;

//    //region 转化为UEditor中的State对象
//    public State ofState(){
//        BaseState state = new BaseState();
//        if(this.isSuccess()){
//            state.setState(true);
//            state.putInfo("original",this.getOriginalName());
//            state.putInfo("size",this.getSize());
//            state.putInfo("title",this.getName());
//            state.putInfo("type",this.getType());
//            state.putInfo("url",this.getUrl());
//        }else{
//            state.setState(false);
//            state.setInfo(this.getMessage());
//        }
//        return state;
//    }
//    //endregion
}

