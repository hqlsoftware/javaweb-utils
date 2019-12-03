package my.utils.utils;

import my.utils.model.Result;
import my.utils.model.ResultEnum;

/**
 *
 *
 * @author heqilin
 * date:  2019-01-15 ok
 **/
public class ResultUtil {

    //region 常用两种
    /**
     * 返回成功
     **/
    public static Result success(Object data) {
        return new Result().setData(data);
    }

    public static Result success() {
        return success(null);
    }
    /**
     * 返回失败
     **/
    public static Result error(int code, String msg, Object data) {
        return new Result()
                .setSuccess(false)
                .setCode(code)
                .setMessage(msg)
                .setData(data);
    }
    public static Result error(int code, String msg) {
        return error(code,msg,null);
    }
    //endregion

    //region 简单封装

    /**
     * 基本参数不能为空
     **/
    public static Result errorWithInvalidParamer() {
        return error(ResultEnum.ErrorWithInvalidParamer.getCode(),ResultEnum.ErrorWithInvalidParamer.getMessage());
    }
    public static Result errorWithInvalidParamer(String msg) {
        return error(ResultEnum.ErrorWithInvalidParamer.getCode(),StringUtil.isEmpty(msg)?ResultEnum.ErrorWithInvalidParamer.getMessage():msg);
    }

    /**
     * 本次操作失败，请重试
     **/
    public static Result errorWithOperationFailed() {
        return error(ResultEnum.ErrorWithOperationFailed.getCode(),ResultEnum.ErrorWithOperationFailed.getMessage());
    }
    public static Result errorWithOperationFailed(String msg) {
        return error(ResultEnum.ErrorWithOperationFailed.getCode(),StringUtil.isEmpty(msg)?ResultEnum.ErrorWithOperationFailed.getMessage():msg);
    }

    /**
     * 未授权，无法访问
     **/
    public static Result errorWithNoneAuthorization() {
        return error(ResultEnum.ErrorWithNoneAuthorization.getCode(),ResultEnum.ErrorWithNoneAuthorization.getMessage());
    }
    public static Result errorWithNoneAuthorization(String msg) {
        return error(ResultEnum.ErrorWithNoneAuthorization.getCode(),StringUtil.isEmpty(msg)?ResultEnum.ErrorWithNoneAuthorization.getMessage():msg);
    }

    /**
     * 权限不足，无法访问
     **/
    public static Result errorWithAuthorizationDenied() {
        return error(ResultEnum.ErrorWithAuthorizationDenied.getCode(),ResultEnum.ErrorWithAuthorizationDenied.getMessage());
    }
    public static Result errorWithAuthorizationDenied(String msg) {
        return error(ResultEnum.ErrorWithAuthorizationDenied.getCode(),StringUtil.isEmpty(msg)?ResultEnum.ErrorWithAuthorizationDenied.getMessage():msg);
    }

    //endregion

    public static void main(String[] args) {
        System.out.println(success());
        System.out.println(success(new int[]{1,2,3}));
        System.out.println(error(401,null));
        System.out.println(errorWithInvalidParamer());
        System.out.println(errorWithAuthorizationDenied());
        System.out.println(errorWithNoneAuthorization());
        System.out.println(errorWithOperationFailed());
        System.out.println(errorWithOperationFailed().setData("为什么呢"));
    }

}
