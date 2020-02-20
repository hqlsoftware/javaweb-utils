package com.heqilin.util.model;

/**
 * @author heqilin
 * date 2019/11/02
 */
public enum  ResultEnum {

    ErrorWithAuthorizationDenied("",401,"权限不足，无法访问"),
    ErrorWithNoneAuthorization("",403,"未授权，无法访问"),
    ErrorWithOperationFailed("",500,"本次操作失败，请重试"),
    ErrorWithInvalidParamer("",400,"基本参数为空");

    ResultEnum(String group,int code,String message){
        this.group=group;
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;
    private String group;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public String getGroup() {
        return group;
    }
    public void setGroup(String group) {
        this.group = group;
    }

}
